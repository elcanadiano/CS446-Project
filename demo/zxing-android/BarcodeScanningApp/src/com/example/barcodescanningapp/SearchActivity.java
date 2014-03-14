package com.example.barcodescanningapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barcodescanningapp.CommunicationClass.DownloadJSON;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
/*
 * activity_search.xml
 */
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
	
		setContentView(R.layout.activity_search);
		
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
	/*
	 * Function called to scan an ISBN in
	 */
	public void searchScan(View view) {

		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
		scanIntegrator.initiateScan();
	
	}//searchScan
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//retrieve result of scanning - instantiate ZXing object
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if(scanningResult != null 
				&& scanningResult.getContents() != null
				&& scanningResult.getFormatName() != null
		){
			String scanContent = scanningResult.getContents();
			String scanFormat = scanningResult.getFormatName();
			
			// Make sure an ISBN was scanned
			if (! scanFormat.equals("EAN_13")) {
				Toast toast = Toast.makeText(
						getApplicationContext(), 
						"Barcode scanned is not ISBN",
						Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			
			//go to results page
			Intent resultsIntent = new Intent(this,SearchManualActivity.class);
			
			//scanContent = new String("9787887031990"); //fake it
			scanContent =  new String("9780176251949");
			String url="http://buymybookapp.com/api/search/search_book/" + scanContent;
			
			CommunicationClass c = new CommunicationClass(url);
			c.new DownloadJSON(this,"search").execute(url);		
			
			// startActivity(intent);
		} else {
			//invalid scan, scan cancelled
			Toast toast = Toast.makeText(
					getApplicationContext(), 
					"No scan data received!",
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}//onActivityResult

}
