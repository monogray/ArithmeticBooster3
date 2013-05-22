package com.example.arithmeticbooster3.game_core;

public class EventEntity {
	// Screen properties
	private static int			w = 0;
	private static int			h = 0;
	protected static int		wHalf = 0;
	protected static int		hHalf = 0;
	
	// Touch properties
	private static boolean		isTouch = false;
	private static int			touchX = -1;
	private static int			touchY = -1;
	private static int			diffTouchX = 0;
	private static int			diffTouchY = 0;
	
	public EventEntity() {
	}
	
	public static void setup(int _w, int _h) {
		w = _w;
		h = _h;
		wHalf = w/2;
		hHalf = h/2;
	}
	
	public static void touchProc(boolean _isTouch, int _newX, int _newY) {
		//if( (_newX != touchX || _newY != touchY) && touchX != -1 )
			//isTouch = true;
		isTouch = _isTouch;
		touchX = _newX;
		touchY = _newY;
		diffTouchX = touchX - _newX;
		diffTouchY = touchY - _newY;
	}
	
	public static boolean getIsTouch() {
		return isTouch;
	}
	
	public static int getTouchX() {
		return touchX;
	}
	
	public static int getTouchY() {
		return touchY;
	}
	
	public static int getDiffTouchX() {
		return diffTouchX;
	}
	
	public static int getDiffTouchY() {
		return diffTouchY;
	}
	
	public static void setIsTouch(boolean _val) {
		isTouch = _val;
	}
}