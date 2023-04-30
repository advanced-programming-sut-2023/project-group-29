package model.people.humanTypes;

import model.people.HumanType;
import model.speedanddamageenums.AimRangeEnum;
import model.speedanddamageenums.DamageEnum;
import model.speedanddamageenums.SpeedEnum;

public enum SoldierType {
    ARCHER(new HumanType(10, false, SpeedEnum.FAST, "ARC"), DamageEnum.WEAK, DamageEnum.WEAK, AimRangeEnum.LONG_RANGE_ARCHER),
    CROSSBOWMAN(new HumanType(15, false, SpeedEnum.SLOW, "CRS"), DamageEnum.WEAK, DamageEnum.NORMAL, AimRangeEnum.SHORT_RANGE_ARCHER),
    SPEARMAN(new HumanType(15, true, SpeedEnum.NORMAL, "SPR"), DamageEnum.NORMAL, DamageEnum.TOO_WEAK, AimRangeEnum.NON_ARCHER),
    PIKEMAN(new HumanType(50, false, SpeedEnum.SLOW, "PIK"), DamageEnum.NORMAL, DamageEnum.STRONG, AimRangeEnum.NON_ARCHER),
    MACEMAN(new HumanType(15, true, SpeedEnum.NORMAL, "MAC"), DamageEnum.STRONG, DamageEnum.NORMAL, AimRangeEnum.NON_ARCHER),
    SWORDSMAN(new HumanType(40, false, SpeedEnum.TOO_SLOW, "SWR"), DamageEnum.TOO_STRONG, DamageEnum.TOO_WEAK, AimRangeEnum.NON_ARCHER),
    KNIGHT(new HumanType(45, false, SpeedEnum.TOO_FAST, "KNI"), DamageEnum.TOO_STRONG, DamageEnum.STRONG, AimRangeEnum.NON_ARCHER),
    TUNNELER(new HumanType(10, false, SpeedEnum.FAST, "TUN"), DamageEnum.NORMAL, DamageEnum.TOO_WEAK, AimRangeEnum.NON_ARCHER),
    LADDER_MAN(new HumanType(10, false, SpeedEnum.FAST, "LAD"), DamageEnum.NO_DAMAGE, DamageEnum.TOO_WEAK, AimRangeEnum.NON_ARCHER),
    ENGINEER(new HumanType(10, false, SpeedEnum.NORMAL, "ENG"), DamageEnum.NO_DAMAGE, DamageEnum.TOO_WEAK, AimRangeEnum.NON_ARCHER),
    BLACK_MONK(new HumanType(25, false, SpeedEnum.SLOW, "BMN"), DamageEnum.NORMAL, DamageEnum.NORMAL, AimRangeEnum.NON_ARCHER),
    ARCHER_BOW(new HumanType(10, false, SpeedEnum.FAST, "RCB"), DamageEnum.WEAK, DamageEnum.WEAK, AimRangeEnum.LONG_RANGE_ARCHER),
    SLAVE(new HumanType(10, false, SpeedEnum.FAST, "SLV"), DamageEnum.WEAK, DamageEnum.ALMOST_ZERO, AimRangeEnum.NON_ARCHER),
    SLINGER(new HumanType(15, false, SpeedEnum.FAST, "SLG"), DamageEnum.WEAK, DamageEnum.TOO_WEAK, AimRangeEnum.SHORT_RANGE_ARCHER),
    ASSASSIN(new HumanType(20, true, SpeedEnum.NORMAL, "ASN"), DamageEnum.NORMAL, DamageEnum.NORMAL, AimRangeEnum.NON_ARCHER),
    HORSE_ARCHER(new HumanType(35, false, SpeedEnum.TOO_FAST, "HRC"), DamageEnum.WEAK, DamageEnum.NORMAL, AimRangeEnum.LONG_RANGE_ARCHER),
    ARABIAN_SWORDSMAN(new HumanType(30, true, SpeedEnum.TOO_FAST, "RSW"), DamageEnum.STRONG, DamageEnum.STRONG, AimRangeEnum.NON_ARCHER),
    FIRE_THROWER(new HumanType(15, true, SpeedEnum.TOO_FAST, "FTR"), DamageEnum.STRONG, DamageEnum.WEAK, AimRangeEnum.SHORT_RANGE_ARCHER);
    private final int attackDamage;
    private final int defenseDamage;
    private final int aimRange;
    private final HumanType humanType;

    SoldierType(HumanType humanType, DamageEnum attackDamage, DamageEnum defenseDamage, AimRangeEnum aimRange) {
        this.humanType = humanType;
        this.attackDamage = attackDamage.getDamageValue();
        this.defenseDamage = defenseDamage.getDamageValue();
        this.aimRange = aimRange.getRange();
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getAimRange() {
        return aimRange;
    }

    public HumanType getHumanType() {
        return humanType;
    }

    public int getDefenseDamage() {
        return defenseDamage;
    }
}
