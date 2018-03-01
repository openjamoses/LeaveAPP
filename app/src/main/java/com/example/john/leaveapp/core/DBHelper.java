package com.example.john.leaveapp.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.util.Log;

import com.example.john.leaveapp.utils.Constants;

import static com.example.john.leaveapp.core.Create_Table.create.CREATE_APPLY;
import static com.example.john.leaveapp.core.Create_Table.create.CREATE_APPROVAL;
import static com.example.john.leaveapp.core.Create_Table.create.CREATE_DEPARTMENT;
import static com.example.john.leaveapp.core.Create_Table.create.CREATE_FACULTY;
import static com.example.john.leaveapp.core.Create_Table.create.CREATE_FORM;
import static com.example.john.leaveapp.core.Create_Table.create.CREATE_LEAVEFORM;
import static com.example.john.leaveapp.core.Create_Table.create.CREATE_LEAVETYPE;
import static com.example.john.leaveapp.core.Create_Table.create.CREATE_SECRETARY;
import static com.example.john.leaveapp.core.Create_Table.create.CREATE_STAFF;
import static com.example.john.leaveapp.core.Create_Table.create.CREATE_UNIVERSITY;

public class DBHelper extends SQLiteOpenHelper {

    private final Handler handler;
    private static DBHelper instance;

    public static synchronized DBHelper getHelper(Context context)
    {
        if (instance == null)
            instance = new DBHelper(context);

        return instance;
    }

    public DBHelper(Context context) {
        super(context, Constants.config.DB_NAME, null, Constants.config.DB_VERSION);
        handler = new Handler(context.getMainLooper());
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO : Creating tables
        db.execSQL(CREATE_STAFF);
        db.execSQL(CREATE_APPLY);
        db.execSQL(CREATE_APPROVAL);
        db.execSQL(CREATE_UNIVERSITY);
        db.execSQL(CREATE_FACULTY);
        db.execSQL(CREATE_DEPARTMENT);
        db.execSQL(CREATE_FORM);
        db.execSQL(CREATE_SECRETARY);
        db.execSQL(CREATE_LEAVETYPE);
        db.execSQL(CREATE_LEAVEFORM);
        Log.e("DATABASE OPERATION",Constants.config.TOTAL_TABLES+" Tables  created / open successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: Updating table here
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_STAFF);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_APPROVAL);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_APPLY);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_UNIVERSITY);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_FACULTY);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_DEPARTMENT);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_FORM);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_SECRETARY);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_LEAVEFORM);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_LEAVE_TYPE);
        onCreate(db);
        Log.e("DATABASE OPERATION", Constants.config.TOTAL_TABLES+" Table created / open successfully");

    }
    private void runOnUiThread(Runnable r) {
        handler.post(r);
    }
    public   SQLiteDatabase getWritableDB(){
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();
        }catch (Exception e){
            e.printStackTrace();
        }

        return database;
    }
    public SQLiteDatabase getReadableDB(){
        SQLiteDatabase database = null;
        try {
            database = this.getReadableDatabase();
        }catch (Exception e){
            e.printStackTrace();
        }

        return database;
    }
    /************** Insertion ends here **********************/
}