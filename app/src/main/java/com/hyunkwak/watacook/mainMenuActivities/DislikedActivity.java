package com.hyunkwak.watacook.mainMenuActivities;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;

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
        recipesAdapter = new RecipesAdapter(getApplicationContext(), arrayList, HIDDEN);
        recyclerView.setAdapter(recipesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_context, menu);
//    }
}