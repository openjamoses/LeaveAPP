package com.example.john.leaveapp.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.core.ReturnCursor;
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.db_operartions.DBController;
import com.example.john.leaveapp.db_operartions.Notications;
import com.example.john.leaveapp.utils.Constants;

import static com.example.john.leaveapp.utils.Constants.config.APPLY_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVETYPE_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVETYPE_NAME;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_ID;
import static com.example.john.leaveapp.utils.Constants.config.NOTICATION_BODY;
import static com.example.john.leaveapp.utils.Constants.config.NOTIFICATION_DATE;
import static com.example.john.leaveapp.utils.Constants.config.OPERATION_NOTIFICATION;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ID;
import static com.example.john.leaveapp.utils.Constants.config.URL_QUERY;

/**
 * Created by john on 4/16/18.
 */

public class NotificationActivity extends AppCompatActivity{
    private LinearLayout layout;
    private TextView hide_text;
    private ScrollView scrollView;
    private Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        layout = (LinearLayout) findViewById(R.id.layout);
        hide_text = (TextView) findViewById(R.id.hide_text);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        notification();
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        update();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            //startActivity(new Intent(context, Mai));
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }


    private void update(){
        try{
            int status = 1;
            String updateQuery = "UPDATE notication_tb SET "+Constants.config.NOTIFICATION_STATUS+" = '"+status+"' WHERE  "+STAFF_ID+" = '"+new UserDetails(context).getid()+"' ";
            DBController.updateQuery(context,updateQuery,URL_QUERY, null);
            new Notications(context).edit(new UserDetails(context).getid(),status);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void notification(){
        try{
            int count = 0;
            String query = "SELECT * FROM "+ Constants.config.TABLE_NOTICATION+" n, "+Constants.config.TABLE_APPLY+" a, "+Constants.config.TABLE_LEAVE+" l, "+Constants.config.TABLE_LEAVE_TYPE+" t " +
                    "WHERE n."+APPLY_ID+" = a."+APPLY_ID+" AND a."+LEAVE_ID+" = l."+LEAVE_ID+" AND l."+LEAVETYPE_ID+" = t."+LEAVETYPE_ID+"" +
                    " AND  n."+Constants.config.STAFF_ID+" = '"+new UserDetails(context).getid()+"' ORDER BY "+Constants.config.NOTICATIONID+" DESC";
            Cursor cursor = ReturnCursor.getCursor(query,context);
            if (cursor.moveToFirst()){
                do {
                    count ++;
                    int type_id =cursor.getInt(cursor.getColumnIndex(Constants.config.LEAVETYPE_ID));
                    if (type_id ==1){
                        addLayoutAnnual(cursor.getString(cursor.getColumnIndex(LEAVETYPE_NAME)),
                                cursor.getString(cursor.getColumnIndex(NOTICATION_BODY)),
                                cursor.getString(cursor.getColumnIndex(NOTIFICATION_DATE)));
                    }else {
                        addLayoutRECIEVED(cursor.getString(cursor.getColumnIndex(LEAVETYPE_NAME)),
                                cursor.getString(cursor.getColumnIndex(NOTICATION_BODY)),
                                cursor.getString(cursor.getColumnIndex(NOTIFICATION_DATE)));
                    }

                }while (cursor.moveToNext());
            }
            if (count > 0){
                hide_text.setVisibility(View.GONE);
            }else {
                hide_text.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addLayoutAnnual(String name, String message, String date){
        try {
            View view = LayoutInflater.from(this).inflate(R.layout.anual_notification, layout, false);

            TextView textView = (TextView) view.findViewById(R.id.text_message_name);
            TextView textView1 = (TextView) view.findViewById(R.id.text_message_body);
            TextView textView2 = (TextView) view.findViewById(R.id.text_message_time);
            ImageView image_message_profile = (ImageView) view.findViewById(R.id.image_message_profile);
            textView.setText(name);
            textView1.setText(message);
            textView2.setText(date);

            layout.addView(view);
            scrollView.fullScroll(View.FOCUS_DOWN);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void addLayoutRECIEVED(String name, String message, String date){
        try {
            View view = LayoutInflater.from(this).inflate(R.layout.recieve_layout, layout, false);

            TextView textView = (TextView) view.findViewById(R.id.text_message_name);
            TextView textView1 = (TextView) view.findViewById(R.id.text_message_body);
            TextView textView2 = (TextView) view.findViewById(R.id.text_message_time);
            ImageView image_message_profile = (ImageView) view.findViewById(R.id.image_message_profile);
            textView.setText(name);
            textView1.setText(message);
            textView2.setText(date);

            layout.addView(view);
            scrollView.fullScroll(View.FOCUS_DOWN);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
