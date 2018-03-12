package com.example.john.leaveapp.db_operartions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.john.leaveapp.core.DBHelper;
import com.example.john.leaveapp.utils.Constants;
import com.example.john.leaveapp.utils.Phone;

import static com.example.john.leaveapp.utils.Constants.config.ACCOMPANIED_AGE;
import static com.example.john.leaveapp.utils.Constants.config.ACCOMPANIED_NAME;
import static com.example.john.leaveapp.utils.Constants.config.ACCOMPANIED_STATUS;
import static com.example.john.leaveapp.utils.Constants.config.ACCOMPANIED_TYPE;
import static com.example.john.leaveapp.utils.Constants.config.DEPARTMENT_NAME;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ID;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_STAFF;
/**
 * Created by john on 3/10/18.
 */
public class Accompanied {
    Context context;
    public Accompanied(Context context){
        this.context = context;
    }
    public String save(String name, String type, int age, int staff_id) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ACCOMPANIED_NAME,name);
            contentValues.put(ACCOMPANIED_TYPE,type);
            contentValues.put(ACCOMPANIED_AGE,age);
            contentValues.put(ACCOMPANIED_STATUS,status);
            contentValues.put(STAFF_ID,staff_id);

            String query = "SELECT *  FROM "+ Constants.config.TABLE_ACCOMPANIED+" f" +
                    " WHERE "+ACCOMPANIED_NAME+" = '"+name+"' AND "+Constants.config.STAFF_ID+" = '"+staff_id+"'";
            database = DBHelper.getHelper(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(query,null);
            if (cursor.moveToFirst()){
                message = "Accompanied details Already exist!";
            }else {
                database = DBHelper.getHelper(context).getWritableDatabase();
                database.insert(Constants.config.TABLE_ACCOMPANIED, null, contentValues);
                //database.setTransactionSuccessful();
                message = "Accompanied Details saved!";
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
    public Cursor selectAll(int staff_id){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_ACCOMPANIED+"  c" +
                    " WHERE c."+Constants.config.STAFF_ID+" = '"+staff_id+"' " +
                    " ORDER BY "+Constants.config.ACCOMPANIED_NAME+" ASC ";
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
