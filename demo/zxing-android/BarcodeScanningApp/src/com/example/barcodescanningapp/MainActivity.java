package com.example.barcodescanningapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
//delete these imports later~

public class MainActivity extends Activity implements OnClickListener {
	
	final Context context = this;

	//UI instance variables
	private Button scanBtn, postBtn, searchBtn, profileBtn;
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
		profileBtn=(Button)findViewById(R.id.profile_button);
		formatTxt = (TextView)findViewById(R.id.scan_format);
		contentTxt = (TextView)findViewById(R.id.scan_content);

		//listen for clicks
		scanBtn.setOnClickListener(this);
		postBtn.setOnClickListener(this);
		searchBtn.setOnClickListener(this);
		profileBtn.setOnClickListener(this);
		
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
			case R.id.profile_button:
				Cursor c = dbHelper.cursorSelectAll();
				
				final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
	                    context, android.R.layout.select_dialog_item);
				
				c.moveToFirst();
				while (!c.isAfterLast()) {
					arrayAdapter.add(c.getString(0) + "  $" + c.getString(2)+":"+c.getString(3));
					c.moveToNext();
				}
				
				
		        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setTitle("My Books");
		        
		        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int item) {
		                // Do something with the selection
		            }
		        });
		        AlertDialog alert = builder.create();
		        alert.show();
				
				
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
			
			// Output contents to UI if it exists
			if (scanContent != null) {
				formatTxt.setText("FORMAT: "+scanFormat);
			} else {
				formatTxt.setText("FORMAT: No scan data received!");
			}
			
			// Output format name to UI if it exists
			if (scanFormat != null) {
				contentTxt.setText("CONTENT: "+scanContent);
			} else {
				contentTxt.setText("CONTENT: No scan data received!");
			}
		} else {
			//invalid scan data or scan canceled <-- Doesn't get triggered on scan cancel (K)
			Toast toast = Toast.makeText(getApplicationContext(), 
					"No scan data received!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

}