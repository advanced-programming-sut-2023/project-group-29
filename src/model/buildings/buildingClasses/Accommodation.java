package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.AccommodationType;

public class Accommodation extends Building {
    private final AccommodationType accommodationType;
    private final int numberOfSettler;

    public Accommodation(String buildingName, PlayerNumber playerNumber, int positionX, int positionY) {
        super(AccommodationType.getAccommodationTypeByBuildingName(buildingName).getBuildingType()
                , playerNumber, positionX, positionY);

        this.accommodationType = AccommodationType.getAccommodationTypeByBuildingName(buildingName);
        this.numberOfSettler = accommodationType.getNumberOfSettler();
    }
}
