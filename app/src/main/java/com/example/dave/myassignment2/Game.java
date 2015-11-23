package com.example.dave.myassignment2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

/**
 * Created by Dave on 03/11/2015.
 */
public class Game extends Activity {

    private Random random;
    private int count;
    private int level;
    private int[] pattern;
    private int[] input_pattern;
    private int index;
    private int input_index;
    private int highScore;
    private boolean playButtonLock;
    private boolean colorButtonLock;
    private final int MAX_GAMES = 20;
    private Button topLeft;
    private Button topRight;
    private Button bottomLeft;
    private Button bottomRight;
    private ImageButton statusButton;
    private TextView levelText;
    private TextView guesses;
    private ProgressBar firstBar;
    private Runnable countdown3;
    private Runnable countdown2;
    private Runnable countdown1;
    private Runnable countdown0;
    private final Handler UIHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        topLeft = (Button) findViewById(R.id.top_left);
        topRight = (Button) findViewById(R.id.top_right);
        bottomLeft = (Button) findViewById(R.id.bottom_left);
        bottomRight = (Button) findViewById(R.id.bottom_right);
        statusButton = (ImageButton) findViewById(R.id.status_button);
        levelText = (TextView) findViewById(R.id.level);
        guesses = (TextView) findViewById(R.id.guess);
        firstBar = (ProgressBar) findViewById(R.id.firstBar);
        //secondBar = (ProgressBar) findViewById(R.id.secondBar);
        count = 2;
        level = 1;
        index = 0;
        input_index = 0;
        pattern = new int[MAX_GAMES];
        input_pattern = new int[MAX_GAMES];
        playButtonLock = false;
        colorButtonLock = true;
        random = new Random();
        highScore = getIntent().getIntExtra("highScore", -1);
        levelText.setText(level + "");
        guesses.setText("\t" + input_index + "/" + count);

        statusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!playButtonLock) {
                    countdown();
                    UIHandler.postDelayed(countdown3, 0);
                    UIHandler.postDelayed(countdown2, 1000);
                    UIHandler.postDelayed(countdown1, 2000);
                    UIHandler.postDelayed(countdown0, 3000);
                    playButtonLock = true;
                    UIHandler.postDelayed(new Runnable() {
                        public void run() {
                            game();
                        }
                    }, 3000);
                    colorButtonLock = false;
                }
            }
        });
    }

    /**
     * generate random pattern of buttons, take in user commands
     */
    private void game() {
        topLeft.setBackgroundResource(R.color.topLeftDark);
        topRight.setBackgroundResource(R.color.topRightDark);
        bottomLeft.setBackgroundResource(R.color.bottomLeftDark);
        bottomRight.setBackgroundResource(R.color.bottomRightDark);
        firstBar.setVisibility(View.VISIBLE);
        firstBar.setMax(MAX_GAMES);
       // secondBar.setVisibility(View.VISIBLE);
        //generate pattern
        for (int i = index; i < count; i++) {
            pattern[i] = random.nextInt(4);
        }

        String s = "";
        for (int i = 0; i < count; i++) {
            final int p = i;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            flash(pattern[p]);
                        }
                    }, 500);
                }
            },500);

        }

        topLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!colorButtonLock) {
                    topLeft.setBackgroundResource(R.color.topLeftBright);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            topLeft.setBackgroundResource(R.color.topLeftDark);
                        }
                    }, 200);
                    input_pattern[input_index] = 0;
                    input_index++;
                    guesses.setText("\t" + input_index + "/" + count);
                    checkDone();
                }
            }
        });

        topRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!colorButtonLock) {
                    topRight.setBackgroundResource(R.color.topRightBright);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            topRight.setBackgroundResource(R.color.topRightDark);
                        }
                    }, 200);
                    input_pattern[input_index] = 1;
                    input_index++;
                    guesses.setText("\t" + input_index + "/" + count);
                    checkDone();
                }
            }
        });

        bottomLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!colorButtonLock) {
                    bottomLeft.setBackgroundResource(R.color.bottomLeftBright);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            bottomLeft.setBackgroundResource(R.color.bottomLeftDark);
                        }
                    }, 200);
                    input_pattern[input_index] = 2;
                    input_index++;
                    guesses.setText("\t" + input_index + "/" + count);
                    checkDone();
                }
            }
        });

        bottomRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!colorButtonLock) {
                    bottomRight.setBackgroundResource(R.color.bottomRightBright);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            bottomRight.setBackgroundResource(R.color.bottomRightDark);
                        }
                    }, 200);
                    input_pattern[input_index] = 3;
                    input_index++;
                    guesses.setText("\t" + input_index + "/" + count);
                    checkDone();
                }
            }
        });
    }
    /**
     * highlight buttons for user to repeat
     * @param i - int representing panel to flash
     */
    private void flash(int i) {
        final int s = i;
//        for(int i = 0; i <count; i++) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                switch (s) {
                    case 0:
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                topLeft.setBackgroundResource(R.color.topLeftBright);
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        topLeft.setBackgroundResource(R.color.topLeftDark);
                                    }
                                }, 10);
                            }
                        }, 1000);
                        break;
                    case 1:
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                topRight.setBackgroundResource(R.color.topRightBright);
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        topRight.setBackgroundResource(R.color.topRightDark);
                                    }
                                }, 10);
                            }
                        }, 1000);
                        break;
                    case 2:
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bottomLeft.setBackgroundResource(R.color.bottomLeftBright);
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        bottomLeft.setBackgroundResource(R.color.bottomLeftDark);
                                    }
                                }, 10);
                            }
                        }, 1000);
                        break;
                    case 3:
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bottomRight.setBackgroundResource(R.color.bottomRightBright);
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        bottomRight.setBackgroundResource(R.color.bottomRightDark);
                                    }
                                }, 10);
                            }
                        }, 1000);
                        break;
                    default:
                        break;
                }
            }
        }, 100);

//        }
    }

    /**
     * checks if user had input required number of commands
     */
    public void checkDone() {
        if (input_index == count)
            done();
    }

    /**
     * moves on to next level/shows win or lose
     */
    public void done() {
        for (int i = 0; i < count; i++) {
            if (input_pattern[i] != pattern[i]) {
                statusButton.setImageResource(R.drawable.wrong);
                if(level > highScore) {
                    highScore = level - 1;
                    try {
                        FileOutputStream fileOut = openFileOutput("simon_high_score.txt", MODE_PRIVATE);
                        OutputStreamWriter outputWriter=new OutputStreamWriter(fileOut);
                        outputWriter.write(highScore + "");
                        outputWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                reset();
                return;
            }
        }
        if (count == MAX_GAMES) {
            Intent winIntent = new Intent(this, Win.class);
            winIntent.putExtra("highScore", highScore);
            startActivity(winIntent);
            finish();
        }
        statusButton.setImageResource(R.drawable.tick);
        colorButtonLock = true;
        playButtonLock = false;
        index = count;
        count++;
        input_index = 0;
        guesses.setText("\t" + input_index + "/" + count);
        firstBar.setProgress(count - 1);
        firstBar.setSecondaryProgress(count);
        level++;
        levelText.setText(level + "");
    }

    /**
     * reset game variable on lose of play again
     */
    public void reset() {
        count = 2;
        index = 0;
        input_index = 0;
        level = 1;
        pattern = new int[MAX_GAMES];
        input_pattern = new int[MAX_GAMES];
        playButtonLock = false;
        colorButtonLock = true;
        guesses.setText("\t" + input_index + "/" + count);
        levelText.setText(level + "");
        firstBar.setVisibility(View.GONE);
    }

    /**
     * used in countdown
     * @param d - image to be shown
     */
    public void changeButton(int d) {
        statusButton.setImageResource(d);
    }

    /**
     * Create threads to display countdown on playbutton
     */
    public void countdown() {
        countdown3 = new Runnable() {
            @Override
            public void run() {
                changeButton(R.drawable.three);
            }
        };

        countdown2 = new Runnable() {
            @Override
            public void run() {
                changeButton(R.drawable.two);
            }
        };

        countdown1 = new Runnable() {
            @Override
            public void run() {
                changeButton(R.drawable.one);
            }
        };

        countdown0 = new Runnable() {
            @Override
            public void run() {
                changeButton(R.color.black);
            }
        };
    }

    @Override
    public void onBackPressed() {
        if (level > highScore) {
            highScore = level - 1;
            try {
                FileOutputStream fileOut=openFileOutput("simon_high_score.txt", MODE_PRIVATE);
                OutputStreamWriter outputWriter=new OutputStreamWriter(fileOut);
                outputWriter.write(highScore + "");
                outputWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Intent back = new Intent(this, MainMenu.class);
        back.putExtra("highScore", highScore);
        startActivity(back);
        finish();
    }
}