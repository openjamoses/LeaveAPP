package com.example.john.leaveapp.db_operartions;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.john.leaveapp.core.BaseApplication;
import com.example.john.leaveapp.core.DBHelper;
import com.example.john.leaveapp.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import static com.example.john.leaveapp.utils.Constants.config.ENTITLEMENT;
import static com.example.john.leaveapp.utils.Constants.config.HOST_URL;
import static com.example.john.leaveapp.utils.Constants.config.LEAVETYPE_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVETYPE_NAME;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ID;
import static com.example.john.leaveapp.utils.Constants.config.UNIVERSITY_ID;
import static com.example.john.leaveapp.utils.Constants.config.UNIVERSITY_LOGO;
import static com.example.john.leaveapp.utils.Constants.config.UNIVERSITY_NAME;
import static com.example.john.leaveapp.utils.Constants.config.UNIVERSITY_STATUS;
import static com.example.john.leaveapp.utils.Constants.config.URL_SAVE_LEAVETYPE;
import static com.example.john.leaveapp.utils.Constants.config.URL_SAVE_UNIVERSITY;

/**
 * Created by john on 10/18/17.
 */

public class University {
    Context context;
    private static final String TAG = "University";
    public University(Context context){
        this.context = context;
    }

    public String save(int id,String name, int status) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(UNIVERSITY_ID,id);
            contentValues.put(UNIVERSITY_NAME,name);
            contentValues.put(UNIVERSITY_STATUS,status);
            database.insert(Constants.config.TABLE_UNIVERSITY, null, contentValues);
            //database.setTransactionSuccessful();
            message = "University Details saved!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }

    public String edit(String name, int id) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(UNIVERSITY_NAME,name);
            //contentValues.put(UNIVERSITY_LOGO,logo);
            database.update(Constants.config.TABLE_UNIVERSITY, contentValues, UNIVERSITY_ID+"="+id, null);
            //database.setTransactionSuccessful();
            message = "University Details updated!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }

    public void send(final String names , final ProgressDialog progressDialog){
        BaseApplication.deleteCache(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_UNIVERSITY,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, "Results: " + response);

                            String[] splits = response.split("/");
                            int status = 0, id = selectLastID();

                            if (splits[0].equals("Success")) {
                                status = 1;
                                id = Integer.parseInt(splits[1]);
                            }
                            String message = save(id,names,status);
                            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        if (progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        Log.e(TAG,response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        try {
                            Log.e(TAG, ""+volleyError.getMessage());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                int status = 1;
                Map<String, String> params = new Hashtable<String, String>();

                params.put(UNIVERSITY_NAME,names);
                //params.put(ENTITLEMENT,entitlement);

                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }



    ///// TODO: 10/13/17  select here!
    public  Cursor selectAll(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_UNIVERSITY+"  ORDER BY "+Constants.config.UNIVERSITY_NAME+" ASC ";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }

    public int selectLastID(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        int id = 0;
        try{
            db.beginTransaction();
            String query = "SELECT "+Constants.config.UNIVERSITY_ID+"  FROM" +
                    " "+ Constants.config.TABLE_UNIVERSITY+"  ORDER BY "+Constants.config.UNIVERSITY_ID+" DESC LIMIT 1 ";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();

            if (cursor.moveToFirst()){
                do {
                    id = cursor.getInt(cursor.getColumnIndex(Constants.config.UNIVERSITY_ID));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  id;
    }

    public Cursor select(int id){
        SQLiteDatabase db = new DBHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_UNIVERSITY+" WHERE "+UNIVERSITY_ID+" = '"+id+"' " +
                    " ORDER BY "+Constants.config.UNIVERSITY_NAME+" ASC ";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }


    ///// TODO: 10/23/17

    public void insert(JSONArray jsonArray){
        new InsertBackground(context).execute(jsonArray);
    }
    public class InsertBackground extends AsyncTask<JSONArray,Void,String> {
        Context context;
        InsertBackground(Context context){
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // startTime = System.nanoTime();
            // progressDialog.setTitle("Now Saving ! ...");
        }
        @Override
        protected String doInBackground(JSONArray... jsonArrays) {
            String message = null;
            int status = 1;
            SQLiteDatabase db = DBHelper.getHelper(context).getWritableDB();
            try{
                db.beginTransaction();
                //String get_json = get
                //JSONArray jsonArray = new JSONArray(results);
                JSONArray jsonArray = jsonArrays[0];
                db.execSQL("DELETE FROM " + Constants.config.TABLE_UNIVERSITY+" ");
                int total = 0;

                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(UNIVERSITY_ID,jsonObject.getString(Constants.config.UNIVERSITY_ID));
                    contentValues.put(UNIVERSITY_NAME,jsonObject.getString(Constants.config.UNIVERSITY_NAME));
                    //contentValues.put(ENTITLEMENT,jsonObject.getString(Constants.config.ENTITLEMENT));
                    // contentValues.put(LEAVETYPE_STATUS,jsonObject.getString(Constants.config.LEAVETYPE_STATUS));
                    db = DBHelper.getHelper(context).getWritableDB();

                    db.insert(Constants.config.TABLE_UNIVERSITY, null, contentValues);
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records ,"+Constants.config.TABLE_UNIVERSITY+" Updated successfully!";

            }catch (Exception e){
                e.printStackTrace();
                message = "Error: "+e;
                Log.e("Error: ",e.toString());
            }finally {
                db.endTransaction();
            }
            return  message;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Fetch results",s);

        }
    }

}
