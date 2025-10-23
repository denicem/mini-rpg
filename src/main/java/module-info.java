module com.minirpg.game {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.minirpg.game to javafx.fxml;
    exports com.minirpg.game;
}