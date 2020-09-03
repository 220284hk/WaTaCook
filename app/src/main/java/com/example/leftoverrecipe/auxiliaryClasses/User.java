package com.example.leftoverrecipe.auxiliaryClasses;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;

public class User {
    public static String FULL_NAME = "Full name", USERNAME = "Username", EMAIL_ADDRESS = "Email address", PHONE_NUMBER = "Phone number";
    public static User user;
    private static HashSet<Recipe> likesSet, dislikesSet;
    private static HashMap<String, HashMap> data;
    private static HashMap<String, Recipe> likesMap, dislikesMap;
    private static HashMap<String, String> userInfo;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference;

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
//        User has no previous preferences
        likesSet = new HashSet<Recipe>();
        dislikesSet = new HashSet<Recipe>();
        HashMap<String, HashMap> preferences = userData.get("Preferences");
        if (preferences == null) {
            likesMap = new HashMap<>();
            dislikesMap = new HashMap<>();
            return;
        } else {
            if (preferences.get("Likes") == null) {
                likesMap = new HashMap<>();
            } else {
                likesMap = preferences.get("Likes");
                for (Map.Entry<String, Recipe> x : likesMap.entrySet()) {
                    likesSet.add(x.getValue());
                }
            }
            if (preferences.get("Dislikes") == null) {
                dislikesMap = new HashMap<>();
            } else {
                dislikesMap = preferences.get("Dislikes");
                for (Map.Entry<String, Recipe> x : dislikesMap.entrySet()) {
                    dislikesSet.add(x.getValue());
                }
            }
//                System.out.println(likesMap.get("74172"));
//                System.out.println("likesMap: " + likesMap);
//                System.out.println("likesMap: " + likesMap.values());
//                HashMap<String, String> testMap = new HashMap<>();
//                testMap.put("hlelo" , "lheolhe");
//                System.out.println(testMap);
//                for (Collection x: likesMap.values()) {
//                    System.out.println(x);
//                }
//                likesSet.addAll(likesMap.values());
//                likesSet = new HashSet<Recipe>(likesMap.values());
//                HashSet<Recipe> test = new HashSet<Recipe>();
//                test.add(testRecipe);
//                System.out.println(likesSet);
//                System.out.println(test);
//            likesSet = (likesMap == null ? new HashSet<>() : new HashSet(likesMap.values()));

//            User has preferences but no history of dislikes
//            if (userData.get("Preferences").get("Dislikes") == null)
//                dislikesSet = new HashSet<Recipe>();
//            else {
//                dislikesMap = (HashMap) userData.get("Preferences").get("Dislikes");
//                dislikesSet = new HashSet<Recipe>(dislikesMap.values());
//            }
//                User has history of dislikes.
//            RISKY TEST
//                dislikesSet = (dislikesMap == null ? new HashSet<>() : new HashSet(dislikesMap.values()));
//        dislikesMap = (HashMap) userData.get("Preferences").get("Dislikes");
//        likesMap = (HashMap) userData.get("Preferences").get("Likes");
//            Log.d("220284hk", "dislikesMap.values:" + dislikesMap.values());
        }
//        Log.d("220284hk", "dislikesSet:" + dislikesSet + "\nlikesSet:" + likesSet);
//        user = new User(userInfo);
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

    public static HashSet<Recipe> getLikesSet() { return likesSet; }

    public static HashSet<Recipe> getDislikesSet() { return dislikesSet; }

    public static HashMap<String, String> getUserInfo() { return userInfo; }

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

    public static String getFullName() { return userInfo.get(FULL_NAME); }

    public static String getUsername() { return userInfo.get(USERNAME); }

    public static String getEmailAddress() { return userInfo.get(EMAIL_ADDRESS); }

    public static String getPhoneNumber() { return userInfo.get(PHONE_NUMBER); }

    public void updateDBPreferences() {
        setUserLikes();
        setUserDislikes();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid()).child("Preferences");
        data = new HashMap<String, HashMap>();
        data.put("Likes", likesMap);
        data.put("Dislikes", dislikesMap);
        System.out.println("0.0 likesMap: " + likesMap.toString());
        System.out.println("0.0 dislikesMap: " + dislikesMap.toString());
        System.out.println("0.0 likesSet: " + likesSet.toString());
        System.out.println("0.0 dislikesSet: " + dislikesSet.toString());
        databaseReference.setValue(data);
    }

    public void setUserLikes() {
//        likesMap = new HashMap<>();
        HashSet testSet = new HashSet();
        testSet.add("testing");
        testSet.add("yikes");
        testSet.add("friend");
        for (Object x: testSet) {
            System.out.println(x);
            System.out.println(((String) x));
        }
//        System.out.println(testSet);
//        System.out.println("setUserLikes: likesSet" + likesSet);
//        System.out.println("setUserLikes: likesMap" + likesMap);
        for (Object x : likesSet) {
//            likesMap.put(x.getId(), x);
            System.out.println(x);
        }
//        LinkedHashSet linkedHashSet = new LinkedHashSet();
//        linkedHashSet.add("hello");
//        Recipe x = likesSet.forEach();
//        Recipe[] likesArray = (Recipe[]) likesSet.toArray();
//        for (int i = 0; i < likesSet.size(); i++) {
//            likesMap.put(likesArray[i].getId(), likesArray[i]);
//        }
    }

    public void setUserDislikes() {
//        dislikesMap = new HashMap<String, Recipe>();
        for (Recipe x : dislikesSet) {
            System.out.println(x);
//            dislikesMap.put(x.getId(), x);
//            System.out.println(x);
//            System.out.println(x.getId());
        }
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s: %s\n%s: %s\n\"%s: %s\n%s: %s\n", FULL_NAME, getFullName(), USERNAME, getUsername(), EMAIL_ADDRESS, getEmailAddress(), PHONE_NUMBER, getPhoneNumber());
    }
}