package com.example.leftoverrecipe.auxiliaryClasses;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class User {
    public static String FULL_NAME = "Full name", USERNAME = "Username", EMAIL_ADDRESS = "Email address", PHONE_NUMBER = "Phone number";
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference;
    private static HashSet<String> likesSet, dislikesSet;
    private static HashMap<String, HashMap> data;
    private static HashMap<String, String> likesMap, dislikesMap;
    private static HashMap<String, String> userInfo;
    public static User user;

    public User(HashMap<String, String> userInfo) {
        User.userInfo = userInfo;
//        // Log.d("220284hk", "User : (in user method)" + userIn);
    }

    public static User getInstance() {
        return user;
    }

    public static void createInstance(HashMap<String, String> userInfo) {
        user = new User(userInfo);
    }

    public static void retrieveInstance(HashMap<String, HashMap> userData) {
         Log.d("220284hk", "retrieveInstance: " + userData);
        data = userData;
        createInstance(userData.get("User Info"));
        Log.d("220284hk", "dislikesMap:" + dislikesMap);
        ArrayList dislikesArray = (ArrayList) userData.get("Preferences").get("Dislikes");
        ArrayList likesArray = (ArrayList) userData.get("Preferences").get("Likes");
        likesSet = (likesArray == null? new HashSet<>() : new HashSet<String>(likesArray));
        dislikesSet = (dislikesArray == null? new HashSet<>() : new HashSet<String>(dislikesArray));
        Log.d("220284hk", "dislikesSet:" + dislikesSet);
        user = new User(userInfo);
    }


//    public  HashMap getData() {
//        data = new HashMap();
//        setUserLikes();
//        setUserDislikes();
//        data.put("Likes", new HashMap<>(likesMap));
//        data.put("Dislikes", new HashMap<>(dislikesMap));
//        data.put("User Info", new HashMap<>(userInfo));
//        return data;
//    }

    public void updateDBPreferences() {
        setUserLikes();
        setUserDislikes();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid()).child("Preferences");
        data = new HashMap<String, HashMap>();
        data.put("Likes", likesMap);
        data.put("Dislikes", dislikesMap);
        System.out.println("likesMap: " + likesMap.toString());
        System.out.println("dislikesMap: " + dislikesMap.toString());
        System.out.println("likesSet: " + likesSet.toString());
        System.out.println("dislikesSet: " + dislikesSet.toString());
        databaseReference.setValue(data);
    }


    public void setUserLikes() {
        likesMap = new HashMap<>();
        int count = 0;
        for (String x : likesSet) {
            likesMap.put(String.valueOf(count++), x);
        }
    }

    public void setUserDislikes() {
        dislikesMap = new HashMap<>();
        int count = 0;
        for (String x : dislikesSet) {
            dislikesMap.put(String.valueOf(count++), x);
        }
    }

//    public void updateLikesSet() {
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid()).child("Preferences");
//        likesRef = databaseReference.child("Likes");
//        dislikesRef = databaseReference.child("Dislikes");
//        likesRef.setValue(new ArrayList<>(likesSet));
//        setUserLikes();
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