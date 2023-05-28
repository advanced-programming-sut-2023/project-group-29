package model.map;

import model.*;
import model.buildings.buildingClasses.AttackingBuilding;
import model.buildings.buildingClasses.OtherBuildings;
import model.buildings.buildingTypes.AttackingBuildingType;
import model.buildings.buildingTypes.OtherBuildingsType;
import model.people.humanClasses.Soldier;
import model.people.humanTypes.SoldierType;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Map {
    private final Cell[][] cells;
    private final int width;
    private final int usersCount;
    private final int assassinVisibilityRadius = 10;

    public Map(int width, int usersCount) {
        this.width = width;
        this.cells = new Cell[width + 1][width + 1];
        this.usersCount = usersCount;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int distanceOfTwoCellsForMoving(Movable movingUnit, int firstX, int firstY, int secondX, int secondY) {
        return BFSForMoving(movingUnit, firstX, firstY, secondX, secondY);
    }

    public int distanceOfTwoCellsForAttacking
            (int firstX, int firstY, int secondX, int secondY, boolean overTheWallAllowed) {
        return BFSForAttacking(firstX, firstY, secondX, secondY, overTheWallAllowed);
    }

    private int BFSForMoving(Movable movableUnit, int firstX, int firstY, int secondX, int secondY) {

        Queue<Pair> queue = new LinkedList<>();

        int[][] distances = new int[width + 1][width + 1];
        for (int[] array : distances)
            Arrays.fill(array, -1);

        queue.add(new Pair(firstX, firstY));
        distances[firstX][firstY] = 0;

        while (!queue.isEmpty()) {
            Pair<Integer,Integer> thisCellCoordination = queue.poll();
            if (thisCellCoordination.first == secondX && thisCellCoordination.second == secondY)
                break;

            Cell thisCell = cells[thisCellCoordination.first][thisCellCoordination.second];

            for (Direction direction : Direction.values()) {
                Pair<Integer,Integer> addingCellCoordination;

                if (direction.equals(Direction.RIGHT) && thisCellCoordination.first < width)
                    addingCellCoordination = new Pair(thisCellCoordination.first + 1, thisCellCoordination.second);
                else if (direction.equals(Direction.DOWN) && thisCellCoordination.second < width)
                    addingCellCoordination = new Pair(thisCellCoordination.first, thisCellCoordination.second + 1);
                else if (direction.equals(Direction.LEFT) && thisCellCoordination.first > 1)
                    addingCellCoordination = new Pair(thisCellCoordination.first - 1, thisCellCoordination.second);
                else if (direction.equals(Direction.UP) && thisCellCoordination.second > 1)
                    addingCellCoordination = new Pair(thisCellCoordination.first, thisCellCoordination.second - 1);
                else
                    continue;

                if (distances[addingCellCoordination.first][addingCellCoordination.second] != -1)  //has been marked
                    continue;

                Cell addingCell = cells[addingCellCoordination.first][addingCellCoordination.second];

                if (!addingCell.getCellType().isAbleToMoveOn())
                    continue;

                Asset movableAsset = (Asset) movableUnit;

                boolean isUnitAssassin = movableAsset instanceof Soldier soldier && soldier.getSoldierType().equals(SoldierType.ASSASSIN);

                int firstCellHeight = thisCell.heightOfUnitsOfPlayer().getHeightNumber();
                int secondCellHeight = addingCell.heightOfUnitsOfPlayer().getHeightNumber();

                boolean firstCellCanGoHigher =
                        (thisCell.goHigherLevel().equals(Cell.LevelChanger.LADDER)
                                && movableUnit.isAbleToClimbLadder())
                                || (thisCell.goHigherLevel().equals(Cell.LevelChanger.STAIRS)
                                && movableUnit.isAbleToClimbStairs());
                boolean secondCellCanGoHigher =
                        (addingCell.goHigherLevel().equals(Cell.LevelChanger.LADDER)
                                && movableUnit.isAbleToClimbLadder())
                                || (addingCell.goHigherLevel().equals(Cell.LevelChanger.STAIRS)
                                && movableUnit.isAbleToClimbStairs());

                //check if has big tower so that every unit can go higher
                if (thisCell.hasBuilding() && thisCell.getBuilding() instanceof AttackingBuilding attackingBuilding) {
                    AttackingBuildingType type = attackingBuilding.getAttackingBuildingType();
                    if (type.equals(AttackingBuildingType.CIRCLE_TOWER) || type.equals(AttackingBuildingType.SQUARE_TOWER))
                        if (addingCell.goHigherLevel().equals(Cell.LevelChanger.STAIRS))
                            secondCellCanGoHigher = true;
                }
                //for the other one
                if (addingCell.hasBuilding() && addingCell.getBuilding() instanceof AttackingBuilding attackingBuilding) {
                    AttackingBuildingType type = attackingBuilding.getAttackingBuildingType();
                    if (type.equals(AttackingBuildingType.CIRCLE_TOWER) || type.equals(AttackingBuildingType.SQUARE_TOWER))
                        if (thisCell.goHigherLevel().equals(Cell.LevelChanger.STAIRS))
                            firstCellCanGoHigher = true;
                }


                if (!isUnitAssassin) {
                    if (firstCellHeight > secondCellHeight && !secondCellCanGoHigher)
                        continue;
                    if (firstCellHeight < secondCellHeight && !firstCellCanGoHigher)
                        continue;
                }

                if (addingCell.hasBuilding() && addingCell.getBuilding() instanceof OtherBuildings otherBuildings)
                    if (otherBuildings.getOtherBuildingsType().equals(OtherBuildingsType.MOAT))
                        if (!movableUnit.isAbleToClimbLadder())
                            continue;

                if (addingCell.hasTrap() && addingCell.getTrap().getOwnerNumber().equals(movableAsset.getOwnerNumber()))
                    continue;

                queue.add(addingCellCoordination);
                distances[addingCellCoordination.first][addingCellCoordination.second] =
                        distances[thisCellCoordination.first][thisCellCoordination.second] + 1;
            }
        }

        return distances[secondX][secondY];
    }

    private int BFSForAttacking(int firstX, int firstY, int secondX, int secondY, boolean overWallAllowed) {

        Queue<Pair> queue = new LinkedList<>();

        int[][] distances = new int[width + 1][width + 1];
        for (int[] array : distances)
            Arrays.fill(array, -1);

        queue.add(new Pair(firstX, firstY));
        distances[firstX][firstY] = 0;

        while (!queue.isEmpty()) {
            Pair<Integer,Integer> thisCellCoordination = queue.poll();
            if (thisCellCoordination.first == secondX && thisCellCoordination.second == secondY)
                break;

            for (Direction direction : Direction.values()) {
                Pair<Integer,Integer> addingCellCoordination;

                if (direction.equals(Direction.RIGHT) && thisCellCoordination.first < width)
                    addingCellCoordination = new Pair(thisCellCoordination.first + 1, thisCellCoordination.second);
                else if (direction.equals(Direction.DOWN) && thisCellCoordination.second < width)
                    addingCellCoordination = new Pair(thisCellCoordination.first, thisCellCoordination.second + 1);
                else if (direction.equals(Direction.LEFT) && thisCellCoordination.first > 1)
                    addingCellCoordination = new Pair(thisCellCoordination.first - 1, thisCellCoordination.second);
                else if (direction.equals(Direction.UP) && thisCellCoordination.second > 1)
                    addingCellCoordination = new Pair(thisCellCoordination.first, thisCellCoordination.second - 1);
                else
                    continue;

                if (distances[addingCellCoordination.first][addingCellCoordination.second] != -1)  //has been marked
                    continue;

                Cell addingCell = cells[addingCellCoordination.first][addingCellCoordination.second];
                if (!overWallAllowed && addingCell.hasBuilding()) {
                    if (addingCell.getBuilding() instanceof OtherBuildings otherBuildings) {
                        OtherBuildingsType type = otherBuildings.getOtherBuildingsType();
                        if (type.equals(OtherBuildingsType.TALL_WALL) ||
                                type.equals(OtherBuildingsType.SHORT_WALL) ||
                                type.equals(OtherBuildingsType.WALL_WITH_STAIR))
                            continue;
                    }
                    else if (addingCell.getBuilding() instanceof AttackingBuilding)
                        continue;
                }

                queue.add(addingCellCoordination);
                distances[addingCellCoordination.first][addingCellCoordination.second] =
                        distances[thisCellCoordination.first][thisCellCoordination.second] + 1;
            }
        }

        return distances[secondX][secondY];
    }

    public boolean isAssassinSeen(int assassinCellX, int assassinCellY, PlayerNumber playerNumber) {
        for (int i = -assassinVisibilityRadius; i <= assassinVisibilityRadius; i++)
            for (int j = -assassinVisibilityRadius; j <= assassinVisibilityRadius; j++)
                if (isIndexValid(assassinCellX + i, assassinCellY + j) &&
                        (i + j) <= assassinVisibilityRadius &&
                        cells[assassinCellX + i][assassinCellY + j].getMovingObjectsOfPlayer(playerNumber).size() > 0)
                    return true;
        return false;
    }

    public int getWidth() {
        return width;
    }

    public boolean isIndexValid(int x, int y) {
        return x >= 1 && x <= width && y >= 1 && y <= width;
    }

    public int getUsersCount() {
        return usersCount;
    }

}
