package com.example.facebooklogin;

import java.util.Arrays;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.*;
import com.facebook.internal.Logger;
import com.facebook.model.*;
import com.facebook.widget.LoginButton;

public class LoginFragment extends Fragment {
	
	private static final String TAG = "LoginFragment";
	private UiLifecycleHelper uiHelper;
	
	 View view = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, 
	        Bundle savedInstanceState) {
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

	    return view;
	}
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	// Does stuff when the user is logged in and when the user is not logged in
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        final TextView accessToken = (TextView) view.findViewById(R.id.accessToken);
        final TextView userId = (TextView) view.findViewById(R.id.userId);
        final TextView name = (TextView) view.findViewById(R.id.name);
        final TextView fbURL = (TextView) view.findViewById(R.id.fbURL);
        
	    if (state.isOpened()) {
	        Log.i(TAG, "Logged in...");
	        
	        accessToken.setText("The session's access token is: " + session.getAccessToken()
	        	+ "\nThe access token will expire on: " + session.getExpirationDate());
	        
	        Request.newMeRequest(session, new Request.GraphUserCallback() {
        		
      		  // callback after Graph API response with user object
      		  @Override
      		  public void onCompleted(GraphUser user, Response response) {
      		    if (user != null) {
      		    	userId.setText("Your userId is: " + user.getId());
      		    	name.setText("Your first name is: " + user.getFirstName() 
      		    			+ " and your last name is: " + user.getLastName());
      		    	fbURL.setText("Your Facebook URL is: " + user.getLink());
      		    	
      		    	// Should we query the server here to see if the user exists?
      		    	
      		    } else {
      		    	userId.setText("Sorry, something went wrong and we were not able to get your Facebook information.");
      		    }
      		  }
      		}).executeAsync();
	        
	        accessToken.setVisibility(View.VISIBLE);
	        userId.setVisibility(View.VISIBLE);
	        name.setVisibility(View.VISIBLE);
	        fbURL.setVisibility(View.VISIBLE);
	    } else if (state.isClosed()) {
	        Log.i(TAG, "Logged out...");
	        accessToken.setVisibility(View.INVISIBLE);
	        userId.setVisibility(View.INVISIBLE);
	        name.setVisibility(View.INVISIBLE);
	        fbURL.setVisibility(View.INVISIBLE);
	    }
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