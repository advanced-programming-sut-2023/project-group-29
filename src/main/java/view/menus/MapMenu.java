package view.menus;

import controller.Controller;
import controller.menucontrollers.GameController;
import controller.menucontrollers.MapFunctions;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Empire;
import model.GameData;
import model.GameKeyConstants;
import model.Pair;
import model.buildings.Building;
import model.buildings.Category;
import model.buildings.buildingTypes.BuildType;
import model.gamestates.GameState;
import model.map.Cell;
import view.shape.BuildingIcon;
import view.shape.CircleImage;

import java.net.URL;
import java.util.HashMap;

public class MapMenu extends Application {
    private Pane mainPane=null;
    private GameData gameData = null;
    private Pane[][] tiles=null;
    private GameGraphicFunctions gameGraphicFunctions;
    private Stage stage;
    private final Popup popularityPopup = new Popup();
    private final Popup buildingPopup = new Popup();
    private final HashMap<Category, VBox> subMenus = new HashMap<>();
    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/MapMenu.fxml");
        mainPane = FXMLLoader.load(url);
        Scene scene = new Scene(mainPane);
        this.stage = stage;

        this.gameData = GameController.getGameData();
        gameData.setMapMenu(this);

        gameGraphicFunctions=new GameGraphicFunctions(mainPane);
        gameData.setGameGraphicFunctions(gameGraphicFunctions);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                keyHandle(keyEvent);
            }
        });

        setUpAndShowMap();
        //setDragFunctions(); todo add this function to ordinary cells

        buildPopularity();
        buildingPopup.show(stage);
        makeBuildingBar();

        stage.setScene(scene);
        stage.show();
    }

    private void setUpAndShowMap(){
        tiles= MapFunctions.showMap(gameData.getCornerCellIndex().first, gameData.getCornerCellIndex().second, mainPane);
        setTilesListeners();
    }

    private void setTilesListeners(){
        for(int i=0;i<gameData.getNumberOfTilesShowingInRow();i++)
            for(int j=0;j<gameData.getNumberOfTilesShowingInColumn();j++)
            {
                int i_dup = i;
                int j_dup = j;

                tiles[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        mouseClickHandle(mouseEvent, i_dup,j_dup);
                    }
                });

                tiles[i][j].setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        mouseDragStartHandle(mouseEvent,i_dup,j_dup);
                    }
                });

                tiles[i][j].setOnMouseDragReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        mouseDragEndHandle(mouseEvent,i_dup,j_dup);
                    }
                });

                tiles[i][j].hoverProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        showDetails(i_dup,j_dup);
                    } else {
                        hideDetails(i_dup,j_dup);
                    }
                });

            }
    }

    private void showDetails(int x,int y){
        Cell cell=gameData.getMap().getCells()[x][y];
        //todo handle all pop up menus - pause game and specially these functions
    }
    private void hideDetails(int x,int y){
        Cell cell=gameData.getMap().getCells()[x][y];
    }

    private void keyHandle(KeyEvent keyEvent) {
        System.out.println(gameData.getGameState());

        switch (gameData.getGameState()) {
            case CELL_SELECTED -> {
                if(keyEvent.getCode().equals(GameKeyConstants.cancelKey)) {
                    gameData.setGameState(GameState.VIEW_MAP);
                    gameData.setStartSelectedCellsPosition(null);
                    gameData.setEndSelectedCellsPosition(null);
                }
                else if(keyEvent.getCode().equals(GameKeyConstants.attackKey)) {
                    gameData.setGameState(GameState.ATTACKING);
                    gameGraphicFunctions.attackUnits();
                }
                else if(keyEvent.getCode().equals(GameKeyConstants.moveKey)) {
                    gameData.setGameState(GameState.MOVING);
                    gameGraphicFunctions.moveUnits();
                }
                else if(keyEvent.getCode().equals(GameKeyConstants.pourOilKey)) {
                    gameData.setGameState(GameState.POURING_OIL);
                    gameGraphicFunctions.engineersPourOil();
                }
                else if(keyEvent.getCode().equals(GameKeyConstants.dropLadderKey)) {
                    gameGraphicFunctions.dropLadders();
                }
                else if(keyEvent.getCode().equals(GameKeyConstants.disbandUnitKey)) {
                    gameGraphicFunctions.disbandUnits();
                }
                else if(keyEvent.getCode().equals(GameKeyConstants.buildEquipmentKey)) {
                    gameGraphicFunctions.buildEquipments();
                }
            }
            case VIEW_MAP -> {
                if(keyEvent.getCode().equals(GameKeyConstants.mapMoveLeft))
                    MapFunctions.moveMap(0,0,0,1);
                else if(keyEvent.getCode().equals(GameKeyConstants.mapMoveRight))
                    MapFunctions.moveMap(0,1,0,0);
                else if(keyEvent.getCode().equals(GameKeyConstants.mapMoveDown))
                    MapFunctions.moveMap(0,0,1,0);
                else if(keyEvent.getCode().equals(GameKeyConstants.mapMoveUp))
                    MapFunctions.moveMap(1,1,0,0);

                setUpAndShowMap();
            }
        }
    }

    private void mouseClickHandle(MouseEvent mouseEvent,int tileX,int tileY) {
        switch (gameData.getGameState()) {
            case VIEW_MAP, CELL_SELECTED -> {
                gameData.setGameState(GameState.CELL_SELECTED);
                gameData.setStartSelectedCellsPosition(new Pair<>(tileX,tileY));
                gameData.setEndSelectedCellsPosition(new Pair<>(tileX,tileY));
            }
        }
    }

    private void mouseDragStartHandle(MouseEvent mouseEvent,int tileX,int tileY) {
        switch (gameData.getGameState()) {
            case VIEW_MAP, CELL_SELECTED -> {
                gameData.setGameState(GameState.CELL_SELECTED);
                gameData.setStartSelectedCellsPosition(new Pair<>(tileX,tileY));
                gameData.setEndSelectedCellsPosition(new Pair<>(tileX,tileY));
            }
        }
    }
    private void mouseDragEndHandle(MouseEvent mouseEvent,int tileX,int tileY) {
        switch (gameData.getGameState()) {
            case VIEW_MAP, CELL_SELECTED -> {
                gameData.setEndSelectedCellsPosition(new Pair<>(tileX,tileY));
            }
        }
    }

    private void makeBuildingBar() {
        VBox vBox = makeVBox(1020,180, new VBox());
        for (Category category : Category.values()) {
            if (category.equals(Category.UNBUILDABLE)) continue;
            subMenus.put(category, new VBox());
            makeSubMenu(category);
            BuildingIcon icon = addIcon(vBox, "/images/buildings/" + category.name() + ".png");
            setFunctionOnClick(icon, category);
        }
        mainPane.getChildren().add(vBox);
    }

    private void setFunctionOnClick(BuildingIcon icon, Category category) {
        icon.setOnMouseClicked(mouseEvent -> {
            buildingPopup.setX(stage.getX() + 955);
            buildingPopup.setY(stage.getY() + 200);
            if (buildingPopup.getContent().size() != 0){
                ScrollPane scrollPane = (ScrollPane) buildingPopup.getContent().get(0);
                boolean againClick = scrollPane.getContent().equals(subMenus.get(category));
                buildingPopup.getContent().clear();
                if (!againClick) addToPopup(category);
            }
            else{

                addToPopup(category);
            }
        });
    }

    private void addToPopup(Category category) {
        ScrollPane scrollPane = new ScrollPane(subMenus.get(category));
        scrollPane.setMaxHeight(400);
        scrollPane.setPrefSize(70,400);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        buildingPopup.getContent().add(scrollPane);
    }

    private void makeSubMenu(Category category) {
        VBox subMenu = subMenus.get(category);
        makeVBox(0, 0, subMenu);
        for (BuildType buildType : Building.getBuildingTypesAndTheirGroup().keySet()) {
            if (buildType.getBuildingType().category().equals(category)) {
                String add = "/images/buildings/" + category.name() + "/" + buildType.getBuildingType().name() + ".png";
                try {
                    BuildingIcon icon = addIcon(subMenu, add);
                    icon.setDragFunctions(buildType);
                }
                catch (Exception e) {
                    System.out.println("no such file in this address:");
                    System.out.println(add);
                }
            }
        }
    }

    private VBox makeVBox(int xTranslate, int yTranslate, VBox vBox) {
        vBox.setPrefWidth(50);
        vBox.setSpacing(5);
        vBox.setTranslateX(xTranslate);
        vBox.setTranslateY(yTranslate);
        return vBox;
    }

    private BuildingIcon addIcon(VBox vBox, String address) {
        BuildingIcon buildingIcon = new BuildingIcon(50, address);
        vBox.getChildren().add(buildingIcon);
        return buildingIcon;
    }

    private void buildPopularity() {
        //todo faratin beautify
        makeMainPopularity();
        makeFactorsTable();
    }

    private void makeFactorsTable() {
        VBox outerBox = makeOuterBox();
        Text text1 = new Text("Popularity factors:");
        text1.setStyle("-fx-font: 18 arial;");
        HBox factorsHBox = new HBox();
        factorsHBox.setSpacing(50);
        factorsHBox.setAlignment(Pos.CENTER);
        outerBox.getChildren().add(text1);
        outerBox.getChildren().add(factorsHBox);
        for (int i = 1; i <= 4; i++) addVbox(factorsHBox, i);
        Text text = new Text("change for next turn: " + 0); //todo jasbi correct number
        outerBox.getChildren().add(text);
        popularityPopup.getContent().add(outerBox);
    }

    private VBox makeOuterBox() {
        VBox outerBox = new VBox();
        outerBox.setAlignment(Pos.CENTER);
        outerBox.setPrefWidth(400);
        outerBox.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, null, null)));
        mainPane.getChildren().add(outerBox);
        return outerBox;
    }

    private void makeMainPopularity() {
        Text text = new Text("Popularity : " + 0);//correct number
        text.setStyle("-fx-font: 18 arial;");
        text.setOnMouseClicked(mouseEvent -> {
            if (!popularityPopup.isShowing()) {
                popularityPopup.setAnchorX(347 + stage.getX());
                popularityPopup.setAnchorY(570 + stage.getY());
                popularityPopup.show(stage);
            } else popularityPopup.hide();
        });
        CircleImage circleImage = new CircleImage("/images/green.png", 20); //todo jasbi change
        Button button = makeButton();
        makeHBox(text, circleImage, button);
    }

    private void makeHBox(Text text, Node circleImage, Button button) {
        HBox hBox = new HBox(text, circleImage, button);
        hBox.setAlignment(Pos.CENTER);
        hBox.setTranslateX(340);
        hBox.setTranslateY(670);
        hBox.setPrefWidth(400);
        hBox.setSpacing(40);
        hBox.setBackground(new Background(new BackgroundFill(Color.GOLD, null, null)));
        mainPane.getChildren().add(hBox);
    }

    private static Button makeButton() {
        Button button = new Button("change factors");
        button.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px;" +
                " -fx-background-color: lightYellow; -fx-border-color: black;");
        button.setOnMouseClicked(mouseEvent -> {
            try {
                new SetFactorsMenu().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return button;
    }

    private void addVbox(HBox factorsHBox, int switcher) {
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        factorsHBox.getChildren().add(vBox);
        for (Empire.PopularityFactors factor : Empire.PopularityFactors.values()) {
            switch (switcher) {
                case 1 -> {
                    Text text = new Text(factor.name());
                    vBox.getChildren().add(text);
                }
                case 2 -> {
                    Text text = new Text(": " + 0);
                    vBox.getChildren().add(text);
                    //todo jasbi correct numbers
                }
                case 3 -> {
                    CircleImage circleImage = new CircleImage("/images/green.png", 8); //todo jasbi change
                    vBox.getChildren().add(circleImage);
                }
                case 4 -> {
                    Text text = new Text("affect --> " + 0);
                    vBox.getChildren().add(text);
                    //todo jasbi correct numbers
                }
            }
        }
    }
    public Pane getMainPane() {
        return mainPane;
    }
}