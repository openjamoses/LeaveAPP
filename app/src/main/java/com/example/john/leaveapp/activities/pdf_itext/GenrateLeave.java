package com.example.john.leaveapp.activities.pdf_itext;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.example.john.leaveapp.core.ReturnCursor;
import com.example.john.leaveapp.utils.Constants;
import com.example.john.leaveapp.utils.DateTime;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.john.leaveapp.utils.Constants.config.LEAVETYPE_ID;

/**
 * Created by john on 4/18/18.
 */

public class GenrateLeave {

    public static void createStaff(Context context, String query, String header, String subTitle, File file){
        //create document file
        try {
//            Log.e("PDFCreator", "PDF Path: " + file.getAbsolutePath());
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            //file = new File(dir, "leave PDF" + sdf.format(Calendar.getInstance().getTime())+ DateTime.getCurrentTime() + ".pdf");
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

            /**
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_LEAVE+" n, "+Constants.config.TABLE_STAFF+" s, "+Constants.config.TABLE_LEAVE_TYPE+" t, "+Constants.config.TABLE_DEPARTMENT+" d" +
                    " WHERE  s."+Constants.config.DEPARTMENT_ID+" = d."+Constants.config.DEPARTMENT_ID+"" +
                    "  AND a."+Constants.config.LEAVE_ID+" = n."+Constants.config.LEAVE_ID+"  AND t."+ LEAVETYPE_ID+" = n."+LEAVETYPE_ID+"  " +
                    " AND s."+Constants.config.STAFF_ID+" = a."+Constants.config.STAFF_ID+" ORDER BY a."+Constants.config.DATE+" DESC";

             **/
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
            Paragraph preface = new Paragraph(header);
            preface.setAlignment(Element.ALIGN_CENTER);
            document.add(preface);
            Paragraph preface2 = new Paragraph(subTitle);
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


    public static void createYour(Context context, String query, String header, String subTitle, File file){
        //create document file
        try {
            Log.e("PDFCreator", "PDF Path: " + file.getAbsolutePath());
            FileOutputStream fOut = new FileOutputStream(file);
            Document document = new Document();
            PdfPTable table = new PdfPTable(new float[] {2, 6,4,4});
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("#");
            table.addCell("LeaveType");
            table.addCell("T Applicants");
            table.addCell("DEPARTMENT");
            table.addCell("T Days");
            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j=0;j<cells.length;j++){
                cells[j].setBackgroundColor(BaseColor.GRAY);
            }
            /**
             String query = "SELECT *  FROM" +
             " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_LEAVE+" n, "+Constants.config.TABLE_STAFF+" s, "+Constants.config.TABLE_LEAVE_TYPE+" t, "+Constants.config.TABLE_DEPARTMENT+" d" +
             " WHERE  s."+Constants.config.DEPARTMENT_ID+" = d."+Constants.config.DEPARTMENT_ID+"" +
             "  AND a."+Constants.config.LEAVE_ID+" = n."+Constants.config.LEAVE_ID+"  AND t."+ LEAVETYPE_ID+" = n."+LEAVETYPE_ID+"  " +
             " AND s."+Constants.config.STAFF_ID+" = a."+Constants.config.STAFF_ID+" ORDER BY a."+Constants.config.DATE+" DESC";
             **/
            Cursor cursor = ReturnCursor.getCursor(query,context);
            int count = 0;
            if (cursor.moveToFirst()){
                do {
                    count ++;
                    table.addCell(count+"");
                    table.addCell(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVETYPE_NAME)));
                    table.addCell(cursor.getString(cursor.getColumnIndex(Constants.config.STAFFL_LNAME)));
                    table.addCell(cursor.getString(cursor.getColumnIndex(Constants.config.DEPARTMENT_NAME)));
                    table.addCell(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVE_NOW)));
                }while (cursor.moveToNext());
            }
            PdfWriter writer = PdfWriter.getInstance(document, fOut);
            // add header and footer
            HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            writer.setPageEvent(event);
            document.open();
            Paragraph preface = new Paragraph(header);
            preface.setAlignment(Element.ALIGN_CENTER);
            document.add(preface);
            Paragraph preface2 = new Paragraph(subTitle);
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


    public static void createTYPE(Context context, String query, String header, String subTitle, File file){
        //create document file
        try {
            Log.e("PDFCreator", "PDF Path: " + file.getAbsolutePath());
            FileOutputStream fOut = new FileOutputStream(file);
            Document document = new Document();
            PdfPTable table = new PdfPTable(new float[] {2, 6,6,4,4,4});
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("#");
            table.addCell("FNAME");
            table.addCell("LNAME");
            table.addCell("T Days");
            table.addCell("L-FROM");
            table.addCell("L-TO");
            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j=0;j<cells.length;j++){
                cells[j].setBackgroundColor(BaseColor.GRAY);
            }
            /**
             String query = "SELECT *  FROM" +
             " "+ Constants.config.TABLE_APPLY+" a,"+Constants.config.TABLE_LEAVE+" n, "+Constants.config.TABLE_STAFF+" s, "+Constants.config.TABLE_LEAVE_TYPE+" t, "+Constants.config.TABLE_DEPARTMENT+" d" +
             " WHERE  s."+Constants.config.DEPARTMENT_ID+" = d."+Constants.config.DEPARTMENT_ID+"" +
             "  AND a."+Constants.config.LEAVE_ID+" = n."+Constants.config.LEAVE_ID+"  AND t."+ LEAVETYPE_ID+" = n."+LEAVETYPE_ID+"  " +
             " AND s."+Constants.config.STAFF_ID+" = a."+Constants.config.STAFF_ID+" ORDER BY a."+Constants.config.DATE+" DESC";
             **/
            Cursor cursor = ReturnCursor.getCursor(query,context);
            int count = 0;
            if (cursor.moveToFirst()){
                do {
                    count ++;
                    table.addCell(count+"");
                    table.addCell(cursor.getString(cursor.getColumnIndex(Constants.config.STAFFL_FNAME)));
                    table.addCell(cursor.getString(cursor.getColumnIndex(Constants.config.STAFFL_LNAME)));
                    table.addCell(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVE_NOW)));

                    table.addCell(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVE_FROM)));
                    table.addCell(cursor.getString(cursor.getColumnIndex(Constants.config.LEAVE_TO)));

                }while (cursor.moveToNext());
            }
            PdfWriter writer = PdfWriter.getInstance(document, fOut);
            // add header and footer
            HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            writer.setPageEvent(event);
            document.open();
            Paragraph preface = new Paragraph(header);
            preface.setAlignment(Element.ALIGN_CENTER);
            document.add(preface);
            Paragraph preface2 = new Paragraph(subTitle);
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
