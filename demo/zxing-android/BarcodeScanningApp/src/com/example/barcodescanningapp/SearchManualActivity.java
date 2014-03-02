package com.example.barcodescanningapp;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


/*
 * This activity is the result
 * xmlfile:activity_search_manual.xml
 */
public class SearchManualActivity extends ListActivity {
	private String tag = "SearchManualActivity";
	static final String[] FRUITS = new String[] { "Apple", "Avocado", "Banana",
		"Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
		"Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_search_manual);
		//
		Bundle extras =getIntent().getExtras();
		if(extras != null){
			Log.d(tag,"ResultsPageSearch: "+extras.getString("SUBMITVAL_TITLE"));
			Log.d(tag,"ResultsPageSearch: "+extras.getString("SUBMITVAL_AUTHOR"));
			Log.d(tag,"ResultsPageSearch: "+extras.getString("SUBMITVAL_ISBN"));
		}//if
		setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_search_manual,FRUITS));
		 
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
 /*
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    // When clicked, show a toast with the TextView text
			    Toast.makeText(getApplicationContext(),
				((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			}
		});
*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_manual, menu);
		return true;
	}
	

}
