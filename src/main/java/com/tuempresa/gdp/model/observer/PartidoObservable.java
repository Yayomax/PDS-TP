package com.tuempresa.gdp.model.observer;

public interface PartidoObservable {
    void agregarObservador(NotificacionObserver observer);
    void quitarObservador(NotificacionObserver observer);
    void notificarObservadores(String mensaje);
}
