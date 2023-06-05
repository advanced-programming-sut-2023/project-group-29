package model.buildings.buildingTypes;

import model.buildings.BuildingType;

public interface BuildType {
    BuildingType getBuildingType();
    int getNeededResources(int i);
    default String getShowingImageFilePath(){
        return "/images/buildings/" +
                this.getBuildingType().category().name() + "/" + getBuildingType().name() + ".png";
    }

}
