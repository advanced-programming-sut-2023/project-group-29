package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

public enum UnitCreatorType {
    BARRACK( //سربازخانه
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 15, 0, 0}),
            0, "barrack"
    ),
    MERCENARY_POST( // سربازخانه مزدوران
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 10, 0}),
            0, "mercenaryPost"
    ),
    ENGINEER_GUILD( // صنف مهندسان
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{100, 0, 10, 0}),
            0, "engineerGuild"
    ),
    ;
    private String name;
    private int unitCost;
    private BuildingType buildingType;

    UnitCreatorType(BuildingType buildingType, int unitCost, String buildingName) {
        this.name = buildingName;
        this.unitCost = unitCost;
        this.buildingType = buildingType;
        this.name = buildingName;
        Building.addToValidBuildingNames(buildingName, 9);
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
}
