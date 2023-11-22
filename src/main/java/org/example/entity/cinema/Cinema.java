package org.example.entity.cinema;

import org.example.entity.endereco.Endereco;
import org.example.entity.sala.Sala;
import org.example.filme.Filme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cinema implements Serializable {

    private String nome;
    private Endereco endereco;
    private Set<Sala> salas = new HashSet<>();

    private List<Filme> filmes = new ArrayList<>();

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

    public Set<Sala> getSalas() {
        return salas;
    }

    public void setSalas(Set<Sala> salas) {
        this.salas = salas;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<Filme> filmes) {
        this.filmes = filmes;
    }

    public void addSala(Sala sala){
        sala.setCinema(this);
        salas.add(sala);
    }

    public void addFilme(Filme filme){
        filmes.add(filme);
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "nome='" + nome + '\'' +
                ", endereco=" + endereco +
                ", salas=" + salas +
                '}';
    }
}
