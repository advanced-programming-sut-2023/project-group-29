package controller.menucontrollers;

import model.*;
import model.buildings.Building;
import model.dealing.Resource;
import model.map.Cell;
import model.map.Map;
import model.people.Human;
import model.people.UnitState;
import model.people.humanClasses.Soldier;
import view.messages.SelectBuildingMenuMessages;

import java.util.ArrayList;

public class SelectBuildingMenuController {
    private static Building selectedBuilding;

    public static void setSelectedBuilding(Building selectedBuilding) {
        SelectBuildingMenuController.selectedBuilding = selectedBuilding;
    }

    public static void createUnit() {
    }

    public static SelectBuildingMenuMessages repairBuilding() {
        Empire ownerEmpire = selectedBuilding.getOwnerEmpire();
        if (!hasEnoughStoneToRepair(ownerEmpire, selectedBuilding)) {
            return SelectBuildingMenuMessages.LACK_OF_STONE;
        } else if (selectedBuilding.isEnemyNearIt()) {
            return SelectBuildingMenuMessages.ENEMY_IS_NEAR;
        }
        selectedBuilding.repair();
        return SelectBuildingMenuMessages.SUCCESS;
    }

    private static boolean hasEnoughStoneToRepair(Empire ownerEmpire, Building building) {
        int needed = (building.getMaxHp() - building.getHp()); //TODO maybe * zarib
        int valid = ownerEmpire.getResourceAmount(Resource.STONE);
        return valid >= needed;
    }
}
