package org.example.aplicacion_inventario.controllers;

import javafx.fxml.FXML;
import org.example.aplicacion_inventario.sceneManager.SceneManager;

import java.awt.event.ActionEvent;

public class LoginController {

    @FXML
    public void login(ActionEvent actionEvent) {
        SceneManager.goTo("main");
    }
}