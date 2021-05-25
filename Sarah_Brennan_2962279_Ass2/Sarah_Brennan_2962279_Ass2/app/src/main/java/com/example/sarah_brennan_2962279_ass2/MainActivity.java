package com.example.sarah_brennan_2962279_ass2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * @author sarah brennan
 */

public class MainActivity extends AppCompatActivity {

    // set number of mines and size of squares
    private static final int NUM_MINES = 20;
    private static final int SQUARE_ROWS_COLUMNS = 10;

    // create variables needed for the items on MainActivity to update
    private TextView minesMarked;
    // reference chronometer from: https://en.proft.me/2017/11/18/how-create-count-timer-android/
    private Chronometer timer;
    private TextView minesRemaining;
    private Button flagToggleBtn;
    private Button resetBtn;
    private CustomView view;

    // create 2D array for minesweeper rows and columns
    private Minesweeper[][] minesweeper;
    // create arraylist of type point to add mines to
    private ArrayList<Point> mines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialise everything on activity_main.xml
        minesMarked = (TextView) findViewById(R.id.minesMarkedTV);
        timer = (Chronometer) findViewById(R.id.cmTimer);
        minesRemaining = (TextView) findViewById(R.id.minesRemainingTV);
        flagToggleBtn = (Button) findViewById(R.id.flagToggleButton);
        resetBtn = (Button) findViewById(R.id.resetBtn);
        view = (CustomView) findViewById(R.id.minesweeper_custom_view);

        flagToggleBtn.setOnClickListener((View.OnClickListener) v -> {
            changeToFlagOrUncover();
        });

        resetBtn.setOnClickListener((View.OnClickListener) v -> {
            reset();
        });

        // start the minesweeper game
        initMinesweeper();
    }

    /**
     * changes the mode of touch from flag to uncover and vice versa
     */
    public void changeToFlagOrUncover(){
        // changes from flag to uncover depending on what it was before
        view.setUncoverBoxes(!view.getUncoverBoxes());
        if(view.getUncoverBoxes()){
            flagToggleBtn.setText("Uncover Boxes");
        }
        else{
            flagToggleBtn.setText("Flag Boxes");
        }
    }

    /**
     * resets the game to play again
     */
    private void reset() {
        // reference of how to reset chronometer from: https://stackoverflow.com/questions/31520859/chronometer-i-want-to-make-a-button-reset-to-00-00-not-restart-the-chronomete
        timer.setBase(SystemClock.elapsedRealtime());
        timer.stop();
        initMinesweeper();
        //set gameOver back to false
        view.setGameOver(false);
        // start game in uncoverboxes mode
        this.view.setUncoverBoxes(true);
        this.flagToggleBtn.setText(R.string.flag);
    }

    /**
     * initialises the array needed for game and sets up the mines within the boxes
     */
    private void initMinesweeper() {
        timer.start();
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer arg0) {
                long minutes = ((SystemClock.elapsedRealtime() - timer.getBase()) / 1000) / 60;
                long seconds = ((SystemClock.elapsedRealtime() - timer.getBase()) / 1000) % 60;
                long elapsedTime = SystemClock.elapsedRealtime();
            }
        });
        // initialise 2D array for game with mines
        minesweeper = new Minesweeper[SQUARE_ROWS_COLUMNS][SQUARE_ROWS_COLUMNS];
        // initialise mines
        mines = new ArrayList<>();
        for (int i = 0; i < NUM_MINES; i++) {
            int rowMine, columnMine;
            rowMine = (int) (Math.random() * minesweeper.length);
            columnMine = (int) (Math.random() * minesweeper[0].length);
            // use point for location of mine
            Point mine = new Point(rowMine, columnMine);
            mines.add(mine);
        }
        // creates the boxes for the game
        for (int i = 0; i < minesweeper.length; i++) {

            for (int j = 0; j < minesweeper[i].length; j++) {
                minesweeper[i][j] = new Minesweeper(true, false, getMinesSurrounding(i, j), i, j);
            }
        }
        // set up the array on the view
        view.setArray(minesweeper);
    }

    /**
     * gets the amount of mines that are surrounding the box selected
     *
     * @param row    - row location
     * @param column - column location
     * @return - returns an int with the amount of mines surrounding the box, if it is a mine it returns -1
     */
    public int getMinesSurrounding(int row, int column) {
        if (mines.contains(new Point(row, column))) {
            return -1;
        } else {
            int count = 0;
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (row + i < 0 || row + i > this.minesweeper.length ||
                            column + j < 0 || column + j > this.minesweeper.length) {
                        continue;
                    }
                    if (mines.contains(new Point(row + i, column + j))) {
                        count++;
                    }
                }
            }
            return count;
        }
    }
}