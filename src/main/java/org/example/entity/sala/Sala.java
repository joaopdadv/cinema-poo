package org.example.entity.sala;

import org.example.entity.cinema.Cinema;
import org.example.entity.poltrona.Assento;

import java.io.Serializable;
import java.util.*;

public class Sala implements Serializable {

    private String nome;
    private Cinema cinema;
    private Set<Assento> assentos = new HashSet<>();

    public Sala(String nome) {
        this.nome = nome;
    }

    public void addAssento(Assento assento) {
        assentos.add(assento);
        assento.setSala(this);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Assento> getAssentos() {
        return assentos;
    }

    public void setAssentos(Set<Assento> poltronas) {
        this.assentos = poltronas;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sala)) return false;
        Sala sala = (Sala) o;
        return Objects.equals(getNome(), sala.getNome()) && Objects.equals(assentos, sala.assentos);
    }
    @Override
    public int hashCode() {
        return Objects.hash(getNome(), assentos);
    }
    @Override
    public String toString() {
        return "Sala " + nome + " - Assentos: " + assentos + "\n";
    }
}
