package com.example.leftoverrecipe.auxiliaryClasses;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.example.leftoverrecipe.auxiliaryClasses.Strings.DISLIKES;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.EMAIL_ADDRESS;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.FULL_NAME;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.LIKES;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.PHONE_NUMBER;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.PREFERENCES;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.TAG;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.TEMP;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.USERNAME;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.USERS;
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

    public static void createInstance(HashMap<String, String> userInfo) {
        likesMap = new HashMap<>();
        dislikesMap = new HashMap<>();
        user = new User(userInfo);
    }

    public static void retrieveInstance(HashMap<String, HashMap> userData) {
        Log.d(TAG, "retrieveInstance: " + userData);
        data = userData;
        createInstance(userData.get(USER_INFO));
        HashMap<String, HashMap> preferences = userData.get(PREFERENCES);
        likesMap = new HashMap<>();
        dislikesMap = new HashMap<>();
        if (preferences != null) {      //New account or no history of preferences
            if (preferences.get(LIKES) != null) {
                for (Map.Entry<String, HashMap> x : ((HashMap<String, HashMap>) preferences.get(LIKES)).entrySet()) {
                    likesMap.put(x.getKey(), new Recipe(x.getValue()));
                }
                Log.d(TAG, "<USER> retrieveInstance, likesMap size: " + likesMap.size());
            }
            if (preferences.get(DISLIKES) != null) {
                for (Map.Entry<String, HashMap> x : ((HashMap<String, HashMap>) preferences.get(DISLIKES)).entrySet()) {
                    dislikesMap.put(x.getKey(), new Recipe(x.getValue()));
                }
                Log.d(TAG, "<USER> retrieveInstance, dislikesMap size: " + dislikesMap.size());
            }
        }
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

    public void updateDBPreferences() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(USERS).child(firebaseAuth.getUid()).child(PREFERENCES);
        DatabaseReference tempRef = FirebaseDatabase.getInstance().getReference().child(USERS).child(firebaseAuth.getUid()).child(TEMP);
        data = new HashMap<>();
        data.put(LIKES, likesMap);
        data.put(DISLIKES, dislikesMap);
        Log.d(TAG, "updateDBPreferences likesMap: " + likesMap);
        Log.d(TAG, "updateDBPreferences dislikesMap: " + dislikesMap);
        tempRef.setValue(data).addOnCompleteListener(t -> {
            if (t.isSuccessful()) {
                databaseReference.removeValue().addOnCompleteListener(a -> {
                    if (a.isSuccessful()) {
                        databaseReference.setValue(data).addOnCompleteListener( s -> {
                            Log.d(TAG, "updateDBPreferences executed perfectly. All data has been uploaded without issue");
                        });
                    } else {
                        Log.d(TAG, "updateDBPreferences failed to remove user preferences data");
                    }
                });
            } else {
                Log.d(TAG, "updateDBPreferences failed to update tempRef");
            }
        });
    }

    public void resetPreferences() {
        likesMap.clear();
        dislikesMap.clear();
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s: %s\n%s: %s\n\"%s: %s\n%s: %s\n", FULL_NAME, getFullName(), USERNAME, getUsername(), EMAIL_ADDRESS, getEmailAddress(), PHONE_NUMBER, getPhoneNumber());
    }
}