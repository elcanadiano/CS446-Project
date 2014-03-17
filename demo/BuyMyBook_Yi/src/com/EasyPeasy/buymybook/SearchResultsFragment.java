package com.EasyPeasy.buymybook;
import com.EasyPeasy.buymybook.CustomSearchListAdaptor;



import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/*
 * xml file: fragment_results_detail
 */
public class SearchResultsFragment extends Fragment{
	final String tag = "SearchResultsFragment";
	String result = null;

	/*
	 *Called before onCreateView() of the fragment
	 */
	public SearchResultsFragment(){
		super();
		;
	}
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.d(tag,"onCreate");
		//setContentView(R.layout.activity_search_manual);
		result = getArguments() != null ? getArguments().getString("json") : null;
		Log.d(tag,"results: "+ result);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		Log.d(tag,"onCreateView");
		View view = inflater.inflate(R.layout.fragment_results_search,container,false);
		
		JSONArray endResult = new JSONArray();
		/*
		 * Problem: listings vs single item
		 * Will Alex return a listing? or just one data item
		 * 
		 */
		JSONObject data = new JSONObject();
		if(result != null){
			try{
				JSONObject jsonString = new JSONObject(result);
				data = jsonString.getJSONObject("data");
				Log.d(tag,"got data");
				endResult = data.getJSONArray("book");
				//endResult.put(data);
				Log.d(tag,"got book");
				//endResult = book.getJSONArray("listings");
				Log.d(tag,"got end result");
				Log.d(tag,"endResult size: "+endResult.length());
				
			}
			catch(JSONException e){
				Log.d(tag,"Exception: "+e.toString());
			}
		}//if
		//endResult.put(book);
		Log.d(tag,"endResult size: "+endResult.length());
		Log.d(tag,"endResult: "+endResult.toString());
	
		ArrayList image_details;
		image_details = parseJSONResult(endResult);
		if(image_details == null){
			Log.d(tag,"image_details null");
		}
		else{
			Log.d(tag,"imagedetails not null");
		}
		
		   final ListView lv1 = (ListView) view.findViewById(R.id.search_manual_listview);
		   if(lv1 != null){
			   lv1.setAdapter(new CustomSearchListAdaptor(getActivity(), image_details));
			   lv1.setOnItemClickListener(new OnItemClickListener() {
		        	/*
		        	 * Click listener for each item view
		        	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
		        	 */
		            @Override
		            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		                Object o = lv1.getItemAtPosition(position);
		                SearchListItem newsData = (SearchListItem) o;
		                Toast.makeText(getActivity(), "Selected :" + " " + newsData, Toast.LENGTH_LONG).show();            
		                detailsFragment(newsData);
		            }
		 
		        });
		   }
		   else{
			   Log.d(tag,"searchresultfragment, adapter null");
		   }	       
		   //lv1.setAdapter(new CustomSearchListAdaptor(inflater, image_details));
		return view;
	}

	public void detailsFragment(SearchListItem newsData){
		FragmentActivity f;
		Fragment fragment = null;
		fragment =  new DetailsFragment();
		f = getActivity();
			if (fragment != null) {
				Bundle args = new Bundle();
				
				args.putString("json", result);
				fragment.setArguments(args);
				f.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null).commit();
			}
	}
		
	private ArrayList parseJSONResult(JSONArray jsonArray){
			
			ArrayList results = new ArrayList();
			
			try{
				for(int i = 0 ; i < jsonArray.length();i++){
					JSONObject childObject = jsonArray.getJSONObject(i);
					String title = childObject.getString("title");
				String author = childObject.getString("authors");
				String price = "1";
				String condition = "1";
				
				//String price = childObject.getString("listing_price");
				//String condition = childObject.getString("condition");
			
				SearchListItem item = new SearchListItem(title,author,price,condition);
				
				results.add(item);
			}//for
		}//try
		catch(JSONException e){
			Log.e(tag,"Exception in parseJSONResult: "+e.toString());
		}
		return results;
	}

}
