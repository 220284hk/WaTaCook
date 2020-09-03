package com.example.leftoverrecipe.auxiliaryClasses;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.example.leftoverrecipe.auxiliaryClasses.Strings.EMAIL_ADDRESS;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.FULL_NAME;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.PHONE_NUMBER;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.USERNAME;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.USER_INFO;

public class User {
    public static User user;
    private static HashMap<String, HashMap> data;
    private static HashMap<String, Recipe> likesMap, dislikesMap;
    private static HashMap<String, String> userInfo;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference;

    public User(HashMap<String, String> userInfo) {
        User.userInfo = userInfo;
    }

    public static void createInstance(HashMap<String, String> userInfo) { user = new User(userInfo); }

    public static void retrieveInstance(HashMap<String, HashMap> userData) {
        Log.d(TAG, "retrieveInstance: " + userData);
        data = userData;
        createInstance(userData.get(USER_INFO));
        HashMap<String, HashMap> preferences = userData.get("Preferences");
        if (preferences == null) {      //New account or no history of preferences
            likesMap = new HashMap<>();
            dislikesMap = new HashMap<>();
            return;
        } else {
            if (preferences.get("Likes") == null) {
                likesMap = new HashMap<>();
            } else {
                likesMap = preferences.get("Likes");
            }
            if (preferences.get("Dislikes") == null) {
                dislikesMap = new HashMap<>();
            } else {
                dislikesMap = preferences.get("Dislikes");
            }
            System.out.println("After retrieval: dislikesMap:" + dislikesMap.size());
            System.out.println("After retrieval: likesMap:" + likesMap.size());
        }
    }

    public void updateDBPreferences() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid()).child("Preferences");
        data = new HashMap<String, HashMap>();
        data.put("Likes", likesMap);
        data.put("Dislikes", dislikesMap);
        System.out.println("0.0 likesMap: " + likesMap.toString());
        System.out.println("0.0 dislikesMap: " + dislikesMap.toString());
//        System.out.println("0.0 likesSet: " + likesSet.toString());
//        System.out.println("0.0 dislikesSet: " + dislikesSet.toString());
        databaseReference.setValue(data);
    }


    public static User getInstance() {
        return user;
    }

    public static HashMap<String, String> getUserInfo() { return userInfo; }

    public static String getFullName() { return userInfo.get(FULL_NAME); }

    public static String getUsername() { return userInfo.get(USERNAME); }

    public static String getEmailAddress() { return userInfo.get(EMAIL_ADDRESS); }

    public static String getPhoneNumber() { return userInfo.get(PHONE_NUMBER); }

    public static HashMap<String, Recipe> getLikesMap() { return likesMap; }

    public static HashMap<String, Recipe> getDislikesMap() { return dislikesMap; }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s: %s\n%s: %s\n\"%s: %s\n%s: %s\n", FULL_NAME, getFullName(), USERNAME, getUsername(), EMAIL_ADDRESS, getEmailAddress(), PHONE_NUMBER, getPhoneNumber());
    }
}