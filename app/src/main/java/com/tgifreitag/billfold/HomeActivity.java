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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeActivity extends Activity {

  //  DBAdapter myDB;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // get the bills list
        expListView = (ExpandableListView) findViewById(R.id.bills_list);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
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
        // Select all bills where due date == current Date
        // Add Name to todayArray
        today.add("Stars Card");

        List<String> tomorrow = new ArrayList<String>();

        List<String> thisWeek = new ArrayList<String>();
        thisWeek.add("Utilities");
        thisWeek.add("Rent");
        thisWeek.add("Internet");

        List<String> nextWeek = new ArrayList<String>();
        nextWeek.add("Crossfit");
        nextWeek.add("Car Insurance");
        nextWeek.add("Truck Insurance");

        List<String> thisMonth = new ArrayList<String>();
        thisMonth.add("Dental Insurance");
        thisMonth.add("Medical Insurance");

        List<String> other = new ArrayList<String>();
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
