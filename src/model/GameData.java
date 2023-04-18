package model;

import java.util.ArrayList;

public class GameData
{
    private ArrayList<Empire> empires = new ArrayList<>();
    private Map map;
    private Shop shop;
    private final ArrayList<Trade> trades=new ArrayList<>();
    private int turnNumber=1;
    private PlayerNumber PlayerOfTurn=PlayerNumber.FIRST;
    private int selectedCellX;
    private int selectedCellY;
    public GameData()
    {
    }

    public void addEmpire(Empire empire)
    {
        empires.add(empire);
    }

    public ArrayList<Empire> getEmpires()
    {
        return empires;
    }

    public Map getMap()
    {
        return map;
    }

    public Shop getShop()
    {
        return shop;
    }

    public int getTurnNumber()
    {
        return turnNumber;
    }
    public void incrementTurn()
    {
        turnNumber++;
    }

    public PlayerNumber getPlayerOfTurn()
    {
        return PlayerOfTurn;
    }

    public void setPlayerOfTurn(PlayerNumber playerOfTurn)
    {
        PlayerOfTurn = playerOfTurn;
    }

    public int getSelectedCellX()
    {
        return selectedCellX;
    }

    public void setSelectedCellX(int selectedCellX)
    {
        this.selectedCellX = selectedCellX;
    }

    public int getSelectedCellY()
    {
        return selectedCellY;
    }

    public void setSelectedCellY(int selectedCellY)
    {
        this.selectedCellY = selectedCellY;
    }
}
