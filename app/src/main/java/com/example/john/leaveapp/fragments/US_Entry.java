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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.db_operartions.Secretary;
import com.example.john.leaveapp.db_operartions.University;
import com.example.john.leaveapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2/28/18.
 */

public class US_Entry extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    int[] bgs = new int[]{R.drawable.ic_flight_24dp, R.drawable.ic_mail_24dp, R.drawable.ic_explore_24dp};

    private Activity activity;
    public US_Entry() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static US_Entry newInstance(int sectionNumber) {
        US_Entry fragment = new US_Entry();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.us_entry, container, false);
        Spinner us_spinner = (Spinner) rootView.findViewById(R.id.us_spinner);
        Button submitBtn = (Button) rootView.findViewById(R.id.submitBtn);

        List<String > staff = new ArrayList<>();
        final List<Integer > id_ = new ArrayList<>();
        staff.add("--- Select Staff Name ----");
        id_.add(0);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Cursor cursor = new University(activity).selectAll();
                    if (cursor != null){
                        if (cursor.moveToFirst()){
                            do {
                                int id = cursor.getInt(cursor.getColumnIndex(Constants.config.UNIVERSITY_ID));
                                String message = new Secretary(activity).save(id,id_.get(0));
                                Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
                            }while (cursor.moveToNext());
                        }else {
                            Toast.makeText(activity,"University name not defined..!",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(activity,"University name not defined..!",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_spinner_item, staff);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        us_spinner.setAdapter(dataAdapter);
        return rootView;
    }
}