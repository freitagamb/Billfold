package com.tgifreitag.billfold;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import static com.google.android.gms.wallet.Wallet.API;


public class BillsActivity extends Activity{

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

        /*GoogleApiClient client = new GoogleApiClient.Builder(this)
                .addApi(Wallet.API, new Wallet.WalletOptions.Builder()
                        .setTheme(WalletConstants.THEME_HOLO_LIGHT)
                        .build())
                .build();

        client.connect(); */
    }

    private void initializeAdapter(){
        BillsAdapter adapter = new BillsAdapter(db.getAllData());
        rv.setAdapter(adapter);
    }

    public void addNewBill(View v) {
        Intent openMainActivity= new Intent(this, NewBillActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(openMainActivity);
    }

    public void payBill(View v) {
        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.walletnfcrel");
        startActivity(LaunchIntent);
    }



};


