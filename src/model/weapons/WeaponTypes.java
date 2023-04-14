package model.weapons;

public class WeaponTypes
{
    private Weapon.BuilderType builderType;
    private int speed;
    private int hp;
    private int builderNeededCount;

    public WeaponTypes(Weapon.BuilderType builderType, int speed, int hp, int builderNeededCount)
    {
        this.builderType = builderType;
        this.speed = speed;
        this.hp = hp;
        this.builderNeededCount = builderNeededCount;
    }

    public Weapon.BuilderType getBuilderType()
    {
        return builderType;
    }

    public int getSpeed()
    {
        return speed;
    }

    public int getHp()
    {
        return hp;
    }

    public int getBuilderNeededCount()
    {
        return builderNeededCount;
    }
}
