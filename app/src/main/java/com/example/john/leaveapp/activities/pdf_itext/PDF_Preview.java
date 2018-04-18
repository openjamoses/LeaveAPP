package com.example.john.leaveapp.activities.pdf_itext;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.activities.ApplyActivity;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by john on 4/18/18.
 */

public class PDF_Preview extends AppCompatActivity {
    private PDFView pdfView;
    private String filePath = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_pdf);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        filePath = getIntent().getStringExtra("filePath");
       setPdfPreview();
    }

    private void setPdfPreview(){
        try {

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
