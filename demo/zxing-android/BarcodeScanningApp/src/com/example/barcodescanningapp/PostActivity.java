package com.example.barcodescanningapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class PostActivity extends Activity implements OnClickListener{
	
	private Button manualBtn, scanBtn;
	
	@SuppressLint("NewApi")
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent(); //should be from main activity only
		setContentView(R.layout.activity_post);
		
		scanBtn = (Button)findViewById(R.id.post_scan);
		manualBtn = (Button)findViewById(R.id.post_manual);
		
		scanBtn.setOnClickListener(this);
		manualBtn.setOnClickListener(this);
		
	}
	
	public void onClick(View v){
		
		switch (v.getId()) {
			case R.id.post_manual:
				setContentView(R.layout.post_manual);
				break;
			case R.id.post_scan:
				IntentIntegrator scanIntegrator = new IntentIntegrator(this);
				scanIntegrator.initiateScan();
				break;
		}
	}
	
	/*
	 * Methods for manual posting
	 */
	private void manualPost(View view) {
		/*
		 * write this in js. Must:
		 * 1. check for input validity (client side)
		 * 2. make json string (for query)
		 * 3. talk to server
		 * 4. store return value from server in a json string
		 * 5. parse string (client side)
		 */
	}
	
	
	/*
	 * Method for scan posting
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//retrieve result of scanning - instantiate ZXing object
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		//check we have a valid result
		if (scanningResult != null) {
			//get content from Intent Result
			String scanContent = scanningResult.getContents();
			//get format name of data scanned
			String scanFormat = scanningResult.getFormatName();
			
			//2. talk to isbndb:
			String url="http://isbndb.com/api/v2/json/2L1HKXO4/book/"+scanContent;
			CommunicationClass c = new CommunicationClass(url);
			
			
		}
		else{
			//invalid scan data or scan canceled
			Toast toast = Toast.makeText(getApplicationContext(), 
					"No scan data received!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
