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
    public static void createUnit() {
    }

    public static SelectBuildingMenuMessages repairBuilding(Building building) {
        Empire ownerEmpire = building.getOwnerEmpire();
        if (!hasEnoughStoneToRepair(ownerEmpire, building)) {
            return SelectBuildingMenuMessages.LACK_OF_STONE;
        } else if (building.isEnemyNearIt()) {
            return SelectBuildingMenuMessages.ENEMY_IS_NEAR;
        }
        building.repair();
        return SelectBuildingMenuMessages.SUCCESS;
    }

    private static boolean hasEnoughStoneToRepair(Empire ownerEmpire, Building building) {
        int needed = (building.getMaxHp() - building.getHp()); //TODO maybe * zarib
        int valid = ownerEmpire.getResourceAmount(Resource.STONE);
        return valid >= needed;
    }
}
