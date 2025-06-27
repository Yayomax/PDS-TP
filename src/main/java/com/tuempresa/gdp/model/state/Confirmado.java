package com.tuempresa.gdp.model.state;

import com.tuempresa.gdp.model.Partido;
import com.tuempresa.gdp.model.Usuario;

public class Confirmado implements EstadoPartido {
    @Override
    public void agregarJugador(Partido partido, Usuario usuario) {
        // No se pueden agregar más jugadores
    }
    @Override
    public void confirmarJugador(Partido partido, Usuario usuario) {
        // Ya no cambia a EnJuego por confirmación, solo por tiempo
        // Se puede dejar vacío o mostrar mensaje si se desea
        // Si se quiere notificar, hacerlo aquí
    }
    @Override
    public String toString() {
        return "confirmado";
    }
}
