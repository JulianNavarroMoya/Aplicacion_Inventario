package org.example.aplicacion_inventario;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.aplicacion_inventario.data.DataBaseInitializer;
import org.example.aplicacion_inventario.data.DataSourceFactory;
import org.example.aplicacion_inventario.sceneManager.SceneManager;

public class AppG1 extends Application {

    @Override
    public void start(Stage stage) {
        DataBaseInitializer.initialize(DataSourceFactory.getDataSource());
        DataBaseInitializer.loadData(DataSourceFactory.getDataSource());

        SceneManager.init(stage);
        stage.setTitle("Inventario App G1");
        SceneManager.goTo("login");
        stage.show();
    }
}
