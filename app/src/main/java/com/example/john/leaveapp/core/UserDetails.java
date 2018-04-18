package com.example.john.leaveapp.core;

import android.content.Context;

import java.util.HashMap;

import static com.example.john.leaveapp.utils.Constants.config.DEPARTMENT_ID;
import static com.example.john.leaveapp.utils.Constants.config.DEPARTMENT_NAME;
import static com.example.john.leaveapp.utils.Constants.config.LOGIN_DATE;
import static com.example.john.leaveapp.utils.Constants.config.LOGIN_TIME;
import static com.example.john.leaveapp.utils.Constants.config.STAFFL_FNAME;
import static com.example.john.leaveapp.utils.Constants.config.STAFFL_LNAME;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_GENDER;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ID;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_PASSWORD;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_PHONE;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_ROLE;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_SALARY;
import static com.example.john.leaveapp.utils.Constants.config.STAFF_USERNAME;
import static com.example.john.leaveapp.utils.Constants.config.USER_TYPE;

/**
 * Created by john on 3/6/18.
 */

public class UserDetails {
    private String fname, lname, gender, phone, department, role, salary,username,password,date,time, user_type;
    private int id,department_id;
    public UserDetails(Context context){
        SessionManager sessionManager = new SessionManager(context);
        if(sessionManager.isLoggedIn()) {
            HashMap<String, String> user = sessionManager.getDetails();
            fname = user.get(STAFFL_FNAME);
            lname = user.get(STAFFL_LNAME);
            phone = user.get(STAFF_PHONE);
            gender = user.get(STAFF_GENDER);
            salary = user.get(STAFF_SALARY);
            role = user.get(STAFF_ROLE);
            username = user.get(STAFF_USERNAME);
            password = user.get(STAFF_PASSWORD);
            date = user.get(LOGIN_DATE);
            time = user.get(LOGIN_TIME);
            id = Integer.parseInt(user.get(STAFF_ID));
            department_id = Integer.parseInt(user.get(DEPARTMENT_ID));
            department = user.get(DEPARTMENT_NAME);

            user_type = sessionManager.getType().get(USER_TYPE);
        }
    }
    public  String getfname(){
        return fname;
    }
    public  String getlname(){
        return lname;
    }
    public  String getphone(){
        return phone;
    }
    public  String getgender(){
        return gender;
    }
    public  String getsalary(){
        return salary;
    }
    public   String getrole(){
        return role;
    }
    public  String getusername(){
        return username;
    }
    public  String getpassword(){
        return password;
    }

    public String getUser_type(){
        return user_type;
    }
    public String getDepartment(){
        return department;
    }

    public  int getid(){
        return id;
    }
    public  int getDepartment_id(){
        return department_id;
    }
}
