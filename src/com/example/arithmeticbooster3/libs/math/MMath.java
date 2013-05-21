package com.example.arithmeticbooster3.libs.math;

public class MMath {
	
	public MMath(){
	}

	public static float getInertValue(float _val1, float _val2, float _step){
		return (_val1 * _step + _val2) / (_step + 1);
	}
	
	public static float getInertValue_flow(float _val1, float _val2, float _step){
		float __div = _val1 - _val2;
		return _val1 - (__div * _step);
	}
	
	public static float round(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
    }
}