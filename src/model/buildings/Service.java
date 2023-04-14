package model.buildings;

public class Service extends Building {
    private ServiceType accommodationType;
    private int popularityRange;
    private int wineUsage;

    public Service(ServiceType accommodationType, int popularityRange, int wineUsage) {
        this.accommodationType = accommodationType;
        this.popularityRange = popularityRange;
        this.wineUsage = wineUsage;
    }
}
