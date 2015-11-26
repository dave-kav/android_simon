package com.example.dave.myassignment2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Dave on 22/11/2015.
 */
public class MainMenu extends Activity implements View.OnClickListener {
    private int highScore;
    private int maxGames;
    private GestureDetector gestureDetector;
    private Intent playGame;
    private Intent subMenu;
    private boolean trackScore;
    private boolean playSounds;
    private ImageView myGraphic;

    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.main_menu_activity);
        highScore = getIntent().getIntExtra("highScore", -1);
        trackScore = getIntent().getBooleanExtra("trackScore", true);
        playSounds= getIntent().getBooleanExtra("playSounds", true);
        maxGames = getIntent().getIntExtra("maxGames", 5);

        playGame = new Intent(this, Game.class);
        playGame.putExtra("trackScore", trackScore).putExtra("maxGames", maxGames).putExtra("playSounds", true);
        subMenu = new Intent(this, SubMenu.class);
        subMenu.putExtra("highScore", highScore).putExtra("maxGames", maxGames).putExtra("playSounds", true);

        if(highScore > -1)
            playGame.putExtra("highScore", highScore);

        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        // Create a GestureDetector
        gestureDetector = new GestureDetector(this, customGestureDetector);
        gestureDetector.setOnDoubleTapListener(customGestureDetector);

        myGraphic = (ImageView) findViewById(R.id.imageView2);
        draw();
    }

    public void draw() {
        Bitmap blankBitmap = Bitmap.createBitmap(600,600,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blankBitmap);
        myGraphic.setImageBitmap(blankBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawRect(0, 0, 600, 600, paint);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(300, 0, 600, 600, paint);
        paint.setColor(Color.RED);
        canvas.drawRect(0, 300, 300, 600, paint);
        paint.setColor(Color.BLUE);
        canvas.drawRect(300, 300, 600, 600, paint);
    }

    @Override
    //receives result from subMenu updates game variables accordingly
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            highScore = data.getIntExtra("highScore", -1);
            maxGames = data.getIntExtra("maxGames", -1);
            trackScore = data.getBooleanExtra("trackScore", true);
            playSounds = data.getBooleanExtra("playSounds", true);

            playGame = new Intent(this, Game.class);
            playGame.putExtra("trackScore", trackScore).putExtra("maxGames", maxGames).putExtra("playSounds", playSounds);
            if(highScore > -1)
                playGame.putExtra("highScore", highScore);
            subMenu.putExtra("highScore", highScore).putExtra("maxGames", maxGames).putExtra("trackScore", trackScore)
            .putExtra("playSounds", playSounds);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {

    }

    class CustomGestureDetector implements GestureDetector.OnGestureListener,
            GestureDetector.OnDoubleTapListener {

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if (e1.getX() < e2.getX()) {
                startActivity(playGame);
                finish();
            }

            if (e1.getX() > e2.getX()) {
                subMenu.putExtra("maxGames", maxGames).putExtra("trackScore", trackScore).putExtra("playSounds", playSounds);
                startActivityForResult(subMenu, 1);
            }
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }
    }
}
