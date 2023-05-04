package model.buildings;

import model.Asset;
import model.PlayerNumber;

import java.util.HashMap;

public abstract class Building extends Asset {
    private static final HashMap<String, Integer> buildingNamesAndTheirGroup = new HashMap<>();
    protected int numberOfWorkers;
    protected int[] neededResources;
    protected int maxHp;
    protected String name;
    //todo jasbi this field must be completed

    protected Building(BuildingType buildingType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(playerNumber, positionX, positionY);
        this.neededResources = buildingType.neededResources();
        this.hp = buildingType.hp();
        this.numberOfWorkers = buildingType.numberOfWorkers();
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

    public String getName() {
        return name;
    }
}
