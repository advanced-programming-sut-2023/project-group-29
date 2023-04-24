package model;

public enum Resource {
    ;
    private final int buyingPrice;
    private final int sellingPrice;

    Resource(int buyingPrice, int sellingPrice) {
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
    }

}
