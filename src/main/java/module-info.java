module project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.common;
    requires com.google.gson;
    requires java.datatransfer;
    requires java.desktop;


    exports view;
    exports view.menus;

    opens view to javafx.fxml;
    opens view.menus to javafx.fxml;

    exports model;
    exports model.gamestates;
    exports model.unitfeatures;
    exports view.shape;
    exports view.menus.gamepopupmenus;
    exports model.weapons.weaponTypes;
    exports model.map;

    opens model.map;
    opens view.menus.gamepopupmenus;
    opens model.weapons.weaponTypes;
    exports model.network;

}