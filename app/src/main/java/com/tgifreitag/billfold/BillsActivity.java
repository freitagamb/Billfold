package com.tgifreitag.billfold;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class BillsActivity extends Activity {

    DBAdapter db = new DBAdapter(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);

        db.open();
        Cursor c = db.getRecord(1);
        if (c.moveToFirst())
        {
            do {
                TextView billNameTxt = (TextView) findViewById(R.id.billNametxt);
                billNameTxt.setText(c.getString(2));
            } while (c.moveToNext());
        }
        db.close();
    }

    public void addNewBill(View v) {
            Intent i = new Intent(this, NewBillActivity.class);
            startActivity(i);
    }
/*
    public void DisplayRecord(Cursor c)
    {
        Log.d("testingDisplayRecords", "Beginning of DisplayRecord");
        TextView billNameTxt = (TextView) findViewById(R.id.billNametxt);
        billNameTxt.setText(c.getString(2));
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                        "Bill Name: " + c.getString(1) + "\n" +
                        "Due Date:  " + c.getString(2) +

        public static final String KEY_ROWID = "id";
        public static final String KEY_BILLNAME = "billName";
        public static final String KEY_DUEDATE = "duedate";
        public static final String KEY_PAYEEID = "payeeID";
        public static final String KEY_AMOUNT = "amount";
        public static final String KEY_START = "start";
        public static final String KEY_END = "end";
        public static final String KEY_PAYOR1_ID = "payor1ID";
        public static final String KEY_PAYOR1_PCT = "payor1pct";
        public static final String KEY_NOTES = "notes";
                Toast.LENGTH_SHORT).show();

    }*/
};


