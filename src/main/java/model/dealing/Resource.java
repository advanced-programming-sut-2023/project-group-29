package model.dealing;

public enum Resource {
    //TODO: complete resources!!
    COINS(50, 40, "coins"), // TODO: we can't buy coin
    STONE(50, 40, "stone"),
    WOOD(50, 40, "wood"),
    IRON(50, 40, "iron"),
    PITCH(0,0,"pitch") //TODO: complete!
    ;
    private final String name;
    private final int buyingPrice;
    private final int sellingPrice;

    Resource(int buyingPrice, int sellingPrice, String name) {
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getBuyingPrice() {
        return buyingPrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public static Resource getResourceByName(String name) {
        for (Resource resource : Resource.values()) {
            if (resource.name.equals(name)) return resource;
        }
        return null;
    }
}
