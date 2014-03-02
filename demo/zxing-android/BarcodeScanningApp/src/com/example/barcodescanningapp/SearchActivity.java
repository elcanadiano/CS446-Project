package com.example.barcodescanningapp;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import Communication.CommunicationClass;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class SearchActivity extends Activity implements OnClickListener{
	
	/*
	 * This activity is for options for searching manually or searching by scanning
	 */
	private Button searchManualBtn,searchScanBtn;
	private TextView searchText;
	private String TAG = "SearchActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	//	Intent intent = getIntent();
	
		Log.d(TAG,"YOOOOO: ");
		setContentView(R.layout.activity_search);
		//listen for clicks
		//searchManualBtn.setOnClickListener(this);
		//searchScanBtn.setOnClickListener( this);
		searchManualBtn = (Button)findViewById(R.id.search_manual);
		searchScanBtn = (Button)findViewById(R.id.search_scan);
		searchText = (TextView)findViewById(R.id.search_text);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	
	public void onClick(View view){
		if(view.getId() == R.id.search_manual){
			
		}
		else if(view.getId() == R.id.search_scan){
			
		}
	}
	/*
	 * Goes to the Search Manual Activity Page
	 * Uses android:onClick
	 */
	public void searchManual(View view){
		Intent intent = new Intent(this,ManualSearchActivity.class);
		startActivity(intent);
	}//searchManual
	public void searchScan(View view){
		
		
		
		
		/*
		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
		scanIntegrator.initiateScan();*/
	}//searchScan
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//retrieve result of scanning - instantiate ZXing object
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if(scanningResult != null){
			String scanContent = scanningResult.getContents();
			//get format name of data scanned
			String scanFormat = scanningResult.getFormatName();
			//output to UI
			//formatTxt.setText("FORMAT: "+scanFormat);
			//contentTxt.setText("CONTENT: "+scanContent);
			//go to results page
			Intent resultsIntent = new Intent(this,ManualSearchActivity.class);
			startActivity(intent);
		}
		else{
			//invalid scan, scan cancelled
			Toast toast = Toast.makeText(getApplicationContext(), 
					"No scan data received!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}//onActivityResult

}
