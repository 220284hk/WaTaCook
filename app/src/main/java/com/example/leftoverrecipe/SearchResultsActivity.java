package com.example.leftoverrecipe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.leftoverrecipe.auxiliaryClasses.Recipe;
import com.example.leftoverrecipe.auxiliaryClasses.User;
import com.example.leftoverrecipe.fragments.SearchFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class SearchResultsActivity extends AppCompatActivity {
    private static String LIKE = "like", DISLIKE = "dislike";
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(USERS).child(firebaseAuth.getUid()).child("Preferences");
    private DatabaseReference likesRef = databaseReference.child("Likes");
    private DatabaseReference dislikesRef = databaseReference.child("Dislikes");
    private HashSet<String> likesSet, dislikesSet;
    private RecyclerView recyclerView;
    private RecipesAdapter recipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        setupRecyclerView();
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            setUpPreferences();
//        }

//        System.out.println("------------------");
//        System.out.println(User.getInstance().getLikesSet());
//        System.out.println("------------------");

    }

    private void setUpPreferences() {
        likesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                likesSet = (dataSnapshot.getValue(HashSet.class) == null ? new HashSet<String>() : dataSnapshot.getValue(HashSet.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                    System.out.println(databaseError.getMessage());
                Log.e("SearchResultsActivity", databaseError.getMessage());
            }
        });
        dislikesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dislikesSet = (snapshot.getValue(HashSet.class) == null ? new HashSet<String>() : snapshot.getValue(HashSet.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("SearchResultsActivity", error.getMessage());
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview);
        Bundle b = getIntent().getExtras();
        MyParcelable object = b.getParcelable(SearchFragment.KEY);
        assert object != null;
        ArrayList<Recipe> arrayList = object.getArrList();
//        System.out.println(arrayList.get(0));
        recipesAdapter = new RecipesAdapter(this, arrayList);
        recyclerView.setAdapter(recipesAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recipesAdapter.notifyDataSetChanged();
    }

//    public void liked(View view) {
//        TextView idTextView = (TextView) ((ViewGroup) view.getParent()).getChildAt(0);
//        TextView titleTextView = (TextView) ((ViewGroup) view.getParent()).getChildAt(1);
//        String id = idTextView.getText().toString();
//        if (User.getLikesSet().contains(id)) {
//            Toast.makeText(this, titleTextView.getText().toString() + " has already been liked!", Toast.LENGTH_LONG).show();
//        } else {
//            User.getLikesSet().add(recipe);
//            Toast.makeText(this, titleTextView.getText().toString() + id + " has been liked. Added to favourites", Toast.LENGTH_LONG).show();
//            ((ImageView) view).setImageResource(R.drawable.favourite_post_icon);
//        }
//    }

//    public void disliked(View view) {
//        TextView idTextView = (TextView) ((ViewGroup) view.getParent()).getChildAt(0);
//        TextView titleTextView = (TextView) ((ViewGroup) view.getParent()).getChildAt(1);
//        String id = idTextView.getText().toString();
//        if (User.getDislikesSet().contains(id)) {
//            Toast.makeText(this, titleTextView.getText().toString() + " has already been disliked!", Toast.LENGTH_LONG).show();
//        } else {
//            User.getDislikesSet().add(id);
//            Toast.makeText(this, titleTextView.getText().toString() + id + " has been disliked. This will menu will not be shown in future searches!", Toast.LENGTH_LONG).show();
//        }
//    }
}