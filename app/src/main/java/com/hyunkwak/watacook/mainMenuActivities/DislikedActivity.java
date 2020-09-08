package com.hyunkwak.watacook.mainMenuActivities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyunkwak.watacook.R;
import com.hyunkwak.watacook.RecipesAdapter;
import com.hyunkwak.watacook.auxiliaryClasses.Recipe;
import com.hyunkwak.watacook.auxiliaryClasses.User;

import java.util.ArrayList;
import java.util.Map;

import static com.hyunkwak.watacook.auxiliaryClasses.Strings.HIDDEN;

public class DislikedActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipesAdapter recipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (User.getDislikesMap().isEmpty()) {
            setContentView(R.layout.activity_disliked_empty);
        } else {
            setContentView(R.layout.activity_disliked);
            setUpRecyclerView();
        }
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.favourites_recyclerView);
        ArrayList<Recipe> arrayList = new ArrayList<Recipe>();
        for (Map.Entry<String, Recipe> x : User.getDislikesMap().entrySet()) {
            Recipe recipe = x.getValue();
            arrayList.add(recipe);
        }
//        Log.d(TAG, "2 arrayList" + arrayList);

        recipesAdapter = new RecipesAdapter(getApplicationContext(), arrayList, HIDDEN);
        recyclerView.setAdapter(recipesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}