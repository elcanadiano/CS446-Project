package com.example.barcodescanningapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class PostScanConfirmationActivity extends Activity implements OnClickListener {
	String json;
	int price;
	private Button confirmBtn;
	private EditText priceTag;
	
	@SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_scan_confirmation);
        
        json =getIntent().getExtras().getString("json");
        
        System.out.println(getIntent().getExtras().getString("json")+"successful pass!");
        
        confirmBtn = (Button)findViewById(R.id.post_scan_confirm);	
        confirmBtn.setOnClickListener(this);
        
        priceTag = (EditText)findViewById(R.id.post_scan_price);
        priceTag.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                    	price=Integer.parseInt(priceTag.getText().toString());
                        System.out.println("EditText" + price);
                    }
                });
    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.post_scan_confirm:
//insert into database
		}
		
	}



    
}
