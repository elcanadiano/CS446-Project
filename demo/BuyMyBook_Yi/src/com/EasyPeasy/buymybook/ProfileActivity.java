package com.EasyPeasy.buymybook;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NavUtils;
import android.telephony.SmsManager;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

// Yi - make sure this extends MainActivity when merged into Main Activity
// Keep image if Facebook image works
public class ProfileActivity extends MainActivity {
	final Context context = this;
	final String tag = "ProfileActivity";
	
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
		this.dieAfterFinish=true;
		//this.setupDrawer(savedInstanceState);
		
		editPhoneNumber = (EditText)findViewById(R.id.phone);
		editEmailAddress = (EditText)findViewById(R.id.email);
		
		phoneme = (TextView)findViewById(R.id.phoneme);
		emailme = (TextView)findViewById(R.id.emailme);
		textme = (TextView)findViewById(R.id.textme);
		
		originalPhoneKeyListener = editPhoneNumber.getKeyListener();
		originalEmailKeyListener = editEmailAddress.getKeyListener();
		
		//Put name, join date, phone, text, and email
		((TextView)findViewById(R.id.first_name)).setText(
				//getIntent().getExtras().getString("firstName"));
				"Your first name here");
		((TextView)findViewById(R.id.last_name)).setText(
				//getIntent().getExtras().getString("lastName"));
				"Your last name here");
		editPhoneNumber.setText(
				//getIntent().getExtras().getString("phone"));
				"5198888888");
		editEmailAddress.setText(
				//getIntent().getExtras().getString("email"));
				"khusain@uwaterloo.ca");
		((TextView)findViewById(R.id.books_selling_text)).setText(
				"Books that " + 
				//getIntent().getExtras().getString("firstName") + 
				//" " + getIntent().getExtras().getString("lastName") +
				//" is selling");
				"you are selling");
		
		// Find and display profile picture
		//String imageName = getIntent().getExtras().getString("image");
		String imageName = "bryan";
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
		
		ListView lv = (ListView) findViewById(R.id.my_books);
		
		ArrayList<String> myArrayOfBooks = new ArrayList<String>();
		myArrayOfBooks.add("Introduction to Winning");
		myArrayOfBooks.add("Winning: The Charlie Sheen Story");
		myArrayOfBooks.add("Introduction to Android");
		myArrayOfBooks.add("Linear Algebra I");
		myArrayOfBooks.add("C++ book");
		myArrayOfBooks.add("Java: The good parts");
		myArrayOfBooks.add("C# in a nutshell");
		myArrayOfBooks.add("Introduction to Macroeconomics 4th Canadian Edition Revision 2 UWaterloo Edition Golden Hardcover Limited Print Foreward by Larry Smith");
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
			this,
			android.R.layout.simple_list_item_1,
			myArrayOfBooks );
		
		lv.setAdapter(arrayAdapter);
		
		editPhoneNumber.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editPhoneNumber.setKeyListener(originalPhoneKeyListener);
			}
		});
		
		editPhoneNumber.setOnEditorActionListener(
			    new EditText.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND
			            || actionId == EditorInfo.IME_ACTION_DONE
			            || actionId == EditorInfo.IME_ACTION_GO
			            || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
			    	
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
				    	editPhoneNumber.clearFocus();
				    	hideKeyboard(v);
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
			}
		});
	
		editEmailAddress.setOnEditorActionListener(
			    new EditText.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND
			            || actionId == EditorInfo.IME_ACTION_DONE
			            || actionId == EditorInfo.IME_ACTION_GO
			            || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
			    	Toast toast = Toast.makeText(
				   		getApplicationContext(),
				    	"Email address has been updated",
				    	Toast.LENGTH_SHORT);
				   	toast.show();
				   	editEmailAddress.clearFocus();
				   	hideKeyboard(v);
				   	return true;
			    } else {
			    	return false; // pass on to other listeners. 
			    }
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
				
		// Allows the ability to send a text message
		textme.setOnClickListener(new View.OnClickListener() {
			// Need because Build.VERSION if statement uses KitKat specific stuff
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				String smsText = "HELLO WORLD";
				String smsNumber = "5195728132";
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //At least KitKat
					String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(getApplicationContext());
					Intent sendIntent = new Intent(
						Intent.ACTION_SENDTO, 
						Uri.parse("smsto:" + Uri.encode(smsNumber)));
					sendIntent.putExtra("sms_body", smsText);

					// If the user doesn't have a default app that supports SMS
					if (defaultSmsPackageName != null) {
						sendIntent.setPackage(defaultSmsPackageName);
					}
					startActivity(sendIntent);

				} else { // Stuff for pre-KitKat
					Intent sendIntent = new Intent(Intent.ACTION_VIEW);
					sendIntent.setData(Uri.parse("sms:"));
					sendIntent.putExtra("sms_body", smsText);
					sendIntent.putExtra("address", smsNumber);
					startActivity(sendIntent);
				}
			}
		});
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// Called when user touches the image
    public void goToFacebook(View view) {
    	Toast toast = Toast.makeText(
    		getApplicationContext(),
    		"This will redirect to your Facebook profile in the future",
    		Toast.LENGTH_SHORT);
    	toast.show();
    }
    
	private void hideKeyboard(View view) {
	    InputMethodManager manager = (InputMethodManager) view.getContext()
	            .getSystemService(INPUT_METHOD_SERVICE);
	    if (manager != null)
	        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
}