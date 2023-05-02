package model.weapons;

import model.speedanddamageenums.SpeedEnum;

public record WeaponTypes(
        String name,
        Weapon.BuilderType builderType,
        int hp,
        int builderNeededCount)
{}
