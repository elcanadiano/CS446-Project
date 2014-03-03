package com.example.barcodescanningapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class PostScanConfirmationActivity extends Activity implements OnClickListener {
	String json;
	private TextView info;
	private Button confirmBtn;
	private EditText priceTag;
	private Spinner spinner;
	
	String title;
	String isbn;
	String author;
	String price;
	String condition;
	
	//local userdata storage
	private DBHelper dbHelper;
	
	@SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_scan_confirmation);
        
        json =getIntent().getExtras().getString("json");
        
        info = (TextView)findViewById(R.id.post_scan_text);
        confirmBtn = (Button)findViewById(R.id.post_scan_confirm);	
        confirmBtn.setOnClickListener(this);
        
        priceTag = (EditText)findViewById(R.id.post_scan_price);
        priceTag.setOnClickListener(
        		//THIS DOESN'T WORK!!!
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                    	//price=Integer.parseInt(priceTag.getText().toString());
                        
                    }
                });
        spinner=(Spinner)findViewById(R.id.post_scan_spinner);
        
        //set up db
        dbHelper = new DBHelper(this);
        
        //populate fields
        try {
			JSONObject jsonObj = new JSONObject(json);
			//title=jsonObj.getJSONArray("data");
			
			title=jsonObj.getJSONArray("data").getJSONObject(0).getString("title_latin");
			isbn=jsonObj.getJSONArray("data").getJSONObject(0).getString("isbn13");
			author= jsonObj.getJSONArray("data").getJSONObject(0).getJSONArray("author_data").getJSONObject(0).getString("name");
			price="30.00";
			condition="Good";
			
		// display text:
			info.setText("Is This what you are looking for?\n\n\n"+
						"Title: " + title +"\n"+
						"Author: " + author + "\n"
					);
				
				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.post_scan_confirm:
				//insert into database
				dbHelper.insert(title, isbn, author, price, condition);
				Cursor c = dbHelper.cursorSelectAll();
				info.setText("Your Book Has Been Posted!");
				break;

		}
		
	}



    
}
