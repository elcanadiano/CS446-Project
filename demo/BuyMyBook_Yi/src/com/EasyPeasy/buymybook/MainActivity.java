package com.EasyPeasy.buymybook;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	final Context contenxt = this;
	
	//UI instance variables
	private ImageButton scanBtn;
	private TextView greeting, instruction;

	//local db stuff
	private DBHelper dbHelper;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//instantiate UI items
		scanBtn = (ImageButton)findViewById(R.id.scan_button);
		greeting=(TextView)findViewById(R.id.greeting);
		instruction=(TextView)findViewById(R.id.instruction);
		
		scanBtn.setOnClickListener(this);
		scanBtn.setBackgroundResource(R.drawable.scan_button);
		//set up greeting message (static right now)
		this.setupGreeting();
		//set up instructions
		this.setupInstruction();
		//set up local db
		dbHelper = new DBHelper(this);
		
		if(true){
			dbHelper.clearAll();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.scan_button:
			System.out.println("i should scan something....");
			scanBtn.setBackgroundResource(R.drawable.scan_button_contact);
			break;
		}
		
	}
	
	
	/// helpers for set up
	private void setupGreeting() {
		
		Drawable booker = this.getResources().getDrawable(R.drawable.ic_booker_default);
		booker.setBounds(0, 0, booker.getIntrinsicWidth(), booker.getIntrinsicHeight()); 
		ImageSpan b = new ImageSpan(booker);
		
		SpannableStringBuilder builder = new SpannableStringBuilder();
		builder.append(" ");
		builder.append(this.getText(R.string.main_greeting));
		builder.setSpan(b, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		greeting.setText(builder);
	}
	private void setupInstruction() {
		SpannableStringBuilder builder = new SpannableStringBuilder();
		
		Drawable camera = this.getResources().getDrawable(R.drawable.ic_action_camera);
		camera.setBounds(0, 0, camera.getIntrinsicWidth(), camera.getIntrinsicHeight()); // <---- Very important otherwise your image won't appear
		ImageSpan c = new ImageSpan(camera);
		Drawable swipe = this.getResources().getDrawable(R.drawable.ic_swipe_menu);
		swipe.setBounds(0, 0, swipe.getIntrinsicWidth(), swipe.getIntrinsicHeight());
		ImageSpan s = new ImageSpan(swipe);
		Drawable look = this.getResources().getDrawable(R.drawable.ic_action_search);
		look.setBounds(0, 0, look.getIntrinsicWidth(), look.getIntrinsicHeight());
		ImageSpan l = new ImageSpan(look);
		
		builder.append(" ");
		builder.append(this.getText(R.string.main_inst_sell));
		int lengthOfPart1 = builder.length();
		builder.append(" ");
		builder.append(this.getText(R.string.main_inst_swipe));
		int lengthOfPart2 = builder.length();
		builder.append(" ");
		builder.append(this.getText(R.string.main_inst_look));
		
		builder.setSpan(c, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		builder.setSpan(s,  lengthOfPart1,  lengthOfPart1+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		builder.setSpan(l, lengthOfPart2, lengthOfPart2+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		builder.setSpan(new ForegroundColorSpan(Color.parseColor("#16a085")), 4, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		builder.setSpan(new ForegroundColorSpan(Color.parseColor("#e67e22")), 18, 23, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		builder.setSpan(new ForegroundColorSpan(Color.parseColor("#8e44ad")), 49, 53, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		instruction.setText(builder);
	}

}
