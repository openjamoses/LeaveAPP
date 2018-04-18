package com.example.john.leaveapp.fragments.us_fragments;

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
import com.example.john.leaveapp.activities.NotificationActivity;
import com.example.john.leaveapp.activities.pdf_itext.GenrateLeave;
import com.example.john.leaveapp.core.ReturnCursor;
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.utils.Constants;

import java.io.File;

import static com.example.john.leaveapp.utils.Constants.config.LEAVETYPE_ID;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_LEAVE_TYPE;

/**
 * Created by john on 2/24/18.
 */
public class Report_Fragment extends Fragment {
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
        View view = inflater.inflate(R.layout.us_report, container, false);
        Button generate_btn = (Button) view.findViewById(R.id.generate_btn);
        generate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReport();
            }
        });
        return view;
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

                    GenrateLeave.createStaff(activity,query,"MBARARA UNIVERSITY OF SCIENCE AND TECHNOLOGY", "YOUR LEAVE HISTORY",file);

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
