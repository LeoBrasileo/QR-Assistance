package com.example.android.pruebas3;

public class ObjetoPresensia {
    private String alumno;
    private String horario;
    private String dni;

    public ObjetoPresensia(){}

    public ObjetoPresensia(String alumno, String horario, String dni) {
        this.alumno = alumno;
        this.horario = horario;
        this.dni = dni;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }
}
