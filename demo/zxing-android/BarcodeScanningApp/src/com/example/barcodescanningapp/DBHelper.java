package com.example.barcodescanningapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/*
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
*/

public class DBHelper extends SQLiteOpenHelper{
	private SQLiteDatabase db;
	private static final int DATABASE_VERSION =3;
	private static final String DB_NAME = "userdata.db";
	private static final String TABLE_NAME = "listing";
	
	public DBHelper(Context c) { // the application context
		super (c, DB_NAME, null, DATABASE_VERSION);
		
		db=getWritableDatabase();
		
		
		System.out.println("loading db");
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) { // the SQLite DB
		
		db.execSQL(
				"CREATE TABLE listing (_id integer primary key autoincrement, book_title text, book_isbn integer, book_author text, book_price text, book_condition text);"
				);
	}
	
	public void insert(String title, String isbn, String author, String price, String condition) {
		db.execSQL("INSERT INTO "+TABLE_NAME+"('book_title', 'book_isbn', 'book_author', 'book_price', 'book_condition') values ('"+
				title + "', '"+
				isbn + "', '"+
				author + "', '"+
				price + "', '"+
				isbn + "')");
	}
	
	public void clearAll() {
		db.delete(TABLE_NAME, null, null);
	}
	
	public Cursor cursorSelectAll() { // get all return results
		Cursor cursor = this.db.query(
				TABLE_NAME,
				new String[] {"book_title", "book_author", "book_price", "book_condition"},
				null, // WHERE
				null, //selection args
				null, // GROUP BY
				null, // HAVING
				"book_title"); //ORDER BY
		
		//delete this while section later!
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			System.out.println(cursor.getString(0));
			cursor.moveToNext();
		}
		
		
		cursor.moveToFirst();
		return cursor;
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
	
	

}
