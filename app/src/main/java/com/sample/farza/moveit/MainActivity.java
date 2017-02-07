package com.sample.farza.moveit;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sample.farza.moveit.barcodescan.BarcodeActivity;
import com.sample.farza.moveit.database.DataSource;
import com.sample.farza.moveit.mailnotification.CheckBoxActivity;
import com.sample.farza.moveit.previousmoveslist.MoveListActivity;

public class MainActivity extends AppCompatActivity {

    private Button newMove, packingTips, sendNotification, scanQRButton, previousMove;
    SQLiteDatabase sqLiteDatabase;
    DataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new DataSource(this);
        dataSource.open();

        newMove = (Button)findViewById(R.id.newMoveButton);
        packingTips = (Button)findViewById(R.id.packingTipsButton);
        packingTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PackingTipsVideo.class);
                startActivity(intent);
            }
        });

        newMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                final View moveDialogView = factory.inflate(
                        R.layout.new_move_enter_name, null);
                final AlertDialog nameDialog = new AlertDialog.Builder(MainActivity.this).create();
                nameDialog.setTitle("New Move");
                nameDialog.setView(moveDialogView);

                //when presses ok button
                moveDialogView.findViewById(R.id.OKButton).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Clicked OK", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(MainActivity.this, NewMoveActivity.class);
                        EditText newMoveName = (EditText)nameDialog.findViewById(R.id.newMoveEditText);
                        String moveName = newMoveName.getText().toString();
                       //Integer moveId = dataSource.getMoveId(moveName);
                        intent.putExtra("moveName",moveName);

                        //enter data into move table
                        dataSource.open();
                        dataSource.createMove(moveName);
                        dataSource.close();
                        Toast.makeText(getApplicationContext(), "Move saved.", Toast.LENGTH_SHORT).show();
                        finish();
                        nameDialog.dismiss();
                        startActivity(intent);
                    }
                });
                moveDialogView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Clicked Cancel", Toast.LENGTH_LONG).show();
                        nameDialog.dismiss();

                    }
                });

                nameDialog.show();

            }
        });

        sendNotification = (Button)findViewById(R.id.notificationButton);
        sendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheckBoxActivity.class);
                startActivity(intent);
            }
        });

        scanQRButton = (Button)findViewById(R.id.scanQRButton);
        scanQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BarcodeActivity.class);
                startActivity(intent);
            }
        });

        previousMove = (Button)findViewById(R.id.previousMoveButton);
        previousMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MoveListActivity.class);
                startActivity(intent);
            }
        });
    }
}
