package model.buildings;

import model.Asset;
import model.PlayerNumber;
import model.buildings.buildingTypes.*;
import model.dealing.Resource;

import java.util.HashMap;

public abstract class Building extends Asset {

    private static final HashMap<BuildType, Integer> buildingTypesAndTheirGroup = new HashMap<>();
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

    public int getNumberOfWorkers() {
        return numberOfWorkers;
    }

    public static void addToValidBuildingNames(BuildType buildType, int groupNumber) {
        buildingTypesAndTheirGroup.put(buildType, groupNumber);
    }

    public static boolean isBuildingNameValid(String buildingName) {
        if (buildingName.equals("wallWithStair") || buildingName.equals("ladder")) return false;
        for (BuildType buildType : buildingTypesAndTheirGroup.keySet()) {
            if (buildType.getName().equals(buildingName)) return true;
        }
        return false;
    }

    //todo faratin:     دکل قیر باید روی جلگه باشد.
    //TODO FARATIN:     معدن آهن فقط برروی زمین‌هایی از جنس آهن گذاشته می‌شود.
    //todo jasbi bad building on texture and vice versa
    public static int getGroupNumberByBuildingName(String buildingName) {
        for (BuildType buildType : buildingTypesAndTheirGroup.keySet()) {
            if (buildType.getName().equals(buildingName)) {
                return buildingTypesAndTheirGroup.get(buildType);
            }
        }
        return 0;
    }

    public static int getNeededResource(int i, String buildingName) {
       for (BuildType buildType : buildingTypesAndTheirGroup.keySet()) {
           if (buildType.getName().equals(buildingName)) return buildType.getNeededResources(i);
       }
       return 0;
    }
    public static int getNumberOfWorkers(String buildingName) {
        for (BuildType buildType : buildingTypesAndTheirGroup.keySet()) {
            if (buildType.getName().equals(buildingName)) return buildType.getBuildingType().numberOfWorkers();
        }
        return 0;
    }

    public void repair() {
        this.getOwnerEmpire().changeTradableAmount(Resource.STONE, (maxHp - hp)/10);
        hp = maxHp;
    }

    public abstract String getName();

    public int getMaxHp() {
        return maxHp;
    }


    public abstract void setShowingSignInMap();

    public void update() {}
}
