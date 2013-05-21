package com.example.arithmeticbooster3.game_core;

import java.util.*;

import com.example.arithmeticbooster3.game_core.modules.ArithmeticGame;

import android.content.Context;
import android.graphics.*;

public class GameCore{
	private Context				context;
	private Paint				paint;
	private Map<String, Bitmap>	bitmaps = new HashMap<String, Bitmap>();
	//private Map<String, MButton>buttons = new HashMap<String, MButton>();
	
	private DataManipulator		dh; 
	
	private int					w = 0;
	private int					h = 0;
	
	// Touch properties
/*	private boolean				isTouch = false;
	private int					touchX = -1;
	private int					touchY = -1;
	private int					diffTouchX = 0;
	private int					diffTouchY = 0;*/
	
	// Games
	private int					currentGame = 0;
	private ArithmeticGame		arithmeticGame;
	
	public GameCore() {
	}
	
	public void setup(Context _context, Paint _paint, int _w, int _h) {
		context = _context;
		paint = _paint;
		w = _w;
		h = _h;
		
		setupDataBase();
		
		arithmeticGame = new ArithmeticGame();
		arithmeticGame.setParams(w, h);
		arithmeticGame.setBitmaps(bitmaps);
		arithmeticGame.setup(context, paint);
	}
	
	public void setBitmaps(Map<String, Bitmap> _bitmaps) {
		bitmaps = _bitmaps;
	}
	
	private void setupDataBase() {
		dh = new DataManipulator(context);
	}

	public void draw(Canvas canvas){//, boolean _isTouch, int _newX, int _newY) {
		//touchProc(_isTouch, _newX, _newY);
		//touchProcessing( onTouchEvent() );
		
		drawGraphic(canvas);
	}
	
	//private void touchProc(boolean _isTouch, int _newX, int _newY) {
		/*if(_newX != touchX && _newY != touchY && touchX != -1)
			isTouch = true;
		touchX = _newX;
		touchY = _newY;
		diffTouchX = touchX - _newX;
		diffTouchY = touchY - _newY;*/
	//}
	
	//private void touchProcessing(String _btName) {
	//}
	
	/*private String onTouchEvent() {
		String _touchedBtName = "";
		if(isTouch){
			Iterator<Entry<String, MButton>> it = buttons.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, MButton> elem = (Map.Entry<String, MButton>) it.next();
				if(elem.getValue().isClicked(touchX, touchY, currentStage)){
					_touchedBtName = elem.getKey();
				}	
			}
			isTouch = false;
		}
		return _touchedBtName;
	}*/

	private void drawGraphic(Canvas canvas) {
		if(currentGame == 0)
			drawGame_0(canvas);
		else if(currentGame == 1)
			drawGame_1(canvas);
	}
	
	private void drawGame_0(Canvas canvas) {
		arithmeticGame.draw(canvas);
	}
	
	private void drawGame_1(Canvas canvas) {
	}
	
	public void onDestroy() {
		System.out.println("GameCore.onDestroy()");
		DataManipulator.getDb().close();
	}
}