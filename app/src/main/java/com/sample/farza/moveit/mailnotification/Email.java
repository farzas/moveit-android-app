package com.sample.farza.moveit.mailnotification;

/**
 * Created by farzaali on 5/20/16.
 */
public class Email  {

    private String email = "" ;
    private boolean checked = false ;

    public Email() {}

    public Email( String email) {
        this.email = email;
    }
    public Email(String email, boolean checked ) {
        this.email = email;
        this.checked = checked ;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public String toString() {
        return email;
    }
    public void toggleChecked() {
        checked = !checked ;
    }
}
