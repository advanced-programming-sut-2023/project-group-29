package model;

public enum Resource {
    //TODO: complete resources!!
    STONE(50, 40, "stone"),
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
        for (Resource resource: Resource.values()) {
            if (resource.name.equals(name)) return resource;
        }
        return null;
    }
}
