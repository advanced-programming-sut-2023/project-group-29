package controller.menucontrollers;

import model.*;
import model.buildings.Building;
import model.buildings.buildingClasses.AttackingBuilding;
import model.buildings.buildingClasses.OtherBuildings;
import model.buildings.buildingTypes.OtherBuildingsType;
import model.map.Cell;
import model.map.Map;
import model.people.Human;
import model.people.humanClasses.Soldier;
import model.people.humanTypes.SoldierType;
import model.unitfeatures.HeightOfAsset;
import model.unitfeatures.Movable;
import model.unitfeatures.Offensive;
import model.unitfeatures.UnitState;
import model.weapons.Weapon;
import model.weapons.weaponClasses.OffensiveWeapons;
import model.weapons.weaponClasses.Trap;
import model.weapons.weaponTypes.OffensiveWeaponsType;
import view.messages.SelectUnitMenuMessages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnitFunctions {
    private static final int maximumLengthOfTunnel = 3;
    public static void moveUnits(ArrayList<Asset> movingUnits){
        for(Asset movingUnit:movingUnits){
            ArrayList<Pair<Integer, Integer>> path=moveOneUnit(movingUnit);

            if(path==null)
                return;

            GameController.getGameData().getGameGraphicFunctions().moveAnimate(movingUnit,path);
        }
    }
    public static ArrayList<Pair<Integer, Integer>> moveOneUnit(Asset movingUnit) {
        GameData gameData = GameController.getGameData();
        Map map = gameData.getMap();

        Pair<Integer, Integer> destinationCellPosition = gameData.getDestinationCellPosition();
        Pair<Integer, Integer> currentCellPosition = new Pair<>(movingUnit.getPositionX(), movingUnit.getPositionY());

        Cell currentCell = map.getCells()[currentCellPosition.first][currentCellPosition.second];

        Movable movableUnit = (Movable) movingUnit;

        Movable.MovingResult movingResult = movableUnit.move(map, destinationCellPosition.first, destinationCellPosition.second);
        switch (movingResult) {
            case TOO_FAR, HAS_MOVED -> {
                return null;
            }
            case SUCCESSFUL -> {
                currentCell.getMovingObjects().remove(movingUnit);
                return Movable.pathToDestination(map, movingUnit, destinationCellPosition.first, destinationCellPosition.second);
            }
        }

        return null;
    }

    public static SelectUnitMenuMessages moveUnitsCheckError(ArrayList<Asset> movingUnits, Pair<Integer, Integer> destination) {
        GameData gameData = GameController.getGameData();
        Map map = gameData.getMap();

        if (destination == null || destination.first == null || destination.second == null)
            return SelectUnitMenuMessages.NO_DESTINATION_SELECTED;
        if (movingUnits.size() == 0)
            return SelectUnitMenuMessages.EMPTY_MOVING_UNIT_ARRAY_LIST;

        Movable.MovingResult movingResult = Movable.checkForMoveErrors(map, movingUnits.get(0), destination.first, destination.second);
        if (movingResult.equals(Movable.MovingResult.BAD_PLACE))
            return SelectUnitMenuMessages.BAD_PLACE_TO_MOVE_ON;

        return SelectUnitMenuMessages.SUCCESSFUL;
    }

    public static ArrayList<Pair<Integer, Integer>> patrolUnit(Asset asset) {
        return null;
        //todo abbasfar do patrol
    }

    public static SelectUnitMenuMessages patrolUnitsCheckError() {
        return null;
    }

    public static void setUnitsPatrolling(ArrayList<Movable> movables, int firstX, int firstY, int secondX, int secondY) {

        Map map = GameController.getGameData().getMap();

        for (Movable movingObject : movables) {
            if (!movingObject.checkForMoveErrors(map, firstX, firstY).equals(Movable.MovingResult.SUCCESSFUL)) {
                continue;
            }

            Asset patrollingAsset = (Asset) movingObject;
            int currentX = patrollingAsset.getPositionX();
            int currentY = patrollingAsset.getPositionY();

            //todo maintain bad place, should check manually instead of distance -1;

            patrollingAsset.setPositionX(firstX);
            patrollingAsset.setPositionY(firstY);

            if (movingObject.checkForMoveErrors(map, secondX, secondY).equals(Movable.MovingResult.SUCCESSFUL)) {
                movingObject.getPatrol().startNewPatrol(firstX, firstY, secondX, secondY);
            }

            patrollingAsset.setPositionX(currentX);
            patrollingAsset.setPositionY(currentY);
        }
    }

    public static SelectUnitMenuMessages setStateOfUnit(ArrayList<Offensive> attackingUnits, UnitState unitState) {
        for (Offensive attacker : attackingUnits)
            attacker.setUnitState(unitState);

        return SelectUnitMenuMessages.SUCCESSFUL;
    }

    public static SelectUnitMenuMessages makeOneUnitAttack(Offensive attacker, Pair<Integer, Integer> target) {

        GameData gameData = GameController.getGameData();
        Map map = gameData.getMap();

        Asset attackerAsset = (Asset) attacker;
        Cell currentCell = map.getCells()[attackerAsset.getPositionX()][attackerAsset.getPositionY()];

        //create enemy objects list
        Cell targetCell = map.getCells()[target.first][target.second];
        ArrayList<Asset> enemies = targetCell.getEnemiesOfPlayerInCell(attackerAsset.getOwnerNumber());
        if (targetCell.hasBuilding())
            enemies.add(targetCell.getBuilding());

        if (enemies.size() == 0)
            return SelectUnitMenuMessages.NO_ENEMY_THERE;

        //apply attack
        //if a unit is near to death or not, makes no change for others

        HeightOfAsset heightOfAttacker = currentCell.heightOfUnitsOfPlayer();
        DamageStruct attackerDamage = findDamage(heightOfAttacker, attacker, map, target, true);

        if (attackerDamage == null)
            return SelectUnitMenuMessages.ATTACK_FAILED;

        applyAttackDamage(enemies, attackerDamage, targetCell);
        return SelectUnitMenuMessages.SUCCESSFUL;
    }

    public static SelectUnitMenuMessages unitsAttackingCheckError(ArrayList<Offensive> attackers, Pair<Integer, Integer> target) {
        return SelectUnitMenuMessages.SUCCESSFUL;
    }

    public static int makeUnitsAttacking(ArrayList<Offensive> attackers, Pair<Integer, Integer> target) {

        GameData gameData = GameController.getGameData();
        Map map = gameData.getMap();

        if (!map.isIndexValid(target.first, target.second))
            return -1;

        //attack
        int failures = 0;
        for (Offensive attacker : attackers) {
            SelectUnitMenuMessages result = makeOneUnitAttack(attacker, target);
            if(!result.equals(SelectUnitMenuMessages.SUCCESSFUL))
                failures++;
        }

        //apply back attack
        /*
        {
            DamageStruct counterAttackTotalDamage = new DamageStruct();
            DamageStruct partialDamage;

            for (PlayerNumber playerNumber : PlayerNumber.values()) {
                if (playerNumber.equals(currentPlayer))
                    continue;

                ArrayList<Offensive> playerAttackers = targetCell.getAttackingListOfPlayerNumber(playerNumber);

                heightOfAttackers = currentCell.heightOfUnitsOfPlayer();
                partialDamage = findTotalDamage
                        (heightOfAttackers, playerAttackers, map, currentX, currentY, false);
                counterAttackTotalDamage.add(partialDamage);
            }

            applyAttackDamage(offensiveArrayListToAssetArrayList(currentPlayerAttackers),
                    counterAttackTotalDamage, currentCell);
        }
        */

        return failures;

    }

    private static ArrayList<Asset> offensiveArrayListToAssetArrayList(ArrayList<Offensive> attackers) {
        ArrayList<Asset> assets = new ArrayList<>();
        for (Offensive attacker : attackers)
            assets.add((Asset) attacker);
        return assets;
    }

    public static void applyAttackDamage(ArrayList<Asset> targetedAssets, DamageStruct damageStruct, Cell damagedCell) {
        boolean[] playerHasShieldInCell = new boolean[8];
        for (int i = 0; i < 8; i++)
            playerHasShieldInCell[i] = damagedCell.shieldExistsInCell(PlayerNumber.getPlayerByIndex(i));

        ArrayList<Asset> upHeightTargets = new ArrayList<>();
        ArrayList<Asset> middleHeightTargets = new ArrayList<>();
        ArrayList<Asset> groundHeightTargets = new ArrayList<>();

        for (Asset asset : targetedAssets) {
            if (damagedCell.heightOfUnitsOfPlayer().equals(HeightOfAsset.UP))
                upHeightTargets.add(asset);
            else if (damagedCell.heightOfUnitsOfPlayer().equals(HeightOfAsset.MIDDLE))
                middleHeightTargets.add(asset);
            else if (damagedCell.heightOfUnitsOfPlayer().equals(HeightOfAsset.GROUND))
                groundHeightTargets.add(asset);
        }

        int totalNumberOfUnits = upHeightTargets.size() + middleHeightTargets.size() + groundHeightTargets.size();
        int dividedAirDamage = totalNumberOfUnits == 0 ? 0 : damageStruct.airDamage / totalNumberOfUnits;

        int dividedUpDamage = upHeightTargets.size() == 0 ? 0 : damageStruct.upDamage / upHeightTargets.size();
        applyAttackDamageOnLevel(upHeightTargets, dividedUpDamage, dividedAirDamage, playerHasShieldInCell);

        int dividedMiddleDamage = middleHeightTargets.size() == 0 ? 0
                : damageStruct.middleDamage / middleHeightTargets.size();
        applyAttackDamageOnLevel(middleHeightTargets, dividedMiddleDamage, dividedAirDamage, playerHasShieldInCell);

        int dividedGroundDamage = groundHeightTargets.size() == 0 ? 0
                : damageStruct.groundDamage / groundHeightTargets.size();
        applyAttackDamageOnLevel(groundHeightTargets, dividedGroundDamage, dividedAirDamage, playerHasShieldInCell);

        Building building = null;
        for (Asset asset : targetedAssets)
            if (asset instanceof Building)
                building = (Building) asset;

        ArrayList<Asset> nonBuildings = new ArrayList<>(targetedAssets);
        if (building != null)
            nonBuildings.remove(building);

        int dividedNonBuildingDamage = nonBuildings.size() == 0 ? 0 : damageStruct.nonBuildingDamage / nonBuildings.size();
        applyAttackDamageOnLevel
                (nonBuildings, 0, dividedNonBuildingDamage, playerHasShieldInCell);

        if (building != null)
            applyAttackDamageOnLevel(new ArrayList<>(List.of(building)), damageStruct.onlyBuildingDamage,
                    0, playerHasShieldInCell);

        damagedCell.removeDeadUnitsAndBuilding();
    }

    private static void applyAttackDamageOnLevel(ArrayList<Asset> targetedAssets, int sameHeightDamageDividedToTargets,
                                                 int airDamageDividedToTargets, boolean[] playerHasShieldInCell) {
        for (Asset asset : targetedAssets) {
            asset.decreaseHp(sameHeightDamageDividedToTargets);
            if (playerHasShieldInCell[asset.getOwnerNumber().getNumber()])
                asset.decreaseHp(airDamageDividedToTargets / Offensive.decreasingFactorForAirDamageDueToShield);
            else asset.decreaseHp(airDamageDividedToTargets);
        }
    }

    public static DamageStruct findDamage(HeightOfAsset heightOfAttacker, Offensive attacker,
                                          Map map, Pair<Integer, Integer> target, boolean setHasAttacked) {

        DamageStruct damageStruct = new DamageStruct();

        Offensive.AttackingResult attackingResult = attacker.canAttack(map, target.first, target.second, setHasAttacked);

        switch (attackingResult) {
            case TOO_FAR, HAS_ATTACKED -> {
                return null;
            }

            case SUCCESSFUL -> {
                if (attacker instanceof OffensiveWeapons offensiveWeapons) {
                    OffensiveWeaponsType type = offensiveWeapons.getOffensiveWeaponsType();
                    if (type.equals(OffensiveWeaponsType.GATE_DESTROYER)) {
                        damageStruct.onlyBuildingDamage += formulatedDamage(attacker);
                        break;
                    }
                    else if (type.equals(OffensiveWeaponsType.FIRE_STONE_THROWER)) {
                        damageStruct.nonBuildingDamage += formulatedDamage(attacker);
                        break;
                    }
                }
                if (attacker.isArcherType())
                    damageStruct.airDamage += formulatedDamage(attacker);
                else if (heightOfAttacker.equals(HeightOfAsset.UP))
                    damageStruct.upDamage += formulatedDamage(attacker);
                else if (heightOfAttacker.equals(HeightOfAsset.MIDDLE))
                    damageStruct.middleDamage += formulatedDamage(attacker);
                else if (heightOfAttacker.equals(HeightOfAsset.GROUND))
                    damageStruct.groundDamage += formulatedDamage(attacker);
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


    //todo attack complete



    public static int pourOil(ArrayList<Offensive> engineersWithOil) {

        GameData gameData = GameController.getGameData();
        Map map = gameData.getMap();

        if (engineersWithOil.size() == 0)
            return 0;

        int successes = 0;
        for (Offensive engineer : engineersWithOil) {
            int deltaX=gameData.getDestinationCellPosition().first-((Asset)engineer).getPositionX();
            int deltaY=gameData.getDestinationCellPosition().second-((Asset)engineer).getPositionY();

            Direction direction;
            if(deltaX==0 && deltaY>0) direction=Direction.DOWN;
            else if(deltaX==0 && deltaY<0) direction=Direction.UP;
            else if(deltaX>0 && deltaY==0) direction=Direction.RIGHT;
            else if(deltaX<0 && deltaY==0) direction=Direction.LEFT;
            else continue;

            if (oneUnitPourOil(engineer, direction, 0).equals(SelectUnitMenuMessages.SUCCESSFUL))
                successes++;
        }

        return successes;
    }

    public static SelectUnitMenuMessages oneUnitPourOil(Offensive engineerWithOil, Direction direction, int minimumUnitToAttack) {

        GameData gameData = GameController.getGameData();
        Map map = gameData.getMap();

        int deltaX = 0, deltaY = 0;
        switch (direction) {
            case UP -> deltaY=1;
            case DOWN -> deltaY=-1;
            case RIGHT -> deltaX=1;
            case LEFT -> deltaX=-1;
        }

        Asset engineerAsset=((Asset)engineerWithOil);
        int currentX=engineerAsset.getPositionX();
        int currentY=engineerAsset.getPositionY();

        for (int i = 1; i <= SoldierType.ENGINEER_WITH_OIL.getAimRange(); i++) {
            if (!map.isIndexValid(currentX + deltaX * i, currentY + deltaY * i))
                continue;

            Cell targetedCell = map.getCells()[currentX + deltaX * i][currentY + deltaY * i];
            ArrayList<Asset> enemies = targetedCell.getEnemiesOfPlayerInCell(gameData.getPlayerOfTurn());

            Offensive.AttackingResult attackingResult = engineerWithOil.canAttack
                    (map, currentX + deltaX * i, currentY + deltaY * i, false);
            switch (attackingResult) {
                case HAS_ATTACKED:
                    return SelectUnitMenuMessages.HAS_ATTACKED;
                case NO_ENEMY_THERE:
                    break;
                case SUCCESSFUL:
                    if (targetedCell.getEnemiesOfPlayerInCell(engineerAsset.getOwnerNumber()).size() < minimumUnitToAttack)
                        break;

                    makeOneUnitAttack(engineerWithOil,new Pair<>(currentX + deltaX * i,currentY + deltaY * i));

                    return SelectUnitMenuMessages.SUCCESSFUL;
            }
        }
        return SelectUnitMenuMessages.NO_ENEMY_THERE;
    }

    public static SelectUnitMenuMessages digTunnel(int x, int y) {

        GameData gameData = GameController.getGameData();
        Map map = gameData.getMap();

        if (!map.isIndexValid(x, y))
            return SelectUnitMenuMessages.INVALID_INDEX;

        Cell currentCell = map.getCells()[gameData.getStartSelectedCellsPosition().first][gameData.getStartSelectedCellsPosition().first];

        ArrayList<Offensive> attackingUnits = currentCell.getAttackingListOfPlayerNumber(gameData.getPlayerOfTurn());
        Soldier tunneler = null;
        for (Offensive attacker : attackingUnits)
            if (attacker instanceof Soldier soldier && soldier.getSoldierType().equals(SoldierType.TUNNELER)) {
                tunneler = soldier;
                break;
            }

        if (tunneler == null)
            return SelectUnitMenuMessages.NO_PROPER_UNIT;

        Offensive.AttackingResult attackingResult = tunneler.canAttack(map, x, y, true);
        if (attackingResult.equals(Offensive.AttackingResult.TOO_FAR))
            return SelectUnitMenuMessages.TOO_FAR;
        else if (attackingResult.equals(Offensive.AttackingResult.HAS_ATTACKED))
            return SelectUnitMenuMessages.HAS_ATTACKED;


        if (currentCell.hasBuilding()) {
            Building building = currentCell.getBuilding();
            if (building instanceof OtherBuildings otherBuildings)
                if (otherBuildings.getOtherBuildingsType().equals(OtherBuildingsType.MOAT))
                    return SelectUnitMenuMessages.INVALID_PLACE_FOR_DIGGING_TUNNEL;

            if (building instanceof AttackingBuilding)
                return SelectUnitMenuMessages.INVALID_PLACE_FOR_DIGGING_TUNNEL;
        }

        currentCell.setHasTunnel(true);

        boolean[][] mark = new boolean[map.getWidth() + 1][map.getWidth() + 1];
        for (boolean[] array : mark)
            Arrays.fill(array, false);

        int numberOfAdjacentTunnelsPlusNewTunnel = dfs(map, new Pair(x, y), mark);

        if (numberOfAdjacentTunnelsPlusNewTunnel >= maximumLengthOfTunnel ||
                currentCell.hasBuilding() ||
                currentCell.getSiegeTowerInMovingUnits() != null)
            destroyTunnels(map, new Pair(x, y));

        return SelectUnitMenuMessages.SUCCESSFUL;
    }

    private static void destroyTunnels(Map map, Pair<Integer, Integer> thisCellCoordination) {
        Cell currentCell = map.getCells()[thisCellCoordination.first][thisCellCoordination.second];

        //destroy tunnel
        currentCell.setHasTunnel(false);
        currentCell.setBuilding(null);
        Asset siegeTower;
        while ((siegeTower = currentCell.getSiegeTowerInMovingUnits()) != null)
            currentCell.getMovingObjects().remove(siegeTower);

        //destroy other tunnels
        if (thisCellCoordination.first < map.getWidth() &&
                map.getCells()[thisCellCoordination.first + 1][thisCellCoordination.second].hasTunnel())
            destroyTunnels(map, new Pair(thisCellCoordination.first + 1, thisCellCoordination.second));
        if (thisCellCoordination.second < map.getWidth() &&
                map.getCells()[thisCellCoordination.first][thisCellCoordination.second + 1].hasTunnel())
            destroyTunnels(map, new Pair(thisCellCoordination.first, thisCellCoordination.second + 1));
        if (thisCellCoordination.first > 1 &&
                map.getCells()[thisCellCoordination.first - 1][thisCellCoordination.second].hasTunnel())
            destroyTunnels(map, new Pair(thisCellCoordination.first - 1, thisCellCoordination.second));
        if (thisCellCoordination.second > 1 &&
                map.getCells()[thisCellCoordination.first][thisCellCoordination.second - 1].hasTunnel())
            destroyTunnels(map, new Pair(thisCellCoordination.first, thisCellCoordination.second - 1));
    }

    private static int dfs(Map map, Pair<Integer, Integer> thisCellCoordination, boolean[][] mark) {
        int numberOfAdjacentTunnels = 1;
        mark[thisCellCoordination.first][thisCellCoordination.second] = true;

        if (thisCellCoordination.first < map.getWidth() &&
                map.getCells()[thisCellCoordination.first + 1][thisCellCoordination.second].hasTunnel() &&
                !mark[thisCellCoordination.first + 1][thisCellCoordination.second])
            numberOfAdjacentTunnels += dfs
                    (map, new Pair(thisCellCoordination.first + 1, thisCellCoordination.second), mark);

        if (thisCellCoordination.second < map.getWidth() &&
                map.getCells()[thisCellCoordination.first][thisCellCoordination.second + 1].hasTunnel() &&
                !mark[thisCellCoordination.first][thisCellCoordination.second + 1])
            numberOfAdjacentTunnels += dfs
                    (map, new Pair(thisCellCoordination.first, thisCellCoordination.second + 1), mark);

        if (thisCellCoordination.first > 1 &&
                map.getCells()[thisCellCoordination.first - 1][thisCellCoordination.second].hasTunnel() &&
                !mark[thisCellCoordination.first - 1][thisCellCoordination.second])
            numberOfAdjacentTunnels += dfs
                    (map, new Pair(thisCellCoordination.first - 1, thisCellCoordination.second), mark);

        if (thisCellCoordination.second > 1 &&
                map.getCells()[thisCellCoordination.first][thisCellCoordination.second - 1].hasTunnel() &&
                !mark[thisCellCoordination.first][thisCellCoordination.second - 1])
            numberOfAdjacentTunnels += dfs
                    (map, new Pair(thisCellCoordination.first, thisCellCoordination.second - 1), mark);

        return numberOfAdjacentTunnels;
    }

    public static String buildEquipment(String equipmentName) {
        GameData gameData = GameController.getGameData();
        int x = 1;
        int y = 1;//todo
        Cell selectedCell = gameData.getMap().getCells()[x][y];
        PlayerNumber currentPlayer = gameData.getPlayerOfTurn();

        Weapon weapon = Weapon.createWeaponByWeaponTypeString(equipmentName, currentPlayer, x, y);
        if (weapon == null)
            return "This is not a valid name of an equipment!";

        ArrayList<Movable> movingObjects = selectedCell.getMovingObjectsOfPlayer(currentPlayer);

        ArrayList<Movable> engineers = new ArrayList<>();
        for (Movable movingUnit : movingObjects)
            if (movingUnit instanceof Soldier soldier &&
                    soldier.getSoldierType().equals(SoldierType.ENGINEER) &&
                    soldier.getOwnerNumber().equals(currentPlayer))
                engineers.add(movingUnit);

        if (engineers.size() < weapon.getBuilderNeededCount())
            return "You do not have enough engineers here to build this equipment!";

        if (weapon instanceof Trap)
            if (selectedCell.hasBuilding() || selectedCell.getMovingObjects().size() > weapon.getBuilderNeededCount())
                return "You can not build a trap while there are some units here!";

        for (int i = 0; i < weapon.getBuilderNeededCount(); i++)
            selectedCell.getMovingObjects().remove((Asset) (engineers.get(i)));

        if (weapon instanceof Trap trap) {
            selectedCell.setTrap(trap);
        }
        else {
            selectedCell.getMovingObjects().add(weapon);
        }
        return "Equipment was successfully built!";
    }

    public static void disbandUnits(ArrayList<Human> humans){
        for(Human human:humans)
            disbandOneUnit(human);
    }
    public static void disbandOneUnit(Human human) {
        GameData gameData = GameController.getGameData();
        int x = human.getPositionX();
        int y = human.getPositionY();

        Cell selectedCell = gameData.getMap().getCells()[x][y];
        selectedCell.getMovingObjects().remove(human);

        Empire empire = gameData.getEmpireByPlayerNumber(human.getOwnerNumber());
        empire.changeWorklessPopulation(1);
    }

    public static int ladderMenDropLadders(ArrayList<Soldier> ladderMen){
        int failures=0;
        for(Soldier ladderMan:ladderMen)
            if(!dropLadder(ladderMan))
                failures++;

        return failures;
    }

    public static boolean dropLadder(Soldier ladderMan) {

        if(!ladderMan.getSoldierType().equals(SoldierType.LADDER_MAN))
            return false;

        GameData gameData = GameController.getGameData();
        int currentX = ladderMan.getPositionX();
        int currentY = ladderMan.getPositionY();
        Cell currentCell = gameData.getMap().getCells()[currentX][currentY];

        if (currentCell.hasBuilding() || currentCell.hasTrap() || !currentCell.getCellType().isAbleToBuildOn(OtherBuildingsType.LADDER.getBuildingType().name()))
            return false;

        //successful
        currentCell.makeBuilding(OtherBuildingsType.LADDER.getBuildingType().name(), gameData.getPlayerOfTurn());
        disbandOneUnit(ladderMan);

        return true;
    }

    static class DamageStruct {
        public int groundDamage = 0;
        public int middleDamage = 0;
        public int upDamage = 0;
        public int airDamage = 0;
        public int onlyBuildingDamage = 0;
        public int nonBuildingDamage = 0;
        public int failures = 0;

        public void add(DamageStruct secondDamageStruct) {
            groundDamage += secondDamageStruct.groundDamage;
            middleDamage += secondDamageStruct.middleDamage;
            upDamage += secondDamageStruct.upDamage;
            airDamage += secondDamageStruct.airDamage;
            failures += secondDamageStruct.failures;
            onlyBuildingDamage += secondDamageStruct.onlyBuildingDamage;
            nonBuildingDamage += secondDamageStruct.nonBuildingDamage;
        }
    }
}
