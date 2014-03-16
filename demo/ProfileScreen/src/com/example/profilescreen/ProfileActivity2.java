package com.example.profilescreen;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
		((TextView)findViewById(R.id.books_selling_text)).setText(
				"Books that " + getIntent().getExtras().getString("name") + " is selling");
		
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
		
		ListView lv = (ListView) findViewById(R.id.my_books);
		
		ArrayList<String> myArrayOfBooks = new ArrayList<String>();
		myArrayOfBooks.add("Foo");
		myArrayOfBooks.add("Bar");
		myArrayOfBooks.add("Foobar");
		myArrayOfBooks.add("Booker");
		myArrayOfBooks.add("Samsung");
		myArrayOfBooks.add("Buy My Book");
		myArrayOfBooks.add("Test1");
		myArrayOfBooks.add("Test2");
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				this,
				android.R.layout.simple_list_item_1,
				myArrayOfBooks );
		
		lv.setAdapter(arrayAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
}
