package com.example.profilescreen;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Populate the fields - Debugging purposes
        ((EditText)findViewById(R.id.txt_name)).setText("Daniel Bryan");
        ((EditText)findViewById(R.id.txt_phone)).setText("(519)-888-8888");
        ((EditText)findViewById(R.id.txt_email)).setText("dbryan@uwaterloo.ca");
        ((EditText)findViewById(R.id.txt_image)).setText("bryan");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    // Helper for checking if user entered data in a specific EditText
    private int enteredData(int id, String field) {
    	if (((EditText)findViewById(id)).getText().toString().equals("")) {
    		Toast toast = Toast.makeText(
    			getApplicationContext(),
    			"Enter a " + field,
    			Toast.LENGTH_SHORT);
    		toast.show();
    		return 0;
    	}
    	return 1;
    }
    
    // Make sure that user entered stuff into all EditText
    private boolean validator() {
    	int[] ids = {
        	R.id.txt_name,
        	R.id.txt_phone,
        	R.id.txt_email,
        	R.id.txt_image
        };
        
        String[] fields = {
        	"name",
        	"phone number",
        	"email address",
        	"image link"
        };
        	
        for (int i = 0; i < ids.length; i++) {
        	if (enteredData(ids[i], fields[i]) == 0) {
        		return false;
        	}
        }
        return true;
    }
    
    // Called when user touches the other button
    public void profile(View view) {
    	// Start the profile activity and pass it the info that it needs
    	if (! validator()) {
    		return;
    	}
    	
    	Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
    	intent.putExtra("name", ((EditText)findViewById(R.id.txt_name)).getText().toString());
    	intent.putExtra("phone", ((EditText)findViewById(R.id.txt_phone)).getText().toString());
    	intent.putExtra("email", ((EditText)findViewById(R.id.txt_email)).getText().toString());
    	intent.putExtra("image", ((EditText)findViewById(R.id.txt_image)).getText().toString());
    	startActivity(intent);
    }
    
}