package com.example.john.leaveapp.core;

import com.example.john.leaveapp.utils.Constants;

/**
 * Created by john on 8/12/17.
 */

public class Create_Table {
    public abstract class create{
        //todo: queries to create the table
        public static final String CREATE_STAFF =
                "CREATE TABLE "+ Constants.config.TABLE_STAFF +" ("+ Constants.config.STAFF_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.STAFFL_FNAME+" TEXT,"+Constants.config.STAFFL_LNAME+" TEXT, "+Constants.config.STAFF_DOB+" TEXT, " +
                        " "+Constants.config.STAFF_USERNAME+" TEXT,"+Constants.config.STAFF_PASSWORD+" TEXT,"+Constants.config.STAFF_GENDER+" TEXT," +
                        " "+Constants.config.FETCH_STATUS+" INTEGER , "+Constants.config.IMEI+" TEXT );";

        public static final String CREATE_UNIVERSITY =
                "CREATE TABLE "+ Constants.config.TABLE_UNIVERSITY +" ("+ Constants.config.UNIVERSITY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.UNIVERSITY_NAME+" TEXT,"+Constants.config.FETCH_STATUS+" INTEGER, " +
                        " "+Constants.config.UNIVERSITY_LOGO+" TEXT);";

        public static final String CREATE_APPLY =
                "CREATE TABLE "+ Constants.config.TABLE_APPLY +" ("+ Constants.config.APPLY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.START_DATE+" TEXT,"+Constants.config.END_DATE+" TEXT, "+Constants.config.LEAVETYPE_ID+" INTEGER, "+Constants.config.FETCH_STATUS+" TEXT );";

        public static final String CREATE_FORM =
                "CREATE TABLE "+ Constants.config.TABLE_FORM +" ("+ Constants.config.FORM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.FORM_NAME+" TEXT,"+Constants.config.FETCH_STATUS+" INTEGER,"+Constants.config.STAFF_ID+" INTEGER);";


        public static final String CREATE_DEPARTMENT =
                "CREATE TABLE "+ Constants.config.TABLE_DEPARTMENT +" ("+ Constants.config.DEPARTMENT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.DEPARTMENT_NAME+" TEXT,"+Constants.config.FETCH_STATUS+" INTEGER,"+Constants.config.FACULTY_ID+" INTEGER );";

        public static final String CREATE_FACULTY =
                "CREATE TABLE "+ Constants.config.TABLE_FACULTY +" ("+ Constants.config.FACULTY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.FACULTY_NAME+" TEXT, "+Constants.config.UNIVERSITY_ID+" INTEGER ,"+Constants.config.FETCH_STATUS+" INTEGER );";


        public static final String CREATE_RESPONSIBILY =
                "CREATE TABLE "+ Constants.config.TABLE_RESPONSIBILITY +" ("+ Constants.config.RESPONSIBILITY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.RESPONSIBILITY_NAME+" TEXT, "+Constants.config.FETCH_STATUS+" INTEGER );";

        public static final String CREATE_SECRETARY =
                "CREATE TABLE "+ Constants.config.TABLE_SECRETARY +" ("+ Constants.config.SECRETARY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.SECRETARY_NAME+" TEXT, "+Constants.config.UNIVERSITY_ID+" INTEGER,"+Constants.config.FETCH_STATUS+" INTEGER);";


        public static final String CREATE_LEAVETYPE =
                "CREATE TABLE "+ Constants.config.TABLE_LEAVE_TYPE +" ("+ Constants.config.LEAVETYPE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.LEAVETYPE_NAME+" TEXT, "+Constants.config.FETCH_STATUS+" INTEGER);";

        public static final String CREATE_LEAVEFORM =
                "CREATE TABLE "+ Constants.config.TABLE_LEAVEFORM +" ("+ Constants.config.LEAVEFORM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.LEAVETYPE_ID+" TEXT, "+Constants.config.FORM_ID+" INTEGER,"+Constants.config.FETCH_STATUS+" INTEGER);";


        public static final String CREATE_APPROVAL =
                "CREATE TABLE "+ Constants.config.TABLE_APPROVAL +" ("+ Constants.config.APPROVAL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.SECRETARY_ID+" TEXT, "+Constants.config.DEPARTMENT_ID+" INTEGER ,"+Constants.config.FETCH_STATUS+" INTEGER," +
                        " "+Constants.config.US_STATUS+" INTEGER, "+Constants.config.HOD_STATUS+" INTEGER );";


        public abstract void start(String name);
    }
}
