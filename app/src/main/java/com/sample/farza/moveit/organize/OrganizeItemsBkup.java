package com.sample.farza.moveit.organize;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sample.farza.moveit.R;
import com.sample.farza.moveit.data.Box;
import com.sample.farza.moveit.data.ItemBox;
import com.sample.farza.moveit.database.DataSource;
import com.sample.farza.moveit.listitems.ItemArrayAdaptor;

import java.util.ArrayList;
import java.util.List;

public class OrganizeItemsBkup extends AppCompatActivity {

    ArrayList<String> spinnerArray;
    int countOfboxes=1;
    List<ItemBox> mItemArrayList;
    List<Box> mBoxesForMoveid;
    Button mAddToBoxButton;
    TextView mItemList, mBoxName;
    DataSource dataSource;
    ItemArrayAdaptor itemArrayAdaptor;
    Integer mMoveId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organize_items_bkup);

        Intent i = getIntent();
        String mNumberOfBoxesStr = i.getStringExtra("numberOfBoxes");
        String mMoveName= i.getStringExtra("movename");
        boolean mMoveExists = i.getBooleanExtra("moveExists",false );
        Log.i("AMY", mMoveName);
        int mNumberOfBoxes = Integer.parseInt(mNumberOfBoxesStr);

        spinnerArray = new ArrayList<>();
        dataSource = new DataSource(this);
        dataSource.open();
        mMoveId = dataSource.getMoveId(mMoveName);
        Log.i("AMY", mMoveId.toString());
        //dataSource.deleteexistingBoxes(mMoveId);

        if (!mMoveExists) {
            while (countOfboxes <= mNumberOfBoxes) {

                String mBoxname = "Box " + countOfboxes;
                spinnerArray.add(mBoxname);
                dataSource.createBox(mMoveId, mBoxname);
                countOfboxes++;

            }

        }

//        dataSource = new DataSource(this);
//        dataSource.open();
        mItemArrayList = new ArrayList<>();
        mBoxesForMoveid = new ArrayList<>();

        mItemArrayList = dataSource.getAllItemsNotInBoxes(mMoveId);
        mBoxesForMoveid = dataSource.getBoxesForThisMove(mMoveId);
        dataSource.close();

       // mItemList = (TextView)findViewById(R.id.tvitemNameOI_bk);

        //photos contains all the data stored in the table
        //itemArrayAdaptor = new ItemArrayAdaptor(this, R.layout.organize_items_itemlist_custom_row, mItemArrayList);
        //mItemList.setAdapter(itemArrayAdaptor);
        //itemArrayAdaptor.notifyDataSetChanged();
//
//        mAnimalArrayList = new ArrayList<String>();
//        mAnimalArrayList.add("spoon");
//        mAnimalArrayList.add("fork");
//        mAnimalArrayList.add("plate");




        // Find the number of animals added
      //  numAnimals = mAnimalArrayList.size();


//        Spinner spinner = (Spinner) findViewById(R.id.spnrBoxListOI_bk);
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
//        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//        spinner.setAdapter(spinnerArrayAdapter);

        // Using an Adapter to render the custom view, single_row
        ListView mItemListView = (ListView) findViewById(R.id.lvItemListOIBkup);
        OrganizeItemsArrayAdapterBkup mAnimalListAdapterTest = new OrganizeItemsArrayAdapterBkup(OrganizeItemsBkup.this, mItemArrayList,mBoxesForMoveid);
        mItemListView.setAdapter(mAnimalListAdapterTest);
        mAnimalListAdapterTest.notifyDataSetChanged();

        ListView mBoxListView = (ListView) findViewById(R.id.lvBoxListOIBkup);
        OrganizeBoxesListArrayAdapter mBoxListAdapter = new OrganizeBoxesListArrayAdapter(OrganizeItemsBkup.this, mBoxesForMoveid);
        mBoxListView.setAdapter(mBoxListAdapter);


        //mItemListView.setOnItemClickListener(MainActivity.this);




//        spinnerArray.add("Box1");
//        spinnerArray.add("Box2");
//        spinnerArray.add("Box3");


    }

    @Override
    protected void onResume() {
        super.onResume();

        dataSource.open();


//        dataSource = new DataSource(this);
//        dataSource.open();
        mItemArrayList = new ArrayList<>();
        mBoxesForMoveid = new ArrayList<>();

        mItemArrayList = dataSource.getAllItemsNotInBoxes(mMoveId);
        mBoxesForMoveid = dataSource.getBoxesForThisMove(mMoveId);
        dataSource.close();

        ListView mItemListView = (ListView) findViewById(R.id.lvItemListOIBkup);
        OrganizeItemsArrayAdapterBkup mAnimalListAdapterTest = new OrganizeItemsArrayAdapterBkup(OrganizeItemsBkup.this, mItemArrayList,mBoxesForMoveid);
        mItemListView.setAdapter(mAnimalListAdapterTest);
        mAnimalListAdapterTest.notifyDataSetChanged();

        ListView mBoxListView = (ListView) findViewById(R.id.lvBoxListOIBkup);
        OrganizeBoxesListArrayAdapter mBoxListAdapter = new OrganizeBoxesListArrayAdapter(OrganizeItemsBkup.this, mBoxesForMoveid);
        mBoxListView.setAdapter(mBoxListAdapter);
    }
}
