package model.weapons;

public class WeaponTypes {
    private final Weapon.BuilderType builderType;
    private final int speed;
    private final int hp;
    private final int builderNeededCount;

    public WeaponTypes(Weapon.BuilderType builderType, int speed, int hp, int builderNeededCount) {
        this.builderType = builderType;
        this.speed = speed;
        this.hp = hp;
        this.builderNeededCount = builderNeededCount;
    }

    public Weapon.BuilderType getBuilderType() {
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
}
