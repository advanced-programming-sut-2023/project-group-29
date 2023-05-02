package model.weapons;

import model.Asset;
import model.PlayerNumber;

public class Weapon extends Asset {
    protected BuilderType builderType;
    protected int builderNeededCount;
    private String name;

    protected Weapon(WeaponTypes weaponTypes, PlayerNumber playerNumber, int positionX, int positionY) {
        super(playerNumber, positionX, positionY);
        this.hp = weaponTypes.hp();
        this.name=weaponTypes.name();
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

    public static Weapon createWeaponByWeaponTypeString(String string)
    {
        return null;//todo abbasfar
        //todo abbasfar similarly for human
    }

    public String getName() {
        return name;
    }
}
