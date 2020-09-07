//package com.example.leftoverrecipe.fragments;
//
//import androidx.lifecycle.ViewModelProviders;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.leftoverrecipe.viewmodel.NewsViewModel;
//import com.example.leftoverrecipe.R;
//
//import java.util.zip.Inflater;
//
//public class NewsFragment extends Fragment {
//
//    private NewsViewModel mViewModel;
//
//    public static NewsFragment newInstance() {
//        return new NewsFragment();
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.news_fragment, container, false);
//    }
//
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
//        // TODO: Use the ViewModel
//    }
//
//}