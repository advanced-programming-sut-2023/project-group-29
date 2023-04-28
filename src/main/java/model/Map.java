package model;

public class Map {
    private final Cell[][] cells;
    private final int width;

    public Map(int width) {
        this.width = width;
        this.cells = new Cell[width][width];
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int distanceOfTwoCellsForMoving(int firstX, int firstY, int secondX, int secondY, boolean ableToClimbLadder) {
        return 10;  //TODO implement function
    }

    public int distanceOfTwoCellsForAttacking(int firstX, int firstY, int secondX, int secondY) {
        //TODO implement
        //TODO can target over the wall or not
        return 10;
    }

    public int getWidth() {
        return width;
    }

    public boolean isIndexValid(int x, int y) {
        return x >= 1 && x <= width && y >= 1 && y <= width;
    }
}