package com.tgifreitag.billfold;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewBillActivity  extends Activity{

    DBAdapter db = new DBAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bill);

        Spinner spinner = (Spinner) findViewById(R.id.frequency);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.frequency_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void submitNewBill(View v)
    {
        Log.d("AddingNewBill", "adding");
        //get data from form
        EditText billNameTxt = (EditText)findViewById(R.id.editBillName);
        EditText dueDateTxt = (EditText)findViewById(R.id.editDueDate);
        EditText payeeIDTxt = (EditText)findViewById(R.id.editPayeeID);
        EditText amountTxt = (EditText)findViewById(R.id.editAmount);
        EditText startTxt = (EditText)findViewById(R.id.editStart);
        EditText endTxt = (EditText)findViewById(R.id.editEnd);
        EditText payor1IDTxt = (EditText)findViewById(R.id.editPayor1ID);
        EditText payor1pctTxt = (EditText)findViewById(R.id.editPayor1pct);
        EditText notesTxt = (EditText)findViewById(R.id.editNotes);

        db.open();
        long id = db.insertRecord(billNameTxt.getText().toString(),
                dueDateTxt.getText().toString(),
                payeeIDTxt.getText().toString(),
                amountTxt.getText().toString(),
                startTxt.getText().toString(),
                endTxt.getText().toString(),
                payor1IDTxt.getText().toString(),
                payor1pctTxt.getText().toString(),
                notesTxt.getText().toString());
        db.close();

        billNameTxt.setText("");
        dueDateTxt.setText("");
        payeeIDTxt.setText("");
        amountTxt.setText("");
        startTxt.setText("");
        endTxt.setText("");
        payor1IDTxt.setText("");
        payor1pctTxt.setText("");
        notesTxt.setText("");
        Toast.makeText(NewBillActivity.this,"Bill Added", Toast.LENGTH_LONG).show();

        viewBills(v);
    }

    public void viewBills(View v) {
        Intent i = new Intent(this, BillsActivity.class);
        startActivity(i);
    }
}
