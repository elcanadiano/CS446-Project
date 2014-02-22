package com.example.barcodescanningapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

//xml file: activity_manual_Search.xml
public class ManualSearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual_search);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manual_search, menu);
		return true;
	}
	
	public void submitResults(View view){
		Intent intent = new Intent(this,SearchManualActivity.class);
		startActivity(intent);
	}

}
