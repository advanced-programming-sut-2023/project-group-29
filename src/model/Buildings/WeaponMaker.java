package model.buildings;

public class WeaponMaker extends Building {
    private WeaponMakerType weaponMakerType;
    private int productionRate;
    //مواد مصرفی


    public WeaponMaker(WeaponMakerType weaponMakerType, int productionRate) {
        this.weaponMakerType = weaponMakerType;
        this.productionRate = productionRate;
    }
}
