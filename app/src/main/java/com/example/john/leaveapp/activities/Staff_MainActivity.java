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
import com.example.john.leaveapp.activities.us_activities.ApplyActivity_DEl;
import com.example.john.leaveapp.core.BaseApplication;
import com.example.john.leaveapp.core.ReturnCursor;
import com.example.john.leaveapp.core.SessionManager;
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.utils.Constants;

/**
 * Created by john on 2/28/18.
 */

public class Staff_MainActivity extends AppCompatActivity {
    private Context context = this;
    private Button btn_history,btn_apply,btn_profile,btn_notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        TextView toolbar_subtitle = (TextView) findViewById(R.id.toolbar_subtitle);
        toolbar_subtitle.setText("Staff Platform");

        btn_history = (Button) findViewById(R.id.btn_history);
        btn_apply = (Button) findViewById(R.id.btn_apply);
        btn_profile = (Button) findViewById(R.id.btn_profile);
        btn_notification = (Button) findViewById(R.id.btn_notification);

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ApplyActivity.class));
            }
        });
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, YourLeaveHistory.class);
                intent.putExtra("mode","single");
                startActivity(intent);
            }
        });
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ProfileActivity.class));
            }
        });
        //TODO:::
        BaseApplication.deleteCache(context);
        notification();

        btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,NotificationActivity.class));
            }
        });
    }

    private void notification(){
        try{
            int count = 0;
            String query = "SELECT * FROM "+ Constants.config.TABLE_NOTICATION+" " +
                    "WHERE "+Constants.config.STAFF_ID+" = '"+new UserDetails(context).getid()+"' ORDER BY "+Constants.config.NOTICATIONID+" DESC";
            Cursor cursor = ReturnCursor.getCursor(query,context);
            if (cursor.moveToFirst()){
                do {
                    count ++;
                }while (cursor.moveToNext());
            }
            if (count > 0){
                btn_notification.setTextColor(getResources().getColor(R.color.deep_orange));
                btn_notification.setText("( "+count+" ) Notifications");
            }else {
                btn_notification.setTextColor(getResources().getColor(android.R.color.black));
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