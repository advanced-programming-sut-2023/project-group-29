package model.buildings;

public record BuildingType(int hp, int numberOfWorkers, int[] neededResources, String abbreviation, Category category) {
}
