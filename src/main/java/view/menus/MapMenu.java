package view.menus;

import controller.menucontrollers.GameController;
import controller.menucontrollers.MapFunctions;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;
import model.buildings.Building;
import model.buildings.Category;
import model.buildings.buildingTypes.BuildType;
import model.gamestates.GameState;
import view.shape.BuildingIcon;
import view.shape.CircleImage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class MapMenu extends Application {
    private Pane mainPane = null;
    private GameData gameData = null;
    private Pane[][] tiles = null;
    private final Group tilesGroupInMainPainChildren =new Group();
    private GameGraphicFunctions gameGraphicFunctions;
    private Stage stage;
    private Pane popularityPopupPane;
    private Pane buildingPopupPane;
    private final HashMap<Category, VBox> subMenus = new HashMap<>();
    private GamePopUpMenus currentCellDetailsPopup;


    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/MapMenu.fxml");
        mainPane = FXMLLoader.load(url);
        Scene scene = new Scene(mainPane);
        this.stage = stage;

        this.gameData = GameController.getGameData();
        gameData.setMapMenu(this);

        gameGraphicFunctions = new GameGraphicFunctions(mainPane);
        gameData.setGameGraphicFunctions(gameGraphicFunctions);

        mainPane.getChildren().add(tilesGroupInMainPainChildren);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                try {
                    keyHandle(keyEvent);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        setUpAndShowMap();
        //setDragFunctions(); todo jasbi add this function to ordinary cells

        buildPopularity();
        makeBuildingBar();

        GamePopUpMenus buildingPopUp = new GamePopUpMenus(mainPane, buildingPopupPane, GamePopUpMenus.PopUpType.BUILDING_ICONS_COLUMN);
        buildingPopUp.showAndWait();

        stage.setScene(scene);
        stage.show();
    }

    private void setUpAndShowMap() {
        tilesGroupInMainPainChildren.getChildren().clear();
        tiles = MapFunctions.showMap(gameData.getCornerCellIndex().first, gameData.getCornerCellIndex().second, tilesGroupInMainPainChildren);
        setTilesListeners();
    }


    private void setTilesListeners() {
        for (int i = 0; i < gameData.getNumberOfTilesShowingInRow(); i++)
            for (int j = 0; j < gameData.getNumberOfTilesShowingInColumn(); j++) {
                int i_dup = i;
                int j_dup = j;

                tiles[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        mouseClickHandle(mouseEvent, i_dup, j_dup);
                    }
                });

                tiles[i][j].setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        mouseDragStartHandle(mouseEvent, i_dup, j_dup);
                    }
                });

                tiles[i][j].setOnMouseDragReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        mouseDragEndHandle(mouseEvent, i_dup, j_dup);
                    }
                });

                tiles[i][j].setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        gameGraphicFunctions.showDetails(i_dup + gameData.getCornerCellIndex().first, j_dup + gameData.getCornerCellIndex().second);
                    }
                });
                tiles[i][j].setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        gameGraphicFunctions.hideDetails();
                    }
                });
                tiles[i][j].setOnDragDropped(dragEvent -> {
                    if (dragEvent.getDragboard().hasImage()) {
                        dragEvent.setDropCompleted(true);
                    }
                    dragEvent.consume();
                });
                tiles[i][j].setOnDragExited(dragEvent -> {
                    if (dragEvent.getDragboard().hasImage()) {
                        ColorAdjust colorAdjust = new ColorAdjust();
                        colorAdjust.setHue(0);
                        colorAdjust.setSaturation(0);
                        tiles[i_dup][j_dup].setEffect(colorAdjust);
                    }
                    dragEvent.consume();
                });
                tiles[i][j].setOnDragEntered(dragEvent -> {
                    if (dragEvent.getDragboard().hasImage()) {
                        ColorAdjust colorAdjust = new ColorAdjust();
                        colorAdjust.setHue(0.2);
                        colorAdjust.setSaturation(0.2);
                        tiles[i_dup][j_dup].setEffect(colorAdjust);
                    }
                    dragEvent.consume();
                });
                tiles[i][j].setOnDragOver(dragEvent -> {
                    if (dragEvent.getDragboard().hasImage()) {
                        dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        dragEvent.consume();
                    }
                });
            }
    }

    private void keyHandle(KeyEvent keyEvent) throws IOException {
        switch (gameData.getGameState()) {
            case CELL_SELECTED -> {
                if (keyEvent.getCode().equals(GameKeyConstants.cancelKey)) {
                    gameData.setGameState(GameState.VIEW_MAP);
                    gameData.setStartSelectedCellsPosition(null);
                    gameData.setEndSelectedCellsPosition(null);
                }
                else if (keyEvent.getCode().equals(GameKeyConstants.attackKey)) {
                    gameData.setGameState(GameState.ATTACKING);
                }
                else if (keyEvent.getCode().equals(GameKeyConstants.moveKey)) {
                    gameData.setGameState(GameState.MOVING);
                }
                else if (keyEvent.getCode().equals(GameKeyConstants.pourOilKey)) {
                    gameData.setGameState(GameState.POURING_OIL);
                    gameGraphicFunctions.engineersPourOil();
                }
                else if (keyEvent.getCode().equals(GameKeyConstants.dropLadderKey)) {
                    gameGraphicFunctions.dropLadders();
                }
                else if (keyEvent.getCode().equals(GameKeyConstants.disbandUnitKey)) {
                    gameGraphicFunctions.disbandUnits();
                }
                else if (keyEvent.getCode().equals(GameKeyConstants.buildEquipmentKey)) {
                    gameGraphicFunctions.buildEquipments();
                }
            }
            case VIEW_MAP -> {
                if (keyEvent.getCode().equals(GameKeyConstants.mapMoveLeft))
                    MapFunctions.moveMap(0, 0, 0, 1);
                else if (keyEvent.getCode().equals(GameKeyConstants.mapMoveRight))
                    MapFunctions.moveMap(0, 1, 0, 0);
                else if (keyEvent.getCode().equals(GameKeyConstants.mapMoveDown))
                    MapFunctions.moveMap(0, 0, 1, 0);
                else if (keyEvent.getCode().equals(GameKeyConstants.mapMoveUp))
                    MapFunctions.moveMap(1, 0, 0, 0);
                else if (keyEvent.getCode().equals(GameKeyConstants.mapZoomIn))
                    gameData.zoomIn();
                else if (keyEvent.getCode().equals(GameKeyConstants.mapZoomOut))
                    gameData.zoomOut();

                setUpAndShowMap();
            }
            case MOVING -> {
                if (keyEvent.getCode().equals(GameKeyConstants.moveFinalize)) {
                    gameData.setGameState(GameState.CELL_SELECTED);
                    gameGraphicFunctions.moveUnits();
                }
            }
            case ATTACKING -> {
                if (keyEvent.getCode().equals(GameKeyConstants.attackFinalize)) {
                    gameData.setGameState(GameState.ATTACKING);
                    gameGraphicFunctions.attackUnits();
                }
            }
        }
    }

    private void mouseClickHandle(MouseEvent mouseEvent, int tileX, int tileY) {
        switch (gameData.getGameState()) {
            case VIEW_MAP, CELL_SELECTED -> {
                gameData.setGameState(GameState.CELL_SELECTED);
                gameData.setStartSelectedCellsPosition(new Pair<>(tileX, tileY));
                gameData.setEndSelectedCellsPosition(new Pair<>(tileX, tileY));

                resetAllSelectedColors();

                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setHue(0.6);
                colorAdjust.setSaturation(0.2);
                tiles[tileX][tileY].setEffect(colorAdjust);
            }
        }
    }

    public void resetAllSelectedColors() {
        for (Pane[] panes : tiles)
            for (Pane pane : panes) {
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setHue(0);
                colorAdjust.setSaturation(0);
                pane.setEffect(colorAdjust);
            }
    }

    private void mouseDragStartHandle(MouseEvent mouseEvent, int tileX, int tileY) {
        switch (gameData.getGameState()) {
            case VIEW_MAP, CELL_SELECTED -> {
                gameData.setGameState(GameState.CELL_SELECTED);
                gameData.setStartSelectedCellsPosition(new Pair<>(tileX, tileY));
                gameData.setEndSelectedCellsPosition(new Pair<>(tileX, tileY));

                resetAllSelectedColors();
            }
        }
    }

    private void mouseDragEndHandle(MouseEvent mouseEvent, int tileX, int tileY) {
        switch (gameData.getGameState()) {
            case VIEW_MAP, CELL_SELECTED -> {
                gameData.setEndSelectedCellsPosition(new Pair<>(tileX, tileY));

                for (int i = gameData.getStartSelectedCellsPosition().first; i <= gameData.getEndSelectedCellsPosition().first; i++)
                    for (int j = gameData.getStartSelectedCellsPosition().second; j <= gameData.getEndSelectedCellsPosition().second; j++) {
                        ColorAdjust colorAdjust = new ColorAdjust();
                        colorAdjust.setHue(0.6);
                        colorAdjust.setSaturation(0.2);
                        tiles[tileX][tileY].setEffect(colorAdjust);
                    }
            }
        }
    }

    private void makeBuildingBar() {
        buildingPopupPane = new Pane();
        //todo abbasfar: check if the constructor above is correctly used
        VBox vBox = makeVBox(1020, 180, new VBox());
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
            buildingPopupPane.setTranslateX(940);
            buildingPopupPane.setTranslateY(170);
            if (buildingPopupPane.getChildren().size() != 0) {
                ScrollPane scrollPane = (ScrollPane) buildingPopupPane.getChildren().get(0);
                boolean againClick = scrollPane.getContent().equals(subMenus.get(category));
                buildingPopupPane.getChildren().clear();
                if (!againClick) addToPopup(category);
            }
            else {

                addToPopup(category);
            }
        });
    }

    private void addToPopup(Category category) {
        ScrollPane scrollPane = new ScrollPane(subMenus.get(category));
        scrollPane.setMaxHeight(400);
        scrollPane.setPrefSize(70, 400);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        buildingPopupPane.getChildren().add(scrollPane);
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
                } catch (Exception e) {
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
        //todo maybe faratin beautify
        popularityPopupPane = new Pane();
        //todo abbasfar: check if the constructor above is correctly completed
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
        for (int i = 1; i <= 4; i++) addPopularityPopUpVbox(factorsHBox, i);
        Text text = new Text("change for next turn: " + 0); //todo jasbi correct number
        outerBox.getChildren().add(text);
        popularityPopupPane.getChildren().add(outerBox);
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
        GamePopUpMenus popularityPopUp = new GamePopUpMenus(mainPane, popularityPopupPane, GamePopUpMenus.PopUpType.POPULARITY_DETAIL);
        Text text = new Text("Popularity : " + 0);//correct number
        text.setStyle("-fx-font: 18 arial;");
        text.setOnMouseClicked(mouseEvent -> {
            if (!popularityPopUp.isShowing()) {
                popularityPopupPane.setTranslateX(340);
                popularityPopupPane.setTranslateY(530);
                popularityPopUp.showAndWait();
            }
            else popularityPopUp.hide();
        });
        CircleImage circleImage = new CircleImage("/images/green.png", 20); //todo jasbi change
        Rectangle button = makeButton();
        makeHBox(text, circleImage, button);
    }

    private void makeHBox(Text text, Node circleImage, Rectangle button) {
        HBox hBox = new HBox(text, circleImage, button);
        hBox.setAlignment(Pos.CENTER);
        hBox.setTranslateX(340);
        hBox.setTranslateY(670);
        hBox.setPrefWidth(400);
        hBox.setSpacing(40);
        hBox.setBackground(new Background(new BackgroundFill(Color.GOLD, null, null)));
        mainPane.getChildren().add(hBox);
    }

    private static Rectangle makeButton() {
        Rectangle button = new Rectangle(90, 30);
        button.setArcHeight(30.0);
        button.setArcWidth(30.0);
//        Image image = new Image(MapMenu.class.getResource("/images/menus/changeFactors.png").toString());
//        button.setFill(new ImagePattern(image));
        button.setOnMouseClicked(mouseEvent -> {
            try {
                new SetFactorsMenu().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return button;
    }

    private void addPopularityPopUpVbox(HBox factorsHBox, int switcher) {
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