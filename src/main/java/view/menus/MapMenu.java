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
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;
import model.buildings.Building;
import model.buildings.Category;
import model.buildings.buildingTypes.BuildType;
import model.gamestates.GameState;
import model.map.Cell;
import view.menus.gamepopupmenus.SetFactorsMenu;
import view.shape.BuildingIcon;
import view.shape.CircleImage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class MapMenu extends Application {
    private PixelWriter miniMapWriter;
    private Pane mainPane = null;
    private GameData gameData = null;
    private Pane[][] tiles = null;
    private final Group tilesGroupInMainPainChildren = new Group();
    private GameGraphicFunctions gameGraphicFunctions;
    private Stage stage;
    private Pane popularityPopupPane;
    private GamePopUpMenus popularityPopup;
    private Pane buildingPopupPane;
    private final HashMap<Category, VBox> subMenus = new HashMap<>();
    private GamePopUpMenus currentCellDetailsPopup;

    public PixelWriter getMiniMapWriter() {
        return miniMapWriter;
    }

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
        scene.setOnKeyPressed(keyEvent -> {
            try {
                keyHandle(keyEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        setUpAndShowMap();
        makeMainPopularity(gameData.getPlayingEmpire());
        makeBuildingBar();
        makeMiniMap();
        GamePopUpMenus buildingPopUp = new GamePopUpMenus
                (mainPane, buildingPopupPane, GamePopUpMenus.PopUpType.BUILDING_ICONS_COLUMN);
        buildingPopUp.showAndWait();
        stage.setScene(scene);
        stage.show();
    }

    private void makeMiniMap() {
        Rectangle miniMap = new Rectangle(100, 100);
        miniMap.setTranslateX(10);
        miniMap.setTranslateY(10);
        int y = gameData.getMap().getHeight();
        int x = gameData.getMap().getWidth();
        WritableImage image = new WritableImage(x, y);
        miniMapWriter = image.getPixelWriter();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Cell cell = gameData.getMap().getCells()[i][j];
                Image image1 = new Image(MapMenu.class.getResource(cell.getCellType().getImageAddress()).toString());
                Color color = image1.getPixelReader().getColor(0, 0);
                miniMapWriter.setColor(i, j, color);
            }
        }
        miniMap.setFill(new ImagePattern(image));
        mainPane.getChildren().add(miniMap);
        miniMap.setOnMouseClicked(this::moveMap);
    }

    private void moveMap(MouseEvent mouseEvent) {
        if (!gameData.getGameState().equals(GameState.VIEW_MAP)) return;
        int x1 = (int) Math.floor(mouseEvent.getX());
        int y1 = (int) Math.floor(mouseEvent.getY());
        gameData.setCornerCellIndex(new Pair<>(x1, y1));
        setUpAndShowMap();
    }

    public void setUpAndShowMap() {
        tilesGroupInMainPainChildren.getChildren().clear();
        tiles = MapFunctions.showMap(gameData.getCornerCellIndex().first, gameData.getCornerCellIndex().second, tilesGroupInMainPainChildren);
        setTilesListeners();

        if(Pair.notNull(gameData.getStartSelectedCellsPosition()) && Pair.notNull(gameData.getEndSelectedCellsPosition()))
            for(int i=gameData.getStartSelectedCellsPosition().first;i<=gameData.getEndSelectedCellsPosition().first;i++)
                for(int j=gameData.getStartSelectedCellsPosition().second;j<=gameData.getEndSelectedCellsPosition().second;j++)
                    effectTile(tiles[i][j],0.6,0.2);

        if(Pair.notNull(gameData.getDestinationCellPosition()))
            effectTile(tiles[gameData.getDestinationCellPosition().first][gameData.getDestinationCellPosition().second], -0.2, 0.2);
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


                tiles[i][j].setOnDragDetected(mouseEvent -> {
                    Dragboard dragboard = tiles[i_dup][j_dup].startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putString("");
                    dragboard.setContent(content);
                    mouseDragStartHandle(i_dup, j_dup);
                });

                tiles[i][j].setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (gameData.getGameState().equals(GameState.VIEW_MAP))
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
                    if(dragEvent.getDragboard().hasString())
                        mouseDragEndHandle(i_dup, j_dup);
                    else if (dragEvent.getDragboard().hasImage()) {
                        int x = i_dup + gameData.getCornerCellIndex().first;
                        int y = j_dup + gameData.getCornerCellIndex().second;
                        gameGraphicFunctions.callBuildBuilding(x, y, BuildingIcon.getDraggingBuildingName());
                        dragEvent.setDropCompleted(true);
                    }

                    dragEvent.consume();
                });

                tiles[i][j].setOnDragExited(dragEvent -> {
                    if (dragEvent.getDragboard().hasImage() || dragEvent.getDragboard().hasString()) {
                        effectTile(tiles[i_dup][j_dup], 0, 0);
                    }
                    dragEvent.consume();
                });
                tiles[i][j].setOnDragEntered(dragEvent -> {
                    if (dragEvent.getDragboard().hasImage() || dragEvent.getDragboard().hasString()) {
                        effectTile(tiles[i_dup][j_dup], 0.2, 0.2);
                    }
                    dragEvent.consume();
                });
                tiles[i][j].setOnDragOver(dragEvent -> {
                    if (dragEvent.getDragboard().hasImage() || dragEvent.getDragboard().hasString()) {
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
                    resetAllSelectedColors();
                } else if (keyEvent.getCode().equals(GameKeyConstants.attackKey)) {
                    gameGraphicFunctions.showAttackPopUp();
                    gameData.setGameState(GameState.ATTACKING);
                } else if (keyEvent.getCode().equals(GameKeyConstants.moveKey)) {
                    gameData.setGameState(GameState.MOVING);
                } else if (keyEvent.getCode().equals(GameKeyConstants.patrolUnit)) {
                    gameData.setGameState(GameState.PATROLLING);
                } else if (keyEvent.getCode().equals(GameKeyConstants.digTunnel)) {
                    gameData.setGameState(GameState.TUNNELLING);
                } else if (keyEvent.getCode().equals(GameKeyConstants.pourOilKey)) {
                    gameData.setGameState(GameState.POURING_OIL);
                } else if (keyEvent.getCode().equals(GameKeyConstants.dropLadderKey)) {
                    gameGraphicFunctions.dropLadders();
                } else if (keyEvent.getCode().equals(GameKeyConstants.disbandUnitKey)) {
                    gameGraphicFunctions.disbandUnits();
                } else if (keyEvent.getCode().equals(GameKeyConstants.buildEquipmentKey)) {
                    gameGraphicFunctions.buildEquipments();
                } else if (keyEvent.getCode().equals(GameKeyConstants.addRemoveUnit)) {
                    gameGraphicFunctions.addOrRemoveSelectedUnits();
                } else if (keyEvent.getCode().equals(GameKeyConstants.selectBuilding)) {
                    gameGraphicFunctions.selectBuilding();
                } else if (keyEvent.getCode().equals(GameKeyConstants.pasteBuilding)) {
                    gameGraphicFunctions.pasteBuilding();
                } else if (keyEvent.getCode().equals(GameKeyConstants.dropUnit)) {
                    gameGraphicFunctions.dropUnit();
                } else if (keyEvent.getCode().equals(GameKeyConstants.dropBuilding)) {
                    gameGraphicFunctions.dropBuilding();
                } else if (keyEvent.getCode().equals(GameKeyConstants.setTexture)) {
                    gameGraphicFunctions.setTexture();
                } else if (keyEvent.getCode().equals(GameKeyConstants.setStateOfUnit)) {
                    gameGraphicFunctions.setStateOfUnits();
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
                    gameData.setGameState(GameState.VIEW_MAP);
                    gameGraphicFunctions.moveUnits();

                    gameData.setDestinationCellPosition(null);
                    gameData.setStartSelectedCellsPosition(null);
                    gameData.setEndSelectedCellsPosition(null);
                }
            }
            case ATTACKING -> {
                if (keyEvent.getCode().equals(GameKeyConstants.attackFinalize)) {
                    gameData.setGameState(GameState.VIEW_MAP);
                    gameGraphicFunctions.attackUnits();

                    gameData.setDestinationCellPosition(null);
                    gameData.setStartSelectedCellsPosition(null);
                    gameData.setEndSelectedCellsPosition(null);
                }
            }
            case TUNNELLING -> {
                if (keyEvent.getCode().equals(GameKeyConstants.diggingTunnelFinalize)) {
                    gameData.setGameState(GameState.VIEW_MAP);
                    gameGraphicFunctions.digTunnel();

                    gameData.setDestinationCellPosition(null);
                    gameData.setStartSelectedCellsPosition(null);
                    gameData.setEndSelectedCellsPosition(null);
                }
            }
            case POURING_OIL -> {
                if (keyEvent.getCode().equals(GameKeyConstants.pourOilFinalize)) {
                    gameData.setGameState(GameState.VIEW_MAP);
                    gameGraphicFunctions.engineersPourOil();

                    gameData.setDestinationCellPosition(null);
                    gameData.setStartSelectedCellsPosition(null);
                    gameData.setEndSelectedCellsPosition(null);
                }
            }
            case PATROLLING -> {
                if (keyEvent.getCode().equals(GameKeyConstants.patrolUnitFinalize)) {
                    gameData.setGameState(GameState.VIEW_MAP);
                    gameGraphicFunctions.patrolUnits();

                    gameData.setDestinationCellPosition(null);
                    gameData.setStartSelectedCellsPosition(null);
                    gameData.setEndSelectedCellsPosition(null);
                }
            }
        }
        setUpAndShowMap();
    }

    private void mouseClickHandle(MouseEvent mouseEvent, int tileX, int tileY) {
        switch (gameData.getGameState()) {
            case VIEW_MAP, CELL_SELECTED -> {
                gameData.setGameState(GameState.CELL_SELECTED);
                gameData.setStartSelectedCellsPosition(new Pair<>(tileX, tileY));
                gameData.setEndSelectedCellsPosition(new Pair<>(tileX, tileY));

                Cell cell = gameData.getMap().getCells()
                        [tileX + gameData.getCornerCellIndex().first][tileY + gameData.getCornerCellIndex().second];

                gameData.getSelectedUnits().clear();
                gameData.getAllUnitsInSelectedCells().clear();

                gameData.getAllUnitsInSelectedCells().addAll(cell.getMovingObjectsOfPlayer(gameData.getPlayerOfTurn()));
                gameData.getSelectedUnits().addAll(cell.getMovingObjectsOfPlayer(gameData.getPlayerOfTurn()));

                resetAllSelectedColors();

                effectTile(tiles[tileX][tileY], 0.6, 0.2);
            }
            case ATTACKING, POURING_OIL, MOVING, PATROLLING -> {
                resetAllSelectedColors();
                effectTile(tiles[tileX][tileY], -0.2, 0.2);
                gameData.setDestinationCellPosition(new Pair<>(tileX, tileY));
            }
        }
    }

    public void effectTile(Pane tile, double hue, double saturation) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(hue);
        colorAdjust.setSaturation(saturation);
        tile.setEffect(colorAdjust);
    }

    public void resetAllSelectedColors() {
        for (Pane[] panes : tiles)
            for (Pane pane : panes)
                effectTile(pane, 0, 0);
    }

    private void mouseDragStartHandle(int tileX, int tileY) {
        switch (gameData.getGameState()) {
            case VIEW_MAP, CELL_SELECTED -> {
                gameData.setGameState(GameState.CELL_SELECTED);
                gameData.setStartSelectedCellsPosition(new Pair<>(tileX, tileY));
                gameData.setEndSelectedCellsPosition(new Pair<>(tileX, tileY));

                resetAllSelectedColors();
            }
        }
    }

    private void mouseDragEndHandle(int tileX, int tileY) {
        switch (gameData.getGameState()) {
            case VIEW_MAP, CELL_SELECTED -> {

                if (tileX < gameData.getStartSelectedCellsPosition().first ||
                        tileY < gameData.getStartSelectedCellsPosition().second) {
                    gameData.setEndSelectedCellsPosition(
                            new Pair<>(gameData.getStartSelectedCellsPosition().first, gameData.getStartSelectedCellsPosition().second)
                    );
                } else gameData.setEndSelectedCellsPosition(new Pair<>(tileX, tileY));

                gameData.getSelectedUnits().clear();
                gameData.getAllUnitsInSelectedCells().clear();

                for (int i = gameData.getStartSelectedCellsPosition().first; i <= gameData.getEndSelectedCellsPosition().first; i++)
                    for (int j = gameData.getStartSelectedCellsPosition().second; j <= gameData.getEndSelectedCellsPosition().second; j++) {
                        effectTile(tiles[i][j], 0.6, 0.2);

                        Cell cell = gameData.getMap().getCells()
                                [i + gameData.getCornerCellIndex().first][j + gameData.getCornerCellIndex().second];
                        gameData.getAllUnitsInSelectedCells().addAll(cell.getMovingObjectsOfPlayer(gameData.getPlayerOfTurn()));
                        gameData.getSelectedUnits().addAll(cell.getMovingObjectsOfPlayer(gameData.getPlayerOfTurn()));
                    }
            }
        }
    }

    private void makeBuildingBar() {
        buildingPopupPane = new Pane();
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
            } else {

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
                    icon.setDragFunctions(buildType.getBuildingType().name());
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

    private void makeFactorsTable() {
        VBox outerBox = makeOuterBox();
        Text text1 = new Text("Popularity factors:");
        text1.setStyle("-fx-font: 18 arial;");
        HBox factorsHBox = new HBox();
        factorsHBox.setSpacing(50);
        factorsHBox.setAlignment(Pos.CENTER);
        outerBox.getChildren().add(text1);
        outerBox.getChildren().add(factorsHBox);
        Empire empire = gameData.getPlayingEmpire();
        for (int i = 1; i <= 4; i++) addPopularityPopUpVbox(factorsHBox, i, empire);
        Text text = makeTextForSumOfChanges(empire);
        outerBox.getChildren().add(text);
        popularityPopupPane.getChildren().add(outerBox);
    }

    private static Text makeTextForSumOfChanges(Empire empire) {
        int popularityChange = 0;
        for (Empire.PopularityFactors factor : Empire.PopularityFactors.values())
            popularityChange += empire.getPopularityChange(factor);
        return new Text("change for next turn: " + popularityChange);
    }

    private VBox makeOuterBox() {
        VBox outerBox = new VBox();
        outerBox.setAlignment(Pos.CENTER);
        outerBox.setPrefWidth(400);
        outerBox.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, null, null)));
        mainPane.getChildren().add(outerBox);
        return outerBox;
    }

    private void makeMainPopularity(Empire empire) {
        Text text = new Text("Popularity : " + empire.getPopularity());
        text.setStyle("-fx-font: 18 arial;");
        text.setOnMouseClicked(mouseEvent -> {
            if (popularityPopupPane == null) {
                newPopularityPopup();
            } else {
                popularityPopup.hide();
                popularityPopupPane = null;
            }
        });
        CircleImage circleImage = chooseFaceColor(empire.getPopularity());
        Rectangle button = makeChangeFactorButton();
        Rectangle nextTurnButton = makeNextTurnButton();
        makeHBox(text, circleImage, button, nextTurnButton);
    }

    private Rectangle makeNextTurnButton() {
        Rectangle button = new Rectangle(30, 30);
        button.setArcHeight(30.0);
        button.setArcWidth(30.0);
        Image image = new Image(MapMenu.class.getResource("/images/nextTurn.png").toString());
        button.setFill(new ImagePattern(image));
        button.setOnMouseClicked(mouseEvent -> {
            if (!GameController.nextTurn()) {
                GameController.showWinner();
                //todo back to main menu
            }
            makeMainPopularity(gameData.getPlayingEmpire());
        });
        return button;
    }

    private void newPopularityPopup() {
        popularityPopupPane = new Pane();
        makeFactorsTable();
        popularityPopupPane.setTranslateX(340);
        popularityPopupPane.setTranslateY(530);
        popularityPopup = new GamePopUpMenus
                (mainPane, popularityPopupPane, GamePopUpMenus.PopUpType.POPULARITY_DETAIL);
        popularityPopup.showAndWait();
    }

    private static CircleImage chooseFaceColor(int popularity) {
        if (popularity > 60) {
            return new CircleImage("/images/empirefactors/green.png", 20);
        } else if (popularity > 30) {
            return new CircleImage("/images/empirefactors/yellow.png", 20);
        } else {
            return new CircleImage("/images/empirefactors/red.png", 20);
        }
    }

    private void makeHBox(Text text, Node circleImage, Rectangle button, Rectangle button2) {
        HBox hBox = new HBox(text, circleImage, button, button2);
        hBox.setAlignment(Pos.CENTER);
        hBox.setTranslateX(340);
        hBox.setTranslateY(670);
        hBox.setPrefWidth(400);
        hBox.setSpacing(40);
        hBox.setBackground(new Background(new BackgroundFill(Color.GOLD, null, null)));
        mainPane.getChildren().add(hBox);
    }

    private static Rectangle makeChangeFactorButton() {
        Rectangle button = new Rectangle(90, 30);
        button.setArcHeight(30.0);
        button.setArcWidth(30.0);
        Image image = new Image(MapMenu.class.getResource("/images/menus/changeFactors.png").toString());
        button.setFill(new ImagePattern(image));
        button.setOnMouseClicked(mouseEvent -> {
            try {
                new SetFactorsMenu().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return button;
    }

    private void addPopularityPopUpVbox(HBox factorsHBox, int switcher, Empire empire) {
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        factorsHBox.getChildren().add(vBox);
        empire.transferFactorAffectToHashMap();
        for (Empire.PopularityFactors factor : Empire.PopularityFactors.values()) {
            switch (switcher) {
                case 1 -> {
                    Text text = new Text(factor.name());
                    vBox.getChildren().add(text);
                }
                case 2 -> {
                    Text text = new Text(": " + empire.getMeasureOfFactor(factor));
                    vBox.getChildren().add(text);
                }
                case 3 -> {
                    CircleImage circleImage = chooseEachFactorFaceColor(empire, factor);
                    vBox.getChildren().add(circleImage);
                }
                case 4 -> {
                    Text text = new Text("affect --> " + empire.getPopularityChange(factor));
                    vBox.getChildren().add(text);
                }
            }
        }
    }

    private static CircleImage chooseEachFactorFaceColor(Empire empire, Empire.PopularityFactors factor) {
        int factorAffect = empire.getMeasureOfFactor(factor);
        if (factorAffect > 0) {
            return new CircleImage("/images/empirefactors/green.png", 8);
        } else if (factorAffect < 0) {
            return new CircleImage("/images/empirefactors/red.png", 8);
        } else {
            return new CircleImage("/images/empirefactors/yellow.png", 8);
        }
    }

    public Pane getMainPane() {
        return mainPane;
    }
}