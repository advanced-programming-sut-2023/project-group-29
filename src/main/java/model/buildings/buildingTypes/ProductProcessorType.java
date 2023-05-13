package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.dealing.Food;
import model.dealing.Product;
import model.dealing.Tradable;

public enum ProductProcessorType implements BuildType {
    MILL( //آسیاب
            new BuildingType(60, 3, new int[]{0, 0, 20, 0},"Mill_"),
            10, Product.WHEAT, Product.FLOUR, "mill"
    ),
    BAKERY( //نانوایی
            new BuildingType(40, 1, new int[]{0, 0, 10, 0},"Bkery"),
            10, Product.FLOUR, Food.BREAD, "bakery"
    ),
    BEER_BREWING(// آبجوسازی
            new BuildingType(40, 1, new int[]{0, 0, 10, 0},"BeerB"),
            10, Product.GRAIN, Product.BEER, "beerBrewing"
    ),
    INN( //مسافرخانه
            new BuildingType(60, 1, new int[]{100, 0, 20, 0},"Inn__"),
            10, Product.BEER, null,"inn"
    ),
    ;

    private final String name;
    private final int rate;
    private final Product consumingProduct;
    private final Tradable producingTradable;
    private final BuildingType buildingType;
    private final int[] neededResources;


    ProductProcessorType(BuildingType buildingType, int rate,
                         Product consumingProduct, Tradable producingTradable, String buildingName) {
        this.rate = rate;
        this.consumingProduct = consumingProduct;
        this.producingTradable = producingTradable;
        this.buildingType = buildingType;
        this.name = buildingName;
        this.neededResources = buildingType.neededResources();
        Building.addToValidBuildingNames(this, 5);
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

    public Tradable getProducingTradable() {
        return producingTradable;
    }

    public String getName() {
        return name;
    }

    public int getNeededResources(int i) {
        return neededResources[i];
    }
}
