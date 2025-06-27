package com.tuempresa.gdp.model;

public class UsuarioNivelDecorado extends UsuarioDecorador {
    private NivelJuego nivel;

    public UsuarioNivelDecorado(Usuario usuario, NivelJuego nivel) {
        super(usuario);
        this.nivel = nivel;
    }

    public NivelJuego getNivel() { return nivel; }
}
