package controller.menucontrollers;

import model.*;
import model.map.Cell;
import model.map.Map;
import model.people.Human;
import model.people.UnitState;
import model.people.humanClasses.Soldier;
import model.people.humanTypes.SoldierType;
import org.checkerframework.checker.units.qual.A;
import view.menus.GameMenu;
import view.menus.SelectUnitMenu;
import view.messages.SelectUnitMenuMessages;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectUnitMenuController {
    private static final int maximumLengthOfTunnel=3;
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

    public static SelectUnitMenuMessages setStateOfUnit(int cellX, int cellY, String stateOfUnitString) {

        GameData gameData = GameMenuController.getGameData();
        Map map = gameData.getMap();

        if (!map.isIndexValid(cellX, cellY))
            return SelectUnitMenuMessages.INVALID_INDEX;
        Cell cell = map.getCells()[cellX][cellY];

        UnitState unitState = UnitState.getUnitStateByString(stateOfUnitString);
        if (unitState == null)
            return SelectUnitMenuMessages.INVALID_STATE;

        ArrayList<Offensive> attackingUnits=cell.getAttackingListOfPlayerNumber(gameData.getPlayerOfTurn());

        for (Offensive attacker: attackingUnits)
            attacker.setUnitState(unitState);

        return SelectUnitMenuMessages.SUCCESSFUL;
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

    public SelectUnitMenuMessages pourOil(String direction) {
        int deltaX=0,deltaY=0;
        if(direction.equals("right"))
            deltaX=1;
        else if(direction.equals("down"))
            deltaY=1;
        else if(direction.equals("left"))
            deltaX=-1;
        else if(direction.equals("up"))
            deltaY=-1;
        else
            return SelectUnitMenuMessages.INVALID_DIRECTION;

        GameData gameData= GameMenuController.getGameData();
        Map map=gameData.getMap();

        int currentX=gameData.getSelectedCellX();
        int currentY=gameData.getSelectedCellY();
        Cell currentCell=map.getCells()[currentX][currentY];

        ArrayList<Offensive> attackingUnits=currentCell.getAttackingListOfPlayerNumber(gameData.getPlayerOfTurn());
        Soldier engineerWithOil=null;
        for(Offensive attacker:attackingUnits)
            if(attacker instanceof Soldier soldier && soldier.getSoldierType().equals(SoldierType.ENGINEER_WITH_OIL))
            {
                engineerWithOil=soldier;
                break;
            }

        if(engineerWithOil==null)
            return SelectUnitMenuMessages.NO_PROPER_UNIT;

        for(int i=1;i<=SoldierType.ENGINEER_WITH_OIL.getAimRange();i++) {
            if(!map.isIndexValid(currentX+deltaX*i,currentY+deltaY*i))
                return SelectUnitMenuMessages.INVALID_INDEX;

            Cell targetedCell=map.getCells()[currentX+deltaX*i][currentY+deltaY*i];
            ArrayList<Asset> enemies=targetedCell.getEnemiesOfPlayerInCell(gameData.getPlayerOfTurn());

            Offensive.AttackingResult attackingResult=engineerWithOil.canAttack(map,currentX+deltaX*i,currentY+deltaY*i);
            switch (attackingResult)
            {
                case TOO_FAR:
                    return SelectUnitMenuMessages.TOO_FAR;
                case HAS_ATTACKED:
                    return SelectUnitMenuMessages.HAS_ATTACKED;
                case NO_ENEMY_THERE:
                    break;
                case SUCCESSFUL:
                    DamageStruct damageStruct=new DamageStruct();
                    damageStruct.groundDamage=formulatedDamage(engineerWithOil);

                    applyAttackDamage(enemies,damageStruct,targetedCell);
                    return SelectUnitMenuMessages.SUCCESSFUL;
            }
        }
    public static String pourOil(String direction) {
        return null;
    }

        return SelectUnitMenuMessages.NO_ENEMY_THERE;
    }

    public static SelectUnitMenuMessages digTunnel(int x, int y) {

        GameData gameData= GameMenuController.getGameData();
        Map map=gameData.getMap();

        if(!map.isIndexValid(x,y))
            return SelectUnitMenuMessages.INVALID_INDEX;

        Cell currentCell=map.getCells()[gameData.getSelectedCellX()][gameData.getSelectedCellY()];

        ArrayList<Offensive> attackingUnits=currentCell.getAttackingListOfPlayerNumber(gameData.getPlayerOfTurn());
        Soldier tunneler=null;
        for(Offensive attacker:attackingUnits)
            if(attacker instanceof Soldier soldier && soldier.getSoldierType().equals(SoldierType.TUNNELER))
            {
                tunneler=soldier;
                break;
            }

        if(tunneler==null)
            return SelectUnitMenuMessages.NO_PROPER_UNIT;

        Offensive.AttackingResult attackingResult=tunneler.canAttack(map,x,y);
        if(attackingResult.equals(Offensive.AttackingResult.TOO_FAR))
            return SelectUnitMenuMessages.TOO_FAR;
        else if(attackingResult.equals(Offensive.AttackingResult.HAS_ATTACKED))
            return SelectUnitMenuMessages.HAS_ATTACKED;


        if(currentCell.hasBuilding())
        {
            Building building=currentCell.getBuilding();
            if(building instanceof OtherBuildings otherBuildings)
                if(otherBuildings.getOtherBuildingsType().equals(OtherBuildingsType.MOAT))
                    return SelectUnitMenuMessages.INVALID_PLACE_FOR_DIGGING_TUNNEL;

            if(building instanceof AttackingBuilding)
                return SelectUnitMenuMessages.INVALID_PLACE_FOR_DIGGING_TUNNEL;
        }

        currentCell.setHasTunnel(true);

        boolean[][] mark=new boolean[map.getWidth()+1][map.getWidth()+1];
        for(boolean[] array:mark)
            Arrays.fill(array,false);

        int numberOfAdjacentTunnelsPlusNewTunnel=dfs(map,new Pair(x,y),mark);

        if(numberOfAdjacentTunnelsPlusNewTunnel>=maximumLengthOfTunnel ||
                currentCell.hasBuilding() ||
                currentCell.getSiegeTowerInMovingUnits()!=null)
            destroyTunnels(map,new Pair(x,y));

        return SelectUnitMenuMessages.SUCCESSFUL;
    }
    private static void destroyTunnels(Map map,Pair thisCellCoordination)
    {
        Cell currentCell=map.getCells()[thisCellCoordination.first][thisCellCoordination.second];

        //destroy tunnel
        currentCell.setHasTunnel(false);
        currentCell.setBuilding(null);
        Asset siegeTower;
        while((siegeTower=currentCell.getSiegeTowerInMovingUnits())!=null)
            currentCell.getMovingObjects().remove(siegeTower);

        //destroy other tunnels
        if(thisCellCoordination.first< map.getWidth() && map.getCells()[thisCellCoordination.first+1][thisCellCoordination.second].hasTunnel())
            destroyTunnels(map,new Pair(thisCellCoordination.first+1,thisCellCoordination.second));
        if(thisCellCoordination.second< map.getWidth() && map.getCells()[thisCellCoordination.first][thisCellCoordination.second+1].hasTunnel())
            destroyTunnels(map,new Pair(thisCellCoordination.first,thisCellCoordination.second+1));
        if(thisCellCoordination.first>1 && map.getCells()[thisCellCoordination.first-1][thisCellCoordination.second].hasTunnel())
            destroyTunnels(map,new Pair(thisCellCoordination.first-1,thisCellCoordination.second));
        if(thisCellCoordination.second>1 && map.getCells()[thisCellCoordination.first][thisCellCoordination.second-1].hasTunnel())
            destroyTunnels(map,new Pair(thisCellCoordination.first,thisCellCoordination.second-1));
    }
    private static int dfs(Map map,Pair thisCellCoordination,boolean[][] mark)
    {
        int numberOfAdjacentTunnels=1;
        mark[thisCellCoordination.first][thisCellCoordination.second]=true;

        if(thisCellCoordination.first< map.getWidth() &&
                map.getCells()[thisCellCoordination.first+1][thisCellCoordination.second].hasTunnel() &&
                !mark[thisCellCoordination.first+1][thisCellCoordination.second])
            numberOfAdjacentTunnels+=dfs(map,new Pair(thisCellCoordination.first+1,thisCellCoordination.second),mark);

        if(thisCellCoordination.second< map.getWidth() &&
                map.getCells()[thisCellCoordination.first][thisCellCoordination.second+1].hasTunnel() &&
                !mark[thisCellCoordination.first][thisCellCoordination.second+1])
            numberOfAdjacentTunnels+=dfs(map,new Pair(thisCellCoordination.first,thisCellCoordination.second+1),mark);

        if(thisCellCoordination.first>1 &&
                map.getCells()[thisCellCoordination.first-1][thisCellCoordination.second].hasTunnel() &&
                !mark[thisCellCoordination.first-1][thisCellCoordination.second])
            numberOfAdjacentTunnels+=dfs(map,new Pair(thisCellCoordination.first-1,thisCellCoordination.second),mark);

        if(thisCellCoordination.second>1 &&
                map.getCells()[thisCellCoordination.first][thisCellCoordination.second-1].hasTunnel() &&
                !mark[thisCellCoordination.first][thisCellCoordination.second-1])
            numberOfAdjacentTunnels+=dfs(map,new Pair(thisCellCoordination.first,thisCellCoordination.second-1),mark);

        return numberOfAdjacentTunnels;
    public static String digTunnel(int x, int y) {
        return null;
    }

    public static String buildEquipment(String equipmentName) {
        return null;
    }

    public static String disbandUnit() {
        //todo abbasfar for later
        return null;
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
