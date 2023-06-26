package view.menus.gamepopupmenus;

import controller.menucontrollers.GameController;
import controller.menucontrollers.MapFunctions;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.GameData;
import model.ImagePracticalFunctions;
import model.people.humanTypes.SoldierType;
import model.weapons.weaponTypes.EquipmentsType;
import model.weapons.weaponTypes.OffensiveWeaponsType;
import model.weapons.weaponTypes.StaticOffensiveWeaponsType;
import model.weapons.weaponTypes.TrapType;
import view.menus.EnterMenu;
import view.menus.MapMenu;

public class DropUnitsGraphics {
    @FXML
    private TextField ownerNumber;
    @FXML
    private Pane dropUnitPane;

    private final int numberOfPicturesInRow=9;

    public void exitPopUp(MouseEvent mouseEvent) {
        GameController.getGameData().getGameGraphicFunctions().exitPopUp();
    }

    @FXML
    public void initialize(){
        int index=0;
        for(SoldierType type : SoldierType.values()){
            Image image=new Image(MapMenu.class.getResource(type.getHumanType().showingImageFilePath()).toExternalForm());
            setupUnitImage(image,index,type.getName());
            index++;
        }

        for(EquipmentsType type : EquipmentsType.values()){
            Image image=new Image(MapMenu.class.getResource(type.getWeaponTypes().showingImageFilePath()).toExternalForm());
            setupUnitImage(image,index,type.getName());
            index++;
        }
        for(OffensiveWeaponsType type : OffensiveWeaponsType.values()){
            Image image=new Image(MapMenu.class.getResource(type.getWeaponTypes().showingImageFilePath()).toExternalForm());
            setupUnitImage(image,index,type.getName());
            index++;
        }
        for(TrapType type : TrapType.values()){
            Image image=new Image(MapMenu.class.getResource(type.getWeaponTypes().showingImageFilePath()).toExternalForm());
            setupUnitImage(image,index,type.getName());
            index++;
        }
        for(StaticOffensiveWeaponsType type : StaticOffensiveWeaponsType.values()){
            Image image=new Image(MapMenu.class.getResource(type.getWeaponTypes().showingImageFilePath()).toExternalForm());
            setupUnitImage(image,index,type.getName());
            index++;
        }
    }
    public void setupUnitImage(Image image, int index, String typeName){
        GameData gameData=GameController.getGameData();

        ImageView imageView=new ImageView(image);
        ImagePracticalFunctions.fitWidthHeight(imageView,70,70);
        imageView.setLayoutX(75*(index%numberOfPicturesInRow)+50);
        imageView.setLayoutY(80*(index/numberOfPicturesInRow)+120);

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int x=gameData.getStartSelectedCellsPosition().first+gameData.getCornerCellIndex().first;
                int y=gameData.getStartSelectedCellsPosition().second+gameData.getCornerCellIndex().second;

                int playerNumber=0;
                if(!ownerNumber.getText().matches("\\d")) {
                    gameData.getGameGraphicFunctions().alertMessage(Color.RED, "drop failed", "please enter a valid player number");
                    return;
                }
                else playerNumber=Integer.parseInt(ownerNumber.getText());

                if(playerNumber<=0 || playerNumber>gameData.getNumberOfPlayers()) {
                    gameData.getGameGraphicFunctions().alertMessage(Color.RED, "drop failed", "please enter a valid player number");
                    return;
                }

                MapFunctions.dropUnit(x,y,typeName,1,playerNumber);
                //todo handle messages
            }
        });

        dropUnitPane.getChildren().add(imageView);
    }
}
