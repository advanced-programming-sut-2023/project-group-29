package model.weapons;

import model.Asset;
import model.PlayerNumber;
import model.people.humanClasses.Soldier;
import model.people.humanClasses.Worker;
import model.people.humanTypes.SoldierType;
import model.people.humanTypes.WorkerType;
import model.weapons.weaponClasses.Equipments;
import model.weapons.weaponClasses.OffensiveWeapons;
import model.weapons.weaponClasses.StaticOffensiveWeapons;
import model.weapons.weaponClasses.Trap;
import model.weapons.weaponTypes.EquipmentsType;
import model.weapons.weaponTypes.OffensiveWeaponsType;
import model.weapons.weaponTypes.StaticOffensiveWeaponsType;
import model.weapons.weaponTypes.TrapType;

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
        this.showingSignInMap=weaponTypes.showingSignInMap() + "__" + (playerNumber.getNumber() + 1);
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

    public static Weapon createWeaponByWeaponTypeString(String weaponName,PlayerNumber ownerNumber,int positionX,int positionY)
    {
        //if equipment
        for(EquipmentsType equipmentsType: EquipmentsType.values())
            if(equipmentsType.getName().equals(weaponName))
                return new Equipments(equipmentsType,ownerNumber,positionX,positionY);


        //if offensive weapons
        for(OffensiveWeaponsType offensiveWeaponsType: OffensiveWeaponsType.values())
            if(offensiveWeaponsType.getName().equals(weaponName))
                return new OffensiveWeapons(offensiveWeaponsType,ownerNumber,positionX,positionY);

        //if static offensive weapons
        for(StaticOffensiveWeaponsType staticOffensiveWeaponsType: StaticOffensiveWeaponsType.values())
            if(staticOffensiveWeaponsType.getName().equals(weaponName))
                return new StaticOffensiveWeapons(staticOffensiveWeaponsType,ownerNumber,positionX,positionY);

        //if trap
        for(TrapType trapType: TrapType.values())
            if(trapType.getName().equals(weaponName))
                return new Trap(trapType,ownerNumber,positionX,positionY);

        return null;
    }

    public String getName() {
        return name;
    }
}
