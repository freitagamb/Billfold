package com.tgifreitag.billfold;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class BillsActivity extends Activity {

    DBAdapter db = new DBAdapter(this);
    private RecyclerView rv;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        db.open();
        rv = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        initializeAdapter();
        db.close();
    }

    private void initializeAdapter(){
        BillsAdapter adapter = new BillsAdapter(db.getAllData());
        rv.setAdapter(adapter);
    }

    public void addNewBill(View v) {
            Intent i = new Intent(this, NewBillActivity.class);
            startActivity(i);
    }
};


