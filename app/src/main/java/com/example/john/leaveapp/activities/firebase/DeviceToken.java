package com.example.john.leaveapp.activities.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import static com.example.john.leaveapp.activities.firebase.Config.KEY_TOKEN;

/**
 * Created by john on 8/31/17.
 */

public class DeviceToken  {
    Context context;
    public DeviceToken(Context context){
        this.context = context;
    }
    public String token(){
        SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString(KEY_TOKEN, null);
        return regId;
    }

}
