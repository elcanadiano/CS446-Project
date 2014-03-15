package com.EasyPeasy.buymybook;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/*
 * xml file: fragment_results_detail
 */
public class SearchResultsFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		
		View view = inflater.inflate(R.layout.fragment_results_search,container,false);
		return view;
		
	}
}
