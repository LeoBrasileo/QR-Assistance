package com.example.android.pruebas3;

public class ObjetoHorariosMaterias
{
    private String id;
    private String horario;
    private String numero;

    public ObjetoHorariosMaterias(String id, String horario, String numero) {
        this.id = id;
        this.horario = horario;
        this.numero = numero;
    }

    public ObjetoHorariosMaterias(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
