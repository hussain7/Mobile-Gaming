

package com.flappydoraemon;

import android.graphics.Canvas;

public class Obstacle extends SpriteObject{
	private FlyingPigs pigs;
	private Wood log;
	
	/**  onPass method called once */
	public boolean isAlreadyPassed = false;

	public Obstacle(GameView view, Game game) {
		super(view, game);
		pigs = new FlyingPigs(view, game);
		log = new Wood(view, game);
		
		initPos();
	}
	
	/**
	 *.
	 * The vertical position is  random.
	 */
	private void initPos(){
		int height = game.getResources().getDisplayMetrics().heightPixels;
		int gab = height / 4 - view.getSpeedX();
		if(gab < height / 5){
			gab = height / 5;
		}
		int random = (int) (Math.random() * height * 2 / 5);
		int y1 = (height / 10) + random - pigs.height;
		int y2 = (height / 10) + random + gab;
		
		pigs.init(game.getResources().getDisplayMetrics().widthPixels, y1);
		log.init(game.getResources().getDisplayMetrics().widthPixels, y2);
	}

	
	@Override
	public void draw(Canvas canvas) {
		pigs.draw(canvas);
		log.draw(canvas);
	}

	
	@Override
	public boolean isOutOfRange() {
		return pigs.isOutOfRange() && log.isOutOfRange();
	}

	
	@Override
	public boolean isColliding(SpriteObject sprite) {
		return pigs.isColliding(sprite) || log.isColliding(sprite);
	}

	
	@Override
	public void move() {
		pigs.move();
		log.move();
	}

	
	@Override
	public void setSpeedX(float speedX) {
		pigs.setSpeedX(speedX);
		log.setSpeedX(speedX);
	}
	
	
	@Override
	public boolean isPassed(){
		return pigs.isPassed() && log.isPassed();
	}
	
	
	@Override
	public void onPass(){
		if(!isAlreadyPassed){
			isAlreadyPassed = true;
			view.getGame().increasePoints();
		}
	}

}
