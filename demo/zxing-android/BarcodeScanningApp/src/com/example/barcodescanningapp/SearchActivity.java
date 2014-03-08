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
	public void searchScan(View view) {
		/*
		DataJSON json = new DataJSON();
		json.isbn = "9780176251949";
		json.author = "Tim Kenyon";
		json.title = "Clear Thinking In A Blurry World";
		json.year = "2004";
		CommunicationClass c = new CommunicationClass();
		String result = c.buildJSONFromObject(json);
		searchText.setText(result);
		Log.d(TAG,"json resultfromobject: "+result);
		*/
		/*Log.d(TAG,"calling communication class");
		CommunicationClass c = new CommunicationClass("http://buymybookapp.com/api/test/test2");
		c.new DownloadJSON(this,"scan").execute("http://buymybookapp.com/api/test/test2");
		*/
		/*
		try{
		CommunicationClass d = new CommunicationClass();
		//String json = "{\"users\":[{\"id\":\"2\",\"username\":\"admin\"},{\"id\":\"1\",\"username\":\"awpoon\"}],\"pet_name\":\"Cat\",\"name\":\"Whiskers\"}";
		//String json = "{\"isbn\":\"9780176251949\",\"author\":\"Tim Kenyon\",\"title\":\"Clear Thinking in a blurry world\",\"year\":\"2004\"}";
		//String json2 = "{\"isbn2345\":\"9780176251949\",\"author\":\"Tim Kenyon\",\"title\":\"Clear Thinking in a blurry world\",\"year\":\"2004\"}";
		
		DataJSON test = (DataJSON) d.objectFromJson(json2);
		Log.d(TAG,"test1 title: "+test.title);
		Log.d(TAG,"test2 author: "+test.author);
		Log.d(TAG,"test3 year: "+test.year);
		Log.d(TAG,"test4 isbn: "+test.isbn);
		}
		catch(JSONException e){
			Log.d(TAG, "Exception searchScan: "+ e.toString());
		}
		*/
	
		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
		scanIntegrator.initiateScan();
	
		/*
		Intent resultsIntent = new Intent(this,SearchManualActivity.class);
		String scanContent = new String("9787887031990"); //fake it
		String url="http://buymybookapp.com/api/search/search_book/"+scanContent;
		CommunicationClass c = new CommunicationClass(url);
		c.new DownloadJSON(this,"search").execute(url);	
		*/
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
			Intent resultsIntent = new Intent(this,SearchManualActivity.class);
			scanContent = new String("9787887031990"); //fake it
			String url="http://buymybookapp.com/api/search/search_book/"+scanContent;
			CommunicationClass c = new CommunicationClass(url);
			c.new DownloadJSON(this,"search").execute(url);		
			
		//	startActivity(intent);
		}
		else{
			//invalid scan, scan cancelled
			Toast toast = Toast.makeText(getApplicationContext(), 
					"No scan data received!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}//onActivityResult

}
