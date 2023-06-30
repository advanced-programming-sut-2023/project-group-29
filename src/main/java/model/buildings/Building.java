package model.buildings;

import model.Asset;
import model.PlayerNumber;
import model.buildings.buildingTypes.BuildType;
import model.dealing.Resource;

import java.util.HashMap;

public abstract class Building extends Asset {

    private static final HashMap<BuildType, Integer> buildingTypesAndTheirGroup = new HashMap<>();
    protected int numberOfWorkers;
    protected int[] neededResources;
    protected int maxHp;
    protected String name;
    private final Category category;

    protected Building(BuildingType buildingType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(playerNumber, positionX, positionY);
        this.neededResources = buildingType.neededResources();
        this.hp = buildingType.hp();
        this.numberOfWorkers = buildingType.numberOfWorkers();
        this.maxHp = hp;
        this.category = buildingType.category();
        this.name = buildingType.name();
        this.showingImageFilePath = "/images/buildings/" + category.name() + "/" + name + ".png";
    }

    public static HashMap<BuildType, Integer> getBuildingTypesAndTheirGroup() {
        return buildingTypesAndTheirGroup;
    }

    public static BuildType getBuildTypeByName(String buildingName) {
        for (BuildType buildType : buildingTypesAndTheirGroup.keySet()) {
            if (buildType.getBuildingType().name().equals(buildingName)) return buildType;
        }
        return null;
    }

    public static void addToValidBuildingNames(BuildType buildType, int groupNumber) {
        buildingTypesAndTheirGroup.put(buildType, groupNumber);
    }

    public static boolean isBuildingNameValid(String buildingName) {
        if (buildingName.equals("wallWithStair") || buildingName.equals("ladder")) return false;
        for (BuildType buildType : buildingTypesAndTheirGroup.keySet()) {
            if (buildType.getBuildingType().name().equals(buildingName)) return true;
        }
        return false;
    }

    public static int getGroupNumberByBuildingName(String buildingName) {
        for (BuildType buildType : buildingTypesAndTheirGroup.keySet()) {
            if (buildType.getBuildingType().name().equals(buildingName)) {
                return buildingTypesAndTheirGroup.get(buildType);
            }
        }
        return 0;
    }

    public static int getNeededResource(int i, String buildingName) {
        for (BuildType buildType : buildingTypesAndTheirGroup.keySet()) {
            if (buildType.getBuildingType().name().equals(buildingName)) return buildType.getNeededResources(i);
        }
        return 0;
    }

    public static int getNumberOfWorkers(String buildingName) {
        for (BuildType buildType : buildingTypesAndTheirGroup.keySet()) {
            if (buildType.getBuildingType().name().equals(buildingName))
                return buildType.getBuildingType().numberOfWorkers();
        }
        return 0;
    }

    public int getNumberOfWorkers() {
        return numberOfWorkers;
    }

    public void repair() {
        this.getOwnerEmpire().changeTradableAmount(Resource.STONE, (maxHp - hp) / 10);
        hp = maxHp;
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }


    public abstract void setShowingSignInMap();

    public void update() {
    }
}
