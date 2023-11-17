package org.example.services;

import org.example.entity.cinema.Cinema;
import org.example.entity.poltrona.Assento;
import org.example.entity.sala.Sala;
import org.example.exceptions.CinemaNotFoundException;
import org.example.exceptions.SalasNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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

    public void salvarEmArquivo(Object o, String filePath){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(o);

            objectOutputStream.close();
            fileOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Cinema lerArquivoCinemas() throws CinemaNotFoundException {
        try{
            FileInputStream fileInputStream = new FileInputStream("cinemas.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Object object = objectInputStream.readObject();
            Cinema c = (Cinema) object;

            objectInputStream.close();
            fileInputStream.close();
            return c;
        }catch (Exception e){
            throw new CinemaNotFoundException();
        }
    }

    public List<Sala> getListaSalas() throws SalasNotFoundException {
        try{
            Cinema cinema = lerArquivoCinemas();
            return cinema.getSalas();

        } catch (CinemaNotFoundException e) {
            e.printStackTrace();
            throw new SalasNotFoundException();
        }
    }

    public boolean verificarSala(String nome) throws SalasNotFoundException {
        try {
            List<Sala> salas = getListaSalas();
            return salas.stream().anyMatch(sala -> sala.getNome().equals(nome));
        }catch (SalasNotFoundException e){
            e.printStackTrace();
            throw new SalasNotFoundException();
        }
    }

    public void editarSala(String nomeSala, String novoNome) throws SalasNotFoundException {
        try {
            if(verificarSala(nomeSala)){
                List<Sala> salas = getListaSalas();

                salas = salas.stream().map((sala) -> {
                    if (sala.getNome().equalsIgnoreCase(nomeSala)){
                        sala.setNome(novoNome);
                    }
                    return sala;
                }).collect(Collectors.toList());

                salvarListaSalas(salas);
            }
        }catch (SalasNotFoundException e){
            throw new SalasNotFoundException();
        }
    }

    public void salvarListaSalas(List<Sala> salas){
        try {
            Cinema cinema = lerArquivoCinemas();
            cinema.setSalas(salas);

            salvarEmArquivo(cinema, "cinemas.dat");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void salvarSala(Sala sala){
        try{
            List<Sala> salas = getListaSalas();

            salas = salas.stream().map((s) -> {
                if(s.getNome().equalsIgnoreCase(sala.getNome())){
                    return sala;
                }
                return s;
            }).collect(Collectors.toList());

            salvarListaSalas(salas);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Sala getSalaByName(String nome){
        try {
            List<Sala> salas = getListaSalas();

            Sala sala = salas.stream()
                    .filter(s -> s.getNome().equals(nome))
                    .findFirst()
                    .orElse(null);

            if (sala == null) {
                throw new SalasNotFoundException("Sala com o nome " + nome + " não encontrada");
            }

            return sala;
        } catch (SalasNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean salasCadastradas() {
        try{
            List<Sala> salas = getListaSalas();
            if(salas.isEmpty()){
                return false;
            }else{
                return true;
            }
        }catch(SalasNotFoundException e){
            return false;
        }
    }

    public void removeAssento(Sala sala, String fileira, String numero){
        List<Assento> assentos = sala.getAssentos();

        assentos = assentos.stream()
                .filter(a -> !(a.getNumero().equalsIgnoreCase(numero) && a.getFileira().equalsIgnoreCase(fileira)))
                .collect(Collectors.toList());

        sala.setAssentos(assentos);
        salvarSala(sala);
    }

    public boolean verificaAssento(Sala sala, String fileira, String numero){
        try{
            List<Assento> assentos = sala.getAssentos();

            Assento assento = assentos.stream().filter((a) -> {
                return (a.getFileira().equalsIgnoreCase(fileira) && a.getNumero().equalsIgnoreCase(numero));
            }).collect(Collectors.toList()).stream()
                    .findFirst()
                    .orElse(null);

            if (assento == null){
                return false;
            }

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
