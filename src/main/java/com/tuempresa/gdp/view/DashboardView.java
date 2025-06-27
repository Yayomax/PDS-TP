package com.tuempresa.gdp.view;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.tuempresa.gdp.SceneManager;
import com.tuempresa.gdp.controller.PartidoController;
import com.tuempresa.gdp.controller.UsuarioController;
import com.tuempresa.gdp.model.Partido;

public class DashboardView {
    private VBox root;

    public DashboardView() {
        root = new VBox(10);
        if (UsuarioController.getInstance().getUsuarioActual() == null) {
            SceneManager.showLogin();
            return;
        }
        Label lblUser = new Label("Usuario: " + UsuarioController.getInstance().getUsuarioActual().getNombre());
        Button btnLogout = new Button("Cerrar Sesión");
        btnLogout.setOnAction(e -> {
            UsuarioController.getInstance().logout();
            SceneManager.showLogin();
        });
        HBox topBar = new HBox(10);
        Button btnBuscar = new Button("Buscar Partidos");
        btnBuscar.setOnAction(e -> SceneManager.showBuscarPartido());
        Button btnCrear = new Button("Crear Partido");
        btnCrear.setOnAction(e -> SceneManager.showCrearPartido());
        Button btnMisPartidos = new Button("Ver Mis Partidos");
        btnMisPartidos.setOnAction(e -> SceneManager.showMisPartidos());
        Button btnPerfil = new Button("Ver Perfil");
        btnPerfil.setOnAction(e -> SceneManager.showPerfil());
        topBar.getChildren().addAll(btnBuscar, btnCrear, btnMisPartidos, btnPerfil);
        root.getChildren().add(topBar);

        // Mostrar todos los partidos disponibles (sin filtro de emparejamiento)
        ListView<Partido> listaPartidos = new ListView<>();
        Runnable recargar = () -> {
            var partidosFiltrados = PartidoController.getInstance().getAll().stream()
                .filter(p -> !(p.getEstado() instanceof com.tuempresa.gdp.model.state.Cancelado)
                        && !(p.getEstado() instanceof com.tuempresa.gdp.model.state.Finalizado)
                        && !(p.getEstado() instanceof com.tuempresa.gdp.model.state.Armado))
                .filter(p -> !PartidoController.getInstance().isOcultoParaUsuario(p, UsuarioController.getInstance().getUsuarioActual()))
                .toList();
            listaPartidos.setItems(javafx.collections.FXCollections.observableArrayList(partidosFiltrados));
        };
        listaPartidos.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Partido item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    item.actualizarEstadoSiCorresponde(); // Actualiza el estado antes de mostrar
                    setText(item.getDeporte() + " en " + item.getUbicacion());
                }
            }
        });
        listaPartidos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && listaPartidos.getSelectionModel().getSelectedItem() != null) {
                SceneManager.showDetallePartido(listaPartidos.getSelectionModel().getSelectedItem());
            }
        });
        root.getChildren().addAll(listaPartidos);
        recargar.run();
        root.getChildren().addAll(lblUser, btnLogout);
    }

    public Parent getRoot() {
        return root;
    }

    public void mostrar(Stage stage) {
        ListView<String> listaPartidos = new ListView<>();
        HBox filtros = new HBox(10, new Label("Filtros:"), new TextField("Deporte"), new TextField("Ubicación"));
        Button btnBuscar = new Button("Buscar");
        filtros.getChildren().add(btnBuscar);
        root.getChildren().addAll(filtros, listaPartidos);
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Dashboard");
        stage.show();
    }
}
