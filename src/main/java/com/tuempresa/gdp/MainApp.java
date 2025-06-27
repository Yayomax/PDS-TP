package com.tuempresa.gdp;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        SceneManager.init(primaryStage);
        SceneManager.showLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
