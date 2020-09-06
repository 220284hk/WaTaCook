package com.example.leftoverrecipe.mainMenuActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leftoverrecipe.R;
import com.example.leftoverrecipe.auxiliaryClasses.User;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_NEUTRAL;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener, AlertDialog.OnClickListener {
    private static int FLAG;
    private Button dislikesButton, favouritesButton;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private TextView fullName, username, phoneNumber, emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initialiseViews();
        setText();
        attachListeners();
    }

    private void setText() {
        fullName.setText(String.format("Full name: %s", User.getFullName()));
        username.setText(String.format("Username: %s", User.getUsername()));
        emailAddress.setText(String.format("Email address: %s", User.getEmailAddress()));
        if (User.getPhoneNumber() != null) {
            phoneNumber.setText(String.format("Phone number: %s", User.getPhoneNumber()));
        }
    }

    private void attachListeners() {
        favouritesButton.setOnClickListener(this);
        dislikesButton.setOnClickListener(this);
    }

    private void initialiseViews() {
        favouritesButton = findViewById(R.id.user_info_reset_favourites_button);
        dislikesButton = findViewById(R.id.reset_dislikes_button);
        alertDialogBuilder = new AlertDialog.Builder(this);
        fullName = findViewById(R.id.user_info_full_name_textView);
        username = findViewById(R.id.user_info_user_name_textView);
        phoneNumber = findViewById(R.id.user_info_phone_number_textView);
        emailAddress = findViewById(R.id.user_info_email_address_textView);

    }

    @Override
    public void onClick(View view) {
        if (view.getTag().equals(getResources().getString(R.string.reset_favourites))) {
            alertDialogBuilder.setTitle("Reset favourites?");
            alertDialogBuilder.setMessage("Are you sure you wish to reset favourited recipes? This process is irreversible");
            FLAG = 1;
        } else {
            alertDialogBuilder.setTitle("Reset dislikes?");
            alertDialogBuilder.setMessage("Are you sure you wish to reset disliked recipes? This process is irreversible");
            FLAG = 0;
        }
        alertDialogBuilder.setPositiveButton("I am sure dood!", this);
        alertDialogBuilder.setNegativeButton("I'll think about it!", this);
        dialog = alertDialogBuilder.create();
        dialog.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
            case BUTTON_NEGATIVE:
                dialogInterface.dismiss();
                break;
            case BUTTON_POSITIVE:
                if (FLAG == 1) {
                    Toast.makeText(getApplicationContext(), "Likes has been reset. Adios", Toast.LENGTH_SHORT).show();
                    User.getLikesMap().clear();
                } else {
                    Toast.makeText(getApplicationContext(), "Dislikes has been reset. Tschüß!", Toast.LENGTH_SHORT).show();
                    User.getDislikesMap().clear();
                }
                User.getInstance().updateDBPreferences();
                break;
        }
    }
}