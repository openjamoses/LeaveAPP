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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import static com.example.john.leaveapp.utils.Constants.config.DEPARTMENT_ID;
import static com.example.john.leaveapp.utils.Constants.config.DEPARTMENT_NAME;
import static com.example.john.leaveapp.utils.Constants.config.DEPARTMENT_STATUS;
import static com.example.john.leaveapp.utils.Constants.config.DEP_STATUS;
import static com.example.john.leaveapp.utils.Constants.config.FACULTY_ID;
import static com.example.john.leaveapp.utils.Constants.config.FACULTY_NAME;
import static com.example.john.leaveapp.utils.Constants.config.HOST_URL;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ID;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_STAFF;
import static com.example.john.leaveapp.utils.Constants.config.UNIVERSITY_ID;
import static com.example.john.leaveapp.utils.Constants.config.UNIVERSITY_NAME;
import static com.example.john.leaveapp.utils.Constants.config.URL_SAVE_DEPARTMENT;
import static com.example.john.leaveapp.utils.Constants.config.URL_SAVE_UNIVERSITY;

/**
 * Created by john on 10/18/17.
 */

public class Departments {

    private Context context;
    private static final String TAG = "Departments";
    public Departments(Context context){
        this.context = context;
    }

    public String save(int dep_id,String name, int faculty_id, int status) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            ContentValues contentValues = new ContentValues();

            contentValues.put(DEPARTMENT_ID,dep_id);
            contentValues.put(DEPARTMENT_NAME,name);
            contentValues.put(FACULTY_ID,faculty_id);
            contentValues.put(DEPARTMENT_STATUS,status);

            String query = "SELECT *  FROM "+Constants.config.TABLE_DEPARTMENT+" f" +
                    " WHERE "+DEPARTMENT_NAME+" = '"+name+"' ORDER BY "+Constants.config.DEPARTMENT_ID+" ASC ";

            database = DBHelper.getHelper(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(query,null);
            if (cursor.moveToFirst()){
                message = "Department details Already exist!";
            }else {
                database = DBHelper.getHelper(context).getWritableDatabase();

                database.insert(Constants.config.TABLE_DEPARTMENT, null, contentValues);
                //database.setTransactionSuccessful();
                message = "Department Details saved!";
            }

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }

    public String save_staff(int staff_id, int department_id) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            ContentValues contentValues = new ContentValues();
            contentValues.put(DEPARTMENT_ID,department_id);
            contentValues.put(STAFF_ID,staff_id);
            contentValues.put(DEP_STATUS,status);
            //contentValues.put(STAFF_ID,staff_id);
            String query = "SELECT *  FROM "+Constants.config.TABLE_DEP_STAFF+" f" +
                    " WHERE "+STAFF_ID+" = '"+staff_id+"'";
            database = DBHelper.getHelper(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(query,null);
            if (cursor.moveToFirst()){
                message = "Department details Already exist!";
            }else {
                database = DBHelper.getHelper(context).getWritableDatabase();
                database.insert(Constants.config.TABLE_DEP_STAFF, null, contentValues);
                //database.setTransactionSuccessful();
                message = "Department Details saved!";
            }

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }



    public String edit(String name,  int faculty_id, int staff_id, int id) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DEPARTMENT_NAME,name);
            contentValues.put(FACULTY_ID,faculty_id);
            //contentValues.put(STAFF_ID,staff_id);
            contentValues.put(DEPARTMENT_STATUS,status);
            String query = "SELECT *  FROM "+Constants.config.TABLE_DEPARTMENT+" f" +
                    " WHERE "+DEPARTMENT_NAME+" = '"+name+"' ORDER BY "+Constants.config.DEPARTMENT_ID+" ASC ";

            database = DBHelper.getHelper(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(query,null);
            if (cursor.moveToFirst()){
                message = "Department details Already exist!";
            }else {
                database.update(Constants.config.TABLE_DEPARTMENT,contentValues, DEPARTMENT_ID+"="+id, null);
                //database.setTransactionSuccessful();
                message = "Department details updated!";
            }

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }

    public Cursor selectAll(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_DEPARTMENT+" d, "+Constants.config.TABLE_FACULTY+" f " +
                    " WHERE d."+Constants.config.FACULTY_ID+" = f."+Constants.config.FACULTY_ID+" " +
                    " ORDER BY "+Constants.config.DEPARTMENT_NAME+" ASC ";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }

    public Cursor select(int id){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_DEPARTMENT+" d, "+Constants.config.TABLE_FACULTY+" f, "+TABLE_STAFF+"" +
                    "  WHERE d."+Constants.config.STAFF_ID+" = s."+Constants.config.STAFF_ID+" AND " +
                    " d."+FACULTY_ID+" = f."+FACULTY_ID+" AND "+DEPARTMENT_ID+" = '"+id+"'" +
                    " ORDER BY "+Constants.config.DEPARTMENT_NAME+" ASC";
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
                db.execSQL("DELETE FROM " + Constants.config.TABLE_DEPARTMENT+" ");
                int total = 0;

                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(DEPARTMENT_ID,jsonObject.getString(Constants.config.DEPARTMENT_ID));
                    contentValues.put(DEPARTMENT_NAME,jsonObject.getString(Constants.config.DEPARTMENT_NAME));
                    contentValues.put(FACULTY_ID,jsonObject.getString(Constants.config.FACULTY_ID));
                    //contentValues.put(ENTITLEMENT,jsonObject.getString(Constants.config.ENTITLEMENT));
                    // contentValues.put(LEAVETYPE_STATUS,jsonObject.getString(Constants.config.LEAVETYPE_STATUS));
                    db = DBHelper.getHelper(context).getWritableDB();

                    db.insert(Constants.config.TABLE_DEPARTMENT, null, contentValues);
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records ,"+Constants.config.TABLE_DEPARTMENT+" Updated successfully!";

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
