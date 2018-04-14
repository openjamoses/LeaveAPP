package com.example.john.leaveapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.activities.us_activities.ApplyActivity;
import com.example.john.leaveapp.core.BaseApplication;
import com.example.john.leaveapp.core.SessionManager;
import com.example.john.leaveapp.fragments.us_fragments.Report_Fragment;
import com.example.john.leaveapp.fragments.us_fragments.US_DFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2/28/18.
 */

public class HOD_MainActivity extends AppCompatActivity {
    private Context context = this;
    private Button btn_incoming,btn_apply,btn_recent,btn_others;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hod_main);

        try{
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
            setSupportActionBar(toolbar);
            TextView toolbar_subtitle = (TextView) findViewById(R.id.toolbar_subtitle);
            toolbar_subtitle.setText("HOD Platform");

            btn_incoming = (Button) findViewById(R.id.btn_incoming);
            btn_apply = (Button) findViewById(R.id.btn_apply);
            btn_recent = (Button) findViewById(R.id.btn_recent);
            btn_others = (Button) findViewById(R.id.btn_others);


            btn_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, ApplyActivity.class));
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
            BaseApplication.deleteCache(context);

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