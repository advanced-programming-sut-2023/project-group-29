package model.weapons;

public class OffensiveWeapons extends Weapon {
    private final int damage;
    private final int aimRange;
    private final OffensiveWeaponsType offensiveWeaponsType;

    public OffensiveWeapons(OffensiveWeaponsType offensiveWeaponsType) {
        super(offensiveWeaponsType.getWeaponTypes());

        this.offensiveWeaponsType = offensiveWeaponsType;
        this.damage = offensiveWeaponsType.getDamage();
        this.aimRange = offensiveWeaponsType.getAimRange();
    }
}
