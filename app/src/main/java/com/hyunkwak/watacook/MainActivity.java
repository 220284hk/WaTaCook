package com.hyunkwak.watacook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hyunkwak.watacook.auxiliaryClasses.User;
import com.hyunkwak.watacook.mainMenuActivities.AboutActivity;
import com.hyunkwak.watacook.mainMenuActivities.FeedbackActivity;
import com.hyunkwak.watacook.mainMenuActivities.DislikedActivity;
import com.hyunkwak.watacook.mainMenuActivities.UserInfoActivity;

import static com.hyunkwak.watacook.auxiliaryClasses.Strings.TAG;
import static com.hyunkwak.watacook.auxiliaryClasses.Strings.VERSION;
import static com.hyunkwak.watacook.auxiliaryClasses.User.SaidHi;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Version");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        BottomNavigationView navBar = findViewById(R.id.bottom_menu);
        NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navBar, controller);
        navBar.setOnNavigationItemReselectedListener(i -> { return; });  //This line took me 3 hours :))

        if (!SaidHi) {
            Log.d(TAG, "This has been called");
            greetUser();
            SaidHi = true;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.userInfo_settings:
//                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, UserInfoActivity.class));
                return true;
            case R.id.feedback_settings:
                startActivity(new Intent(this, FeedbackActivity.class));
//                Toast.makeText(getApplicationContext(), "Reset clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.show_dislikes_settings:
                startActivity(new Intent(this, DislikedActivity.class));
                return true;
            case R.id.check_updates_settings:
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (VERSION.equals(snapshot.getValue().toString()))
                            Toast.makeText(getApplicationContext(), "There are no updates at this present time", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "The latest version is: " + snapshot.getValue(), Toast.LENGTH_SHORT).show();

//                        Toast.makeText(getApplicationContext(), "There is an update available!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                //TODO
                return true;
            case R.id.about_settings:
//                Toast.makeText(getApplicationContext(), "Abouts clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}