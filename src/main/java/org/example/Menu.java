package org.example;

import org.example.entity.cinema.Cinema;
import org.example.entity.endereco.Endereco;
import org.example.entity.genero.Genero;
import org.example.entity.horario.Horario;
import org.example.entity.ingresso.Ingresso;
import org.example.entity.pessoas.Pessoa;
import org.example.entity.pessoas.tipos.Ator;
import org.example.entity.pessoas.tipos.Diretor;
import org.example.entity.poltrona.Assento;
import org.example.entity.sala.Sala;
import org.example.exceptions.SalasNotFoundException;
import org.example.filme.Filme;
import org.example.services.CinemaService;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
            System.out.println(" 4 - FILMES");
            System.out.println(" 5 - PESSOAS");
            System.out.println(" 6 - GENEROS");
            System.out.println(" 7 - INGRESSOS");
            System.out.println(" 8 - CONSULTAS");
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
                case 4:
                    mostrarMenuFilmes();
                    break;
                case 5:
                    mostrarMenuPessoas();
                    break;
                case 6:
                    mostrarMenuGeneros();
                    break;
                case 7:
                    mostrarMenuIngressos();
                    break;
                case 8:
                    mostrarMenuConsultas();
                    break;
                case 0:
                    if(confirmarAcao("sair")){
                        System.out.println("Saindo...");
                        scanner.close();
                        System.exit(0);
                    }
                    opcao = -1;
            }
            System.out.println("---------------------------------------------");
        }while (true);
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

                boolean existe = false;
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
                listarSalas();

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

            listarSalas();

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
        listarSalas();
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
        System.out.println(sala.getAssentos());
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
    // -------------------------------------------------------------------------------------------------- //ß

    public Filme criarHorario(Filme filme){
        listarSalas();

        System.out.println("Qual o nome da sala que deseja adicionar o horário?");
        String nomeSala = scanner.nextLine();

        Sala sala = cinemaService.getSalaByName(nomeSala);

        if(sala == null){
            System.out.println("Sala inválida");
            return filme;
        }

        System.out.println("Qual a data do horário? (dd/mm/yyyy)");
        String inputDate = scanner.nextLine();
        System.out.println("Qual a horário? (hh:mm)");
        String inputTime = scanner.nextLine();

        LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalTime time = LocalTime.parse(inputTime, DateTimeFormatter.ofPattern("HH:mm"));

        Horario horario = new Horario(date, time, sala);

        filme.addHorario(horario);
        return filme;
    }

    public void excluirHorario(Filme filme){
        Map<Integer, Horario> map = listarHorarios(filme);
        System.out.println("-------------------");
        System.out.println("Qual o identificador do horário para ser excluído?");
        int id = scanner.nextInt();
        scanner.nextLine();

        if(confirmarAcao("remover horário")){
            cinemaService.excluirHorario(id, filme, map);
            System.out.println("Horário excluído com sucesso!");
        }
    }

    public void editarHorario(Integer idFilme, Filme filme){
        listarHorarios(filme);
        System.out.println("-------------------");
        System.out.println("Qual o identificador do horário para ser editado?");
        int id = scanner.nextInt();
        scanner.nextLine();

        Map<Integer, Horario> horarioMap = filme.getHorarios();
        if(!horarioMap.containsKey(id)){
            System.out.println("Id inválido");
            return;
        }
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
        }while(opcao != 0);

        cinemaService.editarHorario(idFilme, id, horario, filme);
    }

    public Map<Integer, Horario> listarHorarios(Filme filme){
        Map<Integer, Horario> map = cinemaService.getHorariosFromFilme(filme);
        for (Map.Entry<Integer, Horario> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        return map;
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
                Map<Integer, Genero> generosMap = cinemaService.getGenerosMapFromFile();
                printMap(generosMap);
                Integer id;
                if(generosMap.isEmpty()){
                    System.out.println("Nenhum genero cadastrado!");
                }else{
                    do{
                        System.out.println("Qual o identificador do genero do filme?");
                        id = scanner.nextInt();
                    }while (!generosMap.containsKey(id));

                    filme.setGenero(generosMap.get(id));
                }
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

                    if(mapAtores.containsKey(id)){
                        filme.addAtor(mapAtores.get(id));
                    }else{
                        System.out.println("Esse identificador não existe");
                    }

                    pedirAtor = pedeAcao("Adicionar mais um ator");
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

                    if (mapDiretores.containsKey(id)){
                        filme.addDiretor(mapDiretores.get(id));
                    }else{
                        System.out.println("Esse identificador não existe");
                    }

                    pedirDiretor = pedeAcao("adicionar mais um diretor");
                }while (pedirDiretor);

                System.out.println("Diretores adicionados com sucesso!");
            }else{
                System.out.println("Diretores não encontrados nos arquivos.");
            }
        }

        if (pedeAcao("Adicionar horário?")){
                boolean continuar;
                do{
                    filme = criarHorario(filme);
                    continuar = pedeAcao("adicionar mais um horário?");
                }while(continuar);
        }

        cinemaService.salvarFilme(filme);
    }
    public void excluirFilme(){
        printMap(cinemaService.getFilmesMap());
        System.out.println("Qual o nome do filme para ser excluído?");
        Integer id = scanner.nextInt();
        scanner.nextLine();

        if(confirmarAcao("excluir filme")){
            cinemaService.excluirFilme(id);
        }
    }
    public void editarFilme(){

        int opcao = 0;

        Map<Integer, Filme> filmesMap = cinemaService.getFilmesMap();
        printMap(filmesMap);

        System.out.println("Qual o identificador do filme para ser editado?");
        Integer id = scanner.nextInt();
        scanner.nextLine();

        Filme filme;
        if(filmesMap.containsKey(id)){
            filme = filmesMap.get(id);
        }else{
            System.out.println("Identificador inválido");
            return;
        }

        do{
            System.out.println("O Que você quer editar?");
            System.out.println("1 - Nome");
            System.out.println("2 - Ano");
            System.out.println("3 - Descrição");
            System.out.println("4 - Duração");
            System.out.println("5 - Genero");
            System.out.println("6 - Adicionar Ator");
            System.out.println("7 - Remover Ator");
            System.out.println("8 - Adicionar Diretor");
            System.out.println("9 - Remover Diretor");
            System.out.println("10 - Adicionar Horário");
            System.out.println("11 - Remover Horário");
            System.out.println("12 - Editar Horário");
            System.out.println("0 - Voltar");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    System.out.println("Novo nome:");
                    String nome = scanner.nextLine();

                    filme.setNome(nome);
                    break;
                case 2:
                    System.out.println("Nova descrição:");
                    Integer ano = scanner.nextInt();
                    scanner.nextLine();

                    filme.setAno(ano);
                    break;
                case 3:
                    System.out.println("Nova descrição:");
                    String desc = scanner.nextLine();

                    filme.setDescricao(desc);
                    break;
                case 4:
                    System.out.println("Nova duração:");
                    Integer dur = scanner.nextInt();
                    scanner.nextLine();

                    filme.setAno(dur);
                    break;
                case 5:
                    if (cinemaService.getGenerosMapFromFile().isEmpty()){
                        System.out.println("Nenhum genero cadastrado.");
                        break;
                    }
                    printMap(cinemaService.getGenerosMapFromFile());
                    System.out.println("Identificador do novo gênero:");
                    Integer idGenero = scanner.nextInt();
                    scanner.nextLine();

                    filme.setGenero(cinemaService.getGenerosMapFromFile().get(idGenero));
                    break;
                case 6:
                    if (cinemaService.getMapAtores().isEmpty()){
                        System.out.println("Nenhum ator cadastrado.");
                        break;
                    }
                    printMap(cinemaService.getMapAtores());
                    System.out.println("Identificador do novo ator:");
                    Integer idNovoAtor = scanner.nextInt();
                    scanner.nextLine();

                    filme.addAtor(cinemaService.getMapAtores().get(idNovoAtor));
                    break;
                case 7:
                    Map<Integer, Ator> mapAtores = cinemaService.getMapAtores();

                    printMap(mapAtores);
                    System.out.println("Identificador do ator para ser removido:");
                    Integer idAtor = scanner.nextInt();
                    scanner.nextLine();

                    if (mapAtores.containsKey(idAtor)) {
                        Ator ator = mapAtores.get(idAtor);

                        Set<Ator> atorSet = new HashSet<>();
                        for (Ator a: filme.getAtores()){
                            if(!a.equals(ator)){
                                atorSet.add(a);
                            }
                        }

                        filme.setAtores(atorSet);
                        System.out.println("Ator removido com sucesso.");
                    } else {
                        System.out.println("Ator com o identificador fornecido não encontrado.");
                    }
                    break;
                case 8:
                    if (cinemaService.getMapDiretores().isEmpty()){
                        System.out.println("Nenhum diretor cadastrado.");
                        break;
                    }
                    printMap(cinemaService.getMapDiretores());
                    System.out.println("Identificador do novo diretor:");
                    Integer idNovoDiretor = scanner.nextInt();
                    scanner.nextLine();

                    filme.addDiretor(cinemaService.getMapDiretores().get(idNovoDiretor));
                    break;
                case 9:
                    Map<Integer, Diretor> mapDiretores = cinemaService.getMapDiretores();

                    printMap(mapDiretores);
                    System.out.println("Identificador do diretor para ser removido:");
                    Integer idDiretor = scanner.nextInt();
                    scanner.nextLine();

                    if (mapDiretores.containsKey(idDiretor)) {
                        Diretor diretor = mapDiretores.get(idDiretor);

                        Set<Diretor> diretorSet = new HashSet<>();
                        for (Diretor d: filme.getDiretores()){
                            if(!d.equals(diretor)){
                                diretorSet.add(d);
                            }
                        }

                        filme.setDiretores(diretorSet);
                        System.out.println("Diretor removido com sucesso.");
                    } else {
                        System.out.println("Diretor com o identificador fornecido não encontrado.");
                    }
                    break;
                case 10:
                    criarHorario(filme);
                    break;
                case 11:
                    excluirHorario(filme);
                    break;
                case 12:
                    editarHorario(id, filme);
                    break;
            }
        }while (opcao != 0);

        cinemaService.editarFilme(id, filme);
    }
    public void listarFilmes(){
        printMap(cinemaService.getFilmesMap());
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
            System.out.println(" 5 - Casar");
            System.out.println(" 6 - Divorciar");
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
                case 5:
                    casarPessoas();
                    break;
                case 6:
                    divorciarPessoas();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
            }
            System.out.println("---------------------------------------------");
        }while (opcao != 0);
    }

    public void casarPessoas(){
        Map<Integer, Pessoa> pessoasMap = cinemaService.getPessoasFromFile();
        printMap(pessoasMap);

        System.out.println("Identificdor da primeira pessoa:");
        Integer id1 = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Identificdor da segunda pessoa:");
        Integer id2 = scanner.nextInt();
        scanner.nextLine();


        Pessoa pessoa1 = pessoasMap.get(id1);
        Pessoa pessoa2 = pessoasMap.get(id2);

        pessoa1.casar(pessoa2);
        pessoa2.casar(pessoa1);

        pessoasMap.put(id1, pessoa1);
        pessoasMap.put(id2, pessoa2);

        cinemaService.salvarEmArquivo(pessoasMap, "pessoas.dat");
    }

    public void divorciarPessoas(){
        Map<Integer, Pessoa> pessoasMap = cinemaService.getPessoasFromFile();
        printMap(pessoasMap);

        System.out.println("Identificdor da pessoa:");
        Integer id1 = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Identificdor da segunda pessoa:");
        Integer id2 = scanner.nextInt();
        scanner.nextLine();

        Pessoa pessoa1 = pessoasMap.get(id1);
        Pessoa pessoa2 = pessoasMap.get(id2);

        pessoa1.divorciar();
        pessoa2.divorciar();

        pessoasMap.put(id1, pessoa1);
        pessoasMap.put(id2, pessoa2);

        cinemaService.salvarEmArquivo(pessoasMap, "pessoas.dat");
    }


    public void criarPessoa(){
        System.out.println("A pessoa é um Ator ou Diretor?");
        System.out.println("1 - Ator");
        System.out.println("2 - Diretor");
        System.out.println("0 - Voltar");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        if(opcao != 1 && opcao != 2){
            return;
        }

        System.out.println("Nome:");
        String nome = scanner.nextLine();

        System.out.println("País de origem:");
        String pais = scanner.nextLine();

        Pessoa pessoa;
        if(opcao == 1){
            pessoa = new Ator(nome, pais);
        }else{
            pessoa = new Diretor(nome, pais);
        }

        cinemaService.salvarPessoa(pessoa);
    }
    public void excluirPessoa(){
        listarPessoas();
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
        listarGeneros();

        System.out.println("Qual o identificador do gênero para ser excluido?");
        Integer id = scanner.nextInt();
        scanner.nextLine();

        cinemaService.excluirGenero(id);
    }
    public void editarGenero() {
        listarGeneros();

        System.out.println("Qual o identificador do genero para ser editado?");
        Integer id = scanner.nextInt();
        scanner.nextLine();

        if(!cinemaService.verificaGenero(id)){
            System.out.println("Gênero inválido");
            return;
        }

        System.out.println("Novo nome:");
        String nome = scanner.nextLine();

        Genero genero = new Genero(nome);

        cinemaService.editarGenero(id, genero);
    }
    public void listarGeneros(){
        printMap(cinemaService.getGenerosMapFromFile());
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

    public void criarIngresso(){

        Map<Integer, Filme> filmeMap = cinemaService.getFilmesMap();
        printMap(filmeMap);
        System.out.println("Qual identificador do filme?");
        Integer idFilme = scanner.nextInt();
        scanner.nextLine();

        if(!filmeMap.containsKey(idFilme)){
            System.out.println("Id inválido");
            return;
        }

        Filme filme = filmeMap.get(idFilme);

        Map<Integer, Horario> horarioMap = filme.getHorarios();
        printMap(horarioMap);
        System.out.println("Qual identificador do horário?");
        Integer idHorario = scanner.nextInt();
        scanner.nextLine();

        if(!horarioMap.containsKey(idHorario)){
            System.out.println("Id inválido");
            return;
        }

        Horario horario = horarioMap.get(idHorario);

        Map<Integer, Assento> assentoMap = cinemaService.getAssentosMap(horario);
        printMap(assentoMap);
        System.out.println("Qual identificador do assento?");
        Integer idAssento = scanner.nextInt();
        scanner.nextLine();

        if(!assentoMap.containsKey(idAssento)){
            System.out.println("Id inválido");
            return;
        }

        Assento assento = assentoMap.get(idAssento);

//        if(cinemaService.verificaAssentoVendido(assento, horario)){
//            System.out.println("Esse assento já está vendido para este horario!");
//            return;
//        }

        System.out.println("Nome do comprador:");
        String nome = scanner.nextLine();

        System.out.println("Telefone do comprador:");
        String telefone = scanner.nextLine();

        System.out.println("Preço:");
        Double preco = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Meia entrada? 1 - Sim | 0 - Não");
        Integer meia = scanner.nextInt();
        scanner.nextLine();

        boolean meiaBool;
        if(meia == 1){
            meiaBool = true;
        }else{
            meiaBool = false;
        }

        Ingresso ingresso = new Ingresso(nome, telefone, preco, meiaBool, assento, horario, filme);

        cinemaService.salvarIngresso(ingresso);
    }

    public void listarIngressos(){
        System.out.println(cinemaService.getIngressosFromFile());
    }

    // -------------------------------------------------------------------------------------------------- //
    // --------------------------------------------- CONSULTAS ------------------------------------------ //
    // -------------------------------------------------------------------------------------------------- //

    public void mostrarMenuConsultas(){
        int opcao = 0;
        do {
            System.out.println("---------------------------------------------");
            System.out.println("MENU CONSULTAS");
            System.out.println(" 1 - Consultar filme por nome");
            System.out.println(" 2 - Ingressos vendidos");
            System.out.println(" 0 - Voltar");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    filmePorNome();
                    break;
                case 2:
                    filmesPorIngressos();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
            }
            System.out.println("---------------------------------------------");
        }while (opcao != 0);
    }

    public void filmePorNome(){
        System.out.println("Palavra chave para pesquisa: ");
        String palavra = scanner.nextLine();

        Map<Integer, String> filmesMap = cinemaService.findFilmesPorPalavraChave(palavra);

        if(filmesMap.isEmpty()){
            System.out.println("Nenhum filme encontrado com a palavra chave: " + palavra);
            return;
        }

        printMap(filmesMap);

        System.out.println("Qual o identificador do filme para ser acessado?");
        Integer id = scanner.nextInt();
        scanner.nextLine();

        if(!filmesMap.containsKey(id)){
            System.out.println("Id inválido");
            return;
        }

        System.out.println(cinemaService.getFilmeByName(filmesMap.get(id)));

        System.out.println("Clique ENTER para continuar...");
        scanner.nextLine();
    }

    public void filmesPorIngressos(){
        Map<String, Integer> map = cinemaService.filmesPorIngressos();

        printMapFilmesDecrescente(map);
    }

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

    private static <K, V> void printMapFilmesDecrescente(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue() + " ingressos");
        }
    }
}