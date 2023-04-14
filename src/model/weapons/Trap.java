package model.weapons;

public class Trap extends Weapon {
    private final int damage;
    private final TrapType trapType;

    public Trap(TrapType trapType) {
        super(trapType.getWeaponTypes());

        this.trapType = trapType;
        this.damage = trapType.getDamage();
    }

    public int getDamage() {
        return damage;
    }

    public TrapType getTrapType() {
        return trapType;
    }
}
