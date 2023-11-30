module com.example.pepe {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pepe to javafx.fxml;
    exports com.example.pepe;
}