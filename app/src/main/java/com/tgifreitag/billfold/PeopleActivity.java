package com.tgifreitag.billfold;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PeopleActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
    }

    public void addNewBill(View v) {
        Intent i = new Intent(this, NewPersonActivity.class);
        startActivity(i);
    }
}
