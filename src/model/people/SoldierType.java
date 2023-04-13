package model.people;

public enum SoldierType
{
    //TODO complete below list
    AMIN(10000,100000,true,10000);
    private int hp;
    private int damage;
    private boolean ableToClimbLadder;
    private int aimRange;
    private SoldierType(int hp,int damage,boolean canClimbLadder,int aimRange)
    {
        this.hp=hp;
        this.damage=damage;
        this.ableToClimbLadder =canClimbLadder;
        this.aimRange=aimRange;
    }
    public int getHp()
    {
        return hp;
    }

    public int getDamage()
    {
        return damage;
    }

    public boolean isAbleToClimbLadder()
    {
        return ableToClimbLadder;
    }

    public int getAimRange()
    {
        return aimRange;
    }
}
