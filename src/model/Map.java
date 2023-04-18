package model;

import java.util.PropertyResourceBundle;

public class Map {
    private int width;
    private final Cell[][] cells;

    public Map(int width) {
        this.width=width;
        this.cells = new Cell[width][width];
    }

    public Cell[][] getCells()
    {
        return cells;
    }

    public int distanceOfTwoCells(int firstX,int firstY,int secondX,int secondY,boolean ableToClimbLadder)
    {
        return 10;  //TODO implement function
    }

    public int getWidth()
    {
        return width;
    }
}
