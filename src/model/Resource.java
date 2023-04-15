package model;

public enum Resource
{
    ;
    private int buyingPrice;
    private int sellingPrice;

    private Resource(int buyingPrice, int sellingPrice)
    {
        this.buyingPrice=buyingPrice;
        this.sellingPrice=sellingPrice;
    }

}
