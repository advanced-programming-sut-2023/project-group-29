package model.buildings.buildingClasses;

import controller.menucontrollers.GameMenuController;
import controller.menucontrollers.MapMenuController;
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
        Cell currentCell = GameMenuController.getGameData().getMap().getCells()[getPositionX()][getPositionY()];
        if (otherBuildingsType.equals(OtherBuildingsType.OIL_SMELTER)) {
            Soldier engineer = findEngineer(currentCell);
            if (engineer != null) {
                convertEngineerToEngineerWithOil(engineer,currentCell);
            }
        }
    }

    private void convertEngineerToEngineerWithOil(Soldier soldier, Cell currentCell) {
        PlayerNumber playerNumber = GameMenuController.getGameData().getPlayerOfTurn();
        currentCell.getMovingObjects().remove(soldier);
        MapMenuController.dropUnit
                (getPositionX(), getPositionY(), "engineerWithOil", 1,playerNumber.getNumber());
    }

    private Soldier findEngineer(Cell currentCell) {
        for (Asset asset: currentCell.getMovingObjects()) {
            if (asset instanceof Soldier soldier){
                if (soldier.getSoldierType().equals(SoldierType.ENGINEER)) return soldier;
            }
        }
        return null;
    }

    public void changeState() {
        if (showingSignInMap.charAt(4) == 'D') {
            showingSignInMap = showingSignInMap.substring(0,4) + "U" + showingSignInMap.charAt(5) ;
        } else if (showingSignInMap.charAt(4) == 'U') {
            showingSignInMap = showingSignInMap.substring(0,4) + "D" + showingSignInMap.charAt(5) ;
        }
    }
}
