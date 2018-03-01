package com.example.john.leaveapp.utils;
/**
 * Created by john on 7/8/17.
 */
public class Constants {
    public abstract class config{
        public static final String DB_NAME = "leaveapp_db";
        public static final int DB_VERSION = 1;

        public static final int TOTAL_TABLES = 10;
        /****** URL DECLARATION ******************************/
        public static final String URL_PHONE = "http://192.168.43.18/";
        public static final String URL_CAMTECH = "http://192.168.1.119/";
        public static final String URL_SERVER = "http://173.255.219.164/";
        public static final String HOST_URL = URL_PHONE+ "leaveapp/";

        public static final String IMEI = "IMEI";
        public static final String APP_VERSION = "app_version";

        ///DB CONNECTIONS

        //// TODO: 10/12/17   STAFF
        public static final String STAFF_ID = "staff_id";
        public static final String TABLE_STAFF = "staff_tb";
        public static final String STAFFL_FNAME = "fname";
        public static final String STAFFL_LNAME = "lname";
        public static final String STAFF_DOB = "date_of_birth";
        public static final String STAFF_USERNAME = "user_name";
        public static final String STAFF_PASSWORD = "password";
        public static final String FETCH_STATUS = "fetch_status";
        public static final String STAFF_GENDER = "gender";
        public static final String STAFF_PHONE = "phone_number";
        //// TODO: 10/12/17   APPLY
        public static final String APPLY_ID = "apply_id";
        public static final String TABLE_APPLY = "apply_tb";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";

        //// TODO: 10/13/17 FORM TB
        public static final String FORM_ID = "form_id";
        public static final String TABLE_FORM = "form_tb";
        public static final String FORM_NAME = "form_name";

        //// TODO: 10/13/17 DEPARTMENT
        public static final String DEPARTMENT_ID = "department_id";
        public static final String TABLE_DEPARTMENT = "department_tb";
        public static final String DEPARTMENT_NAME = "department_name";
        //// TODO: 10/13/17 FACULTY
        public static final String FACULTY_ID = "faculty_id";
        public static final String TABLE_FACULTY = "faculty_tb";
        public static final String FACULTY_NAME = "faculty_name";
        ///TODO::: SECRETARY
        public static final String SECRETARY_ID = "secretary_id";
        public static final String TABLE_SECRETARY = "secretary_tb";
        public static final String SECRETARY_NAME = "secretary_name";
        //TODO::: LEAVE TYPE
        public static final String TABLE_LEAVE_TYPE = "leavetype_tb";
        public static final String LEAVETYPE_ID = "leavetype_id";
        public static final String LEAVETYPE_NAME = "leavetype_name";
        //// TODO: 11/10/17  UNIVERSITY
        public static final String TABLE_UNIVERSITY = "university_tb";
        public static final String UNIVERSITY_ID = "university_id";
        public static final String UNIVERSITY_NAME = "university_name";
        public static final String UNIVERSITY_LOGO = "university_logo";
        //// TODO: 11/10/17  RESPONSIBILITY
        public static final String TABLE_RESPONSIBILITY = "responsibility_tb";
        public static final String RESPONSIBILITY_ID = "responsibility_id";
        public static final String RESPONSIBILITY_NAME = "responsibility_name";
        //// TODO: 11/10/17 INTERMEDIATE TABLE BETWEEN  LEAVETYPE_TB AND FORM_TB
        public static final String TABLE_LEAVEFORM = "responsibility_tb";
        public static final String LEAVEFORM_ID = "responsibility_id";
        //// TODO: 11/10/17  APPROVAL
        public static final String TABLE_APPROVAL = "approval_tb";
        public static final String APPROVAL_ID = "approval_id";
        public static final String APPROVAL_DATE = "approval_date";
        public static final String US_STATUS = "us_status";
        public static final String HOD_STATUS = "hod_status";



        ///// TODO: 10/15/17    NUMBER OF FREE DEMO DAYS

        ///*********************** End of Operations ******************************/
        ///
        public static final String OPERATION_CASES = "cases";
        public static final String OPERATION_ACTIVATION = "activation";
        public static final String OPERATION_INSTALATION = "instalation";
        public static final String OPERATION_MANAGEMENT = "management";
        public static final String OPERATION_SUBSCRIPTION = "subscription";
        public static final String OPERATION_FEES = "fees";
        public static final String OPERATION_PAYMENTS = "payments";
        public static final String OPERATION_PAYPAL = "paypal";

        public static final String ACTION_ASSESSEMENT = "assessement";
        public static final String ACTION_HISTORY = "history";
        public static final String ACTION_SEARCH = "search";
        public static final String ACTION_ACTIVATE = "activate";

    }
}
