package view.menus;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.ImagePracticalFunctions;
import model.MapTemplate;
import model.Pair;
import model.map.Cell;
import model.map.CellType;
import model.map.Map;
import model.map.TreeType;

import javax.print.attribute.standard.PrinterName;

public class EditMapGraphics {
    @FXML
    private Pane pane;
    private CellType selectedCellType=CellType.PLAIN_GROUND;
    private TreeType selectedTreeType=TreeType.CHERRY;
    private SelectMode selectMode=SelectMode.CELL_TYPE;
    private Pair<Integer,Integer> cornerIndex=new Pair<>(0,0);
    public void initializePane(MapTemplate mapTemplate){
        initializeTextureButtons();

        showMap(mapTemplate);
    }

    private void initializeTextureButtons(){
        int index=0;
        for(CellType cellType:CellType.values()){
            ImageView imageView=new ImageView(cellType.getImage());

            ImagePracticalFunctions.fitWidthHeight(imageView,50,50);
            imageView.setLayoutX(0);
            imageView.setLayoutY(index*50);

            pane.getChildren().add(imageView);

            index++;

            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectMode=SelectMode.CELL_TYPE;
                    selectedCellType=cellType;
                }
            });
        }

        for(TreeType treeType: TreeType.values()){
            ImageView imageView=new ImageView(new Image(MapMenu.class.getResource(treeType.getShowingImagePath()).toExternalForm()));

            ImagePracticalFunctions.fitWidthHeight(imageView,50,50);
            imageView.setLayoutX(50);
            imageView.setLayoutY(index*50);

            pane.getChildren().add(imageView);

            index++;

            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectMode=SelectMode.TREE_TYPE;
                    selectedTreeType=treeType;
                }
            });
        }

        ImageView imageView=new ImageView(MapMenu.class.getResource("/images/empire.png").toExternalForm());

        ImagePracticalFunctions.fitWidthHeight(imageView,50,50);
        imageView.setLayoutX(50);
        imageView.setLayoutY(index*50);

        pane.getChildren().add(imageView);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectMode=SelectMode.EMPIRE;
            }
        });

    }
    private void showMap(MapTemplate mapTemplate){
        for(int i=0;i<19;i++)
            for(int j=0;j<14;j++){
                ImageView imageView=new ImageView(mapTemplate.getCells()[0][0].getCellType().getImage());

                ImagePracticalFunctions.fitWidthHeight(imageView,50,50);
                imageView.setLayoutX(i*50+70);
                imageView.setLayoutY(j*50);

                pane.getChildren().add(imageView);

                int i_dup=i;
                int j_dup=j;
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(selectMode.equals(SelectMode.EMPIRE))
                            handleEmpireClick(mapTemplate,i_dup,j_dup);
                        else if(selectMode.equals(SelectMode.CELL_TYPE))
                            handleCellTypeClick(mapTemplate,i_dup,j_dup);
                        else if(selectMode.equals(SelectMode.TREE_TYPE))
                            handleTreeTypeClick(mapTemplate,i_dup,j_dup);
                    }
                });
            }
    }

    private void handleEmpireClick(MapTemplate mapTemplate,int x,int y){
        int realX=x+cornerIndex.first;
        int realY=y+cornerIndex.second;
    }
    private void handleCellTypeClick(MapTemplate mapTemplate,int x,int y){
        int realX=x+cornerIndex.first;
        int realY=y+cornerIndex.second;
    }
    private void handleTreeTypeClick(MapTemplate mapTemplate,int x,int y){
        int realX=x+cornerIndex.first;
        int realY=y+cornerIndex.second;
    }


    private enum SelectMode{
        CELL_TYPE,
        TREE_TYPE,
        EMPIRE,
    }
}
