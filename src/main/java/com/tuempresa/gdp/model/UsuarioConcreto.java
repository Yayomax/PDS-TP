package com.tuempresa.gdp.model;

public class UsuarioConcreto extends Usuario {
    private String nombre;
    private String email;
    private String contraseña;

    public UsuarioConcreto(String nombre, String email, String contraseña) {
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
    }

    @Override
    public String getNombre() { return nombre; }
    @Override
    public String getEmail() { return email; }
    @Override
    public String getContraseña() { return contraseña; }
    @Override
    public void setNombre(String nombre) { this.nombre = nombre; }
    @Override
    public void setEmail(String email) { this.email = email; }
    @Override
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
}
