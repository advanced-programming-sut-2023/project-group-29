package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.dealing.Product;

public enum ProductProcessorType {
    MILL( //آسیاب
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 20, 0}),
            0, Product.WHEAT, Product.FLOUR, "mill"
    ),
    BAKERY( //نانوایی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 10, 0}),
            0, Product.FLOUR, Product.BREAD, "bakery"
    ),
    BEER_BREWING(// آبجوسازی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 10, 0}),
            0, Product.GRAIN, Product.BEER, "beerBrewing"
    ),
    ;

    private String name;
    private int rate;
    private Product consumingProduct;
    private Product producingProduct;
    private BuildingType buildingType;

    ProductProcessorType(BuildingType buildingType, int rate,
                         Product consumingProduct, Product producingProduct, String buildingName) {
        this.name = buildingName;
        this.rate = rate;
        this.consumingProduct = consumingProduct;
        this.producingProduct = producingProduct;
        this.buildingType = buildingType;
        this.name = buildingName;
        Building.addToValidBuildingNames(buildingName, 5);
    }

    public static void enumBuilder() {
    }

    public static ProductProcessorType getTypeByBuildingName(String buildingName) {
        for (ProductProcessorType productProcessorType : ProductProcessorType.values()) {
            if (productProcessorType.name.equals(buildingName)) return productProcessorType;
        }
        return null;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getRate() {
        return rate;
    }

    public Product getConsumingProduct() {
        return consumingProduct;
    }

    public Product getProducingProduct() {
        return producingProduct;
    }

    public String getName() {
        return name;
    }
}
