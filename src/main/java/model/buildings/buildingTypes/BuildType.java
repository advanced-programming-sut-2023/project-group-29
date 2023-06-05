package model.buildings.buildingTypes;

import model.buildings.BuildingType;

public interface BuildType {
    BuildingType getBuildingType();

    String getName();

    int getNeededResources(int i);
}
