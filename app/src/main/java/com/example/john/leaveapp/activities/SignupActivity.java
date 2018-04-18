package com.example.john.leaveapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.john.leaveapp.R;
import com.example.john.leaveapp.core.BaseApplication;
import com.example.john.leaveapp.core.ReturnCursor;
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.db_operartions.Departments;
import com.example.john.leaveapp.db_operartions.Notications;
import com.example.john.leaveapp.db_operartions.Staff;
import com.example.john.leaveapp.utils.Constants;
import com.example.john.leaveapp.utils.DateTime;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static com.example.john.leaveapp.utils.Constants.config.DATE;
import static com.example.john.leaveapp.utils.Constants.config.DEPARTMENT_ID;
import static com.example.john.leaveapp.utils.Constants.config.END_DATE;
import static com.example.john.leaveapp.utils.Constants.config.HOST_URL;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_STATUS;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_STATUS1;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_STATUS2;
import static com.example.john.leaveapp.utils.Constants.config.RESPONSIBILITY_ID;
import static com.example.john.leaveapp.utils.Constants.config.STAFFL_FNAME;
import static com.example.john.leaveapp.utils.Constants.config.STAFFL_LNAME;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_GENDER;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ID;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_PASSWORD;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_PHONE;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ROLE;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_SALARY;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_USERNAME;
import static com.example.john.leaveapp.utils.Constants.config.START_DATE;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_DEPARTMENT;
import static com.example.john.leaveapp.utils.Constants.config.TIME;
import static com.example.john.leaveapp.utils.Constants.config.URL_SAVE_APPLY;
import static com.example.john.leaveapp.utils.Constants.config.URL_SAVE_STAFF;

/**
 * Created by john on 3/3/18.
 */

public class SignupActivity extends AppCompatActivity {
    private Button btn_signup;
    private EditText input_fname,input_lname,input_salary,input_username,input_password,input_confirm;
    private Spinner  spinner_role, spinner_department;
    private RadioGroup gender_group;
    private IntlPhoneInput my_phone_input;
    private AwesomeValidation awesomeValidation;
    private TextInputLayout pass_layout, con_layout;
    private RadioGroup radioGroup;
    List<String> roleList = new ArrayList<>();
    List<String> depList = new ArrayList<>();
    List<Integer> depID = new ArrayList<>();
    private Context context = this;
    String password1 = "",password2 = "";
    private static final String TAG = "SignupActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //TODO:: GET BY ID...!!
        input_fname = (EditText) findViewById(R.id.input_fname);
        input_lname = (EditText) findViewById(R.id.input_lname);
        input_salary = (EditText) findViewById(R.id.input_salary);
        input_username = (EditText) findViewById(R.id.input_username);
        input_password = (EditText) findViewById(R.id.input_password);
        input_confirm = (EditText) findViewById(R.id.input_confirm);
        spinner_role = (Spinner) findViewById(R.id.spinner_role);
        spinner_department = (Spinner) findViewById(R.id.spinner_department);
        my_phone_input = (IntlPhoneInput) findViewById(R.id.my_phone_input);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        pass_layout = (TextInputLayout) findViewById(R.id.pass_layout);
        con_layout = (TextInputLayout) findViewById(R.id.con_layout);
        radioGroup = (RadioGroup) findViewById(R.id.gender_group);

                setSpinner();
                setSpinnerD();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        final String pattern = "\\d{10}|(?:\\d{3})";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        awesomeValidation.addValidation(this, R.id.input_lname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.enter_valid_name);
        awesomeValidation.addValidation(this, R.id.input_fname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.enter_valid_name);
        //awesomeValidation.addValidation(this, R.id.input_salary, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.enter_valid_name);
        awesomeValidation.addValidation(this, R.id.input_username, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.enter_valid_username);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        input_password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                password1 = input_password.getText().toString().trim();
                //todo::.. you can call or do what you want with your EditText here
                if (password1.length() > 5){
                    input_confirm.setEnabled(true);
                    pass_layout.setErrorEnabled(false);
                }else {
                    input_confirm.setEnabled(false);
                    pass_layout.setError("Atleast 6 characters!");
                    pass_layout.setErrorEnabled(true);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        input_confirm.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                password2 = input_confirm.getText().toString().trim();
                // you can call or do what you want with your EditText here
                if (password2.equals(password1)){
                    btn_signup.setEnabled(true);
                    con_layout.setErrorEnabled(false);
                }else {
                    btn_signup.setEnabled(false);
                    con_layout.setError("Passwords doesn't match!");
                    con_layout.setErrorEnabled(true);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()){
                    String phone = my_phone_input.getNumber();
                    if (my_phone_input.isValid()){
                        //TODO:: Signup here..!!
                        String fname = input_fname.getText().toString().trim();
                        String lname = input_lname.getText().toString().trim();
                        String salary = input_salary.getText().toString().trim();
                        String username = input_username.getText().toString().trim();
                        String password = input_password.getText().toString().trim();
                        String department = spinner_department.getSelectedItem().toString().trim();
                        String role = spinner_role.getSelectedItem().toString().trim();
                        String gender = "";
                        int department_id = 0;
                        if (!department.equals(" --- Select Department ---- ")){
                            for (int i=0; i<depList.size(); i++){
                                if (depList.get(i).equals(department)){
                                    department_id = depID.get(i);
                                }
                            }
                        }
                        if(radioGroup.getCheckedRadioButtonId() != -1 ) {
                            int driesId = radioGroup.getCheckedRadioButtonId();
                            // find the radiobutton by returned id
                             gender = ((RadioButton)findViewById(driesId)).getText().toString();
                        }
                            if (!role.equals(" --- Select Designation ---- ") && !gender.equals("") && !department.equals(" --- Select Department ---- ")){
                                send(fname,lname,gender,username,password,phone,salary,role,1,department_id); ;
                        }else {
                            Toast.makeText(context,"Please make a valid selection..!",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(context,phone+" Is invalid for the country Selected..!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void setSpinner() {
        try{
            roleList.add(" --- Select Designation ---- ");
            roleList.add("Lecturer");
            roleList.add("Senior Lecturer");
            roleList.add("HOD");
            roleList.add("Non Staff");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, roleList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_role.setAdapter(dataAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setSpinnerD() {
        try{
            depList.add(" --- Select Department ---- ");
            depID.add(0);
            String query = " SELECT * FROM "+TABLE_DEPARTMENT+" ORDER BY "+Constants.config.DEPARTMENT_NAME+"";
            try{
               Cursor cursor = ReturnCursor.getCursor(query,context);
               if (cursor.moveToFirst()){
                   do {
                      depList.add(cursor.getString(cursor.getColumnIndex(Constants.config.DEPARTMENT_NAME)));
                      depID.add(cursor.getInt(cursor.getColumnIndex(Constants.config.DEPARTMENT_ID)));
                   }while (cursor.moveToNext());
               }
            }catch (Exception e){
                e.printStackTrace();
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, depList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_department.setAdapter(dataAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
    public void send(final String fname, final String lname, final String gender, final String username, final String password, final String contact, final String salary, final String role, final int responsibity_id, final int department_id){
        BaseApplication.deleteCache(context);
        final ProgressDialog dialog = new ProgressDialog(context);
        try{
            dialog.setMessage("Pleasse wait...");
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_STAFF,
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
                            String message = new Staff(context).save(id,fname,lname,gender,username,password,contact,salary,role,responsibity_id,department_id,status);
                            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                            if (message.equals("Staff Details saved!")){
                                //startActivity(new Intent(context,LoginActivity.class));
                                finish();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        try{
                            if (dialog.isShowing()){
                                dialog.dismiss();
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
                //int status = 1;
                Map<String, String> params = new Hashtable<String, String>();

                params.put(STAFFL_FNAME,fname);
                params.put(STAFFL_LNAME,lname);
                params.put(STAFF_ROLE,role);
                params.put(STAFF_GENDER,gender);
                params.put(STAFF_USERNAME,username);
                params.put(STAFF_PASSWORD,password);
                params.put(STAFF_PHONE,contact);
                params.put(STAFF_SALARY,salary);
                params.put(RESPONSIBILITY_ID, String.valueOf(responsibity_id));
                params.put(DEPARTMENT_ID, String.valueOf(department_id));

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
