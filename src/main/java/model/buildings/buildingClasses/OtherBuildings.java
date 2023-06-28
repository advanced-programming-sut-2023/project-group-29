package model.buildings.buildingClasses;

import controller.menucontrollers.GameController;
import controller.menucontrollers.MapFunctions;
import model.Asset;
import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.OtherBuildingsType;
import model.map.Cell;
import model.people.humanClasses.Soldier;
import model.people.humanTypes.SoldierType;

public class OtherBuildings extends Building {
    private final OtherBuildingsType otherBuildingsType;

    public OtherBuildings
            (OtherBuildingsType otherBuildingsType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(otherBuildingsType.getBuildingType(), playerNumber, positionX, positionY);
        this.otherBuildingsType = otherBuildingsType;
        setShowingSignInMap();
    }

    @Override
    public void setShowingSignInMap() {
        showingSignInMap = otherBuildingsType.getBuildingType().abbreviation() + (getOwnerNumber().getNumber() + 1);
    }

    public OtherBuildingsType getOtherBuildingsType() {
        return otherBuildingsType;
    }

    public void update() {
        Cell currentCell = GameController.getGameData().getMap().getCells()[getPositionX()][getPositionY()];
        if (otherBuildingsType.equals(OtherBuildingsType.OIL_SMELTER)) {
            Soldier soldier = findSoldier(currentCell);
            if (soldier != null) {
                if (soldier.getSoldierType().equals(SoldierType.ENGINEER)) {
                    convertEngineerToEngineerWithOil(soldier, currentCell);
                } else if (soldier.getSoldierType().equals(SoldierType.ENGINEER_WITH_OIL)) {
                } else {
                    soldier.setHasFire(true);
                }
            }
        }
    }

    private void convertEngineerToEngineerWithOil(Soldier soldier, Cell currentCell) {
        PlayerNumber playerNumber = GameController.getGameData().getPlayerOfTurn();
        currentCell.getMovingObjects().remove(soldier);
        MapFunctions.dropUnit(getPositionX(), getPositionY(),
                "engineerWithOil", 1, playerNumber.getNumber() + 1);
    }

    private Soldier findSoldier(Cell currentCell) {
        for (Asset asset : currentCell.getMovingObjects()) {
            if (asset instanceof Soldier soldier) {
                if (soldier.getOwnerEmpire().equals(currentCell.getBuilding().getOwnerEmpire())
                && !soldier.hasFire()) return soldier;
            }
        }
        return null;
    }

    public void changeState() {
        if (showingSignInMap.charAt(4) == 'D') {
            showingSignInMap = showingSignInMap.substring(0, 4) + "U" + showingSignInMap.charAt(5);
            showingImageFilePath = "/images/buildings/CASTLE/drawBridge2.png";
        } else if (showingSignInMap.charAt(4) == 'U') {
            showingSignInMap = showingSignInMap.substring(0, 4) + "D" + showingSignInMap.charAt(5);
            showingImageFilePath = "/images/buildings/CASTLE/drawBridge.png";
        }
    }
}
