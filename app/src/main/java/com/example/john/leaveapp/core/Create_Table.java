package com.example.john.leaveapp.core;

import com.example.john.leaveapp.utils.Constants;

/**
 * Created by john on 8/12/17.
 */

public class Create_Table {
    public abstract class create{
        //todo: queries to create the table
        public static final String CREATE_STAFF =
                "CREATE TABLE "+ Constants.config.TABLE_STAFF +" ("+ Constants.config.STAFFID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.STAFFL_FNAME+" TEXT,"+Constants.config.STAFFL_LNAME+" TEXT, "+Constants.config.STAFF_ROLE+" TEXT, " +
                        " "+Constants.config.STAFF_USERNAME+" TEXT,"+Constants.config.STAFF_PASSWORD+" TEXT,"+Constants.config.STAFF_GENDER+" TEXT," +
                        " "+Constants.config.STAFF_STATUS+" INTEGER , "+Constants.config.IMEI+" TEXT,"+Constants.config.STAFF_PHONE+" TEXT," +
                        " "+Constants.config.STAFF_SALARY+" TEXT,"+Constants.config.STAFF_ID+" INTEGER, "+Constants.config.RESPONSIBILITY_ID+" INTEGER," +
                        " "+Constants.config.DEPARTMENT_ID+" INTEGER );";

        public static final String CREATE_UNIVERSITY =
                "CREATE TABLE "+ Constants.config.TABLE_UNIVERSITY +" ("+ Constants.config.UNIVERSITYID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.UNIVERSITY_NAME+" TEXT,"+Constants.config.UNIVERSITY_STATUS+" INTEGER,"+Constants.config.UNIVERSITY_ID+" INTEGER);";

        public static final String CREATE_APPLY =
                "CREATE TABLE "+ Constants.config.TABLE_APPLY +" ("+ Constants.config.APPLYID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.APPLY_ID+" INTEGER, "+Constants.config.START_DATE+" TEXT,"+Constants.config.END_DATE+" TEXT,"+Constants.config.STAFF_ID+" INTEGER, " +
                        " "+Constants.config.LEAVE_ID+" INTEGER,"+Constants.config.APPLY_STATUS+" INTEGER," +
                        " "+Constants.config.LEAVE_STATUS1+" INTEGER, "+Constants.config.LEAVE_STATUS2+" INTEGER, "+Constants.config.LEAVE_STATUS+" INTEGER," +
                        " "+Constants.config.DATE+" TEXT, "+Constants.config.TIME+" TEXT );";

        public static final String CREATE_LEAVE =
                "CREATE TABLE "+ Constants.config.TABLE_LEAVE +" ("+ Constants.config.LEAVEID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.DATE_ASSUMPTION+" TEXT,"+Constants.config.DATE_PROMOTION+" TEXT, "+Constants.config.DATE_RETURN+" TEXT, " +
                        " "+Constants.config.DAYS_TAKEN+" TEXT,"+Constants.config.BALANCE_TAKEN+" TEXT,"+Constants.config.LEAVE_ID+" INTEGER, " +
                        " "+Constants.config.LEAVE_NOW+" TEXT , "+Constants.config.LEAVE_FROM+" TEXT,"+Constants.config.LEAVE_TO+" TEXT," +
                        " "+Constants.config.BALANCE_OUTSTANDING+" TEXT,"+Constants.config.LEAVEDUE_FROM+" TEXT,"+Constants.config.LEAVEDUE_TO+" TEXT," +
                        " "+Constants.config.SIGNATURE+" INTEGER, "+Constants.config.LEAVETYPE_ID+" INTEGER );";
        public static final String CREATE_DEPARTMENT =
                "CREATE TABLE "+ Constants.config.TABLE_DEPARTMENT +" ("+ Constants.config.DEPARTMENTID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.DEPARTMENT_NAME+" TEXT,"+Constants.config.DEPARTMENT_STATUS+" INTEGER,"+Constants.config.FACULTY_ID+" INTEGER," +
                        " "+Constants.config.DEPARTMENT_ID+" INTEGER );";

        public static final String CREATE_FACULTY =
                "CREATE TABLE "+ Constants.config.TABLE_FACULTY +" ("+ Constants.config.FACULTY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.FACULTY_NAME+" TEXT, "+Constants.config.UNIVERSITY_ID+" INTEGER ,"+Constants.config.FACULTY_STATUS+" INTEGER );";

        public static final String CREATE_RESPONSIBILY =
                "CREATE TABLE "+ Constants.config.TABLE_RESPONSIBILITY +" ("+ Constants.config.RESPONSIBILITY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.RESPONSIBILITY_NAME+" TEXT, "+Constants.config.RESPONSIBILITY_STATUS+" INTEGER );";

        public static final String CREATE_SECRETARY =
                "CREATE TABLE "+ Constants.config.TABLE_SECRETARY +" ("+ Constants.config.SECRETARY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.STAFF_ID+" INTEGER, "+Constants.config.UNIVERSITY_ID+" INTEGER,"+Constants.config.SECRETARY_STATUS+" INTEGER);";

        public static final String CREATE_LEAVETYPE =
                "CREATE TABLE "+ Constants.config.TABLE_LEAVE_TYPE +" ("+ Constants.config.LEAVETYPEID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.LEAVETYPE_NAME+" TEXT, "+Constants.config.LEAVETYPE_STATUS+" INTEGER, "+Constants.config.ENTITLEMENT+" TEXT," +
                        " "+Constants.config.LEAVETYPE_ID+" INTEGER);";

        public static final String CREATE_DEP_STAFF =
                "CREATE TABLE "+ Constants.config.TABLE_DEP_STAFF +" ("+ Constants.config.DEP_STAFF_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.STAFF_ID+" INTEGER,"+Constants.config.DEPARTMENT_ID+" INTEGER, "+Constants.config.DEP_STATUS+" INTEGER);";

        public static final String CREATE_APPROVAL =
                "CREATE TABLE "+ Constants.config.TABLE_APPROVAL +" ("+ Constants.config.APPROVAL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.SECRETARY_ID+" TEXT, "+Constants.config.DEPARTMENT_ID+" INTEGER ,"+Constants.config.APPROVAL_STATUS+" INTEGER," +
                        " "+Constants.config.US_STATUS+" INTEGER, "+Constants.config.HOD_STATUS+" INTEGER );";

        public static final String CREATE_ACCOMPANIED =
                "CREATE TABLE "+ Constants.config.TABLE_ACCOMPANIED +" ("+ Constants.config.ACCOMPANIED_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.ACCOMPANIED_NAME+" TEXT, "+Constants.config.ACCOMPANIED_TYPE+" INTEGER ," +
                        ""+Constants.config.ACCOMPANIED_STATUS+" INTEGER,"+Constants.config.ACCOMPANIED_AGE+" INTEGER,"+Constants.config.STAFF_ID+" INTEGER);";
        public abstract void start(String name);

        public static final String CREATE_NOTICATION =
                "CREATE TABLE "+ Constants.config.TABLE_NOTICATION +" ("+ Constants.config.NOTICATIONID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.NOTICATION_BODY+" TEXT, "+Constants.config.NOTIFICATION_DATE+" INTEGER ,"+Constants.config.STAFF_ID+" INTEGER," +
                        " "+Constants.config.APPLY_ID+" INTEGER, "+Constants.config.NOTIFICATION_STATUS+" INTEGER, "+Constants.config.NOTICATION_ID+" TEXT );";

    }
}
