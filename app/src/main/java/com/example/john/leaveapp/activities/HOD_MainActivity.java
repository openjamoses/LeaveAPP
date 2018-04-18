package com.example.john.leaveapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.activities.pdf_itext.GenrateLeave;
import com.example.john.leaveapp.activities.pdf_itext.PdfMain;
import com.example.john.leaveapp.core.BaseApplication;
import com.example.john.leaveapp.core.ReturnCursor;
import com.example.john.leaveapp.core.SessionManager;
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.db_operartions.Apply;
import com.example.john.leaveapp.db_operartions.DBController;
import com.example.john.leaveapp.db_operartions.Notications;
import com.example.john.leaveapp.utils.Constants;
import com.example.john.leaveapp.utils.DateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.john.leaveapp.utils.Constants.config.APPLY_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVETYPE_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_ID;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ID;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_LEAVE_TYPE;
import static com.example.john.leaveapp.utils.Constants.config.URL_QUERY;

/**
 * Created by john on 2/28/18.
 */

public class HOD_MainActivity extends AppCompatActivity {
    private Context context = this;
    private Button btn_incoming,btn_apply,btn_recent,btn_others,btn_notification,btn_report;
    String path;
    File dir;
    File file;
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
                    //startActivity(new Intent(context, PdfMain.class));
                    showReport();
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

    private void showReport(){
        List<String> depList = new ArrayList<>();
        List<Integer> depIDList = new ArrayList<>();
        List<String> typeList = new ArrayList<>();
        List<Integer> typeIDList = new ArrayList<>();

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.genrate_report, null);
        // this is set the view from XML inside AlertDialog
        alert.setView(view);
        Button close_btn = (Button) view.findViewById(R.id.close_btn);
        final Button your_leave_history = (Button) view.findViewById(R.id.your_leave_history);
        Button all_staffs = (Button) view.findViewById(R.id.all_staffs);
        final Spinner department_spinner = (Spinner) view.findViewById(R.id.department_spinner);
        final Spinner type_spinner = (Spinner) view.findViewById(R.id.type_spinner);


        final AlertDialog dialog;
        try{
             // disallow cancel of AlertDialog on click of back button and outside touch

            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Leave/PDF Files";
            dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            all_staffs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    file = new File(dir, "leave PDF" + "all_staff" + ".pdf");
                    String query = "SELECT *  FROM" +
                            " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_LEAVE+" n, "+Constants.config.TABLE_STAFF+" s, "+Constants.config.TABLE_LEAVE_TYPE+" t, "+Constants.config.TABLE_DEPARTMENT+" d" +
                            " WHERE  s."+Constants.config.DEPARTMENT_ID+" = d."+Constants.config.DEPARTMENT_ID+"" +
                            "  AND a."+Constants.config.LEAVE_ID+" = n."+Constants.config.LEAVE_ID+"  AND t."+ LEAVETYPE_ID+" = n."+LEAVETYPE_ID+"  " +
                            " AND s."+Constants.config.STAFF_ID+" = a."+Constants.config.STAFF_ID+"  ORDER BY a."+Constants.config.DATE+" DESC";

                    GenrateLeave.createStaff(context,query,"MBARARA UNIVERSITY OF SCIENCE AND TECHNOLOGY", "STAFF LEAVE APPLICANTS",file);
                }
            });
            your_leave_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    file = new File(dir, "leave PDF" + "your_leave_history" + ".pdf");
                    String query = "SELECT *  FROM" +
                            " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_LEAVE+" n, "+TABLE_LEAVE_TYPE+" t WHERE " +
                            " a."+Constants.config.LEAVE_ID+" = n."+Constants.config.LEAVE_ID+"  " +
                            " AND t."+LEAVETYPE_ID+" = n."+LEAVETYPE_ID+" AND a."+Constants.config.STAFF_ID+" = '"+new UserDetails(context).getid()+"' " +
                            "ORDER BY a."+Constants.config.DATE+" DESC";

                    GenrateLeave.createYour(context,query,"MBARARA UNIVERSITY OF SCIENCE AND TECHNOLOGY", "YOUR LEAVE HISTORY",file);

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
                    Toast.makeText(context, item,Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String item = type_spinner.getSelectedItem().toString().trim();
                    Toast.makeText(context, item,Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        String query = "SELECT * FROM "+Constants.config.TABLE_DEPARTMENT+" ";
        try{
            Cursor cursor = ReturnCursor.getCursor(query,context);
            if (cursor.moveToFirst()){
                do {
                    depList.add(cursor.getString(cursor.getColumnIndex(Constants.config.DEPARTMENT_NAME)));
                    depIDList.add(cursor.getInt(cursor.getColumnIndex(Constants.config.DEPARTMENT_ID)));
                }while (cursor.moveToNext());
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, depList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            department_spinner.setAdapter(dataAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }


        String query2 = "SELECT * FROM "+Constants.config.TABLE_LEAVE_TYPE+" ";
        try{
            Cursor cursor = ReturnCursor.getCursor(query2,context);
            if (cursor.moveToFirst()){
                do {
                    typeList.add(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVETYPE_NAME)));
                    typeIDList.add(cursor.getInt(cursor.getColumnIndex(Constants.config.LEAVETYPE_ID)));
                }while (cursor.moveToNext());
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, typeList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            type_spinner.setAdapter(dataAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}