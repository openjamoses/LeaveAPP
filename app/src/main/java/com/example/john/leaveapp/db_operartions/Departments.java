package com.example.john.leaveapp.db_operartions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.john.leaveapp.core.DBHelper;
import com.example.john.leaveapp.utils.Constants;
import com.example.john.leaveapp.utils.Phone;

import static com.example.john.leaveapp.utils.Constants.config.DEPARTMENT_ID;
import static com.example.john.leaveapp.utils.Constants.config.DEPARTMENT_NAME;
import static com.example.john.leaveapp.utils.Constants.config.FACULTY_ID;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ID;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_STAFF;

/**
 * Created by john on 10/18/17.
 */

public class Departments {

    Context context;
    public Departments(Context context){
        this.context = context;
    }

    public String save(String name, int faculty_id, int staff_id) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(DEPARTMENT_NAME,name);
            contentValues.put(FACULTY_ID,faculty_id);
            contentValues.put(STAFF_ID,staff_id);
            database.insert(Constants.config.TABLE_DEPARTMENT, null, contentValues);
            //database.setTransactionSuccessful();
            message = "Department Details saved!";

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
            contentValues.put(STAFF_ID,staff_id);
            database.update(Constants.config.TABLE_DEPARTMENT,contentValues, DEPARTMENT_ID+"="+id, null);
            //database.setTransactionSuccessful();
            message = "Department details updated!";

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
                    " "+ Constants.config.TABLE_DEPARTMENT+" d, "+Constants.config.TABLE_FACULTY+" f, "+TABLE_STAFF+" s" +
                    " WHERE d."+Constants.config.STAFF_ID+" = s."+Constants.config.STAFF_ID+" AND " +
                    " d."+FACULTY_ID+" = f."+FACULTY_ID+"" +
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
}
