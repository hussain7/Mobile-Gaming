
package com.flappydoraemon;

import com.flappydoraemon.R;

import android.graphics.Bitmap;

public class Wood extends SpriteObject {

	
	public static Bitmap globalBitmap;

	public Wood(GameView view, Game game) {
		super(view, game);
		if(globalBitmap == null){
			globalBitmap = createBitmap(game.getResources().getDrawable(R.drawable.log_full));
		}
		this.bitmap = globalBitmap;
		this.width = this.bitmap.getWidth();
		this.height = this.bitmap.getHeight();
	}
	
	
	public void init(int x, int y){
		this.x = x;
		this.y = y;
	}
}
