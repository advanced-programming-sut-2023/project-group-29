package model;

public interface Offensive
{
    public enum AttackingResult{
        SUCCESSFUL,
        INVALID_INDEX,
        //EMPTY_CELL,
        TOO_FAR
    }

    public AttackingResult attack(Map map, int targetX, int targetY);

    public static AttackingResult attack(Map map, Asset asset, int aimRange, int targetX, int targetY)
    {
        int currentX=asset.getPositionX();
        int currentY=asset.getPositionY();

        if (targetX < 1 || targetY < 1 || targetX > map.getWidth() || targetY > map.getWidth())
            return AttackingResult.INVALID_INDEX;

        Cell targetCell = map.getCells()[targetX][targetY];

        int destinationDistance = map.distanceOfTwoCellsForAttacking(currentX, currentY, targetX,targetY);

        if (destinationDistance > aimRange)
            return AttackingResult.TOO_FAR;

        //TODO extra distance for taller towers

        return AttackingResult.SUCCESSFUL;
    }
    public int getDamage();
}
