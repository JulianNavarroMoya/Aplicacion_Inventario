package org.example.aplicacion_inventario.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.aplicacion_inventario.data.DataSourceFactory;
import org.example.aplicacion_inventario.data.EquipoJdbcDAO;
import org.example.aplicacion_inventario.models.Equipo;
import org.example.aplicacion_inventario.sceneManager.SceneManager;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableView<Equipo> table;
    @FXML private TableColumn<Equipo, String> cId;
    @FXML private TableColumn<Equipo, String> cName;
    @FXML private TableColumn<Equipo, String> cCategory;
    @FXML private TableColumn<Equipo, String> cAvailability;

    private final EquipoJdbcDAO dao = new EquipoJdbcDAO(DataSourceFactory.getDataSource());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cId.setCellValueFactory(row -> new SimpleStringProperty(String.valueOf(row.getValue().getId())));
        cName.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getNombre()));
        cCategory.setCellValueFactory(row -> new SimpleStringProperty(String.valueOf(row.getValue().getCategoria()).toUpperCase()));
        cAvailability.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getDisponible() ? "disponible" : "no disponible"));

        table.setItems(FXCollections.observableArrayList(dao.findAll()));
    }

    @FXML
    public void newRecord(ActionEvent e) {
        SceneManager.goTo("create");
    }

    @FXML
    public void logout(ActionEvent e) {
        SceneManager.goTo("login");
    }
}