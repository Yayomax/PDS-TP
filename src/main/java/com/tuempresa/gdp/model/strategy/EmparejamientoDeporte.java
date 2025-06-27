package com.tuempresa.gdp.model.strategy;

import com.tuempresa.gdp.model.Partido;
import com.tuempresa.gdp.model.Usuario;
import java.util.List;

public class EmparejamientoDeporte implements EstrategiaEmparejamiento {
    private final String deporte;

    public EmparejamientoDeporte(String deporte) {
        this.deporte = deporte;
    }

    @Override
    public List<Partido> emparejar(Usuario usuario, List<Partido> partidos) {
        // Si el usuario tiene deporte favorito, solo puede unirse si coincide
        String miDeporte = null;
        if (usuario != null) {
            com.tuempresa.gdp.model.Usuario u = usuario;
            while (u instanceof com.tuempresa.gdp.model.UsuarioDecorador) {
                if (u instanceof com.tuempresa.gdp.model.UsuarioDeporteFavoritoDecorado) {
                    miDeporte = ((com.tuempresa.gdp.model.UsuarioDeporteFavoritoDecorado) u).getDeporteFavorito().name();
                    break;
                }
                u = ((com.tuempresa.gdp.model.UsuarioDecorador) u).getUsuario();
            }
        }
        if (miDeporte != null && !miDeporte.equalsIgnoreCase(deporte)) {
            // No cumple el filtro, no puede unirse
            return java.util.Collections.emptyList();
        }
        return partidos.stream()
            .filter(p -> p.getDeporte().equalsIgnoreCase(deporte))
            .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public String toString() {
        return "Por Deporte: " + deporte;
    }
}
