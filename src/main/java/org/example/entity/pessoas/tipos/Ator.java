package org.example.entity.pessoas.tipos;

import org.example.entity.pessoas.Pessoa;

import java.io.Serializable;

public class Ator implements Pessoa, Serializable {

    private String nome;
    private String paisOrigem;
    private Pessoa conjuge;

    public Ator(String nome, String paisOrigem) {
        this.nome = nome;
        this.paisOrigem = paisOrigem;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getPaisOrigem() {
        return paisOrigem;
    }

    @Override
    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }

    @Override
    public boolean casar(Pessoa conjuge) {
        if(this.conjuge == null){
            this.conjuge = conjuge;
            return true;
        }
        return false;
    }

    @Override
    public boolean divorciar() {
        if(this.conjuge == null){
            return false;
        }
        this.conjuge = null;
        return false;
    }

    @Override
    public String toString() {
        return "Ator{" +
                "nome='" + nome + '\'' +
                ", paisOrigem='" + paisOrigem + '\'' +
                ", conjuge=" + conjuge +
                '}';
    }
}
