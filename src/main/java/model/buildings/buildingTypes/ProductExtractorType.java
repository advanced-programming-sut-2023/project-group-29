package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.dealing.Food;
import model.dealing.Product;
import model.dealing.Tradable;

public enum ProductExtractorType {
    STABLE(//اصطبل
            new BuildingType(100, 0, new int[]{400, 0, 20, 0},"Stble"),
            4, Product.HORSE,"stable"
    ),
    APPLE_GARDEN( //باغ سیب
            new BuildingType(30, 1, new int[]{0, 0, 5, 0},"ApGdn"),
            12, Food.APPLE, "appleGarden"
    ),
    DAIRY_PRODUCTS( //لبنیاتی
            new BuildingType(50, 1, new int[]{0, 0, 10, 0},"Dairy"),
            15,Food.CHEESE,"dairyProducts"
    ),
    GRAIN_FARM(// مزرعه جو
            new BuildingType(60, 1, new int[]{0, 0, 15, 0},"GFarm"),
            20,Product.GRAIN, "grainFarm"
    ),
    HUNTING_POST(// پست شکار
            new BuildingType(30, 1, new int[]{0, 0, 5, 0},"HPost"),
            10,Food.MEAT, "huntingPost"
    ),
    WHEAT_FARM( //مزرعه گندم
            new BuildingType(60, 1, new int[]{0, 0, 15, 0},"WFarm"),
            30, Product.WHEAT,"wheatFarm"
    ),
    ;
    private String name;
    private int rate;
    private Tradable producingTradable;
    private BuildingType buildingType;
    private int[] neededResources;

    ProductExtractorType(BuildingType buildingType, int rate,
                         Tradable producingTradable, String buildingName) {
        this.name = buildingName;
        this.rate = rate;
        this.producingTradable = producingTradable;
        this.buildingType = buildingType;
        this.neededResources = buildingType.neededResources();
        Building.addToValidBuildingNames(buildingName, 4);
    }

    public static void enumBuilder() {
    }

    public static ProductExtractorType getTypeByBuildingName(String buildingName) {
        for (ProductExtractorType productExtractorType : ProductExtractorType.values()) {
            if (productExtractorType.name.equals(buildingName)) return productExtractorType;
        }
        return null;
    }

    public int getRate() {
        return rate;
    }

    public Tradable getProducingTradable() {
        return producingTradable;
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
