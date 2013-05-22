package com.example.arithmeticbooster3.game_core.modules;

import java.util.*;
import java.util.Map.Entry;

import com.example.arithmeticbooster3.game_core.DataManipulator;
import com.example.arithmeticbooster3.game_core.EventEntity;
import com.example.arithmeticbooster3.graphics.MButton;
import com.example.arithmeticbooster3.libs.math.MMath;

import android.content.Context;
import android.graphics.*;

public class ArithmeticGame extends ModulesCore{
	// Game properties
	private int					currentLevel = 3;
	private float				borderLineY = 300;
	private int					borderLineStep = 20;
	private int					points = 0;
	private int					bestPoints = 0;
	private int					pointsPlus = 10;
	private int					pointsMinus = -5;
	
	// Game entities
	private ArithmeticBooble[]	boobles = new ArithmeticBooble[4];
	private ArithmeticBooble	curBooble;
	
	public ArithmeticGame() {
	}
	
	@Override
	public void setup(Context _context, Paint _paint) {
		super.setup(_context, _paint);
		setupBoobles();
		setupButtons();
	}
	
	@Override
	public void setParams(int _w, int _h) {
		super.setParams(_w, _h);
		borderLineStep = h / 24;
	}
	
	private void setupBoobles() {
		for (int i = 0; i < boobles.length; i++) {
			boobles[i] = new ArithmeticBooble();
			boobles[i].setup(paint, w, h);
			boobles[i].setBitmaps(bitmaps);
		}
		curBooble = boobles[0];
		curBooble.setActivity(true);
	}
	
	private void setupButtons() {
		buttons.put("bt_start", new MButton());
		buttons.get("bt_start").setParams(w/3, h/2-70, w/3, 90, 0);
		
		buttons.put("bt_1", new MButton());
		buttons.get("bt_1").setParams((w-260)/5, h - 72, 65, 65, 1);
		
		buttons.put("bt_2", new MButton());
		buttons.get("bt_2").setParams((w-260)/5 * 2 + 65, h - 72, 65, 65, 1);
		
		buttons.put("bt_3", new MButton());
		buttons.get("bt_3").setParams((w-260)/5 * 3 + 65*2, h - 72, 65, 65, 1);
		
		buttons.put("bt_4", new MButton());
		buttons.get("bt_4").setParams((w-260)/5 * 4 + 65*3, h - 72, 65, 65, 1);
	}

	public void draw(Canvas canvas) {
		touchProcessing( onTouchEvent() );
		drawGraphic(canvas);
	}
	
	private void touchProcessing(String _btName) {
		if(!_btName.equals("")){
			boolean _isWrongAnswer = false;
			if(_btName.equals("bt_start")){
				startGame();
			}else if(_btName.equals("bt_1")){
				if( !curBooble.checkAnswer(0) )
					_isWrongAnswer = true;
			}else if(_btName.equals("bt_2")){
				if( !curBooble.checkAnswer(1) )
					_isWrongAnswer = true;
			}if(_btName.equals("bt_3")){
				if( !curBooble.checkAnswer(2) )
					_isWrongAnswer = true;
			}if(_btName.equals("bt_4")){
				if( !curBooble.checkAnswer(3) )
					_isWrongAnswer = true;
			}
			
			if(_isWrongAnswer) {
				if( !curBooble.checkAnswer(3) ){
					points += pointsMinus;
					borderLineY -= borderLineStep/2;
				}
			}
		}
	}
	
	private void drawGraphic(Canvas canvas) {
		if(currentStage == 0)
			drawGraphic_Stage_0(canvas);
		else if(currentStage == 1)
			drawGraphic_Stage_1(canvas);
	}
	
	private void drawGraphic_Stage_0(Canvas canvas) {
		//canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(bitmaps.get("bg2"), 0, 0, paint);
		paint.setColor(Color.WHITE);
		
		paint.setTextSize(23);
		canvas.drawText("Play", w/2 - 20, h/2 - 15, paint);
		if(points != 0){
			paint.setTextSize(23);
			canvas.drawText("Score: " + points, w/2 - 54, h/2 + 20, paint);
			paint.setTextSize(20);
		    canvas.drawText("Best score: " + bestPoints, w/2 - 68, h/2 + 44, paint);
		}
	}
	
	private void drawGraphic_Stage_1(Canvas canvas) {
		canvas.drawBitmap(bitmaps.get("bg"), 0, 0, paint);
		
		// Draw buttons
		for (int i = 1; i <= 4; i++) {
			canvas.drawBitmap(bitmaps.get("bt_1"), buttons.get("bt_"+i).getX(), buttons.get("bt_"+i).getY(), paint);
		}
		
		// Draw answers
		paint.setColor(Color.WHITE);
		paint.setTextSize(23);
		int _offsetX = 20;
		int _offsetY = 42;
		for (int i = 0; i < 4; i++) {
			canvas.drawText(curBooble.answersStr[i], buttons.get("bt_"+(i+1)).getX()+_offsetX, buttons.get("bt_"+(i+1)).getY()+_offsetY, paint);
		}
		
		for (ArithmeticBooble elem : boobles) {
			if(!elem.equals(curBooble))
				elem.update(canvas, currentLevel, borderLineY);
		}
		curBooble.update(canvas, currentLevel, borderLineY);	// Need cause current booble must be on top
		
		// Draw points
		paint.setTextSize(20);
		canvas.drawText("Score: " + points, 10, 23, paint);
		paint.setTextSize(17);
		canvas.drawText("Speed: " + MMath.round(curBooble.getSpeed(), 2), 10, 44, paint);
		
		String _infoState = curBooble.getInfoState();
		if(_infoState.equals("DIE")){
			borderLineY -= borderLineStep;
			points += pointsMinus;
			if(borderLineY < 20)
				endGame();
		}else if(_infoState.equals("CORRECT")){
			points += pointsPlus;
		}
		
		for (ArithmeticBooble elem : boobles) {
			if(elem.getState().equals("WAIT_CREATION")){
				elem.create(currentLevel);
				break;
			}
		}
		
		float _max = -10000;
		int _minId = 0;
		for (int i = 0; i < boobles.length; i++) {
			if( boobles[i].y > _max && boobles[i].getState().equals("ACTIVE") ){
				_max = boobles[i].y;
				_minId = i;
			}
			boobles[i].setActivity(false);
		}
		boobles[_minId].setActivity(true);
		if(!curBooble.equals(boobles[_minId]))
			curBooble = boobles[_minId];
		
		if(borderLineY < h - 85)
			borderLineY += 0.01;
		
		// Increase speed
		for (int i = 0; i < boobles.length; i++) {
			boobles[i].setSpeed(boobles[i].getSpeed() + (float)0.00002);
		}

		paint.setColor(Color.RED);
		canvas.drawLine(0, borderLineY, w, borderLineY, paint);
		canvas.drawLine(0, borderLineY + 1, w, borderLineY + 1, paint);
		canvas.drawLine(0, borderLineY + 2, w, borderLineY + 2, paint);
	}
	
	private void startGame() {
		points = 0;
		currentStage = 1;
		curBooble.create(currentLevel);
		borderLineY = h - 85;
		
		// Set start speed 
		for (int i = 0; i < boobles.length; i++) {
			boobles[i].setSpeed((float) (h * 0.0004)); // 0.0004
		}
	}
	
	private void endGame() {
		currentStage = 0;
		
		List<String[]> names2 = null;
		names2 = dh.selectAll();
		
		String[] stg1;
		stg1 = new String[names2.size()]; 
	    int x = 0;
	    String stg = "0";
	    for (String[] name : names2) {
	        stg = name[2];
	        stg1[x] = stg;
	        x++;
	    }
	    
	    bestPoints = Integer.parseInt(stg);
	    if(points > bestPoints){
	    	bestPoints = points;
	    	dh.insert("user", String.valueOf(points));
	    }
	}
	
	public void onDestroy() {
		System.out.println("GameCore.onDestroy()");
		DataManipulator.getDb().close();
	}
}

class ArithmeticBooble {
	private Map<String, Bitmap>	bitmaps = new HashMap<String, Bitmap>();
	private Paint		paint;
	private Random		rand = new Random();
	
	private int			w = 0;
	private int			h = 0;

	private float		x = 0;
	public float		y = 0;
	public float		destX = rand.nextInt(260) + 20;
	private float		speedX = (float) 0.0015;
	
	private float		speed = (float) 0.8;

	private String		state = "DIE";
	private String		infoState = "";
	
	private boolean		isActive = false;

	private String[]	operators = new String[] { "+", "-", "×", "/" };
	private int			operator;
	private int			operand_1;
	private int			operand_2;

	private String		expression = "";
	private int			answer = 0;
	public int			answerPosition = 0;
	public int[]		answers = new int[] { 0, 0, 0, 0 };
	public String[]		answersStr = new String[] { "", "", "", "" };

	private int			currentLevel = 0;
	
	private int			reBirthCounterMax = 100;
	private int			reBirthCounter = reBirthCounterMax;

	public ArithmeticBooble() {
	}

	public void setup(Paint _paint, int _w, int _h) {
		paint = _paint;
		w = _w;
		h = _h;
	}
	
	public void setBitmaps(Map<String, Bitmap> _bitmaps) {
		bitmaps = _bitmaps;
	}

	public void update(Canvas canvas, int _currentLevel, float _borderLine) {
		if (state.equals("ACTIVE")) {
			y += speed;
			
			float diffX = (destX - x) * speedX;
			x += diffX;
			if( Math.abs(x - destX) < 1 ){
				destX = rand.nextInt(w-50) + 20;
			}
			
			if (y > _borderLine) 
				setState("DIE");
			draw(canvas);
		}else if (state.equals("DIE") || state.equals("CORRECT")) {
			reBirthCounter--;
			if(reBirthCounter < 0){
				setState("WAIT_CREATION");
			}
		}
	}

	private void draw(Canvas canvas) {
		if(isActive)
			canvas.drawBitmap(bitmaps.get("leaf_red"), x-15, y-40, paint);
		else
			canvas.drawBitmap(bitmaps.get("leaf_black"), x-15, y-40, paint);
		paint.setColor(Color.WHITE);
		paint.setTextSize(14);
		canvas.drawText(expression, x, y, paint);
	}

	public boolean checkAnswer(int _answerPos) {
		if (answerPosition == _answerPos) {
			setState("CORRECT");
			return true;
		}
		return false;
	}

	public void create(int _currentLevel) {
		currentLevel = _currentLevel;
		
		operator = getRandomOperator();
		setOperands();
		answer = getAnswer();
		answerPosition = rand.nextInt(4);
		
		for (int i = 0; i < answers.length; i++) {
			answers[i] = rand.nextInt(answer + 22) + 3;
			while(answers[i] == answer)
				answers[i] = rand.nextInt(answer + 22) + 3;
			answersStr[i] = String.valueOf(answers[i]);
		}
		answers[answerPosition] = answer;
		answersStr[answerPosition] = String.valueOf(answer);
		
		String _operand_1 = String.valueOf(operand_1);
		if(operand_1 < 10)
			_operand_1 = " "+_operand_1;
		String _operand_2 = String.valueOf(operand_2);
		if(operand_2 < 10)
			_operand_2 = " "+_operand_2;
		expression = _operand_1 + " " + operators[operator] + " " + _operand_2;

		x = rand.nextInt(w-30) + 20;
		destX = rand.nextInt(w-50) + 20;
		y = -(rand.nextInt(80) + 20);
		setState("ACTIVE");
	}

	private int getRandomOperator() {
		if (currentLevel == 0)
			return 0;
		else if (currentLevel == 1)
			if(rand.nextBoolean())
				return 1;
			else
				return 0;
		else if (currentLevel == 2)
			if(rand.nextBoolean())
				return 1;
			else
				if(rand.nextBoolean())
					return 0;
				else
					return 2;
		else if (currentLevel == 3)
			if(rand.nextBoolean())
				if(rand.nextBoolean())
					return 0;
				else
					return 1;
			else
				if(rand.nextBoolean())
					return 2;
				else
					return 3;
		return 0;
	}

	public void setOperands() {
		if (operator == 0 || operator == 1){		// '+' or '-'
			operand_1 = rand.nextInt(25) + 2;
			operand_2 = operand_1 - rand.nextInt(operand_1);
			while(operand_2 == operand_1)
				operand_2 = operand_1 - rand.nextInt(operand_1);
		}
		else if (operator == 2){					// '*'
			operand_1 = rand.nextInt(11) + 2;
			operand_2 = rand.nextInt(11) + 2;
		}
		else if (operator == 3){					// '÷'
			operand_1 = rand.nextInt(11) + 2;
			operand_2 = rand.nextInt(11) + 2;
		}
	}

	private int getAnswer() {
		if (operator == 0)
			return operand_1 + operand_2;
		else if (operator == 1)
			return operand_1 - operand_2;
		else if (operator == 2)
			return operand_1 * operand_2;
		else if (operator == 3){
			int _answer = operand_1;
			operand_1 = operand_1 * operand_2;
			return _answer;
		}
		return 0;
	}
	
	public void setActivity(boolean _activity) {
		isActive = _activity;
	}

	private void setState(String _state) {
		state = _state;
		infoState = state;
		if (state.equals("DIE")) {
			reBirthCounter = rand.nextInt(reBirthCounterMax) + 15;
		}else if (state.equals("CORRECT")) {
			reBirthCounter = rand.nextInt(reBirthCounterMax) + 15;
		}
	}
	
	public String getState() {
		return state;
	}
	
	public String getInfoState() {
		String _infoState = infoState;
		infoState = "";
		return _infoState;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float _speed) {
		speed = _speed;
	}
}