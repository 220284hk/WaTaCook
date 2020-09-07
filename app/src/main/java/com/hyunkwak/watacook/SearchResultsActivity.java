package com.hyunkwak.watacook;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.hyunkwak.watacook.auxiliaryClasses.MyParcelable;
import com.hyunkwak.watacook.auxiliaryClasses.Recipe;
import com.hyunkwak.watacook.fragments.SearchFragment;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipesAdapter recipesAdapter;
    private static Integer ONE = 1;

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
        recipesAdapter = new RecipesAdapter(this, arrayList, ONE);
        recyclerView.setAdapter(recipesAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recipesAdapter.notifyDataSetChanged();
    }

    public void pleaseLogIn(View view) {
        Toast.makeText(getApplicationContext(), R.string.please_log_in, Toast.LENGTH_SHORT).show();
    }
}