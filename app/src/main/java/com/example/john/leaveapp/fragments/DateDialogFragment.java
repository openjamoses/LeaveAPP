package com.example.john.leaveapp.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by john on 3/10/18.
 */

public class DateDialogFragment extends DialogFragment {
    public static String TAG = "DateDialogFragment";

    static Context sContext;
    static Calendar sDate;
    static DateDialogFragmentListener sListener;

    public static DateDialogFragment newInstance(Context context, int titleResource, Calendar date){
        DateDialogFragment dialog  = new DateDialogFragment();

        sContext = context;
        sDate = Calendar.getInstance();
        sDate = date;

        Bundle args = new Bundle();
        args.putInt("title", titleResource);
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(sContext, dateSetListener, sDate.get(Calendar.YEAR), sDate.get(Calendar.MONTH), sDate.get(Calendar.DAY_OF_MONTH));
    }

    public void setDateDialogFragmentListener(DateDialogFragmentListener listener){
        sListener = listener;
    }

    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {

                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);

                    //call back to the DateDialogFragment listener
                    sListener.dateDialogFragmentDateSet(newDate);
                }
            };

    //---------------------------------------------------------
    //DateDialogFragment listener interface
    //---------------------------------------------------------
    public interface DateDialogFragmentListener{
        public void dateDialogFragmentDateSet(Calendar date);
    }

}