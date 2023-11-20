package org.example.filme;

import org.example.entity.genero.Genero;
import org.example.entity.horario.Horario;
import org.example.entity.pessoas.tipos.Ator;
import org.example.entity.pessoas.tipos.Diretor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Filme implements Serializable {

    private String nome;
    private Genero genero;
    private Set<Ator> atores = new HashSet<>();
    private Set<Diretor> diretores = new HashSet<>();
    private List<Horario> horarios = new ArrayList<>();

    public Filme(String nome, Genero genero) {
        this.nome = nome;
        this.genero = genero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Set<Ator> getAtores() {
        return atores;
    }

    public void setAtores(Set<Ator> atores) {
        this.atores = atores;
    }

    public Set<Diretor> getDiretores() {
        return diretores;
    }

    public void setDiretores(Set<Diretor> diretores) {
        this.diretores = diretores;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarioList) {
        this.horarios = horarioList;
    }

    public void addHorario(Horario horario){
        this.horarios.add(horario);
    }

    @Override
    public String toString() {
        return "Filme{" +
                "nome='" + nome + '\'' +
                ", genero=" + genero +
                ", atores=" + atores +
                ", diretores=" + diretores +
                '}';
    }
}
