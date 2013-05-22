package com.example.arithmeticbooster3.game_core;

import java.util.*;

import com.example.arithmeticbooster3.game_core.modules.ArithmeticGame;
import com.example.arithmeticbooster3.game_core.modules.ComparisonGame;

import android.content.Context;
import android.graphics.*;

public class GameCore{
	private Context				context;
	private Paint				paint;
	private Map<String, Bitmap>	bitmaps = new HashMap<String, Bitmap>();
	
	private DataManipulator		dh; 
	
	private int					w = 0;
	private int					h = 0;
	
	// Games
	private int					currentGame = 1;
	private ArithmeticGame		arithmeticGame;
	private ComparisonGame		comparisonGame;
	
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
		arithmeticGame.setDataBase(dh);
		
		comparisonGame = new ComparisonGame();
		comparisonGame.setParams(w, h);
		comparisonGame.setBitmaps(bitmaps);
		comparisonGame.setup(context, paint);
		comparisonGame.setDataBase(dh);
	}
	
	public void setBitmaps(Map<String, Bitmap> _bitmaps) {
		bitmaps = _bitmaps;
	}
	
	private void setupDataBase() {
		dh = new DataManipulator(context);
	}

	public void draw(Canvas canvas){
		drawGraphic(canvas);
	}

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
		comparisonGame.draw(canvas);
	}
	
	public void onDestroy() {
		System.out.println("GameCore.onDestroy()");
		DataManipulator.getDb().close();
	}
}