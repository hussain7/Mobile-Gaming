

package com.flappydoraemon;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.app.Activity;

public class Game extends Activity{
	
	
	/***/
	public static SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
	
	public MyHandler handler;
	
	Performance performanceIndex;
	
	GameView view;

	GameDialog gameOverDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		performanceIndex = new Performance();
		view = new GameView(this);
		gameOverDialog = new GameDialog(this);
		handler = new MyHandler(this);
		setLayouts();
		
	}

	private void setLayouts(){
		LinearLayout mainLayout = new LinearLayout(this);
		mainLayout.setOrientation(LinearLayout.VERTICAL);

		mainLayout.addView(view);

		setContentView(mainLayout);
	}
	

	@Override
	protected void onResume() {
		view.resume();
		
		super.onResume();
	}
	
	
	public void gameOver(){
		handler.sendMessage(Message.obtain(handler,0));
	}
	
	
	/**
	 * when an obstacle is passed
	 */
	public void increasePoints(){
		performanceIndex.points++;
		
	}
	
	
	static class MyHandler extends Handler{
		private Game game;
		
		public MyHandler(Game game){
			this.game = game;
		}
		
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
				case 0:
					game.gameOverDialog.init();
					game.gameOverDialog.show();
					break;
				case 1:
					
			}
		}
	}

	
}
