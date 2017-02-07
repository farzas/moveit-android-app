package com.sample.farza.moveit.organize;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.sample.farza.moveit.R;
import com.sample.farza.moveit.data.ItemBox;
import com.sample.farza.moveit.database.DataSource;

import java.util.ArrayList;
import java.util.List;

public class OrganizeBoxDetailsViewBkup extends AppCompatActivity{

    TextView mDisplayBoxName;
    ListView mItemList;
    List<ItemBox>  mItemArrayList;
    DataSource dataSource;
    String mBoxName;
    Integer mBoxId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organize_box_details_view_bkup);

        Intent i = getIntent();
        mBoxName = i.getStringExtra("boxname");
        mBoxId= i.getIntExtra("boxid", 0);

        mDisplayBoxName = (TextView) findViewById(R.id.tvBoxName_BL_OIBkup);
        mDisplayBoxName.setText(mBoxName);
        dataSource = new DataSource(this);

        dataSource.open();
        mItemArrayList = new ArrayList<>();


        mItemArrayList = dataSource.getAllItemsInThisBox(mBoxId);
        dataSource.close();

        mItemList = (ListView) findViewById(R.id.lvItemList_BL_OIBkp);
       // mItemList.setLongClickable(true);
       // mItemList.OnItemLongClickListener(new AdapterView.OnItemLongClickListener())

        OrganizeBoxDetailsViewArrayAdapter mShowItemsinBox = new OrganizeBoxDetailsViewArrayAdapter(OrganizeBoxDetailsViewBkup.this, mItemArrayList);
        mItemList.setAdapter(mShowItemsinBox);



    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
        mItemArrayList = new ArrayList<>();

        Log.i("AMY", "Box id : " +mBoxId);


        mItemArrayList = dataSource.getAllItemsInThisBox(mBoxId);
        dataSource.close();

        mItemList = (ListView) findViewById(R.id.lvItemList_BL_OIBkp);

        OrganizeBoxDetailsViewArrayAdapter mShowItemsinBox = new OrganizeBoxDetailsViewArrayAdapter(OrganizeBoxDetailsViewBkup.this, mItemArrayList);
        mItemList.setAdapter(mShowItemsinBox);

        Log.i("AMY", "On resume of Box details view");
    }

//    @Override
//    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//        Log.i("AMY", " Long click at " + position);
//        return true;
//    }
}
