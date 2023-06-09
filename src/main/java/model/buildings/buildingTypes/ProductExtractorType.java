package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.Category;
import model.dealing.Food;
import model.dealing.Product;
import model.dealing.Tradable;

public enum ProductExtractorType implements BuildType {
    STABLE(//اصطبل
            new BuildingType(100, 0, new int[]{400, 0, 20, 0},
                    "stable", "Stble", Category.CASTLE),
            4, Product.HORSE
    ),
    APPLE_GARDEN( //باغ سیب
            new BuildingType(30, 1, new int[]{0, 0, 5, 0},
                    "appleGarden", "ApGdn", Category.FARM), 12, Food.APPLE
    ),
    DAIRY_PRODUCTS( //لبنیاتی
            new BuildingType(50, 1, new int[]{0, 0, 10, 0},
                    "dairyProducts", "Dairy", Category.FARM), 15, Food.CHEESE
    ),
    GRAIN_FARM(// مزرعه جو
            new BuildingType(60, 1, new int[]{0, 0, 15, 0},
                    "grainFarm", "GFarm", Category.FARM), 20, Product.GRAIN
    ),
    HUNTING_POST(// پست شکار
            new BuildingType(30, 1, new int[]{0, 0, 5, 0},
                    "huntingPost", "HPost", Category.FARM), 10, Food.MEAT
    ),
    WHEAT_FARM( //مزرعه گندم
            new BuildingType(60, 1, new int[]{0, 0, 15, 0},
                    "wheatFarm", "WFarm", Category.FARM), 30, Product.WHEAT
    ),
    ;
    private final int rate;
    private final Tradable producingTradable;
    private final BuildingType buildingType;
    private final int[] neededResources;

    ProductExtractorType(BuildingType buildingType, int rate, Tradable producingTradable) {
        this.rate = rate;
        this.producingTradable = producingTradable;
        this.buildingType = buildingType;
        this.neededResources = buildingType.neededResources();
        Building.addToValidBuildingNames(this, 4);
    }

    public static void enumBuilder() {
    }

    public static ProductExtractorType getTypeByBuildingName(String buildingName) {
        for (ProductExtractorType productExtractorType : ProductExtractorType.values()) {
            if (productExtractorType.getBuildingType().name().equals(buildingName)) return productExtractorType;
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

    public int getNeededResources(int i) {
        return neededResources[i];
    }
}
