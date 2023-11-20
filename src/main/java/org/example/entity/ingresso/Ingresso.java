package org.example.entity.ingresso;

import org.example.entity.horario.Horario;
import org.example.entity.poltrona.Assento;
import org.example.filme.Filme;

import java.io.File;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

public class Ingresso implements Serializable {
    private String nomeComprador;
    private Date data = Date.from(Instant.now());
    private String celular;
    private Double preco;
    private boolean meiaEntrada;
    private Assento assento;
    private Horario horario;
    private Filme filme;

    public Ingresso(String nomeComprador, String celular, Double preco, boolean meiaEntrada, Assento assento, Horario horario, Filme filme) {
        this.nomeComprador = nomeComprador;
        this.celular = celular;
        this.preco = preco;
        this.meiaEntrada = meiaEntrada;
        this.assento = assento;
        this.horario = horario;
        this.filme = filme;
    }

    public String getNomeComprador() {
        return nomeComprador;
    }

    public void setNomeComprador(String nomeComprador) {
        this.nomeComprador = nomeComprador;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public boolean isMeiaEntrada() {
        return meiaEntrada;
    }

    public void setMeiaEntrada(boolean meiaEntrada) {
        this.meiaEntrada = meiaEntrada;
    }

    public Assento getAssento() {
        return assento;
    }

    public void setAssento(Assento assento) {
        this.assento = assento;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    @Override
    public String toString() {
        return "Ingresso{" +
                "nomeComprador='" + nomeComprador + '\'' +
                ", data=" + data +
                ", celular='" + celular + '\'' +
                ", preco=" + preco +
                ", meiaEntrada=" + meiaEntrada +
                ", assento=" + assento +
                ", horario=" + horario +
                ", filme=" + filme +
                '}';
    }
}
