package org.example.services;

import org.example.entity.cinema.Cinema;
import org.example.entity.genero.Genero;
import org.example.entity.horario.Horario;
import org.example.entity.pessoas.Pessoa;
import org.example.entity.pessoas.tipos.Ator;
import org.example.entity.pessoas.tipos.Diretor;
import org.example.entity.sala.Sala;
import org.example.exceptions.CinemaNotFoundException;
import org.example.exceptions.SalasNotFoundException;
import org.example.filme.Filme;

import java.io.*;
import java.util.*;

public class CinemaService {

    public boolean verificaArquivo(String path){
        try{
            File file = new File(path);

            if (file.exists()) {
                return true;
            } else {
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void salvarEmArquivo(Object o, String filePath) {

        //try-with-resources fecha os recursos após execução
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(o);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cinema lerArquivoCinemas() throws CinemaNotFoundException {

        //try-with-resources fecha os recursos após execução
        try (FileInputStream fileInputStream = new FileInputStream("cinemas.dat");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            return (Cinema) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new CinemaNotFoundException();
        }
    }

    public Set<Sala> getHashSetSalas() throws SalasNotFoundException {
        try {
            return lerArquivoCinemas().getSalas();
        } catch (CinemaNotFoundException e) {
            e.printStackTrace();
            throw new SalasNotFoundException();
        }
    }

    public boolean verificarSala(String nome) {
        try {
            return getHashSetSalas().stream().anyMatch(sala -> sala.getNome().equalsIgnoreCase(nome));
        } catch (SalasNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void editarSala(String nomeSala, String novoNome) throws SalasNotFoundException {
        try {
            Set<Sala> salas = getHashSetSalas();

            salas.forEach(sala -> {
                if (sala.getNome().equalsIgnoreCase(nomeSala)) {
                    sala.setNome(novoNome);
                }
            });

            if (!verificarSala(nomeSala)) {
                throw new SalasNotFoundException();
            }

            salvarListaSalas(salas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void salvarListaSalas(Set<Sala> salas){
        try {
            Cinema cinema = lerArquivoCinemas();
            cinema.setSalas(salas);
            salvarEmArquivo(cinema, "cinemas.dat");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void salvarSala(Sala sala) {
        try {
            Set<Sala> salas = getHashSetSalas();

            salas.removeIf(s -> s.getNome().equalsIgnoreCase(sala.getNome()));
            salas.add(sala);

            salvarListaSalas(salas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Sala getSalaByName(String nome) {
        try {
            return getHashSetSalas().stream()
                    .filter(s -> s.getNome().equals(nome))
                    .findFirst()
                    .orElseThrow(() -> new SalasNotFoundException("Sala com o nome " + nome + " não encontrada"));
        } catch (SalasNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean salasCadastradas() {
        try {
            return !getHashSetSalas().isEmpty(); // se vazio retorna false
        } catch (SalasNotFoundException e) {
            return false;
        }
    }

    public void removeAssento(Sala sala, String fileira, String numero) {
        sala.getAssentos().removeIf(a -> a.getNumero().equalsIgnoreCase(numero) && a.getFileira().equalsIgnoreCase(fileira));
        salvarSala(sala);
    }

    public boolean verificaAssento(Sala sala, String fileira, String numero) {
        return sala.getAssentos().stream()
                .anyMatch(a -> a.getFileira().equalsIgnoreCase(fileira) && a.getNumero().equalsIgnoreCase(numero));
    }

    public void salvarPessoa(Pessoa pessoa) {
        Map<Integer, Pessoa> pessoas = getPessoasFromFile();

        pessoas.put(getNextIndex(pessoas), pessoa);

        salvarEmArquivo(pessoas, "pessoas.dat");
    }

    public Map<Integer, Pessoa> getPessoasFromFile(){

        Map<Integer, Pessoa> map = new HashMap<>();

        File file = new File("pessoas.dat");

        if(file.exists()){
            try{
                FileInputStream fileInputStream = new FileInputStream("pessoas.dat");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Object o = objectInputStream.readObject();

                map = (Map<Integer, Pessoa>) o;

                fileInputStream.close();
                objectInputStream.close();
            }catch (Exception e){

            }
        }
        return map;
    }

    public void excluirPessoa(int id){
        Map<Integer, Pessoa> pessoasMap = getPessoasFromFile();

        pessoasMap.remove(id);

        //TODO: percorrer filmes e remover também

        salvarEmArquivo(pessoasMap, "pessoas.dat");
    }

    public boolean verificaAtores() {
        Map<Integer, Pessoa> pessoas = getPessoasFromFile();

        for (Pessoa pessoa : pessoas.values()) {
            if (pessoa instanceof Ator) {
                return true;
            }
        }
        return false;
    }

    public Map<Integer, Ator> getMapAtores() {
        Map<Integer, Pessoa> pessoas = getPessoasFromFile();
        Map<Integer, Ator> atoresMap = new HashMap<>();

        for (Map.Entry<Integer, Pessoa> entry : pessoas.entrySet()) {
            if (entry.getValue() instanceof Ator) {
                Ator ator = (Ator) entry.getValue();
                atoresMap.put(entry.getKey(), ator);
            }
        }

        return atoresMap;
    }

    public boolean verificaDiretores() {
        Map<Integer, Pessoa> pessoas = getPessoasFromFile();

        for (Pessoa pessoa : pessoas.values()) {
            if (pessoa instanceof Diretor) {
                return true;
            }
        }
        return false;
    }

    public Map<Integer, Diretor> getMapDiretores() {
        Map<Integer, Pessoa> pessoas = getPessoasFromFile();
        Map<Integer, Diretor> diretoresMap = new HashMap<>();

        for (Map.Entry<Integer, Pessoa> entry : pessoas.entrySet()) {
            if (entry.getValue() instanceof Diretor) {
                Diretor diretor = (Diretor) entry.getValue();
                diretoresMap.put(entry.getKey(), diretor);
            }
        }

        return diretoresMap;
    }

    public void salvarHorario(Horario horario, Filme filme){
        //TODO: salvar no filme
    }

    public void excluirHorario(Integer id, Filme filme){}

    public Map<Integer, Horario> getHorariosFromFilme(){
        //TODO: get Map de horarios do filme
        return null;
    };

    public void salvarFilme(Filme filme){
        try{
            Cinema cinema = lerArquivoCinemas();

            cinema.addFilme(filme);

            salvarEmArquivo(cinema, "cinemas.dat");
        }catch (CinemaNotFoundException e){
            e.printStackTrace();
        }

    }

    public int getNextIndex(Map<Integer, Pessoa> map) {
        int newIndex = 0;

        while (map.containsKey(newIndex)) {
            newIndex++;
        }

        return newIndex;
    }


    public Set<Genero> getGenerosFromFile(){

        Set<Genero> set = new HashSet<>();

        File file = new File("generos.dat");

        if(file.exists()){
            try{
                FileInputStream fileInputStream = new FileInputStream("generos.dat");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Object o = objectInputStream.readObject();

                set = (Set<Genero>) o;

                fileInputStream.close();
                objectInputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return set;
    }

    public boolean verificaHorarios(){
        //TODO: ver se tem horarios cadastrados
        return true;
    }

    public void salvarGenero(Genero genero){
        Set<Genero> generos = getGenerosFromFile();

        generos.add(genero);

        salvarEmArquivo(generos, "generos.dat");
    }

    public void editarGenero(Integer id, Genero genero){
        Map<Integer, Genero> generosMap = getGenerosMapFromFile();

        generosMap.put(id, genero);

        Set<Genero> generosSet = new HashSet<>(generosMap.values());

        salvarEmArquivo(generosSet, "generos.dat");
    }

    public Map<Integer, Genero> getGenerosMapFromFile() {
        Set<Genero> generoSet = getGenerosFromFile();

        Map<Integer, Genero> generosMap = new HashMap<>();

        int id = 0;
        for (Genero genero : generoSet) {
            generosMap.put(id++, genero);
        }

        return generosMap;
    }

    public void excluirGenero(Genero genero){
        //TODO: remover do Set por nome e percorrer filmes e deletar também.
    }

    public Map<Integer, Filme> getFilmesMap() {
        Map<Integer, Filme> filmesMap = new HashMap<>();

        try {
            List<Filme> filmes = lerArquivoCinemas().getFilmes();

            // Transforma a lista de filmes em um HashMap
            for (int i = 0; i < filmes.size(); i++) {
                filmesMap.put(i, filmes.get(i));
            }

        } catch (CinemaNotFoundException e) {
            e.printStackTrace();
        }

        return filmesMap;
    }

    public void excluirFilme(Integer id){
        Map<Integer, Filme> filmesMap = getFilmesMap();

        filmesMap.remove(id);

        try {
            Cinema cinema = lerArquivoCinemas();

            cinema.setFilmes(new ArrayList<>(filmesMap.values()));
            salvarEmArquivo(cinema, "cinemas.dat");
        }catch (CinemaNotFoundException e){
            e.printStackTrace();
        }
    }

    public void editarFilme(Integer id, Filme filme){
        try {
            Cinema cinema = lerArquivoCinemas();

            Map<Integer, Filme> filmeMap = getFilmesMap();

            filmeMap.put(id, filme);

            cinema.setFilmes(new ArrayList<>(filmeMap.values()));
            salvarEmArquivo(cinema, "cinemas.dat");
        }catch (CinemaNotFoundException e){
            e.printStackTrace();
        }
    }
}
