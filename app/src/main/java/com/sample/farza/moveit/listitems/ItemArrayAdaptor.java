package com.sample.farza.moveit.listitems;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.farza.moveit.R;
import com.sample.farza.moveit.data.Item;

import java.io.File;
import java.util.List;

/**
 * Created by farzaali on 5/14/16.
 */
public class ItemArrayAdaptor extends ArrayAdapter<Item> {
    //array adapter is used to dynamically populate the list

    private final List<Item> item;

    public ItemArrayAdaptor(Context context, int resource, List<Item> item) {
        super(context, resource, item); //this will help the base class to know the size of the data
        this.item = item;
    }

    @Override
    //called by the view control. Supplies custom view for each row
    public View getView(final int position, View convertView, ViewGroup parent) {

        //Inflator renders the view from xml file. very expensive operation
        //Instantiates a layout XML file into its corresponding View objects.

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_row, null);

        //retrieves the corresponding name of the animal and displays it in the position.
        TextView textView = (TextView) row.findViewById(R.id.captionTextView);
        textView.setText(item.get(position).getName());



        try {

            //sets the icon for the list
            ImageView imageView = (ImageView) row.findViewById(R.id.photoThumb);
            String filename = item.get(position).getImagePath();
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

