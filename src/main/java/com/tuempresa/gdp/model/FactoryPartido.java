package com.tuempresa.gdp.model;

import java.time.LocalDateTime;
import com.tuempresa.gdp.model.state.EstadoPartido;
import com.tuempresa.gdp.model.strategy.EstrategiaEmparejamiento;

public class FactoryPartido {
    public static Partido crearPartido(String deporte, String ubicacion, LocalDateTime horario, int duracion, int cantidadJugadores, EstadoPartido estado, EstrategiaEmparejamiento estrategiaEmparejamiento, Usuario creador) {
        Partido partido = new Partido(deporte, ubicacion, horario, duracion, cantidadJugadores, estado, estrategiaEmparejamiento, creador);
        // Auto-unión del creador
        if (creador != null && !partido.getJugadores().contains(creador)) {
            partido.getJugadores().add(creador);
        }
        return partido;
    }
}
