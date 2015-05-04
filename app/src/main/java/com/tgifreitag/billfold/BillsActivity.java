package com.tgifreitag.billfold;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BillsActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
    }

    public void newBillOnClickHandler(View v) {
            Intent i = new Intent(this, NewBillActivity.class);
            startActivity(i);
    }
}
