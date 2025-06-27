package com.tuempresa.gdp.model.strategy;

import com.tuempresa.gdp.model.Partido;
import com.tuempresa.gdp.model.Usuario;
import java.util.List;

public class EmparejamientoLibre implements EstrategiaEmparejamiento {
    @Override
    public List<Partido> emparejar(Usuario usuario, List<Partido> partidos) {
        return partidos;
    }

    @Override
    public String toString() {
        return "Libre";
    }
}
