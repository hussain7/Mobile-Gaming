

package com.flappydoraemon;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameDialog extends Dialog {
	public static final int REVIVE_PRICE = 5;
	
	/** SharedPreferencewhich saves the score */
	public static final String score_save = "score_save";
	
	/** Key which saves the score */
	public static final String key_best_score = "score";
	
	private Game game;
	
	private TextView currentScoreVal;
	private TextView bestScoreVal;


	public GameDialog(Game game) {
		super(game);
		this.game = game;
		this.setContentView(R.layout.gameover);
		this.setCancelable(false);
		
		currentScoreVal = (TextView) findViewById(R.id.tv_current_score_value);
		bestScoreVal = (TextView) findViewById(R.id.tv_best_score_value);

	}
	
	public void init(){
		
		Button okButton = (Button) findViewById(R.id.b_ok);
		okButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
		
				game.performanceIndex.saveLocal(game);
		 		dismiss();
				game.finish();
			}
		});

		manageScore();
	}
	
	private void manageScore(){
		SharedPreferences saves = game.getSharedPreferences(score_save, 0);
		int oldPoints = saves.getInt(key_best_score, 0);
		if(game.performanceIndex.points > oldPoints){
			// Saving new highscore
			SharedPreferences.Editor editor = saves.edit();
			editor.putInt(key_best_score, game.performanceIndex.points);
			bestScoreVal.setTextColor(Color.BLUE);
			editor.commit();
		}
		currentScoreVal.setText("" + game.performanceIndex.points);
		bestScoreVal.setText("" + oldPoints);
	}
	
	
}
