package model.buildings.buildingTypes;

import model.buildings.BuildingType;

public interface BuildType {
    public BuildingType getBuildingType();
    public String getName();
    public int getNeededResources(int i);
}
