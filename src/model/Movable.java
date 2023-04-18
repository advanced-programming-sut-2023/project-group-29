package model;

public interface Movable
{
    public enum MovingResult{
        SUCCESSFUL,
        INVALID_INDEX,
        BAD_PLACE,
        TOO_FAR
    }
    public MovingResult move(Map map,int currentX,int currentY,int destinationX,int destinationY);
}
