package model.buildings;

import model.Asset;
import model.PlayerNumber;
import model.dealing.Resource;

import java.util.HashMap;

public abstract class Building extends Asset {

    //TODO: تعیین قیمت برای ساختمان های رایگان
    private static final HashMap<String, Integer> buildingNamesAndTheirGroup = new HashMap<>();
    protected int numberOfWorkers;
    protected int[] neededResources;
    protected int maxHp;

    protected Building(BuildingType buildingType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(playerNumber, positionX, positionY);
        this.neededResources = buildingType.neededResources();
        this.hp = buildingType.hp();
        this.numberOfWorkers = buildingType.numberOfWorkers();
        this.maxHp = hp;
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
        ownerEmpire.changeResourceAmount(Resource.STONE, maxHp - hp);
        hp = maxHp;
    }

    public abstract String getName();

    public int getMaxHp() {
        return maxHp;
    }

    public int getNeededResource(int i) {
        return neededResources[i];
    }
}
