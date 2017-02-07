package com.sample.farza.moveit.barcodescan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.farza.moveit.R;


/**
 * Created by farzaali on 5/21/16.
 */
public class BarcodeActivity extends AppCompatActivity implements View.OnClickListener{
    private Button scanBtn;
    private TextView formatTxt, contentTxt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_scan);
        scanBtn = (Button)findViewById(R.id.scan_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);

        scanBtn.setOnClickListener(this);


    }

    public void onClick(View v){
        //respond to clicks
        if(v.getId()==R.id.scan_button){
        //scan
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
            //At this point, the scanner will start if it's installed on the user's device.
            // If not, they'll be prompted to download it. The results of the scan will be returned to the main activity
            // where scanning was initiated,
            // so we'll be able to retrieve it in the onActivityResult method.


        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            //we have a result
            //The Intent Result object provides methods to retrieve
            // the content of the scan and the format of the data returned from it. Retrieve the content as a string value.
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText("CONTENT: " + scanContent);

        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
