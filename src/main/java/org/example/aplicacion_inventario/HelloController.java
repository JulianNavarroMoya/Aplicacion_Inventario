package org.example.aplicacion_inventario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.ui.helper.MessageHelper;

import java.text.BreakIterator;

public class HelloController {
    public ChoiceBox<?> cbCategoria;
    public CheckBox chkDisponible;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCategoria;
    @FXML
    private TextField txtDisponible;


    @FXML
    protected void onHelloButtonClick() {
    }

    @FXML
    protected void onActionIdDisponible(ActionEvent event) {
        MessageHelper.debug(event, "TODO: onActionIdDisponible");
    }

    @FXML
    protected void Guardar(ActionEvent event) {
        MessageHelper.debug(event, "TODO: Guardar");
    }

    @FXML
    protected void Volver(ActionEvent event) {
        MessageHelper.debug(event, "TODO: Volver");
    }
}
