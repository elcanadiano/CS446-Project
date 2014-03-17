package com.EasyPeasy.buymybook;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
/*
 * xml file: fragment_results_search.xml
 */
public class DetailsFragment extends Fragment {
	//private OnItemSelectedListener listener;
	
	public DetailsFragment(){
		;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		
		View view = inflater.inflate(R.layout.fragment_results_details,container,false);
		TextView txt = (TextView) view.findViewById(R.id.search_manual_title);
		String result = getArguments() != null ? getArguments().getString("json") : null;
		if(result != null){
			txt.setText(result);
		}
		return view;
		
	}
	
	@Override
    public void onAttach(Activity activity) {
      super.onAttach(activity);
      
      
      /*
      if (activity instanceof OnItemSelectedListener) {
        listener = (OnItemSelectedListener) activity;
      } else {
        throw new ClassCastException(activity.toString()
            + " must implemenet MyListFragment.OnItemSelectedListener");
      }
      */
    }
	
	
}
