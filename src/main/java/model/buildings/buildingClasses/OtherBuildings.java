package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.OtherBuildingsType;

public class OtherBuildings extends Building {
    private final OtherBuildingsType otherBuildingsType;

    public OtherBuildings
            (OtherBuildingsType otherBuildingsType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(otherBuildingsType.getBuildingType(), playerNumber, positionX, positionY);
        this.otherBuildingsType = otherBuildingsType;
        setShowingSignInMap();
    }



    @Override
    public String getName() {
        return otherBuildingsType.getName();
    }

    @Override
    public void setShowingSignInMap() {
        showingSignInMap = otherBuildingsType.getBuildingType().abbreviation() + (getOwnerNumber().getNumber() + 1);
    }

    public OtherBuildingsType getOtherBuildingsType() {
        return otherBuildingsType;
    }

    public void update() {
        //todo jasbi: کارخانه ذوب مهندس خالی بگیرد و مهندس با نفت بدهد
    }
}
