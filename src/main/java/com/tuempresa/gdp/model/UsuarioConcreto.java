package com.tuempresa.gdp.model;

public class UsuarioConcreto extends Usuario {
    public UsuarioConcreto(String nombre, String email, String contraseña) {
        super(nombre, email, contraseña);
    }

    @Override
    public void setContraseña(String contraseña) {
        super.setContraseña(contraseña);
    }
}
