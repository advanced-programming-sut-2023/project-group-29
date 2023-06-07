package view.shape;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ExperimentalCell extends Rectangle {
    public ExperimentalCell() {
        super(200,200);
        setFill(Color.BLUE);
    }

    public void setDragFunctions() {
        this.setOnDragDropped(dragEvent -> {
            if (dragEvent.getDragboard().hasImage()) {
                dragEvent.setDropCompleted(true);
            }
            dragEvent.consume();
        });
        this.setOnDragExited(dragEvent -> {
            if (dragEvent.getDragboard().hasImage()) {
                setFill(Color.BLUE);
            }
            dragEvent.consume();
        });
        this.setOnDragEntered(dragEvent -> {
            if (dragEvent.getDragboard().hasImage()) {
                setFill(Color.GREEN); //todo jasbi maybe conditional
            }
            dragEvent.consume();
        });
        this.setOnDragOver(dragEvent -> {
            if (dragEvent.getDragboard().hasImage()) {
                dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                dragEvent.consume();
            }
        });
    }
}
