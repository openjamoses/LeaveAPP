package com.example.john.leaveapp.core;

/**
 * Created by john on 7/8/17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.john.leaveapp.utils.Constants;

import java.util.HashMap;

import static com.example.john.leaveapp.utils.Constants.config.STAFFL_FNAME;
import static com.example.john.leaveapp.utils.Constants.config.STAFFL_LNAME;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_DOB;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_GENDER;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ID;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_PASSWORD;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_USERNAME;


public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "HBB_USER_Pref";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_SESSION_TYPE = "user";

    // User name (make variable public to access from outside)
    public static final String KEY_IMEI = Constants.config.IMEI;
    public static final String KEY_VERSION = Constants.config.APP_VERSION;

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    /**
     * Create login session
     * */
    public void createLoginSession(int userID, String fname, String lname, String contact, String district,
                                   String gender, String dob, String phone_id, String reg_date, String status){

        String session = "user";
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing users Details  in pref

        // commit changes
        editor.commit();
    }
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            //Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            //_context.startActivity(i);
        }
    }
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_SESSION_TYPE, pref.getString(KEY_SESSION_TYPE, null));

        // return user
        return user;
    }



    public HashMap<String, String> getCurrentDoctor(){
        HashMap<String, String> calls = new HashMap<String, String>();
        //calls.put(KEY_CURRENT_DOCTOR, pref.getString(KEY_CURRENT_DOCTOR, null));
        return calls;
    }

    public HashMap<String, String> getCurrentPassword(){
        HashMap<String, String> calls = new HashMap<String, String>();
        //calls.put(KEY_CURRENT_PASSWORD, pref.getString(KEY_CURRENT_PASSWORD, null));
        return calls;
    }

    public HashMap<String, String> getDoctorDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_SESSION_TYPE, pref.getString(KEY_SESSION_TYPE, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void loginSession(int useID, String fname, String lname, String username, String password,String dob, String gender) {
        // Storing login value as TRUE
        if(isLoggedIn()){
            logoutUser();
        }
        editor.putString(STAFFL_FNAME, fname);
        editor.putString(STAFFL_LNAME, lname);
        editor.putString(STAFF_USERNAME, username);
        editor.putString(STAFF_PASSWORD, password);
        editor.putString(STAFF_ID, String.valueOf(useID));
        editor.putString(STAFF_DOB, dob);
        editor.putString(STAFF_GENDER, gender);
        editor.putBoolean(IS_LOGIN, true);
         // commit changes
        editor.commit();
    }
}
