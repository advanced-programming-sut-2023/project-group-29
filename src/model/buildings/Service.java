package model.buildings;

public class Service extends Building {
    private final ServiceType serviceType;
    private final int popularityRange;
    private final int wineUsage;

    public Service(ServiceType serviceType) {
        super(serviceType.getBuildingType());
        this.serviceType = serviceType;
        this.popularityRange = serviceType.getWineUsage();
        this.wineUsage = serviceType.getWineUsage();
    }
}
