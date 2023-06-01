package model;

import controller.menucontrollers.GameMenuController;
import javafx.scene.image.Image;
import view.Main;
import view.menus.LoginMenu;

public class Asset {
    private final PlayerNumber ownerNumber;
    protected int hp;
    protected String showingSignInMap;
    private int positionX;
    private int positionY;
    protected String showingImageFilePath;    //todo set for all values

    public Asset(PlayerNumber ownerNumber, int positionX, int positionY) {
        this.ownerNumber = ownerNumber;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Empire getOwnerEmpire() {
        return GameMenuController.getGameData().getEmpireByPlayerNumber(ownerNumber);
    }

    public PlayerNumber getOwnerNumber() {
        return ownerNumber;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getHp() {
        return hp;
    }

    public void decreaseHp(int amount) {
        hp -= amount;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public String getShowingSignInMap() {
        return showingSignInMap;
    }

    public void setShowingSignInMap(String showingSignInMap) {
        this.showingSignInMap = showingSignInMap;
    }

    public String getShowingImageFilePath() {
        return showingImageFilePath;
    }

    public Image getShowingImage(){
        return new Image(LoginMenu.class.getResource(showingImageFilePath).toExternalForm());
    }

    public void setShowingImageFilePath(String showingImageFilePath) {
        this.showingImageFilePath = showingImageFilePath;
    }
}
