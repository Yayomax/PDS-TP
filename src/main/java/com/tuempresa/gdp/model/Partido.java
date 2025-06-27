package com.tuempresa.gdp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.tuempresa.gdp.model.state.EstadoPartido;
import com.tuempresa.gdp.model.strategy.EstrategiaEmparejamiento;

public class Partido {
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
    public void setEstado(EstadoPartido estado) { this.estado = estado; }
    public EstrategiaEmparejamiento getEstrategiaEmparejamiento() { return estrategiaEmparejamiento; }
    public void setEstrategiaEmparejamiento(EstrategiaEmparejamiento estrategiaEmparejamiento) { this.estrategiaEmparejamiento = estrategiaEmparejamiento; }
    public Usuario getCreador() { return creador; }
    public List<Usuario> getConfirmaciones() { return confirmaciones; }

    @Override
    public String toString() {
        return deporte + " en " + ubicacion;
    }
}
