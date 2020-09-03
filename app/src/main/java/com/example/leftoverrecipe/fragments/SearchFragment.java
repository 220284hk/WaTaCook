package com.example.leftoverrecipe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.leftoverrecipe.MyParcelable;
import com.example.leftoverrecipe.NoResultsActivity;
import com.example.leftoverrecipe.R;
import com.example.leftoverrecipe.SearchResultsActivity;
import com.example.leftoverrecipe.auxiliaryClasses.Recipe;
import com.example.leftoverrecipe.auxiliaryClasses.User;
import com.example.leftoverrecipe.viewmodel.SearchViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.leftoverrecipe.auxiliaryClasses.Strings.ID;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.READY_IN_MINUTES;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.SERVINGS;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.SOURCE_URL;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.TITLE;

@SuppressWarnings("ConstantConditions")
public class SearchFragment extends Fragment implements View.OnClickListener {
    public final static String KEY = "REQUEST_RESULT";
    private static int NUMBER_OF_RECIPES = 10;
    private ImageView searchButton;
    private EditText searchText;
    private SearchViewModel mViewModel;
    private RequestQueue requestQueue;
    private ImageView[] ingredientsImages;
    private String[] ingredientsStringArray;
    private ArrayList arrayList;
    private boolean[] ingredientsSelected;
    private int size, count;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
//        Find all views
        initialiseViews();
        attachListeners();
//        Request queue
        requestQueue = Volley.newRequestQueue(getContext());
        // TODO: Use the ViewModel
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    @Override
    public void onClick(View view) {
//        Search button clicked
        if (view instanceof ImageButton) {
            String ingredients = searchText.getText().toString();
            if (ingredients.equals("")) return;
            String url = String.format("https://api.spoonacular.com/recipes/search?query=%s&number=%s&apiKey=29345819847c4e6f89d18c63baca3cab", ingredients, NUMBER_OF_RECIPES);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
                try {
                    JSONArray results = response.getJSONArray("results");
//                    No results -> NoResultsActivity is shown. Else, put results into intent.
                    Intent intent = (results.length() == 0 ? new Intent(getContext(), NoResultsActivity.class) : new Intent(getContext(), SearchResultsActivity.class));
//                    Turning the JSONArray into an array of recipes
                    ArrayList<Recipe> recipeArray = resultsToRecipe(results);
//                    arrayList = new ArrayList<>(recipeArray);
                    MyParcelable object = new MyParcelable();
                    object.setArrList(recipeArray);
                    intent.putExtra(SearchFragment.KEY, object);
                    startActivity(intent);
                    ingredientsSelected = new boolean[size];
                    count = 0;
                    searchText.setText("");
                } catch (JSONException e) {
                    Log.e("JSON ERROR", "JSON ERROR", e.getCause());
                }
            }, error -> {
                Log.e("JSON ERROR", "list error" + error, error);
            });
            requestQueue.add(request);
        } else {
//        Ingredient image clicked
            Integer indexSelected = null;
            for (int i = 0; i < ingredientsImages.length; i++) {
                if (ingredientsImages[i].equals(view)) {
                    indexSelected = i;
                }
            }
//            Check to see if there is an attempt to add the same item
//            AND check to see if 3 items are already added
            if (ingredientsSelected[indexSelected]) {
                Toast.makeText(getContext(), "You've already selected this!", Toast.LENGTH_SHORT).show();
            } else if (count == 3) {
                Toast.makeText(getContext(), "It's not recommended that you add more than 3 items!", Toast.LENGTH_SHORT).show();
            } else {
                ingredientsSelected[indexSelected] = true;
                count++;
                if (searchText.getText().toString().equals("")) {
                    searchText.setText(ingredientsStringArray[indexSelected]);
                } else {
                    searchText.setText(String.format("%s, %s", searchText.getText().toString(), ingredientsStringArray[indexSelected]));
                }
            }

        }

    }

    private ArrayList<Recipe> resultsToRecipe(JSONArray jsonArray) throws JSONException {
        int num = jsonArray.length(), j = 0;
        ArrayList<Recipe> recipeArray = new ArrayList<Recipe>();
        String id, sourceUrl, title, imageURL, servings, prepTime;
        for (int i = 0; i < num; i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            id = object.getString(ID);
            if (User.getDislikesMap().containsKey(id)) { continue; }
            sourceUrl = object.getString(SOURCE_URL);
            title = i + " " + object.getString(TITLE);
            imageURL = String.format("https://spoonacular.com/recipeImages/%s-480x360.jpg", object.get(ID));
            servings = "Serves: " + object.getString(SERVINGS);
            prepTime = object.getString(READY_IN_MINUTES);
            if (Integer.parseInt(prepTime) > 99)
                prepTime = "99+ mins";
            else
                prepTime += " mins";
            String[] strings = new String[]{id, sourceUrl, title, imageURL, servings, prepTime};
            recipeArray.add(new Recipe(strings));
        }
        return recipeArray;
    }

    private void initialiseViews() {
        searchButton = getActivity().findViewById(R.id.search_button);
        searchText = getActivity().findViewById(R.id.search_box);
        ingredientsStringArray = getResources().getStringArray(R.array.ingredients_string);
        size = ingredientsStringArray.length;
        ingredientsImages = new ImageView[size];
        ingredientsSelected = new boolean[size];
    }

    private void attachListeners() {
//        Button listener
        searchButton.setOnClickListener(this);
//        Ingredients listeners
        for (int i = 0; i < size; i++) {
            String ingredientID = "ingredient_" + ingredientsStringArray[i];
//            Log.d(TAG, "SearchFragment issue with: " + ingredientID + " " + i);
            int resID = getResources().getIdentifier(ingredientID, "id", getActivity().getPackageName());
            ingredientsImages[i] = ((ImageView) getActivity().findViewById(resID));
            ingredientsImages[i].setOnClickListener(this);
        }
    }
}