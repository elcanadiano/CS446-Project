package com.example.facebooklogin;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.facebook.*;
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
      public void call(Session session, SessionState state, Exception exception) {
        if (session.isOpened()) {

        	Request.newMeRequest(session, new Request.GraphUserCallback() {

        		  // callback after Graph API response with user object
        		  @Override
        		  public void onCompleted(GraphUser user, Response response) {
        		      TextView welcome = (TextView) findViewById(R.id.welcome);
        		    if (user != null) {
        		      welcome.setText("Hello " + user.getName() + "!");
        		    } else {
        		    	welcome.setText("FAILED TO GET USER");
        		    }
        		  }
        		}).executeAsync();
        }
      }
    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
  }

}