package com.sample.farza.moveit;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.farza.moveit.database.DataSource;
import com.sample.farza.moveit.listitems.ItemListActivity;
import com.sample.farza.moveit.movearchived.MoveArchived;
import com.sample.farza.moveit.organize.OrganizeItemsBkup;
import com.sample.farza.moveit.printpdf.PrintPdfActivity;

/**
 * Created by farzaali on 5/13/16.
 */
public class NewMoveActivity extends AppCompatActivity {
    TextView moveName;
    Button itemListButton, printListButton,packedButton, organizeItemsButton;
    EditText numBoxesEditTextAlert;
    DataSource dataSource;
    SQLiteDatabase sqLiteDatabase;
    boolean moveExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_move);
        dataSource = new DataSource(NewMoveActivity.this);
        Intent intent = getIntent();
        final String moveNameString = intent.getStringExtra("moveName");

        moveName = (TextView)findViewById(R.id.moveNameTextView);
        moveName.setText(moveNameString);

        itemListButton = (Button)findViewById(R.id.itemListbutton);
        itemListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMoveActivity.this, ItemListActivity.class);
                intent.putExtra("moveName",moveNameString);
                startActivity(intent);

            }
        });
        printListButton = (Button)findViewById(R.id.printListButton);
        printListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent printIntent = new Intent(NewMoveActivity.this, PrintPdfActivity.class);

                startActivity(printIntent);
            }
        });

        packedButton = (Button)findViewById(R.id.packedReadyButton);
        packedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.open();
                Toast.makeText(NewMoveActivity.this, "Move Details Archived. Please see previous moves.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(NewMoveActivity.this, MoveArchived.class);
                startActivity(intent);
                /*if (dataSource.setStatusPassive() == true){
                   Toast.makeText(NewMoveActivity.this, "Move Details Archived. Please see previous moves.", Toast.LENGTH_LONG).show();
               }

                else{
                   Toast.makeText(NewMoveActivity.this, "Please try once more", Toast.LENGTH_LONG).show();

               }*/
                dataSource.setStatusPassive();

                dataSource.close();
            }
        });

        //********************* Amy *********************

        //Adding onclick listener for organize button for a new move
        organizeItemsButton = (Button) findViewById(R.id.organizeButton);
        organizeItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataSource = new DataSource(NewMoveActivity.this);
                dataSource.open();
                Integer mMoveId = dataSource.getMoveId(moveNameString);
                moveExists = dataSource.moveAlreadyExists(mMoveId);
                dataSource.close();
                final AlertDialog enterBoxesAlert = new AlertDialog.Builder(NewMoveActivity.this).create();
                if(!moveExists) {
                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(getApplicationContext());
                    View promptsView = li.inflate(R.layout.organize_enter_num_boxes_alert, null);




                    // set prompts.xml to alertdialog builder
                    enterBoxesAlert.setView(promptsView);

                    final EditText userInputNumBoxes = (EditText) promptsView
                            .findViewById(R.id.etNumBoxesNM);

                    enterBoxesAlert.setTitle("Enter number of boxes");
                    enterBoxesAlert.setMessage("How many boxes do you need?");
                    enterBoxesAlert.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    enterBoxesAlert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            EditText numberOfBoxes = (EditText) enterBoxesAlert.findViewById(R.id.etNumBoxesNM);

                            if (numberOfBoxes.getText().toString()!= "") {

                                // Go to animal details page(second activity)

                                //Get move id, pass boxnumbers and moveid


                                Intent showItemsToBoxesIntent = new Intent(NewMoveActivity.this, OrganizeItemsBkup.class);
                                //   Intent showItemsToBoxesIntent = new Intent(NewMoveActivity.this, OrganizeItems.class);
                                String boxes = numberOfBoxes.getText().toString();

                                //Integer moveId = dataSource.getMoveId(moveName);
                                showItemsToBoxesIntent.putExtra("numberOfBoxes", boxes);
                                showItemsToBoxesIntent.putExtra("movename", moveNameString);
                                showItemsToBoxesIntent.putExtra("moveExists", moveExists);
                                startActivity(showItemsToBoxesIntent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Please enter the number of boxes to continue", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                    enterBoxesAlert.show();
                }
                else{
                    Intent showItemsToBoxesIntent = new Intent(NewMoveActivity.this, OrganizeItemsBkup.class);
                    showItemsToBoxesIntent.putExtra("moveExists", moveExists);
                    showItemsToBoxesIntent.putExtra("numberOfBoxes", "0");
                    showItemsToBoxesIntent.putExtra("movename", moveNameString);
                    startActivity(showItemsToBoxesIntent);
                }



//                if(moveExists){
//                    Intent showItemsToBoxesIntent = new Intent(NewMoveActivity.this, OrganizeItemsBkup.class);
//                    showItemsToBoxesIntent.putExtra("moveExists", moveExists);
//                    showItemsToBoxesIntent.putExtra("numberOfBoxes", 0);
//                    showItemsToBoxesIntent.putExtra("movename", moveNameString);
//                }
//                else{
//                    enterBoxesAlert.show();
//                }


                // Show scary animal caution dialog

            }
        });


    }
}
