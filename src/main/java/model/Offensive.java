package model;

import model.map.Cell;
import model.map.Map;
import model.weapons.weaponClasses.StaticOffensiveWeapons;
import model.weapons.weaponTypes.StaticOffensiveWeaponsType;

public interface Offensive {
    int decreasingFactorForAirDamageDueToShield = 2;

    static AttackingResult canAttack(Map map, Asset asset, int aimRange, int targetX, int targetY) {
        int currentX = asset.getPositionX();
        int currentY = asset.getPositionY();

        if (targetX < 1 || targetY < 1 || targetX > map.getWidth() || targetY > map.getWidth())
            return AttackingResult.INVALID_INDEX;

        if(((Offensive)asset).hasAttackedThisTurn())
            return AttackingResult.HAS_ATTACKED;

        Cell targetCell = map.getCells()[targetX][targetY];

        int destinationDistance;
        if(asset instanceof StaticOffensiveWeapons &&
                ((StaticOffensiveWeapons)asset).getStaticOffensiveWeaponsType().equals(StaticOffensiveWeaponsType.CATAPULT_WITH_BALLAST))
            destinationDistance = map.distanceOfTwoCellsForAttacking(currentX, currentY, targetX, targetY,true);
        else
            destinationDistance = map.distanceOfTwoCellsForAttacking(currentX, currentY, targetX, targetY,false);


        if (destinationDistance > aimRange)
            return AttackingResult.TOO_FAR;
        if(targetCell.getEnemiesOfPlayerInCell(asset.getOwnerNumber()).size()==0 &&
                (!targetCell.hasBuilding() || targetCell.getBuilding().getOwnerNumber().equals(asset.getOwnerNumber())))
            return AttackingResult.NO_ENEMY_THERE;

        ((Offensive)asset).setAttackedThisTurn(true);
        return AttackingResult.SUCCESSFUL;
    }

    AttackingResult canAttack(Map map, int targetX, int targetY);
    boolean hasAttackedThisTurn();
    void setAttackedThisTurn(boolean attackedThisTurn);

    int getDamage();

    void setUnitState(UnitState unitState);
    UnitState getUnitState();

    boolean isArcherType();
    int getAimRange();

    enum AttackingResult {
        SUCCESSFUL,
        INVALID_INDEX,
        HAS_ATTACKED,
        NO_ENEMY_THERE,
        TOO_FAR
    }
}
