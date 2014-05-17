

package com.flappydoraemon;

import android.app.Activity;
import com.flappydoraemon.R;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends Activity {
	
	
	
	public static final float DEFAULT_VOLUME = 0.3f;
	
	/** Volume for sound and music */
	public static float volume = DEFAULT_VOLUME;
	
	private ImageButton muteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ((ImageButton)findViewById(R.id.play_button)).setImageBitmap(SpriteObject.createBitmap(getResources().getDrawable(R.drawable.play_button), this));
        ((ImageButton)findViewById(R.id.play_button)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent("com.flappydoraemon.Game"));
			}
		});
           
        muteButton = ((ImageButton)findViewById(R.id.mute_button));
        muteButton.setImageBitmap(SpriteObject.createBitmap(getResources().getDrawable(R.drawable.speaker), this));
        muteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(volume != 0){
					volume = 0;
					muteButton.setImageBitmap(SpriteObject.createBitmap(getResources().getDrawable(R.drawable.speaker_mute), MainActivity.this));
				}else{
					volume = DEFAULT_VOLUME;
					muteButton.setImageBitmap(SpriteObject.createBitmap(getResources().getDrawable(R.drawable.speaker), MainActivity.this));
				}
			}
		});
         
   
    }
    
	
	@Override
	protected void onResume() {
		super.onResume();
		
	}

	

}
