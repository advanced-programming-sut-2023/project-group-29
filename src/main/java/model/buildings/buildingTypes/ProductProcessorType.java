package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.Category;
import model.dealing.Food;
import model.dealing.Product;
import model.dealing.Tradable;

public enum ProductProcessorType implements BuildType {
    MILL( //آسیاب
            new BuildingType(60, 3, new int[]{0, 0, 20, 0}, "mill",
                    "Mill_", Category.FOOD_PROCESSING), 10, Product.WHEAT, Product.FLOUR
    ),
    BAKERY( //نانوایی
            new BuildingType(40, 1, new int[]{0, 0, 10, 0}, "bakery",
                    "Bkery", Category.FOOD_PROCESSING), 10, Product.FLOUR, Food.BREAD
    ),
    BEER_BREWING(// آبجوسازی
            new BuildingType(40, 1, new int[]{0, 0, 10, 0}, "beerBrewing",
                    "BeerB", Category.FOOD_PROCESSING), 10, Product.GRAIN, Product.BEER
    ),
    INN( //مسافرخانه
            new BuildingType(60, 1, new int[]{100, 0, 20, 0}, "inn",
                    "Inn__", Category.FOOD_PROCESSING), 10, Product.BEER, null
    ),
    ;

    private final int rate;
    private final Product consumingProduct;
    private final Tradable producingTradable;
    private final BuildingType buildingType;
    private final int[] neededResources;


    ProductProcessorType(BuildingType buildingType, int rate,
                         Product consumingProduct, Tradable producingTradable) {
        this.rate = rate;
        this.consumingProduct = consumingProduct;
        this.producingTradable = producingTradable;
        this.buildingType = buildingType;
        this.neededResources = buildingType.neededResources();
        Building.addToValidBuildingNames(this, 5);
    }

    public static void enumBuilder() {
    }

    public static ProductProcessorType getTypeByBuildingName(String buildingName) {
        for (ProductProcessorType productProcessorType : ProductProcessorType.values()) {
            if (productProcessorType.getBuildingType().name().equals(buildingName)) return productProcessorType;
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

    public Tradable getProducingTradable() {
        return producingTradable;
    }

    public int getNeededResources(int i) {
        return neededResources[i];
    }
}
