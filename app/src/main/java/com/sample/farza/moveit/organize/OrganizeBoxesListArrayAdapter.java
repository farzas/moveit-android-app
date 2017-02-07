package com.sample.farza.moveit.organize;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.sample.farza.moveit.R;
import com.sample.farza.moveit.data.Box;

import java.util.List;

/**
 * Created by Amy on 5/24/16.
 */
public class OrganizeBoxesListArrayAdapter extends ArrayAdapter<Box>{

    private final List<Box> aBoxesList;
    //ArrayList<String> mSpinnerArray = new ArrayList<>();
    Context aContext;
    Button mBoxList;

    OrganizeBoxesListArrayAdapter(Context c, List<Box> aBoxes) {
        super(c, R.layout.organize_items_boxlist_custom_row_bkup, R.id.tvtextView_BoxList_OIBkup, aBoxes);
        this.aContext = c;
        this.aBoxesList = aBoxes;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater rowInflater = (LayoutInflater) aContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = rowInflater.inflate(R.layout.organize_items_boxlist_custom_row_bkup, parent, false);

       // Log.i("AMY", "You clicked " +  aBoxesList.get(position).getName());
        mBoxList = (Button) row.findViewById(R.id.btnBoxName_BoxList_OIBkup);

        mBoxList.setText(aBoxesList.get(position).getName());
        mBoxList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("AMY", "You clicked " + aBoxesList.get(position).getName());
                Integer mBoxId = aBoxesList.get(position).getId();
                Intent showItemsInBoxesIntent = new Intent(aContext, OrganizeBoxDetailsViewBkup.class);
                showItemsInBoxesIntent.putExtra("boxname", aBoxesList.get(position).getName());
                showItemsInBoxesIntent.putExtra("boxid",mBoxId);
                aContext.startActivity(showItemsInBoxesIntent);
//                String boxes = numberOfBoxes.getText().toString();
//
//                //Integer moveId = dataSource.getMoveId(moveName);
//                showItemsToBoxesIntent.putExtra("numberOfBoxes", boxes);
//                showItemsToBoxesIntent.putExtra("movename", moveNameString);

            }
        });


        return row;
    }
}
