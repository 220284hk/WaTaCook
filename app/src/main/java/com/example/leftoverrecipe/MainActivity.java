package com.example.leftoverrecipe;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.leftoverrecipe.auxiliaryClasses.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.leftoverrecipe.auxiliaryClasses.Strings.*;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child(USERS).child(FirebaseAuth.getInstance().getUid()).child(FULL_NAME);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
//        // Log.d("USERZ", "0 MainActivity: " + User.getInstance());
//        System.out.println("MainActitivty: " + User.getInstance());
//        if (firebaseAuth.getCurrentUser() == null)
//        else
//            Toast.makeText(this, "hello " + intent.getStringExtra("username"),Toast.LENGTH_SHORT).show();
        BottomNavigationView navBar = findViewById(R.id.bottom_menu);
        NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navBar, controller);
        greetUser();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (User.getInstance() != null) {
             Log.d(TAG, "MainActivity onStop method preupdateDB");
            User.getInstance().updateDBPreferences();
             Log.d(TAG, "MainActivity onStop method postupdateDB");
        }
    }

    private void greetUser() {
        if (User.getInstance() != null) {
            Toast.makeText(getApplicationContext(), "Welcome, " + User.getFullName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Welcome :)", Toast.LENGTH_SHORT).show();
        }
    }
}