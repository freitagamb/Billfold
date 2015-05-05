package com.tgifreitag.billfold;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {


	// ---------------- Bills Table ---------------- //

	// Bills Columns //
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
	private static final String TAG = "DBAdapterBills";

	private static final String DATABASE_NAME = "BillfoldDB";
	private static final String DATABASE_TABLE = "bills";
	private static final int DATABASE_VERSION = 5;

	private static final String DATABASE_CREATE =
			"create table if not exists "+ DATABASE_TABLE +" (id integer primary key autoincrement, billName VARCHAR not null, duedate DATE, payeeID INT, amount VARCHAR, start DATE, end DATE, payor1ID INT, payor1pct DECIMAL, notes VARCHAR);";

	private final Context context;

	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx)
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db)
		{
			try {
				db.execSQL(DATABASE_CREATE);
				Log.d(TAG, "Database created");
			} catch (SQLException e) {
				Log.d(TAG, "Exception was found on db create");
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS bills");
			onCreate(db);
		}
	}
	//---opens the database---
	public DBAdapter open() throws SQLException
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}

	//---deleteAllRecords the database---
	public long deleteAllRecords()
	{
		Log.d(TAG, "All Records Deleted");
		return db.delete(DATABASE_TABLE, null, null);
	}

	//---closes the database---
	public void close()
	{
		DBHelper.close();
	}

	//---insert a record into the database---
	public long insertRecord(String billName, String duedate, String
			payeeID, String amount, String start, String end, String payor1ID, String payor1pct, String notes)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_BILLNAME, billName);
		initialValues.put(KEY_DUEDATE, duedate);
		initialValues.put(KEY_PAYEEID, payeeID);
		initialValues.put(KEY_AMOUNT, amount);
		initialValues.put(KEY_START, start);
		initialValues.put(KEY_END, end);
		initialValues.put(KEY_PAYOR1_ID, payor1ID);
		initialValues.put(KEY_PAYOR1_PCT, payor1pct);
		initialValues.put(KEY_NOTES, notes);
		Log.d(TAG, "insert Record completed successfully");
		return db.insert(DATABASE_TABLE, null, initialValues);

	}

	//---deletes a particular record---
	public boolean deleteBill(long rowId)
	{
		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	//---retrieves all the records---
	public Cursor getAllRecords()
	{
		return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_BILLNAME,
				KEY_DUEDATE, KEY_PAYEEID, KEY_AMOUNT, KEY_START, KEY_END, KEY_PAYOR1_ID, KEY_PAYOR1_PCT, KEY_NOTES}, null, null, null, null, null, null);
	}

	//---retrieves a particular record---
	public Cursor getRecord(long rowId) throws SQLException
	{
		Cursor mCursor =
				db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_BILLNAME,
								KEY_DUEDATE, KEY_PAYEEID, KEY_AMOUNT, KEY_START, KEY_END, KEY_PAYOR1_ID, KEY_PAYOR1_PCT, KEY_NOTES},
						KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	//---finds a particular record---
	public Cursor findRecordByDate(String op, String todayDate) throws SQLException
	{
		Cursor mCursor =
				db.query(true, DATABASE_TABLE, new String[] {KEY_BILLNAME},
						KEY_DUEDATE + op + "'" + todayDate + "'", null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		Log.d(TAG,"End of findRecordByDate, " + mCursor.getCount());
		return mCursor;
	}

	//---finds a particular record---
	public Cursor findRecordBetween(String date1, String date2) throws SQLException
	{
		Cursor mCursor =
				db.query(true, DATABASE_TABLE, new String[] {KEY_BILLNAME},
						KEY_DUEDATE + " BETWEEN '" + date1 + "' AND '" + date2 + "'", null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		Log.d(TAG,"End of findRecordBetween, " + mCursor.getCount());
		return mCursor;
	}

	//---finds a particular record---
	public Cursor findMonth(String dateString, String month) throws SQLException
	{
		Cursor mCursor =
				db.query(true, DATABASE_TABLE, new String[] {KEY_BILLNAME},
						"strftime('%m', "+KEY_DUEDATE+ ") = '" + month + "'", null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		Log.d(TAG,"End of findMonth, " + mCursor.getCount());
		return mCursor;
	}

	//---updates a record---
	public boolean updateRecord(long rowId, String billName, String duedate, String
			payeeID, String amount, String start, String end, String payor1ID, String payor1pct, String notes)
	{
		ContentValues args = new ContentValues();
		args.put(KEY_BILLNAME, billName);
		args.put(KEY_DUEDATE, duedate);
		args.put(KEY_PAYEEID, payeeID);
		args.put(KEY_AMOUNT, amount);
		args.put(KEY_START, start);
		args.put(KEY_END, end);
		args.put(KEY_PAYOR1_ID, payor1ID);
		args.put(KEY_PAYOR1_PCT, payor1pct);
		args.put(KEY_NOTES, notes);
		return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
}
