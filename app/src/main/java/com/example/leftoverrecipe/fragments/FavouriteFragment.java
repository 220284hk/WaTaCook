package com.example.leftoverrecipe.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leftoverrecipe.viewmodel.FavouriteViewModel;
import com.example.leftoverrecipe.R;
import com.google.firebase.auth.FirebaseAuth;

public class FavouriteFragment extends Fragment {

    private FavouriteViewModel mViewModel;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (firebaseAuth.getCurrentUser() == null)
            return inflater.inflate(R.layout.favourite_fragment_not_logged_in, container, false);
        else
            return inflater.inflate(R.layout.favourite_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);
        // TODO: Use the ViewModel
    }

}