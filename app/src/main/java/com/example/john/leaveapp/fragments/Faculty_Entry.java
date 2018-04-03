package com.example.john.leaveapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.db_operartions.Faculty;
import com.example.john.leaveapp.db_operartions.University;
import com.example.john.leaveapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2/28/18.
 */

public class Faculty_Entry extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    int[] bgs = new int[]{R.drawable.ic_flight_24dp, R.drawable.ic_mail_24dp, R.drawable.ic_explore_24dp};
    private EditText input_name;
    private Button btn_submit;
    private ListView listView;
    private Activity activity;


    public Faculty_Entry() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Faculty_Entry newInstance(int sectionNumber) {
        Faculty_Entry fragment = new Faculty_Entry();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity =  activity;

    }

    public interface OnFacultyListener{
        void onFacultyListener(String name, String desc);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.faculty_entry, container, false);
        input_name = (EditText) rootView.findViewById(R.id.input_name);
        btn_submit = (Button) rootView.findViewById(R.id.btn_submit);
        listView = (ListView) rootView.findViewById(R.id.listView);

        setValue();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = new University(activity).selectLastID();
                if (!input_name.getText().toString().trim().equals("")){
                    String message = new Faculty(activity).save(input_name.getText().toString().trim(), id);
                    Toast.makeText(activity,message, Toast.LENGTH_SHORT).show();
                    setValue();
                    input_name.setText("");
                }
            }
        });

        return rootView;
    }
    private void setValue(){
        List<String> list = new ArrayList<>();

        try{
            Cursor cursor = new Faculty(activity).selectAll();
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do {
                        list.add(cursor.getString(cursor.getColumnIndex(Constants.config.FACULTY_NAME)));
                    }while (cursor.moveToNext());
                }
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    activity,
                    R.layout.simple_list_item,
                    list );
            listView.setAdapter(arrayAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}