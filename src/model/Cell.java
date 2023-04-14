package model;

import model.buildings.Building;
import model.people.Soldier;

import java.util.ArrayList;

public class Cell {
    private ArrayList<Soldier> soldiers;
    private Building building = null;
    private int speed;
    private Color showingColor;

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(ArrayList<Soldier> soldiers) {
        this.soldiers = soldiers;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Color getShowingColor() {
        return showingColor;
    }

    public void setShowingColor(Color showingColor) {
        this.showingColor = showingColor;
    }

    enum Color {
        RED,
        GREEN,
        BLUE
    }
}
