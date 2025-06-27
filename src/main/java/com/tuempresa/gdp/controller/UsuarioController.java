package com.tuempresa.gdp.controller;

import com.tuempresa.gdp.model.*;
import java.util.*;

// UsuarioController ahora es instanciable y desacoplado para MVC
public class UsuarioController {
    private static UsuarioController singleton;
    private final List<Usuario> usuarios = new ArrayList<>();
    private Usuario usuarioActual;

    public static UsuarioController getInstance() {
        if (singleton == null) singleton = new UsuarioController();
        return singleton;
    }

    public boolean login(String email, String contraseña) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getContraseña().equals(contraseña)) {
                setUsuarioActual(u);
                return true;
            }
        }
        return false;
    }
    public void registrar(String nombre, String email, String contraseña, String deporte, String nivel) {
        Usuario u = new UsuarioConcreto(nombre, email, contraseña);
        if (deporte != null && !deporte.isEmpty()) {
            try {
                DeporteFavorito df = DeporteFavorito.valueOf(deporte);
                u = new UsuarioDeporteFavoritoDecorado(u, df);
            } catch (Exception ignored) {}
        }
        if (nivel != null && !nivel.isEmpty()) {
            try {
                NivelJuego nj = NivelJuego.valueOf(nivel);
                u = new UsuarioNivelDecorado(u, nj);
            } catch (Exception ignored) {}
        }
        usuarios.add(u);
        usuarioActual = u;
    }
    public void setUsuarioActual(Usuario u) {
        usuarioActual = u;
    }
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    public void logout() {
        usuarioActual = null;
    }
    public Usuario altaUsuario(String nombre, String email, String contraseña) {
        return FactoryUsuario.crearUsuario(nombre, email, contraseña);
    }
    public Usuario decorarConDeporte(Usuario usuario, DeporteFavorito deporte) {
        return new UsuarioDeporteFavoritoDecorado(usuario, deporte);
    }
    public Usuario decorarConNivel(Usuario usuario, NivelJuego nivel) {
        return new UsuarioNivelDecorado(usuario, nivel);
    }
    public void actualizarUsuario(String nuevoNombre, String nuevoEmail) {
        Usuario actual = usuarioActual;
        if (actual instanceof UsuarioConcreto) {
            ((UsuarioConcreto) actual).setNombre(nuevoNombre);
            ((UsuarioConcreto) actual).setEmail(nuevoEmail);
        }
    }
    public void actualizarUsuarioCompleto(String nombre, String email, String contrasena, NivelJuego nivel, String deporte) {
        Usuario actual = getUsuarioActual();
        UsuarioConcreto base = null;
        Usuario temp = actual;
        while (temp instanceof UsuarioDecorador) {
            temp = ((UsuarioDecorador) temp).getUsuario();
        }
        if (temp instanceof UsuarioConcreto) {
            base = (UsuarioConcreto) temp;
        }
        if (base != null) {
            base.setNombre(nombre);
            base.setEmail(email);
            if (contrasena != null && !contrasena.isEmpty()) {
                base.setContraseña(contrasena);
            }
            Usuario decorado = base;
            if (deporte != null && !deporte.isEmpty()) {
                try {
                    DeporteFavorito df = DeporteFavorito.valueOf(deporte);
                    decorado = new UsuarioDeporteFavoritoDecorado(decorado, df);
                } catch (Exception ignored) {}
            }
            if (nivel != null) {
                decorado = new UsuarioNivelDecorado(decorado, nivel);
            }
            setUsuarioActual(decorado);
            for (int i = 0; i < usuarios.size(); i++) {
                Usuario u = usuarios.get(i);
                if (u.getEmail().equalsIgnoreCase(email)) {
                    usuarios.set(i, decorado);
                    break;
                }
            }
        }
    }
    public String getDeporteFavoritoActual() {
        Usuario u = getUsuarioActual();
        while (u instanceof UsuarioDecorador) {
            if (u instanceof UsuarioDeporteFavoritoDecorado) {
                return ((UsuarioDeporteFavoritoDecorado) u).getDeporteFavorito().name();
            }
            u = ((UsuarioDecorador) u).getUsuario();
        }
        return null;
    }
}
