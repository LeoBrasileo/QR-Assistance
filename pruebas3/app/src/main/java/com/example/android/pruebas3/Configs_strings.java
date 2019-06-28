package com.example.android.pruebas3;

import android.graphics.drawable.Drawable

public class Configs_strings
{
    int imagen;
    String configuracion;

    public Configs_strings(int imagen, String configuracion) {
        this.imagen = imagen;
        this.configuracion = configuracion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(String configuracion) {
        this.configuracion = configuracion;
    }
}
