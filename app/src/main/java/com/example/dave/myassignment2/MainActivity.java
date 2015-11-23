package com.example.dave.myassignment2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Dave on 22/11/2015.
 */
public class MainActivity extends Activity {
    private int highScore;
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_main);
        readScore();
        Thread startGame = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent mainMenu = new Intent(MainActivity.this, MainMenu.class);
                mainMenu.putExtra("highScore", highScore);
                startActivity(mainMenu);
                finish();
            }
        });
        startGame.start();
    }

    private void readScore() {
        String score = "";

        try {
            InputStream inputStream = openFileInput("simon_high_score.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                score = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        try {
            highScore = Integer.parseInt(score);
        }
        catch (NumberFormatException n) {
            highScore = 0;
        }
    }
}