package com.example.john.leaveapp.db_operartions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.john.leaveapp.core.DBHelper;
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.utils.Constants;
import com.example.john.leaveapp.utils.Phone;

import static com.example.john.leaveapp.utils.Constants.config.ANNUAL_ID;
import static com.example.john.leaveapp.utils.Constants.config.ANNUAL_STATUS;
import static com.example.john.leaveapp.utils.Constants.config.BALANCE_OUTSTANDING;
import static com.example.john.leaveapp.utils.Constants.config.BALANCE_TAKEN;
import static com.example.john.leaveapp.utils.Constants.config.DATE;
import static com.example.john.leaveapp.utils.Constants.config.DATE_ASSUMPTION;
import static com.example.john.leaveapp.utils.Constants.config.DATE_PROMOTION;
import static com.example.john.leaveapp.utils.Constants.config.DATE_RETURN;
import static com.example.john.leaveapp.utils.Constants.config.DAYS_TAKEN;
import static com.example.john.leaveapp.utils.Constants.config.ENTITLEMENT;
import static com.example.john.leaveapp.utils.Constants.config.LEAVEDUE_FROM;
import static com.example.john.leaveapp.utils.Constants.config.LEAVEDUE_TO;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_FROM;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_NOW;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_STATUS1;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_STATUS2;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_TO;
import static com.example.john.leaveapp.utils.Constants.config.SIGNATURE;
import static com.example.john.leaveapp.utils.Constants.config.STAFFL_FNAME;

/**
 * Created by john on 3/10/18.
 */

public class AnnualLeave {
    Context context;
    public AnnualLeave(Context context){
        this.context = context;
    }

    public String save(String assumption, String promotion, String dreturn,String dbance,String dtaken, String entitlement,String lnow,String lfrom,String lto,
                       String outstanding, String ldfrom, String ldto,int signature) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;
        try{
            int status = 0;
            //String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DATE_ASSUMPTION,assumption);
            contentValues.put(DATE_PROMOTION,promotion);
            contentValues.put(DATE_RETURN,dreturn);
            contentValues.put(DAYS_TAKEN,dtaken);
            contentValues.put(BALANCE_TAKEN,dbance);
            contentValues.put(ENTITLEMENT,entitlement);
            contentValues.put(LEAVE_NOW,lnow);
            contentValues.put(LEAVE_FROM,lfrom);

            contentValues.put(LEAVE_TO,lto);
            contentValues.put(BALANCE_OUTSTANDING,outstanding);
            contentValues.put(LEAVEDUE_FROM,ldfrom);
            contentValues.put(LEAVEDUE_TO,ldto);
            contentValues.put(SIGNATURE,signature);
            contentValues.put(ANNUAL_STATUS,status);
            database.insert(Constants.config.TABLE_ANNUAL, null, contentValues);
            //database.setTransactionSuccessful();
            message = "Anual Leave Details saved!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }


    public String edit(String assumption, String promotion, int dreturn,int dbance,String dtaken, String entitlement,String lnow,String lfrom,String lto,
                       String outstanding, String ldfrom, String ldto,int signature, int id) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(DATE_ASSUMPTION,assumption);
            contentValues.put(DATE_PROMOTION,promotion);
            contentValues.put(DATE_RETURN,dreturn);
            contentValues.put(DAYS_TAKEN,dtaken);
            contentValues.put(BALANCE_TAKEN,dbance);
            contentValues.put(ENTITLEMENT,entitlement);
            contentValues.put(LEAVE_NOW,lnow);
            contentValues.put(LEAVE_FROM,lfrom);
            contentValues.put(LEAVE_TO,lto);
            contentValues.put(BALANCE_OUTSTANDING,outstanding);
            contentValues.put(LEAVEDUE_FROM,ldfrom);
            contentValues.put(LEAVEDUE_TO,ldto);
            contentValues.put(SIGNATURE,signature);
            database.update(Constants.config.TABLE_ANNUAL
                    ,contentValues,ANNUAL_ID+"="+id, null);
            message = "Anual Leave details updated!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }
    public int lastID(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        int id = 0;
        try{
            db.beginTransaction();
            String query = "SELECT "+ANNUAL_ID+" FROM" +
                    " "+ Constants.config.TABLE_ANNUAL+" ORDER BY "+ANNUAL_ID+" DESC LIMIT 1";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndex(Constants.config.ANNUAL_ID));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  id;
    }

    public Cursor getAll(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT * FROM" +
                    " "+ Constants.config.TABLE_ANNUAL+" n, "+Constants.config.TABLE_APPLY+" p, "+Constants.config.TABLE_STAFF+" s " +
                    " WHERE n"+Constants.config.ANNUAL_ID+" = p."+Constants.config.LEAVE_ID+" AND p."+Constants.config.STAFF_ID+" = s."+Constants.config.STAFF_ID+" " +
                    " ORDER BY "+DATE+" ASC";
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
