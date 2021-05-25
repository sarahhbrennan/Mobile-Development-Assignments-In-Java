package com.example.sarah_brennan_2962279_ass2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * @author sarah brennan
 */

public class CustomView extends View {

    //used to keep the lines in between the boxes
    private static final float PADDING = 5.0f;
    // create paint colours for all colours needed
    private Paint black;
    private Paint snow;
    private Paint grey;
    private Paint salmon;
    private Paint rose_taupe;
    private Paint mint;
    private Paint saffron;
    private Paint cadet;
    private Paint cadet_blue;
    private Paint star_command_blue;

    // position on screen where touched/untouched
    private Point point;

    // get the width and height of square
    private double widthHeight;
    // get the width and height of the boxes
    private double cellWidthHeight;

    // create 2D array of minesweeper object
    private Minesweeper[][] minesweeper = new Minesweeper[10][10];

    // for the points where lines will be drawn to create boxes
    private float[] verticalPoints;
    private float[] horizontalPoints;

    // to check which setting is on, marking or uncovering
    private boolean uncoverBoxes = true;
    // boolean to stop allowing input once gameover
    private boolean gameOver = false;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Initialises the variables in customview
     */
    private void init() {

        // reset variables for when reset button clicked
        gameOver = false;

        // initialise and set the correct color for each paint created
        black = new Paint();
        black.setColor(Color.BLACK);
        // used for text so set text size
        black.setTextSize(100);

        // change style to stroke as using for lines between the black boxes
        snow = new Paint();
        snow.setColor(Color.parseColor("#FFF7F8"));
        snow.setStyle(Paint.Style.STROKE);
        snow.setStrokeWidth(10.0f);

        grey = new Paint();
        grey.setColor(Color.LTGRAY);

        salmon = new Paint();
        salmon.setColor(Color.parseColor("#FF7E6B"));

        rose_taupe = new Paint();
        rose_taupe.setColor(Color.parseColor("#8C5E58"));
        // used for text so set text size
        rose_taupe.setTextSize(100);

        mint = new Paint();
        mint.setColor(Color.parseColor("#A9F0D1"));
        // used for text so set text size
        mint.setTextSize(100);

        saffron = new Paint();
        saffron.setColor(Color.parseColor("#E3B505"));

        cadet = new Paint();
        cadet.setColor(Color.parseColor("#4F6D7A"));
        // used for text so set text size
        cadet.setTextSize(100);

        cadet_blue = new Paint();
        cadet_blue.setColor(Color.parseColor("#56A3A6"));

        star_command_blue = new Paint();
        star_command_blue.setColor(Color.parseColor("#2176AE"));
        // used for text so set text size
        star_command_blue.setTextSize(100);
    }

    /**
     * Draws minesweeper game boxes and if mines or numbers/blank spaces are uncovered
     *
     * @param canvas - takes in canvas to draw
     */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw square for minesweeper
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredWidth(), cadet_blue);

        // draw horizontal lines
        canvas.drawLines(horizontalPoints, snow);
        // draw vertical lines
        canvas.drawLines(verticalPoints, snow);

        for (int i = 0; i < minesweeper.length; i++) {
            for (int j = 0; j < minesweeper[i].length; j++) {
                // checks to see which mode we are in, flag or unmark
                if (!this.minesweeper[i][j].getBoxesCovered()) {
                    // checks how many mines surround the selected box, or if it is a mine
                    if (minesweeper[i][j].getMineCount() == -1) {
                        // mine selected but not flagged
                        if (!this.minesweeper[i][j].getBoxesMarked()) {
                            drawBox(canvas, i, j, salmon, "M", black);
                        }
                        // mine selected and flagged
                        else {
                            drawBox(canvas, i, j, saffron, "M", rose_taupe);
                        }
                    }
                    // no mines surrounding box
                    else if (minesweeper[i][j].getMineCount() == 0) {
                        drawBox(canvas, i, j, grey);
                    }
                    // 1 mine surrounds box selected up to 8 as 8 is the most amount of mines that can surround a cell.
                    else if (minesweeper[i][j].getMineCount() == 1) {
                        drawBox(canvas, i, j, grey, "1", rose_taupe);
                    } else if (minesweeper[i][j].getMineCount() == 2) {
                        drawBox(canvas, i, j, grey, "2", mint);
                    } else if (minesweeper[i][j].getMineCount() == 3) {
                        drawBox(canvas, i, j, grey, "3", cadet);
                    } else if (minesweeper[i][j].getMineCount() == 4) {
                        drawBox(canvas, i, j, grey, "4", star_command_blue);
                    } else if (minesweeper[i][j].getMineCount() == 5) {
                        drawBox(canvas, i, j, grey, "5", rose_taupe);
                    } else if (minesweeper[i][j].getMineCount() == 6) {
                        drawBox(canvas, i, j, grey, "6", mint);
                    } else if (minesweeper[i][j].getMineCount() == 7) {
                        drawBox(canvas, i, j, grey, "7", cadet);
                    } else if (minesweeper[i][j].getMineCount() == 8) {
                        drawBox(canvas, i, j, grey, "8", star_command_blue);
                    }
                } else {
                    if (minesweeper[i][j].getBoxesMarked()) {
                        drawBox(canvas, i, j, saffron);
                    } else {
                        drawBox(canvas, i, j, cadet_blue);
                    }
                }
            }
        }
    }

    /**
     * Checks position of where user has touched the screen if it has been touched
     *
     * @param - takes in the event of the screen being touched
     * @return - returns boolean
     */
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        if (!gameOver) {
            switch (action) {
                case (MotionEvent.ACTION_DOWN):
                    double xDown = event.getX();
                    double yDown = event.getY();
                    int columnDown = (int) (xDown / cellWidthHeight);
                    int rowDown = (int) (yDown / cellWidthHeight);
                    // holds the point where screen touched
                    point = new Point(rowDown, columnDown);
                    invalidate();
                    return true;
                case (MotionEvent.ACTION_UP):
                    // get x and y axis of location where finger is not selected anymore
                    double xUp = event.getX();
                    double yUp = event.getY();
                    // get the row and column of cell selected
                    int rowUp = (int) (yUp / cellWidthHeight);
                    int columnUp = (int) (xUp / cellWidthHeight);

                    if (point.equals(rowUp, columnUp)) {
                        // make sure that where released is the same x and y axis as where touched
                        if (uncoverBoxes) {
                            // make sure the box is not already marked
                            if (!minesweeper[rowUp][columnUp].getBoxesMarked()) {
                                this.minesweeper[rowUp][columnUp].setBoxesCovered(false);
                                // if mine is clicked on
                                if (this.minesweeper[rowUp][columnUp].getMineCount() == -1) {
                                    this.setGameOver(true);
                                    // uncover all the mines
                                    for (int i = 0; i < this.minesweeper.length; i++) {
                                        for (int j = 0; j < this.minesweeper[i].length; j++) {
                                            if (this.minesweeper[i][j].getMineCount() == -1) {
                                                this.minesweeper[i][j].setBoxesCovered(false);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        // if in flag mode, check if the boxes are already marked or not.
                        // if boxes already flagged, unflag it. If boxes not flagged, flag it
                        else {
                            if (!this.minesweeper[rowUp][columnUp].getBoxesMarked()) {
                                this.minesweeper[rowUp][columnUp].setBoxesMarked(true);
                            } else if (this.minesweeper[rowUp][columnUp].getBoxesMarked()) {
                                this.minesweeper[rowUp][columnUp].setBoxesMarked(false);
                            }
                        }
                    }
                    invalidate();
                    return true;
                default:
                    return super.onTouchEvent(event);
            }
        }
        return true;
    }

    /**
     * creates the boxes for the minesweeper game
     *
     * @param widthMeasureSpec  - gets the width of the custom view
     * @param heightMeasureSpec - gets the height of the custom view
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (width > height) {
            widthHeight = height;
        } else {
            widthHeight = width;
        }

        cellWidthHeight = (widthHeight / 10);

        // creates vertical lines for boxes
        verticalPoints = new float[44];
        for (int i = 0; i < verticalPoints.length; i = i + 4) {
            // x and y axis of first point in vertical line
            verticalPoints[i] = (float) (cellWidthHeight * (i / 4));
            verticalPoints[i + 1] = 0f;
            // x and y axis of second point in vertical line
            verticalPoints[i + 2] = (float) (cellWidthHeight * (i / 4));
            verticalPoints[i + 3] = (float) widthHeight;
        }

        //creates horizontal lines for
        horizontalPoints = new float[44];
        for (int i = 0; i < horizontalPoints.length; i = i + 4) {
            // x and y axis for first point in horizontal line
            horizontalPoints[i] = 0f;
            horizontalPoints[i + 1] = (float) (cellWidthHeight * (i / 4));
            // x and y axis for second point in horizontal line
            horizontalPoints[i + 2] = (float) widthHeight;
            horizontalPoints[i + 3] = (float) (cellWidthHeight * (i / 4));

        }
        setMeasuredDimension((int) widthHeight, (int) widthHeight);
    }

    /**
     * sets the background colour of the box that has been uncovered
     *
     * @param canvas           - canvas to draw to
     * @param row              - row location
     * @param column           - column location
     * @param backgroundColour - colour of box to be changed to
     */
    public void drawBox(Canvas canvas, int row, int column, Paint backgroundColour) {
        float leftSide = (float) ((column * cellWidthHeight) + PADDING);
        float topSide = (float) ((row * cellWidthHeight) + PADDING);
        float rightSide = (float) (((column + 1) * cellWidthHeight) - PADDING);
        float bottomSide = (float) (((row + 1) * cellWidthHeight) - PADDING);

        canvas.drawRect(leftSide, topSide, rightSide, bottomSide, backgroundColour);

    }

    /**
     * sets the background colour of box uncovered and text if there are mines surrounding it/is a mine, it calls drawBox and textForBox with information passed into it
     *
     * @param canvas           - canavs to draw to
     * @param row              - row location
     * @param column           - column location
     * @param backgroundColour - colour of box to be changed to
     * @param surroundMineNum  - amount of mines the box is surrounded by
     * @param textColour       - colour of text to be drawn
     */
    public void drawBox(Canvas canvas, int row, int column, Paint backgroundColour, String surroundMineNum, Paint textColour) {
        drawBox(canvas, row, column, backgroundColour);
        textForBox(canvas, row, column, textColour, surroundMineNum);
    }

    /**
     * draws the correct text for the box uncovered if it is a either a mine or is surrounded by mines
     *
     * @param canvas          - canvas to draw to
     * @param textColour      - colour of text
     * @param surroundMineNum - number to write to screen/M for mine
     * @param row             - row location
     * @param column          - column location
     */
    public void textForBox(Canvas canvas, int row, int column, Paint textColour, String surroundMineNum) {
        int leftSide = (int) ((column * cellWidthHeight) + PADDING);
        int bottomSide = (int) (((row + 1) * cellWidthHeight) - PADDING);
        canvas.drawText(surroundMineNum, leftSide + 25, bottomSide - 25, textColour);
    }

    /**
     * sets up the array for minesweeper boxes
     *
     * @param minesweeper - takes in the 2D array that makes up the game
     */
    public void setArray(Minesweeper[][] minesweeper) {
        this.minesweeper = minesweeper;
        //forces the view to draw
        invalidate();
    }

    /**
     * sets boolean gameOver to true if mine selected, otherwise it is false
     *
     * @param gameOver - used to stop allowing to click boxes when mine found
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * checks to see which mode user is on, flagging or uncover
     *
     * @return - returns boolean true if set to uncover and false if set to flag
     */
    public boolean getUncoverBoxes() {
        return uncoverBoxes;
    }

    /**
     * sets the boolean to correct setting for flag or uncover
     *
     * @param uncoverBoxes - sets to true if in uncover setting or false if set to flag
     */
    public void setUncoverBoxes(boolean uncoverBoxes) {
        this.uncoverBoxes = uncoverBoxes;
    }
}
