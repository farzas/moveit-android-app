package com.sample.farza.moveit.printpdf;

/**
 * Created by farzaali on 5/19/16.
*/

import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class FileOperations {

    private static String FILE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/filename.pdf";
    //List<Item> itemToPrint = new ArrayList<>();
    //DataSource dataSource = new DataSource(this);

    File file;
    public FileOperations() {
    }

    public Boolean write(List<String> itemToPrint) {
        try {

            file = new File(FILE);
            System.out.println("File Path: "+FILE);
            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // step 1
            Document document = new Document();
            // step 2
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
            // step 3
            document.open();
            // step 4

            for(int i = 0; i<itemToPrint.size(); i++){
                document.add(new Paragraph(itemToPrint.get(i).toString()));

            }

            // step 5
            document.close();


            Log.d("Success", "Success");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public String getPdfFile(){
        return FILE;
    }

    public String read() {
        BufferedReader br = null;
        String response = null;
        try {
            StringBuffer output = new StringBuffer();
            //String fpath = "/sdcard/" + fname + ".pdf";
            System.out.println("File Path: "+FILE);
            PdfReader reader = new PdfReader(new FileInputStream(FILE));
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);

            StringWriter strW = new StringWriter();

            TextExtractionStrategy strategy;
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                strategy = parser.processContent(i,
                        new SimpleTextExtractionStrategy());

                strW.write(strategy.getResultantText());

            }

            response = strW.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }
}
