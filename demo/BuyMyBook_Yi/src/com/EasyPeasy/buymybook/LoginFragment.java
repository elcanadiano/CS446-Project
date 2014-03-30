package com.EasyPeasy.buymybook;

import java.util.Arrays;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.*;
import com.facebook.model.*;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

public class LoginFragment extends Fragment {
	
	private static final String TAG = "LoginFragment";
	private UiLifecycleHelper uiHelper;

    boolean phoneNumCorrect = false;
    boolean textNumCorrect = false;
    boolean newEmailCorrect = false;
    boolean newUser = false;
    View view = null;
    String fbUserId = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("onCreate:39", "Accessing this function");
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, 
	        Bundle savedInstanceState) {
		
		Log.i("onCreateView:49", "Accessing this function");
	    view = inflater.inflate(R.layout.activity_login, container, false);
	    
	    LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
	    authButton.setFragment(this);
	    // We would like the user's basic info (name, user id, picture) and their email address
	    /*
	     * "basic_info" is implied with every request. It's best practice to include it as per Facebook SDK documentation
	     * "basic_info" can retrieve: id, name, first_name, last_name, link, user name, gender, locale, age_range and other public info
	     * "email" retrieves the user's primary email address.
	     * "publish_actions" lets the app post content/comment/likes to a user's stream (might be useful feature in future)
	     */
	    authButton.setReadPermissions(Arrays.asList("basic_info", "email"));
	    
	    Button newPage = (Button) view.findViewById(R.id.goToMain);
	    newPage.setOnClickListener(new View.OnClickListener() {
			
	    	// Go to main page when user clicks on the appropriate button
			@Override
			public void onClick(View v) {
				
				if (newUser) {
					// Do stuff if you are a new user?
				}
				
				Intent intent = new Intent(getActivity(), MainActivity.class);
				intent.putExtra("userId", fbUserId);
				getActivity().startActivity(intent);
			}
		});
	    
	    return view;
	}
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	    	Log.i("Session.StatusCallback:84", "Accessing this function");
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	// Does stuff when the user is logged in and when the user is not logged in
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		
		Log.i("onSessionStateChange:91", "Accessing this function");
        final TextView accessToken = (TextView) view.findViewById(R.id.accessToken);
        final TextView userId = (TextView) view.findViewById(R.id.userId);
        final TextView welcome = (TextView) view.findViewById(R.id.welcome);
        final TextView name = (TextView) view.findViewById(R.id.name);
        final TextView informUser = (TextView) view.findViewById(R.id.informUserForMoreInfo);
        final EditText phoneNum = (EditText) view.findViewById(R.id.phoneNum);
        final EditText textNum = (EditText) view.findViewById(R.id.textNum);
        final EditText newEmail = (EditText) view.findViewById(R.id.newEmail);
        final TextView email = (TextView) view.findViewById(R.id.email);
        final TextView fbURL = (TextView) view.findViewById(R.id.fbURL);
        final Button goToMain = (Button) view.findViewById(R.id.goToMain);
        final ProfilePictureView profilePic = (ProfilePictureView) view.findViewById(R.id.profilePicture);
        
        Log.i("onSessionStateChange:109", "Called up the necessary Views and Buttons");
        final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
        	"[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
        );
        
        if (state == null) {
        	Log.i("onSessionStateChange:121", "why is my state null?");
        }
        
	    if (state.isOpened()) {
	        Log.i(TAG, "Logged in...");
	        
	        accessToken.setText("The session's access token is: " + session.getAccessToken()
	        	+ "\nThe access token will expire on: " + session.getExpirationDate());
	        
	        Request.newMeRequest(session, new Request.GraphUserCallback() {
        		
      		  // callback after Graph API response with user object
      		  @Override
      		  public void onCompleted(GraphUser user, Response response) {
      		    if (user != null) {
      		    	/*
      		    	 * user ID: user.getId()
      		    	 * First Name: user.getFirstName()
      		    	 * Last Name: user.getLastName()
      		    	 * Email: user.asMap().get("email")
      		    	 * FB URL: user.getLink();
      		    	 */
      		    	//welcome.setText(getResources().getString(R.string.welcome_back));
      		    	userId.setText("Your userId is: " + user.getId());
      		    	name.setText(user.getFirstName() + " " + user.getLastName());
      		    	email.setText("Your email address is: " + user.asMap().get("email"));
      		    	fbURL.setText("Your Facebook URL is: " + user.getLink());
      		    	
      		    	fbUserId = user.getId();
      		    	
      		    	// Facebook Profile Picture -- http://graph.facebook.com/id/picture
      		    	// For Booker Book, it is http://graph.facebook.com/100008045347915/picture
      		    	profilePic.setProfileId(user.getId());
      		    } else {
      		    	userId.setText("Sorry, something went wrong and we were not able to get your Facebook information.");
      		    	return;
      		    }
      		  }
      		}).executeAsync();
	        accessToken.setVisibility(View.VISIBLE);
	        userId.setVisibility(View.VISIBLE);
	        name.setVisibility(View.VISIBLE);
	        email.setVisibility(View.VISIBLE);
	        fbURL.setVisibility(View.VISIBLE);
	        profilePic.setVisibility(View.VISIBLE);
	        
	        // Check if user exists in the database
	        if (true) { // Doesn't exist in database
	        	newUser = false;
		        welcome.setText(getResources().getString(R.string.welcome_new_user));
	        	informUser.setText(getResources().getString(R.string.inform_user));
	        	informUser.setVisibility(View.VISIBLE);
	        	phoneNum.setVisibility(View.VISIBLE);
	        	phoneNum.setText("8888888888");				// TESTING PURPOSES
	        	textNum.setVisibility(View.VISIBLE);
	        	textNum.setText("8888888888");				// TESTING PURPOSES
	        	newEmail.setVisibility(View.VISIBLE); 
	        	newEmail.setText("booker@buymybook.com"); 	// TESTING PURPOSES
	        	goToMain.setVisibility(View.VISIBLE);		// TESTING PURPOSES
	        } else { // Exists in database -- DEAD CODE
	        	newUser = true;
	        	welcome.setText(getResources().getString(R.string.welcome_back));
	        	informUser.setVisibility(View.INVISIBLE);
	        	phoneNum.setVisibility(View.INVISIBLE);
	        	textNum.setVisibility(View.INVISIBLE);
	        	newEmail.setVisibility(View.INVISIBLE);
	        	goToMain.setVisibility(View.VISIBLE);
	        }
	    } else if (state.isClosed()) {
	        Log.i(TAG, "Logged out...");
	        welcome.setText(getResources().getString(R.string.welcome));
	        accessToken.setVisibility(View.INVISIBLE);
	        userId.setVisibility(View.INVISIBLE);
	        name.setVisibility(View.INVISIBLE);
	        informUser.setVisibility(View.INVISIBLE);
	        phoneNum.setVisibility(View.INVISIBLE);
        	textNum.setVisibility(View.INVISIBLE);
        	newEmail.setVisibility(View.INVISIBLE);
	        email.setVisibility(View.INVISIBLE);
	        fbURL.setVisibility(View.INVISIBLE);
	        goToMain.setVisibility(View.INVISIBLE);
	        profilePic.setVisibility(View.INVISIBLE);
	    }
	    
	    phoneNum.setOnEditorActionListener(
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
			    		    getActivity().getApplicationContext(),
				    		"You must enter 10 digits for the phone number",
				    		Toast.LENGTH_SHORT);
				    	toast.show();
				    	phoneNumCorrect = false;
				    	goToMain.setVisibility(View.INVISIBLE);
				    	informUser.setVisibility(View.VISIBLE);
				    	return false;
			    	} else {
			    		Toast toast = Toast.makeText(
			    			getActivity().getApplicationContext(),
			    			"Thanks for providing a valid phone number",
				    		Toast.LENGTH_SHORT);
				    	toast.show();
				    	phoneNum.clearFocus();
				    	phoneNumCorrect = true;
				    	if (phoneNumCorrect && textNumCorrect && newEmailCorrect) {
				    		informUser.setVisibility(View.INVISIBLE);
				    		goToMain.setVisibility(View.VISIBLE);
				    	}
				    	//hideKeyboard(v, 1);
				    	return true;
			    	}
			    } else {
			    	return false; // pass on to other listeners. 
			    }
			}
		});
	    
	    textNum.setOnEditorActionListener(
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
			    		    getActivity().getApplicationContext(),
				    		"You must enter 10 digits for the texting number",
				    		Toast.LENGTH_SHORT);
				    	toast.show();
				    	textNumCorrect = false;
				    	goToMain.setVisibility(View.INVISIBLE);
				    	informUser.setVisibility(View.VISIBLE);
				    	return false;
			    	} else {
			    		Toast toast = Toast.makeText(
			    			getActivity().getApplicationContext(),
				    		"Thanks for providing a valid texting number",
				    		Toast.LENGTH_SHORT);
				    	toast.show();
				    	textNum.clearFocus();
				    	textNumCorrect = true;
				    	
				    	if (phoneNumCorrect && textNumCorrect && newEmailCorrect) {
				    		informUser.setVisibility(View.INVISIBLE);
				    		goToMain.setVisibility(View.VISIBLE);
				    	}
				    	//hideKeyboard(v, 2);
				    	return true;
			    	}
			    } else {
			    	return false; // pass on to other listeners. 
			    }
			}
		});
	    
	    newEmail.setOnEditorActionListener(
			    new EditText.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND
			            || actionId == EditorInfo.IME_ACTION_DONE
			            || actionId == EditorInfo.IME_ACTION_GO
			            || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
			    	
			    	// Check if user entered 
			    	if (! EMAIL_ADDRESS_PATTERN.matcher(newEmail.getText()).matches()) {
			    		Toast toast = Toast.makeText(
			    		    getActivity().getApplicationContext(),
				    		"You must enter a valid email address",
				    		Toast.LENGTH_SHORT);
				    	toast.show();
				    	newEmailCorrect = false;
				    	goToMain.setVisibility(View.INVISIBLE);
				    	informUser.setVisibility(View.VISIBLE);
				    	return false;
			    	} else {
			    		Toast toast = Toast.makeText(
			    			getActivity().getApplicationContext(),
			    			"Thanks for providing a valid email addressz",
				    		Toast.LENGTH_SHORT);
				    	toast.show();
				    	newEmail.clearFocus();
				    	newEmailCorrect = true;
				    	
				    	if (phoneNumCorrect && textNumCorrect && newEmailCorrect) {
				    		informUser.setVisibility(View.INVISIBLE);
				    		goToMain.setVisibility(View.VISIBLE);
				    	}
				    	//hideKeyboard(v, 2);
				    	return true;
			    	}
			    } else {
			    	return false; // pass on to other listeners. 
			    }
			}
		});
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    
	    // For scenarios where the main activity is launched and user
	    // session is not null, the session state change notification
	    // may not be triggered. Trigger it if it's open/closed.
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }
	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
}