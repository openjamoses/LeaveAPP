package com.example.john.leaveapp.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.db_operartions.Staff;

/**
 * Created by john on 3/6/18.
 */

public class ProfileActivity extends AppCompatActivity {
    private TextView fnameText,lnameText,genderText,phoneText,usernameText,departmentText,roleText,salaryText;
    private FloatingActionButton flag;
    private Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fnameText = (TextView) findViewById(R.id.fnameText);
        lnameText = (TextView) findViewById(R.id.lnameText);
        genderText = (TextView) findViewById(R.id.genderText);
        phoneText = (TextView) findViewById(R.id.phoneText);
        usernameText = (TextView) findViewById(R.id.userText);
        roleText = (TextView) findViewById(R.id.roleText);
        departmentText = (TextView) findViewById(R.id.depatartmentText);
        salaryText = (TextView) findViewById(R.id.salaryText);
        flag = (FloatingActionButton) findViewById(R.id.flag);

        flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,EditProfileActivity.class));
                //Toast.makeText(context,">>> Not yet Implemented..!", Toast.LENGTH_SHORT).show();
            }
        });
        setValue();
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setValue() {
        try{
            fnameText.setText(new UserDetails(context).getfname());
            lnameText.setText(new UserDetails(context).getlname());
            genderText.setText(new UserDetails(context).getgender());
            phoneText.setText(new UserDetails(context).getphone());
            usernameText.setText(new UserDetails(context).getusername());
            roleText.setText(new UserDetails(context).getrole());
            salaryText.setText(new UserDetails(context).getsalary());



        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            int id = new UserDetails(context).getid();
            String department = new Staff(context).getDepartment(id);
            if (department.equals("")){
                departmentText.setText("Unknown");
            }else {
                departmentText.setText(department);
            }

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
