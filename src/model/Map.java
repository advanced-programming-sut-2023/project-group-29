package model;

public class Map
{
    private Cell[][] cells;
    public Map(int width)
    {
        this.cells = new Cell[width][width];
    }
}
