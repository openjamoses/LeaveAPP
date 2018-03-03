package com.example.john.leaveapp.db_operartions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.john.leaveapp.core.DBHelper;
import com.example.john.leaveapp.utils.Constants;

import static com.example.john.leaveapp.utils.Constants.config.STAFF_ID;
import static com.example.john.leaveapp.utils.Constants.config.UNIVERSITY_ID;
import static com.example.john.leaveapp.utils.Constants.config.UNIVERSITY_LOGO;
import static com.example.john.leaveapp.utils.Constants.config.UNIVERSITY_NAME;
/**
 * Created by john on 10/18/17.
 */

public class University {
    Context context;
    public University(Context context){
        this.context = context;
    }

    public String save(String name, String logo) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(UNIVERSITY_NAME,name);
            contentValues.put(UNIVERSITY_LOGO,logo);
            database.insert(Constants.config.TABLE_UNIVERSITY, null, contentValues);
            //database.setTransactionSuccessful();
            message = "University Details saved!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }

    public String edit(String name,String logo, int id) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(UNIVERSITY_NAME,name);
            contentValues.put(UNIVERSITY_LOGO,logo);
            database.update(Constants.config.TABLE_UNIVERSITY, contentValues, UNIVERSITY_ID+"="+id, null);
            //database.setTransactionSuccessful();
            message = "University Details updated!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }

    ///// TODO: 10/13/17  select here!
    public  Cursor selectAll(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_UNIVERSITY+"  ORDER BY "+Constants.config.UNIVERSITY_NAME+" ASC ";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }

    public int selectLastID(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        int id = 0;
        try{
            db.beginTransaction();
            String query = "SELECT "+Constants.config.UNIVERSITY_ID+"  FROM" +
                    " "+ Constants.config.TABLE_UNIVERSITY+"  ORDER BY "+Constants.config.UNIVERSITY_ID+" DESC LIMIT 1 ";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();

            if (cursor.moveToFirst()){
                do {
                    id = cursor.getInt(cursor.getColumnIndex(Constants.config.UNIVERSITY_ID));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  id;
    }

    public Cursor select(int id){
        SQLiteDatabase db = new DBHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_UNIVERSITY+" WHERE "+UNIVERSITY_ID+" = '"+id+"' " +
                    " ORDER BY "+Constants.config.UNIVERSITY_NAME+" ASC ";
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
