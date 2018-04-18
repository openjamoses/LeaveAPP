package com.example.john.leaveapp.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.activities.pdf_itext.PdfMain;
import com.example.john.leaveapp.core.BaseApplication;
import com.example.john.leaveapp.core.ReturnCursor;
import com.example.john.leaveapp.core.SessionManager;
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.utils.Constants;

import static com.example.john.leaveapp.utils.Constants.config.APPLY_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVETYPE_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_ID;

/**
 * Created by john on 2/28/18.
 */

public class HOD_MainActivity extends AppCompatActivity {
    private Context context = this;
    private Button btn_incoming,btn_apply,btn_recent,btn_others,btn_notification,btn_report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hod_main);

        try{
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
            setSupportActionBar(toolbar);
            TextView toolbar_subtitle = (TextView) findViewById(R.id.toolbar_subtitle);
            toolbar_subtitle.setText("HOD Platform");

            btn_notification = (Button) findViewById(R.id.btn_notification);

            btn_incoming = (Button) findViewById(R.id.btn_incoming);
            btn_apply = (Button) findViewById(R.id.btn_apply);
            btn_recent = (Button) findViewById(R.id.btn_recent);
            //btn_others = (Button) findViewById(R.id.btn_others);
            btn_report = (Button) findViewById(R.id.btn_report);

            btn_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, com.example.john.leaveapp.activities.ApplyActivity.class));
                }
            });

            btn_incoming.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, LeaveHistoryActivity.class);
                    intent.putExtra("mode","hod");
                    startActivity(intent);
                }
            });

            btn_recent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, YourLeaveHistory.class));
                }
            });
            btn_notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context,NotificationActivity.class));
                }
            });
            btn_report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, PdfMain.class));
                }
            });
            BaseApplication.deleteCache(context);
            notification();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void notification(){
        try{
            int count = 0, status_count = 0;
            String query = "SELECT * FROM "+ Constants.config.TABLE_NOTICATION+" n, "+Constants.config.TABLE_APPLY+" a, "+Constants.config.TABLE_LEAVE+" l, "+Constants.config.TABLE_LEAVE_TYPE+" t " +
                    "WHERE n."+APPLY_ID+" = a."+APPLY_ID+" AND a."+LEAVE_ID+" = l."+LEAVE_ID+" AND l."+LEAVETYPE_ID+" = t."+LEAVETYPE_ID+"" +
                    " AND  n."+Constants.config.STAFF_ID+" = '"+new UserDetails(context).getid()+"' ORDER BY "+Constants.config.NOTICATIONID+" DESC";
            Cursor cursor = ReturnCursor.getCursor(query,context);
            if (cursor.moveToFirst()){
                do {
                     count ++;
                     int status = cursor.getInt(cursor.getColumnIndex(Constants.config.NOTIFICATION_STATUS));
                    if (status == 0){
                        status_count ++;
                    }
                }while (cursor.moveToNext());
            }
            String tot = status_count+"/"+count;
            btn_notification.setText("( "+tot+" ) Notifications");
            if (status_count > 0){
                btn_notification.setTextColor(getResources().getColor(R.color.deep_orange));

            }else {
                btn_notification.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            startActivity(new Intent(context, ProfileActivity.class));
            //Toast.makeText(context,"Not Implemented...!",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_logout) {
            try{
                new SessionManager(context).logoutUser();
            }catch (Exception e){
                e.printStackTrace();
            }
            startActivity(new Intent(context, LoginActivity.class));
            //Toast.makeText(context,"Not Implemented...!",Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}