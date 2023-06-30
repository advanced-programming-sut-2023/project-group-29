package view.menus;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;
import model.map.Cell;
import model.map.CellType;
import model.map.TreeType;
import view.Command;

import java.net.URL;

public class EditMapGraphics extends Application {
    @FXML
    private Pane pane;
    private CellType selectedCellType = CellType.PLAIN_GROUND;
    private TreeType selectedTreeType = TreeType.CHERRY;
    private SelectMode selectMode = SelectMode.CELL_TYPE;
    private final Pair<Integer, Integer> cornerIndex = new Pair<>(0, 0);
    private int currentEmpiresCount = 0;

    private MapTemplate mapTemplate = null;

    public EditMapGraphics() {
    }

    public EditMapGraphics(MapTemplate mapTemplate) {
        this.mapTemplate = mapTemplate;
    }

    @Override
    public void start(Stage stage) throws Exception {

        URL url = Command.class.getResource("/FXML/EditMap.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Pane mainPane = loader.load();

        if (mapTemplate != null) {
            EditMapGraphics graphicsController = loader.getController();
            graphicsController.initializePane(mapTemplate);
        }

        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        mainPane.requestFocus();
        stage.show();
    }

    public void initializePane(MapTemplate mapTemplate) {
        initializeTextureButtons();

        showMap(mapTemplate);

        initializeSaveButton(mapTemplate);
    }

    private void initializeSaveButton(MapTemplate mapTemplate) {
        ImageView saveButton = new ImageView(new Image(MapMenu.class.getResource("/images/menus/SaveAndQuitButton.png").toExternalForm()));
        ImagePracticalFunctions.fitWidthHeight(saveButton, 300, 200);
        saveButton.setLayoutX(25);
        saveButton.setLayoutY(500);

        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                saveMap(mapTemplate);
            }
        });
    }

    private void saveMap(MapTemplate mapTemplate) {
        if (currentEmpiresCount != mapTemplate.getUsersCount()) {
            AlertWindowPane alertWindowPane = new AlertWindowPane(pane, Color.RED);
            alertWindowPane.addTitle("saving failed");
            alertWindowPane.addText("please choose exactly " + mapTemplate.getUsersCount() + " empires!");
            alertWindowPane.show();
            return;
        }

        AppData.getMapTemplates().add(mapTemplate);

        try {
            new MainMenu().start(AppData.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeTextureButtons() {
        int index = 0;
        for (CellType cellType : CellType.values()) {
            ImageView imageView = new ImageView(cellType.getImage());

            ImagePracticalFunctions.fitWidthHeight(imageView, 50, 50);
            imageView.setLayoutX(0);
            imageView.setLayoutY(index * 50);

            pane.getChildren().add(imageView);

            index++;

            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectMode = SelectMode.CELL_TYPE;
                    selectedCellType = cellType;
                }
            });
        }

        for (TreeType treeType : TreeType.values()) {
            ImageView imageView = new ImageView(new Image(MapMenu.class.getResource(treeType.getShowingImagePath()).toExternalForm()));

            ImagePracticalFunctions.fitWidthHeight(imageView, 50, 50);
            imageView.setLayoutX(50);
            imageView.setLayoutY(index * 50);

            pane.getChildren().add(imageView);

            index++;

            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectMode = SelectMode.TREE_TYPE;
                    selectedTreeType = treeType;
                }
            });
        }

        ImageView imageView = new ImageView(MapMenu.class.getResource("/images/empire.png").toExternalForm());

        ImagePracticalFunctions.fitWidthHeight(imageView, 50, 50);
        imageView.setLayoutX(50);
        imageView.setLayoutY(index * 50);

        index++;

        pane.getChildren().add(imageView);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectMode = SelectMode.EMPIRE;
            }
        });

        ImageView clearEmpire = new ImageView(MapMenu.class.getResource("/images/eraser.png").toExternalForm());

        ImagePracticalFunctions.fitWidthHeight(clearEmpire, 50, 50);
        clearEmpire.setLayoutX(50);
        clearEmpire.setLayoutY(index * 50);

        pane.getChildren().add(clearEmpire);
        clearEmpire.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectMode = SelectMode.ERASING;
            }
        });

    }

    private void showMap(MapTemplate mapTemplate) {
        for (int i = 0; i < 19; i++)
            for (int j = 0; j < 14; j++) {
                Cell cell = mapTemplate.getCells()[i + cornerIndex.first][j + cornerIndex.second];

                Pane tile = new Pane();
                tile.setMaxWidth(50);
                tile.setMinWidth(50);
                tile.setMaxHeight(50);
                tile.setMinHeight(50);

                BackgroundSize backgroundSize = new BackgroundSize(50, 50, false, false, false, false);
                tile.setBackground(new Background(new BackgroundImage(cell.getCellType().getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize)));

                pane.getChildren().add(tile);

                if (cell.getTreeTypes() != null) {
                    ImageView tree = new ImageView(new Image(MapMenu.class.getResource(cell.getTreeTypes().getShowingImagePath()).toExternalForm()));

                    ImagePracticalFunctions.fitWidthHeight(tree, 50, 50);
                    tree.setLayoutX(i * 50 + 70);
                    tree.setLayoutY(j * 50);

                    tile.getChildren().add(tree);
                }
                if (mapTemplate.getHasEmpire()[i + cornerIndex.first][j + cornerIndex.second]) {
                    ImageView tree = new ImageView(new Image(MapMenu.class.getResource("/images/units/states/sheild.png").toExternalForm()));

                    ImagePracticalFunctions.fitWidthHeight(tree, 50, 50);
                    tree.setLayoutX(i * 50 + 70);
                    tree.setLayoutY(j * 50);

                    tile.getChildren().add(tree);
                }

                int i_dup = i;
                int j_dup = j;
                tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (selectMode.equals(SelectMode.EMPIRE))
                            handleEmpireClick(mapTemplate, i_dup, j_dup);
                        else if (selectMode.equals(SelectMode.CELL_TYPE))
                            handleCellTypeClick(mapTemplate, i_dup, j_dup);
                        else if (selectMode.equals(SelectMode.TREE_TYPE))
                            handleTreeTypeClick(mapTemplate, i_dup, j_dup);
                        else if (selectMode.equals(SelectMode.ERASING))
                            handleEmpireDelete(mapTemplate, i_dup, j_dup);
                    }
                });
            }
    }

    private void handleEmpireClick(MapTemplate mapTemplate, int x, int y) {
        int realX = x + cornerIndex.first;
        int realY = y + cornerIndex.second;

        if (!hasNearEmpire(mapTemplate, realX, realY)) {
            mapTemplate.getHasEmpire()[realX][realY] = true;
            currentEmpiresCount++;
        }
    }

    private void handleEmpireDelete(MapTemplate mapTemplate, int x, int y) {
        int realX = x + cornerIndex.first;
        int realY = y + cornerIndex.second;

        if (mapTemplate.getHasEmpire()[realX][realY]) {
            mapTemplate.getHasEmpire()[realX][realY] = false;
            currentEmpiresCount--;
        }
    }

    private void handleCellTypeClick(MapTemplate mapTemplate, int x, int y) {
        int realX = x + cornerIndex.first;
        int realY = y + cornerIndex.second;

        if (changeTypeAllowed(mapTemplate, realX, realY))
            mapTemplate.getCells()[realX][realY].setCellType(selectedCellType);
    }

    private void handleTreeTypeClick(MapTemplate mapTemplate, int x, int y) {
        int realX = x + cornerIndex.first;
        int realY = y + cornerIndex.second;

        if (changeTypeAllowed(mapTemplate, realX, realY))
            mapTemplate.getCells()[realX][realY].setTree(selectedTreeType);
    }

    private boolean changeTypeAllowed(MapTemplate mapTemplate, int realX, int realY) {
        if (isValidIndex(mapTemplate, realX, realY) && mapTemplate.getHasEmpire()[realX][realY])
            return false;
        if (isValidIndex(mapTemplate, realX - 1, realY) && mapTemplate.getHasEmpire()[realX - 1][realY])
            return false;
        if (isValidIndex(mapTemplate, realX, realY - 1) && mapTemplate.getHasEmpire()[realX][realY - 1])
            return false;
        return !isValidIndex(mapTemplate, realX - 1, realY - 1) || !mapTemplate.getHasEmpire()[realX - 1][realY - 1];
    }

    private boolean hasNearEmpire(MapTemplate mapTemplate, int realX, int realY) {
        for (int i = -5; i <= 5; i++)
            for (int j = -5; j <= 5; j++) {
                if (Math.abs(i) + Math.abs(j) >= 5)
                    continue;
                if (!isValidIndex(mapTemplate, realX + i, realY + j))
                    continue;
                if (mapTemplate.getHasEmpire()[realX + i][realY + j])
                    return true;
            }

        return false;
    }

    private boolean isValidIndex(MapTemplate mapTemplate, int x, int y) {
        if (x >= mapTemplate.getWidth() || x < 0)
            return false;
        return y <= mapTemplate.getHeight() && y >= 0;
    }


    private enum SelectMode {
        CELL_TYPE,
        TREE_TYPE,
        EMPIRE,
        ERASING
    }
}
