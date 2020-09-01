//package com.example.leftoverrecipe.login;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.leftoverrecipe.R;
//import com.example.leftoverrecipe.auxiliaryClasses.User;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.concurrent.atomic.AtomicBoolean;
//
//public class CreateAccountActivityCLEAN extends AppCompatActivity {
//    private EditText[] editTextArray;
//    private String[] userInfo, editTextNameArray;
//    private Button createButton;
//    //    private FirebaseDatabase mFirebaseDatabase;
//    private FirebaseAuth mFirebaseAuth;
//    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users");
//    private FirebaseUser firebaseUser;
//    private User user;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_account);
//        initialiseViews();
//
//        createButton.setOnClickListener(v -> {
//            if (!extractStrings()) return;
//            Toast.makeText(getApplicationContext(), "Attempting to make an account. Please wait...", Toast.LENGTH_SHORT).show();
//            createLocalUser();      //done
//            if (!createAccount()) {
//                System.out.println("CAA: Error whilst creating an account");
//                return;
//            }
//            if (!addUserToDatabase()) {
//                System.out.println("CAA: Error whilst adding user to the database");
//                return;
//            }
//            if (!sendEmailAndVerify()) {
//                System.out.println("CAA: Error whilst sending email to user");
//            }
//        });
//
//    }
//
//    private boolean sendEmailAndVerify() {
//        final AtomicBoolean flag = new AtomicBoolean();
//        firebaseUser.sendEmailVerification().addOnCompleteListener(t -> {
//            if (t.isSuccessful()) {
//                Intent intent = new Intent(CreateAccountActivityCLEAN.this, LoginActivity.class);
//                startActivity(intent);
//                flag.set(true);
//            } else {
//                Toast.makeText(getApplicationContext(), "email not sent :( " + t.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                flag.set(false);
//            }
//        });
//        return flag.get();
//    }
//
//    private boolean addUserToDatabase() {
//        firebaseUser = mFirebaseAuth.getCurrentUser();
//        final AtomicBoolean flag = new AtomicBoolean();
//        myRef.child(firebaseUser.getUid()).child("User Info").setValue(user).addOnCompleteListener(t -> {
//            flag.set(t.isSuccessful());
////                Toast.makeText(getApplicationContext(), t.getException().getMessage(), Toast.LENGTH_SHORT).show();
//        });
//        myRef.child(firebaseUser.getUid()).child("Full Name").setValue(user.getFullname()).addOnCompleteListener(t -> {
//            if (!t.isSuccessful()) {
//                Toast.makeText(getApplicationContext(), t.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                flag.set(false);
//            }
//        });
//        return flag.get();
//    }
//
//    private boolean createAccount() {
//        AtomicBoolean flag = new AtomicBoolean();
//        mFirebaseAuth.createUserWithEmailAndPassword(user.getEmailAddress(), userInfo[4]).addOnCompleteListener(CreateAccountActivityCLEAN.this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(getApplicationContext(), "Account created", Toast.LENGTH_SHORT).show();
//                    flag.set(true);
//                    System.out.println("Just after set to true" + flag.get());
//                } else {
//                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    flag.set(false);
//                }
//            }
//            return flag.get();
//        }).wait();
////        System.out.println("towards the end" + flag.get());
////        return flag.get();
//    }
//
//    private void createLocalUser() {
//        user = new User(userInfo);
//    }
//
//    private void initialiseViews() {
//        editTextNameArray = getResources().getStringArray(R.array.editText_array);
//        editTextArray = new EditText[editTextNameArray.length];
//        for (int i = 0; i < editTextNameArray.length; i++) {
//            int resID = getResources().getIdentifier(editTextNameArray[i], "id", this.getPackageName());
//            editTextArray[i] = ((EditText) findViewById(resID));
////            System.out.println(editTextArray[i]);
//        }
//        createButton = findViewById(R.id.create_button);
//        mFirebaseAuth = FirebaseAuth.getInstance();
//
//    }
//
//    private boolean extractStrings() {
//        userInfo = new String[editTextNameArray.length];
//        for (int i = 0; i < editTextArray.length; i++) {
//            String inputField = editTextArray[i].getText().toString();
////            userInfo[i] = inputField.equals("") ? "Null" : inputField;
//            userInfo[i] = inputField;
//            if (i != 3 && userInfo[i].isEmpty()) {
//                Toast.makeText(this, "Please complete the " + editTextNameArray[i] + " field!", Toast.LENGTH_LONG).show();
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public void previousActivity(View view) {
//        finish();
//    }
//
//}
//
////Create local user
////Create account
////Add user info to database
////Send email address and ask to verify