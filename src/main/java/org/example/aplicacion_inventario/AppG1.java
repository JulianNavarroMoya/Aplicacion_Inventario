package org.example.aplicacion_inventario;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.aplicacion_inventario.data.DataBaseInitializer;
import org.example.aplicacion_inventario.data.DataSourceFactory;
import org.example.aplicacion_inventario.sceneManager.SceneManager;

public class AppG1 extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager.init(stage);
        stage.setTitle("Inventario App G1");
        stage.setMinWidth(640);
        stage.setMinHeight(480);

        // La base de datos se prepara antes de mostrar la interfaz, pero un fallo
        // de conexión ya no impide abrir la ventana: se informa al usuario.
        Exception startupError = null;
        try {
            DataBaseInitializer.initialize(DataSourceFactory.getDataSource());
            DataBaseInitializer.loadData(DataSourceFactory.getDataSource());
        } catch (Exception e) {
            startupError = e;
        }

        SceneManager.goTo("login");
        stage.show();

        if (startupError != null) {
            showStartupError(startupError);
        }
    }

    /**
     * Muestra un diálogo explicando por qué no se pudo preparar la base de datos.
     *
     * @param error excepción producida durante la inicialización
     */
    private void showStartupError(Exception error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de conexión");
        alert.setHeaderText("No se ha podido preparar la base de datos");
        alert.setContentText(SceneManager.rootMessage(error)
                + "\n\nRevisa db.url, db.user y db.password en application.properties.");
        alert.showAndWait();
    }
}
