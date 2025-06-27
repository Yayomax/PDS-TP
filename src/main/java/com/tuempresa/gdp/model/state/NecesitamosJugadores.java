package com.tuempresa.gdp.model.state;

import com.tuempresa.gdp.model.Partido;
import com.tuempresa.gdp.model.Usuario;

public class NecesitamosJugadores implements EstadoPartido {
    @Override
    public void agregarJugador(Partido partido, Usuario usuario) {
        partido.getJugadores().add(usuario);
        if (partido.getJugadores().size() >= partido.getCantidadJugadores()) {
            partido.setEstado(new Armado());
        }
    }
    @Override
    public void confirmarJugador(Partido partido, Usuario usuario) {
        // No hace nada en este estado
    }
    @Override
    public String toString() {
        return "necesitamos jugadores";
    }
}
