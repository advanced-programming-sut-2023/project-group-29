package model;

public enum Commodity
{
    ;
    private int buyingPrice;
    private int sellingPrice;

    private Commodity(int buyingPrice,int sellingPrice)
    {
        this.buyingPrice=buyingPrice;
        this.sellingPrice=sellingPrice;
    }

}
