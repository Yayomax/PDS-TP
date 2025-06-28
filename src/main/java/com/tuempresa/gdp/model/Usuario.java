package com.tuempresa.gdp.model;

import com.tuempresa.gdp.model.observer.NotificacionObserver;
import java.util.List;
import java.util.ArrayList;

public abstract class Usuario implements NotificacionObserver {
    private List<String> notificaciones = new ArrayList<>();
    public List<String> getNotificaciones() { return notificaciones; }
    @Override
    public void notificar(String mensaje) {
        notificaciones.add(mensaje);
    }
    public abstract String getNombre();
    public abstract String getEmail();
    public abstract String getContraseña();
    public abstract void setNombre(String nombre);
    public abstract void setEmail(String email);
    public abstract void setContraseña(String contraseña);
}
