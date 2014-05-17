
package com.flappydoraemon;

import com.flappydoraemon.R;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

public class Background extends SpriteObject {

	public static Bitmap globalBitmap;
	
	public Background(GameView view, Game game) {
		super(view, game);
		if(globalBitmap == null){
			BitmapDrawable bd = (BitmapDrawable) game.getResources().getDrawable(R.drawable.background);
			globalBitmap = bd.getBitmap();
		}
		this.bitmap = globalBitmap;
	}

	
	@Override
	public void draw(Canvas canvas) {
		double factor = (1.0 * canvas.getHeight()) / bitmap.getHeight();
		
		if(-x > bitmap.getWidth()){
			// The first bitmap is completely out of the screen
			x += bitmap.getWidth();
		}
		
		int endBitmap = Math.min(-x + (int) (canvas.getWidth() / factor), bitmap.getWidth());
		int endCanvas = (int) ((endBitmap + x) * factor) + 1;
		src = new Rect(-x, 0, endBitmap, bitmap.getHeight());
		dst = new Rect(0, 0, endCanvas, canvas.getHeight());
		canvas.drawBitmap(this.bitmap, src, dst, null);
		
		if(endBitmap == bitmap.getWidth()){
			// draw second bitmap
			src = new Rect(0, 0, (int) (canvas.getWidth() / factor), bitmap.getHeight());
			dst = new Rect(endCanvas, 0, endCanvas + canvas.getWidth(), canvas.getHeight());
			canvas.drawBitmap(this.bitmap, src, dst, null);
		}
	}
}
