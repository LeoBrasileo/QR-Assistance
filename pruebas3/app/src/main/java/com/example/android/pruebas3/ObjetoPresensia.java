package com.example.android.pruebas3;

public class ObjetoPresensia {
    private String alumno;
    private String id;

    public ObjetoPresensia(){}

    public ObjetoPresensia(String alumno, String id) {
        this.alumno = alumno;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }
}
