package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.Category;
import model.dealing.Product;
import model.dealing.Resource;

public enum ResourceProcessorType implements BuildType {
    ARMOURER( // زره سازی
            new BuildingType(80, 1, new int[]{100, 0, 20, 0},
                    "Armourer", "Armrr", Category.WEAPON), 10, Resource.IRON, Product.ARMOUR
    ),
    BLACK_SMITH( // ساختمان آهنگری
            new BuildingType(80, 1, new int[]{100, 0, 20, 0},
                    "blackSmith", "BSmth", Category.WEAPON), 10, Resource.IRON, Product.SWORD
    ),
    FLETCHER( // کمان سازی
            new BuildingType(80, 1, new int[]{100, 0, 20, 0},
                    "fletcher", "Fltch", Category.WEAPON), 10, Resource.WOOD, Product.BOW
    ),
    POLETURNER( // نیزه سازی
            new BuildingType(80, 1, new int[]{100, 0, 10, 0},
                    "poleturner", "Ptrnr", Category.WEAPON), 10, Resource.WOOD, Product.PIKE
    ),
    ;

    private final int rate;
    private final Resource resource;
    private final Product product;
    private final BuildingType buildingType;
    private final int[] neededResources;


    ResourceProcessorType(BuildingType buildingType, int rate, Resource resource, Product product) {
        this.rate = rate;
        this.resource = resource;
        this.buildingType = buildingType;
        this.product = product;
        this.neededResources = buildingType.neededResources();
        Building.addToValidBuildingNames(this, 7);
    }

    public static void enumBuilder() {
    }

    public static ResourceProcessorType getTypeByBuildingName(String buildingName) {
        for (ResourceProcessorType processorType : ResourceProcessorType.values()) {
            if (processorType.getBuildingType().name().equals(buildingName)) return processorType;
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

    public int getNeededResources(int i) {
        return neededResources[i];
    }
}
