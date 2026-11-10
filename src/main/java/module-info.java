module org.example.aplicacion_inventario {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens org.example.aplicacion_inventario to javafx.fxml;
    exports org.example.aplicacion_inventario;
}