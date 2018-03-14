package com.example.john.leaveapp.fragments;

import android.app.Activity;
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
        final Button button = (Button) rootView.findViewById(R.id.button);
        final Button submitBtn = (Button) rootView.findViewById(R.id.submitBtn);
        final EditText editText = (EditText) rootView.findViewById(R.id.editText);
        final TextView uvText = (TextView) rootView.findViewById(R.id.uvText);
        final FloatingActionButton flag = (FloatingActionButton) rootView.findViewById(R.id.flag);
        //flag.setVisibility(View.GONE);
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
                if (!name.equals("") && !file_path.equals("")){
                    String messages = new University(activity).save(name,file_path);
                    Toast.makeText(activity,messages, Toast.LENGTH_SHORT).show();
                    if (messages.equals("University Details saved!")){
                        setView(uvText,button,submitBtn,editText,flag);
                    }
                }else {
                    Toast.makeText(activity,"Name or Image Logo is not defined..!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        setView(uvText,button,submitBtn,editText,flag);

        return rootView;
    }
    private void setView(TextView uvText, Button button, Button submitBtn, EditText editText,FloatingActionButton flag){
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
}