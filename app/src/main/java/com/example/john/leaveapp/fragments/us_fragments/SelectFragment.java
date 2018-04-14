package com.example.john.leaveapp.fragments.us_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.john.leaveapp.R;

/**
 * Created by john on 2/28/18.
 */

public class SelectFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private OnFragmentInteractionListener listener;
    ImageView img;
    int[] bgs = new int[]{R.drawable.ic_flight_24dp, R.drawable.ic_mail_24dp, R.drawable.ic_explore_24dp};
    public SelectFragment() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SelectFragment newInstance(int sectionNumber) {
        SelectFragment fragment = new SelectFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener ){
            listener = (OnFragmentInteractionListener)context;
        }else {
            throw new RuntimeException(context.toString()+"" +
                    " Must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
    public interface OnFragmentInteractionListener{
        void onFragmentInteraction(String name, String desc);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.select_typefragment, container, false);
        final RadioGroup radioGroup  = (RadioGroup) rootView.findViewById(R.id.radioGroup);
        RadioButton radioAnual = (RadioButton) rootView.findViewById(R.id.radioAnual);
        RadioButton radioMaternity = (RadioButton) rootView.findViewById(R.id.radioMaternity);
        //Todo:: <<<<>>>> <<<<<<<<<<<<<<<<<<<< >>>>>>>>>>>>>>>>>>>>>>>
        radioAnual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(radioGroup,rootView);
            }
        });
        radioMaternity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(radioGroup,rootView);
            }
        });
        return rootView;
    }

    private void sendData(RadioGroup radioGroup,View rootView){
        if(radioGroup.getCheckedRadioButtonId() != -1 ) {
            int driesId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            String radioValue = ((RadioButton) rootView.findViewById(driesId)).getText().toString();
            //PlaceholderFragment2 fragmentB = new PlaceholderFragment2();
            //Bundle bundle = new Bundle();
            //bundle.putString("leave_type", radioValue);
            //fragmentB.setArguments(bundle);
            //fragmentB.updateText(radioValue);
            //PlaceholderFragment2.updateText(radioValue);
            if (listener != null){
                listener.onFragmentInteraction(radioValue,"Desc Forms");
            }else {
                Log.e("TAG","Interface is Null");
            }
            Log.e("PagerActivity_Tag1","Value: "+radioValue);

            try {
                PlaceholderFragment2 nextFrag = new PlaceholderFragment2();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(nextFrag)
                        .addToBackStack(null)
                        .commit();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}