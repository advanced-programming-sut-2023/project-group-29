package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.dealing.Product;

public enum ProductExtractorType {
    STABLE(//اصطبل
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{400, 0, 20, 0}),
            0, Product.HORSE,"stable"
    ),
    APPLE_GARDEN( //باغ سیب
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 5, 0}),
            0, Product.APPLE, "appleGarden"
    ),
    DAIRY_PRODUCTS( //لبنیاتی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 10, 0}),
            0,Product.CHEESE,"dairyProducts"
    ),
    GRAIN_FARM(// مزرعه جو
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 15, 0}),
            0,Product.GRAIN, "grainFarm"
    ),
    HUNTING_POST(// پست شکار
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 5, 0}),
            0,Product.MEAT, "huntingPost"
    ),
    WHEAT_FARM( //مزرعه گندم
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 15, 0}),
            0, Product.WHEAT,"wheatFarm"
    ),
    ;
    private String name;
    private int rate;
    private Product producingProduct;
    private BuildingType buildingType;

    ProductExtractorType(BuildingType buildingType, int rate,
                         Product producingProduct, String buildingName) {
        this.name = buildingName;
        this.rate = rate;
        this.producingProduct = producingProduct;
        this.buildingType = buildingType;
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

    public Product getProducingProduct() {
        return producingProduct;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public String getName() {
        return name;
    }
}
