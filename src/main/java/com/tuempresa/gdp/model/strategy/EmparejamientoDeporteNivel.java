package com.tuempresa.gdp.model.strategy;

import com.tuempresa.gdp.model.Partido;
import com.tuempresa.gdp.model.Usuario;
import java.util.List;

public class EmparejamientoDeporteNivel implements EstrategiaEmparejamiento {
    private final String deporte;
    private final com.tuempresa.gdp.model.NivelJuego nivel;

    public EmparejamientoDeporteNivel(String deporte, com.tuempresa.gdp.model.NivelJuego nivel) {
        this.deporte = deporte;
        this.nivel = nivel;
    }

    @Override
    public List<Partido> emparejar(Usuario usuario, List<Partido> partidos) {
        // Si el usuario tiene deporte favorito y nivel, solo puede unirse si ambos coinciden
        String miDeporte = null;
        com.tuempresa.gdp.model.NivelJuego miNivel = null;
        if (usuario != null) {
            com.tuempresa.gdp.model.Usuario u = usuario;
            while (u instanceof com.tuempresa.gdp.model.UsuarioDecorador) {
                if (u instanceof com.tuempresa.gdp.model.UsuarioDeporteFavoritoDecorado) {
                    miDeporte = ((com.tuempresa.gdp.model.UsuarioDeporteFavoritoDecorado) u).getDeporteFavorito().name();
                }
                if (u instanceof com.tuempresa.gdp.model.UsuarioNivelDecorado) {
                    miNivel = ((com.tuempresa.gdp.model.UsuarioNivelDecorado) u).getNivel();
                }
                u = ((com.tuempresa.gdp.model.UsuarioDecorador) u).getUsuario();
            }
        }
        if ((miDeporte != null && !miDeporte.equalsIgnoreCase(deporte)) || (miNivel != null && miNivel != nivel)) {
            return java.util.Collections.emptyList();
        }
        return partidos.stream()
            .filter(p -> p.getDeporte().equalsIgnoreCase(deporte))
            .filter(p -> p.getJugadores().stream().anyMatch(u -> (u instanceof com.tuempresa.gdp.model.UsuarioNivelDecorado) && ((com.tuempresa.gdp.model.UsuarioNivelDecorado)u).getNivel() == nivel))
            .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public String toString() {
        return "Por Deporte y Nivel: " + deporte + " - " + nivel;
    }
}
