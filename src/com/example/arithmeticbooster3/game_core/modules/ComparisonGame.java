package com.example.arithmeticbooster3.game_core.modules;

import java.util.Map.Entry;
import java.util.Random;

import com.example.arithmeticbooster3.graphics.MButton;
import com.example.arithmeticbooster3.libs.math.MMath;

import android.content.Context;
import android.graphics.*;

public class ComparisonGame extends ModulesCore{
	private ComparisonPool		comparisonPool;
	
	private boolean[] 			answers = new boolean[500];
	private int 				answersInd = 0;
	
	private boolean				isTrueAnswer = false;
	
	public ComparisonGame() {
	}
	
	@Override
	public void setup(Context _context, Paint _paint) {
		super.setup(_context, _paint);
		
		comparisonPool = new ComparisonPool();
		comparisonPool.setup(paint, w, h);
		
		setupButtons();
		
		//!!!!!!!!!!!!!!!!!
		currentStage = 1;
		startGame();
	}
	
	private void setupButtons() {
		buttons.put("bt_start", new MButton());
		buttons.get("bt_start").setParams(w/3, h/2-70, w/3, 90, 0);
		
		buttons.put("bt_1", new MButton());
		buttons.get("bt_1").setParams((w-260)/5, h - 72, 65, 65, 1);
		buttons.get("bt_1").setBitmap(paint, bitmaps.get("bt_1"));
		
		buttons.put("bt_2", new MButton());
		buttons.get("bt_2").setParams((w-260)/5 * 2 + 65, h - 72, 65, 65, 1);
		buttons.get("bt_2").setBitmap(paint, bitmaps.get("bt_1"));
	}
	
	public void draw(Canvas canvas) {
		touchProcessing( onTouchEvent() );
		drawGraphic(canvas);
	}
	
	private void touchProcessing(String _btName) {
		if(!_btName.equals("")){
			boolean _isNext = false;
			boolean _isTrueAnswer = false;
			if(_btName.equals("bt_start")){
				startGame();
			}else if(_btName.equals("bt_1")){
				_isNext = true;
				if(answersInd > 0 && !answers[answersInd-1])
					_isTrueAnswer = true;
			}else if(_btName.equals("bt_2")){
				_isNext = true;
				if(answersInd > 0 && answers[answersInd-1])
					_isTrueAnswer = true;
			}
			
			isTrueAnswer = _isTrueAnswer;
			
			if(_isNext) {
				answersInd++;
				if(answersInd >= answers.length)
					answersInd = 0;
				if(answers[answersInd]){
					comparisonPool.moveOn( comparisonPool.getLastType() );
				}else{
					comparisonPool.moveOn( comparisonPool.getNotLastType() );
				}
			}
		}
	}
	
	private void drawGraphic(Canvas canvas) {
		if(currentStage == 0)
			drawGraphic_Stage_0(canvas);
		else if(currentStage == 1)
			drawGraphic_Stage_1(canvas);
		
		for (Entry<String, MButton> entry: buttons.entrySet()){
			entry.getValue().draw(canvas, currentStage);
		}
		
		if(isTrueAnswer){
			paint.setColor(Color.CYAN);
			RectF _r = new RectF();
			_r.left = 10;
			_r.top = 10;
			_r.right = 50;
			_r.bottom = 50;
			canvas.drawRoundRect(_r, 8, 8, paint);
		}else{
			paint.setColor(Color.WHITE);
			RectF _r = new RectF();
			_r.left = 10;
			_r.top = 10;
			_r.right = 50;
			_r.bottom = 50;
			canvas.drawRoundRect(_r, 8, 8, paint);
		}
	}
	
	private void drawGraphic_Stage_0(Canvas canvas) {	
	}
	
	private void drawGraphic_Stage_1(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		comparisonPool.update(canvas);
	}
	
	private void startGame() {
		for (int i = 0; i < answers.length; i++) {
			answers[i] = rand.nextBoolean();
		}
		answersInd = 0;
	}
}

class ComparisonPool {
	protected static Random	rand = new Random();
	private Paint			paint;
	
	private int				w = 0;
	private int				h = 0;
	
	private ComparisonEntity[] compEntities = new ComparisonEntity[3];
	private int[] 			xPos = {0, 0, 0};

	public ComparisonPool() {
	}
	
	public void setup(Paint _paint, int _w, int _h) {
		paint = _paint;
		w = _w;
		h = _h;
		
		for (int i = 0; i < compEntities.length; i++) {
			compEntities[i] = new ComparisonEntity(paint, w, h);
		}
		xPos[0] = -w;
		xPos[1] = 0;
		xPos[2] = w;
	}

	public void update(Canvas canvas) {
		for (int i = 0; i < compEntities.length; i++) {
			compEntities[i].update(canvas, xPos[i]);
		}
	}
	
	public void moveOn(int _type) {
		compEntities[0] = compEntities[1];
		compEntities[1] = compEntities[2];
		compEntities[2] = new ComparisonEntity(paint, w, h);
		compEntities[2].setType(_type);
	}
	
	public int getLastType() {
		return compEntities[2].getType();
	}
	
	public int getNotLastType() {
		int _res = getLastType();
		while(_res == getLastType()){
			_res = rand.nextInt(4);
		}
		return _res;
	}
}

class ComparisonEntity {
	private Paint		paint;
	private int			w = 0;
	private int			h = 0;
	private int			wHalf = 0;
	private int			hHalf = 0;
	
	private int			type = 0;
	private int			typeMax = 3;
	
	private float		curX = 0;

	public ComparisonEntity(Paint _paint, int _w, int _h) {
		paint = _paint;
		w = _w;
		h = _h;
		wHalf = w/2;
		hHalf = h/2;
		
		curX = w;
	}

	public void update(Canvas canvas, int _x) {
		curX = MMath.getInertValue_flow(curX, _x, (float)0.2);
		if(type == 0)
			draw_type_1(canvas, (int)curX);
		else if(type == 1)
			draw_type_2(canvas, (int)curX);
		else if(type == 2)
			draw_type_3(canvas, (int)curX);
		else if(type == 3)
			draw_type_4(canvas, (int)curX);
	}

	private void draw_type_1(Canvas canvas, int _x) {
		drawRect(canvas, _x, wHalf, Color.WHITE);
		drawRect(canvas, _x, (int)(wHalf*0.8), Color.RED);
		drawRect(canvas, _x, (int)(wHalf*0.4), Color.BLACK);
	}
	
	private void draw_type_2(Canvas canvas, int _x) {
		drawRect(canvas, _x, wHalf, Color.WHITE);
		drawRect(canvas, _x, (int)(wHalf*0.8), Color.GREEN);
		drawRect(canvas, _x, (int)(wHalf*0.4), Color.BLACK);
	}
	
	private void draw_type_3(Canvas canvas, int _x) {
		drawRect(canvas, _x, wHalf, Color.WHITE);
		drawRect(canvas, _x, (int)(wHalf*0.8), Color.BLUE);
		drawRect(canvas, _x, (int)(wHalf*0.4), Color.BLACK);
	}
	
	private void draw_type_4(Canvas canvas, int _x) {
		drawRect(canvas, _x, wHalf, Color.WHITE);
		drawRect(canvas, _x, (int)(wHalf*0.8), Color.CYAN);
		drawRect(canvas, _x, (int)(wHalf*0.4), Color.BLACK);
	}
	
	private void drawRect(Canvas canvas, int _x, int _w, int _color) {
		paint.setColor(_color);
		RectF _r = new RectF();
		_r.left = wHalf - _w/2 + _x;
		_r.top = hHalf - _w/2;
		_r.right = _r.left + _w;
		_r.bottom = _r.top + _w;
		canvas.drawRoundRect(_r, 8, 8, paint);
	}
	
	public void setType(int _type) {
		if(_type > typeMax)
			_type = typeMax;
		type = _type;
	}
	
	public int getType() {
		return type;
	}
}