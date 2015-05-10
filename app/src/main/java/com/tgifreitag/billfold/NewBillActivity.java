package com.tgifreitag.billfold;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class NewBillActivity extends Activity implements DatePickerFragment.TheListener{

    DBAdapter db = new DBAdapter(this);
    int btnID = 0;
    boolean dueSet = false;
    boolean startSet = false;
    boolean endSet = false;

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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
        Log.d("myID", "" + v.getId());
        btnID = v.getId();
    }

    public void submitNewBill(View v)
    {
        Log.d("DBAdapterBills", "adding");
        //get data from form
        EditText billNameTxt = (EditText)findViewById(R.id.editBillName);
        TextView dueDateTxt = (TextView)findViewById(R.id.tvDueDate);
        Button dueBtn = (Button)findViewById(R.id.dueDatePicker);
        EditText payeeIDTxt = (EditText)findViewById(R.id.editPayeeID);
        EditText amountTxt = (EditText)findViewById(R.id.editAmount);
        Button startBtn = (Button)findViewById(R.id.startDatePicker);
        Button endBtn = (Button)findViewById(R.id.endDatePicker);
        EditText payor1IDTxt = (EditText)findViewById(R.id.editPayor1ID);
        EditText payor1pctTxt = (EditText)findViewById(R.id.editPayor1pct);
        EditText notesTxt = (EditText)findViewById(R.id.editNotes);

        db.open();
        long id = db.insertRecord(billNameTxt.getText().toString(),
                dueBtn.getText().toString(),
                payeeIDTxt.getText().toString(),
                amountTxt.getText().toString(),
                startBtn.getText().toString(),
                endBtn.getText().toString(),
                payor1IDTxt.getText().toString(),
                payor1pctTxt.getText().toString(),
                notesTxt.getText().toString());
        db.close();
        /*
        billNameTxt.setText("");
        dueBtn.setText("");
        payeeIDTxt.setText("");
        amountTxt.setText("");
        startBtn.setText("");
        endBtn.setText("");
        payor1IDTxt.setText("");
        payor1pctTxt.setText("");
        notesTxt.setText("");*/
        viewBills(v);
        Toast.makeText(NewBillActivity.this,billNameTxt.getText().toString()+" Bill Added", Toast.LENGTH_LONG).show();
    }

    public void viewBills(View v) {
        Intent i = new Intent(this, BillsActivity.class);
        startActivity(i);
    }

    public void returnDate(String date) {
        Button dateBtn = null;
        switch(btnID){
            case(R.id.dueDatePicker): //2131361809
                dateBtn = (Button) findViewById(R.id.dueDatePicker);
                break;
            case(R.id.startDatePicker): // start date 2131361811
                dateBtn = (Button) findViewById(R.id.startDatePicker);
                break;
            case(R.id.endDatePicker) : // end date 2131361812
                dateBtn = (Button) findViewById(R.id.endDatePicker);
                break;
            default:
                break;
        }
        dateBtn.setText(date);

    }
}
