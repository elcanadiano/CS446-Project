package com.example.barcodescanningapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

//delete these imports later~
import android.database.Cursor;

public class MainActivity extends Activity implements OnClickListener {

	//UI instance variables
	private Button scanBtn, postBtn, searchBtn;
	private TextView formatTxt, contentTxt;
	
	//Local userdata DB stuff
	private DBHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//instantiate UI items
		scanBtn = (Button)findViewById(R.id.scan_button);
		postBtn = (Button)findViewById(R.id.post_button);
		searchBtn = (Button)findViewById(R.id.search_button);
		formatTxt = (TextView)findViewById(R.id.scan_format);
		contentTxt = (TextView)findViewById(R.id.scan_content);

		//listen for clicks
		scanBtn.setOnClickListener(this);
		postBtn.setOnClickListener(this);
		searchBtn.setOnClickListener(this);
		
		
		//set up local db for user data
		dbHelper = new DBHelper(this);
		
		if(true) { // cleans record after each launch
			dbHelper.clearAll();
		}
	}

	public void onClick(View v){
		Intent intent = new Intent(this, PostActivity.class);
		

		switch (v.getId()) {
			case R.id.scan_button:
				IntentIntegrator scanIntegrator = new IntentIntegrator(this);
				scanIntegrator.initiateScan();
				break;
			case R.id.post_button:
				/*
				 * dummy code to test local data storage!!
				 */
				dbHelper.insert("cs446", "1234567890");
				
				Cursor c=dbHelper.cursorSelectAll();
				
				

				formatTxt.setText("FORMAT: "+"post");
				contentTxt.setText("CONTENT: "+"button");
				startActivity(intent);
				break;
			case R.id.search_button:
				formatTxt.setText("FORMAT: "+"search");
				contentTxt.setText("CONTENT: "+"button");
				Intent intent2 = new Intent(this, SearchActivity.class);
				startActivity(intent2);
				break;
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//retrieve result of scanning - instantiate ZXing object
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		//check we have a valid result
		if (scanningResult != null) {
			//get content from Intent Result
			String scanContent = scanningResult.getContents();
			//get format name of data scanned
			String scanFormat = scanningResult.getFormatName();
			//output to UI
			
			
			formatTxt.setText("FORMAT: "+scanFormat);
			contentTxt.setText("CONTENT: "+scanContent);
			
		}
		else{
			//invalid scan data or scan canceled
			Toast toast = Toast.makeText(getApplicationContext(), 
					"No scan data received!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

}