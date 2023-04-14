package model.weapons;

public class Weapon {
    protected BuilderType builderType;
    protected int speed;
    protected int hp;
    protected int builderNeededCount;
    protected Weapon(WeaponTypes weaponTypes) {
        this.hp = weaponTypes.getHp();
        this.speed = weaponTypes.getSpeed();
        this.builderType = weaponTypes.getBuilderType();
        this.builderNeededCount = weaponTypes.getBuilderNeededCount();
    }

    public BuilderType getBuilderType() {
        return builderType;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHp() {
        return hp;
    }

    public int getBuilderNeededCount() {
        return builderNeededCount;
    }

    enum BuilderType {
        ENGINEER,
        LADDER_MAN,
        ALL
    }
}
