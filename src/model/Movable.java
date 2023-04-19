package model;

public interface Movable
{
    public enum MovingResult{
        SUCCESSFUL,
        INVALID_INDEX,
        BAD_PLACE,
        TOO_FAR
    }
    public MovingResult move(Map map,int destinationX,int destinationY);

    public static MovingResult move(Map map,Asset asset,int speed,boolean ableToClimbLadder,int destinationX,int destinationY)
    {
        int currentX=asset.getPositionX();
        int currentY=asset.getPositionY();

        if (destinationX < 1 || destinationY < 1 || destinationX > map.getWidth() || destinationY > map.getWidth())
            return MovingResult.INVALID_INDEX;

        Cell destinationCell = map.getCells()[destinationX][destinationY];
        if (destinationCell.isAbleToMoveOn())
            return MovingResult.BAD_PLACE;
        if (!destinationCell.getBuilding().getOwnerNumber().equals(asset.getOwnerNumber()))
            return MovingResult.BAD_PLACE;
        if(destinationCell.getTrap()!=null && destinationCell.getTrap().getOwnerNumber().equals(asset.getOwnerNumber()))
            return MovingResult.BAD_PLACE;

        int destinationDistance = map.distanceOfTwoCellsForMoving(currentX, currentY, destinationX, destinationY, ableToClimbLadder);

        if (destinationDistance > speed)
            return MovingResult.TOO_FAR;

        asset.setPositionX(destinationX);
        asset.setPositionY(destinationY);

        return MovingResult.SUCCESSFUL;
    }
}
