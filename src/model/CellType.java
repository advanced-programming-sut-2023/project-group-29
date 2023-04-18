package model;

public enum CellType
{
    //LAKE,
    //MOUNTAIN;
    ;

    private int speed;
    private Cell.Color showingColor;
    private boolean ableToBuildOn;
    private boolean ableToMoveOn;

    CellType(int speed,Cell.Color showingColor,boolean ableToBuildOn,boolean ableToMoveOn)
    {
        this.speed=speed;
        this.ableToBuildOn=ableToBuildOn;
        this.ableToMoveOn=ableToMoveOn;
        this.showingColor=showingColor;
    }

    public int getSpeed()
    {
        return speed;
    }

    public Cell.Color getShowingColor()
    {
        return showingColor;
    }

    public boolean isAbleToBuildOn()
    {
        return ableToBuildOn;
    }

    public boolean isAbleToMoveOn()
    {
        return ableToMoveOn;
    }
}
