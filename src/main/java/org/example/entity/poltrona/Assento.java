package org.example.entity.poltrona;

import org.example.entity.sala.Sala;

import java.io.Serializable;

public class Assento implements Serializable {

    private String fileira;
    private String numero;
    private Sala sala;

    public Assento(String fileira, String numero) {
        this.fileira = fileira;
        this.numero = numero;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String getFileira() {
        return fileira;
    }

    public void setFileira(String fileira) {
        this.fileira = fileira;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return fileira + numero;
    }
}
