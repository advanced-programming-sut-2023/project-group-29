package model;

import model.map.Cell;
import model.map.Map;
import model.weapons.weaponClasses.StaticOffensiveWeapons;
import model.weapons.weaponTypes.StaticOffensiveWeaponsType;

public interface Offensive {
    int decreasingFactorForAirDamageDueToShield = 1;  //TODO reasonable value

    static AttackingResult canAttack(Map map, Asset asset, int aimRange, int targetX, int targetY) {
        int currentX = asset.getPositionX();
        int currentY = asset.getPositionY();

        if (targetX < 1 || targetY < 1 || targetX > map.getWidth() || targetY > map.getWidth())
            return AttackingResult.INVALID_INDEX;

        Cell targetCell = map.getCells()[targetX][targetY];

        int destinationDistance;
        if(asset instanceof StaticOffensiveWeapons &&
                ((StaticOffensiveWeapons)asset).getStaticOffensiveWeaponsType().equals(StaticOffensiveWeaponsType.CATAPULT_WITH_BALLAST))
            destinationDistance = map.distanceOfTwoCellsForAttacking(currentX, currentY, targetX, targetY,true);
        else
            destinationDistance = map.distanceOfTwoCellsForAttacking(currentX, currentY, targetX, targetY,false);


        if (destinationDistance > aimRange)
            return AttackingResult.TOO_FAR;

        //TODO extra distance for taller towers
        return AttackingResult.SUCCESSFUL;
    }

    AttackingResult canAttack(Map map, int targetX, int targetY);

    int getDamage();

    boolean isArcherType();

    enum AttackingResult {
        SUCCESSFUL,
        INVALID_INDEX,
        TOO_FAR
    }
}
