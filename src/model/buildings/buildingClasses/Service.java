package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.ServiceType;

public class Service extends Building {
    private final ServiceType serviceType;
    private final int popularityRange;
    private final int wineUsage;

    public Service(ServiceType serviceType, PlayerNumber playerNumber) {
        super(serviceType.getBuildingType(), playerNumber);
        this.serviceType = serviceType;
        this.popularityRange = serviceType.getWineUsage();
        this.wineUsage = serviceType.getWineUsage();
    }
}
