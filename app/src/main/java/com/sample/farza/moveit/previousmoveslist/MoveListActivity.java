package com.sample.farza.moveit.previousmoveslist;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sample.farza.moveit.R;
import com.sample.farza.moveit.data.Move;
import com.sample.farza.moveit.database.DataSource;

import java.util.List;

/**
 * Created by farzaali on 5/21/16.
 */
public class MoveListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DataSource dataSource;

    //DatabaseHelper databaseHelper;
    //List<Photo> photos;
    List<Move> moveTableData;
    ListView moveList;
    MoveArrayAdapter moveArrayAdapter;
    SQLiteDatabase sqLiteDatabase;

   // Boolean photoAdded = false;
    String moveName; //move name is to retrieve the move id and insert into item table when creating new item




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move_listview);

        // Intent intent = getIntent();
        // moveName = intent.getStringExtra("moveName");

        dataSource = new DataSource(this);
        dataSource.open();

        moveTableData = dataSource.getAllPassiveMoveNames();

        moveList = (ListView)findViewById(R.id.moveListView);

        //photos contains all the data stored in the table
        moveArrayAdapter = new MoveArrayAdapter(this, R.layout.move_custom_row, moveTableData);
        moveList.setAdapter(moveArrayAdapter);
        moveArrayAdapter.notifyDataSetChanged();
        moveList.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        //pass arraylist of photos to photo description class
        Move move = moveTableData.get(position);

        //Start the photo description activity on click of list view
        Intent intent = new Intent(MoveListActivity.this, PreviousMoveItemListActivity.class);
        intent.putExtra("moveName", move.getName());
        startActivity(intent);
    }




}

