package com.example.leftoverrecipe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.leftoverrecipe.auxiliaryClasses.MyParcelable;
import com.example.leftoverrecipe.auxiliaryClasses.Recipe;
import com.example.leftoverrecipe.fragments.SearchFragment;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipesAdapter recipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview);
        Bundle b = getIntent().getExtras();
        MyParcelable object = b.getParcelable(SearchFragment.KEY);
        assert object != null;
        ArrayList<Recipe> arrayList = object.getArrList();
        recipesAdapter = new RecipesAdapter(this, arrayList);
        recyclerView.setAdapter(recipesAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recipesAdapter.notifyDataSetChanged();
    }

}