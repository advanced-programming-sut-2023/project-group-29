package model.weapons.weaponClasses;

import controller.menucontrollers.GameMenuController;
import model.Offensive;
import model.PlayerNumber;
import model.UnitState;
import model.buildings.buildingClasses.AttackingBuilding;
import model.map.Cell;
import model.map.Map;
import model.weapons.Weapon;
import model.weapons.weaponTypes.StaticOffensiveWeaponsType;

public class StaticOffensiveWeapons extends Weapon implements Offensive {
    private final int damage;
    private final int aimRange;
    private final StaticOffensiveWeaponsType staticOffensiveWeaponsType;
    private boolean attackedThisTurn = false;
    private UnitState unitState = UnitState.STANDING;

    public StaticOffensiveWeapons(StaticOffensiveWeaponsType staticOffensiveWeaponsType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(staticOffensiveWeaponsType.getWeaponTypes(), playerNumber, positionX, positionY);

        this.staticOffensiveWeaponsType = staticOffensiveWeaponsType;
        this.damage = staticOffensiveWeaponsType.getDamage();
        this.aimRange = staticOffensiveWeaponsType.getAimRange();
    }

    public Offensive.AttackingResult canAttack(Map map, int targetX, int targetY, boolean setHasAttacked) {
        return Offensive.canAttack(map, this, targetX, targetY, setHasAttacked);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    public int getAimRange() {
        if (isArcherType()) {
            Cell currentCell = GameMenuController.getGameData().getMap().getCells()[getPositionX()][getPositionY()];
            if (currentCell.hasBuilding() && currentCell.getBuilding() instanceof AttackingBuilding attackingBuilding)
                return Math.max(aimRange, attackingBuilding.getFireRange());
        }
        return aimRange;
    }

    public boolean isArcherType() {
        return true;
    }

    public boolean hasAttackedThisTurn() {
        return attackedThisTurn;
    }

    public StaticOffensiveWeaponsType getStaticOffensiveWeaponsType() {
        return staticOffensiveWeaponsType;
    }

    public void setAttackedThisTurn(boolean attackedThisTurn) {
        this.attackedThisTurn = attackedThisTurn;
    }

    @Override
    public UnitState getUnitState() {
        return unitState;
    }

    @Override
    public void setUnitState(UnitState unitState) {
        this.unitState = unitState;
    }
}
