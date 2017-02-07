package com.sample.farza.moveit.mailnotification;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sample.farza.moveit.R;

import java.util.ArrayList;

/**
 * Created by farzaali on 5/20/16.
 */
public class Notification extends AppCompatActivity{

    EditText name, newAddress, newPhoneNumber, toEmail;
    Button sendButton, selectEmail;
    CheckBoxActivity checkBoxActivity;

    //to retrieve the selected values from Checkboxactivity
    ArrayList<Integer> selectedCheckBoxes;
    Email[] emails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_email);

        checkBoxActivity = new CheckBoxActivity();
        emails = new Email[16];

        //gets the position of the selected emails
        ArrayList<Integer> fetchList= new ArrayList<Integer>();
        fetchList=  getIntent().getIntegerArrayListExtra("selectedEmails");

        //gets the entire email list from CheckBoxActivity Class
        emails = new Email[] {
                new Email("amyfarza@gmail.com"), new Email("John"), new Email("Sue")

        };

        name = (EditText)findViewById(R.id.nameEditText);
        newAddress = (EditText)findViewById(R.id.newAddressEditText);
        newPhoneNumber = (EditText)findViewById(R.id.newPhoneEditText);
        toEmail = (EditText)findViewById(R.id.toEmailEditText);
        String emailString = "";

        //set the email from fetch list
        if(fetchList.size() != 0){
            for(int i = 0; i< fetchList.size(); i++){
                System.out.println("Size of array list: "+fetchList.size());
                emailString += emails[fetchList.get(i)]+ " , ";
                toEmail.setText(emailString);
            }
        }


        sendButton = (Button)findViewById(R.id.sendMailButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
       // selectEmail = (Button)findViewById(R.id.selectEmailButton);




    }

    private void sendEmail() {

        Log.i("Send email", "");
        String []toEmailString = {toEmail.getText().toString()};
        //String[] TO = {selectedCheckBoxes.get(1).toString()};
        String fromName = name.getText().toString();
        //String[] CC = {""};
        String emailBody = "Hello there,"+"\n\n"+"We've moved to our new place! Please do drop by this Sunday at "+newAddress.getText().toString()+" . " + "\n\n"+"Save this number too :"+newPhoneNumber.getText().toString()+ "."+ "\n\n"+ "Regards, " + "\n\n"+fromName;
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, toEmailString);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "We've moved!");
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Notification.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


}
