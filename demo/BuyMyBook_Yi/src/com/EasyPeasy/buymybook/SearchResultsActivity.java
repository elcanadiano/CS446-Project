package com.EasyPeasy.buymybook;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import com.EasyPeasy.buymybook.SearchResultsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchResultsActivity extends FragmentActivity {
	final String tag = "SearchResultsActivity";
	SimpleAdapter adapter;
	
	List<Map<String, String>> listings = new ArrayList<Map<String,String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);
		Bundle extras = getIntent().getExtras();
		String result = null;
		Log.d(tag,"got bundle");
		if(getIntent().getStringExtra("json") != null){
			result = new String(extras.getString("json"));
		}
		else{
			Log.d(tag,"null pointer");
		}
		/*
		 * Launches the result fragment upon creation
		 */
		Fragment fragment = null;
		fragment =  new SearchResultsFragment();
			if (fragment != null) {
				Bundle args = new Bundle();
				args.putString("json", result);
				fragment.setArguments(args);
				getSupportFragmentManager().beginTransaction().add(R.id.frame_container, fragment).addToBackStack(null).commit();
				
			
			}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_results, menu);
		return true;
	}

	/*
	 * Added support for fragment
	 */
	@Override
	public void onBackPressed() {
	   //getSupportFragmentManager();
		Log.d(tag,"onbackpressed: "+this.getSupportFragmentManager().getBackStackEntryCount());
		//if our backstack count is 1 then we only have the activity
	   if( this.getSupportFragmentManager().getBackStackEntryCount() > 1 ){
		   Log.d(tag,"popbackstack");
           this.getSupportFragmentManager().popBackStack();
	   }//if
	   else{
		   Log.d(tag,"super backpressed");
		   //pop the activity
		   this.getSupportFragmentManager().popBackStack();
		   //call super method to go back an activity
		   super.onBackPressed();
	   }
	}

}
