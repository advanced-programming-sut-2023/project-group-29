package model;

import model.buildings.Building;
import model.weapons.weaponClasses.Equipments;
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

    public void removeDeadUnits() {
        movingObjects.removeIf(asset -> asset.isDead());
        if (trap != null && trap.isDead())
            trap = null;
        if (building != null && building.isDead())
            building = null;
    }

    public ArrayList<Movable> getMovingObjectsOfPlayer(PlayerNumber playerNumber) {
        ArrayList<Movable> movables = new ArrayList<>();
        for (Asset movingObject : movingObjects) {
            if (movingObject.getOwnerNumber().equals(playerNumber))
                movables.add((Movable) movingObject);
        }

        return movables;
    }

    public ArrayList<Asset> getEnemiesOfPlayerInCell(PlayerNumber playerNumber) {
        ArrayList<Asset> enemies = new ArrayList<>();
        for (Asset movingObject : movingObjects) {
            if (!movingObject.getOwnerNumber().equals(playerNumber))
                enemies.add(movingObject);
        }

        return enemies;
    }

    public ArrayList<Offensive> getAttackingListOfPlayerNumber(PlayerNumber playerNumber) {
        ArrayList<Offensive> attackingObjects = new ArrayList<>();
        for (Asset movingObject : movingObjects) {
            if (movingObject.getOwnerNumber().equals(playerNumber) && movingObject instanceof Offensive)
                attackingObjects.add((Offensive) movingObject);
        }

        return attackingObjects;
    }

    public boolean hasBuilding() {
        return !building.equals(null);
    }

    public boolean hasTrap() {
        return !trap.equals(null);
    }

    public boolean hasFiringTrap() {
        //TODO has firing trap
        return true;
    }

    public boolean shieldExistsInCell(PlayerNumber playerNumber) {
        for (Asset movingObject : movingObjects) {
            if (movingObject instanceof Equipments equipment && movingObject.getOwnerNumber().equals(playerNumber)) {

                //TODO complete if below for type portable shield
                //if(equipment.getEquipmentsType().equals())
                //return true
            }
        }

        return false;
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

    public boolean isAbleToBuildOn() {
        return ableToBuildOn;
    }

    public boolean isAbleToMoveOn() {
        return ableToMoveOn;
    }

    enum Color {
        RED,
        GREEN,
        BLUE
    }
}
