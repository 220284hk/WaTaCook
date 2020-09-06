package com.example.leftoverrecipe.mainMenuActivities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leftoverrecipe.R;
import com.example.leftoverrecipe.RecipesAdapter;
import com.example.leftoverrecipe.auxiliaryClasses.Recipe;
import com.example.leftoverrecipe.auxiliaryClasses.User;

import java.util.ArrayList;
import java.util.Map;

public class TempDeleteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipesAdapter recipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_delete);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.favourites_recyclerView);
        ArrayList<Recipe> arrayList = new ArrayList<Recipe>();
        for (Map.Entry<String, Recipe> x : User.getDislikesMap().entrySet()) {
            Recipe recipe = x.getValue();
            arrayList.add(recipe);
        }
//        Log.d(TAG, "2 arrayList" + arrayList);

        recipesAdapter = new RecipesAdapter(getApplicationContext(), arrayList, 2);
        recyclerView.setAdapter(recipesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}