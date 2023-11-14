package org.example.entity.cinema;

import org.example.entity.endereco.Endereco;
import org.example.entity.sala.Sala;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cinema implements Serializable {

    private String nome;
    private Endereco endereco;
    private List<Sala> salas = new ArrayList<>();

    public Cinema(String nome, Endereco endereco) {
        this.nome = nome;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "nome='" + nome + '\'' +
                ", endereco=" + endereco +
                '}';
    }
}
