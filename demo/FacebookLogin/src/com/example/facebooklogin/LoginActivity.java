package com.example.facebooklogin;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.facebook.*;
import com.facebook.internal.Logger;
import com.facebook.model.*;

public class LoginActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    // start Facebook Login
    
    Session.openActiveSession(this, true, new Session.StatusCallback() {
    	
      // callback when session changes state
	@Override
    public void call(
    	Session session,
    	SessionState state,
    	Exception exception
    ) {
		if (session.isOpened()) {
			
			// Get the access token
			/*
			 * An access token is an opaque string that identifies a user, app, 
			 * or page and can be used by the app to make graph API calls.
			 * 
			 *  The token includes information about when the token will expire 
			 *  and which app generated the token. Because of privacy checks, the 
			 *  majority of API calls on Facebook need to include an access token.
			 *  
			 *  User Access Token – The user token is the most commonly used type 
			 *  of token. This kind of access token is needed any time the app 
			 *  calls an API to read, modify or write a specific person's Facebook 
			 *  data on their behalf. User access tokens are generally obtained 
			 *  via a login dialog and require a person to permit your app to 
			 *  obtain one.
			 */
			TextView accessToken = (TextView)findViewById(R.id.accessToken);
			accessToken.setText("Your access token is: " + session.getAccessToken()
					+ "\nThe access token will expire on: " + session.getExpirationDate().toLocaleString());
			
        	Request.newMeRequest(session, new Request.GraphUserCallback() {
        		
        		  // callback after Graph API response with user object
        		  @Override
        		  public void onCompleted(GraphUser user, Response response) {
        			  TextView welcome = (TextView) findViewById(R.id.welcome);
        		    if (user != null) {
        	        	welcome.setText("Thanks for logging in"
        	        		+ "\nYour first name is:" + user.getFirstName()
        		    		+ "\nYour last name is: " + user.getLastName()
        		    		+ "\nYour userId is: " + user.getId() 
        		    		+ "\nYour username is: " + user.getUsername()
        		    		+ "\nYour birthday is: " + user.getBirthday()
        		    		+ "\nYour Facebook URL is: " + user.getLink());
        		    } else {
        		    	welcome.setText("Sorry, something went wrong and we were not able to get your Facebook information.");
        		    }
        		  }
        		}).executeAsync();
        } // session.isOpened
      } // call
    });
    
  } // onCreate

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
  }

}