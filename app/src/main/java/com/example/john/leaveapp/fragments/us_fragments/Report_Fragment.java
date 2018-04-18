package com.example.john.leaveapp.fragments.us_fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.john.leaveapp.activities.NotificationActivity;
import com.example.john.leaveapp.core.ReturnCursor;
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.utils.Constants;

/**
 * Created by john on 2/24/18.
 */
public class Report_Fragment extends Fragment {
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
        View view = inflater.inflate(R.layout.us_report, container, false);

        return view;
    }

}
