package model.weapons;

import model.Asset;
import model.PlayerNumber;

public class Weapon extends Asset {
    protected BuilderType builderType;
    protected int speed;
    protected int hp;
    protected int builderNeededCount;

    protected Weapon(WeaponTypes weaponTypes, PlayerNumber playerNumber, int positionX, int positionY) {
        super(playerNumber, positionX, positionY);
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

    public enum BuilderType {
        ENGINEER,
        LADDER_MAN,
        ALL
    }
}
