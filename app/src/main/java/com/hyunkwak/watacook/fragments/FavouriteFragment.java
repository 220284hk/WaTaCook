package com.hyunkwak.watacook.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyunkwak.watacook.R;
import com.hyunkwak.watacook.RecipesAdapter;
import com.hyunkwak.watacook.auxiliaryClasses.Recipe;
import com.hyunkwak.watacook.auxiliaryClasses.User;
import com.hyunkwak.watacook.login.LoginActivity;

import java.util.ArrayList;
import java.util.Map;

import static com.hyunkwak.watacook.auxiliaryClasses.Strings.FAVOURITES;

public class FavouriteFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecipesAdapter recipesAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (User.getInstance() != null) {
            if (!User.getLikesMap().isEmpty()) {
                setUpRecyclerView();
//            setUpViews();
            }
            Toolbar toolbar = view.findViewById(R.id.toolbar);
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        } else {
            view.findViewById(R.id.login_button_num_2).setOnClickListener(v -> {
                startActivity(new Intent(getContext(), LoginActivity.class));
            });
        }
    }


    public void setUpRecyclerView() {
        recyclerView = getActivity().findViewById(R.id.favourites_recyclerView);
        ArrayList<Recipe> arrayList = new ArrayList<Recipe>();
        if (User.getLikesMap() != null) {
            for (Map.Entry<String, Recipe> x : User.getLikesMap().entrySet()) {
                Recipe recipe = x.getValue();
                arrayList.add(recipe);
            }
        }
        recipesAdapter = new RecipesAdapter(getActivity(), arrayList, FAVOURITES);
        recyclerView.setAdapter(recipesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (User.getInstance() == null)
            return inflater.inflate(R.layout.favourite_fragment_not_logged_in, container, false);
        else if (User.getLikesMap().isEmpty())
            return inflater.inflate(R.layout.favourite_fragment_empty, container, false);
        else
            return inflater.inflate(R.layout.favourite_fragment, container, false);


    }

}