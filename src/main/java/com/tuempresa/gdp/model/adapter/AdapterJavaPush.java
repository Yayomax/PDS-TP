package com.tuempresa.gdp.model.adapter;

public class AdapterJavaPush implements IAdapterPush {
    @Override
    public void enviarPush(String destino, String mensaje) {
        System.out.println("Enviando push a: " + destino + " | Mensaje: " + mensaje);
    }
}
