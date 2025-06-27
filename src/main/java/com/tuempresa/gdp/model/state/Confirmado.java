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
        partido.setEstado(new EnJuego());
    }
    @Override
    public String toString() {
        return "confirmado";
    }
}
