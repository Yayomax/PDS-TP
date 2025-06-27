package com.tuempresa.gdp.model.state;

import com.tuempresa.gdp.model.Partido;
import com.tuempresa.gdp.model.Usuario;

public class Finalizado implements EstadoPartido {
    @Override
    public void agregarJugador(Partido partido, Usuario usuario) {
        // Partido finalizado, no se pueden agregar jugadores
    }
    @Override
    public void confirmarJugador(Partido partido, Usuario usuario) {
        // Partido finalizado, no se pueden confirmar jugadores
    }
    @Override
    public String toString() {
        return "finalizado";
    }
}
