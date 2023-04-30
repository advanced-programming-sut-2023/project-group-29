package model;

import model.map.Cell;
import model.map.Map;

public interface Movable {
    //todo go into darvaze in what direction
    static MovingResult move(Map map, Asset asset, int speed, boolean ableToClimbLadder, int destinationX, int destinationY) {
        //TODO: some lines were too long
        int currentX = asset.getPositionX();
        int currentY = asset.getPositionY();

        if (!map.isIndexValid(destinationX, destinationY))
            return MovingResult.INVALID_INDEX;

        Cell destinationCell = map.getCells()[destinationX][destinationY];
        if (destinationCell.isAbleToMoveOn())
            return MovingResult.BAD_PLACE;
        if (!destinationCell.getBuilding().getOwnerNumber().equals(asset.getOwnerNumber()))
            return MovingResult.BAD_PLACE;
        if (destinationCell.getTrap() != null && destinationCell.getTrap().getOwnerNumber().equals(asset.getOwnerNumber()))
            return MovingResult.BAD_PLACE;

        int destinationDistance = map.distanceOfTwoCellsForMoving(currentX, currentY, destinationX, destinationY, ableToClimbLadder);

        if (destinationDistance > speed)
            return MovingResult.TOO_FAR;

        asset.setPositionX(destinationX);
        asset.setPositionY(destinationY);

        return MovingResult.SUCCESSFUL;
    }

    MovingResult move(Map map, int destinationX, int destinationY);

    //TODO handle portable tower. it should not been allowed to go in a building because it is a building
    enum MovingResult {
        SUCCESSFUL,
        INVALID_INDEX,
        BAD_PLACE,
        TOO_FAR
    }
}
