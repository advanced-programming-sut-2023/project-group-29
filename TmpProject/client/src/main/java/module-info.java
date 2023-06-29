module client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    exports view;
    opens view to javafx.fxml;
}