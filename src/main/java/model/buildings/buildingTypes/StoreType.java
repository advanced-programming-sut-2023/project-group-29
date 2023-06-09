package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.Category;

public enum StoreType implements BuildType {
    ARMOURY( //اسلحه خانه
            new BuildingType(60, 0, new int[]{0, 0, 5, 0},
                    "armoury", "Armry", Category.CASTLE), 100
    ),
    FOOD_STORE( //انبار غذا
            new BuildingType(60, 0, new int[]{0, 0, 5, 0},
                    "foodStore", "FStor", Category.FOOD_PROCESSING), 200
    ),
    STOCK_PILE( //انبار
            new BuildingType(60, 0, new int[]{0, 0, 5, 0},
                    "stockPile", "SPile", Category.INDUSTRY), 500
    ),
    ;
    private final int capacity;
    private final BuildingType buildingType;
    private final int[] neededResources;


    StoreType(BuildingType buildingType, int capacity) {
        this.capacity = capacity;
        this.buildingType = buildingType;
        this.neededResources = buildingType.neededResources();
        Building.addToValidBuildingNames(this, 8);
    }

    public static void enumBuilder() {
    }

    public static StoreType getTypeByBuildingName(String buildingName) {
        for (StoreType storeType : StoreType.values()) {
            if (storeType.getBuildingType().name().equals(buildingName)) return storeType;
        }
        return null;
    }

    public int getCapacity() {
        return capacity;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getNeededResources(int i) {
        return neededResources[i];
    }
}
