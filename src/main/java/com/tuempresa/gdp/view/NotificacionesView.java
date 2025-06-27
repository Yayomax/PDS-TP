package com.tuempresa.gdp.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import com.tuempresa.gdp.SceneManager;
import com.tuempresa.gdp.controller.NotificacionController;
import javafx.collections.FXCollections;

public class NotificacionesView {
    private VBox root;

    public NotificacionesView() {
        root = new VBox(10);
        ListView<String> historial = new ListView<>(FXCollections.observableArrayList(NotificacionController.getInstance().getHistorial()));
        ComboBox<String> estrategiaBox = new ComboBox<>();
        estrategiaBox.getItems().addAll("Push", "Email");
        estrategiaBox.setPromptText("Estrategia de Notificación");
        root.getChildren().addAll(new Label("Notificaciones"), historial, estrategiaBox);
        Button btnVolver = new Button("Volver al Dashboard");
        btnVolver.setOnAction(e -> SceneManager.showDashboard());
        root.getChildren().add(btnVolver);
    }

    public Parent getRoot() {
        return root;
    }

    public void mostrar(Stage stage) {
        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("Notificaciones");
        stage.show();
    }
}
