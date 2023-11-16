package org.example;

import org.example.entity.cinema.Cinema;
import org.example.entity.endereco.Endereco;
import org.example.entity.sala.Sala;
import org.example.exceptions.CinemaNotFoundException;
import org.example.exceptions.ListNotFoundException;
import org.example.services.CinemaService;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {

    private static Scanner scanner;
    private final CinemaService cinemaService;
    public Menu(CinemaService cinemaService){
        this.cinemaService = cinemaService;
    }
    public static void main(String[] args) {
        CinemaService service = new CinemaService();
        Menu menu = new Menu(service);
        menu.mostraMenu();
    }

    public void mostraMenu(){

        int opcao = 0;
        scanner = new Scanner(System.in);

        do {
            System.out.println("---------------------------------------------");
            System.out.println("Escolha a opção:");
            System.out.println(" 1 - CINEMA");
            System.out.println(" 2 - SALAS");
            System.out.println(" 0 - Sair");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    mostrarMenuCinema();
                    break;
                case 2:
                    if(cinemaCadastrado()) {
                        mostrarMenuSalas();
                    }
                    break;
                case 0:
                    System.out.println("Saindo...");
                    System.exit(0);
            }
            System.out.println("---------------------------------------------");
        }while (opcao != 0);
        scanner.close();
    }

    public boolean cinemaCadastrado(){
        try{
            File file = new File("cinemas.dat");

            if (file.exists()) {
                return true;
            } else {
                System.out.println("É preciso cadastrar um Cinema!");
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // -------------------------------------------------------------------------------------------------- //
    // --------------------------------------------- CINEMA --------------------------------------------- //
    // -------------------------------------------------------------------------------------------------- //

    public void mostrarMenuCinema(){
        int opcao = 0;

        do {
            System.out.println("---------------------------------------------");
            System.out.println("MENU CINEMA");
            System.out.println(" 1 - Criar");
            System.out.println(" 2 - Excluir");
            System.out.println(" 3 - Editar");
            System.out.println(" 4 - Obter dados");
            System.out.println(" 0 - Voltar");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    criarCinema();
                    break;
                case 2:
                    excluirCinema();
                    break;
                case 3:
                    editarCinema();
                    break;
                case 4:
                    listarCinema();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
            }
            System.out.println("---------------------------------------------");
        }while (opcao != 0);
    }

    public void criarCinema(){

        try{
            File file = new File("cinemas.dat");

            if (file.exists()) {
                System.out.println("File cinemas.dat já existe. Listando cinema...");
                listarCinema();
            } else {
                System.out.println("---------------------------------------------");
                System.out.println("Criar um cinema");
                System.out.println("Informe o nome do cinema:");

                String nome = scanner.nextLine();

                System.out.println("Informe o endereço do cinema:");

                System.out.print("Rua: ");
                String rua = scanner.nextLine();

                System.out.print("Número: ");
                String numero = scanner.nextLine();

                System.out.print("Complemento: ");
                String complemento = scanner.nextLine();

                Cinema cinema = new Cinema(nome, new Endereco(rua, numero, complemento));
                cinemaService.salvarEmArquivo(cinema, "cinemas.dat");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void excluirCinema(){
        try{
            File file = new File("cinemas.dat");

            if (cinemaCadastrado() && confirmarAcao("excluir cinema")){
                if (file.delete()) {
                    System.out.println("Cinema excluído com sucesso!");
                } else {
                    System.out.println("Não foi possível excluir o cinema.");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void editarCinema(){
        try{
            if (cinemaCadastrado()){
                Cinema c = cinemaService.lerArquivoCinemas();

                int opcao = 0;
                do{
                    System.out.println("Editar nome ou endereço?");
                    System.out.println(" 1 - Nome");
                    System.out.println(" 2 - Endereço");
                    System.out.println(" 0 - Voltar");
                    opcao = scanner.nextInt();
                    scanner.nextLine();

                    if (opcao == 1){
                        System.out.println("Digite o novo nome do cinema:");
                        String novoNome = scanner.nextLine();
                        c.setNome(novoNome);

                        cinemaService.salvarEmArquivo(c, "cinemas.dat");
                    }else if(opcao == 2){
                        System.out.print("Rua: ");
                        String novaRua = scanner.nextLine();
                        System.out.print("Número: ");
                        String novoNumero = scanner.nextLine();
                        System.out.print("Complemento: ");
                        String novoComplemento = scanner.nextLine();

                        c.setEndereco(new Endereco(novaRua, novoNumero, novoComplemento));

                        cinemaService.salvarEmArquivo(c, "cinemas.dat");
                    }
                }while(opcao != 0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listarCinema(){
        try{
            if (cinemaCadastrado()){
                Cinema c = cinemaService.lerArquivoCinemas();
                System.out.println(c);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------------------------------------------- //
    // --------------------------------------------- SALAS --------------------------------------------- //
    // -------------------------------------------------------------------------------------------------- //

    public void mostrarMenuSalas(){
        int opcao = 0;

        do {
            System.out.println("---------------------------------------------");
            System.out.println("MENU SALAS");
            System.out.println(" 1 - Criar");
            System.out.println(" 2 - Excluir");
            System.out.println(" 3 - Editar");
            System.out.println(" 4 - Obter dados");
            System.out.println(" 0 - Voltar");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    criarSala();
                    break;
                case 2:
                    excluirSala();
                    break;
                case 3:
                    editarSala();
                    break;
                case 4:
                    listarSalas();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
            }
            System.out.println("---------------------------------------------");
        }while (opcao != 0);
    }

    public void criarSala(){
        try{

                System.out.println("---------------------------------------------");
                System.out.println("Criar uma sala");
                System.out.println("Informe o nome da sala:");

                Boolean existe = false;
                String nome;

                do {
                    if (existe) {
                        System.out.println("Nome já cadastrado. Tente novamente.");
                    }
                    nome = scanner.nextLine();

                    existe = cinemaService.verificarSala(nome);
                }while (existe);

                Sala sala = new Sala(nome);

                Cinema cinema = cinemaService.lerArquivoCinemas();
                cinema.addSala(sala);

                cinemaService.salvarEmArquivo(cinema, "cinemas.dat");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void excluirSala(){
        try{
            List<Sala> salas = cinemaService.getListaSalas();

            if (!salas.isEmpty()){
                System.out.println("Qual o nome da sala para ser excluída?");
                String nome = scanner.nextLine();

                if(cinemaService.verificarSala(nome)){
                    if(confirmarAcao("excluir sala")){
                        salas = salas.stream()
                                .filter(sala -> !sala.getNome().equals(nome))
                                .collect(Collectors.toList());

                        cinemaService.salvarListaSalas(salas);
                    }
                }else{
                    System.out.println("Essa sala não existe!");
                }
            }

        }catch(ListNotFoundException e){
            e.printStackTrace();
        }
    }

    public void editarSala(){
        try{
            System.out.println("Qual o nome da sala que você deseja editar?");
            String nomeSala = scanner.nextLine();

            if (cinemaService.verificarSala(nomeSala)){
                System.out.println("Digite o novo nome da sala:");
                String novoNome = scanner.nextLine();

                cinemaService.editarSala(nomeSala, novoNome);
            }else{
                System.out.println("Essa sala não existe!");
            }
        }catch (ListNotFoundException e){
            e.printStackTrace();
        }
    }

    public void listarSalas(){
        try{
            List<Sala> salas = cinemaService.getListaSalas();
            System.out.println(salas);
        }catch (ListNotFoundException e){
            e.printStackTrace();
        }
    }

    private boolean confirmarAcao(String acao){
        System.out.println("Você tem certeza que quer " + acao + "?");
        System.out.println("1 - Sim");
        System.out.println("0 - Não");

        int opcao = 0;
        opcao = scanner.nextInt();
        scanner.nextLine();

        if(opcao == 0){
            return false;
        } else if (opcao == 1) {
            return true;
        }else{
            return false;
        }
    }
}






















//        try{
//            File file = new File("arquivo.txt");
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            fileOutputStream.write("Teste de string sendo escrita num .txt".getBytes(StandardCharsets.UTF_8));
//            fileOutputStream.close();
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        try{
//            FileWriter fileWriter = new FileWriter("arquivo.txt");
//            fileWriter.write("Meu deus do ceu arthur silva é meio gayzinho");
//            fileWriter.write("\n");
//            fileWriter.write("Meu deus do ceu arthur silva é meio gayzinho");
//
//            PrintWriter printWriter = new PrintWriter(fileWriter);
//            printWriter.println("Gravado com printWriter");
//
//            printWriter.close();
//            fileWriter.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        try {
//            FileInputStream fileInputStream = new FileInputStream("arquivo.txt");
//            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String linha;
//
//            while ((linha = bufferedReader.readLine()) != null){
//                System.out.println(linha);
//            }
//
//            fileInputStream.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }

//        Cinema cinema = new Cinema(1, "Gnc Cinemas");
//
//        try{
//            FileOutputStream fileOutputStream = new FileOutputStream("cinemas.dat");
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//
//            objectOutputStream.writeObject(cinema);
//
//            objectOutputStream.close();
//            fileOutputStream.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        try{
//            FileInputStream fileInputStream = new FileInputStream("cinemas.dat");
//            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//
//            Object object = objectInputStream.readObject();
//            Cinema c = (Cinema) object;
//
//            System.out.println(c.getNome());
//
//            objectInputStream.close();
//            fileInputStream.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
