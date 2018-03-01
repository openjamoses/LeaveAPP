package com.example.john.leaveapp.db_operartions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.john.leaveapp.core.DBHelper;
import com.example.john.leaveapp.utils.Constants;
import com.example.john.leaveapp.utils.Phone;
import static com.example.john.leaveapp.utils.Constants.config.SECRETARY_ID;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ID;
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
}
