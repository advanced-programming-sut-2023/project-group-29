package model.people.humanTypes;

import model.people.HumanType;

public enum SoldierType {
    //TODO abbadfar complete below list
    ;
    private final int damage;
    private final int aimRange;
    private final HumanType humanType;

    SoldierType(HumanType humanType, int damage, int aimRange) {
        this.humanType = humanType;
        this.damage = damage;
        this.aimRange = aimRange;
    }

    public int getDamage() {
        return damage;
    }

    public int getAimRange() {
        return aimRange;
    }

    public HumanType getHumanType() {
        return humanType;
    }
}
