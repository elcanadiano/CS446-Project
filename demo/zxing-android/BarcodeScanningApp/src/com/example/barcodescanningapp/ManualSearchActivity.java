package com.example.barcodescanningapp;

import com.example.barcodescanningapp.CommunicationClass.DownloadJSON;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
//this file is for inputting stuff
//xml file: activity_manual_Search.xml
public class ManualSearchActivity extends Activity {
	private EditText textTitle,textAuthor,textISBN,textCourseNum;
	/*
	 * Spinner stuff
	 */
	Spinner courseCode,courseNum,term;
	ArrayAdapter<String> spinnerCourseAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual_search);
		textTitle = (EditText)findViewById(R.id.search_manual_booktitle);
		textAuthor = (EditText)findViewById(R.id.search_manual_bookauthor);
		textISBN = (EditText)findViewById(R.id.search_manual_bookisbn);
		
		courseCode = (Spinner)findViewById(R.id.spinnerCourse);
		textCourseNum = (EditText)findViewById(R.id.textCourseNum);
		term = (Spinner)findViewById(R.id.spinnerTerm);
		String[] items = new String[]{"CS488,CS348,CS350"};
		spinnerCourseAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items);
		courseCode.setAdapter(spinnerCourseAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manual_search, menu);
		return true;
	}
	
	public void submitResults(View view){
		String title = textTitle.getText().toString();
		String author = textAuthor.getText().toString();
		// Get rid of hyphens in ISBN
		String isbn = textISBN.getText().toString().replace("-","");
		
		
		Intent intent = new Intent(this,SearchManualActivity.class);
		intent.putExtra("SUBMITVAL_TITLE", title);
		intent.putExtra("SUBMITVAL_AUTHOR", author);
		intent.putExtra("SUBMITVAL_ISBN", isbn);
		Intent resultsIntent = new Intent(this,SearchManualActivity.class);
		String scanContent = new String("9787887031990"); //fake it
		String url="http://buymybookapp.com/api/search/search_book/"+scanContent;
		//communication class does all the work in getting results
		CommunicationClass c = new CommunicationClass(url);
		c.new DownloadJSON(this,"search").execute(url);	
		//startActivity(intent);
	}
	/*
	 * Called by the scan ISBN button to scan an ISBN into the textfield
	 * This function will hopefully provide functionality to populate book information when you scan it 
	 */
	public void scanISBN(View view){
		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
		scanIntegrator.initiateScan();
	}
	public void onActivityResult(int requestCode, int resultCode, Intent intent){
		//retrieve result of scanning - instantiate ZXing object
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		
		//check we have a valid result
		if(scanningResult != null 
				&& scanningResult.getContents() != null
				&& scanningResult.getFormatName() != null
		) {
			//get content from Intent Result
			String scanContent = scanningResult.getContents();
			//get format name of data scanned
			String scanFormat = scanningResult.getFormatName();
		
			textISBN.setText(scanContent);
		} else {
			//invalid scan data or scan canceled
			Toast toast = Toast.makeText(
					getApplicationContext(), 
					"No scan data received!",
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}

}
