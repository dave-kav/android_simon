package com.example.dave.myassignment2;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    private int maxGames;
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
    private boolean trackScore;
    private boolean playSounds;
    private int countDownSound = R.raw.countdown;
    private int correct = R.raw.correct;
    private int wrong = R.raw.wrong;
    private MediaPlayer mp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        maxGames = getIntent().getIntExtra("maxGames", 5);
        topLeft = (Button) findViewById(R.id.top_left);
        topRight = (Button) findViewById(R.id.top_right);
        bottomLeft = (Button) findViewById(R.id.bottom_left);
        bottomRight = (Button) findViewById(R.id.bottom_right);
        statusButton = (ImageButton) findViewById(R.id.status_button);
        levelText = (TextView) findViewById(R.id.level);
        guesses = (TextView) findViewById(R.id.guess);
        firstBar = (ProgressBar) findViewById(R.id.firstBar);
        count = 2;
        level = 1;
        index = 0;
        input_index = 0;
        pattern = new int[maxGames];
        input_pattern = new int[maxGames];
        playButtonLock = false;
        colorButtonLock = true;
        random = new Random();
        highScore = getIntent().getIntExtra("highScore", -1);
        levelText.setText(level + "");
        guesses.setText("\t" + input_index + "/" + count);
        trackScore = getIntent().getBooleanExtra("trackScore", true);
        playSounds = getIntent().getBooleanExtra("playSounds", true);

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
        firstBar.setMax(maxGames);
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
                    }, 5);
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
        new Handler().postDelayed(new Runnable() {
            public void run() {
                switch (s) {
                    case 0:
                    try {
                        Thread.sleep(500
                        );
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
                        Thread.sleep(500);
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
                        Thread.sleep(500);
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
                        Thread.sleep(500);
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
    }

    /**
     * checks if user had input required number of commands
     */
    private void checkDone() {
        if (input_index == count)
            done();
    }

    /**
     * moves on to next level/shows win or lose
     */
    private void done() {
        for (int i = 0; i < count; i++) {
            if (input_pattern[i] != pattern[i]) {
                statusButton.setImageResource(R.drawable.wrong);
                playSound(wrong);
                if(trackScore) {
                    if (level > highScore) {
                        highScore = level - 1;
                        try {
                            FileOutputStream fileOut = openFileOutput("simon_high_score.txt", MODE_PRIVATE);
                            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOut);
                            outputWriter.write(highScore + "");
                            outputWriter.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                reset();
                return;
            }
        }
        if (count == maxGames) {
            Intent winIntent = new Intent(this, Win.class);
            if (highScore < maxGames) {
                if (maxGames == 5)
                    highScore = 5;
                else if (maxGames == 10)
                    highScore = 10;
                else
                    highScore = 20;

                if(trackScore) {
                    try {
                        FileOutputStream fileOut = openFileOutput("simon_high_score.txt", MODE_PRIVATE);
                        OutputStreamWriter outputWriter = new OutputStreamWriter(fileOut);
                        outputWriter.write(highScore + "");
                        outputWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            winIntent.putExtra("highScore", highScore).putExtra("trackScore", trackScore).putExtra("playSounds", playSounds);
            startActivity(winIntent);
            finish();
        }
        else {
            statusButton.setImageResource(R.drawable.tick);
            playSound(correct);
            colorButtonLock = true;
            index = count;
            count++;
            input_index = 0;
            guesses.setText("\t" + input_index + "/" + count);
            firstBar.setProgress(count - 1);
            firstBar.setSecondaryProgress(count);
            level++;
            levelText.setText(level + "");
            UIHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playButtonLock = true;
                    playAgain();
                }
            }, 2000);
        }
    }

    private void playAgain() {
        countdown();
        UIHandler.postDelayed(countdown3, 0);
        UIHandler.postDelayed(countdown2, 1000);
        UIHandler.postDelayed(countdown1, 2000);
        UIHandler.postDelayed(countdown0, 3000);
        UIHandler.postDelayed(new Runnable() {
            public void run() {
                game();
            }
        }, 1500);
        colorButtonLock = false;
    }
    /**
     * reset game variable on lose of play again
     */
    private void reset() {
        count = 2;
        index = 0;
        input_index = 0;
        level = 1;
        pattern = new int[maxGames];
        input_pattern = new int[maxGames];
        playButtonLock = false;
        colorButtonLock = true;
        guesses.setText("\t" + input_index + "/" + count);
        levelText.setText(level + "");
        firstBar.setProgress(count - 1);
        firstBar.setSecondaryProgress(count);
        firstBar.setVisibility(View.GONE);
    }

    /**
     * used in countdown
     * @param d - image to be shown
     */
    private void changeButton(int d) {
        statusButton.setImageResource(d);
    }

    /**
     * Create threads to display countdown on playbutton
     */
    private void countdown() {
        playSound(countDownSound);
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

    public void playSound(int soundID) {
        if(playSounds) {
            mp = MediaPlayer.create(this, soundID);
            mp.start();
        }
    }

    @Override
    public void onBackPressed() {
        if (trackScore) {
            if (level > highScore) {
                highScore = level - 1;
                try {
                    FileOutputStream fileOut = openFileOutput("simon_high_score.txt", MODE_PRIVATE);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileOut);
                    outputWriter.write(highScore + "");
                    outputWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Intent back = new Intent(this, MainMenu.class);
        back.putExtra("highScore", highScore);
        if(mp!=null)
            mp.stop();
        startActivity(back);
        finish();
    }
}