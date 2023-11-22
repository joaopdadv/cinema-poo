package org.example;

import org.example.entity.cinema.Cinema;
import org.example.entity.endereco.Endereco;
import org.example.entity.genero.Genero;
import org.example.entity.horario.Horario;
import org.example.entity.pessoas.Pessoa;
import org.example.entity.pessoas.tipos.Ator;
import org.example.entity.pessoas.tipos.Diretor;
import org.example.entity.poltrona.Assento;
import org.example.entity.sala.Sala;
import org.example.exceptions.CinemaNotFoundException;
import org.example.exceptions.SalasNotFoundException;
import org.example.filme.Filme;
import org.example.services.CinemaService;

import javax.xml.transform.Source;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
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
            System.out.println(" 3 - ASSENTOS");
            System.out.println(" 4 - HORÁRIOS");
            System.out.println(" 5 - FILMES");
            System.out.println(" 6 - PESSOAS");
            System.out.println(" 7 - GENEROS");
            System.out.println(" 8 - INGRESSOS");
            System.out.println(" 0 - Sair");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    mostrarMenuCinema();
                    break;
                case 2:
                    if(cinemaService.verificaArquivo("cinemas.dat")) {
                        mostrarMenuSalas();
                    }else{
                        System.out.println("É preciso cadastrar um Cinema!");
                    }
                    break;
                case 3:
                    if(cinemaService.salasCadastradas()){
                        mostrarMenuAssentos();
                    }else{
                        System.out.println("Não existem salas cadastradas no seu cinema!");
                    }
                    break;
//                case 4:
//                    if (cinemaService.salasCadastradas()){
//                        mostrarMenuHorarios();
//                    }else{
//                        System.out.println("Não existem salas cadastradas no seu cinema!");
//                    }
//                    break;
                case 5:
                    mostrarMenuFilmes();
                    break;
                case 6:
                    mostrarMenuPessoas();
                    break;
                case 7:
                    mostrarMenuGeneros();
                    break;
                case 8:
                    mostrarMenuIngressos();
                    break;
                case 0:
                    if(confirmarAcao("sair")){
                        System.out.println("Saindo...");
                        System.exit(0);
                    }
                    opcao = -1;
            }
            System.out.println("---------------------------------------------");
        }while (opcao != 0);
        scanner.close();
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

            if (cinemaService.verificaArquivo("cinemas.dat") && confirmarAcao("excluir cinema")){
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
            if (cinemaService.verificaArquivo("cinemas.dat")){
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
            if (cinemaService.verificaArquivo("cinemas.dat")){
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
            Set<Sala> salas = cinemaService.getHashSetSalas();

            if (!salas.isEmpty()){
                System.out.println("Qual o nome da sala para ser excluída?");
                String nome = scanner.nextLine();

                if(cinemaService.verificarSala(nome)){
                    if(confirmarAcao("excluir sala")){
                        salas = salas.stream()
                                .filter(sala -> !sala.getNome().equals(nome))
                                .collect(Collectors.toSet());

                        cinemaService.salvarListaSalas(salas);
                    }
                }else{
                    System.out.println("Essa sala não existe!");
                }
            }

        }catch(SalasNotFoundException e){
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
        }catch (SalasNotFoundException e){
            e.printStackTrace();
        }
    }

    public void listarSalas(){
        try{
            System.out.println(cinemaService.getHashSetSalas());
        }catch (SalasNotFoundException e){
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------------------------------------------- //
    // --------------------------------------------- ASSENTO -------------------------------------------- //
    // -------------------------------------------------------------------------------------------------- //

    public void mostrarMenuAssentos(){
        int opcao = 0;

        System.out.println("---------------------------------------------");
        System.out.println("Qual o nome da sala que deseja acessar os assentos?");
        String nomeSala = scanner.nextLine();

        Sala sala = cinemaService.getSalaByName(nomeSala);

        if(sala == null){
            return;
        }

        do {
            System.out.println("---------------------------------------------");
            System.out.println("MENU ASSENTOS");
            System.out.println(" 1 - Criar");
            System.out.println(" 2 - Excluir");
            System.out.println(" 3 - Editar");
            System.out.println(" 4 - Obter dados");
            System.out.println(" 0 - Voltar");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    criarAssento(sala);
                    break;
                case 2:
                    excluirAssento(sala);
                    break;
                case 3:
                    editarAssento(sala);
                    break;
                case 4:
                    listarAssentos(sala);
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
            }
            System.out.println("---------------------------------------------");
        }while (opcao != 0);
    }

    public void criarAssento(Sala sala) {
        try {
            System.out.println("Qual a fileira do novo assento?");
            String fileira = scanner.nextLine();

            System.out.println("Qual o número do novo assento?");
            String numero = scanner.nextLine();

            if (cinemaService.verificaAssento(sala, fileira, numero)) {
                System.out.println("Esse assento já existe!");
            } else {
                sala.addAssento(new Assento(fileira, numero));
                cinemaService.salvarSala(sala);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluirAssento(Sala sala){
            System.out.println(sala.getAssentos());

            System.out.println("Qual a fileira do assento a ser excluído?");
            String fileira = scanner.nextLine();

            System.out.println("Qual o número do assento a ser excluído?");
            String numero = scanner.nextLine();

            if(cinemaService.verificaAssento(sala, fileira, numero)){
                cinemaService.removeAssento(sala, fileira, numero);
            }else{
                System.out.println("Esse assento não existe!");
            }
    }

    public void editarAssento(Sala sala){
        System.out.println("Qual a fileira do assento?");
        String fileira = scanner.nextLine();

        System.out.println("Qual o número do assento?");
        String numero = scanner.nextLine();

        if(cinemaService.verificaAssento(sala, fileira, numero)){
            cinemaService.removeAssento(sala, fileira, numero);
            criarAssento(sala);
        }else{
            System.out.println("Esse assento não existe!");
        }
    }

    public void listarAssentos(Sala sala){
        System.out.println(sala.getAssentos());
    }

    // -------------------------------------------------------------------------------------------------- //
    // --------------------------------------------- HORARIOS ------------------------------------------- //
    // -------------------------------------------------------------------------------------------------- //

    public void mostrarMenuHorarios(Filme filme){
        int opcao = 0;
        do {
            System.out.println("---------------------------------------------");
            System.out.println("MENU HORARIOS");
            System.out.println(" 1 - Criar");
            System.out.println(" 2 - Excluir");
            System.out.println(" 3 - Editar");
            System.out.println(" 4 - Obter dados");
            System.out.println(" 0 - Voltar");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    criarHorario(filme);
                    break;
                case 2:
                    excluirHorario(filme);
                    break;
                case 3:
                   editarHorario();
                    break;
                case 4:
                    listarHorarios();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
            }
            System.out.println("---------------------------------------------");
        }while (opcao != 0);
    }

    public void criarHorario(Filme filme){
        System.out.println("Qual o nome da sala que deseja adicionar o horário?");
        String nomeSala = scanner.nextLine();

        Sala sala = cinemaService.getSalaByName(nomeSala);

        if(sala == null){
            return;
        }

        System.out.println("Qual a data do horário? (dd/mm/yyyy)");
        String inputDate = scanner.nextLine();
        System.out.println("Qual a horário? (hh:mm)");
        String inputTime = scanner.nextLine();

        LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalTime time = LocalTime.parse(inputTime, DateTimeFormatter.ofPattern("HH:mm"));

        Horario horario = new Horario(date, time, sala);

        cinemaService.salvarHorario(horario, filme);
    }

    public void excluirHorario(Filme filme){
        listarHorarios();
        System.out.println("-------------------");
        System.out.println("Qual o identificador do horário para ser excluído?");
        int id = scanner.nextInt();
        scanner.nextLine();

        if(confirmarAcao("remover horário")){
            cinemaService.excluirHorario(id, filme);
            System.out.println("Horário excluído com sucesso!");
        }
    }

    public void editarHorario(){
        listarHorarios();
        System.out.println("-------------------");
        System.out.println("Qual o identificador do horário para ser editado?");
        int id = scanner.nextInt();
        scanner.nextLine();

        Map<Integer, Horario> horarioMap = cinemaService.getHorariosFromFilme();
        Horario horario = horarioMap.get(id);

        int opcao = 0;
        do{
            System.out.println("Editar qual campo?");
            System.out.println(" 1 - Data");
            System.out.println(" 2 - Horário");
            System.out.println(" 3 - Sala");
            System.out.println(" 0 - Voltar");
            opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1){
                System.out.println("Nova data:");
                String inputDate = scanner.nextLine();

                horario.setData(LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }else if(opcao == 2){
                System.out.print("Novo Horário:");
                String inputTime = scanner.nextLine();

                horario.setHorario(LocalTime.parse(inputTime, DateTimeFormatter.ofPattern("HH:mm")));
            } else if (opcao == 3) {
                System.out.println("Nova sala:");
                String nomeSala = scanner.nextLine();

                Sala sala = cinemaService.getSalaByName(nomeSala);

                if(sala != null){
                    horario.setSala(sala);
                }else{
                    System.out.println("Essa sala não existe");
                }
            }

            horarioMap.put(id, horario);
            cinemaService.salvarEmArquivo(horarioMap, "horarios.dat");
        }while(opcao != 0);
    }

    public void listarHorarios(){
        for (Map.Entry<Integer, Horario> entry : cinemaService.getHorariosFromFilme().entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }

    // -------------------------------------------------------------------------------------------------- //
    // --------------------------------------------- FILMES --------------------------------------------- //
    // -------------------------------------------------------------------------------------------------- //

    public void mostrarMenuFilmes(){
        int opcao = 0;
        do {
            System.out.println("---------------------------------------------");
            System.out.println("MENU FILMES");
            System.out.println(" 1 - Criar");
            System.out.println(" 2 - Excluir");
            System.out.println(" 3 - Editar");
            System.out.println(" 4 - Obter dados");
            System.out.println(" 0 - Voltar");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    criarFilme();
                    break;
                case 2:
                    excluirFilme();
                    break;
                case 3:
                    editarFilme();
                    break;
                case 4:
                    listarFilmes();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
            }
            System.out.println("---------------------------------------------");
        }while (opcao != 0);
    }

    public void criarFilme(){
        System.out.println("Nome do filme:");
        String nome = scanner.nextLine();

        System.out.println("Ano de lançamento:");
        Integer ano = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Descrição:");
        String desc = scanner.nextLine();

        System.out.println("Duração (minutos):");
        Integer duracao = scanner.nextInt();
        scanner.nextLine();

        Filme filme = new Filme(nome, ano, desc, duracao);

        if(pedeAcao("Adicionar um genero")){
            if(cinemaService.verificaArquivo("generos.dat")){
                Map<Integer, Genero> generosMap = cinemaService.getGenerosFromFile();
                printMap(generosMap);
                Integer id = 0;
                do{
                    System.out.println("Qual o identificador do genero do filme?");
                    id = scanner.nextInt();
                }while (!generosMap.containsKey(id));

                filme.setGenero(generosMap.get(id));
            }else{
                System.out.println("Gêneros não encontrados nos arquivos");
            }
        }


        if(pedeAcao("Adicionar um ator?")){
            if(cinemaService.verificaAtores()){
                Map<Integer, Ator> mapAtores = cinemaService.getMapAtores();
                printMap(mapAtores);
                boolean pedirAtor = true;
                do{
                    System.out.println("Qual o identificador da pessoa para ser adicionada?");
                    Integer id = scanner.nextInt();
                    scanner.nextLine();

                    filme.addAtor(mapAtores.get(id));
                    pedirAtor = pedeAcao("Adicionar mais um ator?");
                }while (pedirAtor);

                System.out.println("Atores adicionados com sucesso!");
            }else{
                System.out.println("Atores não encontrados nos arquivos.");
            }
        }

        if(pedeAcao("Adicionar um diretor?")){
            if(cinemaService.verificaDiretores()){
                Map<Integer, Diretor> mapDiretores = cinemaService.getMapDiretores();
                printMap(mapDiretores);
                boolean pedirDiretor = true;
                do{
                    System.out.println("Qual o identificador da pessoa para ser adicionada?");
                    Integer id = scanner.nextInt();
                    scanner.nextLine();

                    filme.addDiretor(mapDiretores.get(id));
                    pedirDiretor = pedeAcao("adicionar mais um diretor?");
                }while (pedirDiretor);

                System.out.println("Diretores adicionados com sucesso!");
            }else{
                System.out.println("Diretores não encontrados nos arquivos.");
            }
        }

        if (pedeAcao("Adicionar horário?")){
                boolean continuar;
                do{
                    criarHorario(filme);
                    continuar = pedeAcao("adicionar mais um horário?");
                }while(continuar);
        }

        cinemaService.salvarFilme(filme);
    }
    public void excluirFilme(){}
    public void editarFilme(){}
    public void listarFilmes(){
        try{
            System.out.println(cinemaService.lerArquivoCinemas().getFilmes());
        }catch (CinemaNotFoundException e){
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------------------------------------------- //
    // --------------------------------------------- PESSOAS -------------------------------------------- //
    // -------------------------------------------------------------------------------------------------- //

    public void mostrarMenuPessoas(){
        int opcao = 0;
        do {
            System.out.println("---------------------------------------------");
            System.out.println("MENU PESSOAS");
            System.out.println(" 1 - Criar");
            System.out.println(" 2 - Excluir");
            System.out.println(" 3 - Editar");
            System.out.println(" 4 - Obter dados");
            System.out.println(" 0 - Voltar");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    criarPessoa();
                    break;
                case 2:
                    excluirPessoa();
                    break;
                case 3:
                    editarPessoa();
                    break;
                case 4:
                    listarPessoas();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
            }
            System.out.println("---------------------------------------------");
        }while (opcao != 0);
    }

    public void criarPessoa(){
        System.out.println("A pessoa é um Ator ou Diretor?");
        System.out.println("1 - Ator");
        System.out.println("2 - Diretor");
        System.out.println("0 - Voltar");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Nome:");
        String nome = scanner.nextLine();

        System.out.println("País de origem:");
        String pais = scanner.nextLine();

        Pessoa pessoa;
        if(opcao == 1){
            pessoa = new Ator(nome, pais);
        }else if(opcao == 2){
            pessoa = new Diretor(nome, pais);
        }else{
            return;
        }

        cinemaService.salvarPessoa(pessoa);
    }
    public void excluirPessoa(){
        listarHorarios();
        System.out.println("-------------------");
        System.out.println("Qual o identificador da pessoa para ser excluída?");
        int id = scanner.nextInt();
        scanner.nextLine();

        if(confirmarAcao("remover pessoa")){
            cinemaService.excluirPessoa(id);
            System.out.println("Pessoa excluída com sucesso!");
        }
    }
    public void editarPessoa() {
        listarPessoas();
        System.out.println("-------------------");
        System.out.println("Qual o identificador da pessoa para ser editada?");
        int id = scanner.nextInt();
        scanner.nextLine();

        Map<Integer, Pessoa> pessoasMap = cinemaService.getPessoasFromFile();
        Pessoa pessoa = pessoasMap.get(id);
        int opcao = 0;
        do {
            System.out.println("Editar qual campo?");
            System.out.println(" 1 - Nome");
            System.out.println(" 2 - País de Origem");
            System.out.println(" 0 - Voltar");
            opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                System.out.println("Novo nome:");
                String nome = scanner.nextLine();

                pessoa.setNome(nome);
            } else if (opcao == 2) {
                System.out.println("Novo país:");
                String pais = scanner.nextLine();

                pessoa.setPaisOrigem(pais);
            }

            pessoasMap.put(id, pessoa);
            cinemaService.salvarEmArquivo(pessoasMap, "pessoas.dat");
        } while (opcao != 0);
    }
    public void listarPessoas(){
        printMap(cinemaService.getPessoasFromFile());
    }

    // -------------------------------------------------------------------------------------------------- //
    // --------------------------------------------- GENEROS -------------------------------------------- //
    // -------------------------------------------------------------------------------------------------- //

    public void mostrarMenuGeneros(){
        int opcao = 0;
        do {
            System.out.println("---------------------------------------------");
            System.out.println("MENU GÊNEROS");
            System.out.println(" 1 - Criar");
            System.out.println(" 2 - Excluir");
            System.out.println(" 3 - Editar");
            System.out.println(" 4 - Obter dados");
            System.out.println(" 0 - Voltar");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    criarGenero();
                    break;
                case 2:
                    excluirGenero();
                    break;
                case 3:
                    editarGenero();
                    break;
                case 4:
                    listarGeneros();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
            }
            System.out.println("---------------------------------------------");
        }while (opcao != 0);
    }

    public void criarGenero(){
        System.out.println("nome do gênero:");
        String nome = scanner.nextLine();

        cinemaService.salvarGenero(new Genero(nome));
    }
    public void excluirGenero(){

    }
    public void editarGenero() {
        listarGeneros();

        System.out.println("Qual o identificador do genero para ser editado?");
        Integer id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Novo nome:");
        String nome = scanner.nextLine();

        Genero genero = new Genero(nome);

        cinemaService.editarGenero(id, genero);
    }
    public void listarGeneros(){
        printMap(cinemaService.getGenerosFromFile());
    }

    // -------------------------------------------------------------------------------------------------- //
    // --------------------------------------------- INGRESSOS ------------------------------------------ //
    // -------------------------------------------------------------------------------------------------- //

    public void mostrarMenuIngressos(){
        int opcao = 0;
        do {
            System.out.println("---------------------------------------------");
            System.out.println("MENU INGRESSOS");
            System.out.println(" 1 - Criar");
            System.out.println(" 2 - Obter dados");
            System.out.println(" 0 - Voltar");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    criarIngresso();
                    break;
                case 2:
                    listarIngressos();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
            }
            System.out.println("---------------------------------------------");
        }while (opcao != 0);
    }

    public void criarIngresso(){}
    public void listarIngressos(){}

    private boolean confirmarAcao(String acao) {
        System.out.println("Você tem certeza que quer " + acao + "?");
        System.out.println("1 - Sim");
        System.out.println("0 - Não");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        return opcao == 1;
    }

    private boolean pedeAcao(String acao) {
        System.out.println("Você quer " + acao + "?");
        System.out.println("1 - Sim");
        System.out.println("0 - Não");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        return opcao == 1;
    }

    private static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}