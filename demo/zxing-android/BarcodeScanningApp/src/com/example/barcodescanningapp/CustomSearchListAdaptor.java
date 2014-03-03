package com.example.barcodescanningapp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomSearchListAdaptor extends BaseAdapter{

	private ArrayList listData; //listData format:
	private LayoutInflater layoutInflater;
	
	public CustomSearchListAdaptor(Context context, ArrayList listData){
		this.listData = listData;
		layoutInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHolder holder;
	        if (convertView == null) {
	            convertView = layoutInflater.inflate(R.layout.searchlistinglayout, null);
	            holder = new ViewHolder();
	            holder.titleView = (TextView) convertView.findViewById(R.id.search_manual_btitle);
	            holder.authorView = (TextView) convertView.findViewById(R.id.search_manual_author);
	            holder.priceView = (TextView) convertView.findViewById(R.id.search_manual_price);
	            holder.condition = (TextView) convertView.findViewById(R.id.search_manual_condition);
	            
	            convertView.setTag(holder);
	        } else {
	            holder = (ViewHolder) convertView.getTag();
	        }
	        //output text
	        SearchListItem item = (SearchListItem)listData.get(position);
	        holder.titleView.setText(item.getTitle());
	        holder.authorView.setText(item.getAuthor());
	        holder.priceView.setText(item.getPrice());
	        holder.condition.setText(item.getCondition());
	        /*
	        holder.headlineView.setText(listData.get(position).getHeadline());
	        holder.reporterNameView.setText("By, " + listData.get(position).getReporterName());
	        holder.reportedDateView.setText(listData.get(position).getDate());
	 */
	        return convertView;
	}
	static class ViewHolder {
        TextView titleView;
        TextView authorView;
        TextView priceView;
        TextView condition;
    }
}
