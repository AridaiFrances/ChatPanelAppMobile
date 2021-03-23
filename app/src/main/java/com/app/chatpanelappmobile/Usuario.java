package com.app.chatpanelappmobile;

public class Usuario {

    String nombre, pass;

    public Usuario(String nombre, String pass) {
        this.nombre = nombre;
        this.pass = pass;

    }

    public String getNombre() {
        return nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


}