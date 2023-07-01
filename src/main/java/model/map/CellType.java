package model.map;

import javafx.scene.image.Image;
import model.buildings.Building;
import model.buildings.buildingTypes.BuildType;
import model.buildings.buildingTypes.ProductExtractorType;
import model.buildings.buildingTypes.ResourceExtractorType;
import view.menus.LoginMenu;

public enum CellType {
    UP_ROCK("rock1.jpeg", false, "upRock"),
    DOWN_ROCK("rock2.jpeg", false, "downRock"),
    RIGHT_ROCK("rock3.jpeg", false, "rightRock"),
    LEFT_ROCK("rock4.jpeg", false, "leftRock"),
    //زمین عادی
    PLAIN_GROUND("Plain1.jpg", true, "plainGround"),
    //زمین با خرده‌سنگ
    GROUND_WITH_PEBBLES("groundWithPebble.jpeg", true, "groundWithPebbles"),
    //تخته سنگ
    BOULDER("boulder.jpeg", true, "boulder"),
    STONE("stone.jpeg", false, "stone"),
    IRON("iron.jpeg", true, "iron"),
    GRASS("grass.jpeg", true, "grass"),
    //علفزار
    MEADOW("meadow.jpeg", true, "meadow"),
    DENSE_MEADOW("denseMeadow.jpeg", true, "denseMeadow"),
    OIL("oil.jpeg", false, "oil"),
    //جلگه
    PLAIN("Plain1.jpg", true, "plain"),
    SHALLOW_WATER("shallowWater.jpeg", true, "shallowWater"),
    RIVER("river.jpeg", false, "river"),
    SMALL_POND("smallPond.jpeg", false, "smallPond"),
    BIG_POND("bigPond.jpeg", false, "bigPond"),
    BEACH("beach.jpg", false, "beach"),
    SEA("sea.jpg", false, "sea"),

    ;

    private final boolean ableToMoveOn;
    private final String name;
    private final String showingImageFileName;

    CellType(String showingImageFileName, boolean ableToMoveOn, String name) {
        this.ableToMoveOn = ableToMoveOn;
        this.showingImageFileName = showingImageFileName;
        this.name = name;
    }

    public static CellType getCellTypeByName(String typeName) {
        for (CellType cellType : CellType.values()) {
            if (cellType.name.equals(typeName)) return cellType;
        }
        return null;
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

    public Image getImage() {
        return new Image(LoginMenu.class.getResource("/images/tiles/" + showingImageFileName).toExternalForm());
    }

    public String getImageAddress() {
        return "/images/tiles/" + showingImageFileName;
    }
}
