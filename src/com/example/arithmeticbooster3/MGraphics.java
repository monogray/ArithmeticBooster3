package com.example.arithmeticbooster3;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.arithmeticbooster3.game_core.EventEntity;
import com.example.arithmeticbooster3.game_core.GameCore;
import com.monogray.arithmetic_booster.R;

import android.content.*;
import android.graphics.*;
import android.util.DisplayMetrics;
import android.view.*;

public class MGraphics extends SurfaceView implements SurfaceHolder.Callback {
	//private Context				context;
	private Paint				paint = new Paint();
	
	private MThread				myThread;
	private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	 
	private SurfaceHolder		holder;
	private DisplayMetrics		metrics;
	
	private Map<String, Bitmap>	bitmaps = new HashMap<String, Bitmap>();
	  
	private GameCore			game;
	
	//private boolean				isTouch = false;
	//private int					newX = -1;
	//private int					newY = -1;
	//private int					diffX = 0;
	//private int					diffY = 0;
	
	//private EventEntity			eventsEntity;

	public MGraphics(Context _context) {
		super(_context);
		holder = getHolder();
		holder.addCallback(this);
		
		//eventsEntity = new EventEntity();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			//isTouch = true;
			//newX = (int) event.getX();
			//newY = (int) event.getY();
			EventEntity.touchProc((int) event.getX(), (int) event.getY());
		}
		return true;
	}

	@Override
	public void onDraw(Canvas canvas) {
		if(canvas != null){
			game.draw(canvas);
		}
		//if(isTouch) isTouch = false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		game = new GameCore();
		resourcesProcessing();
		game.setBitmaps(bitmaps);
		game.setup(getContext(), paint, metrics.widthPixels, metrics.heightPixels);

		myThread = new MThread(holder, this);
		// myThread.setFlag(true);
		// myThread.start();
		service.scheduleAtFixedRate(myThread, 0, (long) (1000 / 50), TimeUnit.MILLISECONDS);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		System.out.println("MGraphics.surfaceDestroyed()");
		// myThread.setFlag(false); // ?????
		onDestroy();
	}
	
	public void resourcesProcessing() {
		Bitmap _res = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
		Bitmap _img = Bitmap.createScaledBitmap(_res, metrics.widthPixels, metrics.heightPixels, true);
		bitmaps.put("bg", _img);
		
		_res = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
		_img = Bitmap.createScaledBitmap(_res, metrics.widthPixels, metrics.heightPixels, true);
		bitmaps.put("bg2", _img);
		
		_res = BitmapFactory.decodeResource(getResources(), R.drawable.bt_1);
		_img = Bitmap.createScaledBitmap(_res, 65, 65, true);
		bitmaps.put("bt_1", _img);
		
		_res = BitmapFactory.decodeResource(getResources(), R.drawable.leaf_black);
		_img = Bitmap.createScaledBitmap(_res, 70, 70, true);
		bitmaps.put("leaf_black", _img);
		
		_res = BitmapFactory.decodeResource(getResources(), R.drawable.leaf_red);
		_img = Bitmap.createScaledBitmap(_res, 70, 70, true);
		bitmaps.put("leaf_red", _img);
	}

	public void setDisplayMetrics(DisplayMetrics _metrics) {
		metrics = _metrics;
	}

	public void onDestroy() {
		System.out.println("MGraphics.onDestroy()");
		service.shutdown();
		game.onDestroy();
	}
}

class MThread extends Thread {
	//private boolean			flag;
	private SurfaceHolder	myHolder;
	private MGraphics		myDraw;
	
	//private int		_i = 0;
	
	public MThread(SurfaceHolder holder, MGraphics drawMain) {
		myHolder = holder;
		myDraw = drawMain;
	}

	public void setFlag(boolean _flag) {
		//flag = _flag;
	}

	@Override
	public void run() {
		Canvas canvas = null;
		//while (flag) {
			try {
				canvas = myHolder.lockCanvas(null);
				myDraw.onDraw(canvas);
				
				//_i++;
				//System.out.println(_i);
			}finally {
				if (canvas != null) 
					myHolder.unlockCanvasAndPost(canvas);
				else{
					setFlag(false);
				}
			}
		//}
	}
}