package model.buildings;

import model.Material;

public class Processor extends Building {
    private final ProcessorType processorType;
    private final int productionRate;
    private final Material material;


    public Processor(ProcessorType processorType) {
        super(processorType.getBuildingType());
        this.processorType = processorType;
        this.productionRate = processorType.getProductionRate();
        this.material = processorType.getMaterial();
    }
}
