package com.sample.farza.moveit.mailnotification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.sample.farza.moveit.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by farzaali on 5/20/16.
 */
public class CheckBoxActivity extends FragmentActivity {
    private Email[] emails ;
    private ArrayAdapter<Email> listAdapter ;
    Button sendMailAfterSelection;
    GetContactEmails getContactEmails;
    ArrayList<Integer> selectedCheckboxes;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkbox_listview);

        // Find the ListView resource.
        ListView mainListView = (ListView) findViewById( R.id.checkBoxListView );
        getContactEmails= new GetContactEmails();

        //array to store value of selected emails
        selectedCheckboxes=new ArrayList<>();



        // When item is tapped, toggle checked properties of CheckBox and Planet.
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> parent, View item,
                                     int position, long id) {
                Email email = listAdapter.getItem( position );
                email.toggleChecked();
                CheckBoxViewHolder viewHolder = (CheckBoxViewHolder) item.getTag();
                viewHolder.getCheckBox().setChecked( email.isChecked() );



                CheckBox cb = (CheckBox)item.findViewById(R.id.CheckBox01);
                if(cb.isChecked())
                {

                    if(!selectedCheckboxes.contains(position))
                    {
                        selectedCheckboxes.add(position);
                        System.out.println(selectedCheckboxes.get(0));
                    }
                }
                /*else
                {
                    if(selectedCheckboxes.contains(position))
                    {
                        selectedCheckboxes.remove(position);
                    }
                }*/
            }
        });


        // Create and populate planets.
        emails = (Email[]) getLastCustomNonConfigurationInstance() ;
        if ( emails == null ) {
            emails = new Email[] {
                    new Email("amyfarza@gmail.com"), new Email("John"), new Email("Sue")

            };
        }
        ArrayList<Email> emailList = new ArrayList<>();
        //emailList = getContactEmails.getNameEmailDetails();

        //emailList = (ArrayList<Object>)myTempObject.clone();
        emailList.addAll( Arrays.asList(emails) );

        // Set our custom array adapter as the ListView's adapter.
        listAdapter = new CheckboxArrayAdapter(this, emailList);
        mainListView.setAdapter( listAdapter );

        sendMailAfterSelection = (Button)findViewById(R.id.button2);
        sendMailAfterSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckBoxActivity.this, Notification.class);

                intent.putIntegerArrayListExtra("selectedEmails", selectedCheckboxes);
                startActivity(intent);
            }
        });
    }

    public ArrayList<Integer> getSelectedItems(){
        return selectedCheckboxes;
    }

    public Email[] getEmails(){
        return emails;
    }
}
