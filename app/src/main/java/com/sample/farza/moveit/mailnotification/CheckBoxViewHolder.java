package com.sample.farza.moveit.mailnotification;

import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by farzaali on 5/20/16.
 */
public class CheckBoxViewHolder {
    private CheckBox checkBox ;
    private TextView textView ;
    public CheckBoxViewHolder() {}
    public CheckBoxViewHolder( TextView textView, CheckBox checkBox ) {
        this.checkBox = checkBox ;
        this.textView = textView ;
    }
    public CheckBox getCheckBox() {
        return checkBox;
    }
    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
    public TextView getTextView() {
        return textView;
    }
    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
