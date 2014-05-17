/**
 * The SuperClass which is  controlled by the player
 * 

 */

package com.flappydoraemon;

public abstract class PlayableObject extends SpriteObject {
	
	public PlayableObject(GameView view, Game game) {
		super(view, game);
	}
	

	@Override
	public void move(){
		this.x = this.view.getWidth() / 6;
		
		if(speedY < 0){
			// The character is moving up
			speedY = speedY * 2 / 3 + getSpeedTimeDecrease() / 2;
		}else{
			// the character is moving down
			this.speedY += getSpeedTimeDecrease();
		}
		
		if(this.speedY > getMaxSpeed()){
			// speed limit
			this.speedY = getMaxSpeed();
		}
		
		super.move();
	}

	
	public void dead(){
		this.speedY = getMaxSpeed()/2;
	}
	
	/**
	 *  character flap up.
	 */
	public void onTap(){
		this.speedY = getTabSpeed();
		this.y += getPosTabIncrease();
	}
	
	/**
	 * Falling speed limit
	 * @return
	 */
	protected float getMaxSpeed(){
		// 25 @ 720x1280 px
		return view.getHeight() / 51.2f;
	}
	
	protected float getSpeedTimeDecrease(){
		// 4 @ 720x1280 px
		return view.getHeight() / 320;
	}
	

	protected float getTabSpeed(){
		// -80 @ 720x1280 px
		return - view.getHeight() / 16f;
	}
	
	
	protected int getPosTabIncrease(){
		// -12 @ 720x1280 px
		return - view.getHeight() / 100;
	}
	
	public void revive(){
		this.row = 0;
	}
}
