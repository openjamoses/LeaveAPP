package com.example.john.leaveapp.db_operartions;

import android.app.Activity;
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
import com.example.john.leaveapp.core.DBHelper;
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.utils.Constants;
import com.example.john.leaveapp.utils.DateTime;
import com.example.john.leaveapp.utils.Phone;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import static com.example.john.leaveapp.utils.Constants.config.BALANCE_OUTSTANDING;
import static com.example.john.leaveapp.utils.Constants.config.BALANCE_TAKEN;
import static com.example.john.leaveapp.utils.Constants.config.DATE;
import static com.example.john.leaveapp.utils.Constants.config.DATE_ASSUMPTION;
import static com.example.john.leaveapp.utils.Constants.config.DATE_PROMOTION;
import static com.example.john.leaveapp.utils.Constants.config.DATE_RETURN;
import static com.example.john.leaveapp.utils.Constants.config.DAYS_TAKEN;
import static com.example.john.leaveapp.utils.Constants.config.ENTITLEMENT;
import static com.example.john.leaveapp.utils.Constants.config.HOST_URL;
import static com.example.john.leaveapp.utils.Constants.config.LEAVEDUE_FROM;
import static com.example.john.leaveapp.utils.Constants.config.LEAVEDUE_TO;
import static com.example.john.leaveapp.utils.Constants.config.LEAVEID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_FROM;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_NOW;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_STATUS;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_TO;
import static com.example.john.leaveapp.utils.Constants.config.SIGNATURE;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_LEAVE;
import static com.example.john.leaveapp.utils.Constants.config.URL_SAVE_LEAVE;

/**
 * Created by john on 3/10/18.
 */

public class Leave {
    Context context;
    private static final String TAG = "Leave";
    public Leave(Context context){
        this.context = context;
    }

    public String save(int id,String assumption, String promotion, String dreturn,String dbance,String dtaken, String entitlement,String lnow,String lfrom,String lto,
                       String outstanding, String ldfrom, String ldto,int signature, int status) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;
        try{
            //String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DATE_ASSUMPTION,assumption);
            contentValues.put(DATE_PROMOTION,promotion);
            contentValues.put(DATE_RETURN,dreturn);
            contentValues.put(DAYS_TAKEN,dtaken);
            contentValues.put(BALANCE_TAKEN,dbance);
            contentValues.put(ENTITLEMENT,entitlement);
            contentValues.put(LEAVE_NOW,lnow);
            contentValues.put(LEAVE_FROM,lfrom);
            contentValues.put(LEAVE_ID,id);
            contentValues.put(LEAVE_TO,lto);
            contentValues.put(BALANCE_OUTSTANDING,outstanding);
            contentValues.put(LEAVEDUE_FROM,ldfrom);
            contentValues.put(LEAVEDUE_TO,ldto);
            //contentValues.put(SIGNATURE,signature);
            contentValues.put(LEAVE_STATUS,status);
            database.insert(Constants.config.TABLE_LEAVE, null, contentValues);
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


    public String edit(String assumption, String promotion, int dreturn,int dbance,String dtaken, String entitlement,String lnow,String lfrom,String lto,
                       String outstanding, String ldfrom, String ldto,int signature, int id) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(DATE_ASSUMPTION,assumption);
            contentValues.put(DATE_PROMOTION,promotion);
            contentValues.put(DATE_RETURN,dreturn);
            contentValues.put(DAYS_TAKEN,dtaken);
            contentValues.put(BALANCE_TAKEN,dbance);
            contentValues.put(ENTITLEMENT,entitlement);
            contentValues.put(LEAVE_NOW,lnow);
            contentValues.put(LEAVE_FROM,lfrom);
            contentValues.put(LEAVE_TO,lto);
            contentValues.put(BALANCE_OUTSTANDING,outstanding);
            contentValues.put(LEAVEDUE_FROM,ldfrom);
            contentValues.put(LEAVEDUE_TO,ldto);
            //contentValues.put(SIGNATURE,signature);
            database.update(Constants.config.TABLE_LEAVE
                    ,contentValues,LEAVE_ID+"="+id, null);
            message = "Anual Leave details updated!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }
    public int lastID(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        int id = 0;
        try{
            db.beginTransaction();
            String query = "SELECT "+LEAVE_ID+" FROM" +
                    " "+ Constants.config.TABLE_LEAVE+" ORDER BY "+LEAVE_ID+" DESC LIMIT 1";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndex(Constants.config.LEAVE_ID));
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
                    " "+ Constants.config.TABLE_LEAVE+" n, "+Constants.config.TABLE_APPLY+" p, "+Constants.config.TABLE_STAFF+" s " +
                    " WHERE n"+Constants.config.LEAVE_ID+" = p."+Constants.config.LEAVE_ID+" AND p."+Constants.config.STAFF_ID+" = s."+Constants.config.STAFF_ID+" " +
                    " ORDER BY "+DATE+" ASC";
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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_LEAVE ;

        int status = 1;
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(LEAVEID))));

                params.put(DATE_ASSUMPTION,cursor.getString(cursor.getColumnIndex(DATE_ASSUMPTION)));
                params.put(DATE_PROMOTION,cursor.getString(cursor.getColumnIndex(DATE_PROMOTION)));
                params.put(DATE_RETURN,cursor.getString(cursor.getColumnIndex(DATE_RETURN)));
                params.put(DAYS_TAKEN,cursor.getString(cursor.getColumnIndex(DAYS_TAKEN)));
                params.put(BALANCE_TAKEN,cursor.getString(cursor.getColumnIndex(BALANCE_TAKEN)));
                params.put(ENTITLEMENT,cursor.getString(cursor.getColumnIndex(ENTITLEMENT)));
                params.put(LEAVE_NOW,cursor.getString(cursor.getColumnIndex(LEAVE_NOW)));
                params.put(LEAVE_FROM,cursor.getString(cursor.getColumnIndex(LEAVE_FROM)));
                params.put(LEAVE_TO,cursor.getString(cursor.getColumnIndex(LEAVE_TO)));
                params.put(BALANCE_OUTSTANDING,cursor.getString(cursor.getColumnIndex(BALANCE_OUTSTANDING)));
                params.put(LEAVEDUE_FROM,cursor.getString(cursor.getColumnIndex(LEAVEDUE_FROM)));
                params.put(LEAVEDUE_TO,cursor.getString(cursor.getColumnIndex(LEAVEDUE_TO)));
                //params.put(SIGNATURE, String.valueOf(cursor.getLong(cursor.getColumnIndex(SIGNATURE))));

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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_LEAVE + " WHERE " + LEAVE_STATUS + " = '" + status + "' ";
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(LEAVEID))));

                params.put(DATE_ASSUMPTION,cursor.getString(cursor.getColumnIndex(DATE_ASSUMPTION)));
                params.put(DATE_PROMOTION,cursor.getString(cursor.getColumnIndex(DATE_PROMOTION)));
                params.put(DATE_RETURN,cursor.getString(cursor.getColumnIndex(DATE_RETURN)));
                params.put(DAYS_TAKEN,cursor.getString(cursor.getColumnIndex(DAYS_TAKEN)));
                params.put(BALANCE_TAKEN,cursor.getString(cursor.getColumnIndex(BALANCE_TAKEN)));
                params.put(ENTITLEMENT,cursor.getString(cursor.getColumnIndex(ENTITLEMENT)));
                params.put(LEAVE_NOW,cursor.getString(cursor.getColumnIndex(LEAVE_NOW)));
                params.put(LEAVE_FROM,cursor.getString(cursor.getColumnIndex(LEAVE_FROM)));
                params.put(LEAVE_TO,cursor.getString(cursor.getColumnIndex(LEAVE_TO)));
                params.put(BALANCE_OUTSTANDING,cursor.getString(cursor.getColumnIndex(BALANCE_OUTSTANDING)));
                params.put(LEAVEDUE_FROM,cursor.getString(cursor.getColumnIndex(LEAVEDUE_FROM)));
                params.put(LEAVEDUE_TO,cursor.getString(cursor.getColumnIndex(LEAVEDUE_TO)));
               // params.put(SIGNATURE, String.valueOf(cursor.getLong(cursor.getColumnIndex(SIGNATURE))));

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
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_LEAVE+ " WHERE " + LEAVE_STATUS + " = '" + status + "' ";
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
            String updateQuery = "UPDATE " + Constants.config.TABLE_LEAVE + " SET " + LEAVE_STATUS + " = '" + status + "', "+LEAVE_ID+" = '"+log_id+"' where " + LEAVEID + "='" + id + "'  ";
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
                db.execSQL("DELETE FROM " + Constants.config.TABLE_LEAVE+" WHERE "+LEAVE_STATUS+" = '"+status+"' ");

                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(DATE_ASSUMPTION,jsonObject.getString(Constants.config.DATE_ASSUMPTION));
                    contentValues.put(DATE_PROMOTION,jsonObject.getString(Constants.config.DATE_PROMOTION));
                    contentValues.put(DATE_RETURN,jsonObject.getString(Constants.config.DATE_RETURN));
                    contentValues.put(DAYS_TAKEN,jsonObject.getString(Constants.config.DAYS_TAKEN));
                    contentValues.put(BALANCE_TAKEN,jsonObject.getString(Constants.config.BALANCE_TAKEN));
                    contentValues.put(ENTITLEMENT,jsonObject.getString(Constants.config.ENTITLEMENT));
                    contentValues.put(LEAVE_NOW,jsonObject.getString(Constants.config.LEAVE_NOW));
                    contentValues.put(LEAVE_FROM,jsonObject.getString(Constants.config.LEAVE_FROM));
                    contentValues.put(LEAVE_ID,jsonObject.getLong(Constants.config.LEAVE_ID));
                    contentValues.put(LEAVE_TO,jsonObject.getString(Constants.config.LEAVE_TO));
                    contentValues.put(BALANCE_OUTSTANDING,jsonObject.getString(Constants.config.BALANCE_OUTSTANDING));
                    contentValues.put(LEAVEDUE_FROM,jsonObject.getString(Constants.config.LEAVEDUE_FROM));
                    contentValues.put(LEAVEDUE_TO,jsonObject.getString(Constants.config.LEAVEDUE_TO));
                    //contentValues.put(SIGNATURE,jsonObject.getLong(Constants.config.SIGNATURE));
                    contentValues.put(LEAVE_STATUS,status);

                        db = DBHelper.getHelper(context).getWritableDB();
                        db.insert(Constants.config.TABLE_LEAVE, null, contentValues);
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records ,"+TABLE_LEAVE+" Updated successfully!";

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
