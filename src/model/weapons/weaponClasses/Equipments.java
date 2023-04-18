package model.weapons.weaponClasses;

import model.Map;
import model.Movable;
import model.PlayerNumber;
import model.weapons.weaponTypes.EquipmentsType;
import model.weapons.Weapon;

public class Equipments extends Weapon implements Movable
{
    private final EquipmentsType equipmentsType;

    public Equipments(EquipmentsType equipmentsType, PlayerNumber playerNumber,int positionX,int positionY) {
        super(equipmentsType.getWeaponTypes(), playerNumber,positionX,positionY);

        this.equipmentsType = equipmentsType;
    }

    public MovingResult move(Map map, int destinationX, int destinationY)
    {
        return Movable.move(map,this,speed,false,destinationX,destinationY);
    }
}
