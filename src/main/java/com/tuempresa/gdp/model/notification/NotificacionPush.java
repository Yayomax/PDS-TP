package com.tuempresa.gdp.model.notification;

public class NotificacionPush implements IEstrategiaNotificacion {
    @Override
    public void notificar(String mensaje, String destino) {
        System.out.println("Push a " + destino + ": " + mensaje);
    }
}
