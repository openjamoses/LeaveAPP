package com.example.john.leaveapp.db_operartions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.john.leaveapp.core.DBHelper;
import com.example.john.leaveapp.utils.Constants;
import com.example.john.leaveapp.utils.Phone;

import static com.example.john.leaveapp.utils.Constants.config.ANNUAL_ID;
import static com.example.john.leaveapp.utils.Constants.config.APPLY_ID;
import static com.example.john.leaveapp.utils.Constants.config.APPLY_STATUS;
import static com.example.john.leaveapp.utils.Constants.config.DATE;
import static com.example.john.leaveapp.utils.Constants.config.LEAVETYPE_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_STATUS1;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_STATUS2;
import static com.example.john.leaveapp.utils.Constants.config.LESS_LEAVE;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ID;
import static com.example.john.leaveapp.utils.Constants.config.TIME;;

/**
 * Created by john on 3/10/18.
 */

public class Apply {

    Context context;
    public Apply(Context context){
        this.context = context;
    }
    public String save(int leave_id, int type_id,int less, int status1, int status2, String date, String time , int staff_id ) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(LEAVE_ID,leave_id);
            contentValues.put(LEAVETYPE_ID,type_id);
            contentValues.put(LESS_LEAVE,less);
            contentValues.put(LEAVE_STATUS1,status1);
            contentValues.put(LEAVE_STATUS2,status2);
            contentValues.put(DATE,date);
            contentValues.put(TIME,time);
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
            contentValues.put(LEAVE_ID,leave_id);
            contentValues.put(LEAVETYPE_ID,type_id);
            contentValues.put(LESS_LEAVE,less);
            contentValues.put(LEAVE_STATUS1,status1);
            contentValues.put(LEAVE_STATUS2,status2);
            contentValues.put(DATE,date);
            contentValues.put(TIME,time);
            contentValues.put(STAFF_ID,staff_id);
            contentValues.put(APPLY_STATUS,status);
            database.update(Constants.config.TABLE_APPLY,contentValues, APPLY_ID+"="+id, null);
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
                    " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_ANNUAL+" n, "+Constants.config.TABLE_STAFF+" s WHERE " +
                    " a."+Constants.config.LEAVE_ID+" = n."+Constants.config.ANNUAL_ID+" AND a."+Constants.config.LEAVETYPE_ID+" = '"+type+"'" +
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
                    " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_ANNUAL+" n, "+Constants.config.TABLE_STAFF+" s WHERE " +
                    " a."+Constants.config.LEAVE_ID+" = n."+Constants.config.ANNUAL_ID+" AND a."+Constants.config.LEAVETYPE_ID+" = '"+type+"'" +
                    " AND s."+Constants.config.STAFF_ID+" = a."+Constants.config.STAFF_ID+" AND n."+ANNUAL_ID+" = '"+leave_id+"' ORDER BY a."+Constants.config.DATE+" DESC";
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
