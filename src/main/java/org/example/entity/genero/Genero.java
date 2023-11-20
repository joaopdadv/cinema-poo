package org.example.entity.genero;

import java.io.Serializable;
import java.util.Objects;

public class Genero implements Serializable {

    private String nome;

    public Genero(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genero)) return false;
        Genero genero = (Genero) o;
        return Objects.equals(getNome(), genero.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome());
    }

    @Override
    public String toString() {
        return "Genero{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
