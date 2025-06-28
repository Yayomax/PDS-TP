package com.tuempresa.gdp;

import javafx.stage.Stage;
import javafx.scene.Scene;
import com.tuempresa.gdp.view.*;
import com.tuempresa.gdp.model.Partido;

public class SceneManager {
    private static Stage primaryStage;
    public static void init(Stage stage) {
        primaryStage = stage;
    }
    public static void showLogin() {
        LoginView view = new LoginView();
        Scene scene = new Scene(view.getRoot());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }
    public static void showRegistro() {
        RegistroView view = new RegistroView();
        Scene scene = new Scene(view.getRoot());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Registro");
    }
    public static void showDashboard() {
        DashboardView view = new DashboardView();
        Scene scene = new Scene(view.getRoot());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
    }
    public static void showBuscarPartido() {
        BuscarPartidoView view = new BuscarPartidoView();
        Scene scene = new Scene(view.getRoot());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Buscar Partido");
    }
    public static void showCrearPartido() {
        CrearPartidoView view = new CrearPartidoView();
        Scene scene = new Scene(view.getRoot());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Crear Partido");
    }
    public static void showDetallePartido(Partido partido) {
        DetallePartidoView view = new DetallePartidoView(partido);
        Scene scene = new Scene(view.getRoot());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Detalle Partido");
    }
    public static void showMisPartidos() {
        MisPartidosView view = new MisPartidosView();
        Scene scene = new Scene(view.getRoot());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mis Partidos");
    }
    public static void showNotificaciones() {
        NotificacionesView view = new NotificacionesView();
        Scene scene = new Scene(view.getRoot());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Notificaciones");
    }
    public static void showPerfil() {
        PerfilView view = new PerfilView();
        Scene scene = new Scene(view.getRoot());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Perfil");
    }
    public static void showMisNotificaciones() {
        MisNotificacionesView view = new MisNotificacionesView();
        Scene scene = new Scene(view.getRoot());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mis Notificaciones");
    }
}
