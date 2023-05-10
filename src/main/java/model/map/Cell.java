package model.map;

import controller.menucontrollers.GameMenuController;
import model.*;
import model.buildings.Building;
import model.buildings.buildingClasses.*;
import model.buildings.buildingTypes.*;
import model.weapons.weaponClasses.Equipments;
import model.weapons.weaponClasses.OffensiveWeapons;
import model.weapons.weaponClasses.Trap;
import model.weapons.weaponTypes.EquipmentsType;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;


public class Cell {
    private final ArrayList<Asset> movingObjects = new ArrayList<>();
    private Trap trap = null;
    private Building building = null;
    //    private int speed;
    private final CellType cellType;
    private boolean hasTunnel=false;

    //    private boolean ableToBuildOn;
//    private boolean ableToMoveOn;
    public Cell(CellType cellType) {
        this.cellType = cellType;
//        this.speed=cellType.getSpeed();
//        this.ableToBuildOn=cellType.isAbleToBuildOn();
//        this.ableToMoveOn= cellType.isAbleToMoveOn();
    }

    public void removeDeadUnitsAndBuilding() {
        movingObjects.removeIf(asset -> asset.isDead());
        if (trap != null && trap.isDead())
            trap = null;
        if (building != null && building.isDead())
            building = null;
    }

    public HeightOfAsset heightOfUnitsOfPlayer(PlayerNumber playerNumber)
    {
        //todo define height enum for buildings
        return null;
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
        return building!=null;
    }

    public boolean hasTrap() {
        return trap != null;
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

//    public int getSpeed() {
//        return speed;
//    }
//
//    public void setSpeed(int speed) {
//        this.speed = speed;
//    }

    public CellType getCellType() {
        return cellType;
    }

    public boolean isAbleToBuildOn() {
        return cellType.isAbleToBuildOn();
    }

    public boolean isAbleToBuildOn(String type) {
        //TODO: complete this function
        return isAbleToBuildOn();
    }

    public boolean isAbleToMoveOn() {
        return cellType.isAbleToMoveOn();
    }

    public void makeBuilding(String buildingName, PlayerNumber ownerPlayerNumber) {
        int buildingGroupNumber = Building.getGroupNumberByBuildingName(buildingName);
        int x = this.getXPosition(), y = this.getYPosition();
        switch (buildingGroupNumber) {
            case 1 -> building = new Accommodation
                    (AccommodationType.getTypeByBuildingName(buildingName), ownerPlayerNumber, x, y);
            case 2 -> building = new AttackingBuilding
                    (AttackingBuildingType.getTypeByBuildingName(buildingName), ownerPlayerNumber, x, y);
            case 3 -> building = new OtherBuildings
                    (OtherBuildingsType.getTypeByBuildingName(buildingName), ownerPlayerNumber, x, y);
            case 4 -> building = new ProductExtractor
                    (ProductExtractorType.getTypeByBuildingName(buildingName), ownerPlayerNumber, x, y);
            case 5 -> building = new ProductProcessor
                    (ProductProcessorType.getTypeByBuildingName(buildingName), ownerPlayerNumber, x, y);
            case 6 -> building = new ResourceExtractor
                    (ResourceExtractorType.getTypeByBuildingName(buildingName), ownerPlayerNumber, x, y);
            case 7 -> building = new ResourceProcessor
                    (ResourceProcessorType.getTypeByBuildingName(buildingName), ownerPlayerNumber, x, y);
            case 8 -> building = new Store
                    (StoreType.getTypeByBuildingName(buildingName), ownerPlayerNumber, x, y);
            case 9 -> building = new UnitCreator
                    (UnitCreatorType.getTypeByBuildingName(buildingName), ownerPlayerNumber, x, y);
        }
        Empire empire = GameMenuController.getGameData().getEmpireByPlayerNumber(ownerPlayerNumber);
        empire.addBuilding(building, buildingGroupNumber);
    }

    //TODO complete two functions below
    private int getXPosition() {
        return 1;
    }

    private int getYPosition() {
        return 1;
    }

    public ConsoleColors getShowingColor() {
        return cellType.getShowingColor();
    }
    public void addMovingObject(Asset asset)
    {
        movingObjects.add(asset);
    }

    public boolean hasTunnel() {
        return hasTunnel;
    }

    public void setHasTunnel(boolean hasTunnel) {
        this.hasTunnel = hasTunnel;
    }

    public Equipments getSiegeTowerInMovingUnits()
    {
        for(Asset asset:movingObjects)
        {
            if(asset instanceof Equipments equipment)
            {
                if(equipment.getEquipmentsType().equals(EquipmentsType.SIEGE_TOWER))
                    return equipment;
            }
        }
        return null;
    }
}

