package com.example.john.leaveapp.fragments.us_fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.john.leaveapp.R;

/**
 * Created by john on 2/28/18.
 */

public class PlaceholderFragment2 extends Fragment implements SelectFragment.OnFragmentInteractionListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    ImageView img;
    private static TextView textView;
    int[] bgs = new int[]{R.drawable.ic_flight_24dp, R.drawable.ic_mail_24dp, R.drawable.ic_explore_24dp};
    public PlaceholderFragment2() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment2 newInstance(int sectionNumber) {
        PlaceholderFragment2 fragment = new PlaceholderFragment2();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.anual_form_layout, container, false);
        textView =  (TextView) rootView.findViewById(R.id.leaveType);
        Bundle bundle = this.getArguments();
        String radioValue = bundle.getString("leave_type");
        //textView.setText(radioValue);
        //Log.e("PagerActivity_Tag2","Value: "+radioValue);
        return rootView;
    }
    public static void updateText(String radioValue) {
        Log.e("PagerActivity_Tag2","Value: "+radioValue);
        textView.setText(radioValue);
    }
    @Override
    public void onFragmentInteraction(String name, String desc) {
        Log.e("Tag","Name:"+name+"\tDesc: "+desc);
        textView.setText(name);
    }
}

