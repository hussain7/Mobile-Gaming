/**
 * template of game object
 * 
 * 
 */

package com.flappydoraemon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public abstract class SpriteObject {

	/** The bitmaps holds the frames of drawing */
	protected Bitmap bitmap;
	
	/** Height and width of one frame */
	protected int height, width;

	protected int x, y;
	
	protected float speedX, speedY;
	
	/** The source frame  */
	protected Rect src;
	
	/** The destination area */
	protected Rect dst;
	
	/** Coordinates of frame in spritesheet */
	protected byte col, row;
	
	/** Number of columns */
	protected byte colNr = 1;
	
	protected short frameTimeCounter;
	
	protected short frameTime;
	
	protected Game game;
	
	protected GameView view;
	
	public SpriteObject(GameView view, Game game){
		this.view = view;
		this.game = game;
		frameTime = 1;
	}
	

	public void draw(Canvas canvas){
		src = new Rect(col*width, row*height, (col+1)*width, (row+1)*height);
		dst = new Rect(x, y, x+width, y+height);
		canvas.drawBitmap(bitmap, src, dst, null);
	}
	
	/**
	 * Modifies the x and y with speedX and speedY value
	 */
	public void move(){
		
		x+= speedX;
		y+= speedY;
	}
	
	
	protected void changeToNextFrame(){
		this.frameTimeCounter++;
		if(this.frameTimeCounter >= this.frameTime){
			this.col = (byte) ((this.col+1) % this.colNr);
			this.frameTimeCounter = 0;
		}
	}
	
	
	public boolean isOutOfRange(){
		return this.x + width < 0;
	}
	
	
	public boolean isColliding(SpriteObject sprite){
		if(this.x + getCollisionMargin() < sprite.x + sprite.width
				&& this.x + this.width > sprite.x + getCollisionMargin()
				&& this.y + getCollisionMargin() < sprite.y + sprite.height
				&& this.y + this.height > sprite.y + getCollisionMargin()){
			return true;
		}
		return false;
	}
	
	public boolean isCollidingRadius(SpriteObject sprite, float factor){
		int m1x = this.x+(this.width>>1);
		int m1y = this.y+(this.height>>1);
		int m2x = sprite.x+(sprite.width>>1);
		int m2y = sprite.y+(sprite.height>>1);
		int dx = m1x - m2x;
		int dy = m1y - m2y;
		int d = (int) Math.sqrt(dy*dy + dx*dx);
		
		if(d < (this.width + sprite.width) * factor
			|| d < (this.height + sprite.height) * factor){
			return true;
		}else{
			return false;
		}
	}
	

	public boolean isTouching(int x, int y){
		return (x  > this.x && x  < this.x + width
			&& y  > this.y && y < this.y + height);
	}
	
	
	public void onCollision(){
		//  subclass has to specify  itself
	}
	
	
	public boolean isTouchingEdge(){
		return isTouchingGround() || isTouchingSky();
	}
	
	
	public boolean isTouchingGround(){
		return this.y + this.height > this.view.getHeight() - this.view.getHeight() * Frontground.GROUND_HEIGHT;
	}
	
	
	public boolean isTouchingSky(){
		return this.y < 0;
	}
	
	
	public boolean isPassed(){
		return this.x + this.width < view.getPlayer().getX();
	}
	
	
	public void onPass(){
		// subclass has to specify itself
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}
	
	
	public Bitmap createBitmap(Drawable drawable){
		return createBitmap(drawable, game);
	}
	

	public static Bitmap createBitmap(Drawable drawable, Context context){
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bm = bd.getBitmap();
		return Bitmap.createScaledBitmap(bm,
				(int)(bm.getWidth() * getScaleFactor(context)),
				(int)(bm.getHeight() * getScaleFactor(context)),
				false);
	}
	
	
	public static float getScaleFactor(Context context){
		// 1.2 @ 720x1280 px
		return context.getResources().getDisplayMetrics().heightPixels / 1066f;
	}
	
	private int getCollisionMargin(){
		// 25 @ 720x1280 px
		return game.getResources().getDisplayMetrics().heightPixels / 50;
	}

}
