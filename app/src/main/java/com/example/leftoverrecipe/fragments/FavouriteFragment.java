package com.example.leftoverrecipe.fragments;

import androidx.lifecycle.ViewModelProviders;

import static com.example.leftoverrecipe.auxiliaryClasses.Strings.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leftoverrecipe.MyParcelable;
import com.example.leftoverrecipe.RecipesAdapter;
import com.example.leftoverrecipe.auxiliaryClasses.Recipe;
import com.example.leftoverrecipe.auxiliaryClasses.User;
import com.example.leftoverrecipe.viewmodel.FavouriteViewModel;
import com.example.leftoverrecipe.R;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class FavouriteFragment extends Fragment {

    private FavouriteViewModel mViewModel;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private RecyclerView recyclerView;
    private RecipesAdapter recipesAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView = getActivity().findViewById(R.id.favourites_recyclerView);
        ArrayList<Recipe> arrayList = new ArrayList<Recipe>();
        for (Map.Entry<String, Recipe> x: User.getLikesMap().entrySet()) {
            Recipe recipe = x.getValue();
            arrayList.add(recipe);
        }
//        Log.d(TAG, "2 arrayList" + arrayList);

        recipesAdapter = new RecipesAdapter(getContext(), arrayList);
        recyclerView.setAdapter(recipesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


    }

//    public static FavouriteFragment newInstance() {
//        return new FavouriteFragment();
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (User.getInstance() == null)
            return inflater.inflate(R.layout.favourite_fragment_not_logged_in, container, false);
        else
            return inflater.inflate(R.layout.favourite_fragment, container, false);


    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);
//        // TODO: Use the ViewModel
//    }

}