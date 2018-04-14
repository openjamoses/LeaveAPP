package com.example.john.leaveapp.fragments.us_fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.john.leaveapp.R;
import com.example.john.leaveapp.activities.firebase.SendNotification;
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.db_operartions.Accompanied;
import com.example.john.leaveapp.db_operartions.Leave;
import com.example.john.leaveapp.db_operartions.Apply;
import com.example.john.leaveapp.db_operartions.Staff;
import com.example.john.leaveapp.utils.Constants;
import com.example.john.leaveapp.utils.DateTime;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static com.example.john.leaveapp.utils.Constants.config.BALANCE_OUTSTANDING;
import static com.example.john.leaveapp.utils.Constants.config.BALANCE_TAKEN;
import static com.example.john.leaveapp.utils.Constants.config.DATE_ASSUMPTION;
import static com.example.john.leaveapp.utils.Constants.config.DATE_PROMOTION;
import static com.example.john.leaveapp.utils.Constants.config.DATE_RETURN;
import static com.example.john.leaveapp.utils.Constants.config.DAYS_TAKEN;
import static com.example.john.leaveapp.utils.Constants.config.ENTITLEMENT;
import static com.example.john.leaveapp.utils.Constants.config.HOST_URL;
import static com.example.john.leaveapp.utils.Constants.config.LEAVEDUE_FROM;
import static com.example.john.leaveapp.utils.Constants.config.LEAVEDUE_TO;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_FROM;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_NOW;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_TO;
import static com.example.john.leaveapp.utils.Constants.config.SIGNATURE;
import static com.example.john.leaveapp.utils.Constants.config.URL_SAVE_LEAVE;

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
    private Activity activity;
    private static final String TAG = "PlaceholderFragment2";

    private EditText input_fullname,input_department,input_designation,input_salary,input_assumption,input_promotion,
            input_last_leave,input_days_last_leave,input_balance,input_entitlement,input_leave_now,input_from,input_to,
            input_balance_outstanding,leave_due,input_due_to;
    private IntlPhoneInput input_phone;
    private Button button;
    int[] bgs = new int[]{R.drawable.ic_flight_24dp, R.drawable.ic_mail_24dp, R.drawable.ic_explore_24dp};
    public PlaceholderFragment2() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
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

