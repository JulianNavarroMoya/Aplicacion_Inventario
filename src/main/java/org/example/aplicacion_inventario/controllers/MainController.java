package org.example.aplicacion_inventario.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.aplicacion_inventario.data.DataSourceFactory;
import org.example.aplicacion_inventario.data.EquipoJdbcDAO;
import org.example.aplicacion_inventario.models.Equipo;
import org.example.aplicacion_inventario.sceneManager.SceneManager;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableView<Equipo> table;
    @FXML private TableColumn<Equipo, String> cId;
    @FXML private TableColumn<Equipo, String> cName;
    @FXML private TableColumn<Equipo, String> cCategory;
    @FXML private TableColumn<Equipo, String> cAvailability;

    private EquipoJdbcDAO dao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cId.setCellValueFactory(row -> new SimpleStringProperty(String.valueOf(row.getValue().getId())));
        cName.setCellValueFactory(row -> new SimpleStringProperty(
                row.getValue().getNombre() == null ? "" : row.getValue().getNombre()));
        cCategory.setCellValueFactory(row -> new SimpleStringProperty(
                row.getValue().getCategoria() == null ? "" : row.getValue().getCategoria().toUpperCase()));
        cAvailability.setCellValueFactory(row -> new SimpleStringProperty(
                Boolean.TRUE.equals(row.getValue().getDisponible()) ? "disponible" : "no disponible"));

        table.setPlaceholder(new Label("No hay equipos en el inventario"));

        try {
            table.setItems(FXCollections.observableArrayList(dao().findAll()));
        } catch (RuntimeException e) {
            // Se informa dentro de la propia tabla para no bloquear la vista con un diálogo.
            table.setPlaceholder(new Label("No se ha podido cargar el inventario: " + SceneManager.rootMessage(e)));
        }
    }

    @FXML
    public void newRecord(ActionEvent e) {
        SceneManager.goTo("create");
    }

    @FXML
    public void logout(ActionEvent e) {
        SceneManager.goTo("login");
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
}
