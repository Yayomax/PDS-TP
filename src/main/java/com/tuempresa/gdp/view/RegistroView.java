package com.tuempresa.gdp.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.tuempresa.gdp.SceneManager;

public class RegistroView {
    private VBox root;
    private RegistroController controller;
    public RegistroView() {
        this.controller = new RegistroController(this);
        root = new VBox(10);
        root.setPadding(new Insets(20));
        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Contraseña");
        ComboBox<String> nivelBox = new ComboBox<>();
        nivelBox.getItems().addAll("Principiante", "Intermedio", "Avanzado");
        nivelBox.setPromptText("Nivel de Juego");
        ComboBox<com.tuempresa.gdp.model.DeporteFavorito> deporteBox = new ComboBox<>();
        deporteBox.getItems().addAll(com.tuempresa.gdp.model.DeporteFavorito.values());
        deporteBox.setPromptText("Deporte Favorito");
        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.setOnAction(e -> controller.onRegistrar(
            nombreField.getText(),
            emailField.getText(),
            passField.getText(),
            deporteBox.getValue() != null ? deporteBox.getValue().name() : null,
            nivelBox.getValue()
        ));
        Button btnAtras = new Button("Atrás");
        btnAtras.setOnAction(e -> SceneManager.showLogin());
        root.getChildren().addAll(new Label("Registro"), nombreField, emailField, passField, nivelBox, deporteBox, btnRegistrar, btnAtras);
    }
    public Parent getRoot() {
        return root;
    }
    public void showRegistroError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
    public void goToLogin() {
        SceneManager.showLogin();
    }
}

class RegistroController {
    private final RegistroView view;
    public RegistroController(RegistroView view) {
        this.view = view;
    }
    public void onRegistrar(String nombre, String email, String pass, String deporte, String nivel) {
        if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            view.showRegistroError("Todos los campos son obligatorios");
            return;
        }
        com.tuempresa.gdp.controller.UsuarioController.getInstance().registrar(nombre, email, pass, deporte, nivel);
        com.tuempresa.gdp.controller.NotificacionController.getInstance().notificar("Nuevo usuario registrado: " + email);
        view.goToLogin();
    }
}
