package org.example.services;

import org.example.entity.cinema.Cinema;
import org.example.entity.horario.Horario;
import org.example.entity.pessoas.Pessoa;
import org.example.entity.sala.Sala;
import org.example.exceptions.CinemaNotFoundException;
import org.example.exceptions.SalasNotFoundException;

import java.io.*;
import java.util.*;

public class CinemaService {

    public Boolean cinemaCadastrado(){
        try{
            File file = new File("cinemas.dat");

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
                e.printStackTrace();
            }
        }
        return map;
    }

    public void excluirPessoa(int id){
        Map<Integer, Pessoa> horarios = getPessoasFromFile();

        horarios.remove(id);

        salvarEmArquivo(horarios, "horarios.dat");
    }

    public int getNextIndex(Map<Integer, Pessoa> map) {
        int newIndex = 0;

        while (map.containsKey(newIndex)) {
            newIndex++;
        }

        return newIndex;
    }
}
