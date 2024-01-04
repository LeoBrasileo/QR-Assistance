package com.example.android.pruebas3;

import android.widget.Spinner;

public class ObjetoUsuario {
    private String nombre;
    private String password;
    private String dni;
    private String email;
    private String school;


    public ObjetoUsuario(String nombre, String password, String dni, String email, String school)
    {
        this.nombre = nombre;
        this.password = password;
        this.dni = dni;
        this.email = email;
        this.school = school;
    }

    public ObjetoUsuario(String nombre, String password, String dni, String email, Spinner school) {}

    public ObjetoUsuario (){}

    public String getNombre(){
        return nombre;
    }

    public String getPassword(){
        return password;
    }

    public String getDni(){
        return dni;
    }

    public String getSchool() { return school; }

    public void setSchool(String school) { this.school = school; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setDNI(String DNI){
        this.dni = DNI;
    }

    /*public String getAll(){
        //Aca se puede generar la estructura JSON
        return " ";
    }*/

}
