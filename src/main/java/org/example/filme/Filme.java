package org.example.filme;

import org.example.entity.genero.Genero;
import org.example.entity.horario.Horario;
import org.example.entity.pessoas.tipos.Ator;
import org.example.entity.pessoas.tipos.Diretor;

import java.io.Serializable;
import java.util.*;

public class Filme implements Serializable {

    private String nome;
    private Genero genero;
    private Integer ano;
    private String descricao;
    private Integer duração;
    private Set<Ator> atores = new HashSet<>();
    private Set<Diretor> diretores = new HashSet<>();
    private Map<Integer, Horario> horarios = new HashMap<>();

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

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDuração() {
        return duração;
    }

    public void setDuração(Integer duração) {
        this.duração = duração;
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

    public Map<Integer, Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(Map<Integer, Horario> horarioList) {
        this.horarios = horarioList;
    }

    public void addHorario(Horario horario){
        int newIndex = 0;

        while (this.horarios.containsKey(newIndex)) {
            newIndex++;
        }

        this.horarios.put(newIndex, horario);
    }

    @Override
    public String toString() {
        return "Filme{" +
                "nome='" + nome + '\'' +
                ", genero=" + genero +
                ", ano=" + ano +
                ", descricao='" + descricao + '\'' +
                ", duração=" + duração +
                ", atores=" + atores +
                ", diretores=" + diretores +
                ", horarios=" + horarios +
                '}';
    }
}
