package com.example.john.leaveapp.db_operartions;

import android.app.ProgressDialog;
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
import com.example.john.leaveapp.core.BaseApplication;
import com.example.john.leaveapp.core.DBHelper;
import com.example.john.leaveapp.core.UserDetails;
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
import static com.example.john.leaveapp.utils.Constants.config.DEPARTMENT_ID;
import static com.example.john.leaveapp.utils.Constants.config.DEPARTMENT_NAME;
import static com.example.john.leaveapp.utils.Constants.config.DEP_STAFF_ID;
import static com.example.john.leaveapp.utils.Constants.config.RESPONSIBILITY_ID;
import static com.example.john.leaveapp.utils.Constants.config.STAFFID;
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
import static com.example.john.leaveapp.utils.Constants.config.TABLE_APPLY;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_STAFF;

/**
 * Created by john on 10/17/17.
 */
public class Staff {
    Context context;
    private static final String TAG = "Staff";
    public Staff(Context context){
        this.context = context;
    }

    public String save(int id,String fname, String lname, String gender,String username, String password,String contact,String salary,String role, int res_id, int department_id, int status) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;
        try{
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(STAFFL_FNAME,fname);
            contentValues.put(STAFF_ID,id);
            contentValues.put(STAFFL_LNAME,lname);
            contentValues.put(STAFF_ROLE,role);
            contentValues.put(STAFF_GENDER,gender);
            contentValues.put(STAFF_USERNAME,username);
            contentValues.put(STAFF_PASSWORD,password);
            contentValues.put(STAFF_PHONE,contact);
            contentValues.put(STAFF_SALARY,salary);
            contentValues.put(STAFF_STATUS,status);
            contentValues.put(RESPONSIBILITY_ID,res_id);
            contentValues.put(DEPARTMENT_ID,department_id);
            database.insert(Constants.config.TABLE_STAFF, null, contentValues);
            //database.setTransactionSuccessful();
            message = "Staff Details saved!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }
    public String selectPassword(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        String password = "";
        try{
            db.beginTransaction();
            String query = "SELECT "+Constants.config.STAFF_PASSWORD+" FROM" +
                    " "+ Constants.config.TABLE_STAFF+" " +
                    " ORDER BY "+Constants.config.STAFF_ID+" DESC LIMIT 1 ";
            cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    password = cursor.getString(cursor.getColumnIndex(Constants.config.STAFF_PASSWORD));
                }while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  password;
    }

    public Cursor login(String username, String password) {
        BaseApplication.deleteCache(context);
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        String message = "";
        try{
            db.beginTransaction();
            String query = "SELECT * FROM " +
                    " "+ Constants.config.TABLE_STAFF+" WHERE "+STAFF_USERNAME+" = '"+username+"' AND "+STAFF_PASSWORD+" = '"+password+"' " +
                    " ORDER BY "+Constants.config.STAFF_ID+" DESC LIMIT 1 ";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }

    public String edit(String fname, String lname, String role, String gender, String username, String password,String contact, String salary, int res_id, int department_id) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(STAFFL_FNAME,fname);
            contentValues.put(STAFFL_LNAME,lname);
            contentValues.put(STAFF_ROLE,role);
            contentValues.put(STAFF_GENDER,gender);
            contentValues.put(STAFF_USERNAME,username);
            contentValues.put(STAFF_PASSWORD,password);
            contentValues.put(STAFF_PHONE,contact);
            contentValues.put(STAFF_SALARY,salary);
            contentValues.put(RESPONSIBILITY_ID,res_id);
            contentValues.put(DEPARTMENT_ID,department_id);
            contentValues.put(STAFF_STATUS,status);
            database.update(Constants.config.TABLE_STAFF
                    ,contentValues,STAFF_ID+"="+new UserDetails(context).getid(), null);
            message = "personel details updated!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }
    public String getDepartment(int id){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        String department = "";
        try{
            db.beginTransaction();
            String query = "SELECT "+DEPARTMENT_NAME+" FROM" +
                    " "+ Constants.config.TABLE_STAFF+" f, "+Constants.config.TABLE_DEPARTMENT+" d, "+Constants.config.TABLE_DEP_STAFF+" df " +
                    " WHERE f."+STAFF_ID+" = df."+STAFF_ID+" AND df."+DEPARTMENT_ID+" = d."+DEPARTMENT_ID+" ORDER BY "+DEP_STAFF_ID+" DESC LIMIT 1";
            cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    department = cursor.getString(cursor.getColumnIndex(Constants.config.DEPARTMENT_NAME));
                }while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  department;
    }
    public Cursor getAll(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT * FROM" +
                    " "+ Constants.config.TABLE_STAFF+" ORDER BY "+STAFFL_FNAME+" DESC";
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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_STAFF ;

        int status = 1;
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(STAFFID))));

                params.put(STAFFL_FNAME,cursor.getString(cursor.getColumnIndex(STAFFL_FNAME)));
                params.put(STAFFL_LNAME,cursor.getString(cursor.getColumnIndex(STAFFL_LNAME)));
                params.put(STAFF_ROLE,cursor.getString(cursor.getColumnIndex(STAFF_ROLE)));
                params.put(STAFF_GENDER,cursor.getString(cursor.getColumnIndex(STAFF_GENDER)));
                params.put(STAFF_USERNAME,cursor.getString(cursor.getColumnIndex(STAFF_USERNAME)));
                params.put(STAFF_PASSWORD,cursor.getString(cursor.getColumnIndex(STAFF_PASSWORD)));
                params.put(STAFF_PHONE,cursor.getString(cursor.getColumnIndex(STAFF_PHONE)));
                params.put(STAFF_SALARY,cursor.getString(cursor.getColumnIndex(STAFF_SALARY)));
                params.put(RESPONSIBILITY_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(RESPONSIBILITY_ID))));
                params.put(DEPARTMENT_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(DEPARTMENT_ID))));

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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_STAFF + " WHERE " + STAFF_STATUS + " = '" + status + "' ";
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(STAFFID))));

                params.put(STAFFL_FNAME,cursor.getString(cursor.getColumnIndex(STAFFL_FNAME)));
                params.put(STAFFL_LNAME,cursor.getString(cursor.getColumnIndex(STAFFL_LNAME)));
                params.put(STAFF_ROLE,cursor.getString(cursor.getColumnIndex(STAFF_ROLE)));
                params.put(STAFF_GENDER,cursor.getString(cursor.getColumnIndex(STAFF_GENDER)));
                params.put(STAFF_USERNAME,cursor.getString(cursor.getColumnIndex(STAFF_USERNAME)));
                params.put(STAFF_PASSWORD,cursor.getString(cursor.getColumnIndex(STAFF_PASSWORD)));
                params.put(STAFF_PHONE,cursor.getString(cursor.getColumnIndex(STAFF_PHONE)));
                params.put(STAFF_SALARY,cursor.getString(cursor.getColumnIndex(STAFF_SALARY)));
                params.put(RESPONSIBILITY_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(RESPONSIBILITY_ID))));
                params.put(DEPARTMENT_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(DEPARTMENT_ID))));

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
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_STAFF+ " WHERE " + STAFF_STATUS + " = '" + status + "' ";
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
            String updateQuery = "UPDATE " + Constants.config.TABLE_STAFF + " SET " + STAFF_STATUS + " = '" + status + "', "+STAFFID+" = '"+log_id+"' where " + STAFFID + "='" + id + "'  ";
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
                db.execSQL("DELETE FROM " + Constants.config.TABLE_STAFF+" WHERE "+STAFF_STATUS+" = '"+status+"' ");

                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(STAFFL_FNAME,jsonObject.getString(Constants.config.STAFFL_FNAME));
                    contentValues.put(STAFF_ID,jsonObject.getLong(Constants.config.STAFF_ID));
                    contentValues.put(STAFFL_LNAME,jsonObject.getString(Constants.config.STAFFL_LNAME));
                    contentValues.put(STAFF_ROLE,jsonObject.getString(Constants.config.STAFF_ROLE));
                    contentValues.put(STAFF_GENDER,jsonObject.getString(Constants.config.STAFF_GENDER));
                    contentValues.put(STAFF_USERNAME,jsonObject.getString(Constants.config.STAFF_USERNAME));
                    contentValues.put(STAFF_PASSWORD,jsonObject.getString(Constants.config.STAFF_PASSWORD));
                    contentValues.put(STAFF_PHONE,jsonObject.getString(Constants.config.STAFF_PHONE));
                    contentValues.put(STAFF_SALARY,jsonObject.getString(Constants.config.STAFF_SALARY));
                    contentValues.put(STAFF_STATUS,status);
                    contentValues.put(RESPONSIBILITY_ID,jsonObject.getLong(Constants.config.RESPONSIBILITY_ID));
                    contentValues.put(DEPARTMENT_ID,jsonObject.getLong(Constants.config.DEPARTMENT_ID));

                    db = DBHelper.getHelper(context).getWritableDB();
                    db.insert(Constants.config.TABLE_STAFF, null, contentValues);
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records ,"+TABLE_STAFF+" Updated successfully!";

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
