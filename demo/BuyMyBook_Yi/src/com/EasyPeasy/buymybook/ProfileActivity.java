package com.EasyPeasy.buymybook;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.NavUtils;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.widget.ProfilePictureView;

public class ProfileActivity extends MainActivity {
	final Context context = this;
	final String tag = "ProfileActivity";
	
	private EditText editPhoneNumber;
	private EditText editTextNumber;
	private EditText editEmailAddress;
	
	private KeyListener originalPhoneKeyListener;
	private KeyListener originalTextKeyListener;
	private KeyListener originalEmailKeyListener;
	
	private ImageView callme;
	private ImageView textme;
	private ImageView emailme;
	
	private String fbUserId;
	
	//db stuff
	private DBHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		this.dieAfterFinish=true;
		
		fbUserId = getIntent().getStringExtra("userId");
		
		editPhoneNumber = (EditText)findViewById(R.id.phone);
		editTextNumber = (EditText)findViewById(R.id.message);
		editEmailAddress = (EditText)findViewById(R.id.email);
		
		callme = (ImageView)findViewById(R.id.phone_img);
		emailme = (ImageView)findViewById(R.id.email_img);
		textme = (ImageView)findViewById(R.id.message_img);
		
		originalPhoneKeyListener = editPhoneNumber.getKeyListener();
		originalTextKeyListener = editTextNumber.getKeyListener();
		originalEmailKeyListener = editEmailAddress.getKeyListener();
		
		//Put name, join date, phone, text, and email
		((TextView)findViewById(R.id.first_name)).setText("Your first name here");
		((TextView)findViewById(R.id.last_name)).setText("Your last name here");
		editPhoneNumber.setText("5198888888");
		editTextNumber.setText("2268888888");
		editEmailAddress.setText("khusain@uwaterloo.ca");
		((TextView)findViewById(R.id.books_selling_text)).setText("Books that you are selling");
		
		((ProfilePictureView)findViewById(R.id.profile_pic)).setProfileId(fbUserId);
		
		dbHelper = new DBHelper(this);
		
		ArrayList<SearchListItem> myArrayOfBooks = new ArrayList<SearchListItem>();
		
		//gets data from local db
		Cursor c = dbHelper.cursorSelectAll();
		c.moveToFirst();
		while (!c.isAfterLast()) {
			String listing_id=c.getString(0); //listing ID
			String title=c.getString(1); //title
			String author=c.getString(2);
			String price=c.getString(3);
			String condition=c.getString(4);
			
			SearchListItem item = new SearchListItem(
					title,
					author,
					price,
					condition);
			System.out.println("in ProfileActiving loading book "+title);
			myArrayOfBooks.add(item);
			c.moveToNext();
		}
		/*
		for (int i = 0; i < 10; i++) {
			String xt = "LOL " + i;
			SearchListItem item = new SearchListItem(
					xt,
					"author here",
					"12.98",
					"1");
			myArrayOfBooks.add(item);
		}*/
		
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
		
		editTextNumber.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editTextNumber.setKeyListener(originalTextKeyListener);
			}
		});
		
		editTextNumber.setOnEditorActionListener(
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
				    		"You must enter 10 digits for the texting number",
				    		Toast.LENGTH_SHORT);
				    	toast.show();
				    	return false;
			    	} else {
			    		Toast toast = Toast.makeText(
				    		getApplicationContext(),
				    		"Texting number has been updated",
				    		Toast.LENGTH_SHORT);
				    	toast.show();
				    	editTextNumber.clearFocus();
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
		
		callme.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent callIntent = new Intent(Intent.ACTION_DIAL);
				callIntent.setData(Uri.parse("tel:"+ editPhoneNumber.getText().toString()));
				callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				startActivity(callIntent);
			}
		});
		
		// Allows the ability to send a text message
		textme.setOnClickListener(new View.OnClickListener() {
			// Need because Build.VERSION if statement uses KitKat specific stuff
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				String smsText = "HELLO WORLD";
				String smsNumber = editTextNumber.getText().toString();
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //At least KitKat
					String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(getApplicationContext());
					Intent sendIntent = new Intent(
						Intent.ACTION_SENDTO, 
						Uri.parse("smsto:" + Uri.encode(smsNumber))
					);
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
		
		// Allows the ability to send an email
		emailme.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String aEmailList[] = { editEmailAddress.getText().toString()};
						
				final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
			    intent.setType("plain/text");
			    intent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
			    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Reply to your <BOOK TITLE> ad on Buy My Book!");
			    intent.putExtra(android.content.Intent.EXTRA_TEXT, "<EMAIL BODY TEXT HERE>");
			    startActivity(intent);
			}
		});
	
		Log.i("profileActivity", "time for drawer setup");
		//install drawer
		//this.setupDrawer(savedInstanceState);
		Log.i("profileActivity", "Drawer setup worked");
		
		final ListView lv1 = (ListView) findViewById(R.id.my_books);
		if(lv1 != null){
			lv1.setAdapter(new CustomPeronalListingAdapter(this, myArrayOfBooks));
			lv1.setItemsCanFocus(true);
			lv1.setFocusable(false);
			lv1.setFocusableInTouchMode(false);
			lv1.setClickable(true);
			lv1.setOnItemClickListener(new OnItemClickListener() {
		    /*
		     * Click listener for each item view
		     * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
		     */
		    @Override
		    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		    	Object o = lv1.getItemAtPosition(position);
		        SearchListItem newsData = (SearchListItem) o;
		        Toast nre = Toast.makeText(
		        	getApplicationContext(), 
		            "selected :" + " " + newsData.getTitle(), 
		            Toast.LENGTH_SHORT);
		        nre.show();
		    }

		});
		   }
		   else{
			   Log.d(tag,"searchresultfragment, adapter null");
		   }	       
	} // onCreate end
	
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
	
	// When user presses the profile pic, it will go to the Facebook profile
    public void goToFacebook(View view) {
    	try { // Try launching through Facebook app first
    	    context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
    	    String URI = "fb://profile/" + fbUserId;
    	    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URI));
    	    startActivity(intent);
    	} catch (Exception e) { // Fallback to web browser if Facebook app doesn't exist or failed to launch
    		//String URI2 = "https://www.facebook.com/" + 
    		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/booker.book3"));
    	   	startActivity(intent);
    	}
    }
    
	private void hideKeyboard(View view) {
	    InputMethodManager manager = (InputMethodManager) view.getContext()
	            .getSystemService(INPUT_METHOD_SERVICE);
	    if (manager != null)
	        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
}