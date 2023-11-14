package org.example.entity.sala;

import org.example.entity.poltrona.Assento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sala implements Serializable {

    private String nome;
    private List<Assento> poltronas = new ArrayList<>();

    public Sala(String nome) {
        this.nome = nome;
    }

    public void addAssento(Assento assento) {
        poltronas.add(assento);
        assento.setSala(this);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Assento> getPoltronas() {
        return poltronas;
    }

    public void setPoltronas(List<Assento> poltronas) {
        this.poltronas = poltronas;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "nome='" + nome + '\'' +
                ", poltronas=" + poltronas +
                '}';
    }
}
