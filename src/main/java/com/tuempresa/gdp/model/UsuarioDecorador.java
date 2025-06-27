package com.tuempresa.gdp.model;

public abstract class UsuarioDecorador extends Usuario {
    protected Usuario decorateUsuario;

    public UsuarioDecorador(Usuario usuario) {
        this.decorateUsuario = usuario;
    }

    @Override
    public String getNombre() { return decorateUsuario.getNombre(); }
    @Override
    public String getEmail() { return decorateUsuario.getEmail(); }
    @Override
    public String getContraseña() { return decorateUsuario.getContraseña(); }
    @Override
    public void setNombre(String nombre) { decorateUsuario.setNombre(nombre); }
    @Override
    public void setEmail(String email) { decorateUsuario.setEmail(email); }
    @Override
    public void setContraseña(String contraseña) { decorateUsuario.setContraseña(contraseña); }
    public Usuario getUsuario() { return decorateUsuario; }
}
