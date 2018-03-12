package com.example.john.leaveapp.fragments.us_fragments;

/**
 * Created by john on 7/28/17.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.activities.us_activities.ApplyActivity;

public class US_DFragment extends Fragment {

    Activity activity ;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.us_dashboard, container, false);
        Button btn_apply = (Button) view.findViewById(R.id.btn_apply);
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(activity, ApplyActivity.class));
            }
        });
        return view;
    }
}
