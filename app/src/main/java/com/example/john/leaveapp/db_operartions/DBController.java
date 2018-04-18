package com.example.john.leaveapp.db_operartions;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.john.leaveapp.R;
import com.example.john.leaveapp.core.BaseApplication;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

import static com.example.john.leaveapp.utils.Constants.config.HOST_URL;
import static com.example.john.leaveapp.utils.Constants.config.OPERATION_APPLY;
import static com.example.john.leaveapp.utils.Constants.config.OPERATION_DEPARTMENT;
import static com.example.john.leaveapp.utils.Constants.config.OPERATION_FACULTY;
import static com.example.john.leaveapp.utils.Constants.config.OPERATION_LEAVE;
import static com.example.john.leaveapp.utils.Constants.config.OPERATION_LEAVETYPE;
import static com.example.john.leaveapp.utils.Constants.config.OPERATION_NOTIFICATION;
import static com.example.john.leaveapp.utils.Constants.config.OPERATION_SECRETARY;
import static com.example.john.leaveapp.utils.Constants.config.OPERATION_STAFF;
import static com.example.john.leaveapp.utils.Constants.config.OPERATION_UNIVERSITY;
import static com.example.john.leaveapp.utils.Constants.config.SQL_QUERY;

/**
 * Created by john on 10/14/17.
 */
public class DBController {
    private static final String TAG = "DBController";
    Handler mainHandler = new Handler(Looper.getMainLooper());

    public void syncCalls(final String url, final String operations, String show, final Context context){
        Log.e(TAG,"******************************** "+url);
        Log.e(TAG,"Syncing started for: "+operations);
        new Task(context,operations,url).execute();
    }
    private class Task extends AsyncTask<String, Void,String>{

        String operations;
        String url;
        Context context;
        public Task(Context context,String operations, String url){
            this.context = context;
            this.operations = operations;
            this.url = url;
        }
        @Override
        protected String doInBackground(String... strings) {

            try {

                BaseApplication.deleteCache(context);
                //final ProgressDialog prgDialog = new ProgressDialog(context);
                //prgDialog.setMessage("Synching SQLite Data with Remote Server. \n Please wait...");
                //prgDialog.setCancelable(false);
                //Create AsycHttpClient object

                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {
                        //Code that uses AsyncHttpClient in your case ConsultaCaract()

                        AsyncHttpClient client = new AsyncHttpClient();
                        RequestParams params = new RequestParams();
                        int db_count = 0;
                        ArrayList<HashMap<String, String>> userList = new ArrayList<HashMap<String, String>>();
                        String json_data = "";
                        if (operations.equals(OPERATION_STAFF)) {
                            userList = new Staff(context).getAllUsers();
                            db_count = new Staff(context).dbSyncCount();
                            json_data = new Staff(context).composeJSONfromSQLite();
                        } else if (operations.equals(OPERATION_LEAVE)) {
                            userList = new Leave(context).getAllUsers();
                            db_count = new Leave(context).dbSyncCount();
                            json_data = new Leave(context).composeJSONfromSQLite();
                        }else if (operations.equals(OPERATION_APPLY)) {
                            userList = new Apply(context).getAllUsers();
                            db_count = new Apply(context).dbSyncCount();
                            json_data = new Apply(context).composeJSONfromSQLite();
                        }
                        if (userList.size() != 0) {
                            if (db_count != 0) {

                                params.put("dataJSON", json_data);
                                client.post(HOST_URL + url, params, new AsyncHttpResponseHandler() {

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                                        try {
                                            String response = new String(responseBody, "UTF-8");
                                            //prgDialog.hide();
                                            try {
                                                Log.e(TAG, response);
                                                JSONArray arr = new JSONArray(response);
                                                System.out.println(arr.length());
                                                for (int i = 0; i < arr.length(); i++) {
                                                    JSONObject obj = (JSONObject) arr.get(i);
                                                    System.out.println(obj.get("id"));

                                                    if (operations.equals(OPERATION_STAFF)) {
                                                        new Staff(context).updateSyncStatus(Integer.parseInt(obj.get("id").toString()),Integer.parseInt(obj.get("id2").toString()), Integer.parseInt(obj.get("status").toString()));
                                                    } else if (operations.equals(OPERATION_LEAVE)) {
                                                        new Leave(context).updateSyncStatus(Integer.parseInt(obj.get("id").toString()),Integer.parseInt(obj.get("id2").toString()), Integer.parseInt(obj.get("status").toString()));
                                                    }else if (operations.equals(OPERATION_APPLY)) {
                                                        new Apply(context).updateSyncStatus(Integer.parseInt(obj.get("id").toString()),Integer.parseInt(obj.get("id2").toString()), Integer.parseInt(obj.get("status").toString()));
                                                    }
                                                }
                                                //Toast.makeText(getApplicationContext(), "DB Sync completed!", Toast.LENGTH_LONG).show();
                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                //Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                                                e.printStackTrace();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        if (statusCode == 404) {
                                            Log.e("Error ", "Error code " + statusCode + " \t " + url);
                                            //Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                                        } else if (statusCode == 500) {
                                            Log.e("Error ", "Error code " + statusCode + " \t" + url);
                                            //Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                                        } else {
                                            Log.e("Error ", "Error code " + statusCode + " \t " + url);
                                        }
                                    }
                                });
                            } else {
                                Log.e("No Sync data", "Empty data to be sync " + url);
                            }
                        } else {
                            Log.e("Empty", "Empty data to be sync " + url);
                        }
                    }
                };
                mainHandler.post(myRunnable);



            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    public static void fetchJSON(final Context context, final String sql_query, String url, final String operation){
        BaseApplication.deleteCache(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG,response);
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            if(operation.equals(OPERATION_UNIVERSITY)){
                                new University(context).insert(jsonArray);
                            }else if (operation.equals(OPERATION_STAFF)){
                                new Staff(context).insert(jsonArray);
                            }if (operation.equals(OPERATION_LEAVE)){
                                new Leave(context).insert(jsonArray);
                            } if (operation.equals(OPERATION_APPLY)){
                                new Apply(context).insert(jsonArray);
                            }if (operation.equals(OPERATION_SECRETARY)){
                                new Secretary(context).insert(jsonArray);
                            }
                            if (operation.equals(OPERATION_LEAVETYPE)){
                                new LeaveType(context).insert(jsonArray);
                            }
                            if (operation.equals(OPERATION_FACULTY)){
                                //new LeaveType(context).insert(jsonArray);
                            }
                            if (operation.equals(OPERATION_DEPARTMENT)){
                                new Departments(context).insert(jsonArray);
                            }
                            if (operation.equals(OPERATION_NOTIFICATION)){
                                new Notications(context).insert(jsonArray);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Log.e(TAG, volleyError.getMessage());
                        try {
                            Toast toast = Toast.makeText(context, "Connections ERROR!", Toast.LENGTH_LONG);
                            View view = toast.getView();
                            //view.setBackgroundResource(R.drawable.round_conor);
                            //TextView text = (TextView) view.findViewById(android.R.id.message);
                            //toast.show();
                            //Log.e(TAG, volleyError.getMessage());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put(SQL_QUERY, sql_query);
                //Adding parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


    public static void updateQuery(final Context context, final String sql_query, String url, final ProgressDialog  progressDialog){
        BaseApplication.deleteCache(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG,response);
                        try{
                            if (progressDialog != null) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Log.e(TAG, volleyError.getMessage());
                        try {
                            Toast toast = Toast.makeText(context, "Connections ERROR!", Toast.LENGTH_LONG);
                            View view = toast.getView();
                            //view.setBackgroundResource(R.drawable.round_conor);
                            //TextView text = (TextView) view.findViewById(android.R.id.message);
                            //toast.show();
                            //Log.e(TAG, volleyError.getMessage());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put(SQL_QUERY, sql_query);
                //Adding parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


}
