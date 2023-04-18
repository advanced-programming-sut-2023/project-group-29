package model;

import model.buildings.Building;
import model.people.humanClasses.Soldier;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;

public class Cell {
    private ArrayList<Soldier> soldiers;
    private Building building = null;
    private int speed;
    private Color showingColor;
    private CellType cellType;
    private boolean ableToBuildOn;
    private boolean ableToMoveOn;
    public Cell(CellType cellType)
    {
        this.cellType=cellType;
        this.showingColor=cellType.getShowingColor();
        this.speed=cellType.getSpeed();
        this.ableToBuildOn=cellType.isAbleToBuildOn();
        this.ableToMoveOn= cellType.isAbleToMoveOn();
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(ArrayList<Soldier> soldiers) {
        this.soldiers = soldiers;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Color getShowingColor() {
        return showingColor;
    }

    public void setShowingColor(Color showingColor) {
        this.showingColor = showingColor;
    }

    public CellType getCellType()
    {
        return cellType;
    }

    public boolean isAbleToBuildOn()
    {
        return ableToBuildOn;
    }

    public boolean isAbleToMoveOn()
    {
        return ableToMoveOn;
    }

    enum Color {
        RED,
        GREEN,
        BLUE
    }
}
