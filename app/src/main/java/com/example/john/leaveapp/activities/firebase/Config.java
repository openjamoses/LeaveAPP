package com.example.john.leaveapp.activities.firebase;

/**
 * Created by john on 8/31/17.
 */

public class Config {
    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";
    public static final String KEY_TOKEN = "regId";
    //URL to RegisterDevice.php
    public static final String URL_REGISTER_DEVICE_PATIENT = "firebase/RegisterDevice1.php";
    public static final String URL_REGISTER_DEVICE_DOCTOR = "firebase/RegisterDevice2.php";
    public static final String URL_DEVICES_PATIENT = "firebase/GetRegisteredDevices1.php";
    public static final String URL_DEVICES_DOCTOR = "firebase/GetRegisteredDevices2.php";
    public static final String URL_SINGLE_USER = "firebase/sendSinglePush.php";
    public static final String URL_SINGLE_DOCTOR = "firebase/sendSinglePush2.php";
    public static final String URL_MULTIPLE_USERS = "firebase/sendMultiplePush.php";

    public static final String APP_FOLDER = "MOBICARE";
    public static final String IMAGE_SUB_FOLDER = "images";
    public static final String VIDEO_SUB_FOLDER = "videos";
    public static final String AUDIO_SUB_FOLDER = "audio";


}
