package org.example.aplicacion_inventario.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.aplicacion_inventario.data.DataSourceFactory;
import org.example.aplicacion_inventario.data.EquipoJdbcDAO;
import org.example.aplicacion_inventario.models.Equipo;
import org.example.aplicacion_inventario.sceneManager.SceneManager;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateController implements Initializable {

    @FXML private TextField txtName;
    @FXML private ChoiceBox<String> choiceCategory;
    @FXML private CheckBox chkAvailable;

    private EquipoJdbcDAO dao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Las categorías se definen en el FXML; aquí solo se deja una seleccionada.
        if (choiceCategory.getSelectionModel().isEmpty()) {
            choiceCategory.getSelectionModel().selectFirst();
        }
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        String nombre = txtName.getText() == null ? "" : txtName.getText().trim();
        if (nombre.isBlank()) {
            showError(Alert.AlertType.WARNING, "Datos incompletos", "El nombre no puede estar vacío.");
            return;
        }

        String categoria = choiceCategory.getValue();
        if (categoria == null) {
            showError(Alert.AlertType.WARNING, "Datos incompletos", "Debes seleccionar una categoría.");
            return;
        }

        try {
            Equipo equipment = new Equipo();
            equipment.setNombre(nombre);
            equipment.setCategoria(categoria);
            equipment.setDisponible(chkAvailable.isSelected());

            dao().save(equipment);

            SceneManager.goTo("min");
        } catch (RuntimeException e) {
            showError(Alert.AlertType.ERROR, "No se ha podido guardar el equipo",
                    SceneManager.rootMessage(e));
        }
    }

    @FXML
    public void goBack(ActionEvent actionEvent) {
        SceneManager.goTo("min");
    }

    /**
     * Devuelve el DAO creándolo de forma perezosa, para que un fallo de conexión
     * se pueda mostrar como un mensaje y no rompa la carga de la vista.
     *
     * @return el DAO de equipos
     */
    private EquipoJdbcDAO dao() {
        if (dao == null) {
            dao = new EquipoJdbcDAO(DataSourceFactory.getDataSource());
        }
        return dao;
    }

    /**
     * Muestra un diálogo de aviso o error.
     *
     * @param type    tipo de diálogo
     * @param header  texto de cabecera
     * @param content texto del mensaje
     */
    private void showError(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle("Inventario");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
