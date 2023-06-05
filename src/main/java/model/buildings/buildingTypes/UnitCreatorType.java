package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.Category;

public enum UnitCreatorType implements BuildType {
    BARRACK( //سربازخانه
            new BuildingType(50, 0, new int[]{0, 15, 0, 0},
                    "barrack", "Brrck", Category.CASTLE), 50
    ),
    MERCENARY_POST( // سربازخانه مزدوران
            new BuildingType(30, 0, new int[]{0, 0, 10, 0},
                    "mercenaryPost", "MPost", Category.CASTLE), 30
    ),
    ENGINEER_GUILD( // صنف مهندسان
            new BuildingType(30, 0, new int[]{100, 0, 10, 0},
                    "engineerGuild", "EngnG", Category.CASTLE), 60
    ),
    CHURCH( // کلیسا
            new BuildingType(150, 0, new int[]{250, 0, 0, 0},
                    "church", "Chrch", Category.TOWN), 30
    ),
    CATHEDRAL( // کلیسای جامع
            new BuildingType(300, 0, new int[]{1000, 0, 0, 0},
                    "cathedral", "Ctdrl", Category.TOWN), 30
    ),
    ;
    private final int unitCost;
    private final BuildingType buildingType;
    private final int[] neededResources;


    UnitCreatorType(BuildingType buildingType, int unitCost) {
        this.unitCost = unitCost;
        this.buildingType = buildingType;
        this.neededResources = buildingType.neededResources();
        Building.addToValidBuildingNames(this, 9);
    }

    public static void enumBuilder() {
    }

    public static UnitCreatorType getTypeByBuildingName(String buildingName) {
        for (UnitCreatorType unitCreatorType : UnitCreatorType.values()) {
            if (unitCreatorType.getBuildingType().name().equals(buildingName)) return unitCreatorType;
        }
        return null;
    }

    public int getUnitCost() {
        return unitCost;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getNeededResources(int i) {
        return neededResources[i];
    }
}
