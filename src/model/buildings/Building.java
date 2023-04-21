package model.buildings;

import model.Asset;
import model.PlayerNumber;

import java.util.HashMap;

public class Building extends Asset {
    private static final HashMap<String, Integer> buildingNamesAndTheirGroup = new HashMap<>();
    protected int hp;
    protected int numberOfWorkers;
    protected int cost;
    protected int maxHp;

    protected Building(BuildingType buildingType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(playerNumber, positionX, positionY);
        this.cost = buildingType.getCost();
        this.hp = buildingType.getHp();
        this.numberOfWorkers = buildingType.getNumberOfWorkers();
        maxHp = hp;
    }

    public static void addToValidBuildingNames(String buildingName, int groupNumber) {
        buildingNamesAndTheirGroup.put(buildingName, groupNumber);
    }

    public static boolean isBuildingNameValid(String buildingName) {
        for (String name : buildingNamesAndTheirGroup.keySet()) {
            if (name.equals(buildingName)) return true;
        }
        return false;
    }

    public static int getGroupNumberByBuildingName(String buildingName) {
        return buildingNamesAndTheirGroup.get(buildingName);
    }

    public boolean isEnemyNearIt() {
        //TODO: complete
        return false;
    }

    public void repair() {
        ownerEmpire.decreaseStone(maxHp - hp);
        hp = maxHp;
    }
}
