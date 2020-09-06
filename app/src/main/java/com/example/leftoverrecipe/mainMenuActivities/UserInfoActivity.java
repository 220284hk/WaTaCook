package com.example.leftoverrecipe.mainMenuActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initialiseViews();
        attachListeners();
    }

    private void attachListeners() {
        favouritesButton.setOnClickListener(this);
        dislikesButton.setOnClickListener(this);
    }

    private void initialiseViews() {
        favouritesButton = findViewById(R.id.reset_favourites_button);
        dislikesButton = findViewById(R.id.reset_dislikes_button);
        alertDialogBuilder = new AlertDialog.Builder(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getTag().equals(getResources().getString(R.string.reset_favourites))) {
            alertDialogBuilder.setTitle("Reset Likes");
            alertDialogBuilder.setMessage("Are you sure you wish to reset favourited recipes? This process is irreversible");
            FLAG = 1;
        } else {
            alertDialogBuilder.setTitle("Reset dislikes");
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