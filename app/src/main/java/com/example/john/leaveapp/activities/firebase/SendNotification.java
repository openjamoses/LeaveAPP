package com.example.john.leaveapp.activities.firebase;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.john.leaveapp.activities.firebase.Config.URL_MULTIPLE_USERS;
import static com.example.john.leaveapp.activities.firebase.Config.URL_SINGLE_USER;
import static com.example.john.leaveapp.utils.Constants.config.HOST_URL;

/**
 * Created by john on 8/31/17.
 */

public class SendNotification {
    Context context;
    private final static String TAG = "SendNotification";
    public SendNotification(Context context){
        this.context = context;
    }
    public void sendSinglePush(final String title, final String message, final String image, final int staff_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SINGLE_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                        //Log.e(TAG,response+"\t"+phone);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG,error+"");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("message", message);
                //if(!TextUtils.isEmpty(image))
                params.put("image", image);
                params.put("staff_id", String.valueOf(staff_id));
                return params;
            }
        };

        MyVolley.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void sendMultiplePush(final String title, final String message, final String image, final String users) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_MULTIPLE_USERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        Log.e(TAG,response);
                       // Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG,error+"");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("message", message);
                params.put("users", users);

                if (!TextUtils.isEmpty(image))
                    params.put("image", image);
                return params;
            }
        };
        MyVolley.getInstance(context).addToRequestQueue(stringRequest);
    }


}
