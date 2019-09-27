package com.example.android.pruebas3;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class Configs_strings
{
    int imagen;
    String configuracion;
    int color;

    public Configs_strings(int imagen, String configuracion, int color) {
        this.imagen = imagen;
        this.configuracion = configuracion;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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
