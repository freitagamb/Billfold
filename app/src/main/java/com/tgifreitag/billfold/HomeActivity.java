package com.tgifreitag.billfold;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

       // populateDB(db);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Due Today");
        listDataHeader.add("Due Tomorrow");
        listDataHeader.add("Due This Week");
        listDataHeader.add("Due Next Week");
        listDataHeader.add("Due This Month");

        // Adding child data

        List<String> today = new ArrayList<String>();
        List<String> tomorrow = new ArrayList<String>();
        List<String> thisWeek = new ArrayList<String>();
        List<String> nextWeek = new ArrayList<String>();
        List<String> thisMonth = new ArrayList<String>();

        // Set Todays Date and Dates variable for calculations
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        Dates temp = new Dates();

        //Get Today's Date
        String dateString = sdf.format(todayDate);
        Log.d("DBAdapterBills","(Today's) Date String is " + dateString);

        db.open();

        // Set Today's Bills
     //  Cursor c = db.findRecordWHERE("KEY_DUEDATE + ' LIKE ' + '" + dateString + "'");
         Cursor c = db.findRecordByDate(" LIKE ", dateString);
        if (c.moveToFirst())
        {
            Log.d("DBAdapterBills", "today = " + c.getString(0));
            do {
                today.add(c.getString(0));
            } while (c.moveToNext());
        }

        // Set Tomorrow's Bills
        Date tomorrowDate = temp.addDay(todayDate, 1);
        dateString = sdf.format(tomorrowDate);
        Log.d("DBAdapterBills","tomorrow is = " +dateString);
        c = db.findRecordByDate(" LIKE ",dateString);
        if (c.moveToFirst()) {
            do {
                tomorrow.add(c.getString(0));
                } while (c.moveToNext());
        }
        c.close();

        // Set This Week's Bills
        Date weekStart = temp.weekStart(todayDate,0);
        Date weekEnd = temp.weekEnd(todayDate,0);
        String sWeekString = sdf.format(weekStart);
        String eWeekString = sdf.format(weekEnd);
        Log.d("DBAdapterBills","sWeekString is = " +sWeekString);
        Log.d("DBAdapterBills", "eWeekString is = " + eWeekString);
        c = db.findRecordBetween(sWeekString, eWeekString);
        if (c.moveToFirst()) {
            do {
                thisWeek.add(c.getString(0));
            } while (c.moveToNext());
        }
        c.close();

        // Set Next Week's Bills
        weekStart = temp.weekStart(todayDate, 7);
        weekEnd = temp.weekEnd(todayDate, 7);

        sWeekString = sdf.format(weekStart);
        eWeekString = sdf.format(weekEnd);
        Log.d("DBAdapterBills","sNextWeekString is = " +sWeekString);
        Log.d("DBAdapterBills", "eNextWeekString is = " + eWeekString);
        c = db.findRecordBetween(sWeekString, eWeekString);
        if (c.moveToFirst()) {
            do {
                nextWeek.add(c.getString(0));
            } while (c.moveToNext());
        }
        c.close();

        // Set This Month's Bills
        String month = temp.thisMonth(todayDate);
        c = db.findMonth(dateString, month);
        if (c.moveToFirst()) {
            do {
                thisMonth.add(c.getString(0));
            } while (c.moveToNext());
        }
        c.close();

        listDataChild.put(listDataHeader.get(0), today); // Header, Child data
        listDataChild.put(listDataHeader.get(1), tomorrow);
        listDataChild.put(listDataHeader.get(2), thisWeek);
        listDataChild.put(listDataHeader.get(3), nextWeek);
        listDataChild.put(listDataHeader.get(4), thisMonth);

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
        Intent openMainActivity= new Intent(this, BillsActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(openMainActivity);
    }

    public void homeClickHandler(MenuItem item) {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    public void peopleClickHandler(MenuItem item) {
        Intent i = new Intent(this, PeopleActivity.class);
        startActivity(i);
    }

    public void settingsClickHandler(MenuItem item) {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

}