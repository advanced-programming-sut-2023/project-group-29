package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.dealing.Product;
import model.dealing.Resource;

public enum ResourceProcessorType {
    ARMOURER( // زره سازی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{100, 0, 20, 0}),
            0, Resource.IRON, Product.ARMOUR, "Armourer"
    ),
    BLACK_SMITH( // ساختمان آهنگری
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{100, 0, 20, 0}),
            0, Resource.IRON, Product.SWORD, "blackSmith"
    ),
    FLETCHER( // کمان سازی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{100, 0, 20, 0}),
            0, Resource.WOOD, Product.BOW, "fletcher"
    ),
    POLETURNER( // نیزه سازی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{100, 0, 10, 0}),
            0, Resource.WOOD, Product.PIKE, "poleturner"
    ),
    ;

    private final int rate;
    private final Resource resource;
    private Product product;
    private final BuildingType buildingType;
    private String name;


    ResourceProcessorType(BuildingType buildingType, int rate,
                          Resource resource, Product product, String buildingName) {
        this.rate = rate;
        this.resource = resource;
        this.buildingType = buildingType;
        this.product = product;
        this.name = buildingName;
        Building.addToValidBuildingNames(buildingName, 7);
    }

    public static void enumBuilder() {
    }

    public static ResourceProcessorType getTypeByBuildingName(String buildingName) {
        for (ResourceProcessorType processorType : ResourceProcessorType.values()) {
            if (processorType.name.equals(buildingName)) return processorType;
        }
        return null;
    }

    public Resource getResource() {
        return resource;
    }

    public Product getProduct() {
        return product;
    }

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
