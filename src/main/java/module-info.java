module com.minirpg.game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;


    exports com.minirpg.game.controller;
    opens com.minirpg.game.controller to javafx.fxml;
    exports com.minirpg.game.main;
    opens com.minirpg.game.main to javafx.fxml;
    exports com.minirpg.game.model;
    opens com.minirpg.game.model to javafx.fxml;
}