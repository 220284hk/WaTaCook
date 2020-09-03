package com.example.leftoverrecipe.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.leftoverrecipe.MainActivity;
import com.example.leftoverrecipe.R;
import com.example.leftoverrecipe.auxiliaryClasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static com.example.leftoverrecipe.auxiliaryClasses.Strings.USERS;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private EditText mUserName, mPassword;
//    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialiseViews();
    }

    public void skip(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        if (isFieldEmpty()) { return; }
//        Toast.makeText(getApplicationContext(), "Attempting to log in...", Toast.LENGTH_SHORT).show();
        mFirebaseAuth.signInWithEmailAndPassword(mUserName.getText().toString(), mPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && mFirebaseAuth.getCurrentUser().isEmailVerified()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    if (User.getInstance() == null) { //This will be the case if app has just started and the user is NOT creating a new account
                        FirebaseDatabase.getInstance().getReference().child(USERS).child(mFirebaseAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (task.isSuccessful()) {
                                    // Log.d(TAG, "onDataChange method: " + snapshot.getValue().toString());
                                    HashMap<String, HashMap> data = (HashMap) snapshot.getValue();
                                    Log.d("LoginActivity: ", "Data retrieved from firebase: " + data);
                                    User.retrieveInstance(data);
                                    // Log.d("USER", "Global user added!");
                                    startActivity(intent);
                                } else {
                                    System.out.println(task.getException().getCause());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        startActivity(intent);
                    }
                } else if (task.isSuccessful()) {
                    mFirebaseAuth.signOut();
                    Toast.makeText(getApplicationContext(), "Please verify your email address", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong email or password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isFieldEmpty() {
        if (mUserName.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Input username!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (mPassword.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Input password!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    private void initialiseViews() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mUserName = findViewById(R.id.user_name_edittext);
        mPassword = findViewById(R.id.password_edittext);
    }
}