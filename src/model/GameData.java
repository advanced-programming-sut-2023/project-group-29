package model;

import java.util.ArrayList;

public class GameData
{
    private ArrayList<Empire> empires = new ArrayList<>();
    private Map map;
    private Shop shop;



    public GameData()
    {
    }

    public void addEmpire(Empire empire)
    {
        empires.add(empire);
    }
}
