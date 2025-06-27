package com.tuempresa.gdp.controller;

import com.tuempresa.gdp.model.*;
import com.tuempresa.gdp.model.state.*;
import com.tuempresa.gdp.model.strategy.*;
import java.time.LocalDateTime;
import java.util.*;

public class PartidoController {
    private static PartidoController instance;
    private List<Partido> partidos = new ArrayList<>();
    private Map<Usuario, Set<Partido>> partidosOcultos = new HashMap<>();

    private PartidoController() {}

    public static PartidoController getInstance() {
        if (instance == null) {
            instance = new PartidoController();
        }
        return instance;
    }

    public Partido crearPartido(String deporte, String ubicacion, LocalDateTime horario, int duracion, int cantidadJugadores, Usuario creador) {
        // Usar EmparejamientoLibre por defecto
        Partido partido = FactoryPartido.crearPartido(deporte, ubicacion, horario, duracion, cantidadJugadores, new NecesitamosJugadores(), new EmparejamientoLibre(), creador);
        // Auto-unión del creador
        if (creador != null && !partido.getJugadores().contains(creador)) {
            partido.getJugadores().add(creador);
        }
        partidos.add(partido);
        return partido;
    }
    public Partido crearPartido(String deporte, String ubicacion, LocalDateTime horario, int duracion, int cantidadJugadores, Usuario creador, EstrategiaEmparejamiento estrategiaEmparejamiento) {
        // Si la estrategia es EmparejamientoDeporte, guardar el deporte del partido
        if (estrategiaEmparejamiento instanceof com.tuempresa.gdp.model.strategy.EmparejamientoDeporte) {
            estrategiaEmparejamiento = new com.tuempresa.gdp.model.strategy.EmparejamientoDeporte(deporte);
        } else if (estrategiaEmparejamiento instanceof com.tuempresa.gdp.model.strategy.EmparejamientoDeporteNivel) {
            NivelJuego nivel = null;
            if (creador instanceof com.tuempresa.gdp.model.UsuarioNivelDecorado) {
                nivel = ((com.tuempresa.gdp.model.UsuarioNivelDecorado)creador).getNivel();
            }
            estrategiaEmparejamiento = new com.tuempresa.gdp.model.strategy.EmparejamientoDeporteNivel(deporte, nivel);
        }
        Partido partido = FactoryPartido.crearPartido(deporte, ubicacion, horario, duracion, cantidadJugadores, new NecesitamosJugadores(), estrategiaEmparejamiento, creador);
        if (creador != null && !partido.getJugadores().contains(creador)) {
            partido.getJugadores().add(creador);
        }
        partidos.add(partido);
        return partido;
    }
    public List<Partido> buscarPartidos(String deporte, String ubicacion) {
        List<Partido> res = new ArrayList<>();
        for (Partido p : partidos) {
            if ((deporte == null || p.getDeporte().equalsIgnoreCase(deporte)) && (ubicacion == null || p.getUbicacion().equalsIgnoreCase(ubicacion))) {
                res.add(p);
            }
        }
        return res;
    }
    public List<Partido> getMisPartidos(Usuario u) {
        List<Partido> res = new ArrayList<>();
        for (Partido p : partidos) {
            if (p.getCreador().equals(u) || p.getJugadores().contains(u)) {
                res.add(p);
            }
        }
        return res;
    }
    public List<Partido> getAll() {
        return partidos;
    }
    public boolean unirseAPartido(Partido partido, Usuario usuario) {
        if (partido.getJugadores().contains(usuario)) {
            return false; // ya está dentro
        }
        if (partido.getJugadores().size() >= partido.getCantidadJugadores()) {
            return false; // cupo lleno
        }
        partido.getJugadores().add(usuario);
        NotificacionController.getInstance().notificar(usuario.getNombre() + " se unió al partido de " + partido.getDeporte());
        // Cambio automático a Armado
        if (partido.getJugadores().size() >= partido.getCantidadJugadores()
            && !(partido.getEstado() instanceof com.tuempresa.gdp.model.state.Armado)) {
            partido.setEstado(new com.tuempresa.gdp.model.state.Armado());
            NotificacionController.getInstance().notificar("El partido de " + partido.getDeporte() + " está armado");
        }
        return true;
    }

    public void confirmarAsistencia(Partido partido, Usuario usuario) {
        partido.getConfirmaciones().add(usuario);
        NotificacionController.getInstance().notificar(
            usuario.getNombre() + " confirmó asistencia al partido de " + partido.getDeporte()
        );
        if (partido.getConfirmaciones().containsAll(partido.getJugadores())) {
            partido.setEstado(new com.tuempresa.gdp.model.state.Confirmado());
            NotificacionController.getInstance().notificar(
                "El partido de " + partido.getDeporte() + " está confirmado"
            );
        }
    }
    public boolean cancelarPartido(Partido partido, Usuario solicitante) {
        if (!partido.getCreador().equals(solicitante)) {
            return false; // solo el creador puede cancelar
        }
        partido.setEstado(new com.tuempresa.gdp.model.state.Cancelado());
        NotificacionController.getInstance().notificar("El partido de " + partido.getDeporte() + " ha sido cancelado");
        return true;
    }
    public void eliminarParaUsuario(Partido partido, Usuario usuario) {
        partidosOcultos.computeIfAbsent(usuario, u -> new HashSet<>()).add(partido);
    }

    public boolean isOcultoParaUsuario(Partido partido, Usuario usuario) {
        return partidosOcultos.getOrDefault(usuario, Collections.emptySet()).contains(partido);
    }
    public boolean salirDePartido(Partido partido, Usuario usuario) {
        boolean removed = partido.getJugadores().remove(usuario);
        partido.getConfirmaciones().remove(usuario); // También quitar confirmación si sale
        return removed;
    }
}
