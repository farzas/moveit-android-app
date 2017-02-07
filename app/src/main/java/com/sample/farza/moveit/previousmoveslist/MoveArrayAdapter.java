package com.sample.farza.moveit.previousmoveslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sample.farza.moveit.R;
import com.sample.farza.moveit.data.Move;

import java.util.List;

/**
 * Created by farzaali on 5/21/16.
 */
public class MoveArrayAdapter extends ArrayAdapter<Move> {
    //array adapter is used to dynamically populate the list

    private final List<Move> move;

    public MoveArrayAdapter(Context context, int resource, List<Move> move) {
        super(context, resource, move); //this will help the base class to know the size of the data
        this.move = move;
    }

    @Override
    //called by the view control. Supplies custom view for each row
    public View getView(final int position, View convertView, ViewGroup parent) {

        //Inflator renders the view from xml file. very expensive operation
        //Instantiates a layout XML file into its corresponding View objects.

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.move_custom_row, null);

        //retrieves the corresponding name of the animal and displays it in the position.
        TextView textView = (TextView) row.findViewById(R.id.moveNameTextView);
        textView.setText("#"+move.get(position).getId()+") "+move.get(position).getName());




        return row;
    }
}


