package com.example.barcodescanningapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


/*
 * This activity is the result
 * xmlfile:activity_search_manual.xml
 */
public class SearchManualActivity extends Activity {
	private String tag = "SearchManualActivity";
	static final String[] FRUITS = new String[] { "Apple", "Avocado", "Banana",
		"Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
		"Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple" };
	SimpleAdapter adapter;
	List<Map<String, String>> listings = new ArrayList<Map<String,String>>();
	public void populateList(){
		listings.add(createListing("author","Tim Kenyon"));
		listings.add(createListing("author","Tim Kenyon2"));
		listings.add(createListing("author","Tim Kenyon3"));
		listings.add(createListing("author","Tim Kenyon4"));
		listings.add(createListing("author","Tim Kenyon5"));
		listings.add(createListing("author","Tim Kenyon"));
		listings.add(createListing("author","Tim Kenyon2"));
		listings.add(createListing("author","Tim Kenyon3"));
		listings.add(createListing("author","Tim Kenyon4"));
		listings.add(createListing("author","Tim Kenyon5"));
		listings.add(createListing("author","Tim Kenyon"));
		listings.add(createListing("author","Tim Kenyon2"));
		listings.add(createListing("author","Tim Kenyon3"));
		listings.add(createListing("author","Tim Kenyon4"));
		listings.add(createListing("author","Tim Kenyon5"));
		listings.add(createListing("author","Tim Kenyon"));
		listings.add(createListing("author","Tim Kenyon2"));
		listings.add(createListing("author","Tim Kenyon3"));
		listings.add(createListing("author","Tim Kenyon4"));
		listings.add(createListing("author","Tim Kenyon5"));
		listings.add(createListing("author","Tim Kenyon"));
		listings.add(createListing("author","Tim Kenyon2"));
		listings.add(createListing("author","Tim Kenyon3"));
		listings.add(createListing("author","Tim Kenyon4"));
		listings.add(createListing("author","Tim Kenyon5"));
		
	}
	private ArrayList populateBooks(){
		ArrayList results = new ArrayList();
		String title="Clear Thinking In a Blurry World";
		String author="Tim Kenyon";
		String price="100";
		String condition="Good";
		SearchListItem item = new SearchListItem(title,author,price,condition);
		results.add(item);
		title = "Introduction to Algorithmns";
		author = "Cormen";
		price = "50";
		condition = "Excellent";
		item =new SearchListItem(title,author,price,condition);
		results.add(item);
		return results;
	}
	private HashMap<String,String> createListing(String key, String value){
		HashMap<String,String> data = new HashMap<String,String>();
		data.put(key,value);
		return data;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_manual);
		populateBooks();

		 ArrayList image_details = populateBooks();
	        final ListView lv1 = (ListView) findViewById(R.id.search_manual_listview);
	        lv1.setAdapter(new CustomSearchListAdaptor(this, image_details));
	        lv1.setOnItemClickListener(new OnItemClickListener() {
	 
	            @Override
	            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	                Object o = lv1.getItemAtPosition(position);
	                SearchListItem newsData = (SearchListItem) o;
	                Toast.makeText(SearchManualActivity.this, "Selected :" + " " + newsData, Toast.LENGTH_LONG).show();
	            }
	 
	        });
		//
		/*
		Bundle extras =getIntent().getExtras();
		if(extras != null){
			Log.d(tag,"ResultsPageSearch: "+extras.getString("SUBMITVAL_TITLE"));
			Log.d(tag,"ResultsPageSearch: "+extras.getString("SUBMITVAL_AUTHOR"));
			Log.d(tag,"ResultsPageSearch: "+extras.getString("SUBMITVAL_ISBN"));
		}//if
		*/
		/*adapter = new SimpleAdapter(this,listings,android.R.layout.simple_list_item_1,new String[] {"author"}, new int[] {android.R.id.text1});
		lv.setAdapter(adapter);
*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_manual, menu);
		return true;
	}
	

}
