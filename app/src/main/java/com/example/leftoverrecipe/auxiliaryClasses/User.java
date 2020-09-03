package com.example.leftoverrecipe.auxiliaryClasses;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.leftoverrecipe.auxiliaryClasses.Strings.DISLIKES;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.EMAIL_ADDRESS;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.FULL_NAME;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.LIKES;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.PHONE_NUMBER;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.PREFERENCES;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.USERNAME;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.USERS;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.USER_INFO;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.TAG;

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
        HashMap<String, HashMap> preferences = userData.get(PREFERENCES);
        likesMap = new HashMap<>();
        dislikesMap = new HashMap<>();
        if (preferences == null) {      //New account or no history of preferences
        } else {
            if (preferences.get(LIKES) != null) {
                for (Map.Entry<String, HashMap> x: ((HashMap<String, HashMap>) preferences.get(LIKES)).entrySet()) {
                    likesMap.put(x.getKey(), new Recipe(x.getValue()));
                }
            }
            if (preferences.get(DISLIKES) == null) {
                for (Map.Entry<String, HashMap> x: ((HashMap<String, HashMap>) preferences.get(DISLIKES)).entrySet()) {
                    likesMap.put(x.getKey(), new Recipe(x.getValue()));
                }
            }
            System.out.println("After retrieval: dislikesMap:" + dislikesMap.size());
            System.out.println("After retrieval: likesMap:" + likesMap.size());
        }
    }

    public void updateDBPreferences() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(USERS).child(firebaseAuth.getUid()).child(PREFERENCES);
        data = new HashMap<String, HashMap>();
        data.put(LIKES, likesMap);
        data.put(DISLIKES, dislikesMap);
        System.out.println("updateDBPreferences likesMap: " + likesMap);
        System.out.println("updateDBPreferences dislikesMap: " + dislikesMap);
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