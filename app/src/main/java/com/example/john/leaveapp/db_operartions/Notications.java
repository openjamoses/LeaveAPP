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
import com.example.john.leaveapp.utils.Phone;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static com.example.john.leaveapp.utils.Constants.config.APPLY_ID;
import static com.example.john.leaveapp.utils.Constants.config.ENTITLEMENT;
import static com.example.john.leaveapp.utils.Constants.config.HOST_URL;
import static com.example.john.leaveapp.utils.Constants.config.LEAVETYPE_NAME;
import static com.example.john.leaveapp.utils.Constants.config.NOTICATIONID;
import static com.example.john.leaveapp.utils.Constants.config.NOTICATION_BODY;
import static com.example.john.leaveapp.utils.Constants.config.NOTICATION_ID;
import static com.example.john.leaveapp.utils.Constants.config.NOTIFICATION_DATE;
import static com.example.john.leaveapp.utils.Constants.config.NOTIFICATION_STATUS;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ID;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_NOTICATION;
import static com.example.john.leaveapp.utils.Constants.config.URL_SAVE_LEAVETYPE;
import static com.example.john.leaveapp.utils.Constants.config.URL_SAVE_NOTIFICATION;

/**
 * Created by john on 4/7/18.
 */

public class Notications {
    Context context;
    private static final String TAG = "Leave";
    public Notications(Context context){
        this.context = context;
    }

    public String save(int notification_id,int staff_id, int apply_id,String notification_body, String notification_date, int notification_status) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;
        try{


            ContentValues contentValues = new ContentValues();
            contentValues.put(NOTICATION_ID,notification_id);
            contentValues.put(NOTICATION_BODY,notification_body);
            contentValues.put(NOTIFICATION_DATE,notification_date);
            contentValues.put(STAFF_ID,staff_id);
            contentValues.put(APPLY_ID,apply_id);
            contentValues.put(NOTIFICATION_STATUS,notification_status);

            database.insert(Constants.config.TABLE_NOTICATION, null, contentValues);
            //database.setTransactionSuccessful();
            message = "Leave Details saved!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }

    public String edit(int staff_id,int notification_status) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(NOTIFICATION_STATUS,notification_status);

            database.update(Constants.config.TABLE_NOTICATION,contentValues,STAFF_ID+" = "+staff_id, null);
            //database.setTransactionSuccessful();
            message = "Leave Details updated!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }


    public void send(final int staff_id, final int apply_id, final String notification_body, final String notification_date){
        BaseApplication.deleteCache(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_NOTIFICATION,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, "Results: " + response);

                            String[] splits = response.split("/");
                            int status = 0, id = lastID();

                            if (splits[0].equals("Success")) {
                                status = 1;
                                id = Integer.parseInt(splits[1]);
                            }
                            String message = save(id,staff_id,apply_id,notification_body,notification_date,status);
                            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            e.printStackTrace();
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

               // params.put(NOTICATION_ID,jsonObject.getLong(Constants.config.NOTICATION_ID));
                params.put(NOTICATION_BODY,notification_body);
                params.put(NOTIFICATION_DATE,notification_date);
                params.put(STAFF_ID, String.valueOf(staff_id));
                params.put(APPLY_ID, String.valueOf(apply_id));
               // params.put(NOTIFICATION_STATUS,jsonObject.getLong(Constants.config.NOTIFICATION_STATUS));
                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }



    public int lastID(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        int id = 0;
        try{
            db.beginTransaction();
            String query = "SELECT "+NOTICATION_ID+" FROM" +
                    " "+ Constants.config.TABLE_NOTICATION+" ORDER BY "+NOTICATION_ID+" DESC LIMIT 1";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndex(Constants.config.NOTICATION_ID));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  id;
    }

    public Cursor getAll(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT * FROM" +
                    " "+ Constants.config.TABLE_NOTICATION+" ORDER BY "+NOTIFICATION_DATE+" ASC";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }




    //// TODO: 10/15/17  Syncing
    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_NOTICATION ;

        int status = 1;
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(NOTICATIONID))));

                params.put(NOTICATION_BODY,cursor.getString(cursor.getColumnIndex(NOTICATION_BODY)));
                params.put(NOTIFICATION_DATE,cursor.getString(cursor.getColumnIndex(NOTIFICATION_DATE)));
                params.put(APPLY_ID,cursor.getString(cursor.getColumnIndex(APPLY_ID)));
                params.put(STAFF_ID,cursor.getString(cursor.getColumnIndex(STAFF_ID)));

                wordList.add(params);
            } while (cursor.moveToNext());
        }
        //database.close();
        return wordList;
    }
    public String composeJSONfromSQLite(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        int status = 0;
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_NOTICATION + " WHERE " + NOTIFICATION_STATUS + " = '" + status + "' ";
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(NOTICATIONID))));

                params.put(NOTICATION_BODY,cursor.getString(cursor.getColumnIndex(NOTICATION_BODY)));
                params.put(NOTIFICATION_DATE,cursor.getString(cursor.getColumnIndex(NOTIFICATION_DATE)));
                params.put(APPLY_ID,cursor.getString(cursor.getColumnIndex(APPLY_ID)));
                params.put(STAFF_ID,cursor.getString(cursor.getColumnIndex(STAFF_ID)));

                wordList.add(params);
            } while (cursor.moveToNext());
        }
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }
    /**
     * Get Sync status of SQLite
     * @return
     */
    public String getSyncStatus(){
        String msg = null;
        if(this.dbSyncCount() == 0){
            msg = "SQLite and Remote MySQL DBs are in Sync!";
        }else{
            msg = "DB Sync neededn";
        }
        return msg;
    }
    /**
     * Get SQLite records that are yet to be Synced
     * @return
     */
    public int dbSyncCount(){
        int count = 0;
        SQLiteDatabase database = null;
        try {
            int status = 0;
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_NOTICATION+ " WHERE " + NOTIFICATION_STATUS + " = '" + status + "' ";
            database = DBHelper.getHelper(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(selectQuery, null);
            count = cursor.getCount();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                database.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return count;
    }
    /***
     *
     * @param id
     * @param status
     */
    public void updateSyncStatus(int id, int log_id, int status){
        SQLiteDatabase database = null;
        try {
            database = DBHelper.getHelper(context).getWritableDatabase();
            String updateQuery = "UPDATE " + Constants.config.TABLE_NOTICATION + " SET " + NOTIFICATION_STATUS + " = '" + status + "', "+NOTICATION_ID+" = '"+log_id+"' where " + NOTICATION_ID + "='" + id + "'  ";
            Log.d("query", updateQuery);
            database.execSQL(updateQuery);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                database.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
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
                JSONArray jsonArray = jsonArrays[0];
                db.execSQL("DELETE FROM " + Constants.config.TABLE_NOTICATION+" ");

                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(NOTICATION_ID,jsonObject.getLong(Constants.config.NOTICATION_ID));
                    contentValues.put(NOTICATION_BODY,jsonObject.getString(Constants.config.NOTICATION_BODY));
                    contentValues.put(NOTIFICATION_DATE,jsonObject.getString(Constants.config.NOTIFICATION_DATE));
                    contentValues.put(STAFF_ID,jsonObject.getLong(Constants.config.STAFF_ID));
                    contentValues.put(APPLY_ID,jsonObject.getLong(Constants.config.APPLY_ID));
                    contentValues.put(NOTIFICATION_STATUS,jsonObject.getLong(Constants.config.NOTIFICATION_STATUS));

                    db = DBHelper.getHelper(context).getWritableDB();
                    db.insert(Constants.config.TABLE_NOTICATION, null, contentValues);
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records ,"+TABLE_NOTICATION+" Updated successfully!";

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
