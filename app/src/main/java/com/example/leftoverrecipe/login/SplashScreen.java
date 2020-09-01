package com.example.leftoverrecipe.login;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.example.leftoverrecipe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {
    Animation anim1, anim2;
    private TextView title, subtitle;
    private ImageView logo;

    //    TEMP
    private FirebaseAuth.AuthStateListener mAuthStateListener;
//    TEMP


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        setViews();

        //        TEMP
        FirebaseAuth.getInstance().signOut();           //TEMP TO DELETE
        setAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, logo, Objects.requireNonNull(ViewCompat.getTransitionName(logo)));
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent, options.toBundle());
            }
        }, 2500);
    }

    private void setViews() {
        title = findViewById(R.id.title_textview);
        subtitle = findViewById(R.id.subtitle_textview);
        logo = findViewById(R.id.logo);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    private void setAnimation() {
        anim1 = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);
        anim2 = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);

        title.setAnimation(anim1);
        logo.setAnimation(anim2);
        subtitle.setAnimation(anim1);
    }
}