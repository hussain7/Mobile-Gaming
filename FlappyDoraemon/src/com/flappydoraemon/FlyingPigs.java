
package com.flappydoraemon;

import com.flappydoraemon.R;

import android.graphics.Bitmap;

public class FlyingPigs extends SpriteObject {
	
	public static Bitmap globalBitmap;

	public FlyingPigs(GameView view, Game game) {
		super(view, game);
		if(globalBitmap == null){
			globalBitmap = createBitmap(game.getResources().getDrawable(R.drawable.flying_sheep));
		}
		this.bitmap = globalBitmap;
		this.width = this.bitmap.getWidth();
		this.height = this.bitmap.getHeight();
	}
	
	/**
	 * Sets the position
	 * @param x
	 * @param y
	 */
	public void init(int x, int y){
		this.x = x;
		this.y = y;
	}

}
