package model.map;

import model.buildings.Building;
import model.buildings.buildingTypes.BuildType;
import model.buildings.buildingTypes.ProductExtractorType;
import model.buildings.buildingTypes.ResourceExtractorType;

public enum CellType {
    UP_ROCK(ConsoleColors.RED_BACKGROUND, false, "upRock"),
    DOWN_ROCK(ConsoleColors.RED_BACKGROUND, false, "downRock"),
    RIGHT_ROCK(ConsoleColors.RED_BACKGROUND, false, "rightRock"),
    LEFT_ROCK(ConsoleColors.RED_BACKGROUND, false, "leftRock"),
    //زمین عادی
    PLAIN_GROUND(ConsoleColors.YELLOW_BACKGROUND, true, "plainGround"),
    //زمین با خرده‌سنگ
    GROUND_WITH_PEBBLES(ConsoleColors.CYAN_BACKGROUND, true, "groundWithPebbles"),
    //تخته سنگ
    BOULDER(ConsoleColors.BLACK_BACKGROUND, true, "boulder"),
    STONE(ConsoleColors.BLACK_BACKGROUND, false, "stone"),
    IRON(ConsoleColors.RED_BACKGROUND, true, "iron"),
    GRASS(ConsoleColors.GREEN_BACKGROUND, true, "grass"),
    //علفزار
    MEADOW(ConsoleColors.PURPLE_BACKGROUND, true, "meadow"),
    DENSE_MEADOW(ConsoleColors.PURPLE_BACKGROUND, true, "denseMeadow"),
    OIL(ConsoleColors.BLACK_BACKGROUND, false, "oil"),
    //جلگه
    PLAIN(ConsoleColors.BLACK_BACKGROUND, true, "plain"),
    SHALLOW_WATER(ConsoleColors.YELLOW_BACKGROUND, true, "shallowWater"),
    RIVER(ConsoleColors.BLUE_BACKGROUND, false, "river"),
    SMALL_POND(ConsoleColors.WHITE_BACKGROUND, false, "smallPond"),
    BIG_POND(ConsoleColors.WHITE_BACKGROUND, false, "bigPond"),
    BEACH(ConsoleColors.YELLOW_BACKGROUND, false, "beach"),
    SEA(ConsoleColors.BLUE_BACKGROUND, false, "sea"),

    ;

    private final ConsoleColors showingColor;
    private final boolean ableToMoveOn;
    private final String name;

    CellType(ConsoleColors showingColor, boolean ableToMoveOn, String name) {
        this.ableToMoveOn = ableToMoveOn;
        this.showingColor = showingColor;
        this.name = name;
    }

    public ConsoleColors getShowingColor() {
        return showingColor;
    }

    public boolean isAbleToMoveOn() {
        return ableToMoveOn;
    }

    public String getName() {
        return name;
    }

    public boolean isAbleToBuildOn(String buildingName) {
        BuildType buildType = Building.getBuildTypeByName(buildingName);
        if (ProductExtractorType.APPLE_GARDEN.equals(buildType) ||
                ProductExtractorType.GRAIN_FARM.equals(buildType) ||
                ProductExtractorType.WHEAT_FARM.equals(buildType)) {
            return this.equals(CellType.DENSE_MEADOW) || this.equals(CellType.GRASS);
        } else if (ResourceExtractorType.QUARRY.equals(buildType)) {
            return this.equals(CellType.STONE);
        } else if (ResourceExtractorType.IRON_MINE.equals(buildType)) {
            return this.equals(CellType.IRON);
        } else if (ResourceExtractorType.PITCH_RIG.equals(buildType)) {
            return this.equals(CellType.PLAIN);
        }
        return this.equals(CellType.PLAIN_GROUND) || this.equals(CellType.MEADOW)
                || this.equals(CellType.GRASS) || this.equals(CellType.DENSE_MEADOW)
                || this.equals(CellType.GROUND_WITH_PEBBLES);
    }
}
