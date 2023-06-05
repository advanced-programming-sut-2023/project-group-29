package model.weapons;

public record WeaponTypes(
        String name,
        Weapon.BuilderType builderType,
        int hp,
        int builderNeededCount,
        String showingSignInMap,
        String showingImageFilePath) {
}
