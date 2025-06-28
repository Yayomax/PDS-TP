package com.tuempresa.gdp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.tuempresa.gdp.model.state.EstadoPartido;
import com.tuempresa.gdp.model.strategy.EstrategiaEmparejamiento;
import com.tuempresa.gdp.model.observer.NotificacionObserver;
import com.tuempresa.gdp.model.observer.PartidoObservable;

public class Partido implements PartidoObservable {
    private String deporte;
    private String ubicacion;
    private LocalDateTime horario;
    private int duracion;
    private int cantidadJugadores;
    private List<Usuario> jugadores;
    private EstadoPartido estado;
    private EstrategiaEmparejamiento estrategiaEmparejamiento;
    private Usuario creador;
    private List<Usuario> confirmaciones = new ArrayList<>();
    private List<NotificacionObserver> observadores = new ArrayList<>();

    public Partido(String deporte, String ubicacion, LocalDateTime horario, int duracion, int cantidadJugadores, EstadoPartido estado, EstrategiaEmparejamiento estrategiaEmparejamiento, Usuario creador) {
        this.deporte = deporte;
        this.ubicacion = ubicacion;
        this.horario = horario;
        this.duracion = duracion;
        this.cantidadJugadores = cantidadJugadores;
        this.jugadores = new ArrayList<>();
        this.estado = estado;
        this.estrategiaEmparejamiento = estrategiaEmparejamiento;
        this.creador = creador;
    }

    public String getDeporte() { return deporte; }
    public String getUbicacion() { return ubicacion; }
    public LocalDateTime getHorario() { return horario; }
    public int getDuracion() { return duracion; }
    public int getCantidadJugadores() { return cantidadJugadores; }
    public List<Usuario> getJugadores() { return jugadores; }
    public EstadoPartido getEstado() { return estado; }
    public void setEstado(EstadoPartido estado) {
        this.estado = estado;
        String mensaje = "El partido de " + deporte + " en " + ubicacion + " cambió de estado a: " + estado.toString();
        notificarObservadores(mensaje);
    }
    public EstrategiaEmparejamiento getEstrategiaEmparejamiento() { return estrategiaEmparejamiento; }
    public void setEstrategiaEmparejamiento(EstrategiaEmparejamiento estrategiaEmparejamiento) { this.estrategiaEmparejamiento = estrategiaEmparejamiento; }
    public Usuario getCreador() { return creador; }
    public List<Usuario> getConfirmaciones() { return confirmaciones; }

    public void actualizarEstadoSiCorresponde() {
        LocalDateTime ahora = LocalDateTime.now();
        // Confirmado -> EnJuego solo si la hora actual es igual o posterior al horario del partido
        if (estado instanceof com.tuempresa.gdp.model.state.Confirmado) {
            if (!ahora.isBefore(horario)) {
                setEstado(new com.tuempresa.gdp.model.state.EnJuego());
            }
        }
        // EnJuego -> Finalizado solo si la hora actual es igual o posterior al fin del partido
        else if (estado instanceof com.tuempresa.gdp.model.state.EnJuego) {
            LocalDateTime fin = horario.plusMinutes(duracion);
            if (!ahora.isBefore(fin)) {
                setEstado(new com.tuempresa.gdp.model.state.Finalizado());
            }
        }
        // Otros estados no cambian automáticamente por tiempo
    }

    @Override
    public String toString() {
        return deporte + " en " + ubicacion;
    }

    @Override
    public void agregarObservador(NotificacionObserver observer) {
        if (!observadores.contains(observer)) {
            observadores.add(observer);
        }
    }

    @Override
    public void quitarObservador(NotificacionObserver observer) {
        observadores.remove(observer);
    }

    @Override
    public void notificarObservadores(String mensaje) {
        for (NotificacionObserver obs : observadores) {
            obs.notificar(mensaje);
        }
    }
}
