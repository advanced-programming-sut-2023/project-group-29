package model.buildings.buildingTypes;

import model.buildings.BuildingType;

public interface BuildType {
    BuildingType getBuildingType();

    int getNeededResources(int i);
}
