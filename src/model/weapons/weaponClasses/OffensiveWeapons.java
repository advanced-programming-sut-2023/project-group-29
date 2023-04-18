package model.weapons.weaponClasses;

import model.PlayerNumber;
import model.weapons.weaponTypes.OffensiveWeaponsType;
import model.weapons.Weapon;

public class OffensiveWeapons extends Weapon {
    private final int damage;
    private final int aimRange;
    private final OffensiveWeaponsType offensiveWeaponsType;

    public OffensiveWeapons(OffensiveWeaponsType offensiveWeaponsType, PlayerNumber playerNumber,int positionX,int positionY) {
        super(offensiveWeaponsType.getWeaponTypes(), playerNumber,positionX,positionY);

        this.offensiveWeaponsType = offensiveWeaponsType;
        this.damage = offensiveWeaponsType.getDamage();
        this.aimRange = offensiveWeaponsType.getAimRange();
    }
}
