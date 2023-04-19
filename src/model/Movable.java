package model;

public interface Movable {
    static MovingResult move(Map map, Asset asset, int speed, boolean ableToClimbLadder, int destinationX, int destinationY) {
        int currentX = asset.getPositionX();
        int currentY = asset.getPositionY();

        if (destinationX < 1 || destinationY < 1 || destinationX > map.getWidth() || destinationY > map.getWidth())
            return MovingResult.INVALID_INDEX;

        Cell currentCell = map.getCells()[currentX][currentY];
        if (currentCell.isAbleToMoveOn())
            return MovingResult.BAD_PLACE;
        if (!currentCell.getBuilding().getOwnerNumber().equals(asset.getOwnerNumber()))
            return MovingResult.BAD_PLACE;
        if (currentCell.getTrap() != null && currentCell.getTrap().getOwnerNumber().equals(asset.getOwnerNumber()))
            return MovingResult.BAD_PLACE;

        int destinationDistance = map.distanceOfTwoCells(currentX, currentY, destinationX, destinationY, ableToClimbLadder);

        if (destinationDistance > speed)
            return MovingResult.TOO_FAR;

        asset.setPositionX(destinationX);
        asset.setPositionY(destinationY);

        return MovingResult.SUCCESSFUL;
    }

    MovingResult move(Map map, int destinationX, int destinationY);

    enum MovingResult {
        SUCCESSFUL,
        INVALID_INDEX,
        BAD_PLACE,
        TOO_FAR
    }
}
