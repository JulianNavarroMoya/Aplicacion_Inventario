module org.example.aplicacion_inventario {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;
    requires static lombok;
    requires java.sql;
    requires mysql.connector.j;


    opens org.example.aplicacion_inventario to javafx.fxml;
    exports org.example.aplicacion_inventario;
}