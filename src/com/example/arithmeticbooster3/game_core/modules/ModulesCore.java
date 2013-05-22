package com.example.arithmeticbooster3.game_core.modules;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import com.example.arithmeticbooster3.game_core.DataManipulator;
import com.example.arithmeticbooster3.game_core.EventEntity;
import com.example.arithmeticbooster3.graphics.MButton;

import android.content.Context;
import android.graphics.*;

public class ModulesCore {
	protected static Random			rand = new Random();
	
	protected Context				context;
	protected Paint					paint;
	protected Map<String, Bitmap>	bitmaps = new HashMap<String, Bitmap>();
	protected Map<String, MButton>	buttons = new HashMap<String, MButton>();
	
	// Game properties
	protected int					currentStage = 0;
	
	protected DataManipulator		dh;
	
	protected int					w = 0;
	protected int					h = 0;
	protected int					wHalf = 0;
	protected int					hHalf = 0;
	
	public ModulesCore() {
	}
	
	public void setup(Context _context, Paint _paint) {
		context = _context;
		paint = _paint;
	}
	
	public void setBitmaps(Map<String, Bitmap> _bitmaps) {
		bitmaps = _bitmaps;
	}
	
	public void setParams(int _w, int _h) {
		w = _w;
		h = _h;
		wHalf = w/2;
		hHalf = h/2;
	}
	
	public void setDataBase(DataManipulator _dh) {
		dh = _dh;
	}
	
	protected String onTouchEvent() {
		String _touchedBtName = "";
		if(EventEntity.getIsTouch()){
			Iterator<Entry<String, MButton>> it = buttons.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, MButton> elem = (Map.Entry<String, MButton>) it.next();
				if(elem.getValue().isClicked(EventEntity.getTouchX(), EventEntity.getTouchY(), currentStage)){
					_touchedBtName = elem.getKey();
				}	
			}
			EventEntity.setIsTouch(false);
		}
		return _touchedBtName;
	}
}