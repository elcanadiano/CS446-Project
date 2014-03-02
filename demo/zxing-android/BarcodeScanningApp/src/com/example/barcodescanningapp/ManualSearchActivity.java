package com.example.barcodescanningapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
//this file is for inputting stuff
//xml file: activity_manual_Search.xml
public class ManualSearchActivity extends Activity {
	private EditText textTitle,textAuthor,textISBN;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual_search);
		textTitle = (EditText)findViewById(R.id.search_manual_booktitle);
		textAuthor = (EditText)findViewById(R.id.search_manual_bookauthor);
		textISBN = (EditText)findViewById(R.id.search_manual_bookisbn);
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
		startActivity(intent);
	}

}
