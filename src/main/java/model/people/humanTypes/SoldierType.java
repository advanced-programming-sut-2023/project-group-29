package model.people.humanTypes;

import model.people.HumanType;
import model.speedanddamageenums.AimRangeEnum;
import model.speedanddamageenums.DamageEnum;
import model.speedanddamageenums.SpeedEnum;

public enum SoldierType {
    ARCHER(new HumanType("archer",10, false, SpeedEnum.FAST, "ARC"), DamageEnum.WEAK, DamageEnum.WEAK, AimRangeEnum.LONG_RANGE_ARCHER),
    CROSSBOWMAN(new HumanType("crossbowman",15, false, SpeedEnum.SLOW, "CRS"), DamageEnum.WEAK, DamageEnum.NORMAL, AimRangeEnum.SHORT_RANGE_ARCHER),
    SPEARMAN(new HumanType("spearman",15, true, SpeedEnum.NORMAL, "SPR"), DamageEnum.NORMAL, DamageEnum.TOO_WEAK, AimRangeEnum.NON_ARCHER),
    PIKEMAN(new HumanType("pikeman",50, false, SpeedEnum.SLOW, "PIK"), DamageEnum.NORMAL, DamageEnum.STRONG, AimRangeEnum.NON_ARCHER),
    MACEMAN(new HumanType("maceman",15, true, SpeedEnum.NORMAL, "MAC"), DamageEnum.STRONG, DamageEnum.NORMAL, AimRangeEnum.NON_ARCHER),
    SWORDSMAN(new HumanType("swordsman",40, false, SpeedEnum.TOO_SLOW, "SWR"), DamageEnum.TOO_STRONG, DamageEnum.TOO_WEAK, AimRangeEnum.NON_ARCHER),
    KNIGHT(new HumanType("knight",45, false, SpeedEnum.TOO_FAST, "KNI"), DamageEnum.TOO_STRONG, DamageEnum.STRONG, AimRangeEnum.NON_ARCHER),
    TUNNELER(new HumanType("tunneler",10, false, SpeedEnum.FAST, "TUN"), DamageEnum.NORMAL, DamageEnum.TOO_WEAK, AimRangeEnum.NON_ARCHER),
    LADDER_MAN(new HumanType("ladderman",10, false, SpeedEnum.FAST, "LAD"), DamageEnum.NO_DAMAGE, DamageEnum.TOO_WEAK, AimRangeEnum.NON_ARCHER),
    ENGINEER(new HumanType("engineer",10, false, SpeedEnum.NORMAL, "ENG"), DamageEnum.NO_DAMAGE, DamageEnum.TOO_WEAK, AimRangeEnum.NON_ARCHER),
    BLACK_MONK(new HumanType("blackMonk",25, false, SpeedEnum.SLOW, "BMN"), DamageEnum.NORMAL, DamageEnum.NORMAL, AimRangeEnum.NON_ARCHER),
    ARCHER_BOW(new HumanType("archerBow",10, false, SpeedEnum.FAST, "RCB"), DamageEnum.WEAK, DamageEnum.WEAK, AimRangeEnum.LONG_RANGE_ARCHER),
    SLAVE(new HumanType("slave",10, false, SpeedEnum.FAST, "SLV"), DamageEnum.WEAK, DamageEnum.ALMOST_ZERO, AimRangeEnum.NON_ARCHER),
    SLINGER(new HumanType("slinger",15, false, SpeedEnum.FAST, "SLG"), DamageEnum.WEAK, DamageEnum.TOO_WEAK, AimRangeEnum.SHORT_RANGE_ARCHER),
    ASSASSIN(new HumanType("assassin",20, true, SpeedEnum.NORMAL, "ASN"), DamageEnum.NORMAL, DamageEnum.NORMAL, AimRangeEnum.NON_ARCHER),
    HORSE_ARCHER(new HumanType("horseArcher",35, false, SpeedEnum.TOO_FAST, "HRC"), DamageEnum.WEAK, DamageEnum.NORMAL, AimRangeEnum.LONG_RANGE_ARCHER),
    ARABIAN_SWORDSMAN(new HumanType("arabianSwordsman",30, true, SpeedEnum.TOO_FAST, "RSW"), DamageEnum.STRONG, DamageEnum.STRONG, AimRangeEnum.NON_ARCHER),
    FIRE_THROWER(new HumanType("fireThrower",15, true, SpeedEnum.TOO_FAST, "FTR"), DamageEnum.STRONG, DamageEnum.WEAK, AimRangeEnum.SHORT_RANGE_ARCHER);
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
