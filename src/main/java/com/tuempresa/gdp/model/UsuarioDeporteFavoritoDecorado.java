package com.tuempresa.gdp.model;

public class UsuarioDeporteFavoritoDecorado extends UsuarioDecorador {
    private DeporteFavorito deporteFavorito;

    public UsuarioDeporteFavoritoDecorado(Usuario usuario, DeporteFavorito deporteFavorito) {
        super(usuario);
        this.deporteFavorito = deporteFavorito;
    }

    public DeporteFavorito getDeporteFavorito() { return deporteFavorito; }
}
