package com.example.christofferpiilmann.galgeleg;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by emilyrasmussen on 03/05/2018.
 */

public class loginhelper {
    public static Boolean isUserLoggedIn(Context context) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getBoolean("isUserLoggedIn", false);
}

    public static void setUserLoggedIn(Context context, Boolean isLoggedIn)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isUserLoggedIn", isLoggedIn);
        editor.commit();
    }

    public static void logout(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isUserLoggedIn", false);
        editor.commit();
    }

    public static void saveUsernameAndPassword(Context context, String username, String password)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }
}

