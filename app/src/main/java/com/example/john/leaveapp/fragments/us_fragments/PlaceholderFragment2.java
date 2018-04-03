package com.example.john.leaveapp.fragments.us_fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.db_operartions.Accompanied;
import com.example.john.leaveapp.db_operartions.Leave;
import com.example.john.leaveapp.db_operartions.Apply;
import com.example.john.leaveapp.db_operartions.Staff;
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
import static com.example.john.leaveapp.utils.Constants.config.ENTITLEMENT;
import static com.example.john.leaveapp.utils.Constants.config.HOST_URL;
import static com.example.john.leaveapp.utils.Constants.config.LEAVEDUE_FROM;
import static com.example.john.leaveapp.utils.Constants.config.LEAVEDUE_TO;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_FROM;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_NOW;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_TO;
import static com.example.john.leaveapp.utils.Constants.config.SIGNATURE;
import static com.example.john.leaveapp.utils.Constants.config.URL_SAVE_LEAVE;

/**
 * Created by john on 2/28/18.
 */

public class PlaceholderFragment2 extends Fragment implements SelectFragment.OnFragmentInteractionListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";


    ImageView img;
    private static TextView textView;
    private Activity activity;
    private static final String TAG = "PlaceholderFragment2";

    private EditText input_fullname,input_department,input_designation,input_salary,input_assumption,input_promotion,
            input_last_leave,input_days_last_leave,input_balance,input_entitlement,input_leave_now,input_from,input_to,
            input_balance_outstanding,leave_due,input_due_to;
    private IntlPhoneInput input_phone;
    private Button button;
    int[] bgs = new int[]{R.drawable.ic_flight_24dp, R.drawable.ic_mail_24dp, R.drawable.ic_explore_24dp};
    public PlaceholderFragment2() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment2 newInstance(int sectionNumber) {
        PlaceholderFragment2 fragment = new PlaceholderFragment2();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.anual_form_layout, container, false);
        textView =  (TextView) rootView.findViewById(R.id.leaveType);
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
        input_leave_now = (EditText) rootView.findViewById(R.id.input_leave_now);
        input_from = (EditText) rootView.findViewById(R.id.input_from);
        input_to = (EditText) rootView.findViewById(R.id.input_to);
        input_balance_outstanding = (EditText) rootView.findViewById(R.id.input_balance_outstanding);
        leave_due = (EditText) rootView.findViewById(R.id.leave_due);
        input_due_to = (EditText) rootView.findViewById(R.id.input_due_to);
        input_phone = (IntlPhoneInput) rootView.findViewById(R.id.my_phone_input);
        button = (Button) rootView.findViewById(R.id.add_btn);
        Button submit_btn = (Button) rootView.findViewById(R.id.submit_btn);
        try{
            input_fullname.setText(new UserDetails(activity).getfname()+" "+new UserDetails(activity).getlname());
            int id = new UserDetails(activity).getid();
            String department = new Staff(activity).getDepartment(id);
            input_department.setText(department);
            input_designation.setText(new UserDetails(activity).getrole());
            input_salary.setText(new UserDetails(activity).getsalary());
            input_phone.setNumber(new UserDetails(activity).getphone());

            input_assumption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_assumption);
                    newFragment.show(getFragmentManager(), "datePicker");
                }
            });
            input_promotion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_promotion);
                    newFragment.show(getFragmentManager(), "datePicker");
                }
            });
            input_days_last_leave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_days_last_leave);
                    newFragment.show(getFragmentManager(), "datePicker");
                }
            });
            input_last_leave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_last_leave);
                    newFragment.show(getFragmentManager(), "datePicker");
                }
            });

            input_leave_now.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_leave_now);

                    newFragment.show(getFragmentManager(), "datePicker");
                }
            });

            input_from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_from);

                    newFragment.show(getFragmentManager(), "datePicker");
                }
            });

            input_to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_to);

                    newFragment.show(getFragmentManager(), "datePicker");
                }
            });

            leave_due.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(leave_due);
                    newFragment.show(getFragmentManager(), "datePicker");
                }
            });

            input_due_to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new PickDate(input_due_to);

                    newFragment.show(getFragmentManager(), "datePicker");
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
                    String entitlement = input_entitlement.getText().toString().trim();
                    String dbalance = input_balance.getText().toString().trim();
                    String lnow = input_leave_now.getText().toString().trim();
                    String lfrom = input_from.getText().toString().trim();
                    String lto = input_to.getText().toString().trim();
                    String outstanding = input_balance_outstanding.getText().toString().trim();
                    String ldfrom = leave_due.getText().toString().trim();
                    String ldto = input_due_to.getText().toString().trim();

                    if (!assumption.equals("") && !promotion.equals("") && !dtaken.equals("") && !dreturn.equals("") &&
                             !entitlement.equals("") && !dbalance.equals("") && !lnow.equals("") && !lfrom.equals("") &&

                             !lto.equals("") && !outstanding.equals("") && !ldfrom.equals("") && !ldto.equals("")){

                        try{
                            send(assumption,promotion,dreturn,dbalance,dtaken,entitlement,lnow,lfrom,lto,outstanding,ldfrom,ldto,1);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }else {
                        Toast.makeText(activity,"Empty field detected..!",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addAccompanied();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView;
    }


    public void send(final String assumption, final String promotion, final String dreturn, final String dbance, final String dtaken, final String entitlement, final String lnow, final String lfrom, final String lto,
                     final String outstanding, final String ldfrom, final String ldto, final int signature){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_LEAVE,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, "Results: " + response);

                            String[] splits = response.split("/");
                            int status = 0, id = 0;

                            if (splits[0].equals("Success")) {
                                status = 1;
                                id = Integer.parseInt(splits[1]);

                            }
                            String message =new Leave(activity). save(id,assumption,promotion,dreturn,dbance,dtaken,entitlement,lnow,lfrom,lto,outstanding,ldfrom,ldto,signature,status);
                            Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
                            if (message.equals("Leave Details saved!")){
                                String date = DateTime.getCurrentDate();
                                String time = DateTime.getCurrentTime();
                                new Apply(activity).send(id,1,0,0,0,date,time,new UserDetails(activity).getid(), ldfrom, ldto);
                                activity.finish();
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
                params.put(DAYS_TAKEN,dtaken);
                params.put(BALANCE_TAKEN,dbance);
                params.put(ENTITLEMENT,entitlement);
                params.put(LEAVE_NOW,lnow);
                params.put(LEAVE_FROM,lfrom);
                params.put(LEAVE_TO,lto);
                params.put(BALANCE_OUTSTANDING,outstanding);
                params.put(LEAVEDUE_FROM,ldfrom);
                params.put(LEAVEDUE_TO,ldto);
                params.put(SIGNATURE, String.valueOf(signature));

                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


    public static void updateText(String radioValue) {
        Log.e("PagerActivity_Tag2","Value: "+radioValue);
        textView.setText(radioValue);
    }
    @Override
    public void onFragmentInteraction(String name, String desc) {
        Log.e("Tag","Name:"+name+"\tDesc: "+desc);
        textView.setText(name);
    }

    private void addAccompanied(){
        final AlertDialog dialog;
        try{
            final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
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
                        int staff_id = new UserDetails(activity).getid();
                        if (!name.equals("") && !age.equals("")) {
                            String message = new Accompanied(activity).save(name, type, Integer.parseInt(age), staff_id);
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

    private void setSpinner(Spinner spinner){
        List<String> list = new ArrayList<>();
        try{
            String gender = new UserDetails(activity).getgender();
            if (gender.equals("Male")){
                list.add("Wife");
            }else {
                list.add("Husband");
            }
            list.add("Children - Name");

            //TODO:: ... Setting the spinner....!!!!..
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity,
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
            int staff_id = new UserDetails(activity).getid();
            Cursor cursor = new Accompanied(activity).selectAll(staff_id);
            if (cursor.moveToFirst()){
                do {
                    list.add("NAME: "+cursor.getString(cursor.getColumnIndex(Constants.config.ACCOMPANIED_NAME))+"  TYPE: "
                     +cursor.getString(cursor.getColumnIndex(Constants.config.ACCOMPANIED_TYPE))+"  AGE:"+
                     cursor.getInt(cursor.getColumnIndex(Constants.config.ACCOMPANIED_AGE)));
                }while (cursor.moveToNext());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    activity,
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

}

