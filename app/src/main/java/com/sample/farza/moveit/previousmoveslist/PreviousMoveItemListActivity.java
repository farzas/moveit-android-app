package com.sample.farza.moveit.previousmoveslist;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sample.farza.moveit.ItemDescription;
import com.sample.farza.moveit.R;
import com.sample.farza.moveit.data.Item;
import com.sample.farza.moveit.database.DataSource;
import com.sample.farza.moveit.listitems.ItemArrayAdaptor;

import java.util.List;

/**
 * Created by farzaali on 5/21/16.
 */
public class PreviousMoveItemListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DataSource dataSource;

    //DatabaseHelper databaseHelper;
    //List<Photo> photos;
    List<Item> itemTableData;
    ListView itemList;
    ItemArrayAdaptor itemArrayAdaptor;
    SQLiteDatabase sqLiteDatabase;

    String moveName; //move name is to retrieve the move id and insert into item table when creating new item




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list_view);

        // Intent intent = getIntent();
        // moveName = intent.getStringExtra("moveName");

        dataSource = new DataSource(this);
        dataSource.open();

        Intent intent = getIntent();
        moveName = intent.getStringExtra("moveName");
        Integer moveId = dataSource.getMoveId(moveName);
        itemTableData = dataSource.getItemsForAMove(moveId);

        itemList = (ListView)findViewById(R.id.listView);

        //photos contains all the data stored in the table
        itemArrayAdaptor = new ItemArrayAdaptor(this, R.layout.custom_row, itemTableData);
        itemList.setAdapter(itemArrayAdaptor);
        itemArrayAdaptor.notifyDataSetChanged();
        itemList.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        //pass arraylist of photos to photo description class
        Item item = itemTableData.get(position);

        //Start the photo description activity on click of list view
        Intent intent = new Intent(PreviousMoveItemListActivity.this, ItemDescription.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }


//query the table and retrieve all the values
//the result is iterated with the help of cursor
//Pictures are added to the photos array list



}
