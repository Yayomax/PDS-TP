package com.tuempresa.gdp.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.tuempresa.gdp.controller.UsuarioController;
import com.tuempresa.gdp.SceneManager;

public class LoginView {
    private VBox root;
    private LoginController controller;

    public LoginView() {
        this.controller = new LoginController(this);
        root = new VBox(10);
        root.setPadding(new Insets(20));
        TextField usuarioField = new TextField();
        usuarioField.setPromptText("Usuario o Email");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Contraseña");
        Button btnEntrar = new Button("Entrar");
        btnEntrar.setOnAction(e -> controller.onLogin(usuarioField.getText(), passField.getText()));
        Label lblNoCuenta = new Label("¿No tenés cuenta?");
        Hyperlink linkRegistro = new Hyperlink("Regístrate aquí");
        HBox registroBox = new HBox(5, lblNoCuenta, linkRegistro);
        registroBox.setPadding(new Insets(10, 0, 0, 0));
        registroBox.setStyle("-fx-alignment: center-right;");
        linkRegistro.setOnAction(e -> SceneManager.showRegistro());
        root.getChildren().addAll(new Label("Login"), usuarioField, passField, btnEntrar, registroBox);
    }

    public Parent getRoot() {
        return root;
    }

    public void showLoginError() {
        new Alert(Alert.AlertType.ERROR, "Usuario o contraseña incorrectos").showAndWait();
    }

    public void goToDashboard() {
        SceneManager.showDashboard();
    }
}

class LoginController {
    private final LoginView view;
    public LoginController(LoginView view) {
        this.view = view;
    }
    public void onLogin(String email, String password) {
        if (UsuarioController.getInstance().login(email, password)) {
            view.goToDashboard();
        } else {
            view.showLoginError();
        }
    }
}
