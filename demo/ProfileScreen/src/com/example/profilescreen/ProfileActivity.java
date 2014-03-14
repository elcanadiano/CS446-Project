package com.example.profilescreen;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

public class ProfileActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_profile);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.new_title_bar);
		
		// Get name, phone, text, and email that were supplied by the user
		String profile = getIntent().getExtras().getString("name") + "'s profile";
		String imageName = getIntent().getExtras().getString("image");
		int color = Integer.parseInt(getIntent().getExtras().getString("color"), 16)+0xFF000000;
		
		// Find and display wallpaper
		RelativeLayout ll = ((RelativeLayout)findViewById(R.id.profile));
		int imageId = getResources().getIdentifier(imageName + "wallpaper", "drawable", getPackageName());
		if (imageId == 0) {
			Toast toast = Toast.makeText(
					getApplicationContext(),
					"Could not find file: " + imageName + "wallpaper",
					Toast.LENGTH_SHORT);
			toast.show();
		} else {
			ll.setBackgroundDrawable(getResources().getDrawable(imageId));
		}
		
		// Find and display profile picture
		ImageView image = ((ImageView)findViewById(R.id.profile_pic));
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(image.getLayoutParams());
		lp.setMargins(30, 70, 0, 0);
		image.setLayoutParams(lp);
		imageId = getResources().getIdentifier(imageName, "drawable", getPackageName());
		if (imageId == 0) {
			Toast toast = Toast.makeText(
					getApplicationContext(),
					"Could not find file: " + imageName,
					Toast.LENGTH_SHORT);
			toast.show();
		} else {
			image.setImageDrawable(getResources().getDrawable(imageId));
		}
		
		// Put name, join date, phone, text, and email
		((TextView)findViewById(R.id.nameandsignup)).setText(
				getIntent().getExtras().getString("name") + "\n" + "Joined: 03/10/2014");
		((TextView)findViewById(R.id.phone)).setText(
				"Call: " + getIntent().getExtras().getString("phone"));
		((TextView)findViewById(R.id.text)).setText(
				"Text: " + getIntent().getExtras().getString("text"));
		((TextView)findViewById(R.id.email)).setText(
				"Email: " + getIntent().getExtras().getString("email"));
		((TextView)findViewById(R.id.myTitle)).setText(
				profile);
		((TextView)findViewById(R.id.myTitle)).setBackgroundColor(
				color);

		// Launch Messaging app if user clicks on text messaging number
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
}
