module com.example.pepe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.pepe to javafx.fxml;
    exports com.example.pepe;
    exports com.example.pepe.filters;
    opens com.example.pepe.filters to javafx.fxml;
}