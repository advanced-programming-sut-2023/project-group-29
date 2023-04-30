package model.weapons;

import model.speedanddamageenums.SpeedEnum;

public record WeaponTypes(
        Weapon.BuilderType builderType,
        int hp,
        int builderNeededCount)
{}
