package model.map;

public class MapTemplate {
    private TreeType[][] treeTypes;
    private CellType[][] cellTypes;
    private boolean[][] hasEmpire;
    private int width;
    private int height;
    private int usersCount;
    private String name;
    private String creatorUsername;

    public MapTemplate(String creatorUsername, String name, int width, int height, int usersCount) {
        this.creatorUsername = creatorUsername;
        this.width = width;
        this.height = height;
        this.usersCount = usersCount;
        this.name = name;
        initialize();
    }

    public TreeType[][] getTreeTypes() {
        return treeTypes;
    }

    public CellType[][] getCellTypes() {
        return cellTypes;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public boolean[][] getHasEmpire() {
        return hasEmpire;
    }

    public String getName() {
        return name;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void initialize() {
        cellTypes = new CellType[width][height];
        treeTypes = new TreeType[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cellTypes[i][j] = CellType.PLAIN;
                treeTypes[i][j] = null;
            }
        }
        hasEmpire = new boolean[width][height];
    }
}
