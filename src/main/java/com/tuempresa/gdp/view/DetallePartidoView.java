package com.tuempresa.gdp.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.tuempresa.gdp.model.Partido;
import com.tuempresa.gdp.SceneManager;
import com.tuempresa.gdp.model.Usuario;
import com.tuempresa.gdp.controller.UsuarioController;
import com.tuempresa.gdp.controller.PartidoController;
import com.tuempresa.gdp.model.strategy.EstrategiaEmparejamiento;

public class DetallePartidoView {
    private VBox root;
    private Label lblCantidad;
    private Label lblEstado;
    private Label lblConfirmCount;
    private ListView<HBox> lvJugadores;
    private Button btnUnirse;
    private Button btnSalir;
    private Button btnBack;
    private Button btnConfirmarAsistencia;
    private ProgressBar progressBar;
    private Partido partido;

    public DetallePartidoView(Partido partido) {
        this.partido = partido;
        root = new VBox(10);
        Label lblInfo = new Label("Deporte: " + partido.getDeporte() + "\nUbicación: " + partido.getUbicacion() + "\nHorario: " + partido.getHorario() + "\nCreador: " + partido.getCreador().getNombre());
        lblCantidad = new Label();
        lblEstado = new Label();
        lblConfirmCount = new Label();
        lvJugadores = new ListView<>();
        btnUnirse = new Button("Unirse al Partido");
        btnSalir = new Button("Salir del Partido");
        btnBack = new Button("Volver al Dashboard");
        btnBack.setOnAction(e -> SceneManager.showDashboard());
        btnConfirmarAsistencia = new Button();
        progressBar = new ProgressBar(0);
        Label lblEstrategia = new Label("Estrategia: " + (partido.getEstrategiaEmparejamiento() != null ? partido.getEstrategiaEmparejamiento().toString() : "Libre"));
        // Acciones de los botones
        var usuarioActual = UsuarioController.getInstance().getUsuarioActual();
        btnUnirse.setOnAction(e -> {
            EstrategiaEmparejamiento estr = partido.getEstrategiaEmparejamiento();
            Usuario actual = UsuarioController.getInstance().getUsuarioActual();
            if (!estr.emparejar(actual, java.util.List.of(partido)).contains(partido)) {
                new Alert(Alert.AlertType.ERROR, "No cumplís los requisitos para unirte a este partido").showAndWait();
                return;
            }
            boolean ok = PartidoController.getInstance().unirseAPartido(partido, actual);
            if (ok) {
                new Alert(Alert.AlertType.INFORMATION, "Te has unido correctamente.").showAndWait();
                refreshDetails();
            } else {
                new Alert(Alert.AlertType.ERROR, "No se pudo unir al partido.").showAndWait();
            }
        });
        btnSalir.setOnAction(e -> {
            boolean removed = PartidoController.getInstance().salirDePartido(partido, usuarioActual);
            if (removed && partido.getEstado() instanceof com.tuempresa.gdp.model.state.Armado) {
                partido.setEstado(new com.tuempresa.gdp.model.state.NecesitamosJugadores());
                com.tuempresa.gdp.controller.NotificacionController.getInstance().notificar(
                    usuarioActual.getNombre() + " salió del partido y ahora necesita jugadores"
                );
            }
            if (removed) {
                new Alert(Alert.AlertType.INFORMATION, "Has salido del partido").showAndWait();
                refreshDetails();
            } else {
                new Alert(Alert.AlertType.ERROR, "No estabas en este partido").showAndWait();
            }
        });
        root.getChildren().addAll(new Label("Detalle Partido"), lblInfo, lblEstrategia, lblEstado, lblConfirmCount, progressBar, lblCantidad, lvJugadores, btnConfirmarAsistencia);
        refreshDetails();
    }

    private void refreshDetails() {
        Usuario usuarioActual = UsuarioController.getInstance().getUsuarioActual();
        int total = partido.getJugadores().size();
        int confirmados = partido.getConfirmaciones().size();
        lblEstado.setText("Estado: " + partido.getEstado().toString());
        // Mostrar progressBar y lblConfirmCount solo si el estado es Armado o Confirmado
        if (partido.getEstado() instanceof com.tuempresa.gdp.model.state.Armado
            || partido.getEstado() instanceof com.tuempresa.gdp.model.state.Confirmado) {
            lblConfirmCount.setText("(" + confirmados + "/" + total + " confirmados)");
            progressBar.setProgress(total > 0 ? confirmados / (double) total : 0);
            progressBar.setVisible(true);
            lblConfirmCount.setVisible(true);
        } else {
            progressBar.setVisible(false);
            lblConfirmCount.setVisible(false);
        }
        lblCantidad.setText("Cantidad de jugadores: " + total + "/" + partido.getCantidadJugadores());
        lvJugadores.getItems().clear();
        for (Usuario u : partido.getJugadores()) {
            Label lblNombre = new Label(u.getNombre());
            Label lblIcon = new Label(partido.getConfirmaciones().contains(u) ? "✅" : "⏳");
            HBox row = new HBox(10, lblNombre, lblIcon);
            lvJugadores.getItems().add(row);
        }
        // Botón único de confirmación
        if (partido.getEstado() instanceof com.tuempresa.gdp.model.state.Armado) {
            if (partido.getConfirmaciones().contains(usuarioActual)) {
                btnConfirmarAsistencia.setText("Asistencia confirmada");
                btnConfirmarAsistencia.setDisable(true);
            } else {
                btnConfirmarAsistencia.setText("Confirmar asistencia");
                btnConfirmarAsistencia.setDisable(false);
                btnConfirmarAsistencia.setOnAction(ev -> {
                    PartidoController.getInstance().confirmarAsistencia(partido, usuarioActual);
                    refreshDetails();
                });
            }
            btnConfirmarAsistencia.setVisible(true);
        } else {
            btnConfirmarAsistencia.setVisible(false);
        }
        // Botones de acción
        root.getChildren().remove(btnUnirse);
        root.getChildren().remove(btnSalir);
        root.getChildren().removeIf(n -> n instanceof Button && n != btnBack && n != btnUnirse && n != btnSalir && n != btnConfirmarAsistencia);
        boolean haConfirmado = partido.getConfirmaciones().contains(usuarioActual);
        btnSalir.setDisable(haConfirmado); // Bloquea salir si ya confirmó
        if (!usuarioActual.equals(partido.getCreador()) && partido.getJugadores().contains(usuarioActual)) {
            if (!root.getChildren().contains(btnSalir)) root.getChildren().add(btnSalir);
        } else if (!partido.getJugadores().contains(usuarioActual)) {
            if (!root.getChildren().contains(btnUnirse)) root.getChildren().add(btnUnirse);
        }
        if (partido.getEstado() instanceof com.tuempresa.gdp.model.state.Cancelado) {
            Button btnEliminar = new Button("Eliminar Partido");
            btnEliminar.setOnAction(e -> {
                PartidoController.getInstance().eliminarParaUsuario(partido, usuarioActual);
                SceneManager.showDashboard();
            });
            if (!root.getChildren().contains(btnEliminar)) root.getChildren().add(btnEliminar);
        } else if (usuarioActual.equals(partido.getCreador())) {
            Button btnCancelar = new Button("Cancelar Partido");
            btnCancelar.setOnAction(e -> {
                boolean ok = PartidoController.getInstance().cancelarPartido(partido, usuarioActual);
                if (ok) {
                    new Alert(Alert.AlertType.INFORMATION, "Partido cancelado").showAndWait();
                    SceneManager.showDashboard();
                } else {
                    new Alert(Alert.AlertType.ERROR, "No tenés permisos para cancelar").showAndWait();
                }
            });
            if (!root.getChildren().contains(btnCancelar)) root.getChildren().add(btnCancelar);
        }
        if (!root.getChildren().contains(btnBack)) root.getChildren().add(btnBack);
    }

    public Parent getRoot() {
        return root;
    }

    public void mostrar(Stage stage) {
        stage.setScene(new Scene(root, 400, 250));
        stage.setTitle("Detalle Partido");
        stage.show();
    }
}
