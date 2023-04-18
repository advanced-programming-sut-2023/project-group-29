package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.Resource;
import model.buildings.Building;
import model.buildings.buildingTypes.ProcessorType;

public class Processor extends Building {
    private final ProcessorType processorType;
    private final int productionRate;
    private final Resource resource;


    public Processor(ProcessorType processorType, PlayerNumber playerNumber,int positionX,int positionY) {
        super(processorType.getBuildingType(), playerNumber, positionX, positionY);
        this.processorType = processorType;
        this.productionRate = processorType.getProductionRate();
        this.resource = processorType.getMaterial();
    }
}
