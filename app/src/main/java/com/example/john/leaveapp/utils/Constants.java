package com.example.john.leaveapp.utils;
/**
 * Created by john on 7/8/17.
 */
public class Constants {
    public abstract class config{
        public static final String DB_NAME = "leaveapp_db";
        public static final int DB_VERSION = 1;
        public static final int TOTAL_TABLES = 11;
        /****** URL DECLARATION ******************************/
        public static final String URL_PHONE = "http://192.168.43.18/";
        public static final String URL_MODEM = "http://10.127.173.172/";
        public static final String URL_CAMTECH = "http://192.168.137.51/";
        public static final String URL_SERVER = "http://173.255.219.164/";
        public static final String HOST_URL = URL_MODEM + "LEAVEAPP/pages/mobile_connections/";
        public static final String IMEI = "IMEI";
        public static final String APP_VERSION = "app_version";
        ///DB CONNECTIONS..!
        public static final String USER_TYPE = "user_type";
        //// TODO: 10/12/17   STAFF
        public static final String STAFF_ID = "staff_id";
        public static final String STAFFID = "staffID";
        public static final String TABLE_STAFF = "staff_tb";
        public static final String STAFFL_FNAME = "fname";
        public static final String STAFFL_LNAME = "lname";
        public static final String STAFF_ROLE = "staff_role";
        public static final String STAFF_USERNAME = "user_name";
        public static final String STAFF_PASSWORD = "password";
        public static final String STAFF_GENDER = "gender";
        public static final String STAFF_PHONE = "phone_number";
        public static final String STAFF_SALARY = "staff_salary";
        public static final String STAFF_STATUS = "staff_status";
        public static final String LOGIN_TIME = "login_time";
        public static final String LOGIN_DATE = "login_date";
        //// TODO: 10/12/17   APPLY
        public static final String APPLY_ID = "apply_id";
        public static final String APPLYID = "applyID";
        public static final String TABLE_APPLY = "apply_tb";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";
        public static final String APPLY_STATUS = "apply_status";
        //// TODO: 10/13/17 FORM TB
        //public static final String ANNUAL_ID = "annual_id";
        public static final String LEAVE_ID = "leave_id";
        public static final String LEAVEID = "leaveID";
        //public static final String TABLE_ANNUAL = "annual_tb";
        public static final String DATE_ASSUMPTION = "date_assumption";
        public static final String DATE_PROMOTION = "date_promotion";
        public static final String DATE_RETURN = "date_return";
        public static final String DAYS_TAKEN = "days_taken";
        public static final String BALANCE_TAKEN = "balance_taken";
        public static final String ENTITLEMENT = "entitlement";
        public static final String LEAVE_NOW = "leave_now";
        public static final String LEAVE_FROM = "leave_from";
        public static final String LEAVE_TO = "leave_to";
        public static final String BALANCE_OUTSTANDING = "balance_outstanding";
        public static final String LEAVEDUE_FROM = "leavedue_from";
        public static final String LEAVEDUE_TO = "leavedue_to";
        public static final String DATE = "date";
        public static final String TIME = "time";
        public static final String SIGNATURE = "signature";
        public static final String LEAVE_STATUS1 = "leave_status1";
        public static final String LEAVE_STATUS2 = "leave_status2";
        //public static final String L_STATUS = "l_status";

        //public static final String ANNUAL_STATUS = "annual_status";
        public static final String LEAVE_STATUS = "leave_status";
        public static final String TABLE_LEAVE = "leave_tb";
        //// TODO: 10/13/17 DEPARTMENT
        public static final String DEPARTMENT_ID = "department_id";
        public static final String TABLE_DEPARTMENT = "department_tb";
        public static final String DEPARTMENT_NAME = "department_name";
        public static final String DEPARTMENTID = "departmentid";
        public static final String DEPARTMENT_STATUS = "department_status";
        //// TODO: 10/13/17 FACULTY
        public static final String FACULTYID = "facultyid";
        public static final String FACULTY_ID = "faculty_id";
        public static final String TABLE_FACULTY = "faculty_tb";
        public static final String FACULTY_NAME = "faculty_name";
        public static final String FACULTY_STATUS = "faculty_status";
        ///TODO::: SECRETARY
        public static final String SECRETARY_ID = "secretary_id";
        public static final String TABLE_SECRETARY = "secretary_tb";
        public static final String SECRETARY_NAME = "secretary_name";
        public static final String SECRETARY_STATUS = "secretary_status";
        //TODO::: LEAVE TYPE
        public static final String TABLE_LEAVE_TYPE = "leavetype_tb";
        public static final String LEAVETYPE_ID = "leavetype_id";
        public static final String LEAVETYPEID = "leavetypeid";
        public static final String LEAVETYPE_NAME = "leavetype_name";
        public static final String LEAVETYPE_STATUS = "leavetype_status";
        //// TODO: 11/10/17  UNIVERSITY
        public static final String TABLE_UNIVERSITY = "university_tb";
        public static final String UNIVERSITYID = "universityid";
        public static final String UNIVERSITY_ID = "university_id";
        public static final String UNIVERSITY_NAME = "university_name";
        public static final String UNIVERSITY_LOGO = "university_logo";
        public static final String UNIVERSITY_STATUS= "university_status";
        //// TODO: 11/10/17  RESPONSIBILITY
        public static final String TABLE_RESPONSIBILITY = "responsibility_tb";
        public static final String RESPONSIBILITYID = "responsibilityid";
        public static final String RESPONSIBILITY_ID = "responsibility_id";
        public static final String RESPONSIBILITY_NAME = "responsibility_name";
        public static final String RESPONSIBILITY_STATUS = "responsibility_status";
        //// TODO: 11/10/17 INTERMEDIATE TABLE BETWEEN  LEAVETYPE_TB AND FORM_TB
        //// TODO: 11/10/17  APPROVAL
        public static final String TABLE_APPROVAL = "approval_tb";
        public static final String APPROVALID = "approvalid";
        public static final String APPROVAL_ID = "approval_id";
        public static final String APPROVAL_DATE = "approval_date";
        public static final String US_STATUS = "us_status";
        public static final String HOD_STATUS = "hod_status";
        public static final String APPROVAL_STATUS = "approval_status";
        //// TODO: 11/10/17  APPROVAL
        public static final String TABLE_DEP_STAFF = "dep_staff_tb";
        public static final String DEP_STAFFID = "dep_staffid";
        public static final String DEP_STAFF_ID = "dep_staff_id";
        public static final String DEP_STATUS = "dep_status";
        //// TODO: 11/10/17  ACOMPANIED BY
        public static final String TABLE_ACCOMPANIED = "accompanied_tb";
        public static final String ACCOMPANIEDID = "accompaniedid";
        public static final String ACCOMPANIED_ID = "accompanied_id";
        public static final String ACCOMPANIED_NAME = "accompanied_name";
        public static final String ACCOMPANIED_TYPE = "accompanied_type";
        public static final String ACCOMPANIED_AGE = "accompanied_age";
        public static final String ACCOMPANIED_STATUS = "accompanied_status";

        //// TODO: 11/10/17  ACOMPANIED BY
        public static final String TABLE_NOTICATION = "notification_tb";
        public static final String NOTICATIONID = "notificationid";
        public static final String NOTICATION_ID = "notication_id";
        public static final String NOTICATION_BODY = "notification_body";
        public static final String NOTIFICATION_DATE = "notification_date";
        public static final String NOTIFICATION_STATUS = "notification_status";
        ///*********************** End of Operations ******************************/
        //??TODO:: URL CALLINGS >>>>>>>>>>>>>>>>>>>>>>>>>
        public static final String SYNCING_FOLDER = "syncing/";
        public static final String URL_SAVE_STAFF = SYNCING_FOLDER+"save_staff.php";
        public static final String URL_SAVE_LEAVE = SYNCING_FOLDER+"save_leave.php";
        public static final String URL_SAVE_UNIVERSITY = SYNCING_FOLDER+"save_university.php";
        public static final String URL_SAVE_APPLY = SYNCING_FOLDER+"save_apply.php";
        public static final String URL_SAVE_DEPARTMENT = SYNCING_FOLDER+"save_departments.php";
        public static final String URL_SAVE_FACULTY = SYNCING_FOLDER+"save_faculty.php";
        public static final String URL_SAVE_LEAVETYPE = SYNCING_FOLDER+"save_leavetype.php";
        public static final String URL_SAVE_NOTIFICATION = SYNCING_FOLDER+"save_notification.php";
        public static final String URL_QUERY = SYNCING_FOLDER+"query.php";

        public static final String URL_FETCH_JSON = SYNCING_FOLDER+"fetch_json.php";
        ///TODO ?? OPERATIONS.......!!!!!!>>>>>>>>>>>>>>>>>>>
        public static final String OPERATION_LEAVE = "leave";
        public static final String OPERATION_LEAVETYPE = "leave_type";
        public static final String OPERATION_SECRETARY = "secretary";
        public static final String OPERATION_FACULTY = "faculty";
        public static final String OPERATION_UNIVERSITY = "university";
        public static final String OPERATION_STAFF = "staff";
        public static final String OPERATION_APPLY = "apply";
        public static final String OPERATION_DEPARTMENT = "department";
        public static final String OPERATION_NOTIFICATION = "notification";

        public static final String URL_REGISTER_DEVICE = "firebase/saveTokens.php";


        public static final String USER_STAFF = "user_staff";
        public static final String USER_US = "user_us";
        public static final String USER_HOD = "user_hod";
        public static final String KEY_TOKEN = "regId";
        public static final String SQL_QUERY = "query";

    }
}
