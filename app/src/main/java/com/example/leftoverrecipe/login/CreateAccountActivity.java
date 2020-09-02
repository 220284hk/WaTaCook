package com.example.leftoverrecipe.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.leftoverrecipe.R;
import com.example.leftoverrecipe.auxiliaryClasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText[] editTextArray;
    private String[] userInfo, editTextNameArray;
    private Button createButton;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference myRef;
    private DatabaseReference userRef;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        initialiseViews();

        createButton.setOnClickListener(v -> {
            if (!extractStrings()) return;
            Toast.makeText(getApplicationContext(), "Attempting to make an account. Please wait...", Toast.LENGTH_SHORT).show();
            User.getInstance(userInfo);
            startProcess();         //Create account -> Add User info to FireDatabase --> Send verification email
        });
    }

    private void sendVerificationEmail() {
        firebaseUser.sendEmailVerification().addOnCompleteListener(t -> {
            if (t.isSuccessful()) {
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "email not sent :( " + t.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addUserToDatabase() {
        myRef = FirebaseDatabase.getInstance().getReference();
        userRef = myRef.child("Users");
        userRef.child(firebaseUser.getUid()).child("User Info").setValue(User.getUserInfo());
        userRef.child(firebaseUser.getUid()).child("Full Name").setValue(User.getFullName());
//        HashMap<String, String> testMap = new HashMap<>();
//        testMap.put("test", "dude");
//        userRef.child(firebaseUser.getUid()).child("User Info").setValue(testMap);
        sendVerificationEmail();
    }

    private void startProcess() {
        mFirebaseAuth.createUserWithEmailAndPassword(User.getEmailAddress(), userInfo[4]).addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Account created", Toast.LENGTH_SHORT).show();
                    firebaseUser = mFirebaseAuth.getCurrentUser();
                    addUserToDatabase();
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initialiseViews() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {FirebaseAuth.getInstance().signOut();}
        editTextNameArray = getResources().getStringArray(R.array.editText_array);
        editTextArray = new EditText[editTextNameArray.length];
        for (int i = 0; i < editTextNameArray.length; i++) {
            int resID = getResources().getIdentifier(editTextNameArray[i], "id", this.getPackageName());
            editTextArray[i] = ((EditText) findViewById(resID));
//            System.out.println(editTextArray[i]);
        }
        createButton = findViewById(R.id.create_button);
        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    private boolean extractStrings() {
        userInfo = new String[editTextNameArray.length];
        for (int i = 0; i < editTextArray.length; i++) {
            String inputField = editTextArray[i].getText().toString();
            userInfo[i] = inputField;
            if (i != 3 && userInfo[i].isEmpty()) {
                Toast.makeText(this, "Please complete the " + editTextNameArray[i] + " field!", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    public void previousActivity(View view) {
        finish();
    }

}

