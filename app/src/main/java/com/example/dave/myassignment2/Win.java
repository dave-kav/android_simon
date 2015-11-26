package com.example.dave.myassignment2;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Dave on 22/11/2015.
 */
public class Win extends Activity {

    //activity variables
    //sound file and media player
    private int victory = R.raw.victory_fanfare;
    private MediaPlayer mp;
    //booleans indicating save score/play sound
    private boolean playSounds;
    private boolean trackScore;
    //current high score in game
    private int highScore;

    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_win);
        //get variable data from the intent
        playSounds = getIntent().getBooleanExtra("playSounds", true);
        trackScore = getIntent().getBooleanExtra("trackScore", true);
        highScore = getIntent().getIntExtra("highScore", 0);

        //if user has chosen to play sounds in the options menu
        if (playSounds)
            playSound(victory);

        //set up button to allow user to plat another game
        Button again = (Button) findViewById(R.id.play_again_button);
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create intent and pass in game variables
                Intent playAgain = new Intent(Win.this, Game.class);
                playAgain.putExtra("playSounds", playSounds).putExtra("trackScore", trackScore).putExtra("highScore", highScore);
                //start game activity
                startActivity(playAgain);
                //if music is playing, stop it
                if (mp != null)
                    mp.stop();
                finish();
            }
        });

        //set up button to quit app
        Button quit = (Button) findViewById(R.id.quit_button);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if music is playing, stop it
                if (mp != null)
                    mp.stop();
                //close this activity to exit app
                finish();
            }
        });
    }

    /**
     * Plays a sound
     * @param soundID - id of a sound file, contained in raw directory
     */
    public void playSound(int soundID) {
        mp = MediaPlayer.create(this, soundID);
        mp.start();
    }
}
