package model.map;

import controller.menucontrollers.MapFunctions;
import model.Asset;
import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingClasses.*;
import model.buildings.buildingTypes.*;
import model.people.humanClasses.Soldier;
import model.people.humanTypes.SoldierType;
import model.unitfeatures.HeightOfAsset;
import model.unitfeatures.Movable;
import model.unitfeatures.Offensive;
import model.weapons.weaponClasses.Equipments;
import model.weapons.weaponClasses.Trap;
import model.weapons.weaponTypes.EquipmentsType;

import java.util.ArrayList;


public class Cell {
    private final ArrayList<Asset> movingObjects = new ArrayList<>();
    private final int xPosition;
    private final int yPosition;
    private TreeType treeTypes;
    private Trap trap = null;
    private Building building = null;
    private CellType cellType;
    private boolean hasTunnel = false;
    private boolean sick = false;
    private int fireRemainingTurns = 0;

    public Cell(CellType cellType, int xPosition, int yPosition) {
        this.cellType = cellType;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public boolean isSick() {
        return sick;
    }

    public void setSick(boolean sick) {
        this.sick = sick;
    }

    public void removeDeadUnitsAndBuilding() {
        movingObjects.removeIf(asset -> asset.isDead());
        if (trap != null && trap.isDead())
            trap = null;
        if (building != null && building.isDead())
            building = null;
    }

    public HeightOfAsset heightOfUnitsOfPlayer() {
        if (hasBuilding()) {
            if (building instanceof AttackingBuilding)
                return HeightOfAsset.UP;

            if (getSiegeTowerInMovingUnits() != null)
                return HeightOfAsset.UP;

            if (building instanceof OtherBuildings otherBuildings) {
                OtherBuildingsType type = otherBuildings.getOtherBuildingsType();
                if (type.equals(OtherBuildingsType.SHORT_WALL))
                    return HeightOfAsset.MIDDLE;
                if (type.equals(OtherBuildingsType.TALL_WALL))
                    return HeightOfAsset.UP;
                if (type.equals(OtherBuildingsType.WALL_WITH_STAIR))
                    return HeightOfAsset.MIDDLE;
            }
        }

        return HeightOfAsset.GROUND;
    }

    public LevelChanger goHigherLevel() {
        if (!hasBuilding())
            return LevelChanger.NON;

        if (building instanceof OtherBuildings otherBuilding) {
            OtherBuildingsType type = otherBuilding.getOtherBuildingsType();
            if (type.equals(OtherBuildingsType.LADDER))
                return LevelChanger.LADDER;
            if (type.equals(OtherBuildingsType.STAIR) || type.equals(OtherBuildingsType.WALL_WITH_STAIR))
                return LevelChanger.STAIRS;
        }
        return LevelChanger.NON;
    }

    public ArrayList<Asset> getMovingObjectsOfPlayer(PlayerNumber playerNumber) {
        ArrayList<Asset> assets = new ArrayList<>();
        for (Asset movingObject : movingObjects) {
            if (movingObject.getOwnerNumber().equals(playerNumber))
                assets.add(movingObject);
        }

        return assets;
    }

    public ArrayList<Movable> getMovablesOfPlayer(PlayerNumber playerNumber) {
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
            if (movingObject instanceof Soldier soldier &&
                    soldier.getSoldierType().equals(SoldierType.ENGINEER_WITH_OIL))
                continue;
            if (movingObject.getOwnerNumber().equals(playerNumber) && movingObject instanceof Offensive)
                attackingObjects.add((Offensive) movingObject);
        }

        return attackingObjects;
    }

    public boolean hasBuilding() {
        return building != null;
    }

    public boolean hasTrap() {
        return trap != null;
    }

    public boolean shieldExistsInCell(PlayerNumber playerNumber) {
        for (Asset movingObject : movingObjects) {
            if (movingObject instanceof Equipments equipment && movingObject.getOwnerNumber().equals(playerNumber)) {
                if (equipment.getEquipmentsType().equals(EquipmentsType.PORTABLE_SHIELD))
                    return true;
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

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
        MapFunctions.refreshMiniMap(this);
    }

    public boolean isAbleToMoveOn() {
        return cellType.isAbleToMoveOn();
    }

    public void makeBuilding(String buildingName, PlayerNumber ownerPlayerNumber) {
        int buildingGroupNumber = Building.getGroupNumberByBuildingName(buildingName);
        int x = this.getXPosition(), y = this.getYPosition();
        //wall with stair:
        if (buildingName.equals("stair") && this.hasBuilding()) {
            building = new OtherBuildings(OtherBuildingsType.WALL_WITH_STAIR, ownerPlayerNumber, x, y);
            return;
        }
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
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void addMovingObject(Asset asset) {
        movingObjects.add(asset);
    }

    public int getNumberOfStrangeUnits(PlayerNumber myPlayerNumber) {
        int count = 0;
        for (Asset moving : movingObjects) {
            if (!moving.getOwnerNumber().equals(myPlayerNumber)) {
                count++;
            }
        }
        return count;
    }

    public boolean hasTunnel() {
        return hasTunnel;
    }

    public void setHasTunnel(boolean hasTunnel) {
        this.hasTunnel = hasTunnel;
    }

    public Equipments getSiegeTowerInMovingUnits() {
        for (Asset asset : movingObjects) {
            if (asset instanceof Equipments equipment) {
                if (equipment.getEquipmentsType().equals(EquipmentsType.SIEGE_TOWER))
                    return equipment;
            }
        }
        return null;
    }

    public void setTree(TreeType treeType) {
        this.treeTypes = treeType;
    }

    public TreeType getTreeTypes() {
        return treeTypes;
    }

    public int getFireRemainingTurns() {
        return fireRemainingTurns;
    }

    public void setFireRemainingTurns(int fireRemainingTurns) {
        this.fireRemainingTurns = fireRemainingTurns;
    }

    public void decrementFireRemainingTurns() {
        if (fireRemainingTurns > 0)
            fireRemainingTurns--;
    }

    public enum LevelChanger {
        LADDER,
        STAIRS,
        NON
    }
}

