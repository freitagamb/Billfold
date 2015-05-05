package com.tgifreitag.billfold;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class HomeActivity extends Activity {

    DBAdapter db = new DBAdapter(this);
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // get the bills list
        expListView = (ExpandableListView) findViewById(R.id.bills_ExpandableList);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        db.open();
        long newRecord = db.insertRecord("Utilities", "5/4/15", "03", "67.34", "3/4/13","3/4/17","01",".1","My notes");
        Cursor c = db.getRecord(1);
        Toast.makeText(this,"Bill Name: " + c.getString(1) + " Added!" , Toast.LENGTH_SHORT).show();

        newRecord = db.insertRecord("Rent", "6/1/15", "14", "179.00", "7/1/12", "7/1/17", "01", ".1", "My super cool notes");
        c = db.getRecord(2);
        Toast.makeText(this,"Bill Name: " + c.getString(1) + " Added!" , Toast.LENGTH_SHORT).show();
        db.close();
    }
/*
    private void openDB() {
        myDB = new DBAdapter(this);
        myDB.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void closeDB() {
        myDB.close();
    }*/

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Due Today");
        listDataHeader.add("Due Tomorrow");
        listDataHeader.add("Due This Week");
        listDataHeader.add("Due Next Week");
        listDataHeader.add("Due This Month");
        listDataHeader.add("Other");

        // Adding child data

        List<String> today = new ArrayList<String>();
        List<String> tomorrow = new ArrayList<String>();
        List<String> thisWeek = new ArrayList<String>();
        List<String> nextWeek = new ArrayList<String>();
        List<String> thisMonth = new ArrayList<String>();
        List<String> other = new ArrayList<String>();

        //Get Today's Date
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy");
        String todayDate = sdf.format(new Date());
        Log.d("DBAdapterBills",todayDate);
        db.open();

        // Set Today's Bills
        Cursor c = db.findRecordByDate(" LIKE ", todayDate);
        Log.d("DBAdapterBills", "today = " + c.getString(0));
        if (c.moveToFirst())
        {
            do {
                today.add(c.getString(0));
            } while (c.moveToNext());
        }

        // Set Tomorrow's Bills
        Date tomorrowDate = todayDate.parse(sourceDate);
        tomorrowDate = DateUtil.addDays(myDate, 1);
        c = db.findRecordByDate(" LIKE ",todayDate);
        Log.d("DBAdapterBills","today = " +c.getString(0));
        if (c.moveToFirst())
        {
            do {
                today.add(c.getString(0));
            } while (c.moveToNext());
        }




        thisWeek.add("Utilities");
        thisWeek.add("Rent");
        thisWeek.add("Internet");


        nextWeek.add("Crossfit");
        nextWeek.add("Car Insurance");
        nextWeek.add("Truck Insurance");

        thisMonth.add("Dental Insurance");
        thisMonth.add("Medical Insurance");

        other.add("Spotify");
        other.add("Netflix");

        listDataChild.put(listDataHeader.get(0), today); // Header, Child data
        listDataChild.put(listDataHeader.get(1), tomorrow);
        listDataChild.put(listDataHeader.get(2), thisWeek);
        listDataChild.put(listDataHeader.get(3), nextWeek);
        listDataChild.put(listDataHeader.get(4), thisMonth);
        listDataChild.put(listDataHeader.get(5), other);

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void billsClickHandler(MenuItem item) {
        Intent i = new Intent(this, BillsActivity.class);
        startActivity(i);
    }

    public void homeClickHandler(MenuItem item) {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    public void peopleClickHandler(MenuItem item) {
        Intent i = new Intent(this, PeopleActivity.class);
        startActivity(i);
    }
}
