package model.buildings;

import model.Asset;
import model.PlayerNumber;
import model.buildings.buildingTypes.*;
import model.dealing.Resource;

import java.util.HashMap;

public abstract class Building extends Asset {

    //TODO JASBI: number of workers
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

    public static int getNeededResource(int i, String buildingName) {
        for (AccommodationType type: AccommodationType.values())
            if (type.getName().equals(buildingName)) return type.getNeededResources(i);
        for (AttackingBuildingType type: AttackingBuildingType.values())
            if (type.getName().equals(buildingName)) return type.getNeededResources(i);
        for (OtherBuildingsType type: OtherBuildingsType.values())
            if (type.getName().equals(buildingName)) return type.getNeededResources(i);
        for (ProductProcessorType type: ProductProcessorType.values())
            if (type.getName().equals(buildingName)) return type.getNeededResources(i);
        for (ProductExtractorType type: ProductExtractorType.values())
            if (type.getName().equals(buildingName)) return type.getNeededResources(i);
        for (ResourceExtractorType type: ResourceExtractorType.values())
            if (type.getName().equals(buildingName)) return type.getNeededResources(i);
        for (ResourceProcessorType type: ResourceProcessorType.values())
            if (type.getName().equals(buildingName)) return type.getNeededResources(i);
        for (StoreType type: StoreType.values())
            if (type.getName().equals(buildingName)) return type.getNeededResources(i);
        for (UnitCreatorType type: UnitCreatorType.values())
            if (type.getName().equals(buildingName)) return type.getNeededResources(i);
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
}
