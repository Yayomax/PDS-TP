package com.tuempresa.gdp.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.tuempresa.gdp.SceneManager;
import com.tuempresa.gdp.controller.PartidoController;
import com.tuempresa.gdp.controller.UsuarioController;
import com.tuempresa.gdp.model.Partido;
import com.tuempresa.gdp.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MisPartidosView {
    private VBox root;

    public MisPartidosView() {
        root = new VBox(10);
        Usuario actual = UsuarioController.getInstance().getUsuarioActual();
        ObservableList<Partido> creados = FXCollections.observableArrayList();
        ObservableList<Partido> participados = FXCollections.observableArrayList();
        for (Partido p : PartidoController.getInstance().getMisPartidos(actual)) {
            if (PartidoController.getInstance().isOcultoParaUsuario(p, actual)) continue;
            if (p.getCreador().equals(actual)) creados.add(p);
            else participados.add(p);
        }
        ListView<Partido> listCreados = new ListView<>(creados);
        ListView<Partido> listParticipados = new ListView<>(participados);
        listParticipados.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Partido item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    item.actualizarEstadoSiCorresponde();
                    String txt = item.getDeporte() + " en " + item.getUbicacion();
                    if (item.getEstado() instanceof com.tuempresa.gdp.model.state.Cancelado || item.getEstado() instanceof com.tuempresa.gdp.model.state.Finalizado) {
                        txt += " (" + item.getEstado().toString() + ")";
                    }
                    setText(txt);
                }
            }
        });
        listCreados.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Partido item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    item.actualizarEstadoSiCorresponde();
                    String txt = item.getDeporte() + " en " + item.getUbicacion();
                    if (item.getEstado() instanceof com.tuempresa.gdp.model.state.Cancelado || item.getEstado() instanceof com.tuempresa.gdp.model.state.Finalizado) {
                        txt += " (" + item.getEstado().toString() + ")";
                    }
                    setText(txt);
                }
            }
        });
        listCreados.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2 && listCreados.getSelectionModel().getSelectedItem() != null) {
                Partido seleccionado = listCreados.getSelectionModel().getSelectedItem();
                SceneManager.showDetallePartido(seleccionado);
            }
        });
        listParticipados.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2 && listParticipados.getSelectionModel().getSelectedItem() != null) {
                Partido seleccionado = listParticipados.getSelectionModel().getSelectedItem();
                SceneManager.showDetallePartido(seleccionado);
            }
        });
        TabPane tabs = new TabPane();
        Tab tabCreados = new Tab("Creados", listCreados);
        tabCreados.setClosable(false);
        Tab tabParticipados = new Tab("Participados", listParticipados);
        tabParticipados.setClosable(false);
        tabs.getTabs().addAll(tabCreados, tabParticipados);
        Button btnVolver = new Button("Volver al Dashboard");
        btnVolver.setOnAction(e -> SceneManager.showDashboard());
        root.getChildren().addAll(tabs, btnVolver);
    }

    public Parent getRoot() {
        return root;
    }

    public void mostrar(Stage stage) {
        TabPane tabs = new TabPane();
        Tab tabCreados = new Tab("Creados", new ListView<>());
        Tab tabParticipados = new Tab("Participados", new ListView<>());
        tabs.getTabs().addAll(tabCreados, tabParticipados);
        Scene scene = new Scene(tabs, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Mis Partidos");
        stage.show();
    }
}
