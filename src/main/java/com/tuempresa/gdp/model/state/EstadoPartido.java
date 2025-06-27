package com.tuempresa.gdp.model.state;

import com.tuempresa.gdp.model.Partido;
import com.tuempresa.gdp.model.Usuario;

public interface EstadoPartido {
    void agregarJugador(Partido partido, Usuario usuario);
    void confirmarJugador(Partido partido, Usuario usuario);
}
