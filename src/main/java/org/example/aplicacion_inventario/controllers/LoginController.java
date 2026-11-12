package org.example.aplicacion_inventario.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.example.aplicacion_inventario.sceneManager.SceneManager;

public class LoginController {

    @FXML
    public void login(ActionEvent actionEvent) {
        SceneManager.goTo("min");
    }
}