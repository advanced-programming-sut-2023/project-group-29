package model.buildings.buildingClasses;

import model.Empire;
import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.AccommodationType;

public class Accommodation extends Building {
    private final AccommodationType accommodationType;
    private final int numberOfSettler;

    public Accommodation(AccommodationType accommodationType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(accommodationType.getBuildingType(), playerNumber, positionX, positionY);
        this.accommodationType = accommodationType;
        this.numberOfSettler = accommodationType.getNumberOfSettler();
        setShowingSignInMap();
    }

    @Override
    public String getName() {
        return accommodationType.getName();
    }

    @Override
    public void setShowingSignInMap() {
        showingSignInMap = accommodationType.getBuildingType().abbreviation() + (getOwnerNumber().getNumber() + 1);
    }

    public void update() {
        Empire empire = this.getOwnerEmpire();
        empire.addPossiblePopulation(this.numberOfSettler);
    }


}