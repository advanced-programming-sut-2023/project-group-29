package model;

import model.map.Cell;
import model.map.CellType;

public class MapTemplate {
    private final Cell[][] cells;
    private final boolean[][] hasEmpire;
    private final int width;
    private final int height;
    private final int usersCount;

    public MapTemplate(int width, int height, int usersCount) {
        this.width = width;
        this.height = height;
        this.usersCount = usersCount;

        cells=new Cell[width][height];
        for(int i=0;i<width;i++)
            for(int j=0;j<height;j++)
                cells[i][j]=new Cell(CellType.PLAIN_GROUND,i,j);

        hasEmpire=new boolean[width][height];
    }
    public Cell[][] getCells() {
        return cells;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public boolean[][] getHasEmpire() {
        return hasEmpire;
    }
}
