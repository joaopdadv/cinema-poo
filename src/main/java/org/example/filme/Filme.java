package org.example.filme;

import org.example.entity.genero.Genero;
import org.example.entity.horario.Horario;
import org.example.entity.pessoas.Pessoa;
import org.example.entity.pessoas.tipos.Ator;
import org.example.entity.pessoas.tipos.Diretor;

import java.io.Serializable;
import java.util.*;

public class Filme implements Serializable {

    private String nome;
    private Genero genero;
    private Integer ano;
    private String descricao;
    private Integer duracao;
    private Set<Ator> atores = new HashSet<>();
    private Set<Diretor> diretores = new HashSet<>();
    private Map<Integer, Horario> horarios = new HashMap<>();

    public Filme(String nome, Integer ano, String descricao, Integer duracao) {
        this.nome = nome;
        this.ano = ano;
        this.descricao = descricao;
        this.duracao = duracao;
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

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duração) {
        this.duracao = duração;
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

    public void addAtor(Ator ator){
        atores.add(ator);
    }

    public void addDiretor(Diretor diretor){
        diretores.add(diretor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Filme)) return false;
        Filme filme = (Filme) o;
        return Objects.equals(getNome(), filme.getNome()) && Objects.equals(getGenero(), filme.getGenero()) && Objects.equals(getAno(), filme.getAno()) && Objects.equals(getDescricao(), filme.getDescricao()) && Objects.equals(getDuracao(), filme.getDuracao()) && Objects.equals(getAtores(), filme.getAtores()) && Objects.equals(getDiretores(), filme.getDiretores()) && Objects.equals(getHorarios(), filme.getHorarios());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getGenero(), getAno(), getDescricao(), getDuracao(), getAtores(), getDiretores(), getHorarios());
    }

    @Override
    public String toString() {
        return "Filme{" +
                "nome='" + nome + '\'' +
                ", genero=" + genero +
                ", ano=" + ano +
                ", descricao='" + descricao + '\'' +
                ", duração=" + duracao +
                ", atores=" + atores +
                ", diretores=" + diretores +
                ", horarios=" + horarios +
                "}\n";
    }
}
