package com.example.dave.myassignment2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by Dave on 24/11/2015.
 */
public class SubMenu extends Activity {
    private int highScore;
    private int maxGames;
    private boolean trackScore;
    private boolean playSounds;
    private TextView highScoreText;
    private ToggleButton scoreTog;
    private ToggleButton soundTog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_menu_activity);
        highScore = getIntent().getIntExtra("highScore", -1);
        highScoreText = (TextView) findViewById(R.id.high_score_text);
        highScoreText.setText("" + highScore);
        registerForContextMenu(highScoreText);
        maxGames = getIntent().getIntExtra("maxGames", -1);
        trackScore = getIntent().getBooleanExtra("trackScore", false);
        playSounds = getIntent().getBooleanExtra("playSounds", false);
        scoreTog = (ToggleButton) findViewById(R.id.toggleButton);
        if(trackScore)
            scoreTog.setChecked(true);
        else
            scoreTog.setChecked(false);

        soundTog = (ToggleButton) findViewById(R.id.toggleButton2);
        if(playSounds)
            soundTog.setChecked(true);
        else
            soundTog.setChecked(false);

        final Spinner difficulty = (Spinner) findViewById(R.id.spinner);
        if(maxGames == 10)
            difficulty.setSelection(1);
        if(maxGames == 20)
            difficulty.setSelection(2);

        Button save = (Button)  findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int d = difficulty.getSelectedItemPosition();
                if (d == 0)
                    maxGames = 5;
                else if (d == 1)
                    maxGames = 10;
                else
                    maxGames = 20;

                if (scoreTog.isChecked())
                    trackScore = true;
                else
                    trackScore = false;

                if (soundTog.isChecked())
                    playSounds = true;
                else
                    playSounds = false;

                Intent intent = new Intent(SubMenu.this, MainMenu.class);
                intent.putExtra("trackScore", trackScore).putExtra("highScore", highScore).putExtra("maxGames", maxGames)
                .putExtra("playSounds", playSounds);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Reset high score?");
        menu.add(0, v.getId(), 0, "Reset");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Reset") {
            highScore = 0;
            highScoreText.setText("" + highScore);
        }
        return true;
    }

    public void onBackPressed() {
        Intent intent = new Intent(SubMenu.this, MainMenu.class);
        setResult(Activity.RESULT_CANCELED,intent);
        finish();
    }
}
