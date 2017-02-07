package com.sample.farza.moveit.organize;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.farza.moveit.R;
import com.sample.farza.moveit.data.Box;
import com.sample.farza.moveit.data.ItemBox;
import com.sample.farza.moveit.database.DataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amy on 5/21/16.
 */
public class OrganizeItemsArrayAdapterBkup extends ArrayAdapter<ItemBox> {

    private List<ItemBox> aAnimalList;
    private final List<Box> aBoxList;
    ArrayList<String> mSpinnerArray = new ArrayList<>();
    Context aContext;
    DataSource dataSource;


//    OrganizeItemsArrayAdapterBkup(Context c, List<Item> aNames, ArrayList<String> spinnerList) {
      OrganizeItemsArrayAdapterBkup(Context c, List<ItemBox> aNames, List<Box> aBoxes) {

        super(c, R.layout.organize_items_custom_row_backup, R.id.tvitemNameOI_bk, aNames);
        this.aContext = c;
        this.aAnimalList = aNames;
        this.aBoxList = aBoxes;
          dataSource = new DataSource(aContext);
        //this.mSpinnerArray = spinnerList;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //ViewHolder spinnerView;
        LayoutInflater rowInflater = (LayoutInflater) aContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = rowInflater.inflate(R.layout.organize_items_custom_row_backup, parent, false);
        //ImageView mAnimalImage = (ImageView) row.findViewById(R.id.ivAnimal);

        //spinnerView=new ViewHolder();
        final Spinner mSpinnerList = (Spinner) row.findViewById(R.id.spnrBoxListOI_bk);

        //spinnerView.spinner=(Spinner)row.findViewById(R.id.spnrBoxListOI_bk);
        //spinnerView.spinner.setAdapter(adapter);
        //row.setTag(spinnerView);

        TextView mItemList = (TextView) row.findViewById(R.id.tvitemNameOI_bk);
        mItemList.setText(aAnimalList.get(position).getName());

        Button mAddToBoxButton = (Button) row.findViewById(R.id.btnAddToBoxOI_bk);
        mAddToBoxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Insert into the Box table and Box item table

                TextView mItemText = (TextView) row.findViewById(R.id.tvitemNameOI_bk);
                Spinner mSpinnerText = (Spinner) row.findViewById(R.id.spnrBoxListOI_bk);
                Log.i("AMY", mItemText.getText().toString());
                dataSource.open();
                Integer mItemId = dataSource.getItemId(aAnimalList.get(position).getName());
                Integer mSpinnerPosition = mSpinnerText.getSelectedItemPosition()-1;
               // Log.i("AMY", "Id of Box selected is "+ aBoxList.get(mSpinnerText.getSelectedItemPosition()-1).getId());
                Log.i("AMY", "position "+mSpinnerText.getSelectedItemPosition());

                if (mSpinnerPosition >= 0){
                    dataSource.updateItemBox(mItemId,aBoxList.get(mSpinnerPosition).getId() );

                    OrganizeItemsArrayAdapterBkup.this.remove(aAnimalList.get(position));
                }
                else{
                    Toast.makeText(aContext, "Select a valid box", Toast.LENGTH_SHORT).show();
                }


              //  aAnimalList = dataSource.getAllItemsNotInBoxes();
                dataSource.close();
                OrganizeItemsArrayAdapterBkup.this.notifyDataSetChanged();


//
//                dataSource.open();
//                dataSource.createBox(itemString, category, imagePath, moveId);
//                dataSource.close();


                // Disable the row
            }
        });

        //Get the thumbnail image and the name to be displayed in ListView
        //mAnimalImage.setImageResource(aAnimalList.get(position).getThumbnail());
        Integer numBoxes = aBoxList.size();
        mSpinnerArray = new ArrayList<>();
        int boxPos = 0;
        mSpinnerArray.clear();
        mSpinnerArray.add("Select a box for item");
        while(numBoxes != 0){

            String boxname = aBoxList.get(boxPos).getName().toString();
            //Log.i("AMY", "Box name for spinner " +boxname);
            mSpinnerArray.add(boxname);
            boxPos++;
            numBoxes--;
        }


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(aContext, android.R.layout.simple_spinner_item, mSpinnerArray);

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerList.setAdapter(spinnerArrayAdapter);
        //mSpinnerList.setOnItemSelectedListener(aContext);

        return row;
    }

    @Override
    public boolean isEnabled(int position) {
        if(position == 0){
            return false;
        }
        else{
            return true;
        }
       // return super.isEnabled(position);
    }

    public class ViewHolder
    {
        Spinner spinner;
    }
}



