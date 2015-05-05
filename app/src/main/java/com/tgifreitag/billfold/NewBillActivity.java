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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class NewBillActivity  extends FragmentActivity{

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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"datePicker");
        //newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }
    }

    public void submitNewBill(View v)
    {
        Log.d("DBAdapterBills", "adding");
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
        Toast.makeText(NewBillActivity.this,billNameTxt.getText().toString()+" Bill Added", Toast.LENGTH_LONG).show();
        billNameTxt.setText("");
        dueDateTxt.setText("");
        payeeIDTxt.setText("");
        amountTxt.setText("");
        startTxt.setText("");
        endTxt.setText("");
        payor1IDTxt.setText("");
        payor1pctTxt.setText("");
        notesTxt.setText("");
        viewBills(v);
    }

    public void viewBills(View v) {
        Intent i = new Intent(this, BillsActivity.class);
        startActivity(i);
    }
}
