package com.sample.farza.moveit.organize;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.farza.moveit.R;
import com.sample.farza.moveit.data.ItemBox;
import com.sample.farza.moveit.database.DataSource;

import java.io.File;
import java.util.List;

/**
 * Created by Amy on 5/24/16.
 */
public class OrganizeBoxDetailsViewArrayAdapter extends ArrayAdapter<ItemBox>{

    private final List<ItemBox> aItemsList;
    //ArrayList<String> mSpinnerArray = new ArrayList<>();
    Context aContext;
    DataSource dataSource;

    OrganizeBoxDetailsViewArrayAdapter(Context c, List<ItemBox> aBoxes) {
        super(c, R.layout.organize_items_itemlist_in_box_custom_row, R.id.tvItemName_BL, aBoxes);
        this.aContext = c;
        this.aItemsList = aBoxes;
        dataSource = new DataSource(aContext);

    }

    @Override
    //called by the view control. Supplies custom view for each row
    public View getView(final int position, View convertView, ViewGroup parent) {

        //Inflator renders the view from xml file. very expensive operation
        //Instantiates a layout XML file into its corresponding View objects.

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.organize_items_itemlist_in_box_custom_row, null);

        //retrieves the corresponding name of the animal and displays it in the position.
        TextView textView = (TextView) row.findViewById(R.id.tvItemName_BL);
        final Button mButtomRemoveFromBox = (Button) row.findViewById(R.id.btnItemRemoveFromBox_BL);
        textView.setText(aItemsList.get(position).getName());
        row.setLongClickable(true);
        row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mButtomRemoveFromBox.setVisibility(View.VISIBLE);
                Log.i("AMY", " Long click at " + position);

                return true;
            }
        });
       // mButtomRemoveFromBox.setVisibility(View.INVISIBLE);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtomRemoveFromBox.setVisibility(View.INVISIBLE);
            }
        });

        mButtomRemoveFromBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("AMY", " Button click at " + position);

                dataSource.open();
                dataSource.updateItemBox(aItemsList.get(position).getId(),0);
                dataSource.close();

                //mButtomRemoveFromBox.setVisibility(View.INVISIBLE);

                OrganizeBoxDetailsViewArrayAdapter.this.remove(aItemsList.get(position));
                OrganizeBoxDetailsViewArrayAdapter.this.notifyDataSetChanged();
            }
        });



        try {

            //sets the animal icon for the list
            ImageView imageView = (ImageView) row.findViewById(R.id.ivItemPhoto_BL);
            String filename = aItemsList.get(position).getImagePath();
            File imgFile = new File(filename);
            if(imgFile.exists())
            {

                //loads bitmaps from the file system
                //to avoid out of memory errors
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 6; //experiment with different sizes
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(),options);

                //Set the enlarged image view
                imageView.setImageBitmap(myBitmap);
            }

            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }



}
