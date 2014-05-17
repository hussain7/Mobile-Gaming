

package com.flappydoraemon;

import com.flappydoraemon.R;

import android.graphics.Bitmap;

public class Doraemon extends PlayableObject {
	
	public static Bitmap globalBitmap;

	/** The doraemon sound */
	private static int sound = -1;

	public Doraemon(GameView view, Game game) {
		super(view, game);
		if(globalBitmap == null){
			globalBitmap = createBitmap(game.getResources().getDrawable(R.drawable.doraemon));
		}
		this.bitmap = globalBitmap;
		this.width = this.bitmap.getWidth()/(colNr = 8);	 //8 frames in a row
		this.height = this.bitmap.getHeight()/4;		     // 4 in a column
		this.frameTime = 3;		// the frame will change every 3 runs
		this.y = game.getResources().getDisplayMetrics().heightPixels / 2;
		
		if(sound == -1){
			sound = Game.soundPool.load(game, R.raw.doraemon, 1);
		}
	}
	
	private void playSound(){
		Game.soundPool.play(sound, MainActivity.volume, MainActivity.volume, 0, 0, 1);
	}

	@Override
	public void onTap(){
		super.onTap();
		playSound();
	}
	
	
	@Override
	public void move(){
		changeToNextFrame();
		super.move();
		
		// manage frames
		if(row != 3){
			// not dead
			if(speedY > getTabSpeed() / 3 && speedY < getMaxSpeed() * 1/3){
				row = 0;
			}else if(speedY > 0){
				row = 1;
			}else{
				row = 2;
			}
		}
	}

	
	@Override
	public void dead() {
		this.row = 3;
		this.frameTime = 3;
		super.dead();
	}
}
