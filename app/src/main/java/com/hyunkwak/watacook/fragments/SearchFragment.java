package com.hyunkwak.watacook.fragments;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hyunkwak.watacook.EasterEgg;
import com.hyunkwak.watacook.NoResultsActivity;
import com.hyunkwak.watacook.R;
import com.hyunkwak.watacook.SearchResultsActivity;
import com.hyunkwak.watacook.auxiliaryClasses.MyParcelable;
import com.hyunkwak.watacook.auxiliaryClasses.Recipe;
import com.hyunkwak.watacook.auxiliaryClasses.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.hyunkwak.watacook.auxiliaryClasses.Strings.ID;
import static com.hyunkwak.watacook.auxiliaryClasses.Strings.READY_IN_MINUTES;
import static com.hyunkwak.watacook.auxiliaryClasses.Strings.SERVINGS;
import static com.hyunkwak.watacook.auxiliaryClasses.Strings.SOURCE_URL;
import static com.hyunkwak.watacook.auxiliaryClasses.Strings.TITLE;

@SuppressWarnings("ConstantConditions")
public class SearchFragment extends Fragment implements View.OnClickListener {
    public final static String KEY = "REQUEST_RESULT";
    private final static int NUMBER_OF_RECIPES = 20;
    private static int LIMIT = 3;
    private ImageView searchButton;
    private EditText searchText;
    //    private SearchViewModel mViewModel;
    private RequestQueue requestQueue;
    private ImageView[] ingredientsImages;
    private String[] ingredientsStringArray;
    //    private ArrayList arrayList;
    private boolean[] ingredientsSelected;
    private int size, count;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
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
            if (ingredients.isEmpty()) return;
            if (ingredients.equals(getString(R.string.easter_egg))) {
                startActivity(new Intent(getContext(), EasterEgg.class));
                return;
            }
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
            } else if (count == LIMIT) {
                Toast.makeText(getContext(), "It's not recommended that you add more than " + LIMIT + " items!", Toast.LENGTH_SHORT).show();
            } else {
                ingredientsSelected[indexSelected] = true;
                count++;
                if (searchText.getText().toString().equals("")) {
                    searchText.setText(ingredientsStringArray[indexSelected]);
                } else {
                    if (!ingredientsStringArray[indexSelected].equals("red_onion"))
                        searchText.setText(String.format("%s, %s", searchText.getText().toString(), ingredientsStringArray[indexSelected]));
                    else
                        searchText.setText(String.format("%s, %s", searchText.getText().toString(), "red onion"));
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
//            Log.d(TAG, "User.getInstance().toString: " + User.getInstance().toString());
//            Log.d(TAG, "User.getDislikeMap().toString(): " + User.getDislikesMap().toString());
            if (User.getInstance() != null && User.getDislikesMap().containsKey(id)) {
                System.out.println(User.getDislikesMap());
                System.out.println(User.getDislikesMap().get(id));
                continue;
            }
            sourceUrl = object.getString(SOURCE_URL);
//            title = i + " " + object.getString(TITLE);
            title = object.getString(TITLE);
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