package com.example.john.leaveapp.db_operartions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.john.leaveapp.core.DBHelper;
import com.example.john.leaveapp.utils.Constants;
import com.example.john.leaveapp.utils.Phone;

import static com.example.john.leaveapp.utils.Constants.config.DEPARTMENT_ID;
import static com.example.john.leaveapp.utils.Constants.config.FACULTY_ID;
import static com.example.john.leaveapp.utils.Constants.config.FACULTY_NAME;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_STAFF;
import static com.example.john.leaveapp.utils.Constants.config.UNIVERSITY_ID;
/**
 * Created by john on 10/18/17.
 */

public class Faculty {
    Context context;
    public Faculty(Context context){
        this.context = context;
    }
    public String save(String name, int university_id) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(FACULTY_NAME,name);
            contentValues.put(UNIVERSITY_ID,university_id);
            database.insert(Constants.config.TABLE_FACULTY, null, contentValues);
            //database.setTransactionSuccessful();
            message = "Faculty Details saved!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }

    public String edit(String name,  int university_id, int id) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(FACULTY_NAME,name);
            contentValues.put(UNIVERSITY_ID,university_id);
            database.update(Constants.config.TABLE_FACULTY,contentValues, FACULTY_ID+"="+id, null);
            //database.setTransactionSuccessful();
            message = "Faculty details updated!";

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
                    " "+ Constants.config.TABLE_DEPARTMENT+" d, "+Constants.config.TABLE_FACULTY+" f" +
                    " WHERE d."+Constants.config.STAFF_ID+" = f."+Constants.config.FACULTY_ID+" " +
                    "ORDER BY "+Constants.config.FACULTY_NAME+" ASC ";
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
                    " "+ Constants.config.TABLE_DEPARTMENT+" d, "+Constants.config.TABLE_FACULTY+" f" +
                    " WHERE d."+Constants.config.STAFF_ID+" = f."+Constants.config.FACULTY_ID+" " +
                    "AND f."+Constants.config.FACULTY_ID+" = '"+id+"' " +
                    "ORDER BY "+Constants.config.FACULTY_NAME+" ASC ";
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
