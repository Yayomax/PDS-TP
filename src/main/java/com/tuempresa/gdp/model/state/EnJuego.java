package com.tuempresa.gdp.model.state;

import com.tuempresa.gdp.model.Partido;
import com.tuempresa.gdp.model.Usuario;

public class EnJuego implements EstadoPartido {
    @Override
    public void agregarJugador(Partido partido, Usuario usuario) {
        // No se pueden agregar jugadores en juego
    }
    @Override
    public void confirmarJugador(Partido partido, Usuario usuario) {
        // No debe cambiar a Finalizado por confirmación, solo por tiempo
        // Si se quiere notificar, hacerlo aquí
    }
    @Override
    public String toString() {
        return "en juego";
    }
}
