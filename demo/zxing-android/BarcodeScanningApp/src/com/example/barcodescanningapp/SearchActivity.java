package com.example.barcodescanningapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class SearchActivity extends Activity implements OnClickListener{
	
	private Button searchManualBtn,searchScanBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		searchManualBtn = (Button)findViewById(R.id.search_manual);
		searchScanBtn = (Button)findViewById(R.id.search_scan);
		Intent intent = getIntent();
		
		
		setContentView(R.layout.activity_search);
		//listen for clicks
		searchManualBtn.setOnClickListener(this);
		searchScanBtn.setOnClickListener( this);
		
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

}
