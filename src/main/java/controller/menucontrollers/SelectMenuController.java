package controller.menucontrollers;

import model.*;
import model.map.Cell;
import model.map.Map;
import model.people.humanClasses.Soldier;

import java.util.ArrayList;

public class SelectMenuController {
    public static void createUnit() {
    }

    public static void repairBuilding() {
    }

    public static String moveUnit(int destinationX, int destinationY) {
        GameData gameData = GameMenuController.getGameData();

        int currentX = gameData.getSelectedCellX();
        int currentY = gameData.getSelectedCellY();

        Map map = gameData.getMap();
        Cell currentCell = map.getCells()[currentX][currentY];

        //create moving object list
        ArrayList<Movable> movingObjects = currentCell.getMovingObjectsOfPlayer(gameData.getPlayerOfTurn());


        //apply moves
        int failuresCount = 0;
        ArrayList<Movable> successfullyDeletedObjects = new ArrayList<>();

        for (Movable movableObject : movingObjects) {
            Movable.MovingResult movingResult = movableObject.move(map, destinationX, destinationY);

            switch (movingResult) {
                case BAD_PLACE:
                    return "Troops cannot go there!";
                case INVALID_INDEX:
                    return "Destination index is invalid!";
                case TOO_FAR:
                    failuresCount++;
                    break;
                case SUCCESSFUL:
                    successfullyDeletedObjects.add(movableObject);
                    map.getCells()[destinationX][destinationY].getMovingObjects().add((Asset) movableObject);
                    break;
            }
        }

        for (Movable removingObject : successfullyDeletedObjects)
            currentCell.getMovingObjects().remove(removingObject);

        if (failuresCount == 0)
            return "All troops moved successfully!";

        int totalObjectCount = movingObjects.size();
        return (totalObjectCount - failuresCount) + " troop(s) out of " + totalObjectCount + " moved successfully!";
    }

    public static void patrolUnit() {
        //TODO abbasfar
    }

    public static void setStateOfUnit() {
        //TODO abbasfar
    }

    public static String makeUnitAttacking(int targetX, int targetY) {
        GameData gameData = GameMenuController.getGameData();

        int currentX = gameData.getSelectedCellX();
        int currentY = gameData.getSelectedCellY();

        Map map = gameData.getMap();
        if (!map.isIndexValid(targetX, targetY))
            return "Your target index is invalid!";

        //create attacking objects list
        Cell currentCell = map.getCells()[currentX][currentY];
        ArrayList<Offensive> currentPlayerAttackers = currentCell.getAttackingListOfPlayerNumber(gameData.getPlayerOfTurn());

        //create enemy objects list
        Cell targetCell = map.getCells()[targetX][targetY];
        ArrayList<Asset> enemies = targetCell.getEnemiesOfPlayerInCell(gameData.getPlayerOfTurn());

        //apply attack
        //TODO abbasfar: state for soldiers
        //todo abbasfar: building damage applying
        //if a unit is near to death or not, makes no change for others
        DamageStruct totalDamage = findTotalDamage(currentPlayerAttackers, map, targetX, targetY);
        applyAttackDamage(enemies, totalDamage, targetCell);
        int currentPlayerFailures = totalDamage.failures;

        //apply back attack
        DamageStruct counterAttackTotalDamage = new DamageStruct();
        DamageStruct partialDamage = new DamageStruct();

        for (PlayerNumber playerNumber : PlayerNumber.values()) {
            if (playerNumber.equals(gameData.getPlayerOfTurn()))
                continue;

            ArrayList<Offensive> playerAttackers = targetCell.getAttackingListOfPlayerNumber(playerNumber);
            partialDamage = findTotalDamage(playerAttackers, map, currentX, currentY);
            counterAttackTotalDamage.add(partialDamage);
        }

        applyAttackDamage(offensiveArrayListToAssetArrayList(currentPlayerAttackers), counterAttackTotalDamage, currentCell);

        //results
        if (currentPlayerFailures == 0)
            return "All units attacked successfully!";

        int totalAttackingEfforts = currentPlayerAttackers.size();
        return (totalAttackingEfforts - currentPlayerFailures) + " troops out of " + totalAttackingEfforts + " attacked successfully!";
    }

    private static ArrayList<Asset> offensiveArrayListToAssetArrayList(ArrayList<Offensive> attackers) {
        ArrayList<Asset> assets = new ArrayList<>();
        for (Offensive attacker : attackers)
            assets.add((Asset) attacker);
        return assets;
    }

    private static void applyAttackDamage(ArrayList<Asset> assets, DamageStruct damageStruct, Cell damagedCell) {
        boolean[] playerHasShieldInCell = new boolean[9];
        for (int i = 1; i <= 8; i++)
            playerHasShieldInCell[i] = damagedCell.shieldExistsInCell(PlayerNumber.getPlayerByIndex(i));

        for (Asset asset : assets) {
            asset.decreaseHp(damageStruct.landDamage);
            if (playerHasShieldInCell[asset.getOwnerNumber().getNumber()])
                asset.decreaseHp(damageStruct.airDamage / Offensive.decreasingFactorForAirDamageDueToShield);
            else asset.decreaseHp(damageStruct.airDamage);
        }
    }

    private static DamageStruct findTotalDamage(ArrayList<Offensive> attackers, Map map, int targetX, int targetY) {
        DamageStruct damageStruct = new DamageStruct();

        for (Offensive attacker : attackers) {
            Offensive.AttackingResult attackingResult = attacker.canAttack(map, targetX, targetY);

            switch (attackingResult) {
                case SUCCESSFUL:
                    if (attacker.isArcherType())
                        damageStruct.airDamage += formulatedDamage(attacker);
                    else
                        damageStruct.landDamage += formulatedDamage(attacker);
                    break;
                case TOO_FAR:
                    damageStruct.failures++;
                    break;
            }
        }

        return damageStruct;
    }

    private static int formulatedDamage(Offensive attacker) {
        int fearRate = ((Asset) attacker).getOwnerEmpire().getFearRate();
        if (attacker instanceof Soldier)
            return (attacker.getDamage() * (100 + fearRate * 5)) / 100;
        return attacker.getDamage();
    }

    public static void pourOil() {
    }

    public static void digTunnel() {
    }

    public static void buildEquipment() {
    }

    public static void disbandUnit() {
    }

    static class DamageStruct {
        public int landDamage = 0;
        public int airDamage = 0;
        public int failures = 0;

        public void add(DamageStruct secondDamageStruct) {
            landDamage += secondDamageStruct.landDamage;
            airDamage += secondDamageStruct.airDamage;
            failures += secondDamageStruct.failures;
        }
    }


}
