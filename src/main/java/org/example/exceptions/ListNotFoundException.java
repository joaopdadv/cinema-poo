package org.example.exceptions;

public class ListNotFoundException extends Exception{

    public static final String MESSAGE = "Lista não encontrada no cinema. Certifique-se que existe um arquivo 'cinemas.dat' contendo o objeto do cinema na raiz do projeto e esse objeto possui uma lista de salas.";

    public ListNotFoundException(){
        super(MESSAGE);
    }
}
