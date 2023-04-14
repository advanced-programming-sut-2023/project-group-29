package model.people;

public enum SoldierType
{
    //TODO complete below list
    ;
    private int damage;
    private int aimRange;
    private HumanType humanType;
    private SoldierType(HumanType humanType,int damage,int aimRange)
    {
        this.humanType=humanType;
        this.damage=damage;
        this.aimRange=aimRange;
    }

    public int getDamage()
    {
        return damage;
    }

    public int getAimRange()
    {
        return aimRange;
    }

    public HumanType getHumanType()
    {
        return humanType;
    }
}
