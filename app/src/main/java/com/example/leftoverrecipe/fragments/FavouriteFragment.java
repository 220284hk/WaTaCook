package com.example.leftoverrecipe.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leftoverrecipe.R;
import com.example.leftoverrecipe.RecipesAdapter;
import com.example.leftoverrecipe.auxiliaryClasses.Recipe;
import com.example.leftoverrecipe.auxiliaryClasses.TempDeleteActivity;
import com.example.leftoverrecipe.auxiliaryClasses.User;

import java.util.ArrayList;
import java.util.Map;

public class FavouriteFragment extends Fragment {
    private static Integer TWO = 2;
    //    private FavouriteViewModel mViewModel;
//    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private RecyclerView recyclerView;
    private RecipesAdapter recipesAdapter;
    private Button resetButton, showDeletedButton;
    private AlertDialog alertDialog;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (User.getInstance() != null) {
            setUpRecyclerView();
            resetButton = view.findViewById(R.id.reset_button);
            createAlertDialog();
            resetButton.setOnClickListener(v -> {
                alertDialog.show();
            });
            showDeletedButton = view.findViewById(R.id.show_deleted);
            showDeletedButton.setOnClickListener(v -> {
                startActivity(new Intent(getActivity(), TempDeleteActivity.class));
            });


        }
    }

    private void createAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Delete data");
        alertDialogBuilder.setMessage("Are you sure you want to delete all data?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                User.getInstance().resetPreferences();
                Toast.makeText(getContext(), "Data deleted", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "Data not deleted", Toast.LENGTH_SHORT).show();
                dialogInterface.cancel();
            }
        });
        alertDialog = alertDialogBuilder.create();
    }

    private void setUpRecyclerView() {
        recyclerView = getActivity().findViewById(R.id.favourites_recyclerView);
        ArrayList<Recipe> arrayList = new ArrayList<Recipe>();
        if (User.getLikesMap() != null) {
            for (Map.Entry<String, Recipe> x : User.getLikesMap().entrySet()) {
                Recipe recipe = x.getValue();
                arrayList.add(recipe);
            }
        }
//        Log.d(TAG, "2 arrayList" + arrayList);

        recipesAdapter = new RecipesAdapter(getContext(), arrayList, TWO);
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