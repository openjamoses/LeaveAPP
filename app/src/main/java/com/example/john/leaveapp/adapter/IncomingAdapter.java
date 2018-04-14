package com.example.john.leaveapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.core.ReturnCursor;
import com.example.john.leaveapp.db_operartions.Apply;
import com.example.john.leaveapp.utils.Constants;

import java.util.List;

import static com.example.john.leaveapp.utils.Constants.config.LEAVE_ID;

/**
 * Created by john on 3/12/18.
 */

public class IncomingAdapter extends BaseAdapter {
    Context context;
    List<String> name,type,date_from,date_to,status1,status2, phone;
    List<Integer> leave_id;

    LayoutInflater inflter;
    public IncomingAdapter(Context context, List<String> name, List<Integer> leave_id,List<String> type, List<String> date_from,List<String> date_to,List<String> status1,List<String> status2,List<String> phone) {
        this.context = context;
        this.name = name;
        this.date_from = date_from;
        this.date_to = date_to;
        this.status1 = status1;
        this.status2 = status2;
        this.phone = phone;
        this.type = type;
        this.leave_id = leave_id;

        inflter = (LayoutInflater.from(context));
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
        view = inflter.inflate(R.layout.list_incoming, null);
        TextView nameText = (TextView) view.findViewById(R.id.nameText);
        TextView typeText = (TextView) view.findViewById(R.id.typeText);
        TextView datefromText = (TextView) view.findViewById(R.id.datefromText);
        TextView datetoText = (TextView) view.findViewById(R.id.datetoText);
        TextView status1Text = (TextView) view.findViewById(R.id.status1Text);
        TextView status2Text = (TextView) view.findViewById(R.id.status2Text);
        TextView status3Text = (TextView) view.findViewById(R.id.status3Text);
        LinearLayout parent_layout = (LinearLayout) view.findViewById(R.id.parent_layout);

        try {
            nameText.setText(name.get(i));
            typeText.setText(type.get(i));
            datefromText.setText("Leave From: "+date_from.get(i));
            datetoText.setText("Leave To:  "+date_to.get(i));
            status1Text.setText("HOD Approval: "+status1.get(i));
            status2Text.setText("U.S Approval: "+status2.get(i));
            status3Text.setText("Tel Number: "+phone.get(i));
            setOnclick(parent_layout, leave_id.get(i),name.get(i),type.get(i),date_from.get(i),date_to.get(i));
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }
    public void setOnclick(final LinearLayout layout, final int id, final String name, final String type, final String start, final String end) {
        try{
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(context, layout);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.leave_menu);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_detail:
                                    //handle menu1 click
                                    //Toast.makeText(context,"Details: To be Implemented.!",Toast.LENGTH_SHORT).show();
                                    popdetailsDialog(id,type);
                                    break;
                                case R.id.action_update:
                                    //handle menu2 click
                                    popUpdateDialog(id,name,type,start,end);
                                    //Toast.makeText(context,"Updates: To be Implemented.!",Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            return false;
                        }
                    });
                    //displaying the popup
                    popup.show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param leave_id
     * @param type
     */
    private void popdetailsDialog(int leave_id, String type){

        final AlertDialog dialog;
        try{
            final AlertDialog.Builder alert = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.leave_details_dialog, null);
            // this is set the view from XML inside AlertDialog
            alert.setView(view);
            Button close_btn = (Button) view.findViewById(R.id.close_btn);
            TextView name_text = (TextView) view.findViewById(R.id.name_text);
            TextView department_text = (TextView) view.findViewById(R.id.department_text);
            TextView designation_text = (TextView) view.findViewById(R.id.designation_text);
            TextView asuption_text = (TextView) view.findViewById(R.id.asuption_text);
            TextView promotion_text = (TextView) view.findViewById(R.id.promotion_text);
            TextView salary_text = (TextView) view.findViewById(R.id.salary_text);
            TextView return_text = (TextView) view.findViewById(R.id.return_text);
            TextView lleave_text = (TextView) view.findViewById(R.id.lleave_text);
            TextView balance_text = (TextView) view.findViewById(R.id.balance_text);
            TextView entitlement_text = (TextView) view.findViewById(R.id.entitlement_text);
            TextView lnow_text = (TextView) view.findViewById(R.id.lnow_text);
            TextView phone_text = (TextView) view.findViewById(R.id.phone_text);
            TextView from_text = (TextView) view.findViewById(R.id.from_text);
            TextView to_text = (TextView) view.findViewById(R.id.to_text);
            TextView applied_text = (TextView) view.findViewById(R.id.applied_text);


            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_LEAVE+" n, "+Constants.config.TABLE_STAFF+" s WHERE " +
                    " a."+Constants.config.LEAVE_ID+" = n."+Constants.config.LEAVE_ID+" AND a."+Constants.config.LEAVETYPE_ID+" = '"+type+"'" +
                    " AND s."+Constants.config.STAFF_ID+" = a."+Constants.config.STAFF_ID+" AND n."+LEAVE_ID+" = '"+leave_id+"' ORDER BY a."+Constants.config.DATE+" DESC";


            try{
                //if (type.equals("Anual Leave")){

                    Cursor cursor = ReturnCursor.getCursor(query,context);
                    if (cursor.moveToFirst()){
                        do {
                            name_text.setText("Applicant: "+cursor.getString(cursor.getColumnIndex(Constants.config.STAFFL_FNAME))+" "+
                                    cursor.getString(cursor.getColumnIndex(Constants.config.STAFFL_LNAME)));
                            phone_text.setText(cursor.getString(cursor.getColumnIndex(Constants.config.STAFF_PHONE)));
                            from_text.setText(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVE_FROM)));
                            to_text.setText(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVE_TO)));
                            department_text.setText("");
                            designation_text.setText(cursor.getString(cursor.getColumnIndex(Constants.config.STAFF_ROLE)));
                            asuption_text.setText(cursor.getString(cursor.getColumnIndex(Constants.config.DATE_ASSUMPTION)));
                            promotion_text.setText(cursor.getString(cursor.getColumnIndex(Constants.config.DATE_PROMOTION)));
                            salary_text.setText(cursor.getString(cursor.getColumnIndex(Constants.config.STAFF_SALARY)));
                            return_text.setText(cursor.getString(cursor.getColumnIndex(Constants.config.DATE_RETURN)));
                            lleave_text.setText(cursor.getString(cursor.getColumnIndex(Constants.config.DAYS_TAKEN)));
                            balance_text.setText(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVE_TO)));
                            entitlement_text.setText(cursor.getString(cursor.getColumnIndex(Constants.config.ENTITLEMENT)));
                            lnow_text.setText(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVE_NOW)));
                            applied_text.setText("Applied Date: "+cursor.getString(cursor.getColumnIndex(Constants.config.DATE))+" "+
                                    cursor.getString(cursor.getColumnIndex(Constants.config.TIME)));

                        }while (cursor.moveToNext());
                    }
               // }

            }catch (Exception e){
                e.printStackTrace();
            }
            // disallow cancel of AlertDialog on click of back button and outside touch
            alert.setCancelable(false);

            dialog = alert.create();
            dialog.show();

            close_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     *
     * @param leave_id
     * @param name_
     * @param type
     * @param start
     * @param end
     */
    private void popUpdateDialog(int leave_id,String name_, String type, String start, String end){
        final AlertDialog dialog;
            try{
                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.accept_dialog, null);
                // this is set the view from XML inside AlertDialog
                alert.setView(view);
                Button cancel_btn = (Button) view.findViewById(R.id.btn_reject);
                Button accept_btn = (Button) view.findViewById(R.id.btn_accept);

                TextView name_text = (TextView) view.findViewById(R.id.name_text);
                TextView text_department = (TextView) view.findViewById(R.id.text_department);
                TextView text_type = (TextView) view.findViewById(R.id.text_type);
                TextView text_start = (TextView) view.findViewById(R.id.text_start);
                TextView text_end = (TextView) view.findViewById(R.id.text_end);
                TextView text_gender = (TextView) view.findViewById(R.id.text_gender);

                try{
                    if (type.equals("Anual Leave")){
                        Cursor cursor = new Apply(context).getAnual();
                        if (cursor.moveToFirst()){
                            do {
                                name_text.setText(name_);
                                text_type.setText(type);
                                text_gender.setText(cursor.getString(cursor.getColumnIndex(Constants.config.STAFF_GENDER)));
                                text_start.setText(start);
                                text_end.setText(end);
                            }while (cursor.moveToNext());
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);

                dialog = alert.create();
                dialog.show();

                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                accept_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }

    }
}
