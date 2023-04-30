package model.speedanddamageenums;

public enum DamageEnum {
    NO_DAMAGE(0),
    ALMOST_ZERO(2),
    TOO_WEAK(5),
    WEAK(10),
    NORMAL(20),
    STRONG(35),
    TOO_STRONG(50);

    private final int damageValue;
    DamageEnum(int damageValue)
    {
        this.damageValue=damageValue;
    }

    public int getDamageValue() {
        return damageValue;
    }
}
