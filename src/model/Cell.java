package model;

import model.buildings.Building;
import model.weapons.weaponClasses.Trap;

import java.util.ArrayList;

public class Cell {
    private final ArrayList<Asset> movingObjects = new ArrayList<>();
    private Trap trap = null;
    private Building building = null;
    private int speed;
    private Color showingColor;
    private final CellType cellType;
    private final boolean ableToBuildOn;
    private final boolean ableToMoveOn;

    public Cell(CellType cellType) {
        this.cellType = cellType;
        this.showingColor = cellType.getShowingColor();
        this.speed = cellType.getSpeed();
        this.ableToBuildOn = cellType.isAbleToBuildOn();
        this.ableToMoveOn = cellType.isAbleToMoveOn();
    }

    public ArrayList<Asset> getMovingObjects() {
        return movingObjects;
    }

    public Trap getTrap() {
        return trap;
    }

    public void setTrap(Trap trap) {
        this.trap = trap;
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

    public CellType getCellType() {
        return cellType;
    }

    public boolean isAbleToBuildOn(String type) {
        //TODO: complete this function
        return ableToBuildOn;
    }

    public boolean isAbleToMoveOn() {
        return ableToMoveOn;
    }

    public void makeBuilding(String type) {
        //TODO: complete this function
    }

    enum Color {
        RED,
        GREEN,
        BLUE
    }
}
