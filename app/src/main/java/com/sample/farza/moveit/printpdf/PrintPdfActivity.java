package com.sample.farza.moveit.printpdf;

/**
 * Created by farzaali on 5/19/16.
*/

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.farza.moveit.R;
import com.sample.farza.moveit.barcodegeneration.QRActivity;
import com.sample.farza.moveit.database.DataSource;

import java.util.ArrayList;
import java.util.List;

public class PrintPdfActivity extends Activity {

    private static String FILE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/filename.pdf";

    EditText fname, fcontent, fnameread;
    Button write, read, generateBarcode;
    TextView filecon;
    List<String> itemToPrint = new ArrayList<>();
    DataSource dataSource;
    FileOperations fileOperations = new FileOperations();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_create);
        dataSource = new DataSource(this);

        write = (Button) findViewById(R.id.btnwrite);
        read = (Button) findViewById(R.id.btnread);
        filecon = (TextView) findViewById(R.id.filecon);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                dataSource.open();
                itemToPrint = dataSource.getItemNameToPrint();

                FileOperations fop = new FileOperations();
                fop.write(itemToPrint);
                if (fop.write(itemToPrint)) {
                    Toast.makeText(getApplicationContext(),
                            "File created", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), "I/O error",
                            Toast.LENGTH_SHORT).show();
                }

                //Intent printIntent = new Intent(NewMoveActivity.this, PrintDialogActivity.class);
                Intent printIntent = new Intent(PrintPdfActivity.this, PrintDialogActivity.class);
                //docUri - URI of the document to be printed
                //docMimeType - MIME type of the document to be printed. We recommend that you use PDF (application/pdf) format
                //docTitle - title of the printed document, arbitrary string that will be shown on the GCP management console as the print job's title

                try {

                    Uri myUri = Uri.parse("file://" + FILE);

                    printIntent.setDataAndType(myUri, "application/pdf");
                }

                catch (Exception e) {
                    e.printStackTrace();
                }
                printIntent.putExtra("title", "Moving List");
                startActivity(printIntent);
                dataSource.close();
            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dataSource.open();
                //String readfilename = fnameread.getText().toString();
                FileOperations fop = new FileOperations();
                String text = fop.read();
                if (text != null) {
                    filecon.setText(text);
                } else {
                    Toast.makeText(getApplicationContext(), "File not Found",
                            Toast.LENGTH_SHORT).show();
                    filecon.setText(null);
                }
                dataSource.close();
            }
        });

        generateBarcode = (Button)findViewById(R.id.barcodeGenerateButton);
        generateBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrintPdfActivity.this, QRActivity.class);
                startActivity(intent);
            }
        });
    }
}