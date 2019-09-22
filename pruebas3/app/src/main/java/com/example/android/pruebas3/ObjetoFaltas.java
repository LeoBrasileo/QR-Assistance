package com.example.android.pruebas3;

public class ObjetoFaltas {
    private String nombreMateria;
    private Long numeroFaltas;

    public ObjetoFaltas(String nombreMateria, Long numeroFaltas) {
        this.nombreMateria = nombreMateria;
        this.numeroFaltas = numeroFaltas;
    }

    public ObjetoFaltas(String s){}

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public Long getNumeroFaltas() {
        return numeroFaltas;
    }

    public void setNumeroFaltas(Long numeroFaltas) {
        this.numeroFaltas = numeroFaltas;
    }
}
