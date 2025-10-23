module com.allorganized.minirpg {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.allorganized.minirpg to javafx.fxml;
    exports com.allorganized.minirpg;
}