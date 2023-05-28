module project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.common;
    requires com.google.gson;


    exports view;
    exports view.menus;

    opens view to javafx.fxml;
    opens view.menus to javafx.fxml;

    exports model;
}