package com.tuempresa.gdp.controller;

import com.tuempresa.gdp.model.notification.*;
import java.util.*;

public class NotificacionController {
    private static NotificacionController instance;
    private List<String> historial = new ArrayList<>();
    private Notificador notificador = new Notificador();

    private NotificacionController() {}

    public static NotificacionController getInstance() {
        if (instance == null) {
            instance = new NotificacionController();
        }
        return instance;
    }

    public void agregarEstrategia(IEstrategiaNotificacion estrategia) {
        notificador.agregarEstrategia(estrategia);
    }
    public void notificar(String mensaje, String destino) {
        notificador.notificar(mensaje, destino);
        historial.add(mensaje + " → " + destino);
    }
    public void notificar(String mensaje) {
        historial.add(mensaje);
    }
    public List<String> getHistorial() {
        return historial;
    }
}
