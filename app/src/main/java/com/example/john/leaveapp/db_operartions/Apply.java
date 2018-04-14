package com.example.john.leaveapp.db_operartions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import static com.example.john.leaveapp.utils.Constants.config.APPLYID;
import static com.example.john.leaveapp.utils.Constants.config.APPLY_ID;
import static com.example.john.leaveapp.utils.Constants.config.APPLY_STATUS;
import static com.example.john.leaveapp.utils.Constants.config.DATE;
import static com.example.john.leaveapp.utils.Constants.config.END_DATE;
import static com.example.john.leaveapp.utils.Constants.config.HOST_URL;
import static com.example.john.leaveapp.utils.Constants.config.LEAVEID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVETYPE_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_STATUS;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_STATUS1;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_STATUS2;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ID;
import static com.example.john.leaveapp.utils.Constants.config.START_DATE;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_APPLY;
import static com.example.john.leaveapp.utils.Constants.config.TIME;
import static com.example.john.leaveapp.utils.Constants.config.URL_SAVE_APPLY;
import static com.example.john.leaveapp.utils.Constants.config.URL_SAVE_LEAVE;;

/**
 * Created by john on 3/10/18.
 */

public class Apply {

    private Context context;
    private static final String TAG = "Leave";
    public Apply(Context context){
        this.context = context;
    }
    public String save(int apply_id,int leave_id, int less, int status1, int status2, String date, String time , int staff_id, int status, String start_date, String end_date ) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(APPLY_ID,apply_id);
            contentValues.put(LEAVE_ID,leave_id);
            contentValues.put(LEAVE_STATUS,less);
            contentValues.put(LEAVE_STATUS1,status1);
            contentValues.put(LEAVE_STATUS2,status2);
            contentValues.put(DATE,date);
            contentValues.put(TIME,time);
            contentValues.put(START_DATE,start_date);
            contentValues.put(END_DATE,end_date);
            contentValues.put(STAFF_ID,staff_id);
            contentValues.put(APPLY_STATUS,status);
            database.insert(Constants.config.TABLE_APPLY, null, contentValues);
            //database.setTransactionSuccessful();
            message = "Apply Details saved!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }

    public String edit(int leave_id, int type_id,int less, int status1, int status2, String date, String time , int staff_id, int id) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();
            //contentValues.put(LEAVE_ID,leave_id);
            contentValues.put(LEAVE_ID,type_id);
            contentValues.put(LEAVE_STATUS,less);
            contentValues.put(LEAVE_STATUS1,status1);
            contentValues.put(LEAVE_STATUS2,status2);
            contentValues.put(DATE,date);
            contentValues.put(TIME,time);
            contentValues.put(STAFF_ID,staff_id);
            contentValues.put(APPLY_STATUS,status);
            database.update(Constants.config.TABLE_APPLY,contentValues, APPLYID+"="+id, null);
            //database.setTransactionSuccessful();
            message = "Apply details updated!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }

    public Cursor getAnual(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        int type = 1;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_LEAVE+" n, "+Constants.config.TABLE_STAFF+" s WHERE " +
                    " a."+Constants.config.LEAVE_ID+" = n."+Constants.config.LEAVE_ID+"   " +
                    " AND s."+Constants.config.STAFF_ID+" = a."+Constants.config.STAFF_ID+" ORDER BY a."+Constants.config.DATE+" DESC";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }

    public Cursor getSingle(int staff_id){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        int type = 1;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_LEAVE+" n, "+Constants.config.TABLE_STAFF+" s,  WHERE " +
                    " a."+Constants.config.LEAVE_ID+" = n."+Constants.config.LEAVE_ID+"  " +
                    " AND s."+Constants.config.STAFF_ID+" = a."+Constants.config.STAFF_ID+" AND a."+Constants.config.STAFF_ID+" = '"+staff_id+"' ORDER BY a."+Constants.config.DATE+" DESC";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }


    public Cursor get(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_SECRETARY+" d, ORDER BY "+Constants.config.SECRETARY_ID+" DESC LIMIT 1";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }
    public Cursor getAnualbyID(int leave_id) {
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        int type = 1;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_LEAVE+" n, "+Constants.config.TABLE_STAFF+" s WHERE " +
                    " a."+Constants.config.LEAVE_ID+" = n."+Constants.config.LEAVE_ID+" AND a."+Constants.config.LEAVETYPE_ID+" = '"+type+"'" +
                    " AND s."+Constants.config.STAFF_ID+" = a."+Constants.config.STAFF_ID+" AND n."+LEAVE_ID+" = '"+leave_id+"' ORDER BY a."+Constants.config.DATE+" DESC";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }

    public void send(final int leave_id, final int leave_status, final int status1, final int status2, final String date, final String time , final int staff_id, final String start_date, final String end_date){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_APPLY,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, "Results: " + response);

                            String[] splits = response.split("/");
                            int status = 0, id = 0;

                            if (splits[0].equals("Success")) {
                                status = 1;
                                id = Integer.parseInt(splits[1]);

                            }
                            String message = save(id,leave_id,leave_status,status1,status2,date,time,staff_id,status, start_date, end_date);
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

                //params.put(LEAVE_ID, String.valueOf(leave_id));
                params.put(LEAVE_ID, String.valueOf(leave_id));
                params.put(LEAVE_STATUS, String.valueOf(leave_status));
                params.put(LEAVE_STATUS1, String.valueOf(status1));
                params.put(LEAVE_STATUS2, String.valueOf(status2));
                params.put(START_DATE,start_date);
                params.put(END_DATE,end_date);
                params.put(DATE,date);
                params.put(TIME,time);
                params.put(STAFF_ID, String.valueOf(staff_id));

                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


    //// TODO: 10/15/17  Syncing
    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_APPLY ;

        int status = 1;
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(APPLYID))));

               // params.put(LEAVE_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(LEAVE_ID))));
                params.put(LEAVE_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(LEAVE_ID))));
                params.put(LEAVE_STATUS, String.valueOf(cursor.getInt(cursor.getColumnIndex(LEAVE_STATUS))));
                params.put(LEAVE_STATUS1, cursor.getString(cursor.getColumnIndex(LEAVE_STATUS1)));
                params.put(LEAVE_STATUS2, cursor.getString(cursor.getColumnIndex(LEAVE_STATUS2)));
                params.put(START_DATE,cursor.getString(cursor.getColumnIndex(START_DATE)));
                params.put(END_DATE,cursor.getString(cursor.getColumnIndex(END_DATE)));
                params.put(DATE,cursor.getString(cursor.getColumnIndex(DATE)));
                params.put(TIME,cursor.getString(cursor.getColumnIndex(TIME)));
                params.put(STAFF_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(STAFF_ID))));

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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_APPLY + " WHERE " + APPLY_STATUS + " = '" + status + "' ";
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(APPLYID))));

               // params.put(LEAVE_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(LEAVE_ID))));
                params.put(LEAVE_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(LEAVE_ID))));
                params.put(LEAVE_STATUS, String.valueOf(cursor.getInt(cursor.getColumnIndex(LEAVE_STATUS))));
                params.put(LEAVE_STATUS1, cursor.getString(cursor.getColumnIndex(LEAVE_STATUS1)));
                params.put(LEAVE_STATUS2, cursor.getString(cursor.getColumnIndex(LEAVE_STATUS2)));
                params.put(DATE,cursor.getString(cursor.getColumnIndex(DATE)));
                params.put(TIME,cursor.getString(cursor.getColumnIndex(TIME)));

                params.put(START_DATE,cursor.getString(cursor.getColumnIndex(START_DATE)));
                params.put(END_DATE,cursor.getString(cursor.getColumnIndex(END_DATE)));
                params.put(STAFF_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(STAFF_ID))));

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
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_APPLY+ " WHERE " + APPLY_STATUS + " = '" + status + "' ";
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
            String updateQuery = "UPDATE " + Constants.config.TABLE_APPLY + " SET " + APPLY_STATUS + " = '" + status + "', "+APPLYID+" = '"+log_id+"' where " + LEAVEID + "='" + id + "'  ";
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
                //String get_json = get
                //JSONArray jsonArray = new JSONArray(results);
                JSONArray jsonArray = jsonArrays[0];
                db.execSQL("DELETE FROM " + Constants.config.TABLE_APPLY+" WHERE "+APPLY_STATUS+" = '"+status+"' ");

                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(APPLY_ID,jsonObject.getLong(Constants.config.APPLY_ID));
                   // contentValues.put(LEAVE_ID,jsonObject.getLong(Constants.config.LEAVE_ID));
                    contentValues.put(LEAVE_ID,jsonObject.getLong(Constants.config.LEAVE_ID));
                    contentValues.put(LEAVE_STATUS,jsonObject.getLong(Constants.config.LEAVE_STATUS));
                    contentValues.put(LEAVE_STATUS1,jsonObject.getLong(Constants.config.LEAVE_STATUS1));
                    contentValues.put(LEAVE_STATUS2,jsonObject.getLong(Constants.config.LEAVE_STATUS2));
                    contentValues.put(DATE,jsonObject.getString(Constants.config.DATE));
                    contentValues.put(TIME,jsonObject.getString(Constants.config.TIME));
                    contentValues.put(START_DATE,jsonObject.getString(Constants.config.START_DATE));
                    contentValues.put(END_DATE,jsonObject.getString(Constants.config.END_DATE));
                    contentValues.put(STAFF_ID,jsonObject.getLong(Constants.config.STAFF_ID));
                    contentValues.put(APPLY_STATUS,status);

                    db = DBHelper.getHelper(context).getWritableDB();
                    db.insert(Constants.config.TABLE_APPLY, null, contentValues);
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records ,"+TABLE_APPLY+" Updated successfully!";

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
