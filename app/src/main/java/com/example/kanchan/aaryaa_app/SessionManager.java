package com.example.kanchan.aaryaa_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by gdevop on 25/3/18.
 */

public class SessionManager {

    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Aaryaa";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String name){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing name in pref
        editor.putString(KEY_NAME, name);
        // commit changes
        editor.commit();
    }




        public void logoutUser(){
            // Clearing all data from Shared Preferences
            editor.clear();
            editor.putBoolean(IS_LOGIN,false);
            editor.commit();
             // After logout redirect user to Loing Activity
            }

    public boolean isLoggedIn(){

        return pref.getBoolean(IS_LOGIN, false);
    }


}
