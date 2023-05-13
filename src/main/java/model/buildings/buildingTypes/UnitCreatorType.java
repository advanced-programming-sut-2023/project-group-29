package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

public enum UnitCreatorType implements BuildType {
    BARRACK( //سربازخانه
            new BuildingType(50, 0, new int[]{0, 15, 0, 0},"Brrck"),
            50, "barrack"
    ),
    MERCENARY_POST( // سربازخانه مزدوران
            new BuildingType(30, 0, new int[]{0, 0, 10, 0},"MPost"),
            30, "mercenaryPost"
    ),
    ENGINEER_GUILD( // صنف مهندسان
            new BuildingType(30, 0, new int[]{100, 0, 10, 0},"EngnG"),
            60, "engineerGuild"
    ),
    CHURCH( // کلیسا
            new BuildingType(150, 0, new int[]{250, 0, 0, 0},"Chrch"),
            30,"church"
    ),
    CATHEDRAL( // کلیسای جامع
            new BuildingType(300, 0, new int[]{1000, 0, 0, 0},"Ctdrl"),
            30,"cathedral"
    ),
    ;
    private final String name;
    private final int unitCost;
    private final BuildingType buildingType;
    private final int[] neededResources;


    UnitCreatorType(BuildingType buildingType, int unitCost, String buildingName) {
        this.unitCost = unitCost;
        this.buildingType = buildingType;
        this.name = buildingName;
        this.neededResources = buildingType.neededResources();
        Building.addToValidBuildingNames(this, 9);
    }

    public static void enumBuilder() {
    }

    public static UnitCreatorType getTypeByBuildingName(String buildingName) {
        for (UnitCreatorType unitCreatorType : UnitCreatorType.values()) {
            if (unitCreatorType.name.equals(buildingName)) return unitCreatorType;
        }
        return null;
    }

    public int getUnitCost() {
        return unitCost;
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
