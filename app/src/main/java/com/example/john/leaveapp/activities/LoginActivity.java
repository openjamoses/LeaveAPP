package com.example.john.leaveapp.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.core.SessionManager;
import com.example.john.leaveapp.db_operartions.Staff;
import com.example.john.leaveapp.utils.Constants;

/**
 * Created by john on 3/3/18.
 */

public class LoginActivity extends AppCompatActivity {
    private Button btn_login;
    private EditText userText, passText;
    private Context context = this;
    private TextView create_text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        userText = (EditText) findViewById(R.id.userEdit);
        passText = (EditText) findViewById(R.id.passEdit);
        create_text = (TextView) findViewById(R.id.create_text);

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
                                new SessionManager(context).loginSession(cursor.getInt(cursor.getColumnIndex(Constants.config.STAFF_ID)),
                                        cursor.getString(cursor.getColumnIndex(Constants.config.STAFFL_FNAME)),
                                        cursor.getString(cursor.getColumnIndex(Constants.config.STAFFL_LNAME)),
                                        cursor.getString(cursor.getColumnIndex(Constants.config.STAFF_USERNAME)),
                                        cursor.getString(cursor.getColumnIndex(Constants.config.STAFF_PASSWORD)),
                                        cursor.getString(cursor.getColumnIndex(Constants.config.STAFF_PHONE)),
                                        cursor.getString(cursor.getColumnIndex(Constants.config.STAFF_GENDER))
                                        );
                            }while (cursor.moveToNext());
                            //todo::: Go to the main activity...!
                            startActivity(new Intent(context,US_MainActivity.class));
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
    }
}
