

package com.flappydoraemon;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameView extends SurfaceView implements Runnable, OnTouchListener{
	
	/** time thread sleeps after drawing */
	public static final long UPDATE_INTERVAL = 30;

	/** The thread moves and  draws */
	private Thread thread;
	
	private SurfaceHolder holder;
	
	volatile private boolean threadRun = true;
	
	/** Whether tapping help was  shown */
	private boolean showedTapView = false;
	
	private Game game;
	private PlayableObject player;
	private Background bg;
	private Frontground fg;
	private List<Obstacle> obstacles = new ArrayList<Obstacle>();

	
	
	private TapView tapview;
	private boolean tapViewIsShown = false;;

	public GameView(Context context) {
		super(context);
		this.game = (Game) context;

		holder = getHolder();
		player = new Doraemon(this, game);
		bg = new Background(this, game);
		fg = new Frontground(this, game);
		
		tapview = new TapView(this, game);
		
		setOnTouchListener(this);
	}
	
	/**
	 * Manages touchevents
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(tapViewIsShown){
				tapViewIsShown = false;
				resumeAndKeepRunning();
				this.player.onTap();
			}else if(!threadRun){
				// Start game if it's paused
				resumeAndKeepRunning();
			}else{
				
					this.player.onTap();
				
			}
		}
		
		return true;
	}
	
	/**
	 * The thread runs this method
	 */
	public void run() {
		draw();		//draw at least once
		
		while(threadRun || !showedTapView){
			if(!showedTapView){
				showTapView();
			}else{
				// check
				checkPasses();
				checkOutOfRange();
				checkCollision();
				createObstacle();
				move();
	
				draw();
				try {
					Thread.sleep(UPDATE_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void showTapView(){
		showedTapView = true;
		tapViewIsShown = true;
		
		player.move();
		
		
		while(!holder.getSurface().isValid()){/*wait*/}
		
		Canvas canvas = holder.lockCanvas();
		drawCanvas(canvas);
		drawTapView(canvas);
		holder.unlockCanvasAndPost(canvas);
	}
	
	
	public void pause(){
		threadRun = false;
		while(thread != null){
			try {
				thread.join();
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		thread = null;
	}
	
	
	public void resume(){
		pause();	// make sure the old thread isn't running
		thread = new Thread(this);
		thread.start();
	}
	
	
	public void resumeAndKeepRunning(){
		pause();	// make sure the old thread isn't running
		threadRun = true;
		thread = new Thread(this);
		thread.start();
	}
	
	
	private void draw() {
		while(!holder.getSurface().isValid()){/*wait*/}
		Canvas canvas = holder.lockCanvas();
		drawCanvas(canvas);
		holder.unlockCanvasAndPost(canvas);
	}
	
	private void drawCanvas(Canvas canvas) {
		bg.draw(canvas);
		for(Obstacle r : obstacles){
			r.draw(canvas);
		}
		
		player.draw(canvas);
		fg.draw(canvas);
		
		// Score Text
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(getScoreTextMetrics());
		canvas.drawText(game.getResources().getString(R.string.onscreen_score_text) + " " + game.performanceIndex.points
						,
						0, getScoreTextMetrics(), paint);
	}
	
	
	private void drawTapView(Canvas canvas) {
		tapview.move();
		tapview.draw(canvas);
	}
	
	
	private void playerDeadFall(){
		player.dead();
		do{
			player.move();
			draw();
			// sleep
			try {
				Thread.sleep(UPDATE_INTERVAL/4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(!player.isTouchingGround());
	}
	
	
	private void drawBlinking(boolean drawPlayer){
		while(!holder.getSurface().isValid()){/*wait*/}
		Canvas canvas = holder.lockCanvas();
		bg.draw(canvas);
		for(Obstacle r : obstacles){
			r.draw(canvas);
		}
		
		if(drawPlayer){
			player.draw(canvas);
		}
		fg.draw(canvas);
		
		
		// Score Text
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(getScoreTextMetrics());
		canvas.drawText(game.getResources().getString(R.string.onscreen_score_text) + " " + game.performanceIndex.points
						,
						0, getScoreTextMetrics(), paint);
		holder.unlockCanvasAndPost(canvas);
	}
	
	/**
	 * Checks whether a obstacle is passed.
	 */
	private void checkPasses(){
		for(Obstacle o : obstacles){
			if(o.isPassed()){
				if(!o.isAlreadyPassed){
					o.onPass();
					
				}
			}
		}
	}
	
	
	
	/**
	 * Checks obstacles  are out of range and deletes them
	 */
	private void checkOutOfRange(){
		for(int i=0; i<obstacles.size(); i++){
			if(this.obstacles.get(i).isOutOfRange()){
				this.obstacles.remove(i);
				i--;
			}
		}
		
	}
	
	
	private void checkCollision(){
		for(Obstacle o : obstacles){
			if(o.isColliding(player)){
				o.onCollision();
				gameOver();
			}
		}
		
		if(player.isTouchingEdge()){
			gameOver();
		}
	}
	
	
	private void createObstacle(){
		if(obstacles.size() < 1){
			obstacles.add(new Obstacle(this, game));
		}
	}
	
	/**
	 * Update sprite movements
	 */
	private void move(){
		for(Obstacle o : obstacles){
			o.setSpeedX(-getSpeedX());
			o.move();
		}
		
		bg.setSpeedX(-getSpeedX()/2);
		bg.move();
		
		fg.setSpeedX(-getSpeedX()*4/3);
		fg.move();
		
		player.move();
	}
	
	
	/**
	 * return the speed of the obstacles
	 */
	public int getSpeedX(){
		// 16 @ 720x1280 px
		int speedDefault = this.getWidth() / 45;
		// 1,2 every 4 points @ 720x1280 px
		int speedIncrease = (int) (this.getWidth() / 600f * (game.performanceIndex.points / 4));
		
		int speed = speedDefault + speedIncrease;
		
		if(speed > 2*speedDefault){
			return 2*speedDefault;
		}else{
			return speed;
		}
	}
	
	public void gameOver(){
		this.threadRun = false;
		playerDeadFall();
		game.gameOver();
	}
	
	
	public int getScoreTextMetrics(){
		return (int) (this.getHeight() / 21.0f);
	}
	
	public PlayableObject getPlayer(){
		return this.player;
	}
	
	public Game getGame(){
		return this.game;
	}

}
