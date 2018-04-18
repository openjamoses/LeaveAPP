package com.example.john.leaveapp.activities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.adapter.IncomingAdapter;
import com.example.john.leaveapp.core.ReturnCursor;
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.db_operartions.Apply;
import com.example.john.leaveapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.example.john.leaveapp.utils.Constants.config.LEAVETYPE_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_STATUS;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_STATUS1;

/**
 * Created by john on 3/12/18.
 */

public class LeaveHistoryActivity extends AppCompatActivity {
    private Context context = this;
    private final static String TAG = "LeaveHistoryActivity";
    private ListView listView;
    private String mode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_leave);
        listView = (ListView) findViewById(R.id.listView);
        mode = getIntent().getStringExtra("mode");


        int status = 0;
        if (mode.equals("us")){
            status = 1;
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_LEAVE+" n, "+Constants.config.TABLE_STAFF+" s, "+Constants.config.TABLE_LEAVE_TYPE+" t WHERE " +
                    " a."+Constants.config.LEAVE_ID+" = n."+Constants.config.LEAVE_ID+"  AND t."+ LEAVETYPE_ID+" = n."+LEAVETYPE_ID+"  " +
                    " AND s."+Constants.config.STAFF_ID+" = a."+Constants.config.STAFF_ID+" AND a."+LEAVE_STATUS+" = '"+status+"' ORDER BY a."+Constants.config.DATE+" DESC";

            setValues(query);
        }else {
            status = 0;
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_LEAVE+" n, "+Constants.config.TABLE_STAFF+" s, "+Constants.config.TABLE_LEAVE_TYPE+" t WHERE " +
                    " a."+Constants.config.LEAVE_ID+" = n."+Constants.config.LEAVE_ID+"  AND t."+ LEAVETYPE_ID+" = n."+LEAVETYPE_ID+"  " +
                    " AND s."+Constants.config.STAFF_ID+" = a."+Constants.config.STAFF_ID+" AND a."+LEAVE_STATUS+" = '"+status+"' ORDER BY a."+Constants.config.DATE+" DESC";

            setValues(query);
        }
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private void setValues(String query) {
        List<String> name = new ArrayList<>();
        List<String> type = new ArrayList<>();
        List<String> stats1 = new ArrayList<>();
        List<String> status2 = new ArrayList<>();
        List<String> phone = new ArrayList<>();
        List<String> date_from = new ArrayList<>();
        List<String> date_to = new ArrayList<>();
        List<Integer> id = new ArrayList<>();
        try{
            Cursor cursor = ReturnCursor.getCursor(query,context);
            if (cursor.moveToFirst()){
                do {
                    id.add(cursor.getInt(cursor.getColumnIndex(Constants.config.APPLY_ID)));
                    name.add(cursor.getString(cursor.getColumnIndex(Constants.config.STAFFL_FNAME))+" "+
                            cursor.getString(cursor.getColumnIndex(Constants.config.STAFFL_LNAME)));
                    phone.add(cursor.getString(cursor.getColumnIndex(Constants.config.STAFF_PHONE)));
                    type.add(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVETYPE_NAME)));
                    int s1 = cursor.getInt(cursor.getColumnIndex(Constants.config.LEAVE_STATUS));
                    //int s2 = cursor.getInt(cursor.getColumnIndex(Constants.config.LEAVE_STATUS2));
                    Log.e("TAG","STATUS::: "+s1);
                    String st1 = "",st2 = "";
                    if (s1 == 0){
                        st1 = "Not Approved";
                        st2 = "Not Approved";
                    }else {
                        st1 = "Approved";
                        st2 = "Approved";
                    }
                    stats1.add(st1);
                    status2.add(st2);
                    date_from.add(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVE_FROM)));
                    date_to.add(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVE_TO)));
                }while (cursor.moveToNext());
            }
            IncomingAdapter adapter = new IncomingAdapter(context,name,id,type,date_from,date_to,stats1,status2,phone, "incoming");
            listView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
