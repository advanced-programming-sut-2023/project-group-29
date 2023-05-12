package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

public enum StoreType implements BuildType {
    ARMOURY( //اسلحه خانه
            new BuildingType(60, 0, new int[]{0, 0, 5, 0},"Armry"),
            100, "armoury"
    ),
    FOOD_STORE( //انبار غذا
            new BuildingType(60, 0, new int[]{0, 0, 5, 0},"FStor"),
            200, "foodStore"
    ),
    STOCK_PILE( //انبار
            new BuildingType(60, 0, new int[]{0, 0, 5, 0},"SPile"),
            500, "stockPile"
    ),
    ;
    private final int capacity;
    private final BuildingType buildingType;
    private String name;
    private int[] neededResources;


    StoreType(BuildingType buildingType, int capacity, String buildingName) {
        this.capacity = capacity;
        this.buildingType = buildingType;
        this.name = buildingName;
        this.neededResources = buildingType.neededResources();
        Building.addToValidBuildingNames(this, 8);
    }

    public static void enumBuilder() {
    }

    public static StoreType getTypeByBuildingName(String buildingName) {
        for (StoreType storeType : StoreType.values()) {
            if (storeType.name.equals(buildingName)) return storeType;
        }
        return null;
    }

    public int getCapacity() {
        return capacity;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public String getName() {
        return name;
    }

    public int getNeededResources(int i) {
        return neededResources[i];
    }
}
