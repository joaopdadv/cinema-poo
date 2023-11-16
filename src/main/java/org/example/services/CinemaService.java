package org.example.services;

import org.example.entity.cinema.Cinema;
import org.example.entity.sala.Sala;
import org.example.exceptions.CinemaNotFoundException;
import org.example.exceptions.ListNotFoundException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.stream.Collectors;

public class CinemaService {
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
        }catch(Exception e){
            e.printStackTrace();
            throw new CinemaNotFoundException();
        }
    }

    public List<Sala> getListaSalas() throws ListNotFoundException {
        try{
            Cinema cinema = lerArquivoCinemas();
            return cinema.getSalas();

        } catch (CinemaNotFoundException e) {
            e.printStackTrace();
            throw new ListNotFoundException();
        }
    }

    public boolean verificarSala(String nome) throws ListNotFoundException{
        try {
            List<Sala> salas = getListaSalas();
            return salas.stream().anyMatch(sala -> sala.getNome().equals(nome));
        }catch (ListNotFoundException e){
            e.printStackTrace();
            throw new ListNotFoundException();
        }
    }

    public void editarSala(String nomeSala, String novoNome) throws ListNotFoundException{
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
        }catch (ListNotFoundException e){
            throw new ListNotFoundException();
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
}
