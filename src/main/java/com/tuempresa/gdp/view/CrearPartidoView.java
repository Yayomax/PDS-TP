package com.tuempresa.gdp.view;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.tuempresa.gdp.SceneManager;
import com.tuempresa.gdp.controller.PartidoController;
import com.tuempresa.gdp.controller.UsuarioController;
import com.tuempresa.gdp.controller.NotificacionController;
import com.tuempresa.gdp.model.Usuario;
import com.tuempresa.gdp.model.NivelJuego;
import com.tuempresa.gdp.model.UsuarioNivelDecorado;
import com.tuempresa.gdp.model.strategy.*;
import java.time.LocalDateTime;

public class CrearPartidoView {
    private VBox root;

    public CrearPartidoView() {
        root = new VBox(10);
        // Campos principales
        TextField deporteField = new TextField();
        deporteField.setPromptText("Deporte");
        TextField ubicacionField = new TextField();
        ubicacionField.setPromptText("Ubicación");
        DatePicker fechaPicker = new DatePicker();
        TextField horaField = new TextField();
        horaField.setPromptText("Hora (HH:mm)");
        TextField duracionField = new TextField();
        duracionField.setPromptText("Duración (min)");
        TextField cantidadField = new TextField();
        cantidadField.setPromptText("Cantidad de Jugadores");
        // ComboBox de estrategia
        ComboBox<String> cmbEstrategia = new ComboBox<>();
        cmbEstrategia.getItems().addAll("Libre", "Por Deporte", "Por Deporte y Nivel");
        // ComboBox de nivel
        ComboBox<NivelJuego> nivelBox = new ComboBox<>();
        nivelBox.getItems().addAll(NivelJuego.values());
        nivelBox.setPromptText("Nivel de Juego");
        // Lógica para eliminar opciones según datos del usuario actual
        String deporteActual = UsuarioController.getInstance().getDeporteFavoritoActual();
        Usuario actual = UsuarioController.getInstance().getUsuarioActual();
        NivelJuego nivelActual = null;
        if (actual instanceof UsuarioNivelDecorado) {
            nivelActual = ((UsuarioNivelDecorado)actual).getNivel();
        }
        if (deporteActual == null) {
            cmbEstrategia.getItems().remove("Por Deporte");
            cmbEstrategia.getItems().remove("Por Deporte y Nivel");
        } else if (nivelActual == null) {
            cmbEstrategia.getItems().remove("Por Deporte y Nivel");
        }
        // Estado inicial
        cmbEstrategia.setValue("Libre");
        deporteField.setVisible(true);
        deporteField.setEditable(true);
        deporteField.setDisable(false);
        deporteField.clear();
        nivelBox.setVisible(false);
        // Handler dinámico para mostrar/ocultar campos según estrategia
        cmbEstrategia.setOnAction(e -> {
            String estrategia = cmbEstrategia.getValue();
            Usuario usuarioActual = UsuarioController.getInstance().getUsuarioActual();
            String deporteFavorito = UsuarioController.getInstance().getDeporteFavoritoActual();
            NivelJuego nivelUsuario = (usuarioActual instanceof UsuarioNivelDecorado) ? ((UsuarioNivelDecorado) usuarioActual).getNivel() : null;
            if ("Libre".equals(estrategia)) {
                deporteField.setVisible(true);
                deporteField.setEditable(true);
                deporteField.setDisable(false);
                deporteField.clear();
                nivelBox.setVisible(false);
            } else if ("Por Deporte".equals(estrategia)) {
                deporteField.setVisible(true);
                if (deporteFavorito != null) {
                    deporteField.setText(deporteFavorito);
                } else {
                    deporteField.clear();
                }
                deporteField.setEditable(false);
                deporteField.setDisable(true);
                nivelBox.setVisible(false);
            } else if ("Por Deporte y Nivel".equals(estrategia)) {
                deporteField.setVisible(true);
                if (deporteFavorito != null) {
                    deporteField.setText(deporteFavorito);
                } else {
                    deporteField.clear();
                }
                deporteField.setEditable(false);
                deporteField.setDisable(true);
                nivelBox.setVisible(true);
                if (nivelUsuario != null) {
                    nivelBox.setValue(nivelUsuario);
                } else {
                    nivelBox.setValue(null);
                }
                nivelBox.setDisable(true);
            }
        });
        // Botón Crear Partido
        Button btnCrear = new Button("Crear Partido");
        btnCrear.setOnAction(e -> {
            try {
                String deporte = deporteField.isVisible() ? deporteField.getText() : "";
                String ubicacion = ubicacionField.getText();
                LocalDateTime horario = LocalDateTime.parse(fechaPicker.getValue() + "T" + horaField.getText());
                int duracion = Integer.parseInt(duracionField.getText());
                int cantidad = Integer.parseInt(cantidadField.getText());
                String estrategia = cmbEstrategia.getValue();
                EstrategiaEmparejamiento estr;
                switch (estrategia) {
                    case "Por Deporte":
                        estr = new EmparejamientoDeporte(deporte); // Guarda el deporte del partido
                        break;
                    case "Por Deporte y Nivel":
                        estr = new EmparejamientoDeporteNivel(deporte, nivelBox.getValue()); // Guarda deporte y nivel
                        break;
                    default:
                        estr = new EmparejamientoLibre();
                }
                PartidoController.getInstance().crearPartido(deporte, ubicacion, horario, duracion, cantidad, UsuarioController.getInstance().getUsuarioActual(), estr);
                NotificacionController.getInstance().notificar("Nuevo partido creado: " + deporte + " en " + ubicacion);
                SceneManager.showDashboard();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Datos inválidos").showAndWait();
            }
        });
        Button btnAtras = new Button("Atrás");
        btnAtras.setOnAction(e -> SceneManager.showDashboard());
        // Layout: insertar controles en orden lógico
        root.getChildren().addAll(
            new Label("Crear Partido"),
            cmbEstrategia,
            deporteField,
            nivelBox,
            ubicacionField,
            fechaPicker,
            horaField,
            duracionField,
            cantidadField,
            btnCrear,
            btnAtras
        );
    }

    public Parent getRoot() {
        return root;
    }
}
