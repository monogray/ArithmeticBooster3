package com.example.arithmeticbooster3.graphics;

public class MButton{
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