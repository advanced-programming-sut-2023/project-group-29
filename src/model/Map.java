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

    public Cell getCell(int x, int y) {
        return cells[x - 1][y - 1];
    }

    public int distanceOfTwoCells(int firstX, int firstY, int secondX, int secondY, boolean ableToClimbLadder) {
        return 10;  //TODO implement function
    }

    public int getWidth() {
        return width;
    }
}
