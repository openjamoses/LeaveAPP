package com.example.john.leaveapp.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.john.leaveapp.R;
import com.example.john.leaveapp.activities.firebase.SendNotification;
import com.example.john.leaveapp.core.ReturnCursor;
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.db_operartions.Accompanied;
import com.example.john.leaveapp.db_operartions.Apply;
import com.example.john.leaveapp.db_operartions.Leave;
import com.example.john.leaveapp.db_operartions.Notications;
import com.example.john.leaveapp.db_operartions.Staff;
import com.example.john.leaveapp.fragments.us_fragments.PlaceholderFragment2;
import com.example.john.leaveapp.utils.Constants;
import com.example.john.leaveapp.utils.DateTime;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static com.example.john.leaveapp.utils.Constants.config.BALANCE_OUTSTANDING;
import static com.example.john.leaveapp.utils.Constants.config.BALANCE_TAKEN;
import static com.example.john.leaveapp.utils.Constants.config.DATE_ASSUMPTION;
import static com.example.john.leaveapp.utils.Constants.config.DATE_PROMOTION;
import static com.example.john.leaveapp.utils.Constants.config.DATE_RETURN;
import static com.example.john.leaveapp.utils.Constants.config.DAYS_TAKEN;
import static com.example.john.leaveapp.utils.Constants.config.DEPARTMENT_ID;
import static com.example.john.leaveapp.utils.Constants.config.ENTITLEMENT;
import static com.example.john.leaveapp.utils.Constants.config.HOST_URL;
import static com.example.john.leaveapp.utils.Constants.config.LEAVEDUE_FROM;
import static com.example.john.leaveapp.utils.Constants.config.LEAVEDUE_TO;
import static com.example.john.leaveapp.utils.Constants.config.LEAVETYPE_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_FROM;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_NOW;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_STATUS;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_TO;
import static com.example.john.leaveapp.utils.Constants.config.SIGNATURE;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_LEAVE_TYPE;
import static com.example.john.leaveapp.utils.Constants.config.URL_SAVE_LEAVE;

/**
 * Created by john on 4/9/18.
 */

public  class ApplyActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    private Spinner spinner;
    //private LinearLayout linearLayout;
    private Context context = this;
    private List<String> list;
    private List<Integer> list_id;
    List<Integer> days = new ArrayList<>();
    List<String> days_string = new ArrayList<>();

    private  String date_to = "";
    private String tot_days = "";

    private EditText input_fullname,input_department,input_designation,input_salary,input_assumption,input_promotion,
            input_last_leave,input_days_last_leave,input_balance,input_entitlement,input_from,input_to;
    private IntlPhoneInput input_phone;
    private Button button;
    private Spinner input_leave_now;
    int ent = 0;
    int bal = 0;
    int total = 0;
    Button submit_btn;
    private static final String TAG = "ApplyActivity_DEl";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_layout);

        spinner = (Spinner) findViewById(R.id.select_spinner);
        //linearLayout = (LinearLayout) findViewById(R.id.layout);

        spinner.setOnItemSelectedListener(this);
        setValue();
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    private void setValue() {
        String query = " SELECT * FROM "+TABLE_LEAVE_TYPE+"";
        list = new ArrayList<>();
        list.add("Select Leave-Type ");
        list_id = new ArrayList<>();
        list_id.add(-1);
        try{
            Cursor cursor = ReturnCursor.getCursor(query,context);
            if (cursor.moveToFirst()){
                do {
                    list.add(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVETYPE_NAME)));
                    list_id.add(cursor.getInt(cursor.getColumnIndex(Constants.config.LEAVETYPE_ID)));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) adapterView;
        if (spinner.getId() == R.id.select_spinner) {
            String item_ = list.get(i);
            int id = list_id.get(i);
            if (id == 1){
                addLayout_annualleave(id);
            }else if (id == 2){
                addLayout_maternity(id);
            }else {
                remove_view();
                Toast.makeText(context, "Please Either Annual or Maternity", Toast.LENGTH_SHORT).show();
            }

        }
       // try {
          if (spinner.getId() == R.id.input_leave_now) {
                String item_ = days_string.get(i);

                int dy = days.get(days_string.indexOf(item_));
                bal = ent - dy;
                try {
                    if (input_balance != null) {
                        input_balance.setText(bal + " Days");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                if (bal < 0){
                    Toast.makeText(context, "Please select valid days", Toast.LENGTH_SHORT).show();
                    submit_btn.setVisibility(View.GONE);
                }else {
                    submit_btn.setVisibility(View.VISIBLE);
                }

            }
    }

    private void addLayout_annualleave(final int leave_id){

        total = 0;
        days = new ArrayList<>();
        days_string = new ArrayList<>();

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        View rootView = LayoutInflater.from(this).inflate(R.layout.anual_form_layout, layout, false);
        layout.removeAllViews();
        layout.addView(rootView);

        input_fullname = (EditText) rootView.findViewById(R.id.input_fullname);
        input_department = (EditText) rootView.findViewById(R.id.input_department);
        input_designation = (EditText) rootView.findViewById(R.id.input_designation);
        input_salary = (EditText) rootView.findViewById(R.id.input_salary);
        input_assumption = (EditText) rootView.findViewById(R.id.input_assumption);
        input_promotion = (EditText) rootView.findViewById(R.id.input_promotion);
        input_last_leave = (EditText) rootView.findViewById(R.id.input_last_leave);
        input_days_last_leave = (EditText) rootView.findViewById(R.id.input_days_last_leave);
        input_balance = (EditText) rootView.findViewById(R.id.input_balance);
        input_entitlement = (EditText) rootView.findViewById(R.id.input_entitlement);
        input_leave_now = (Spinner) rootView.findViewById(R.id.input_leave_now);
        input_from = (EditText) rootView.findViewById(R.id.input_from);
        input_to = (EditText) rootView.findViewById(R.id.input_to);
;
        input_phone = (IntlPhoneInput) rootView.findViewById(R.id.my_phone_input);
        button = (Button) rootView.findViewById(R.id.add_btn);
        submit_btn = (Button) rootView.findViewById(R.id.submit_btn);

        input_department.setText(new UserDetails(context).getDepartment());
        //int apply_status = 0;
        try{
            String query = "SELECT * FROM "+Constants.config.TABLE_LEAVE_TYPE+" WHERE "+Constants.config.LEAVETYPE_ID+" = '"+leave_id+"'";
            Cursor cursor = ReturnCursor.getCursor(query, context);
            if (cursor.moveToFirst()){
                do {
                    input_entitlement.setText(cursor.getString(cursor.getColumnIndex(Constants.config.ENTITLEMENT))+" Days");
                    input_entitlement.setEnabled(false);
                    ent = cursor.getInt(cursor.getColumnIndex(Constants.config.ENTITLEMENT));
                    ///TODO:::
                }while (cursor.moveToNext());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            int state = -1;
            String query1 = "SELECT * FROM "+Constants.config.TABLE_APPLY+" a, "+Constants.config.TABLE_LEAVE+" l WHERE " +
                    " a."+Constants.config.STAFF_ID+" = '"+new UserDetails(context).getid()+"'" +
                    " AND a. "+Constants.config.LEAVE_ID+" = l.'"+LEAVE_ID+"' AND a."+Constants.config.LEAVE_STATUS+" != '"+state+"' AND l."+Constants.config.LEAVETYPE_ID+" = '"+leave_id+"' ";
            Cursor cursor = ReturnCursor.getCursor(query1,context);
            if (cursor.moveToFirst()){
                do {
                    total += cursor.getInt(cursor.getColumnIndex(Constants.config.LEAVE_NOW));
                }while (cursor.moveToNext());
            }


            bal = ent - total;
            input_days_last_leave.setText(total+" days");
            input_days_last_leave.setEnabled(false);
            input_balance.setText(bal+" days");
            //input_balance.setEnabled(false);
            if (total >= ent){
                Toast.makeText(context, "Number of days exceed the total entitlement", Toast.LENGTH_SHORT).show();
                //apply_status = 1;
                submit_btn.setVisibility(View.GONE);
            }else {
                //apply_status = 0;
                submit_btn.setVisibility(View.VISIBLE);
            }
            for (int i=1; i<=bal; i++){
                days.add(i);
                if (i ==1){
                    days_string.add(i+"  Day");
                }else {
                    days_string.add(i+"  Days");
                }

                //input_balance.addChildrenForAccessibility();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        date_to = input_from.getText().toString().trim();
        try {
            if (!date_to.equals("")){
                String tot_days = DateTime.addDays(date_to,1);
                input_to.setText(tot_days);
                input_to.setEnabled(false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            input_from.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    date_to = input_from.getText().toString().trim();
                    //todo::.. you can call or do what you want with your EditText here
                    if (!input_from.equals("")){
                       try{
                           String dateS = input_leave_now.getSelectedItem().toString().trim();
                           int dyy = days.get(days_string.indexOf(dateS));
                           tot_days = DateTime.addDays(date_to,dyy);
                           input_to.setText(tot_days);

                       }catch (Exception e){
                           e.printStackTrace();
                       }
                    }
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });

        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            input_fullname.setText(new UserDetails(context).getfname()+" "+new UserDetails(context).getlname());
            //int id_ = new UserDetails(context).getid();
            input_designation.setText(new UserDetails(context).getrole());
            input_salary.setText(new UserDetails(context).getsalary());
            input_phone.setNumber(new UserDetails(context).getphone());

            input_assumption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_assumption);
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
            });
            input_promotion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_promotion);
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
            });
            input_days_last_leave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                }
            });
            input_last_leave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_last_leave);
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
            });


            input_from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_from);

                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
            });

            input_to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_to);

                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
            });



            submit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // String assumption, String promotion, int dreturn,int dbance,String dtaken, String entitlement,String lnow,String lfrom,String lto,
                    //         String outstanding, String ldfrom, String ldto,int signature
                    String assumption = input_assumption.getText().toString().trim();
                    String promotion = input_promotion.getText().toString().trim();
                    String dtaken = input_days_last_leave.getText().toString().trim();
                    String dreturn = input_last_leave.getText().toString().trim();

                    String lnow_ = input_leave_now.getSelectedItem().toString().trim();
                    int lnow= 0;
                    if (days_string.contains(lnow_)){
                        lnow = days.get(days_string.indexOf(lnow_));
                    }
                    //int lnow = days.get(days_string.indexOf(lnow_));

                    String ldfrom = input_from.getText().toString().trim();
                    String ldto = input_to.getText().toString().trim();
                    int tot_bal = ent - (lnow+total);

                    if (tot_bal >=0) {
                        if (!assumption.equals("") && !promotion.equals("") && !ldfrom.equals("") && !ldto.equals("")) {

                            try {
                                send(assumption,promotion,dreturn,tot_bal,total,lnow,ldfrom,ldto,"","","",1, leave_id);
                                //send(assumption, promotion, dreturn, tot_bal, total, lnow, ldfrom, ldto, "", "", "", 1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(context, "Empty field detected..!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(context, "You exceeded the entitlement days,, ..!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   addAccompanied();
                }
            });

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, days_string);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            input_leave_now.setAdapter(dataAdapter);


        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void addLayout_maternity(final int leave_id){
        total = 0;
        days = new ArrayList<>();
        days_string = new ArrayList<>();

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        View rootView = LayoutInflater.from(this).inflate(R.layout.maternity_leave_form, layout, false);
        layout.removeAllViews();
        layout.addView(rootView);

        input_fullname = (EditText) rootView.findViewById(R.id.input_fullname);
        input_department = (EditText) rootView.findViewById(R.id.input_department);
        input_designation = (EditText) rootView.findViewById(R.id.input_designation);
        input_salary = (EditText) rootView.findViewById(R.id.input_salary);
        input_assumption = (EditText) rootView.findViewById(R.id.input_assumption);
        input_promotion = (EditText) rootView.findViewById(R.id.input_promotion);
        input_last_leave = (EditText) rootView.findViewById(R.id.input_last_leave);
        input_days_last_leave = (EditText) rootView.findViewById(R.id.input_days_last_leave);
        input_balance = (EditText) rootView.findViewById(R.id.input_balance);
        input_entitlement = (EditText) rootView.findViewById(R.id.input_entitlement);
        input_leave_now = (Spinner) rootView.findViewById(R.id.input_leave_now);
        input_from = (EditText) rootView.findViewById(R.id.input_from);
        input_to = (EditText) rootView.findViewById(R.id.input_to);
        submit_btn = (Button) rootView.findViewById(R.id.submit_btn);

        input_department.setText(new UserDetails(context).getDepartment());
        //int apply_status = 0;
        try{
            String query = "SELECT * FROM "+Constants.config.TABLE_LEAVE_TYPE+" WHERE "+Constants.config.LEAVETYPE_ID+" = '"+leave_id+"'";
            Cursor cursor = ReturnCursor.getCursor(query, context);
            if (cursor.moveToFirst()){
                do {
                    input_entitlement.setText(cursor.getString(cursor.getColumnIndex(Constants.config.ENTITLEMENT))+" Days");
                    input_entitlement.setEnabled(false);
                    ent = cursor.getInt(cursor.getColumnIndex(Constants.config.ENTITLEMENT));
                    ///TODO:::
                }while (cursor.moveToNext());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            int state = -1;
            String query1 = "SELECT * FROM "+Constants.config.TABLE_APPLY+" a, "+Constants.config.TABLE_LEAVE+" l WHERE " +
                    " a."+Constants.config.STAFF_ID+" = '"+new UserDetails(context).getid()+"'" +
                    " AND a. "+Constants.config.LEAVE_ID+" = l.'"+LEAVE_ID+"' AND a."+Constants.config.LEAVE_STATUS+" != '"+state+"' AND l."+Constants.config.LEAVETYPE_ID+" = '"+leave_id+"'  ";
            Cursor cursor = ReturnCursor.getCursor(query1,context);
            if (cursor.moveToFirst()){
                do {
                    total += cursor.getInt(cursor.getColumnIndex(Constants.config.LEAVE_NOW));
                }while (cursor.moveToNext());
            }


            bal = ent - total;
            input_days_last_leave.setText(total+" days");
            input_days_last_leave.setEnabled(false);
            input_balance.setText(bal+" days");
            //input_balance.setEnabled(false);
            if (total >= ent){
                Toast.makeText(context, "Number of days exceed the total entitlement", Toast.LENGTH_SHORT).show();
                //apply_status = 1;
                submit_btn.setVisibility(View.GONE);
            }else {
                //apply_status = 0;
                submit_btn.setVisibility(View.VISIBLE);
            }
            for (int i=1; i<=bal; i++){
                days.add(i);
                if (i ==1){
                    days_string.add(i+"  Day");
                }else {
                    days_string.add(i+"  Days");
                }

                //input_balance.addChildrenForAccessibility();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        date_to = input_from.getText().toString().trim();
        try {
            if (!date_to.equals("")){
                String tot_days = DateTime.addDays(date_to,1);
                input_to.setText(tot_days);
                input_to.setEnabled(false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            input_from.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    date_to = input_from.getText().toString().trim();
                    //todo::.. you can call or do what you want with your EditText here
                    if (!input_from.equals("")){
                        try{
                            String dateS = input_leave_now.getSelectedItem().toString().trim();
                            int dyy = days.get(days_string.indexOf(dateS));
                            tot_days = DateTime.addDays(date_to,dyy);
                            input_to.setText(tot_days);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });

        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            input_fullname.setText(new UserDetails(context).getfname()+" "+new UserDetails(context).getlname());
            //int id_ = new UserDetails(context).getid();
            input_designation.setText(new UserDetails(context).getrole());
            input_salary.setText(new UserDetails(context).getsalary());
            //input_phone.setNumber(new UserDetails(context).getphone());

            input_assumption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_assumption);
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
            });
            input_promotion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_promotion);
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
            });
            input_days_last_leave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                }
            });
            input_last_leave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_last_leave);
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
            });


            input_from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_from);

                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
            });

            input_to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_to);

                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
            });



            submit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // String assumption, String promotion, int dreturn,int dbance,String dtaken, String entitlement,String lnow,String lfrom,String lto,
                    //         String outstanding, String ldfrom, String ldto,int signature
                    String assumption = input_assumption.getText().toString().trim();
                    String promotion = input_promotion.getText().toString().trim();
                    String dtaken = input_days_last_leave.getText().toString().trim();
                    String dreturn = input_last_leave.getText().toString().trim();

                    String lnow_ = input_leave_now.getSelectedItem().toString().trim();
                    int lnow= 0;
                    if (days_string.contains(lnow_)){
                        lnow = days.get(days_string.indexOf(lnow_));
                    }
                    //int lnow = days.get(days_string.indexOf(lnow_));

                    String ldfrom = input_from.getText().toString().trim();
                    String ldto = input_to.getText().toString().trim();
                    int tot_bal = ent - (lnow+total);

                    if (tot_bal >=0) {
                        if (!assumption.equals("") && !promotion.equals("") && !ldfrom.equals("") && !ldto.equals("")) {

                            try {
                                send(assumption,promotion,dreturn,tot_bal,total,lnow,ldfrom,ldto,"","","",1, leave_id);
                                //send(assumption, promotion, dreturn, tot_bal, total, lnow, ldfrom, ldto, "", "", "", 1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(context, "Empty field detected..!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(context, "You exceeded the entitlement days,, ..!", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, days_string);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            input_leave_now.setAdapter(dataAdapter);


        }catch (Exception e){
            e.printStackTrace();
        }


    }


    private void addAccompanied(){
        final AlertDialog dialog;
        try{
            final AlertDialog.Builder alert = new AlertDialog.Builder(context);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.accompanied_dialog, null);
            // this is set the view from XML inside AlertDialog
            alert.setView(view);
            Button cancel_btn = (Button) view.findViewById(R.id.cancel_btn);
            Button continue_btn = (Button) view.findViewById(R.id.ok_btn);
            final EditText input_name = (EditText) view.findViewById(R.id.input_name);
            final EditText input_age = (EditText) view.findViewById(R.id.input_age);
            final Spinner spinner = (Spinner) view.findViewById(R.id.accompanied_spinner);
            final ListView listView = (ListView) view.findViewById(R.id.add_listView);
            // disallow cancel of AlertDialog on click of back button and outside touch
            alert.setCancelable(false);

            dialog = alert.create();
            dialog.show();

            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            setListView(listView);
            setSpinner(spinner);
            continue_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String name = input_name.getText().toString().trim();
                        String age = input_age.getText().toString().trim();
                        String type = spinner.getSelectedItem().toString().trim();
                        int staff_id = new UserDetails(context).getid();
                        if (!name.equals("") && !age.equals("")) {
                            String message = new Accompanied(context).save(name, type, Integer.parseInt(age), staff_id);
                            if (message.equals("Accompanied Details saved!")) {
                                setListView(listView);
                                input_name.setText("");
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void remove_view(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        layout.removeAllViews();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setSpinner(Spinner spinner){
        List<String> list = new ArrayList<>();
        try{
            String gender = new UserDetails(context).getgender();
            if (gender.equals("Male")){
                list.add("Wife");
            }else {
                list.add("Husband");
            }
            list.add("Children - Name");
            //TODO:: ... Setting the spinner....!!!!..
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setListView(ListView listView){
        List<String> list = new ArrayList<>();
        try{
            int staff_id = new UserDetails(context).getid();
            Cursor cursor = new Accompanied(context).selectAll(staff_id);
            if (cursor.moveToFirst()){
                do {
                    list.add("NAME: "+cursor.getString(cursor.getColumnIndex(Constants.config.ACCOMPANIED_NAME))+"  TYPE: "
                            +cursor.getString(cursor.getColumnIndex(Constants.config.ACCOMPANIED_TYPE))+"  AGE:"+
                            cursor.getInt(cursor.getColumnIndex(Constants.config.ACCOMPANIED_AGE)));
                }while (cursor.moveToNext());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    context,
                    R.layout.simple_list_item,
                    list );
            listView.setAdapter(arrayAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static class PickDate extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        EditText editText;
        public PickDate(EditText editText){
            this.editText = editText;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR,2018);
            c.set(Calendar.MONTH,02);
            c.set(Calendar.DAY_OF_MONTH,20);

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            //Button button= (Button) getActivity().findViewById(R.id.dob_btn);
            editText.setText(+view.getYear()+"-"+view.getMonth()+"-"+view.getDayOfMonth());
        }
    }




    public void send(final String assumption, final String promotion, final String dreturn, final int dbance, final int dtaken, final int lnow, final String lfrom, final String lto,
                     final String outstanding, final String ldfrom, final String ldto, final int signature, final int leave_type_id){

        ProgressDialog progressDialog = null;
        try{
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("please wait...");
        }catch (Exception e){
            e.printStackTrace();
        }
        final ProgressDialog finalProgressDialog = progressDialog;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_LEAVE,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, "Results: " + response);

                            String[] splits = response.split("/");
                            int status = 0, id = 0;
                            int staff_id = 0;
                            if (splits[0].equals("Success")) {
                                status = 1;
                                id = Integer.parseInt(splits[1]);
                                String names = new UserDetails(context).getfname()+" "+new UserDetails(context).getlname();
                                String img_url = "";

                                String role = "HOD";
                                String userType = new UserDetails(context).getUser_type();
                                String query = "SELECT * FROM "+Constants.config.TABLE_STAFF+" WHERE "+Constants.config.STAFF_ROLE+" = '"+role+"' AND "+DEPARTMENT_ID+" = '"+new UserDetails(context).getDepartment_id()+"' ";

                                if (userType.equals(Constants.config.USER_US)){
                                    query = "SELECT * FROM "+Constants.config.TABLE_STAFF+" WHERE "+Constants.config.STAFF_ROLE+" = '"+role+"'  ORDER BY "+Constants.config.STAFF_ID+" ASC LIMIT 1";
                                }
                                try{
                                    Cursor cursor = ReturnCursor.getCursor(query,context);
                                    if (cursor.moveToFirst()){
                                        do {
                                            staff_id = cursor.getInt(cursor.getColumnIndex(Constants.config.STAFF_ID));
                                        }while (cursor.moveToNext());
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                new SendNotification(context).sendSinglePush(names+":users:00000","Incomming leave from "+names,img_url,staff_id);
                                //new Notications(context).send(staff_id,);
                            }

                            String message = new Leave(context).save(id,assumption,promotion,dreturn,bal,dtaken,lnow,lfrom,lto,outstanding,ldfrom,ldto,signature,leave_type_id,staff_id);
                            //String message =new Leave(context). save(id,assumption,promotion,dreturn,dbance,dtaken,lnow,lfrom,lto,outstanding,ldfrom,ldto,signature,status);
                            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                            if (message.equals("Leave Details saved!")){
                                String date = DateTime.getCurrentDate();
                                String time = DateTime.getCurrentTime();
                                new Apply(context).send(id,0,0,0,date,time,new UserDetails(context).getid(), ldfrom, ldto);
                                finish();
                            }




                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        try{
                            if (finalProgressDialog != null){
                                if (finalProgressDialog.isShowing()){
                                    finalProgressDialog.dismiss();
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        Log.e(TAG,response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        try {
                            Log.e(TAG, ""+volleyError.getMessage());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // int status = 1;
                Map<String, String> params = new Hashtable<String, String>();

                params.put(DATE_ASSUMPTION,assumption);
                params.put(DATE_PROMOTION,promotion);
                params.put(DATE_RETURN,dreturn);
                params.put(DAYS_TAKEN, String.valueOf(dtaken));
                params.put(BALANCE_TAKEN, String.valueOf(dbance));
                params.put(LEAVE_NOW, String.valueOf(lnow));
                params.put(LEAVE_FROM,lfrom);
                params.put(LEAVE_TO,lto);
                params.put(BALANCE_OUTSTANDING,outstanding);
                params.put(LEAVEDUE_FROM,ldfrom);
                params.put(LEAVEDUE_TO,ldto);
                params.put(LEAVETYPE_ID, String.valueOf(leave_type_id));
                params.put(SIGNATURE, String.valueOf(signature));
                //params.put(DEPARTMENT_ID, String.valueOf(department));
                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
}
