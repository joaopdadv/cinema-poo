package org.example.exceptions;

public class CinemaNotFoundException extends Exception{

    public static final String MESSAGE = "O cinema não foi encontrado nos arquivos. Certifique-se que existe um arquivo 'cinemas.dat' contendo o objeto do cinema na raiz do projeto.";

    public CinemaNotFoundException(){
        super(MESSAGE);
    }
}
