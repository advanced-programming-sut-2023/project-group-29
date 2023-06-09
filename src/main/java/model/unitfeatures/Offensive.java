package model.unitfeatures;

import model.Asset;
import model.map.Cell;
import model.map.Map;
import model.weapons.weaponClasses.StaticOffensiveWeapons;
import model.weapons.weaponTypes.StaticOffensiveWeaponsType;

import java.util.ArrayList;

public interface Offensive {
    int decreasingFactorForAirDamageDueToShield = 2;

    static AttackingResult canAttack(Map map, Asset asset, int targetX, int targetY, boolean setHasAttacked) {
        int currentX = asset.getPositionX();
        int currentY = asset.getPositionY();

        if (targetX < 1 || targetY < 1 || targetX > map.getWidth() || targetY > map.getWidth())
            return AttackingResult.INVALID_INDEX;

        if (((Offensive) asset).hasAttackedThisTurn())
            return AttackingResult.HAS_ATTACKED;

        Cell targetCell = map.getCells()[targetX][targetY];

        int destinationDistance;
        if (asset instanceof StaticOffensiveWeapons &&
                ((StaticOffensiveWeapons) asset).getStaticOffensiveWeaponsType().equals(StaticOffensiveWeaponsType.CATAPULT_WITH_BALLAST))
            destinationDistance = map.distanceOfTwoCellsForAttacking(currentX, currentY, targetX, targetY, true);
        else
            destinationDistance = map.distanceOfTwoCellsForAttacking(currentX, currentY, targetX, targetY, false);


        if (destinationDistance > ((Offensive) asset).getAimRange() || destinationDistance == -1)
            return AttackingResult.TOO_FAR;
        if (targetCell.getEnemiesOfPlayerInCell(asset.getOwnerNumber()).size() == 0 &&
                (!targetCell.hasBuilding() || targetCell.getBuilding().getOwnerNumber().equals(asset.getOwnerNumber())))
            return AttackingResult.NO_ENEMY_THERE;

        if (setHasAttacked)
            ((Offensive) asset).setAttackedThisTurn(true);

        return AttackingResult.SUCCESSFUL;
    }

    static ArrayList<Offensive> getOffensivesOfUnits(ArrayList<Asset> units) {
        ArrayList<Offensive> attackers = new ArrayList<>();
        for (Asset asset : units)
            if (asset instanceof Offensive attacker)
                attackers.add(attacker);

        return attackers;
    }

    AttackingResult canAttack(Map map, int targetX, int targetY, boolean setHasAttacked);

    boolean hasAttackedThisTurn();

    void setAttackedThisTurn(boolean attackedThisTurn);

    int getDamage();

    UnitState getUnitState();

    void setUnitState(UnitState unitState);

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
