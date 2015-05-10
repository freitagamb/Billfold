package com.tgifreitag.billfold;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends Activity {

    DBAdapter db = new DBAdapter(this);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void populateDB(View v)
    {
        db.open();
        db.insertRecord("Rent", "2015-05-06", "03", "67.34", "3/4/13", "3/4/17", "01", ".1", "My notes");
        db.insertRecord("Utilities", "2015-05-06", "03", "67.34", "3/4/13","3/4/17","01",".1","My notes");
        db.insertRecord("Medical Insurance", "2015-05-05", "14", "179.00", "7/1/12", "7/1/17", "01", ".1", "My super cool notes");
        db.insertRecord("Dental Insurance", "2015-05-05", "03", "67.34", "3/4/13","3/4/17","01",".1","My notes");
        db.insertRecord("Phone", "2015-06-01", "14", "179.00", "7/1/12", "7/1/17", "01", ".1", "My super cool notes");
        db.insertRecord("Gym", "2015-05-04", "03", "67.34", "3/4/13","3/4/17","01",".1","My notes");
        db.insertRecord("Utilities", "2015-05-07", "03", "67.34", "3/4/13","3/4/17","01",".1","My notes");
        db.insertRecord("Spotify", "2015-05-12", "14", "179.00", "7/1/12", "7/1/17", "01", ".1", "My super cool notes");
        db.insertRecord("Netflix", "2015-05-27", "03", "67.34", "3/4/13","3/4/17","01",".1","My notes");
        db.insertRecord("Car Loan", "2015-08-03", "14", "179.00", "7/1/12", "7/1/17", "01", ".1", "My super cool notes");
        db.insertRecord("Domain", "2015-10-04", "03", "67.34", "3/4/13", "3/4/17", "01", ".1", "My notes");
        recreate();
    }

    public void clearAllBills(View v) {
        db.deleteAllRecords();
        db.open();
        recreate();
    }
}
