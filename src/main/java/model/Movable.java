package model;

import model.map.Cell;
import model.map.CellType;
import model.map.Map;

import java.util.ArrayList;

public interface Movable {
    static MovingResult move(Map map, Asset asset, int destinationX, int destinationY) {

        MovingResult movingResult = checkForMoveErrors(map, asset, destinationX, destinationY);

        if (movingResult.equals(MovingResult.SUCCESSFUL))
            finalTransfer(asset, destinationX, destinationY);

        return movingResult;
    }

    static MovingResult checkForMoveErrors(Map map, Asset asset, int destinationX, int destinationY) {
        int currentX = asset.getPositionX();
        int currentY = asset.getPositionY();

        Movable movableUnit = (Movable) asset;

        if (!map.isIndexValid(destinationX, destinationY))
            return MovingResult.INVALID_INDEX;

        Cell currentCell = map.getCells()[currentX][currentY];

        ArrayList<Pair<Integer, Integer>> path =
                map.pathOfTwoCellsForMoving(movableUnit, currentX, currentY, destinationX, destinationY);

        if (path == null)
            return MovingResult.BAD_PLACE;

        if (movableUnit.hasMovedThisTurn())
            return MovingResult.HAS_MOVED;

        int formulatedSpeedBecauseOfWaterInCell =
                currentCell.getCellType().equals(CellType.SMALL_POND) ? movableUnit.getSpeed() / 2 + 1 : movableUnit.getSpeed();

        if (path.size() > formulatedSpeedBecauseOfWaterInCell)
            return MovingResult.TOO_FAR;

        return MovingResult.SUCCESSFUL;
    }
    static ArrayList<Pair<Integer,Integer>> pathToDestination(Map map, Asset asset, int destinationX, int destinationY){
        int currentX = asset.getPositionX();
        int currentY = asset.getPositionY();

        Movable movableUnit = (Movable) asset;

        return map.pathOfTwoCellsForMoving(movableUnit, currentX, currentY, destinationX, destinationY);
    }

    private static void finalTransfer(Asset asset, int destinationX, int destinationY) {
        asset.setPositionX(destinationX);
        asset.setPositionY(destinationY);

        ((Movable) asset).setMovedThisTurn(true);
    }

    MovingResult move(Map map, int destinationX, int destinationY);

    MovingResult checkForMoveErrors(Map map, int destinationX, int destinationY);

    void setMovedThisTurn(boolean movedThisTurn);

    boolean hasMovedThisTurn();

    Patrol getPatrol();
    boolean isAbleToClimbStairs();

    boolean isAbleToClimbLadder();

    int getSpeed();

    enum MovingResult {
        SUCCESSFUL,
        INVALID_INDEX,
        BAD_PLACE,
        HAS_MOVED,
        TOO_FAR
    }
}
