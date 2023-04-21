package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.Resource;
import model.buildings.Building;
import model.buildings.buildingTypes.AttackingBuildingType;
import model.buildings.buildingTypes.ProcessorType;

public class Processor extends Building {
    private final ProcessorType processorType;
    private final int productionRate;
    private final Resource resource;


    public Processor(String buildingName, PlayerNumber playerNumber, int positionX, int positionY) {
        super(ProcessorType.getProcessorTypeByBuildingName(buildingName).getBuildingType()
                , playerNumber, positionX, positionY);
        this.processorType = ProcessorType.getProcessorTypeByBuildingName(buildingName);
        this.productionRate = processorType.getProductionRate();
        this.resource = processorType.getMaterial();
    }
}
