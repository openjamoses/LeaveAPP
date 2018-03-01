package com.example.john.leaveapp.db_operartions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.john.leaveapp.core.DBHelper;
import com.example.john.leaveapp.utils.Constants;
import com.example.john.leaveapp.utils.Phone;

import static com.example.john.leaveapp.utils.Constants.config.STAFFL_FNAME;
import static com.example.john.leaveapp.utils.Constants.config.STAFFL_LNAME;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_DOB;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_GENDER;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_PASSWORD;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_PHONE;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_USERNAME;

/**
 * Created by john on 10/17/17.
 */
public class Staff {
    Context context;
    public Staff(Context context){
        this.context = context;
    }

    public String save(String fname, String lname, String gender,String dob,String username, String password,String contact) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(STAFFL_FNAME,fname);
            contentValues.put(STAFFL_LNAME,lname);
            contentValues.put(STAFF_DOB,dob);
            contentValues.put(STAFF_GENDER,gender);
            contentValues.put(STAFF_USERNAME,username);
            contentValues.put(STAFF_PASSWORD,password);
            contentValues.put(STAFF_PHONE,contact);
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
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        String message = "";
        try{
            db.beginTransaction();
            String query = "SELECT * FROM" +
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

    public String edit(String fname, String lname, String dob, String gender, String username, String password,String contact) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(STAFFL_FNAME,fname);
            contentValues.put(STAFFL_LNAME,lname);
            contentValues.put(STAFF_DOB,dob);
            contentValues.put(STAFF_GENDER,gender);
            contentValues.put(STAFF_USERNAME,username);
            contentValues.put(STAFF_PASSWORD,password);
            contentValues.put(STAFF_PHONE,contact);
            database.update(Constants.config.TABLE_STAFF
                    ,contentValues,STAFF_USERNAME+"= '"+username+"' AND '"+STAFF_PASSWORD+"' = '"+password+"'", null);
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
}