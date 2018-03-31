package com.example.kanchan.aaryaa_app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kanchan on 26/3/18.
 */

public class SharedStateDistrictDetails {

    SharedPreferences Detailspref;

    // Editor for Shared preferences
    SharedPreferences.Editor Detailseditor;

    // Context
   public Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "aaryaa";

    // All Shared Preferences Keys
    private static final String IS_Filled = "IsFilled";

    // User name (make variable public to acceass from outside)
    public static final String KEY_State = "state";
    public static final String KEY_District = "district";
    public static final String KEY_Center = "center";

    public SharedStateDistrictDetails(Context context){
        this._context = context;
        Detailspref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        Detailseditor = Detailspref.edit();
    }

    public void createSession(String state,String district,String center){
        // Storing login value as TRUE
        Detailseditor.putBoolean(IS_Filled, true);
        // Storing name in pref

        Detailseditor.putString(KEY_State,state);

        Detailseditor.putString(KEY_District,district);

        Detailseditor.putString(KEY_Center,center);
        // commit changes
        Detailseditor.commit();
    }


    public boolean isFiled(){

        return Detailspref.getBoolean(IS_Filled, false);
    }

    public String getState(){

        Detailspref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return Detailspref.getString(KEY_State,"");
    }

    public String getDistrict(){

        Detailspref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return Detailspref.getString(KEY_District,"");
    }

    public String getCenter(){

        Detailspref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return Detailspref.getString(KEY_Center,"");
    }
}
