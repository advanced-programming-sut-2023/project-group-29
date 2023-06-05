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
    exports model.gamestates;
    exports model.unitfeatures;
    exports view.menus.gamepopupmenus;
    opens view.menus.gamepopupmenus to javafx.fxml;
}