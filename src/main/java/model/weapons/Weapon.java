package model.weapons;

import model.Asset;
import model.PlayerNumber;

public class Weapon extends Asset {
    protected BuilderType builderType;
    protected int builderNeededCount;

    protected Weapon(WeaponTypes weaponTypes, PlayerNumber playerNumber, int positionX, int positionY) {
        super(playerNumber, positionX, positionY);
        this.hp = weaponTypes.hp();
        this.builderType = weaponTypes.builderType();
        this.builderNeededCount = weaponTypes.builderNeededCount();
    }

    public BuilderType getBuilderType() {
        return builderType;
    }

    public int getHp() {
        return hp;
    }

    public int getBuilderNeededCount() {
        return builderNeededCount;
    }

    public enum BuilderType {
        ENGINEER,
        ALL
    }
}
