package model.map;

import javafx.scene.image.Image;
import model.buildings.Building;
import model.buildings.buildingTypes.BuildType;
import model.buildings.buildingTypes.ProductExtractorType;
import model.buildings.buildingTypes.ResourceExtractorType;
import view.menus.LoginMenu;

public enum CellType {
    UP_ROCK(ConsoleColors.RED_BACKGROUND,"rock1.png", false, "upRock"),
    DOWN_ROCK(ConsoleColors.RED_BACKGROUND,"rock2.png", false, "downRock"),
    RIGHT_ROCK(ConsoleColors.RED_BACKGROUND,"rock3.png", false, "rightRock"),
    LEFT_ROCK(ConsoleColors.RED_BACKGROUND,"rock4.png", false, "leftRock"),
    //زمین عادی
    PLAIN_GROUND(ConsoleColors.YELLOW_BACKGROUND,"Plain1.jpg", true, "plainGround"),
    //زمین با خرده‌سنگ
    GROUND_WITH_PEBBLES(ConsoleColors.CYAN_BACKGROUND,"groundWithPebble.jpeg", true, "groundWithPebbles"),
    //تخته سنگ
    BOULDER(ConsoleColors.BLACK_BACKGROUND,"boulder.jpeg", true, "boulder"),
    STONE(ConsoleColors.BLACK_BACKGROUND,"stone.jpeg", false, "stone"),
    IRON(ConsoleColors.RED_BACKGROUND,"iron.jpeg", true, "iron"),
    GRASS(ConsoleColors.GREEN_BACKGROUND,"grass.jpeg", true, "grass"),
    //علفزار
    MEADOW(ConsoleColors.PURPLE_BACKGROUND,"meadow.jpeg", true, "meadow"),
    DENSE_MEADOW(ConsoleColors.PURPLE_BACKGROUND,"denseMeadow.jpeg", true, "denseMeadow"),
    OIL(ConsoleColors.BLACK_BACKGROUND,"oil.jpeg", false, "oil"),
    //جلگه
    PLAIN(ConsoleColors.BLACK_BACKGROUND,"Plain1.jpg", true, "plain"),
    SHALLOW_WATER(ConsoleColors.YELLOW_BACKGROUND,"shallowWater.jpeg", true, "shallowWater"),
    RIVER(ConsoleColors.BLUE_BACKGROUND,"river.jpeg", false, "river"),
    SMALL_POND(ConsoleColors.WHITE_BACKGROUND,"smallPond.jpeg", false, "smallPond"),
    BIG_POND(ConsoleColors.WHITE_BACKGROUND,"bigPond.jpeg", false, "bigPond"),
    BEACH(ConsoleColors.YELLOW_BACKGROUND,"beach.jpg", false, "beach"),
    SEA(ConsoleColors.BLUE_BACKGROUND,"sea.jpg", false, "sea"),

    ;

    private final ConsoleColors showingColor;
    private final boolean ableToMoveOn;
    private final String name;
    private final String showingImageFileName;

    CellType(ConsoleColors showingColor, String showingImageFileName, boolean ableToMoveOn, String name) {
        this.ableToMoveOn = ableToMoveOn;
        this.showingImageFileName = showingImageFileName;
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
        }
        else if (ResourceExtractorType.QUARRY.equals(buildType)) {
            return this.equals(CellType.STONE);
        }
        else if (ResourceExtractorType.IRON_MINE.equals(buildType)) {
            return this.equals(CellType.IRON);
        }
        else if (ResourceExtractorType.PITCH_RIG.equals(buildType)) {
            return this.equals(CellType.PLAIN);
        }
        return this.equals(CellType.PLAIN_GROUND) || this.equals(CellType.MEADOW)
                || this.equals(CellType.GRASS) || this.equals(CellType.DENSE_MEADOW)
                || this.equals(CellType.GROUND_WITH_PEBBLES);
    }

    public Image getImage(){
        return new Image(LoginMenu.class.getResource("/images/tiles/"+showingImageFileName).toExternalForm());
    }
}
