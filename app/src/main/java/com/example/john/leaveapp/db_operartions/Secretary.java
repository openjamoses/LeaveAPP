package com.example.john.leaveapp.db_operartions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.john.leaveapp.core.DBHelper;
import com.example.john.leaveapp.utils.Constants;
import com.example.john.leaveapp.utils.Phone;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.example.john.leaveapp.utils.Constants.config.RESPONSIBILITY_ID;
import static com.example.john.leaveapp.utils.Constants.config.SECRETARY_ID;
import static com.example.john.leaveapp.utils.Constants.config.STAFFL_FNAME;
import static com.example.john.leaveapp.utils.Constants.config.STAFFL_LNAME;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_GENDER;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ID;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_PASSWORD;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_PHONE;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ROLE;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_SALARY;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_STATUS;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_USERNAME;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_SECRETARY;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_STAFF;
import static com.example.john.leaveapp.utils.Constants.config.UNIVERSITY_ID;

/**
 * Created by john on 10/18/17.
 */

public class Secretary {
    Context context;
    public Secretary(Context context){
        this.context = context;
    }
    public String save(int university_id, int staff_id) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(UNIVERSITY_ID,university_id);
            contentValues.put(STAFF_ID,staff_id);
            database.insert(Constants.config.TABLE_SECRETARY, null, contentValues);
            //database.setTransactionSuccessful();
            message = "Secretary Details saved!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }

    public String edit( int university_id, int staff_id, int id) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(UNIVERSITY_ID,university_id);
            contentValues.put(STAFF_ID,staff_id);
            database.update(Constants.config.TABLE_SECRETARY,contentValues, SECRETARY_ID+"="+id, null);
            //database.setTransactionSuccessful();
            message = "Secretary details updated!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }

    public Cursor get(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_SECRETARY+" d  ORDER BY "+Constants.config.SECRETARY_ID+" DESC LIMIT 1";
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

    public Cursor getByID(int id) {
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_SECRETARY+" d WHERE "+Constants.config.STAFF_ID+" = '"+id+"' ORDER BY "+Constants.config.SECRETARY_ID+" DESC LIMIT 1";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
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
                 int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(STAFF_ID,jsonObject.getLong(Constants.config.STAFF_ID));
                    contentValues.put(UNIVERSITY_ID,jsonObject.getString(Constants.config.UNIVERSITY_ID));

                    db = DBHelper.getHelper(context).getReadableDB();
                    String query = "SELECT *  FROM" +
                            " "+ Constants.config.TABLE_SECRETARY+" d WHERE "+Constants.config.STAFF_ID+" = '"+jsonObject.getLong(Constants.config.STAFF_ID)+"'";
                    Cursor cursor = db.rawQuery(query,null);
                    if (cursor.moveToFirst()){

                    }else{
                        db = DBHelper.getHelper(context).getWritableDB();
                        db.insert(Constants.config.TABLE_SECRETARY, null, contentValues);
                    }

                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records ,"+TABLE_SECRETARY+" Updated successfully!";

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
