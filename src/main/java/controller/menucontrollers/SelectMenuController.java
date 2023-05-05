package controller.menucontrollers;

import model.*;
import model.buildings.Building;
import model.dealing.Resource;
import model.map.Cell;
import model.map.Map;
import model.people.Human;
import model.people.HumanState;
import model.people.humanClasses.Soldier;
import view.messages.SelectMenuMessages;

import java.util.ArrayList;

public class SelectMenuController {
    public static void createUnit() {
    }

    public static SelectMenuMessages repairBuilding(Building building) {
        Empire ownerEmpire = building.getOwnerEmpire();
        if (!hasEnoughStoneToRepair(ownerEmpire, building)) {
            return SelectMenuMessages.LACK_OF_STONE;
        } else if (building.isEnemyNearIt()) {
            return SelectMenuMessages.ENEMY_IS_NEAR;
        }
        building.repair();
        return SelectMenuMessages.SUCCESS;
    }

    private static boolean hasEnoughStoneToRepair(Empire ownerEmpire, Building building) {
        int needed = (building.getMaxHp() - building.getHp()); //TODO maybe * zarib
        int valid = ownerEmpire.getResourceAmount(Resource.STONE);
        return valid >= needed;
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
                case TOO_FAR, HAS_MOVED:
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

    //todo abbasfar where is ordoogah??? disband unit

    public static String patrolUnit(int firstX, int firstY, int secondX, int secondY) {

        GameData gameData = GameMenuController.getGameData();

        int currentX = gameData.getSelectedCellX();
        int currentY = gameData.getSelectedCellY();

        Map map = gameData.getMap();
        Cell currentCell = map.getCells()[currentX][currentY];

        //create moving object list
        ArrayList<Movable> movingObjects = currentCell.getMovingObjectsOfPlayer(gameData.getPlayerOfTurn());

        int failures = 0;
        for (Movable movingObject : movingObjects) {
            if (!movingObject.checkForMoveErrors(map, firstX, firstY).equals(Movable.MovingResult.SUCCESSFUL)) {
                failures++;
                continue;
            }

            ((Asset) movingObject).setPositionX(firstX);
            ((Asset) movingObject).setPositionY(firstY);

            if (movingObject.checkForMoveErrors(map, secondX, secondY).equals(Movable.MovingResult.SUCCESSFUL)) {
                movingObject.getPatrol().startNewPatrol(firstX, firstY, secondX, secondY);
            } else {
                failures++;
            }

            ((Asset) movingObject).setPositionX(currentX);
            ((Asset) movingObject).setPositionY(currentY);
        }

        return (movingObjects.size() - failures) + " unit(s) out of " + movingObjects.size() + " has been set successfully!";
    }

    public static String setStateOfUnit(int cellX, int cellY, String stateOfUnitString) {

        GameData gameData = GameMenuController.getGameData();
        Map map = gameData.getMap();

        if (!map.isIndexValid(cellX, cellY))
            return "The index is invalid!";
        Cell cell = map.getCells()[cellX][cellY];

        HumanState humanState = HumanState.getHumanStateByString(stateOfUnitString);
        if (humanState == null)
            return "Enter a valid state";

        ArrayList<Movable> assetsInCell = cell.getMovingObjectsOfPlayer(gameData.getPlayerOfTurn());

        for (Movable movable : assetsInCell)
            if (movable instanceof Human)
                ((Human) movable).setState(humanState);

        return "State successfully set!";
    }

    public static String makeUnitAttacking(int targetX, int targetY) {
        GameData gameData = GameMenuController.getGameData();

        int currentX = gameData.getSelectedCellX();
        int currentY = gameData.getSelectedCellY();

        Map map = gameData.getMap();
        if (!map.isIndexValid(targetX, targetY))
            return "Your target index is invalid!";

        PlayerNumber currentPlayer = gameData.getPlayerOfTurn();

        //create attacking objects list
        Cell currentCell = map.getCells()[currentX][currentY];
        ArrayList<Offensive> currentPlayerAttackers = currentCell.getAttackingListOfPlayerNumber(currentPlayer);

        //create enemy objects list
        Cell targetCell = map.getCells()[targetX][targetY];
        ArrayList<Asset> enemies = targetCell.getEnemiesOfPlayerInCell(gameData.getPlayerOfTurn());
        if (targetCell.hasBuilding())
            enemies.add(targetCell.getBuilding());

        //apply attack
        //if a unit is near to death or not, makes no change for others

        HeightOfAsset heightOfAttackers = currentCell.heightOfUnitsOfPlayer(currentPlayer);
        DamageStruct totalDamage = findTotalDamage(heightOfAttackers, currentPlayerAttackers, map, targetX, targetY);
        applyAttackDamage(enemies, totalDamage, targetCell);
        int currentPlayerFailures = totalDamage.failures;

        //apply back attack
        DamageStruct counterAttackTotalDamage = new DamageStruct();
        DamageStruct partialDamage = new DamageStruct();

        for (PlayerNumber playerNumber : PlayerNumber.values()) {
            if (playerNumber.equals(currentPlayer))
                continue;

            ArrayList<Offensive> playerAttackers = targetCell.getAttackingListOfPlayerNumber(playerNumber);

            heightOfAttackers = currentCell.heightOfUnitsOfPlayer(playerNumber);
            partialDamage = findTotalDamage(heightOfAttackers, playerAttackers, map, currentX, currentY);
            counterAttackTotalDamage.add(partialDamage);
        }

        applyAttackDamage(offensiveArrayListToAssetArrayList(currentPlayerAttackers), counterAttackTotalDamage, currentCell);

        currentCell.removeDeadUnitsAndBuilding();
        targetCell.removeDeadUnitsAndBuilding();

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

    private static void applyAttackDamage(ArrayList<Asset> targetedAssets, DamageStruct damageStruct, Cell damagedCell) {
        boolean[] playerHasShieldInCell = new boolean[8];
        for (int i = 0; i <= 7; i++)
            playerHasShieldInCell[i] = damagedCell.shieldExistsInCell(PlayerNumber.getPlayerByIndex(i));

        ArrayList<Asset> upHeightTargets = new ArrayList<>();
        ArrayList<Asset> middleHeightTargets = new ArrayList<>();
        ArrayList<Asset> groundHeightTargets = new ArrayList<>();

        for (Asset asset : targetedAssets) {
            if (damagedCell.heightOfUnitsOfPlayer(asset.getOwnerNumber()).equals(HeightOfAsset.UP))
                upHeightTargets.add(asset);
            else if (damagedCell.heightOfUnitsOfPlayer(asset.getOwnerNumber()).equals(HeightOfAsset.MIDDLE))
                middleHeightTargets.add(asset);
            else if (damagedCell.heightOfUnitsOfPlayer(asset.getOwnerNumber()).equals(HeightOfAsset.GROUND))
                groundHeightTargets.add(asset);
        }

        int totalNumberOfUnits = upHeightTargets.size() + middleHeightTargets.size() + groundHeightTargets.size();
        int dividedAirDamage = damageStruct.airDamage / totalNumberOfUnits;

        int dividedUpDamage = damageStruct.upDamage / upHeightTargets.size();
        applyAttackDamageOnLevel(upHeightTargets, dividedUpDamage, dividedAirDamage, playerHasShieldInCell);

        int dividedMiddleDamage = damageStruct.middleDamage / middleHeightTargets.size();
        applyAttackDamageOnLevel(middleHeightTargets, dividedMiddleDamage, dividedAirDamage, playerHasShieldInCell);

        int dividedGroundDamage = damageStruct.groundDamage / groundHeightTargets.size();
        applyAttackDamageOnLevel(groundHeightTargets, dividedGroundDamage, dividedAirDamage, playerHasShieldInCell);
    }

    private static void applyAttackDamageOnLevel(ArrayList<Asset> targetedAssets, int sameHeightDamageDividedToTargets, int airDamageDividedToTargets, boolean[] playerHasShieldInCell) {
        for (Asset asset : targetedAssets) {
            asset.decreaseHp(sameHeightDamageDividedToTargets);
            if (playerHasShieldInCell[asset.getOwnerNumber().getNumber()])
                asset.decreaseHp(airDamageDividedToTargets / Offensive.decreasingFactorForAirDamageDueToShield);
            else asset.decreaseHp(airDamageDividedToTargets);
        }
    }

    private static DamageStruct findTotalDamage(HeightOfAsset heightOfAttackers, ArrayList<Offensive> attackers, Map map, int targetX, int targetY) {
        DamageStruct damageStruct = new DamageStruct();

        for (Offensive attacker : attackers) {
            Offensive.AttackingResult attackingResult = attacker.canAttack(map, targetX, targetY);

            switch (attackingResult) {
                case SUCCESSFUL:
                    if (attacker.isArcherType())
                        damageStruct.airDamage += formulatedDamage(attacker);
                    else if (heightOfAttackers.equals(HeightOfAsset.UP))
                        damageStruct.upDamage += formulatedDamage(attacker);
                    else if (heightOfAttackers.equals(HeightOfAsset.MIDDLE))
                        damageStruct.middleDamage += formulatedDamage(attacker);
                    else if (heightOfAttackers.equals(HeightOfAsset.GROUND))
                        damageStruct.groundDamage += formulatedDamage(attacker);
                    break;
                case TOO_FAR, HAS_ATTACKED:
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
        //todo for later
    }

    static class DamageStruct {
        public int groundDamage = 0;
        public int middleDamage = 0;
        public int upDamage = 0;
        public int airDamage = 0;
        public int failures = 0;

        public void add(DamageStruct secondDamageStruct) {
            groundDamage += secondDamageStruct.groundDamage;
            middleDamage += secondDamageStruct.middleDamage;
            upDamage += secondDamageStruct.upDamage;
            airDamage += secondDamageStruct.airDamage;
            failures += secondDamageStruct.failures;
        }
    }


}
