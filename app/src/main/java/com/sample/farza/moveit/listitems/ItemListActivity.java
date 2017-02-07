package com.sample.farza.moveit.listitems;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sample.farza.moveit.AddNewItemActivity;
import com.sample.farza.moveit.ItemDescription;
import com.sample.farza.moveit.R;
import com.sample.farza.moveit.data.Item;
import com.sample.farza.moveit.database.DataSource;

import java.util.List;

/**
 * Created by farzaali on 5/14/16.
 */
public class ItemListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

private DataSource dataSource;

        //DatabaseHelper databaseHelper;
        //List<Photo> photos;
        List<Item> itemTableData;
        ListView itemList;
        ItemArrayAdaptor itemArrayAdaptor;
        SQLiteDatabase sqLiteDatabase;

        Boolean photoAdded = false;
    String moveName; //move name is to retrieve the move id and insert into item table when creating new item


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
        }

@Override
public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_add){
            //add photo activity
            photoAdded = true;
            Intent intent = new Intent(ItemListActivity.this, AddNewItemActivity.class);
            Integer moveId = dataSource.getMoveId(moveName);
            intent.putExtra("moveId", moveId );
            startActivity(intent);
        }else if (id == R.id.action_uninstall){
            //Unistall the app
            Uri packageUri = Uri.parse("package:com.sample.farza.moveit");
            Intent uninstall = new Intent(Intent.ACTION_DELETE,packageUri);
            startActivity(uninstall);
        }
        return true;
        }

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list_view);

   // Intent intent = getIntent();
   // moveName = intent.getStringExtra("moveName");

        dataSource = new DataSource(this);
        dataSource.open();

        Integer moveId = dataSource.getLatestMoveId();
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
        Intent intent = new Intent(ItemListActivity.this, ItemDescription.class);
        intent.putExtra("item", item);
        startActivity(intent);
        }


//query the table and retrieve all the values
//the result is iterated with the help of cursor
//Pictures are added to the photos array list


@Override
protected void onResume(){
        dataSource.open();
        super.onResume();
        if(photoAdded){
        photoAdded = false;
            Integer moveId = dataSource.getLatestMoveId();
            itemTableData = dataSource.getItemsForAMove(moveId);
        itemArrayAdaptor.clear();
        itemArrayAdaptor = new ItemArrayAdaptor(this, R.layout.custom_row, itemTableData);
        itemList.setAdapter(itemArrayAdaptor);
        }
        }

@Override
protected void onPause() {
        dataSource.close();
        super.onPause();
        }

}
