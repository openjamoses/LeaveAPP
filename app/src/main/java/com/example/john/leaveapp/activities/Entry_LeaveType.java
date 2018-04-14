package com.example.john.leaveapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.core.ReturnCursor;
import com.example.john.leaveapp.db_operartions.LeaveType;
import com.example.john.leaveapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.example.john.leaveapp.utils.Constants.config.LEAVETYPE_NAME;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_LEAVE_TYPE;

/**
 * Created by john on 4/9/18.
 */

public class Entry_LeaveType extends AppCompatActivity {
    private EditText leave_type,entitlement;
    private Button submit_btn;
    private Context context = this;
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leavetype_entry);

        leave_type = (EditText) findViewById(R.id.leave_type);
        entitlement = (EditText) findViewById(R.id.entitlement);
        listView = (ListView) findViewById(R.id.listView);

        //// TODO::
        submit_btn = (Button) findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String l_type = leave_type.getText().toString().trim();
                String l_entitlement = entitlement.getText().toString().trim();

                if (!l_type.equals("") && !l_entitlement.equals("")){
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    new LeaveType(context).send(l_type,l_entitlement,progressDialog);

                    leave_type.setText("");
                    entitlement.setText("");

                }
            }
        });

        setListView(listView);
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

    public void setListView(ListView listView) {
       String query = "SELECT * FROM "+TABLE_LEAVE_TYPE+" ORDER BY "+LEAVETYPE_NAME+" ASC ";
        List<String> list = new ArrayList<>();
        try{
            Cursor cursor = ReturnCursor.getCursor(query,context);
            if (cursor.moveToFirst()){
                do {
                    list.add(" - "+cursor.getString(cursor.getColumnIndex(Constants.config.LEAVETYPE_NAME)));
                }while (cursor.moveToNext());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    context,
                    R.layout.simple_list_item,
                    list );
            listView.setAdapter(arrayAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
