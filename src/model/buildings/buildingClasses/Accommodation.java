package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.AccommodationType;

public class Accommodation extends Building {
    private final AccommodationType accommodationType;
    private final int numberOfSettler;

    public Accommodation(AccommodationType accommodationType, PlayerNumber playerNumber) {
        super(accommodationType.getBuildingType(), playerNumber);

        this.accommodationType = accommodationType;
        this.numberOfSettler = accommodationType.getNumberOfSettler();
    }
}
