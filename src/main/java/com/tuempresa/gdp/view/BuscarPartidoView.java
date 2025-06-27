package com.tuempresa.gdp.view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.tuempresa.gdp.model.Partido;

public class BuscarPartidoView {
    private VBox root;

    public BuscarPartidoView() {
        root = new VBox(10);
        TextField deporteField = new TextField();
        deporteField.setPromptText("Deporte");
        TextField ubicacionField = new TextField();
        ubicacionField.setPromptText("Ubicación");
        Button btnBuscar = new Button("Buscar");
        ListView<Partido> resultados = new ListView<>();
        Runnable recargar = () -> {
            java.util.List<com.tuempresa.gdp.model.Partido> partidos = com.tuempresa.gdp.controller.PartidoController.getInstance().getAll();
            java.util.List<com.tuempresa.gdp.model.Partido> filtrados = partidos.stream()
                .filter(p -> (deporteField.getText().isEmpty() || p.getDeporte().toLowerCase().contains(deporteField.getText().toLowerCase())))
                .filter(p -> (ubicacionField.getText().isEmpty() || p.getUbicacion().toLowerCase().contains(ubicacionField.getText().toLowerCase())))
                .toList();
            resultados.setItems(javafx.collections.FXCollections.observableArrayList(filtrados));
        };
        deporteField.setOnKeyReleased(e -> recargar.run());
        ubicacionField.setOnKeyReleased(e -> recargar.run());
        btnBuscar.setOnAction(e -> recargar.run());
        resultados.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Partido item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getDeporte() + " en " + item.getUbicacion());
            }
        });
        resultados.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && resultados.getSelectionModel().getSelectedItem() != null) {
                com.tuempresa.gdp.SceneManager.showDetallePartido(resultados.getSelectionModel().getSelectedItem());
            }
        });
        Button btnVolver = new Button("Volver al Dashboard");
        btnVolver.setOnAction(e -> com.tuempresa.gdp.SceneManager.showDashboard());
        root.getChildren().addAll(new Label("Buscar Partido"), deporteField, ubicacionField, btnBuscar, resultados, btnVolver);
        recargar.run();
    }

    public Parent getRoot() {
        return root;
    }

    public void mostrar(Stage stage) {
        stage.setScene(new Scene(root, 300, 200));
        stage.setTitle("Buscar Partido");
        stage.show();
    }
}
