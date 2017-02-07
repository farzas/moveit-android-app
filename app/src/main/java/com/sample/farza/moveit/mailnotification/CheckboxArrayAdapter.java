package com.sample.farza.moveit.mailnotification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sample.farza.moveit.R;

import java.util.List;

/**
 * Created by farzaali on 5/20/16.
 */
public class CheckboxArrayAdapter extends ArrayAdapter<Email> {

    private LayoutInflater inflater;

    public CheckboxArrayAdapter(Context context, List<Email> emailList ) {
        super( context, R.layout.checkbox_row, R.id.rowTextView, emailList );
        // Cache the LayoutInflate to avoid asking for a new one each time.
        inflater = LayoutInflater.from(context) ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Planet to display
        Email email = this.getItem( position );

        // The child views in each row.
        CheckBox checkBox ;
        TextView textView ;

        // Create a new row view
        if ( convertView == null ) {
            convertView = inflater.inflate(R.layout.checkbox_row, null);

            // Find the child views.
            textView = (TextView) convertView.findViewById( R.id.rowTextView );
            checkBox = (CheckBox) convertView.findViewById( R.id.CheckBox01 );

            // Optimization: Tag the row with it's child views, so we don't have to
            // call findViewById() later when we reuse the row.
            convertView.setTag( new CheckBoxViewHolder(textView,checkBox) );

            // If CheckBox is toggled, update the planet it is tagged with.
            checkBox.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    Email email = (Email) cb.getTag();
                    email.setChecked( cb.isChecked() );
                }
            });
        }
        // Reuse existing row view
        else {
            // Because we use a ViewHolder, we avoid having to call findViewById().
            CheckBoxViewHolder viewHolder = (CheckBoxViewHolder) convertView.getTag();
            checkBox = viewHolder.getCheckBox() ;
            textView = viewHolder.getTextView() ;
        }

        // Tag the CheckBox with the Planet it is displaying, so that we can
        // access the planet in onClick() when the CheckBox is toggled.
        checkBox.setTag( email );

        // Display planet data
        checkBox.setChecked( email.isChecked() );
        textView.setText( email.getEmail() );

        return convertView;
    }



    /*public Object onRetainCustomNonConfigurationInstance () {
        return emails ;
    }*/
}