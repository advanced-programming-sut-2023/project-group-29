package model.buildings;

public record BuildingType(int hp, int numberOfWorkers, int[] neededResources) {
    // int[] neededResources = {coins, stone, wood, iron}
}
