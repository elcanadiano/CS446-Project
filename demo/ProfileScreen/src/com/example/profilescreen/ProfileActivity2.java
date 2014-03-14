package com.example.profilescreen;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity2 extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile2);
		
		//Put name, join date, phone, text, and email
		((TextView)findViewById(R.id.name)).setText(
				getIntent().getExtras().getString("name"));
		//((TextView)findViewById(R.id.signup)).setText(
		//		"Joined: 03/10/2014");
		((TextView)findViewById(R.id.phone)).setText(
				getIntent().getExtras().getString("phone"));
		//((TextView)findViewById(R.id.text)).setText(
		//		getIntent().getExtras().getString("text"));
		((TextView)findViewById(R.id.email)).setText(
				getIntent().getExtras().getString("email"));
		
		// Find and display profile picture
		String imageName = getIntent().getExtras().getString("image");
		ImageView image = ((ImageView)findViewById(R.id.profile_pic));
		int imageId = getResources().getIdentifier(imageName, "drawable", getPackageName());
		if (imageId == 0) {
		Toast toast = Toast.makeText(
			getApplicationContext(),
			"Could not find file: " + imageName,
			Toast.LENGTH_SHORT);
			toast.show();
		} else {
			image.setImageDrawable(getResources().getDrawable(imageId));
		}

		// Launch Messaging app if user clicks on text messaging number
		/*
		TextView text = (TextView)findViewById(R.id.text);
		text.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Intent smsIntent = new Intent(Intent.ACTION_VIEW);
					smsIntent.putExtra("address", getIntent().getExtras().getString("phone"));
					smsIntent.putExtra("sms_body", "Hey! I found your profile on Buy My Book!");
					smsIntent.setType("vnd.android-dir/mms-sms");
					startActivity(smsIntent);
				} catch (Exception e) {
					Toast toast = Toast.makeText(
						getApplicationContext(),
						"SMS Failed!",
						Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
}
