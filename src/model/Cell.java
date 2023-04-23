package model;

import model.buildings.Building;
import model.buildings.buildingClasses.*;
import model.buildings.buildingTypes.*;
import model.weapons.weaponClasses.Trap;

import java.util.ArrayList;

public class Cell {
    private final ArrayList<Asset> movingObjects = new ArrayList<>();
    private final CellType cellType;
    private final boolean ableToBuildOn;
    private final boolean ableToMoveOn;
    private int xPosition;
    private int yPosition;
    private Trap trap = null;
    private Building innerBuilding = null;
    private int speed;
    private Color showingColor;

    public Cell(CellType cellType) {
        this.cellType = cellType;
        this.showingColor = cellType.getShowingColor();
        this.speed = cellType.getSpeed();
        this.ableToBuildOn = cellType.isAbleToBuildOn();
        this.ableToMoveOn = cellType.isAbleToMoveOn();
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
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
        return innerBuilding;
    }

    public void setBuilding(Building building) {
        this.innerBuilding = building;
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

    public void makeBuilding(String buildingName, PlayerNumber ownerPlayerNumber) {
        int buildingGroupNumber = Building.getGroupNumberByBuildingName(buildingName);
        int x = this.getXPosition(), y = this.getYPosition();
        switch (buildingGroupNumber) {
            case 1 -> innerBuilding = new Accommodation
                    (AccommodationType.getTypeByBuildingName(buildingName), ownerPlayerNumber, x, y);
            case 2 -> innerBuilding = new AttackingBuilding
                    (AttackingBuildingType.getTypeByBuildingName(buildingName), ownerPlayerNumber, x, y);
            case 3 -> innerBuilding = new OtherBuildings
                    (OtherBuildingsType.getTypeByBuildingName(buildingName), ownerPlayerNumber, x, y);
            case 4 -> innerBuilding = new Processor
                    (ProcessorType.getTypeByBuildingName(buildingName), ownerPlayerNumber, x, y);
            case 5 -> innerBuilding = new ResourceExtracter
                    (ResourceExtracterType.getTypeByBuildingName(buildingName), ownerPlayerNumber, x, y);
            case 6 -> innerBuilding = new Service
                    (ServiceType.getTypeByBuildingName(buildingName), ownerPlayerNumber, x, y);
            case 7 -> innerBuilding = new Store
                    (StoreType.getTypeByBuildingName(buildingName), ownerPlayerNumber, x, y);
        }
    }

    enum Color {
        RED,
        GREEN,
        BLUE
    }
}
