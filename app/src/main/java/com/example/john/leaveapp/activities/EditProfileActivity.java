package com.example.john.leaveapp.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.john.leaveapp.R;
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.db_operartions.Departments;
import com.example.john.leaveapp.db_operartions.Staff;
import com.example.john.leaveapp.utils.Constants;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 3/6/18.
 */

public class EditProfileActivity extends AppCompatActivity {
    private Button btn_signup;
    private EditText input_fname,input_lname,input_salary,input_username,input_password,input_confirm,input_oldpassword;
    private Spinner spinner_role,spinner_department;
    private RadioGroup gender_group;
    private IntlPhoneInput my_phone_input;
    private AwesomeValidation awesomeValidation;
    private TextInputLayout pass_layout, con_layout,oldpass_layout;
    private RadioGroup radioGroup;
    List<String> roleList = new ArrayList<>();
    private Context context = this;
    String password1 = "",password2 = "",password_old = "";
    List<String> dList = new ArrayList<>();
    List<Integer> dID = new ArrayList<>();
    private RadioButton maleRadio, femaleRadio;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //TODO:: GET BY ID...!!
        input_fname = (EditText) findViewById(R.id.input_fname);
        input_lname = (EditText) findViewById(R.id.input_lname);
        input_salary = (EditText) findViewById(R.id.input_salary);
        input_username = (EditText) findViewById(R.id.input_username);
        input_password = (EditText) findViewById(R.id.input_password);
        input_oldpassword = (EditText) findViewById(R.id.input_oldpassword);
        input_confirm = (EditText) findViewById(R.id.input_confirm);
        spinner_department = (Spinner) findViewById(R.id.spinner_department);
        spinner_role = (Spinner) findViewById(R.id.spinner_role);
        my_phone_input = (IntlPhoneInput) findViewById(R.id.my_phone_input);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        pass_layout = (TextInputLayout) findViewById(R.id.pass_layout);
        con_layout = (TextInputLayout) findViewById(R.id.con_layout);
        oldpass_layout = (TextInputLayout) findViewById(R.id.oldpass_layout);
        radioGroup = (RadioGroup) findViewById(R.id.gender_group);
        maleRadio = (RadioButton) findViewById(R.id.radio_male);
        femaleRadio = (RadioButton) findViewById(R.id.radio_female);

        setSpinner();
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
        input_password.setEnabled(false);
        input_oldpassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                password_old = input_oldpassword.getText().toString().trim();
                //todo::.. you can call or do what you want with your EditText here
                if (password_old.equals(new UserDetails(context).getpassword())){
                    input_password.setEnabled(true);
                    oldpass_layout.setErrorEnabled(false);
                }else {
                    input_password.setEnabled(false);
                    oldpass_layout.setError("Invalid Password..!");
                    oldpass_layout.setErrorEnabled(true);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
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

        setValues();
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
                        if(radioGroup.getCheckedRadioButtonId() != -1 ) {
                            int driesId = radioGroup.getCheckedRadioButtonId();
                            // find the radiobutton by returned id
                            gender = ((RadioButton)findViewById(driesId)).getText().toString();
                        }
                        if (!department.equals(" --- Select Department ---") && !role.equals(" --- Select Designation ---- ") && !gender.equals("")){
                            for (int i=0; i<dList.size(); i++){
                                if (department.equals(dList.get(i))){
                                    new Departments(context).save_staff(new UserDetails(context).getid(),dID.get(i));
                                }
                            }

                            /**
                            //String message = new Staff(context).edit(fname,lname,gender,username,password,phone,salary,role,1) ;
                            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                            if (message.equals("personel details updated!")){
                                //startActivity(new Intent(context,LoginActivity.class));
                                finish();
                            }
                             **/
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

    private void setValues(){
        dList.add(" --- Select Department ---");
        dID.add(0);
        try{
            Cursor cursor = new Departments(context).selectAll();
            if (cursor.moveToFirst()){
                do {
                    dList.add(cursor.getString(cursor.getColumnIndex(Constants.config.DEPARTMENT_NAME)));
                    dID.add(cursor.getInt(cursor.getColumnIndex(Constants.config.DEPARTMENT_ID)));
                }while (cursor.moveToNext());
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, dList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_department.setAdapter(dataAdapter);


        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            input_fname.setText(new UserDetails(context).getfname());
            input_lname.setText(new UserDetails(context).getlname());
            input_salary.setText(new UserDetails(context).getsalary());
            input_username.setText(new UserDetails(context).getusername());
            my_phone_input.setNumber(new UserDetails(context).getphone());

            if (maleRadio.getText().toString().equals(new UserDetails(context).getgender())){
                maleRadio.setSelected(true);
            }else {
                femaleRadio.setSelected(true);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void setSpinner() {
        try{

            roleList.add(new UserDetails(context).getrole());
            roleList.add("Senior Lecturer");
            roleList.add("Other Staff");
            roleList.add("Non Staff");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, roleList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_role.setAdapter(dataAdapter);
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
}
