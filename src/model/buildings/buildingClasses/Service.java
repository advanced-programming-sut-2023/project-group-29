package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.ServiceType;

public class Service extends Building {
    private final ServiceType serviceType;
    private final int popularityRange;
    private final int wineUsage;

    public Service(String buildingName, PlayerNumber playerNumber, int positionX, int positionY) {
        super(ServiceType.getServiceTypeByBuildingName(buildingName).getBuildingType(), playerNumber, positionX, positionY);
        this.serviceType = ServiceType.getServiceTypeByBuildingName(buildingName);
        this.popularityRange = serviceType.getWineUsage();
        this.wineUsage = serviceType.getWineUsage();
    }
}
