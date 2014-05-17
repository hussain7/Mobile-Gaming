

package com.flappydoraemon;

import com.flappydoraemon.R;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class Frontground extends Background {
	/**
	 * ground height relative to height of the bitmap
	 */
	public static final float GROUND_HEIGHT = (1f * /*45*/ 35) / 720;

	public static Bitmap globalBitmap;
	
	public Frontground(GameView view, Game game) {
		super(view, game);
		if(globalBitmap == null){
			BitmapDrawable bd = (BitmapDrawable) game.getResources().getDrawable(R.drawable.frontground);
			globalBitmap = bd.getBitmap();
		}
		this.bitmap = globalBitmap;
	}
	
}
