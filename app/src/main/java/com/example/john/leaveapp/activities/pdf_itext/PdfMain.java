package com.example.john.leaveapp.activities.pdf_itext;

/**
 * Created by john on 4/17/18.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.core.ReturnCursor;
import com.example.john.leaveapp.core.UserDetails;
import com.example.john.leaveapp.utils.Constants;
import com.example.john.leaveapp.utils.DateTime;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.john.leaveapp.utils.Constants.config.DEPARTMENT_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVETYPE_ID;
import static com.example.john.leaveapp.utils.Constants.config.LEAVE_STATUS;
import static com.example.john.leaveapp.utils.Constants.config.STAFFL_FNAME;
import static com.example.john.leaveapp.utils.Constants.config.TABLE_STAFF;

public class PdfMain extends Activity {
    private Button b;
    private PdfPCell cell;
    private String textAnswer;
    private Image bgImage;
    ListView list;
    private String path;
    private File dir;
    private File file;
    private Context context = this;

    //use to set background color
    BaseColor myColor = WebColors.getRGBColor("#9E9E9E");
    BaseColor myColor1 = WebColors.getRGBColor("#757575");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_main);
        b = (Button) findViewById(R.id.button1);
        list = (ListView) findViewById(R.id.list);

        //creating new file path
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Leave/PDF Files";
        dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    createPDF();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//getting files from directory and display in listview
        try {

            final ArrayList<String> FilesInFolder = GetFiles("/sdcard/Leave/PDF Files");
            if (FilesInFolder.size() != 0)
                list.setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, FilesInFolder));

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    // Clicking on items
                    try{
                        Intent intent = new Intent(context, PDF_Preview.class);
                        intent.putExtra("filePath",dir+"/"+FilesInFolder );
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> GetFiles(String DirectoryPath) {
        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);

        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0)
            return null;
        else {
            for (int i = 0; i < files.length; i++)
                MyFiles.add(files[i].getName());
        }

        return MyFiles;
    }


    public void createPDF() throws FileNotFoundException, DocumentException {

        //create document file
        try {
            Log.e("PDFCreator", "PDF Path: " + path);
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            file = new File(dir, "leave PDF" + sdf.format(Calendar.getInstance().getTime())+ DateTime.getCurrentTime() + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            Document document = new Document();
            PdfPTable table = new PdfPTable(new float[] {2, 4,4,4,8,4,4 });
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("#");
            table.addCell("First Name");
            table.addCell("Last Name");
            table.addCell("Department");
            table.addCell("Leave Type");
            table.addCell("Leave Date");
            table.addCell("Return Date");
            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j=0;j<cells.length;j++){
                cells[j].setBackgroundColor(BaseColor.GRAY);
            }

            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_LEAVE+" n, "+Constants.config.TABLE_STAFF+" s, "+Constants.config.TABLE_LEAVE_TYPE+" t, "+Constants.config.TABLE_DEPARTMENT+" d" +
                    " WHERE  s."+Constants.config.DEPARTMENT_ID+" = d."+Constants.config.DEPARTMENT_ID+"" +
                    "  AND a."+Constants.config.LEAVE_ID+" = n."+Constants.config.LEAVE_ID+"  AND t."+ LEAVETYPE_ID+" = n."+LEAVETYPE_ID+"  " +
                    " AND s."+Constants.config.STAFF_ID+" = a."+Constants.config.STAFF_ID+" ORDER BY a."+Constants.config.DATE+" DESC";

            Cursor cursor = ReturnCursor.getCursor(query,context);
            int count = 0;
            if (cursor.moveToFirst()){
                do {
                    count ++;
                    table.addCell(count+"");
                    table.addCell(cursor.getString(cursor.getColumnIndex(Constants.config.STAFFL_FNAME)));
                    table.addCell(cursor.getString(cursor.getColumnIndex(Constants.config.STAFFL_LNAME)));
                    table.addCell(cursor.getString(cursor.getColumnIndex(Constants.config.DEPARTMENT_NAME)));
                    table.addCell(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVETYPE_NAME)));
                    table.addCell(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVE_FROM)));
                    table.addCell(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVE_TO)));
                }while (cursor.moveToNext());
            }


            PdfWriter writer = PdfWriter.getInstance(document, fOut);
            // add header and footer
            HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            writer.setPageEvent(event);

            document.open();
            Paragraph preface = new Paragraph("MBARARA UNIVERSITY OF SCIENCE AND TECHNOLOGY");
            preface.setAlignment(Element.ALIGN_CENTER);
            document.add(preface);
            Paragraph preface2 = new Paragraph("LEAVE REPORT");
            preface2.setAlignment(Element.ALIGN_CENTER);
            //// document.newPage();
            document.add(preface2);
            document.add(new Paragraph("."));
            document.add(new Paragraph("."));
            document.add(new Paragraph("."));
            document.add(table);
            document.close();
            System.out.println("Done");
            Toast.makeText(context,"PDF Created Successfully..!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
