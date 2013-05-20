package com.example.arithmeticbooster3;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.monogray.arithmetic_booster.R;

public class MainActivity extends Activity {
	private MGraphics mDraw;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		// setContentView(new MGraphics(this));
		
		// Hide Title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Go fullscreen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		LinearLayout lLayout = new LinearLayout( getApplicationContext() );
		mDraw = new MGraphics( getApplicationContext() );
		lLayout.addView(mDraw);
		
		// Get metrics for getting width and height of screen
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mDraw.setDisplayMetrics(metrics);
		
		setContentView(lLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onDestroy() {
		System.out.println("MainActivity.onDestroy()");
		mDraw.onDestroy();
		super.onDestroy();
	}
}