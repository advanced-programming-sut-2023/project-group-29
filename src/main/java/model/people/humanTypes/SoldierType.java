package model.people.humanTypes;

import model.dealing.Product;
import model.dealing.Resource;
import model.dealing.Tradable;
import model.people.HumanType;
import model.speedanddamageenums.AimRangeEnum;
import model.speedanddamageenums.DamageEnum;
import model.speedanddamageenums.SpeedEnum;

public enum SoldierType {
    ARCHER(new HumanType("archer",20, false, SpeedEnum.FAST, "ARC"), DamageEnum.WEAK, DamageEnum.WEAK, AimRangeEnum.LONG_RANGE_ARCHER),
    CROSSBOWMAN(new HumanType("crossbowman",30, false, SpeedEnum.SLOW, "CRS"), DamageEnum.WEAK, DamageEnum.NORMAL, AimRangeEnum.SHORT_RANGE_ARCHER),
    SPEARMAN(new HumanType("spearman",30, true, SpeedEnum.NORMAL, "SPR"), DamageEnum.NORMAL, DamageEnum.TOO_WEAK, AimRangeEnum.NON_ARCHER),
    PIKEMAN(new HumanType("pikeman",100, false, SpeedEnum.SLOW, "PIK"), DamageEnum.NORMAL, DamageEnum.STRONG, AimRangeEnum.NON_ARCHER),
    MACEMAN(new HumanType("maceman",30, true, SpeedEnum.NORMAL, "MAC"), DamageEnum.STRONG, DamageEnum.NORMAL, AimRangeEnum.NON_ARCHER),
    SWORDSMAN(new HumanType("swordsman",80, false, SpeedEnum.TOO_SLOW, "SWR"), DamageEnum.TOO_STRONG, DamageEnum.TOO_WEAK, AimRangeEnum.NON_ARCHER),
    KNIGHT(new HumanType("knight",90, false, SpeedEnum.TOO_FAST, "KNI"), DamageEnum.TOO_STRONG, DamageEnum.STRONG, AimRangeEnum.NON_ARCHER),
    TUNNELER(new HumanType("tunneler",20, false, SpeedEnum.FAST, "TUN"), DamageEnum.NORMAL, DamageEnum.TOO_WEAK, AimRangeEnum.NON_ARCHER),
    LADDER_MAN(new HumanType("ladderman",20, false, SpeedEnum.FAST, "LAD"), DamageEnum.NO_DAMAGE, DamageEnum.TOO_WEAK, AimRangeEnum.NON_ARCHER),
    ENGINEER(new HumanType("engineer",20, false, SpeedEnum.NORMAL, "ENG"), DamageEnum.NO_DAMAGE, DamageEnum.TOO_WEAK, AimRangeEnum.NON_ARCHER),
    BLACK_MONK(new HumanType("blackMonk",50, false, SpeedEnum.SLOW, "BMN"), DamageEnum.NORMAL, DamageEnum.NORMAL, AimRangeEnum.NON_ARCHER),
    ARCHER_BOW(new HumanType("archerBow",20, false, SpeedEnum.FAST, "RCB"), DamageEnum.WEAK, DamageEnum.WEAK, AimRangeEnum.LONG_RANGE_ARCHER),
    SLAVE(new HumanType("slave",20, false, SpeedEnum.FAST, "SLV"), DamageEnum.WEAK, DamageEnum.ALMOST_ZERO, AimRangeEnum.NON_ARCHER),
    SLINGER(new HumanType("slinger",30, false, SpeedEnum.FAST, "SLG"), DamageEnum.WEAK, DamageEnum.TOO_WEAK, AimRangeEnum.SHORT_RANGE_ARCHER),
    ASSASSIN(new HumanType("assassin",40, true, SpeedEnum.NORMAL, "ASN"), DamageEnum.NORMAL, DamageEnum.NORMAL, AimRangeEnum.NON_ARCHER),
    HORSE_ARCHER(new HumanType("horseArcher",70, false, SpeedEnum.TOO_FAST, "HRC"), DamageEnum.WEAK, DamageEnum.NORMAL, AimRangeEnum.LONG_RANGE_ARCHER),
    ARABIAN_SWORDSMAN(new HumanType("arabianSwordsman",60, true, SpeedEnum.TOO_FAST, "RSW"), DamageEnum.STRONG, DamageEnum.STRONG, AimRangeEnum.NON_ARCHER),
    FIRE_THROWER(new HumanType("fireThrower",30, true, SpeedEnum.TOO_FAST, "FTR"), DamageEnum.STRONG, DamageEnum.WEAK, AimRangeEnum.SHORT_RANGE_ARCHER),
    ENGINEER_WITH_OIL(new HumanType("engineerWithOil",20, false, SpeedEnum.SLOW, "ENO"), DamageEnum.STRONG, DamageEnum.STRONG, AimRangeEnum.SHORT_RANGE_ARCHER),
    ;
    private final int attackDamage;
    private final int defenseDamage;
    private final int aimRange;
    private final String name;
    private final HumanType humanType;

    SoldierType(HumanType humanType, DamageEnum attackDamage, DamageEnum defenseDamage, AimRangeEnum aimRange) {
        this.humanType = humanType;
        this.attackDamage = attackDamage.getDamageValue();
        this.defenseDamage = defenseDamage.getDamageValue();
        this.aimRange = aimRange.getRange();
        this.name= humanType.name();
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

    public String getName() {
        return name;
    }

    public static SoldierType getSoldierTypeByName(String unitType) {
        for (SoldierType soldierType: SoldierType.values()) {
            if (soldierType.name.equals(unitType)) return soldierType;
        }
        return null;
    }

    public static Tradable[] getTradableFromSoldierType(SoldierType soldierType) {
        return switch (soldierType) {
            case ARCHER, CROSSBOWMAN, ARCHER_BOW -> new Tradable[]{Product.BOW,null};
            case SPEARMAN, PIKEMAN -> new Tradable[]{Product.PIKE,null};
            case MACEMAN, ARABIAN_SWORDSMAN, BLACK_MONK -> new Tradable[]{Product.SWORD,null};
            case SWORDSMAN -> new Tradable[]{Product.ARMOUR,Product.SWORD};
            case KNIGHT -> new Tradable[]{Product.HORSE,null};
            case HORSE_ARCHER -> new Tradable[]{Product.HORSE,Product.BOW};
            case SLINGER -> new Tradable[]{Resource.STONE,null};
            default -> new Tradable[]{null,null};
        };
    }
}

