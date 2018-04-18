package com.example.john.leaveapp.fragments.us_fragments;

/**
 * Created by john on 7/28/17.
 */

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.activities.ApplyActivity;
import com.example.john.leaveapp.activities.Entry_LeaveType;
import com.example.john.leaveapp.activities.LeaveHistoryActivity;
import com.example.john.leaveapp.activities.NotificationActivity;
import com.example.john.leaveapp.activities.SignupActivity;
import com.example.john.leaveapp.activities.UniversitySettings;
import com.example.john.leaveapp.activities.YourLeaveHistory;
import com.example.john.leaveapp.activities.pdf_itext.GenrateLeave;
import com.example.john.leaveapp.core.ReturnCursor;
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.utils.Constants;

import java.io.File;

import static com.example.john.leaveapp.utils.Constants.config.LEAVETYPE_ID;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_LEAVE_TYPE;

public class US_DFragment extends Fragment {

    Activity activity ;
    String path;
    File dir;
    File file;
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


    private void showReport(){
        final AlertDialog dialog;
        try{
            final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            LayoutInflater inflater = LayoutInflater.from(activity);
            View view = inflater.inflate(R.layout.genrate_report, null);
            // this is set the view from XML inside AlertDialog
            alert.setView(view);
            Button close_btn = (Button) view.findViewById(R.id.close_btn);
            final Button your_leave_history = (Button) view.findViewById(R.id.your_leave_history);
            Button all_staffs = (Button) view.findViewById(R.id.close_btn);
            final Spinner department_spinner = (Spinner) view.findViewById(R.id.department_spinner);
            final Spinner type_spinner = (Spinner) view.findViewById(R.id.type_spinner);
            // disallow cancel of AlertDialog on click of back button and outside touch

            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Leave/PDF Files";
            dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            all_staffs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String query = "SELECT *  FROM" +
                            " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_LEAVE+" n, "+Constants.config.TABLE_STAFF+" s, "+Constants.config.TABLE_LEAVE_TYPE+" t, "+Constants.config.TABLE_DEPARTMENT+" d" +
                            " WHERE  s."+Constants.config.DEPARTMENT_ID+" = d."+Constants.config.DEPARTMENT_ID+"" +
                            "  AND a."+Constants.config.LEAVE_ID+" = n."+Constants.config.LEAVE_ID+"  AND t."+ LEAVETYPE_ID+" = n."+LEAVETYPE_ID+"  " +
                            " AND s."+Constants.config.STAFF_ID+" = a."+Constants.config.STAFF_ID+"  ORDER BY a."+Constants.config.DATE+" DESC";

                    GenrateLeave.createYour(activity,query,"MBARARA UNIVERSITY OF SCIENCE AND TECHNOLOGY", "YOUR LEAVE HISTORY",file);

                }
            });
            your_leave_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    file = new File(dir, "leave PDF" + "your_leave_history" + ".pdf");
                    String query = "SELECT *  FROM" +
                            " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_LEAVE+" n, "+TABLE_LEAVE_TYPE+" t WHERE " +
                            " a."+Constants.config.LEAVE_ID+" = n."+Constants.config.LEAVE_ID+"  " +
                            " AND t."+LEAVETYPE_ID+" = n."+LEAVETYPE_ID+" AND a."+Constants.config.STAFF_ID+" = '"+new UserDetails(activity).getid()+"' " +
                            "ORDER BY a."+Constants.config.DATE+" DESC";

                    GenrateLeave.createYour(activity,query,"MBARARA UNIVERSITY OF SCIENCE AND TECHNOLOGY", "YOUR LEAVE HISTORY",file);

                }
            });

            alert.setCancelable(false);
            dialog = alert.create();
            dialog.show();
            close_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            department_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String item = department_spinner.getSelectedItem().toString().trim();
                    Toast.makeText(activity, item,Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String item = type_spinner.getSelectedItem().toString().trim();
                    Toast.makeText(activity, item,Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
