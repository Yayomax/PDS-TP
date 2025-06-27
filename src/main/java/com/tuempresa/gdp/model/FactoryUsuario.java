package com.tuempresa.gdp.model;

public class FactoryUsuario {
    public static Usuario crearUsuario(String nombre, String email, String contraseña) {
        return new UsuarioConcreto(nombre, email, contraseña);
    }
}
