package com.example.sarah_brennan_2962279_ass2;

// Create object for minesweeper to get all the states needed

/**
 * @author sarah brennan
 */
public class Minesweeper {

    private boolean boxesCovered;
    private boolean boxesMarked;
    private int mineCount;
    private int row;
    private int column;

    public Minesweeper(boolean boxesCovered, boolean boxesMarked, int mineCount, int row, int column) {
        this.boxesCovered = boxesCovered;
        this.boxesMarked = boxesMarked;
        this.mineCount = mineCount;
        this.row = row;
        this.column = column;
    }

    public boolean getBoxesCovered() {
        return boxesCovered;
    }

    public boolean getBoxesMarked() {
        return boxesMarked;
    }

    public int getMineCount() {
        return mineCount;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setBoxesCovered(boolean boxesCovered) {
        this.boxesCovered = boxesCovered;
    }

    public void setBoxesMarked(boolean boxesMarked) {
        this.boxesMarked = boxesMarked;
    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
