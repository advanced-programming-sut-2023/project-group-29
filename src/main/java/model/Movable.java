package model;

import model.map.Cell;
import model.map.Map;
import model.people.humanClasses.Soldier;
import model.people.humanTypes.SoldierType;
import org.checkerframework.checker.units.qual.A;

public interface Movable {
    static MovingResult move(Map map, Asset asset, int speed, boolean ableToClimbLadder, int destinationX, int destinationY) {

        MovingResult movingResult=checkForMoveErrors(map,asset,speed,ableToClimbLadder,destinationX,destinationY);

        if(movingResult.equals(MovingResult.SUCCESSFUL))
            finalTransfer(asset,destinationX,destinationY);

        return movingResult;
    }

    public static MovingResult checkForMoveErrors(Map map, Asset asset, int speed, boolean ableToClimbLadder, int destinationX, int destinationY)
    {
        int currentX = asset.getPositionX();
        int currentY = asset.getPositionY();

        if (!map.isIndexValid(destinationX, destinationY))
            return MovingResult.INVALID_INDEX;

        if(((Movable)asset).hasMovedThisTurn())
            return MovingResult.HAS_MOVED;

        Cell destinationCell = map.getCells()[destinationX][destinationY];
        if (!destinationCell.isAbleToMoveOn())
            return MovingResult.BAD_PLACE;
        if (destinationCell.hasBuilding() && !destinationCell.getBuilding().getOwnerNumber().equals(asset.getOwnerNumber()))
            return MovingResult.BAD_PLACE;
        if (destinationCell.getTrap() != null && destinationCell.getTrap().getOwnerNumber().equals(asset.getOwnerNumber()))
            return MovingResult.BAD_PLACE;

        int destinationDistance;
        if(asset instanceof Soldier &&
                ((Soldier)asset).getSoldierType().equals(SoldierType.ASSASSIN))
            destinationDistance = map.distanceOfTwoCellsForMoving(currentX, currentY, destinationX, destinationY, ableToClimbLadder,true);
        else
            destinationDistance = map.distanceOfTwoCellsForMoving(currentX, currentY, destinationX, destinationY, ableToClimbLadder,false);


        if (destinationDistance > speed)
            return MovingResult.TOO_FAR;
        //todo abbasfar if distance is -1 you cant go there

        return MovingResult.SUCCESSFUL;
    }
    private static void finalTransfer(Asset asset,int destinationX,int destinationY)
    {
        asset.setPositionX(destinationX);
        asset.setPositionY(destinationY);

        ((Movable)asset).setMovedThisTurn(true);
    }

    MovingResult move(Map map, int destinationX, int destinationY);
    MovingResult checkForMoveErrors(Map map, int destinationX, int destinationY);

    void setMovedThisTurn(boolean movedThisTurn);

    boolean hasMovedThisTurn();
    Patrol getPatrol();
    enum MovingResult {
        SUCCESSFUL,
        INVALID_INDEX,
        BAD_PLACE,
        HAS_MOVED,
        TOO_FAR
    }
}
