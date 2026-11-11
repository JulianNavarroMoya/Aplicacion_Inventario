package org.example.aplicacion_inventario.sceneManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static Stage stage;

    public static void init(Stage s) {
        stage = s;
    }

    public static void goTo(String view) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/cesur/dam/inventarioappg1/views/" + view + "-view.fxml"));
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar la vista: " + view, e);
        }
    }
}
