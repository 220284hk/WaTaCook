package com.example.leftoverrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.leftoverrecipe.mainMenuActivities.AboutActivity;
import com.example.leftoverrecipe.mainMenuActivities.TempDeleteActivity;
import com.example.leftoverrecipe.auxiliaryClasses.User;
import com.example.leftoverrecipe.mainMenuActivities.UserInfoActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.leftoverrecipe.auxiliaryClasses.Strings.TAG;

public class MainActivity extends AppCompatActivity {
//    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child(USERS).child(FirebaseAuth.getInstance().getUid()).child(FULL_NAME);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        BottomNavigationView navBar = findViewById(R.id.bottom_menu);
        NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navBar, controller);
        greetUser();

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
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                userMenuSelected();
                return true;
            case R.id.reset_settings:
                Toast.makeText(getApplicationContext(), "Reset clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.show_dislikes_settings:
                startActivity(new Intent(this, TempDeleteActivity.class));
                return true;
            case R.id.check_updates_settings:
                Toast.makeText(getApplicationContext(), "There are no updates at this present time", Toast.LENGTH_SHORT).show();
                //TODO
                return true;
            case R.id.about_settings:
                Toast.makeText(getApplicationContext(), "Abouts clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void userMenuSelected() {
        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
    }
}