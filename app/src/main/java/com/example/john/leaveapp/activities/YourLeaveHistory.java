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
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ID;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_LEAVE_TYPE;

/**
 * Created by john on 4/13/18.
 */

public class YourLeaveHistory  extends AppCompatActivity {
    private Context context = this;
    private final static String TAG = "YourLeaveHistory";
    private ListView listView;
    private String mode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_leave);
        listView = (ListView) findViewById(R.id.listView);
        //mode = getIntent().getStringExtra("mode");

        setSigle();

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
    private void setSigle() {
        List<String> name = new ArrayList<>();
        List<String> type = new ArrayList<>();
        List<String> stats1 = new ArrayList<>();
        List<String> status2 = new ArrayList<>();
        List<String> phone = new ArrayList<>();
        List<String> date_from = new ArrayList<>();
        List<String> date_to = new ArrayList<>();
        List<Integer> id = new ArrayList<>();
        int staff_id = new UserDetails(context).getid();
        try{
            /**String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_LEAVE+" n, "+Constants.config.TABLE_STAFF+" s, "+TABLE_LEAVE_TYPE+" t  WHERE " +
                    " a."+Constants.config.LEAVE_ID+" = n."+Constants.config.LEAVE_ID+"  " +
                    " AND s."+Constants.config.STAFF_ID+" = a."+Constants.config.STAFF_ID+" AND a."+Constants.config.STAFF_ID+" = '"+staff_id+"' AND t."+LEAVETYPE_ID+" = n."+LEAVETYPE_ID+" ORDER BY a."+Constants.config.DATE+" DESC";

             **/
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_LEAVE+" n, "+TABLE_LEAVE_TYPE+" t WHERE " +
                    " a."+Constants.config.LEAVE_ID+" = n."+Constants.config.LEAVE_ID+"  " +
                    " AND t."+LEAVETYPE_ID+" = n."+LEAVETYPE_ID+" AND a."+Constants.config.STAFF_ID+" = '"+staff_id+"' " +
                    "ORDER BY a."+Constants.config.DATE+" DESC";


            Cursor cursor = ReturnCursor.getCursor(query,context);
            if (cursor.moveToFirst()){
                do {
                    id.add(cursor.getInt(cursor.getColumnIndex(Constants.config.APPLY_ID)));
                    name.add(cursor.getString(cursor.getColumnIndex(Constants.config.DATE))+" "+
                            cursor.getString(cursor.getColumnIndex(Constants.config.TIME)));
                    phone.add(new UserDetails(context).getphone());
                    type.add(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVETYPE_NAME)));
                    int s1 = cursor.getInt(cursor.getColumnIndex(Constants.config.LEAVE_STATUS1));
                    int s2 = cursor.getInt(cursor.getColumnIndex(Constants.config.LEAVE_STATUS2));
                    String st1 = "",st2 = "";
                    if (s1 == 0){
                        st1 = "Not Approved";
                    }else {
                        st1 = "Approved";
                    }

                    if (s2 == 0){
                        st2 = "Not Approved";
                    }else {
                        st2 = "Approved";
                    }
                    stats1.add(st1);
                    status2.add(st2);
                    date_from.add(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVE_FROM)));
                    date_to.add(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVE_TO)));
                }while (cursor.moveToNext());
            }else {
                Log.e("CURSOR",query+"\nNo cursor data found..!");
            }
            IncomingAdapter adapter = new IncomingAdapter(context,name,id,type,date_from,date_to,stats1,status2,phone);
            listView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
