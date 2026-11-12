package org.example.aplicacion_inventario.sceneManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Gestiona la navegación entre las vistas de la aplicación y el tamaño de la
 * ventana, de forma que un error al cargar una vista no deje la interfaz muerta.
 */
public class SceneManager {

    /** Tamaño usado la primera vez que se muestra una vista. */
    private static final double DEFAULT_WIDTH = 900;
    private static final double DEFAULT_HEIGHT = 600;

    private static Stage stage;

    public static void init(Stage s) {
        stage = s;
    }

    /**
     * Carga la vista indicada y la muestra en la ventana principal.
     *
     * @param view nombre de la vista sin el sufijo {@code -view.fxml}
     */
    public static void goTo(String view) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneManager.class.getResource("/org/example/aplicacion_inventario/" + view + "-view.fxml"));
            Parent root = loader.load();

            // Conserva el tamaño actual de la ventana al cambiar de vista.
            Scene current = stage.getScene();
            Scene scene = current == null
                    ? new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT)
                    : new Scene(root, current.getWidth(), current.getHeight());
            stage.setScene(scene);
        } catch (Exception e) {
            showError("No se pudo cargar la vista \"" + view + "\"", e);
        }
    }

    /**
     * Devuelve el mensaje de la causa raíz de una excepción.
     *
     * @param error excepción original
     * @return el mensaje más específico disponible
     */
    public static String rootMessage(Throwable error) {
        Throwable cause = error;
        while (cause.getCause() != null && cause.getCause() != cause) {
            cause = cause.getCause();
        }
        String message = cause.getMessage();
        return (message == null || message.isBlank()) ? cause.getClass().getSimpleName() : message;
    }

    /**
     * Muestra un diálogo de error sin interrumpir la navegación.
     *
     * @param header texto de cabecera del diálogo
     * @param error  excepción que se quiere mostrar
     */
    private static void showError(String header, Throwable error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(rootMessage(error));
        alert.showAndWait();
    }
}
