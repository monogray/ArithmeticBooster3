package com.example.arithmeticbooster3.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MButton{
	private Bitmap				bitmap = null;
	private Paint				paint = null;
	
	private int					x = 0;
	private int					y = 0;
	private int					w = 0;
	private int					h = 0;
	private int					xw = 0;
	private int					yh = 0;
	private int					stage = 0;
	
	public MButton() {
	}
	
	public void setParams(int _x, int _y, int _w, int _h, int _stage) {
		x = _x;
		y = _y;
		w = _w;
		h = _h;
		xw = x + w;
		yh = y + h;
		stage = _stage;
	}
	
	public void setBitmap(Paint _paint, Bitmap _btm) {
		paint = _paint;
		bitmap = _btm;
	}
	
	public void draw(Canvas canvas, int _stage) {
		if(bitmap != null && _stage == stage){
			canvas.drawBitmap(bitmap, x, y, paint);
		}
	}
	
	public boolean isClicked(int _x, int _y, int _stage) {
		if( _stage == stage && _x >= x && _x <= xw && _y >= y && _y <= yh )
			return true;
		else
			return false;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}
}