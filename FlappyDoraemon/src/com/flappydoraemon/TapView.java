
package com.flappydoraemon;

import com.flappydoraemon.R;

import android.graphics.Bitmap;

public class TapView extends SpriteObject {
	public static Bitmap globalBitmap;

	public TapView(GameView view, Game game) {
		super(view, game);
		if(globalBitmap == null){
			globalBitmap = createBitmap(game.getResources().getDrawable(R.drawable.tutorial));
		}
		this.bitmap = globalBitmap;
		this.width = this.bitmap.getWidth();
		this.height = this.bitmap.getHeight();
	}

	
	@Override
	public void move() {
		this.x = view.getWidth() / 2 - this.width / 2;
		this.y = view.getHeight() / 2 - this.height / 2;
	}

}
