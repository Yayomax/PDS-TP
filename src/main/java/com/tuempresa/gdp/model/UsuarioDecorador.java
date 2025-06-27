package com.tuempresa.gdp.model;

public abstract class UsuarioDecorador extends Usuario {
    protected Usuario decorateUsuario;

    public UsuarioDecorador(Usuario usuario) {
        super(usuario.getNombre(), usuario.getEmail(), usuario.getContraseña());
        this.decorateUsuario = usuario;
    }

    @Override
    public String getNombre() { return decorateUsuario.getNombre(); }
    @Override
    public String getEmail() { return decorateUsuario.getEmail(); }
    @Override
    public String getContraseña() { return decorateUsuario.getContraseña(); }
    public Usuario getUsuario() { return decorateUsuario; }
}
