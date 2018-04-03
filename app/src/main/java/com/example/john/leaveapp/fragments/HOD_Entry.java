package com.example.john.leaveapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.adapter.Other_Adapters;
import com.example.john.leaveapp.db_operartions.Departments;
import com.example.john.leaveapp.db_operartions.Faculty;
import com.example.john.leaveapp.utils.Constants;
import com.example.john.leaveapp.utils.ListView_Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by john on 2/28/18.
 */

public class HOD_Entry extends Fragment implements Faculty_Entry.OnFacultyListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    Activity activity;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Spinner faculty_spinner;
    private static final String TAG = "HOD_Entry";

    int[] bgs = new int[]{R.drawable.ic_flight_24dp, R.drawable.ic_mail_24dp, R.drawable.ic_explore_24dp};
    private ListView listView;
    List<String> lList = new ArrayList<>();
    List<Integer> lID = new ArrayList<>();
    public HOD_Entry() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HOD_Entry newInstance(int sectionNumber) {
        HOD_Entry fragment = new HOD_Entry();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.activity = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.hod_entry, container, false);
        faculty_spinner = (Spinner) rootView.findViewById(R.id.faculty_spinner);
        final EditText input_name = (EditText) rootView.findViewById(R.id.input_name);
        listView = (ListView) rootView.findViewById(R.id.listViewD);
        Button submit_details = (Button) rootView.findViewById(R.id.submit_details);
        setSpinner();
        setVal();
        submit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = faculty_spinner.getSelectedItem().toString().trim();
                String name_2 = input_name.getText().toString().trim();
                try {
                    if (!name.equals("") && !name_2.equals("")){
                        int id = 0;
                        for (int i = 0; i < lList.size(); i++) {
                            if (lList.get(i).equals(name)) {
                                id = lID.get(i);
                            }
                        }
                        String message = new Departments(activity).save(name_2, id);
                        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
                        if (message.equals("Department Details saved!")){
                            setVal();
                            input_name.setText("");
                        }
                    }else {
                        Toast.makeText(activity,"Enter a valid Name..!",Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        try{
            ListView_Util.setListViewHeightBasedOnChildren(listView);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView;
    }
    private void setSpinner(){
        lID = new ArrayList<>();
        lList = new ArrayList<>();
        try{
            Cursor cursor = new Faculty(activity).selectAll();
            if (cursor.moveToFirst()){
                do {
                    lList.add(cursor.getString(cursor.getColumnIndex(Constants.config.FACULTY_NAME)));
                    lID.add(cursor.getInt(cursor.getColumnIndex(Constants.config.FACULTY_ID)));
                }while (cursor.moveToNext());
            }
            //TODO:: ... Setting the spinner....!!!!..
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity,
                    android.R.layout.simple_spinner_item, lList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            faculty_spinner.setAdapter(dataAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setVal(){
        Set<String> facSet = new LinkedHashSet<>();
        List<String> depList = new ArrayList<>();
        List<String> facList = new ArrayList<>();
        List<Integer> depID = new ArrayList<>();
        List<Integer> facID = new ArrayList<>();
        try{
            Cursor cursor = new Departments(activity).selectAll();
            if (cursor.moveToFirst()){
                do {
                    facSet.add(cursor.getString(cursor.getColumnIndex(Constants.config.FACULTY_NAME)));
                    facID.add(cursor.getInt(cursor.getColumnIndex(Constants.config.FACULTY_ID)));
                    facList.add(cursor.getString(cursor.getColumnIndex(Constants.config.FACULTY_NAME)));
                    depID.add(cursor.getInt(cursor.getColumnIndex(Constants.config.DEPARTMENT_ID)));
                    depList.add(cursor.getString(cursor.getColumnIndex(Constants.config.DEPARTMENT_NAME)));
                }while (cursor.moveToNext());
            }

            Log.e(TAG,"List: "+facList+","+depList);
            //TODO::: ..!
            Iterator iterator = facSet.iterator();
            List<String> fac = new ArrayList<>();
            List<Integer> id = new ArrayList<>();
            while (iterator.hasNext()){
                fac.add((String) iterator.next());
                id.add(0);
            }
            List<List<String>> depLis1 = new ArrayList<>();
            List<List<Integer>> depID1 = new ArrayList<>();
            for (int i=0; i<fac.size(); i++){
                List<Integer> dID = new ArrayList<>();
                List<String> dList = new ArrayList<>();
                for (int j=0; j<facList.size(); j++){
                    if (fac.get(i).equals(facList.get(j))){
                        dID.add(depID.get(j));
                        dList.add(depList.get(j));
                        id.set(i, facID.get(j));
                    }
                }
                depID1.add(dID);
                depLis1.add(dList);
            }
            Log.e(TAG,fac.size()+" Size: "+depLis1.size());
            //TODO ... >>>>> ...!
            Other_Adapters adapters = new Other_Adapters(activity,fac,id,depLis1,depID1);
            listView.setAdapter(adapters);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onFacultyListener(String name, String desc) {
        setSpinner();
    }
}