package org.example.entity.poltrona;

import org.example.entity.sala.Sala;

import java.io.Serializable;

public class Assento implements Serializable {
    private Sala sala;

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
}
