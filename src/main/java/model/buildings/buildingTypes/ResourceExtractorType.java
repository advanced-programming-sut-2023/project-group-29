package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

public enum ResourceExtractorType {
    PITCH_RIG( // دکل قیر
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 20, 0}),
            0, "pitchRig"
    ),
    OX_TETHER( // افسار گاو
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 5, 0}),
            0, "oxTether"
    ),
    QUARRY( // معدن سنگ
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 20, 0}),
            0, "quarry"
    ),
    WOOD_CUTTER( //چوب بر
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 3, 0}),
            0, "woodCutter"
    ),
    MILL( //آسیاب
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 20, 0}),
            0, "mill"
    ),
    IRON_MINE(//معدن آهن
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 20, 0}),
            0, "ironMine"
    ),
    APPLE_GARDEN( //باغ سیب
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 5, 0}),
            0, "appleGarden"
    ),
    DAIRY_PRODUCTS( //لبنیاتی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 10, 0}),
            0, "dairyProducts"
    ),
    GRAIN_FARM(// مزرعه جو
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 15, 0}),
            0, "grainFarm"
    ),
    HUNTING_POST(// پست شکار
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 5, 0}),
            0, "huntingPost"
    ),
    WHEAT_FARM( //مزرعه گندم
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 15, 0}),
            0, "wheatFarm"
    ),
    BAKERY( //نانوایی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 10, 0}),
            0, "bakery"
    ),
    BEER_BREWING(// آبجوسازی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 10, 0}),
            0, "beerBrewing"
    ),
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
    STABLE(//اصطبل
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{400, 0, 20, 0}),
            0, "stable"
    ),

    //کارخانه ذوب
    ;

    private final int rate;
    private String name;

    private final BuildingType buildingType;
    ResourceExtractorType(BuildingType buildingType, int rate, String buildingName) {
        this.rate = rate;
        this.buildingType = buildingType;
        Building.addToValidBuildingNames(buildingName, 5);
    }

    public static void enumBuilder() {
    }

    public static ResourceExtractorType getTypeByBuildingName(String buildingName) {
        for (ResourceExtractorType resourceExtractorType : ResourceExtractorType.values()) {
            if (resourceExtractorType.name.equals(buildingName)) return resourceExtractorType;
        }
        return null;
    }

    //todo jasbi name not assigned but used :/

    public int getRate() {
        return rate;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public String getName() {
        return name;
    }
}
