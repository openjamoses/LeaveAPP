package com.example.john.leaveapp.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.db_operartions.Apply;
import com.example.john.leaveapp.db_operartions.University;
import com.example.john.leaveapp.fragments.us_fragments.SelectFragment;
import com.example.john.leaveapp.utils.Constants;

import org.w3c.dom.Text;

import static android.app.Activity.RESULT_OK;

/**
 * Created by john on 2/28/18.
 */

public class UV_Entry extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int SELECTED_PIC = 1;
    private static final int SELECTED_PIC_2 = 2;
    Button button;
    Button submitBtn;
    EditText editText;
    TextView uvText;
    FloatingActionButton flag;
    int[] bgs = new int[]{R.drawable.ic_flight_24dp, R.drawable.ic_mail_24dp, R.drawable.ic_explore_24dp};
    String file_path = "";
    ImageView imageView;

    Activity activity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;

    }
    public UV_Entry() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String[] projection = {MediaStore.Images.Media.DATA};

                Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(projection[0]);
                file_path = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bitmap = BitmapFactory.decodeFile(file_path);
                Drawable drawable = new BitmapDrawable(bitmap);
                imageView.setImageDrawable(drawable);
            }
        }
    }
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static UV_Entry newInstance(int sectionNumber) {
        UV_Entry fragment = new UV_Entry();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    public void chosePics() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PIC);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.uv_entry, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        button = (Button) rootView.findViewById(R.id.button);
        submitBtn = (Button) rootView.findViewById(R.id.submitBtn);
        editText = (EditText) rootView.findViewById(R.id.editText);
        uvText = (TextView) rootView.findViewById(R.id.uvText);
        flag = (FloatingActionButton) rootView.findViewById(R.id.flag);
        flag.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosePics();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString().trim();
                if (!name.equals("")){
                    ProgressDialog dialog = new ProgressDialog(activity);
                    dialog.setMessage("please wait");
                    new University(activity).send(name,dialog);
                    //Toast.makeText(activity,messages, Toast.LENGTH_SHORT).show();
                    //if (messages.equals("University Details saved!")){
                        setView();
                  //  }
                }else {
                    Toast.makeText(activity,"Name or Image Logo is not defined..!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpdateDialog(uvText.getText().toString().trim());
            }
        });
        setView();

        return rootView;
    }
    private void setView(){
        try{
            Cursor cursor = new University(activity).selectAll();
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do {
                        uvText.setText(cursor.getString(cursor.getColumnIndex(Constants.config.UNIVERSITY_NAME)));
                        String path = cursor.getString(cursor.getColumnIndex(Constants.config.UNIVERSITY_LOGO));
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        Drawable drawable = new BitmapDrawable(bitmap);
                        imageView.setImageDrawable(drawable);
                    }while (cursor.moveToNext());

                    button.setVisibility(View.GONE);
                    submitBtn.setVisibility(View.GONE);
                    editText.setVisibility(View.GONE);

                }

                flag.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void popUpdateDialog(String name){
        final AlertDialog dialog;
        try{
            final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            LayoutInflater inflater = LayoutInflater.from(activity);
            View view = inflater.inflate(R.layout.update_university, null);
            // this is set the view from XML inside AlertDialog
            alert.setView(view);
            ImageView close_btn = (ImageView) view.findViewById(R.id.close_btn);
            Button submitBtn = (Button) view.findViewById(R.id.submitBtn);
            final EditText nameText = (EditText) view.findViewById(R.id.editText);
            Button chose_btn = (Button) view.findViewById(R.id.button);
            // disallow cancel of AlertDialog on click of back button and outside touch
            nameText.setText(name+"");
            alert.setCancelable(false);
            dialog = alert.create();
            dialog.show();
            close_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            chose_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chosePics();
                }
            });

            final int id = new University(activity).selectLastID();
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = nameText.getText().toString().trim();
                    if (!name.equals("")){
                        String messages = new University(activity).edit(name,id);
                        Toast.makeText(activity,messages, Toast.LENGTH_SHORT).show();
                        if (messages.equals("University Details updated!")){
                            setView();
                            dialog.dismiss();
                        }
                    }else {
                        Toast.makeText(activity,"Name or Image Logo is not defined..!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            ///todo:::: Make ........
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}