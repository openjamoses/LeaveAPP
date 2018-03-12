package com.example.john.leaveapp.core;

/**
 * Created by john on 7/8/17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.john.leaveapp.utils.Constants;
import com.example.john.leaveapp.utils.DateTime;

import java.util.HashMap;

import static com.example.john.leaveapp.utils.Constants.config.LOGIN_DATE;
import static com.example.john.leaveapp.utils.Constants.config.LOGIN_TIME;
import static com.example.john.leaveapp.utils.Constants.config.STAFFL_FNAME;
import static com.example.john.leaveapp.utils.Constants.config.STAFFL_LNAME;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_GENDER;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ID;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_PASSWORD;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_PHONE;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ROLE;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_SALARY;
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

    public void loginSession(int useID, String fname, String lname, String username, String password,String phone, String gender, String role, String salary) {
        // Storing login value as TRUE
        if(isLoggedIn()){
            logoutUser();
        }
        editor.putString(STAFFL_FNAME, fname);
        editor.putString(STAFFL_LNAME, lname);
        editor.putString(STAFF_USERNAME, username);
        editor.putString(STAFF_PASSWORD, password);
        editor.putString(STAFF_ID, String.valueOf(useID));
        editor.putString(STAFF_PHONE, phone);
        editor.putString(STAFF_ROLE, role);
        editor.putString(STAFF_GENDER, gender);
        editor.putString(STAFF_SALARY, salary);
        editor.putString(LOGIN_DATE, DateTime.getCurrentDate());
        editor.putString(LOGIN_TIME, DateTime.getCurrentTime());
        editor.putBoolean(IS_LOGIN, true);
         // commit changes
        editor.commit();
    }

    public HashMap<String, String> getDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(STAFFL_FNAME, pref.getString(STAFFL_FNAME, null));
        user.put(STAFFL_LNAME, pref.getString(STAFFL_LNAME, null));
        user.put(STAFF_USERNAME, pref.getString(STAFF_USERNAME, null));
        user.put(STAFF_PASSWORD, pref.getString(STAFF_PASSWORD, null));
        user.put(STAFF_GENDER, pref.getString(STAFF_GENDER, null));
        user.put(STAFF_SALARY, pref.getString(STAFF_SALARY, null));
        user.put(STAFF_PHONE, pref.getString(STAFF_PHONE, null));
        user.put(STAFF_ROLE, pref.getString(STAFF_ROLE, null));
        user.put(STAFF_ID, pref.getString(STAFF_ID, null));
        user.put(LOGIN_DATE, pref.getString(LOGIN_DATE, null));
        user.put(LOGIN_TIME, pref.getString(LOGIN_TIME, null));
        // return user
        return user;
    }
}
