package com.tuempresa.gdp.model;

public abstract class Usuario {
    public abstract String getNombre();
    public abstract String getEmail();
    public abstract String getContraseña();
    public abstract void setNombre(String nombre);
    public abstract void setEmail(String email);
    public abstract void setContraseña(String contraseña);
}
