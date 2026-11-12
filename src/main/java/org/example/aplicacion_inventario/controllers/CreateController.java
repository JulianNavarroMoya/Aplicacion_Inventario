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
    @FXML private Spinner<Integer> spinnerId;

    private final EquipoJdbcDAO dao = new EquipoJdbcDAO(DataSourceFactory.getDataSource());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spinnerId.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0, 1));

        choiceCategory.getItems().addAll("Keyboard", "Laptop", "Gamepad", "Monitor", "Computer", "Printer");
        choiceCategory.getSelectionModel().selectFirst();
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        if (txtName.getText().isBlank()) {
            new Alert(Alert.AlertType.WARNING, "El nombre no puede estar vacío").showAndWait();
            return;
        }

        Equipo equipment = new Equipo();
        equipment.setNombre(txtName.getText());
        equipment.setCategoria(choiceCategory.getValue());
        equipment.setDisponible(chkAvailable.isSelected());

        dao.save(equipment);

        SceneManager.goTo("min");
    }

    @FXML
    public void goBack(ActionEvent actionEvent) {
        SceneManager.goTo("min");
    }
}