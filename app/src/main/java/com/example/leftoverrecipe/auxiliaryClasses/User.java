package com.example.leftoverrecipe.auxiliaryClasses;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.HashSet;

public class User {
    //    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//    private static DatabaseReference databaseReference, likesRef, dislikesRef;
    private static HashSet<String> likesSet = new HashSet<String>(), dislikesSet = new HashSet<String>();
    private static HashMap<String, HashMap> data;
    private static HashMap<Integer, String> likesMap, dislikesMap;
    private static HashMap<String, String> userInfo;
    private static String FULL_NAME = "Full name", USERNAME = "Username", EMAIL_ADDRESS = "Email address", PHONE_NUMBER = "Phone number";
    private static User user = null;


    public User(String... userInfo) {
        User.userInfo = new HashMap<>();
        User.userInfo.put(FULL_NAME, userInfo[0]);
        User.userInfo.put(USERNAME, userInfo[1]);
        User.userInfo.put(EMAIL_ADDRESS, userInfo[2]);
        User.userInfo.put(PHONE_NUMBER, userInfo[3]);
        Log.d("220284hk", "User :" + User.userInfo.toString());
    }

//    public static HashMap getData() {
//        data = new HashMap();
//        setUserLikes();
//        setUserDislikes();
//        data.put("Likes", new HashMap<>(likesMap));
//        data.put("Dislikes", new HashMap<>(dislikesMap));
//        data.put("User Info", new HashMap<>(userInfo));
//        return data;
//    }

    public static User getInstance() { return user; }

    public static User getInstance(String... userInfo) {
        Log.d("220284hk", "User is being initialised");
        user = new User(userInfo);
        return user;
    }

    public static void setUserLikes() {
        likesMap = new HashMap<>();
        int count = 0;
        for (String x : likesSet) {
            likesMap.put(count++, x);
        }
    }

    public static void setUserDislikes() {
        int count = 0;
        for (String x : dislikesSet) {
            dislikesMap.put(count++, x);
        }
    }

//    public void updateLikesSet() {
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid()).child("Preferences");
//        likesRef = databaseReference.child("Likes");
//        dislikesRef = databaseReference.child("Dislikes");
//        likesRef.setValue(new ArrayList<>(likesSet));
//    }

//    public void updateDislikesSet() {
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid()).child("Preferences");
//        likesRef = databaseReference.child("Likes");
//        dislikesRef = databaseReference.child("Dislikes");
//        dislikesRef.setValue(new ArrayList<>(dislikesSet));
//    }

    public static HashSet<String> getLikesSet() { return likesSet; }

    public static HashSet<String> getDislikesSet() { return dislikesSet; }

    public static HashMap<String, String> getUserInfo() { return userInfo; }

    public static String getFullName() { return userInfo.get(FULL_NAME); }

    public static String getUsername() { return userInfo.get(USERNAME); }

    public static String getEmailAddress() { return userInfo.get(EMAIL_ADDRESS); }

    public static String getPhoneNumber() { return userInfo.get(PHONE_NUMBER); }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s: %s\n%s: %s\n\"%s: %s\n%s: %s\n", FULL_NAME, getFullName(), USERNAME, getUsername(), EMAIL_ADDRESS, getEmailAddress(), PHONE_NUMBER, getPhoneNumber());
    }
}