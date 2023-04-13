package model.people;

public class Soldier extends Human
{
    private SoldierType soldierType;
    private int damage;
    private int aimRange;

    public Soldier(SoldierType soldierType)
    {
        this.soldierType = soldierType;
        this.hp = soldierType.getHp();
        this.damage = soldierType.getDamage();
        this.ableToClimbLadder = soldierType.isAbleToClimbLadder();
        this.aimRange = soldierType.getAimRange();
    }

    public int getDamage()
    {
        return damage;
    }

    public int getAimRange()
    {
        return aimRange;
    }

    public SoldierType getSoldierType()
    {
        return soldierType;
    }
}
