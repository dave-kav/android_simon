package com.example.dave.myassignment2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Dave on 22/11/2015.
 */
public class Win extends Activity {
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_win);
        final File scoreFile = new File(this.getFilesDir(), "simon_high_score.txt");
        final int highScore = getIntent().getIntExtra("highScore", -1);

        Button again = (Button) findViewById(R.id.play_again_button);
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playAgain = new Intent(Win.this, Game.class);
                startActivity(playAgain);
                finish();
            }
        });

        Button quit = (Button) findViewById(R.id.quit_button);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileWriter pw = null;
                try {
                    pw = new FileWriter(scoreFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    pw.write(highScore);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }
}
