package com.flappydoraemon;


import com.flappydoraemon.R;

import android.app.Activity;
import android.content.SharedPreferences;


public class Performance{
	
	
	public static final String SAVE_NAME = "ACCOMBLISHMENTS";
	
	public static final String KEY_POINTS = "points";
	
	int points;
	
	public void saveLocal(Activity activity){
		SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
		SharedPreferences.Editor editor = saves.edit();
		
		if(points > saves.getInt(KEY_POINTS, 0)){
			editor.putInt(KEY_POINTS, points);
		}
		
		editor.commit();
	}
	
	
	public static Performance getLocal(Activity activity){
		Performance box = new Performance();
		SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
		
		box.points = saves.getInt(KEY_POINTS, 0);
		
		return box;
	}
	
	
}