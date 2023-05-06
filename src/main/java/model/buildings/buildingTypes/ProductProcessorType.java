package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.dealing.Product;

public enum ProductProcessorType {
    MILL( //آسیاب
            new BuildingType(60, 3, new int[]{0, 0, 20, 0},"Mill_"),
            0, Product.WHEAT, Product.FLOUR, "mill"
    ),
    BAKERY( //نانوایی
            new BuildingType(40, 1, new int[]{0, 0, 10, 0},"Bkery"),
            0, Product.FLOUR, Product.BREAD, "bakery"
    ),
    BEER_BREWING(// آبجوسازی
            new BuildingType(40, 1, new int[]{0, 0, 10, 0},"BeerB"),
            0, Product.GRAIN, Product.BEER, "beerBrewing"
    ),
    ;

    private String name;
    private int rate;
    private Product consumingProduct;
    private Product producingProduct;
    private BuildingType buildingType;
    private int[] neededResources;


    ProductProcessorType(BuildingType buildingType, int rate,
                         Product consumingProduct, Product producingProduct, String buildingName) {
        this.name = buildingName;
        this.rate = rate;
        this.consumingProduct = consumingProduct;
        this.producingProduct = producingProduct;
        this.buildingType = buildingType;
        this.name = buildingName;
        this.neededResources = buildingType.neededResources();
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

    public int getNeededResources(int i) {
        return neededResources[i];
    }
}
