package com.example.john.leaveapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.core.SessionManager;
import com.example.john.leaveapp.db_operartions.Staff;
import com.example.john.leaveapp.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.VIBRATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by john on 3/3/18.
 */

public class LoginActivity extends AppCompatActivity {
    private Button btn_login;
    private EditText userText, passText;
    private Context context = this;
    private TextView create_text;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static int SPLASH_TIME_OUT = 2000;
    public static final int RequestPermissionCode = 1;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        userText = (EditText) findViewById(R.id.userEdit);
        passText = (EditText) findViewById(R.id.passEdit);
        create_text = (TextView) findViewById(R.id.create_text);

        try {
            SessionManager sessionManager = new SessionManager(context);
            if (sessionManager.isLoggedIn() == true) {
                startActivity(new Intent(context, US_MainActivity.class));
                finish();
            }
            checkAndRequestPermissions();
        }catch (Exception e){
            e.printStackTrace();
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userText.getText().toString().trim();
                String password = passText.getText().toString().trim();

                try{
                    Cursor cursor = new Staff(context).login(username,password);
                    if (cursor != null){
                        if (cursor.moveToFirst()){
                            do {
                                int id = cursor.getInt(cursor.getColumnIndex(Constants.config.STAFF_ID));
                                String fname = cursor.getString(cursor.getColumnIndex(Constants.config.STAFFL_FNAME));
                                new SessionManager(context).loginSession(
                                        cursor.getInt(cursor.getColumnIndex(Constants.config.STAFF_ID)),
                                        cursor.getString(cursor.getColumnIndex(Constants.config.STAFFL_FNAME)),
                                        cursor.getString(cursor.getColumnIndex(Constants.config.STAFFL_LNAME)),
                                        cursor.getString(cursor.getColumnIndex(Constants.config.STAFF_USERNAME)),
                                        cursor.getString(cursor.getColumnIndex(Constants.config.STAFF_PASSWORD)),
                                        cursor.getString(cursor.getColumnIndex(Constants.config.STAFF_PHONE)),
                                        cursor.getString(cursor.getColumnIndex(Constants.config.STAFF_GENDER)),
                                        cursor.getString(cursor.getColumnIndex(Constants.config.STAFF_ROLE)),
                                        cursor.getString(cursor.getColumnIndex(Constants.config.STAFF_SALARY))
                                        );
                            }while (cursor.moveToNext());
                            //todo::: Go to the main activity...!
                            startActivity(new Intent(context,US_MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(context,"Username or Password not found..!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        create_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,SignupActivity.class));
            }
        });

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    ///// TODO: 10/13/17   permission requests...
    private  boolean checkAndRequestPermissions() {
        int camerapermission = ContextCompat.checkSelfPermission(this, CAMERA);
        int writepermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readpermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionNetworkstate = ContextCompat.checkSelfPermission(this, ACCESS_NETWORK_STATE);
        int permissionVibrate = ContextCompat.checkSelfPermission(this, VIBRATE);

        int getAccountpermission = ContextCompat.checkSelfPermission(this, GET_ACCOUNTS);
        int phonestatepermission = ContextCompat.checkSelfPermission(this, READ_PHONE_STATE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(CAMERA);
        }
        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionNetworkstate != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(ACCESS_NETWORK_STATE);
        }
        if (readpermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (getAccountpermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(GET_ACCOUNTS);
        }
        if (permissionVibrate != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(VIBRATE);
        }
        if (phonestatepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(READ_PHONE_STATE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);

                perms.put(VIBRATE, PackageManager.PERMISSION_GRANTED);
                perms.put(GET_ACCOUNTS, PackageManager.PERMISSION_GRANTED);
                perms.put(READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);

                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(VIBRATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED
                            && perms.get(READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED ) {
                        Log.d(TAG, "All permissions services permission granted");
                        // process the normal flow
                        //Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
                        //startActivity(i);
                        //finish();
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, CAMERA)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_NETWORK_STATE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, VIBRATE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, GET_ACCOUNTS)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE) ) {
                            showDialogOK("Service Permissions are required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    finish();
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?");
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
    private void explain(String msg){
        final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        //  permissionsclass.requestPermission(type,code);
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.exampledemo.parsaniahardik.marshmallowpermission")));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.setCancelable(false);
        dialog.show();

    }
    private void requestPermission() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        ActivityCompat.requestPermissions(LoginActivity.this, new String[]
                {
                        CAMERA,
                        READ_PHONE_STATE,
                        VIBRATE,
                        ACCESS_NETWORK_STATE,
                        WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE,
                        GET_ACCOUNTS
                }, RequestPermissionCode);

    }

}
