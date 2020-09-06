package com.example.leftoverrecipe.mainMenuActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.example.leftoverrecipe.R;
import com.example.leftoverrecipe.auxiliaryClasses.User;

public class FeedbackActivity extends AppCompatActivity {
    private Button emailMeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        findViewById(R.id.activity_feedback_email_me_button).setOnClickListener(v -> {
//            Intent intent = new Intent(Intent.ACTION_SEND);//common intent
////            intent.setData(Uri.parse("mailto:")); // o
//            intent.putExtra(Intent.EXTRA_SUBJECT, "Billion dollar idea!!");
//            intent.putExtra(Intent.EXTRA_TEXT, "We're going to be rich buddy" );
////            intent.putExtra(Intent.EXTRA_EMAIL, User.getEmailAddress());
//            startActivity(intent);
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto",User.getEmailAddress(), null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Billion dollar idea!!");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "We're going to be rich buddy. It's definitely not a silly idea.");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        });
    }
}