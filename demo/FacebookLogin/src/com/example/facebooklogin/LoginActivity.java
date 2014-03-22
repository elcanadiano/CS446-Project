package com.example.facebooklogin;

import com.facebook.*;
import com.facebook.model.*;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.content.Intent;


public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// start Facebook Login
		Session.openActiveSession(this, true, new Session.StatusCallback() {

			// callback when session changes state
			@Override
			public void call(Session session, SessionState state, Exception exception) {
				if (session.isOpened()) {
					// make request to the /me API
					Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

					  // callback after Graph API response with user object
					  @Override
					  public void onCompleted(GraphUser user, Response response) {
						  if (user != null) {
							  TextView welcome = (TextView) findViewById(R.id.welcome);
							  welcome.setText("Hello " + user.getName() + "!");
							}
					  }
					});
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	/*
	 *  An important part of the Facebook SDK is the Session class which manages  
	 *  much of the process of authenticating and authorizing users.
	 * 
	 *  Since the login flow for your app will require the users to transition 
	 *  out of, and back into, this Activity, we need a small amount of wiring 
	 *  to update the active session
	 *  
	 *  (More comprehensive implementations of the Session and Activity life 
	 *  cycles are used in the sample apps, and described in the Facebook Login 
	 *  guide. For example, you will want to benefit from caching tokens, 
	 *  resuming sessions and the like.)
	 *  
	 */

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}

}