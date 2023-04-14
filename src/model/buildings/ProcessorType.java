package model.buildings;

import model.Material;

public enum ProcessorType {
    ARMOURER(0, null, null),
    BLACK_SMITH(0, null, null),
    FLETCHER(0, null, null),
    POLETURNER(0, null, null),
    ;

    private final int productionRate;
    private final Material material;
    private final BuildingType buildingType;

    ProcessorType(int productionRate, Material material, BuildingType buildingType) {
        this.productionRate = productionRate;
        this.material = material;
        this.buildingType = buildingType;
    }

    public int getProductionRate() {
        return productionRate;
    }

    public Material getMaterial() {
        return material;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }
}
