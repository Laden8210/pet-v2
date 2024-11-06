package com.example.epet.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.epet.Model.User;
import com.google.gson.Gson;

public class SessionManager {

    private static SessionManager instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String SHARED_PREF_NAME = "user_session";
    private static final String KEY_USER = "user";

    private SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    public void createSession(User user) {
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        editor.putString(KEY_USER, userJson);
        editor.commit();
    }

    public User getSession() {
        String userJson = sharedPreferences.getString(KEY_USER, null);
        if (userJson == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(userJson, User.class);
    }

    public void clearSession() {
        editor.remove(KEY_USER);
        editor.commit();
    }

    public boolean hasSession() {
        return sharedPreferences.contains(KEY_USER);
    }
}
