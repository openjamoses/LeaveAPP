package com.example.john.leaveapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.utils.ListView_Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 11/6/17.
 */

public class Other_Adapters extends BaseAdapter {
    Context context;
    List<String> name;
    List<List<String>> dep_name;
    List<List<Integer>> depID;
    List<Integer> facID;

    LayoutInflater inflter;
    public Other_Adapters(Context applicationContext, List<String> name, List<Integer> facID, List<List<String>> dep_name, List<List<Integer>>  depID) {
        this.context = applicationContext;
        this.name = name;
        this.facID = facID;
        this.dep_name = dep_name;
        this.depID = depID;

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.list_facuty_department, null);
        try {

            TextView faculty_txt = (TextView) view.findViewById(R.id.faculty_txt);
            TextView subText = (TextView) view.findViewById(R.id.subText);

            faculty_txt.setText(name.get(i));
            String text = "";
            for (int j=0; j<dep_name.get(i).size(); j++){
                text = text.concat("- "+dep_name.get(i).get(j)+"\n");
            }
            subText.setText(text);
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }
}
