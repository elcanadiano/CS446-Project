package com.example.barcodescanningapp;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;


/*
 * This activity is the result
 * xmlfile:activity_search_manual.xml
 */
public class SearchManualActivity extends Activity {
	private String tag = "SearchManualActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_manual);
		//
		Bundle extras =getIntent().getExtras();
		if(extras != null){
			Log.d(tag,"ResultsPageSearch: "+extras.getString("SUBMITVAL_TITLE"));
			Log.d(tag,"ResultsPageSearch: "+extras.getString("SUBMITVAL_AUTHOR"));
			Log.d(tag,"ResultsPageSearch: "+extras.getString("SUBMITVAL_ISBN"));
		}//if
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_manual, menu);
		return true;
	}
	

}
