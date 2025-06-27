package com.tuempresa.gdp.view;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.tuempresa.gdp.SceneManager;
import com.tuempresa.gdp.model.Usuario;
import com.tuempresa.gdp.model.UsuarioNivelDecorado;
import com.tuempresa.gdp.model.NivelJuego;

public class PerfilView {
    private VBox root;
    private PerfilController controller;

    public PerfilView() {
        this.controller = new PerfilController(this);
        root = new VBox(10);
        Usuario u = controller.getUsuarioActual();
        Button btnBack = new Button("Atrás");
        btnBack.setOnAction(e -> SceneManager.showDashboard());
        Label lblNombre = new Label("Nombre: " + (u != null ? u.getNombre() : ""));
        Label lblEmail = new Label("Email: " + (u != null ? u.getEmail() : ""));
        Label lblNivel = new Label("Nivel: " + (u != null && u instanceof UsuarioNivelDecorado ? ((UsuarioNivelDecorado)u).getNivel() : ""));
        String deporteActual = controller.getDeporteFavoritoActual();
        ComboBox<com.tuempresa.gdp.model.DeporteFavorito> deporteBox = new ComboBox<>();
        deporteBox.getItems().addAll(com.tuempresa.gdp.model.DeporteFavorito.values());
        if (deporteActual != null) {
            try {
                deporteBox.setValue(com.tuempresa.gdp.model.DeporteFavorito.valueOf(deporteActual));
            } catch (Exception ignored) {}
        }
        Label lblDeporte = new Label("Deporte favorito: " + (deporteActual != null ? deporteActual : ""));
        ComboBox<NivelJuego> cbNivel = new ComboBox<>();
        cbNivel.getItems().add(null); // Permitir vacío
        cbNivel.getItems().addAll(NivelJuego.values());
        if (u instanceof UsuarioNivelDecorado) {
            cbNivel.setValue(((UsuarioNivelDecorado)u).getNivel());
        } else {
            cbNivel.setValue(null);
        }
        TextField tfNombre = new TextField(u != null ? u.getNombre() : "");
        Button btnEditarGuardar = new Button("Editar");
        btnEditarGuardar.setOnAction(e -> {
            if (btnEditarGuardar.getText().equals("Editar")) {
                int idxNombre = root.getChildren().indexOf(lblNombre);
                int idxNivel = root.getChildren().indexOf(lblNivel);
                int idxDeporte = root.getChildren().indexOf(lblDeporte);
                root.getChildren().set(idxNombre, tfNombre);
                root.getChildren().set(idxNivel, cbNivel);
                root.getChildren().set(idxDeporte, deporteBox);
                btnEditarGuardar.setText("Guardar");
            } else {
                controller.onActualizarUsuario(
                    tfNombre.getText(),
                    u.getEmail(),
                    null,
                    cbNivel.getValue(),
                    deporteBox.getValue() != null ? deporteBox.getValue().name() : null,
                    lblNombre, lblNivel, lblDeporte, tfNombre, cbNivel, deporteBox, btnEditarGuardar
                );
            }
        });
        root.getChildren().addAll(btnBack, lblNombre, lblEmail, lblNivel, lblDeporte, btnEditarGuardar);
    }

    public Parent getRoot() {
        return root;
    }

    public VBox getRootVBox() {
        return root;
    }

    public void showUpdateError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
}

class PerfilController {
    private final PerfilView view;
    public PerfilController(PerfilView view) {
        this.view = view;
    }
    public Usuario getUsuarioActual() {
        return com.tuempresa.gdp.controller.UsuarioController.getInstance().getUsuarioActual();
    }
    public String getDeporteFavoritoActual() {
        return com.tuempresa.gdp.controller.UsuarioController.getInstance().getDeporteFavoritoActual();
    }
    public void onActualizarUsuario(String nombre, String email, String contrasena, NivelJuego nivel, String deporte,
                                    Label lblNombre, Label lblNivel, Label lblDeporte,
                                    TextField tfNombre, ComboBox<NivelJuego> cbNivel, ComboBox<com.tuempresa.gdp.model.DeporteFavorito> deporteBox, Button btnEditarGuardar) {
        if (nombre == null || nombre.isEmpty()) {
            view.showUpdateError("El nombre no puede estar vacío");
            return;
        }
        com.tuempresa.gdp.controller.UsuarioController.getInstance().actualizarUsuarioCompleto(nombre, email, contrasena, nivel, deporte);
        lblNombre.setText("Nombre: " + nombre);
        lblNivel.setText("Nivel: " + (cbNivel.getValue() != null ? cbNivel.getValue().toString() : ""));
        lblDeporte.setText("Deporte favorito: " + (deporteBox.getValue() != null ? deporteBox.getValue().name() : ""));
        VBox root = view.getRootVBox();
        int idxNombre = root.getChildren().indexOf(tfNombre);
        int idxNivel = root.getChildren().indexOf(cbNivel);
        int idxDeporte = root.getChildren().indexOf(deporteBox);
        root.getChildren().set(idxNombre, lblNombre);
        root.getChildren().set(idxNivel, lblNivel);
        root.getChildren().set(idxDeporte, lblDeporte);
        btnEditarGuardar.setText("Editar");
    }
}
