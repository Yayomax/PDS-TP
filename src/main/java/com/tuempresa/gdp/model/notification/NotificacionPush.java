package com.tuempresa.gdp.model.notification;

import com.tuempresa.gdp.model.adapter.IAdapterPush;

public class NotificacionPush implements IEstrategiaNotificacion {
    private IAdapterPush adapterPush;

    public NotificacionPush() {
        this.adapterPush = new com.tuempresa.gdp.model.adapter.AdapterJavaPush();
    }
    public NotificacionPush(IAdapterPush adapterPush) {
        this.adapterPush = adapterPush;
    }
    @Override
    public void notificar(String mensaje, String destino) {
        adapterPush.enviarPush(destino, mensaje);
    }
}
