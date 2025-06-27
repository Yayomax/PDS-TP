package com.tuempresa.gdp.model.strategy;

import java.util.List;
import com.tuempresa.gdp.model.Partido;
import com.tuempresa.gdp.model.Usuario;

public interface EstrategiaEmparejamiento {
    List<Partido> emparejar(Usuario usuario, List<Partido> partidos);
}
