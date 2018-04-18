package com.example.john.leaveapp.fragments.us_fragments;

/**
 * Created by john on 7/28/17.
 */

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.activities.ApplyActivity;
import com.example.john.leaveapp.activities.Entry_LeaveType;
import com.example.john.leaveapp.activities.LeaveHistoryActivity;
import com.example.john.leaveapp.activities.NotificationActivity;
import com.example.john.leaveapp.activities.SignupActivity;
import com.example.john.leaveapp.activities.UniversitySettings;
import com.example.john.leaveapp.activities.YourLeaveHistory;
import com.example.john.leaveapp.core.ReturnCursor;
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.utils.Constants;

public class US_DFragment extends Fragment {

    Activity activity ;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.us_dashboard, container, false);
        Button btn_apply = (Button) view.findViewById(R.id.btn_apply);
        Button history_btn = (Button) view.findViewById(R.id.btn_incoming);
        Button btn_university = (Button) view.findViewById(R.id.btn_university);
        Button register_btn = (Button) view.findViewById(R.id.register_btn);
        Button btn_your = (Button) view.findViewById(R.id.btn_your);
        Button btn_type = (Button) view.findViewById(R.id.btn_type);
        Button btn_notification = (Button) view.findViewById(R.id.btn_notification);
        notification(btn_notification);
        btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity,NotificationActivity.class));
            }
        });

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, ApplyActivity.class));
            }
        });
        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, LeaveHistoryActivity.class);
                intent.putExtra("mode","us");
                startActivity(intent);

            }
        });
        btn_university.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, UniversitySettings.class));
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, SignupActivity.class));
            }
        });
        btn_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, Entry_LeaveType.class));
            }
        });

        btn_your.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, YourLeaveHistory.class));
            }
        });

        return view;
    }

    private void notification(Button btn_notification){
        try{
            int count = 0;
            String query = "SELECT * FROM "+ Constants.config.TABLE_NOTICATION+" " +
                    "WHERE "+Constants.config.STAFF_ID+" = '"+new UserDetails(activity).getid()+"' ORDER BY "+Constants.config.NOTICATIONID+" DESC";
            Cursor cursor = ReturnCursor.getCursor(query,activity);
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


}
