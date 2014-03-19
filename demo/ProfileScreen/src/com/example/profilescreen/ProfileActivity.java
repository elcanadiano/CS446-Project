package com.example.profilescreen;

import java.util.ArrayList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.telephony.SmsManager;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

// Yi - make sure this extends MainActivity when merged into Main Activity
// Keep image if Facebook image works
public class ProfileActivity extends Activity {
	
	private EditText editPhoneNumber;
	private EditText editEmailAddress;
	private TextView phoneme;
	private TextView emailme;
	private TextView textme;
	
	private KeyListener originalPhoneKeyListener;
	private KeyListener originalEmailKeyListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		editPhoneNumber = (EditText)findViewById(R.id.phone);
		editEmailAddress = (EditText)findViewById(R.id.email);
		
		phoneme = (TextView)findViewById(R.id.phoneme);
		emailme = (TextView)findViewById(R.id.emailme);
		textme = (TextView)findViewById(R.id.textme);
		
		originalPhoneKeyListener = editPhoneNumber.getKeyListener();
		originalEmailKeyListener = editEmailAddress.getKeyListener();
		
		//Put name, join date, phone, text, and email
		((TextView)findViewById(R.id.first_name)).setText(
				getIntent().getExtras().getString("firstName"));
		((TextView)findViewById(R.id.last_name)).setText(
				getIntent().getExtras().getString("lastName"));
		editPhoneNumber.setText(
				getIntent().getExtras().getString("phone"));
		editEmailAddress.setText(
				getIntent().getExtras().getString("email"));
		((TextView)findViewById(R.id.books_selling_text)).setText(
				"Books that " + 
						getIntent().getExtras().getString("firstName") + 
						" " + getIntent().getExtras().getString("lastName") +
						" is selling");
		
		// Find and display profile picture
		String imageName = getIntent().getExtras().getString("image");
		ImageButton image = ((ImageButton)findViewById(R.id.profile_pic));
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
		
		editPhoneNumber.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editPhoneNumber.setKeyListener(originalPhoneKeyListener);
				editPhoneNumber.requestFocus(); // Might not be needed
			}
		});
		
		editPhoneNumber.setOnEditorActionListener(
			    new EditText.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			    if (event != null &&
			    	actionId == EditorInfo.IME_ACTION_SEARCH ||
			    	actionId == EditorInfo.IME_ACTION_DONE ||
			        event.getAction() == KeyEvent.ACTION_DOWN &&
			        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
			    	
			    	// Check if user entered 
			    	if (v.length() != 10) {
			    		Toast toast = Toast.makeText(
				    		getApplicationContext(),
				    		"You must enter 10 digits for the phone number",
				    		Toast.LENGTH_SHORT);
				    	toast.show();
				    	return false;
			    	} else {
			    		Toast toast = Toast.makeText(
				    		getApplicationContext(),
				    		"Phone number has been updated",
				    		Toast.LENGTH_SHORT);
				    	toast.show();
				    	editPhoneNumber.setKeyListener(null);
				    	editPhoneNumber.clearFocus();
				    	return true;
			    	}
			    } else {
			    return false; // pass on to other listeners. 
			}
		}
		});
	
		editEmailAddress.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editEmailAddress.setKeyListener(originalEmailKeyListener);
				editEmailAddress.requestFocus(); // Might not be needed
			}
		});
		
		// Allows the ability to send an email
		emailme.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String aEmailList[] = { "user@fakehost.com","user2@fakehost.com" };

				
				final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
		        intent.setType("plain/text");
		        intent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
		        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "OMG SUBJECT");
		        intent.putExtra(android.content.Intent.EXTRA_TEXT, "OMG SELFIE!");
		        startActivity(intent);
			}
		});
		
		// Allows the ability to send a test message
		textme.setOnClickListener(new View.OnClickListener() {
			
			//  Need becuase Build.VERSION if stmt uses KitKat specific stuff
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				String smsText = "HELLO WORLD";
				String smsNumber = "5195728132";
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) //At least KitKat
			    {
			        String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(getApplicationContext());
			        Intent sendIntent = new Intent(
			        		Intent.ACTION_SENDTO, 
			        		Uri.parse("smsto:" + Uri.encode(smsNumber)));
			        sendIntent.putExtra("sms_body", smsText);

			        // If the user doesn't have a default app that supports SMS
			        if (defaultSmsPackageName != null)
			        {
			            sendIntent.setPackage(defaultSmsPackageName);
			        }
			        startActivity(sendIntent);

			    }
			    else // Stuff for pre KitKat
			    {
			        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
			        sendIntent.setData(Uri.parse("sms:"));
			        sendIntent.putExtra("sms_body", smsText);
			        sendIntent.putExtra("address", smsNumber);
			        startActivity(sendIntent);
			    }
			}
		});
	
		editEmailAddress.setOnEditorActionListener(
			    new EditText.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			    if (event != null &&
			    		actionId == EditorInfo.IME_ACTION_SEARCH ||
			            actionId == EditorInfo.IME_ACTION_DONE ||
			            event.getAction() == KeyEvent.ACTION_DOWN &&
			            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
			    	Toast toast = Toast.makeText(
				   		getApplicationContext(),
				    	"Email address has been updated",
				    	Toast.LENGTH_SHORT);
				   	toast.show();
				   	editEmailAddress.setKeyListener(null);
				   	editEmailAddress.clearFocus();
				   	return true;
			    } else {
			    	return false; // pass on to other listeners. 
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
	
	// Called when user touches the image
    public void goToFacebook(View view) {
    	Toast toast = Toast.makeText(
    		getApplicationContext(),
    		"This will redirect to your Facebook profile in the future",
    		Toast.LENGTH_SHORT);
    	toast.show();
    }
}