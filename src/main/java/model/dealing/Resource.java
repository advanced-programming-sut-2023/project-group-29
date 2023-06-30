package model.dealing;

public enum Resource implements Tradable {
    STONE(30, 15, "stone"),
    WOOD(20, 10, "wood"),
    IRON(40, 20, "iron"),
    PITCH(10, 5, "pitch"),
    ;
    private final String name;
    private final int buyingPrice;
    private final int sellingPrice;

    Resource(int buyingPrice, int sellingPrice, String name) {
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.name = name;
    }

    public static Resource getResourceByName(String name) {
        for (Resource resource : Resource.values()) {
            if (resource.getName().equals(name)) return resource;
        }
        return null;
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
}
