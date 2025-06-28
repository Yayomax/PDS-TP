package com.tuempresa.gdp.view;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.tuempresa.gdp.controller.NotificacionesUsuarioController;
import com.tuempresa.gdp.controller.UsuarioController;
import com.tuempresa.gdp.model.Usuario;

public class MisNotificacionesView {
    private VBox root;
    private ListView<String> listNotificaciones;
    private Button btnLimpiar;
    private Button btnVolver;
    public MisNotificacionesView() {
        root = new VBox(10);
        Usuario usuario = UsuarioController.getInstance().getUsuarioActual();
        listNotificaciones = new ListView<>();
        listNotificaciones.getItems().addAll(
            NotificacionesUsuarioController.getInstance().getNotificacionesUsuario(usuario)
        );
        btnLimpiar = new Button("Limpiar Notificaciones");
        btnLimpiar.setOnAction(e -> {
            NotificacionesUsuarioController.getInstance().limpiarNotificaciones(usuario);
            listNotificaciones.getItems().clear();
        });
        btnVolver = new Button("Volver al Dashboard");
        btnVolver.setOnAction(e -> com.tuempresa.gdp.SceneManager.showDashboard());
        root.getChildren().addAll(new Label("Mis Notificaciones"), listNotificaciones, btnLimpiar, btnVolver);
    }
    public Parent getRoot() { return root; }
}
