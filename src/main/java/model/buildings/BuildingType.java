package model.buildings;

public record BuildingType
        (int hp, int numberOfWorkers, int[] neededResources, String name, String abbreviation, Category category) {
}
