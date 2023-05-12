package controller.menucontrollers;

import model.Empire;
import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingClasses.UnitCreator;
import model.buildings.buildingTypes.UnitCreatorType;
import model.dealing.Product;
import model.dealing.Resource;
import model.map.Cell;
import model.map.Map;
import model.people.Human;
import model.people.humanTypes.SoldierType;
import view.messages.SelectBuildingMenuMessages;

public class SelectBuildingMenuController {
    private static Building selectedBuilding;

    public static void setSelectedBuilding(Building selectedBuilding) {
        SelectBuildingMenuController.selectedBuilding = selectedBuilding;
    }

    public static SelectBuildingMenuMessages createUnit(String unitType, int count) {
        Empire ownerEmpire = selectedBuilding.getOwnerEmpire();
        SoldierType soldierType = SoldierType.getSoldierTypeByName(unitType);
        if (soldierType == null) {
            return SelectBuildingMenuMessages.INVALID_TYPE;
        } else if (!(selectedBuilding instanceof UnitCreator
                && buildingAndTroopMatch((UnitCreator) selectedBuilding, soldierType))) {
            return SelectBuildingMenuMessages.UNRELATED_TYPE;
        }
        UnitCreator building = (UnitCreator) selectedBuilding;
        if (building.getUnitCost() * count > ownerEmpire.getWealth()) {
            return SelectBuildingMenuMessages.LACK_OF_COINS;
        } else if (!isWeaponEnough(ownerEmpire, soldierType, count)) {
            return SelectBuildingMenuMessages.LACK_OF_WEAPON;
        } else if (ownerEmpire.getWorklessPopulation() < count) {
            return SelectBuildingMenuMessages.LACK_OF_HUMAN;
        }
        PlayerNumber playerNumber = GameMenuController.getGameData().getPlayerOfTurn();
        for (int i = 0; i < count; i++)
            Human.createUnitByName(unitType, playerNumber, building.getPositionX(), building.getPositionY());
        return SelectBuildingMenuMessages.SUCCESS;
    }

    private static boolean buildingAndTroopMatch(UnitCreator building, SoldierType soldierType) {
        return switch (soldierType) {
            case ARABIAN_SWORDSMAN -> (building.getUnitCreatorType().equals(UnitCreatorType.MERCENARY_POST));
            case ENGINEER, LADDER_MAN -> (building.getUnitCreatorType().equals(UnitCreatorType.ENGINEER_GUILD));
            default -> (building.getUnitCreatorType().equals(UnitCreatorType.BARRACK));
        };
    }

    private static boolean isWeaponEnough(Empire ownerEmpire, SoldierType soldierType, int count) {
        Product weapon = null; //todo jasbi = getProductFromSoldierType complete;
        if (weapon == null) return true;
        return ownerEmpire.getTradableAmount(weapon) >= count;
    }

    public static SelectBuildingMenuMessages repairBuilding() {
        Empire ownerEmpire = selectedBuilding.getOwnerEmpire();
        if (!hasEnoughStoneToRepair(ownerEmpire, selectedBuilding)) {
            return SelectBuildingMenuMessages.LACK_OF_STONE;
        } else if (isEnemyNearIt(selectedBuilding)) {
            return SelectBuildingMenuMessages.ENEMY_IS_NEAR;
        }
        selectedBuilding.repair();
        return SelectBuildingMenuMessages.SUCCESS;
    }

    private static boolean isEnemyNearIt(Building building) {
        int x = building.getPositionX(), y = building.getPositionY();
        Map map = GameMenuController.getGameData().getMap();
        return  isEnemyInThisCell(map, x + 1, y) ||
                isEnemyInThisCell(map, x - 1, y) ||
                isEnemyInThisCell(map, x, y + 1) ||
                isEnemyInThisCell(map, x, y - 1);
    }

    private static boolean isEnemyInThisCell(Map map, int x, int y) {
        if (!map.isIndexValid(x, y)) return false;
        Cell cell = map.getCells()[x][y];
        PlayerNumber myPlayerNumber = GameMenuController.getGameData().getPlayerOfTurn();
        return (cell.getNumberOfStrangeUnits(myPlayerNumber) > 0);
    }

    private static boolean hasEnoughStoneToRepair(Empire ownerEmpire, Building building) {
        int needed = (building.getMaxHp() - building.getHp()) / 10;
        int valid = ownerEmpire.getTradableAmount(Resource.STONE);
        return valid >= needed;
    }

    public static String getBuildingHp() {
        return "Your building hp: " + selectedBuilding.getHp() + " / " + selectedBuilding.getMaxHp();
    }
}
