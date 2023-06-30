package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LeaderBoard extends TableView<LeaderBoard.TableRow> {

    private final ObservableList<TableRow> data = FXCollections.observableArrayList();

    public void setup() {
        TableColumn<TableRow, String> indexColumn = new TableColumn<>("rank");
        indexColumn.setMinWidth(100);
        indexColumn.setMaxWidth(100);
        indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));


        TableColumn<TableRow, String> usernameColumn = new TableColumn<>("username");
        usernameColumn.setMinWidth(100);
        usernameColumn.setMaxWidth(100);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));


        TableColumn<TableRow, String> scoreColumn = new TableColumn<>("high score");
        scoreColumn.setMinWidth(100);
        scoreColumn.setMaxWidth(100);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("highScore"));

        AppData.sortUserByHighScore();
        for (int i = 0; i < AppData.getUsers().size(); i++) {
            User user = AppData.getUsers().get(i);
            data.add(new TableRow(new SimpleStringProperty("" + (i + 1)), new SimpleStringProperty(user.getUsername()), new SimpleStringProperty("" + user.getHighScore())));
        }
        if (AppData.getUsers().size() == 0)
            data.add(new TableRow(new SimpleStringProperty(""), new SimpleStringProperty(""), new SimpleStringProperty("")));

        this.setItems(data);
        this.getColumns().addAll(indexColumn, usernameColumn, scoreColumn);
    }

    public class TableRow {
        private final SimpleStringProperty index;
        private final SimpleStringProperty username;
        private final SimpleStringProperty highScore;

        public TableRow(SimpleStringProperty index, SimpleStringProperty username, SimpleStringProperty highScore) {
            this.index = index;
            this.username = username;
            this.highScore = highScore;
        }

        public SimpleStringProperty indexProperty() {
            return index;
        }

        public SimpleStringProperty usernameProperty() {
            return username;
        }

        public SimpleStringProperty highScoreProperty() {
            return highScore;
        }
    }
}
