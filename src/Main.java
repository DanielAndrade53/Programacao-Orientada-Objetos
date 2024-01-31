import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    private static final Scanner input = new Scanner(System.in);

    private static LocalDateTime tempo = LocalDateTime.now();
    private static final DateTimeFormatter formatar1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter formatar2 = DateTimeFormatter.ofPattern("yyyy-MM-dd (HH:mm:ss");

    public static VintageDataFile datafile;
    public static VintageData data;
    private static Utilizadores user;

    public static void main(String[] args) {
        tempo = LocalDateTime.now();
        user = null;
        data = new VintageData();
        // C:\Users\danie\IdeaProjects\ProjetoVintage\vintage_data.res
        datafile = new VintageDataFile("vintage_data.res");
        if(!datafile.getVintageData().equals(new VintageData())) {
            data = datafile.getVintageData();
        }
        datafile.printVintageData();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n> A salvar a data antes de encerrar o programa...");
            datafile.setVintageData(data);
        }));
        inicio();
        input.close();
    }

    public static void inicio() {
        user=null;
        System.out.println("\n[Bem-vindo]");
        System.out.println("(1) Dar login com uma conta existente.");
        System.out.println("(2) Criar uma nova conta.");
        System.out.println("(3) Estatisticas Vintage.");
        System.out.println("(4) Adicioar/Remover transportadoras");
        System.out.println("(5) Adicioar/Remover backups");
        int opcao = input.nextInt();
        switch (opcao) {
            case 1 -> login();
            case 2 -> registo();
            case 3 -> estatisticasVintage();
            case 4 -> gerirTransportadoras();
            case 5 -> gerirBackups();
        }
    }

    public static void login() {
        System.out.println("\n[Login]");
        System.out.print("> Insira o seu email: ");
        String email = input.next();
        Utilizadores utilizador =data.findUserByEmail(email);
        if (utilizador == null) {
            System.out.println("> Não encontramos nenhuma conta associada a esse email!");
            inicio();
            return;
        }
        user = utilizador;
        System.out.println("[Bem-Vindo]");
        perfil();

        // o professor das praticas disse que nao havia problemas se tivessemos uma exception aqui no login()
    }

    public static void registo() {
        System.out.println("\n[Registo]");
        System.out.print("> Insira um email: ");
        String email = input.next();
        if (data.filterEmailExists(email)) {
            System.out.println("> Esse email ja tem uma conta associada!");
            inicio();
            return;
        }
        System.out.print("> Insira um nome: ");
        String nome = input.next();
        System.out.print("> Insira um morada: ");
        input.nextLine();
        String morada = input.nextLine();
        System.out.print("> Insira um nif: ");
        long nif = input.nextLong();
        System.out.print("> Insira o tipo de utilizador (comprador, vendedor, ambos): ");
        Utilizadores.Tipo tipo = Utilizadores.Tipo.valueOf(input.next());
        user = new Utilizadores(email, nome, morada, nif, tipo);
        data.addUtilizador(user);
        System.out.println("> Conta criada com sucesso, bem-vindo!");
        perfil();
    }

    public static void perfil() {
        System.out.println("\n[Perfil] " + formatar1.format(tempo));
        System.out.println("(1) Ver todos os produtos listados no site.");
        System.out.println("(2) Ver o teu carrinho.");
        System.out.println("(3) Ver o teu stock de produtos.");
        System.out.println("(4) Ver os produtos que ja vendeste.");
        System.out.println("(5) Ver os produtos que ja compraste.");
        System.out.println("(6) Adicionar um novo produto a loja.");
        System.out.println("(7) Manipular horario (salto no tempo).");
        System.out.println("(8) Estado das encomendas.");
        System.out.println("(9) Perfil do utilizador.");
        System.out.println("(0) Terminar sessao.");
        int opcao = input.nextInt();
        switch (opcao) {
            case 1 -> listados();
            case 2 -> carrinho();
            case 3 -> stock();
            case 4 -> vendidos();
            case 5 -> comprados();
            case 6 -> adicionar();
            case 7 -> manipular();
            case 8 -> estadoEncomendas();
            case 9 -> perfilUtilizador();
            case 0 -> {
                System.out.println("> Esperemos novamente pela sua companhia, obrigado por comprar connosco!");
                inicio();
            }
        }
    }

    public static void listados() {
        System.out.println("\n[Produtos]");
        data.showArtigosLoja();
        System.out.println("(0) Sair para o perfil");
        int codigo = input.nextInt();
        if (codigo == 0) {
            perfil();
            return;
        }
        Artigos artigo = data.getArtigoByCodigo(codigo, 1);
        if (artigo == null) {
            System.out.println("> Código inválido!");
            listados();
            return;
        }
        System.out.println("\n" + artigo);
        System.out.println("(1) Adicionar ao carrinho");
        System.out.println("(2) Voltar");
        int opcao = input.nextInt();
        switch (opcao) {
            case 1 -> {
                Utilizadores u = data.findUserByArtigoStock(artigo);
                u.addArtigosVendidos(artigo);
                u.removeArtigoStock(artigo);
                user.addArtigoComprados(artigo);
                criarEncomenda(artigo);
            }
            case 2 -> listados();
            default -> System.out.println("> Escolha inválida!");
        }
        perfil();
    }

    public static void carrinho() {
        double soma=0;
        if (user.getTipo().equals(Utilizadores.Tipo.vendedor)) {
            System.out.println("> Apenas contas de tipo comprador podem utilizar esta funçao!");
            perfil();
            return;
        }
        System.out.println("\n[Carrinho]");
        for(Encomendas enc : user.getCarrinho()) {
            System.out.printf("(%d) %s\n", user.getCarrinho().indexOf(enc)+2, enc.getTransportadora().getNome());
            enc.getArtigos().forEach(artigo -> System.out.printf("  - %s\n", artigo.getMarca()));
        }
        System.out.println("(1) Finalizar compra");
        System.out.println("(0) Fechar carrinho");
        int codigo = input.nextInt();
        if (codigo == 0) {
            perfil();
            return;
        }
        if (codigo == 1) {
            List<Encomendas> toRemove = new ArrayList<>();
            user.getCarrinho().forEach(enc -> {
                enc.setEstado(Encomendas.Estado.finalizada);
                data.getFinalizdas().add(enc);
                toRemove.add(enc);
            });
            for (Encomendas enc : user.getCarrinho()) {
                soma += precoEncomenda(enc);
                enc.getTransportadora().addlucro();
            }
            user.getCarrinho().removeAll(toRemove);
            System.out.println("[Preco]");
            System.out.println("O preco total a pagar é: " +  soma);
            perfil();
        }
        Encomendas encomenda = getEncomendaByCodigo(user.getCarrinho(), codigo, 2);
        if (encomenda == null) {
            System.out.println("> Código inválido!");
            perfil();
            return;
        }
        encomenda.getArtigos().forEach(artigo -> System.out.printf("(%d) %s - %s$\n", encomenda.getArtigos().indexOf(artigo) + 1, artigo.getMarca(), artigo.precoFinal()));
        System.out.println("(0) Voltar");
        int codigo2 = input.nextInt();
        if (codigo2 == 0) {
            carrinho();
            return;
        }
        Artigos artigo = data.getArtigoByCodigo(codigo2, 1);
        if (artigo == null) {
            System.out.println("> Código inválido!");
            perfil();
            return;
        }
        System.out.println(artigo);
        System.out.println("(1) Remover do carrinho");
        System.out.println("(2) Voltar");
        int opcao = input.nextInt();
        switch (opcao) {
            case 1 -> {
                Utilizadores u = data.findUserByArtigoVendidos(artigo);
                u.addArtigoStock(artigo);
                user.removeArtigoComprados(artigo);
                removerDoCarrinho(artigo);
            }
            case 2 -> carrinho();
            default -> System.out.println("> Escolha inválida!");
        }
        perfil();
    }

    public static void stock() {
        if (user.getTipo().equals(Utilizadores.Tipo.comprador)) {
            System.out.println("> Apenas contas de tipo vendedor podem utilizar esta funçao!");
            perfil();
            return;
        }
        System.out.println("\n[Stock]");
        user.getStock().forEach(artigo -> System.out.printf("(%d) %s - %s$\n", user.getStock().indexOf(artigo) + 1, artigo.getMarca(), artigo.getPrecoBase()));
        System.out.println("(0) Sair para o perfil");
        int codigo = input.nextInt();
        if (codigo == 0) {
            perfil();
            return;
        }
        Artigos artigo = getArtigoByCodigo(user.getStock(), codigo, 1);
        if (artigo == null) {
            System.out.println("> Código inválido!");
            perfil();
            return;
        }
        System.out.println("\n" + artigo);
        System.out.println("(1) Alterar preco");
        System.out.println("(2) Alterar desconto");
        System.out.println("(3) Alterar descricao");
        System.out.println("(4) Voltar");
        int opcao = input.nextInt();
        switch (opcao) {
            case 1 -> {
                System.out.print("> Insira o novo preco: ");
                artigo.setPrecoBase(input.nextDouble());
                System.out.println("> Preco atualizado com sucesso!");
                stock();
            }
            case 2 -> {
                System.out.print("> Insira o novo desconto: ");
                artigo.setDesconto(input.nextInt());
                System.out.println("> Preco atualizado com sucesso!");
                stock();
            }
            case 3 -> {
                System.out.print("> Insira uma novo descricao: ");
                artigo.setDescricao(input.next());
                System.out.println("> Descricao atualizada com sucesso!");
                stock();
            }
            case 4 -> stock();
            default -> System.out.println("> Escolha inválida!");
        }
        perfil();
    }

    public static void vendidos() {
        if (user.getTipo().equals(Utilizadores.Tipo.comprador)) {
            System.out.println("> Apenas contas de tipo vendedor podem utilizar esta funçao!");
            perfil();
            return;
        }
        System.out.println("\n[Vendas]");
        user.getVendidos().forEach(artigo -> System.out.printf("(%d) %s - %s$\n", user.getVendidos().indexOf(artigo) + 1, artigo.getMarca(), artigo.getPrecoBase()));
        System.out.println("(0) Sair para o perfil");
        int codigo = input.nextInt();
        if (codigo == 0) {
            perfil();
            return;
        }
        Artigos artigo = getArtigoByCodigo(user.getVendidos(), codigo, 1);
        if (artigo == null) {
            System.out.println("> Código inválido!");
            perfil();
            return;
        }
        System.out.println("\n" + artigo);
        System.out.println("(1) Voltar");
        if (input.nextInt() == 1) vendidos();
        System.out.println("> Escolha inválida!");
        perfil();
    }

    public static void comprados() {
        if (user.getTipo().equals(Utilizadores.Tipo.vendedor)) {
            System.out.println("> Apenas contas de tipo comprador podem utilizar esta funçao!");
            perfil();
            return;
        }
        System.out.println("\n[Compras]");
        user.getComprados().forEach(artigo -> System.out.printf("(%d) %s - %s$\n", user.getComprados().indexOf(artigo) + 1, artigo.getMarca(), artigo.getPrecoBase()));
        System.out.println("(0) Sair para o perfil");
        int codigo = input.nextInt();
        if (codigo == 0) {
            perfil();
            return;
        }
        Artigos artigo = getArtigoByCodigo(user.getComprados(), codigo, 1);
        if (artigo == null) {
            System.out.println("> Código inválido!");
            perfil();
            return;
        }
        System.out.println("\n" + artigo);
        System.out.println("(1) Voltar");
        if (input.nextInt() == 1) comprados();
        System.out.println("> Escolha inválida!");
        perfil();
    }

    public static void adicionar() {
        int estado = 100, donos = 0;
        if (user.getTipo().equals(Utilizadores.Tipo.comprador)) {
            System.out.println("> Apenas contas de tipo vendedor podem utilizar esta funçao!");
            perfil();
            return;
        }
        System.out.println("\n[Novo produto]");
        System.out.print("> Produto novo (true, false): ");
        boolean novo = input.nextBoolean();
        if (!novo) {
            System.out.print("> Condicao do artigo (0-100): ");
            estado = input.nextInt();
            System.out.print("> Numero de donos: ");
            donos = input.nextInt();
        }
        System.out.print("> Descricao do produto: ");
        input.nextLine();
        String descricao = input.nextLine();
        System.out.print("> Marca do produto: ");
        String marca = input.nextLine();
        System.out.print("> Preco do produto: ");
        double preco = input.nextDouble();
        System.out.print("> Desconto para o produto: ");
        int desconto = input.nextInt();
        Transportadoras transp = escolherTransportadora();
        System.out.print("> Tipo de produto (Sapatilhas, TShirts, Malas, Outros): ");
        String option = input.next();
        switch (option) {
            case "Sapatilhas" -> criarSapatilhas(novo, estado, donos, descricao, marca, preco, desconto, transp);
            case "TShirts" -> criarTshirts(novo, estado, donos, descricao, marca, preco, desconto, transp);
            case "Malas" -> criarMalas(novo, estado, donos, descricao, marca, preco, desconto, transp);
            case "Outros" -> criarOutros(novo, estado, donos, descricao, marca, preco, desconto, transp);
            default -> System.out.println("> Escolha inválida!");
        }
        perfil();
    }

    public static void manipular() {
        System.out.println("\n[Manipular]");
        System.out.print("> Indique o número de dias que pretende avançar: ");
        tempo = tempo.plusDays(input.nextLong());
        data.fixEncomendasTempo(tempo);
        perfil();
    }

    public static void estadoEncomendas() {
        System.out.println("\n[Encomendas]");
        System.out.println("Pendentes: " + data.getPendentes());
        System.out.println("Finalizadas: " + data.getFinalizdas());
        System.out.println("Expedidas: " + data.getExpedidas());
        System.out.println("CARRINHO : " + user.getCarrinho());
        System.out.println("(1) Devolver encomenda");
        System.out.println("(0) Voltar");
        int opcao = input.nextInt();
        switch (opcao) {
            case 0 -> perfil();
            case 1 -> data.devolverEncomenda();
            default -> System.out.println("> Escolha inválida!");
        }
        perfil();
    }

    public static void perfilUtilizador() {
        System.out.println(user);
        perfil();
    }

    public static void estatisticasVintage() {
        System.out.println("(1) Transportadora com maior volume de facturação");
        System.out.println("(2) Encomendas emitidas por um vendedor");
        System.out.println("(3) Ranking de compradores/vendedores");
        System.out.println("(4) Facturação da Vintage");
        System.out.println("(0) Voltar");
        int opcao = input.nextInt();
        switch (opcao) {
            case 1 -> transportadoraFaturacao();
            case 2 -> encomendasVendedor();
            case 3 -> ranking();
            case 4 -> vintageFaturacao();
            case 0 -> inicio();
        }
    }

    public static void transportadoraFaturacao() {
        Transportadoras transportadora = data.getTransportadoras().stream()
                .max(Comparator.comparing(Transportadoras::getLucroTotal))
                .orElse(null);
        if(transportadora == null){
            System.out.println("\n[Transportadora] Nao existe uma transportadora com maior lucro.");
        }else{
            double lucro = transportadora.getLucroTotal();
            System.out.println("\n> Transportadora: " + transportadora.getNome() + " - lucro: " + lucro);
        }
        estatisticasVintage();
    }

    public static void encomendasVendedor() {
        for (Utilizadores u : data.getUtilizadores()) {
            System.out.println("\n[Utiliador]" + u.getNome());
            for (Artigos art : u.getVendidos()){
                System.out.println("Artigo: " + art.getMarca());
            }
        }
        estatisticasVintage();
    }

    public static void ranking() {
        List<Artigos> artigosEntreDatas = new ArrayList<>();
        Map<String, Double> valoresTotais = new HashMap<>();
        System.out.println("(1) Escolher datas:");
        System.out.println("(2) Desde sempre");
        int codigo = input.nextInt();
        System.out.println("(1) Ver estatisticas - Comprador");
        System.out.println("(2) Ver estatisticas - Vendedor");
        int opcao = input.nextInt();
        switch (codigo) {
            case 1 -> {
                switch (opcao) {
                    case 1 -> {
                        System.out.println("[Ranking]\n");
                        System.out.println("Comprador:");
                        listaCompradoresEntreDatas(artigosEntreDatas, valoresTotais);
                        return;
                    }
                    case 2 -> {
                        System.out.println("[Ranking]\n");
                        System.out.println("Vendedor:");
                        listaVendedoresEntreDatas(artigosEntreDatas, valoresTotais);
                        return;
                    }
                }
            }
            case 2 -> {
                switch (opcao) {
                    case 1 -> {
                        for (Utilizadores u : data.getUtilizadores()) {
                            double total = 0;
                            for (Artigos a : u.getComprados()) {
                                total += a.precoFinal();
                            }
                            valoresTotais.put(u.getNome(), total);
                        }
                        listaOrdenada(valoresTotais);
                    }
                    case 2 -> {
                        for (Utilizadores u : data.getUtilizadores()) {
                            double total = 0;
                            for (Artigos a : u.getVendidos()) {
                                total += a.precoFinal();
                            }
                            valoresTotais.put(u.getNome(), total);
                        }
                        listaOrdenada(valoresTotais);
                    }
                }
            }
        }
        estatisticasVintage();
    }

    public static void vintageFaturacao() {
        double soma = 0;
        for (Utilizadores u : data.getUtilizadores()) {
            for (Artigos art : u.getVendidos()) {
                soma += art.precoFinal();
            }
        }
        System.out.println("[Faturacao Vintage]: " + soma/0.1);
        estatisticasVintage();
    }

    public static void gerirTransportadoras() {
        System.out.println("\n[Transportadoras]");
        System.out.println("(1) Adicionar transportadora");
        data.getTransportadoras().forEach(transportadora -> System.out.printf("(%d) %s\n", data.getTransportadoras().indexOf(transportadora)+2,
                transportadora.getNome()));
        System.out.println("(0) Voltar para o menu principal");
        int codigo = input.nextInt();
        if (codigo == 0) {
            inicio();
            return;
        }
        if (codigo == 1) {
            criarTransportadora();
            return;
        }
        Transportadoras transportadora = data.getTransportadoraByCodigo(codigo, 2);
        if(transportadora==null) {
            System.out.println("> Codigo invalido!");
            inicio();
            return;
        }
        System.out.println("\n" + transportadora);
        System.out.println("(1) Remover transportadora");
        System.out.println("(2) Alterar precos");
        System.out.println("(3) Alterar lucro (fixo)");
        System.out.println("(3) Alterar formula de calculo");
        System.out.println("(0) Voltar");
        int opcao = input.nextInt();
        switch (opcao) {
            case 1 -> data.removeTransportadora(transportadora);
            case 2 -> alterarPrecoTransportadora(transportadora);
            case 3 -> alterarFormula(transportadora);
            case 0 -> gerirTransportadoras();
            default -> System.out.println("> Escolha inválida!");
        }
        inicio();
    }

    public static void gerirBackups() {
        System.out.println("\n[Backups]");
        data.getBackups().forEach(backup -> System.out.printf("(%d) %s\n", data.getBackups().indexOf(backup)+2, formatar2.format(backup.getDate())));
        System.out.println("(1) Adicionar backup (data atual)");
        System.out.println("(0) Voltar para o meu principal");
        int codigo = input.nextInt();
        if (codigo==0) {
            inicio();
            return;
        }
        if(codigo == 1) {
            data.addBackup();
            inicio();
            return;
        }
        VintageData backup = data.getBackupByCodigo(codigo, 2);
        if(backup == null) {
            System.out.println("> Codigo inválido!");
            perfil();
            return;
        }
        System.out.println("\n" + backup);
        System.out.println("(1) Remover este backup");
        System.out.println("(2) Usar este backup");
        System.out.println("(0) Voltar");
        int opcao = input.nextInt();
        switch (opcao) {
            case 1 -> removerBackup(backup);
            case 2 -> usarBackup(backup);
            case 0 -> gerirBackups();
            default -> System.out.println("> Escolha inválida!");
        }
        inicio();
    }

    //METODOS AUXILIARES

    public static void criarEncomenda(Artigos artigo) {
        if (user.getTipo().equals(Utilizadores.Tipo.vendedor)) {
            System.out.println("> Apenas contas de tipo comprador podem utilizar esta função!");
            perfil();
            return;
        }
        Encomendas encomenda = user.getCarrinho().stream()
                .filter(enc -> enc.getTransportadora().equals(artigo.getTransportadora()))
                .findFirst()
                .orElse(null);
        if (encomenda == null) {
            encomenda = new Encomendas();
            encomenda.setPreco(artigo.precoFinal() + artigo.getTransportadora().getPequena());
            encomenda.addArtigo(artigo);
            encomenda.setTransportadora(artigo.getTransportadora());
            data.addEncomendaPendentes(encomenda);
            user.getCarrinho().add(encomenda);
            System.out.println("[Atualização]");
            System.out.println("> Preco atual: " + encomenda.getPreco());
            System.out.println("> Produto adicionado ao carrinho (nova encomenda)!");
            data.getArtigos().remove(artigo);
            return;
        }
        encomenda.addArtigo(artigo);
        encomenda.setDimensao(calcularDimensao(encomenda.getArtigos().size()));
        encomenda.setPreco(precoEncomenda(encomenda));
        System.out.println("[Atualização]");
        System.out.println("> Preco atual: " + encomenda.getPreco());
        data.getArtigos().remove(artigo);
    }

    public static void removerDoCarrinho(Artigos artigo) {
        if (user.getTipo().equals(Utilizadores.Tipo.vendedor)) {
            System.out.println("> Apenas contas de tipo comprador podem utilizar esta função!");
            perfil();
            return;
        }
        Iterator<Encomendas> iterator = user.getCarrinho().iterator();
        while (iterator.hasNext()) {
            Encomendas encomenda = iterator.next();
            if (encomenda.getTransportadora().equals(artigo.getTransportadora())) {
                encomenda.remArtigo(artigo);
                if (encomenda.getArtigos().isEmpty()) {
                    iterator.remove();
                    data.getArtigos().add(artigo);
                }
                System.out.println("> Produto removido do carrinho!");
                carrinho();
                return;
            }
        }
        System.out.println("> Não foi encontrado o artigo!");
    }
    public static void criarSapatilhas(boolean novo, int estado, int donos, String descricao, String marca, double preco, int desconto, Transportadoras transp) {
        System.out.print("> Tamanho da sapatilha: ");
        double tamanho = input.nextDouble();
        System.out.print("> Possui atacadores (true, false): ");
        boolean atacadores = input.nextBoolean();
        System.out.print("> Cor da sapatilhas: ");
        input.nextLine();
        String cor = input.nextLine();
        System.out.print("> Ano de lancamento: ");
        int ano = input.nextInt();
        System.out.print("> Sapatilha premium (true, false): ");
        boolean premium = input.nextBoolean();
        Sapatilhas sapatilhas = new Sapatilhas(novo, estado, donos, descricao, marca, preco, desconto, transp, tamanho, atacadores, cor, ano, premium);
        System.out.println("\n" + sapatilhas);
        System.out.println("(1) Finalizar criacao do produto");
        System.out.println("(0) Cancelar criacao do produto");
        int opcao = input.nextInt();
        switch (opcao) {
            case 1 -> {
                if (premium && !transp.isPremium()) {
                    System.out.println("> Transportadoras nao premium nao podem ser associadas a sapatilhas premium");
                    return;
                }
                data.getArtigos().add(sapatilhas);
                user.addArtigoStock(sapatilhas);
                System.out.println("> Artigo adicionado à loja com sucesso!");
            }
            case 2 -> perfil();
        }
    }

    public static void criarTshirts(boolean novo, int estado, int donos, String descricao, String marca, double preco, int desconto, Transportadoras transp) {
        System.out.print("> Tamanho da tshirt (S, M, L, XL): ");
        Tshirts.Tamanho tamanho = Tshirts.Tamanho.valueOf(input.next());
        System.out.print("> Padrao da tshirt (liso, riscas, palmeiras): ");
        Tshirts.Padrao padrao = Tshirts.Padrao.valueOf(input.next());
        Tshirts tshirt = new Tshirts(novo, estado, donos, descricao, marca, preco, desconto, transp, tamanho, padrao);
        System.out.println("\n" + tshirt);
        System.out.println("(1) Finalizar criação do produto");
        System.out.println("(0) Cancelar criaçao do produto");
        int opcao = input.nextInt();
        switch (opcao) {
            case 1 -> {
                data.getArtigos().add(tshirt);
                user.addArtigoStock(tshirt);
                System.out.println("> Artigo adicionado à loja com sucesso!");
            }
            case 2 -> perfil();
        }
    }

    public static void criarMalas(boolean novo, int estado, int donos, String descricao, String marca, double preco, int desconto, Transportadoras transp) {
        System.out.print("> Largura da mala: ");
        double largura = input.nextDouble();
        System.out.print("> Comprimento da mala: ");
        double comprimento = input.nextDouble();
        System.out.print("> Altura da mala: ");
        double altura = input.nextDouble();
        System.out.print("> Material da mala: ");
        input.nextLine();
        String material = input.nextLine();
        System.out.print("> Ano de lancamento: ");
        int ano = input.nextInt();
        System.out.print("> Mala premium (true, false): ");
        boolean premium = input.nextBoolean();
        Malas mala = new Malas(novo, estado, donos, descricao, marca, preco, desconto, transp, largura, comprimento, altura, material, ano, premium);
        System.out.println("\n" + mala);
        System.out.println("(1) Finalizar criação do produto");
        System.out.println("(0) Cancelar criaçao do produto");
        int opcao = input.nextInt();
        switch (opcao) {
            case 1 -> {
                if (premium && !transp.isPremium()) {
                    System.out.println("> Transportadoras nao premium nao podem ser associadas a malas premium");
                    return;
                }
                data.getArtigos().add(mala);
                user.addArtigoStock(mala);
                System.out.println("> Artigo adicionado à loja com sucesso!");
            }
            case 2 -> perfil();
        }
    }

    public static void criarOutros(boolean novo, int estado, int donos, String descricao, String marca, double preco, int desconto, Transportadoras transp) {
        Outros outro = new Outros(novo, estado, donos, descricao, marca, preco, desconto, transp);
        System.out.println("\n" + outro);
        System.out.println("(1) Finalizar criação do produto: ");
        System.out.println("(0) Cancelar criaçao do produto");
        int opcao = input.nextInt();
        switch (opcao) {
            case 1 -> {
                data.getArtigos().add(outro);
                user.addArtigoStock(outro);
                System.out.println("> Artigo adicionado à loja com sucesso!");
            }
            case 2 -> perfil();
        }
    }
    public static Transportadoras escolherTransportadora() {
        System.out.println("> Transportadoras: ");
        System.out.println("> Transportadoras Premium: " +
                data.getTransportadoras()
                        .stream()
                        .filter(Transportadoras::isPremium)
                        .map(Transportadoras::getNome)
                        .toList());
        System.out.println("> Transportadoras não Premium: " +
                data.getTransportadoras()
                        .stream()
                        .filter(transportadora -> !transportadora.isPremium())
                        .map(Transportadoras::getNome)
                        .toList());
        data.getTransportadoras().forEach(transportadora -> System.out.printf("(%d) %s\n", (data.getTransportadoras().indexOf(transportadora) + 1), transportadora.getNome()));
        int codigo = input.nextInt();
        Transportadoras transp = data.getTransportadoraByCodigo(codigo, 1);
        if (transp == null) {
            System.out.println("> Código inválido!");
            perfil();
            return null;
        }
        System.out.println("\n" + transp);
        System.out.println("(1) Escolher esta transportadora");
        System.out.println("(0) Voltar");
        int opcao = input.nextInt();
        switch (opcao) {
            case 1 -> System.out.println("> Transportadora escolhida: " + transp.getNome());
            case 0 -> escolherTransportadora();
            default -> System.out.println("> Escolha inválida!");
        }
        return transp;
    }

    public static void criarTransportadora() {
        System.out.println("\n[Nova Transportadora]");
        System.out.print("> Nome: ");
        String nome = input.next();
        System.out.print("> Preco por encomenda pequena: ");
        double precoPequena = input.nextDouble();
        System.out.print("> Preco por encomenda media: ");
        double precoMedia = input.nextDouble();
        System.out.print("> Preco por encomenda grande: ");
        double precoGrande = input.nextDouble();
        System.out.print("> Imposto: ");
        int imposto = input.nextInt();
        System.out.print("> Lucro transportadora: ");
        double lucro = input.nextDouble();
        System.out.print("Transportadora premium (true, false): ");
        boolean premium = input.nextBoolean();
        Transportadoras transportadora = new Transportadoras(nome,precoPequena,precoMedia,precoGrande,imposto,lucro,premium);
        data.addTransportadora(transportadora);
        System.out.println("> Transportdaora adicionada com sucesso");
        inicio();
    }

    public static void usarBackup(VintageData backup) {
        data=backup;
        System.out.println("> O backup foi utilizado e a data foi alterada!");
    }

    public static void removerBackup(VintageData backup) {
        data.removeBackup(backup);
        System.out.println("> O backup foi removido da data!");
    }

    public static void listaVendedoresEntreDatas(List<Artigos> artigosEntreDatas,Map<String,Double> valoresTotais) {
        System.out.print("Data inicial (ex.: 2020-05-08T14:30:00): ");
        LocalDateTime dataInicial = LocalDateTime.parse(input.next());
        System.out.print("Data final (ex.: 2025-05-08T14:30:00): ");
        LocalDateTime dataFinal = LocalDateTime.parse(input.next());
        artigosEntreDatas.clear();
        valoresTotais.clear();
        for (Encomendas enc : data.getFinalizdas()) {
            if (enc.getDataCriada().isAfter(dataInicial) && enc.getDataCriada().isBefore(dataFinal)) {
                for (Artigos a2 : enc.getArtigos()) {
                    for (Utilizadores u : data.getUtilizadores()) {
                        if (u.getVendidos().contains(a2)) {
                            artigosEntreDatas.add(a2);
                            break;
                        }
                    }
                }
            }
        }
        calculateTotalValues(artigosEntreDatas, valoresTotais,1);
        listaOrdenada(valoresTotais);
        estatisticasVintage();
    }
    public static void listaCompradoresEntreDatas(List<Artigos> artigosEntreDatas,Map<String,Double> valoresTotais) {
        System.out.print("Data inicial (ex.: 2020-05-08T14:30:00): ");
        LocalDateTime dataInicial = LocalDateTime.parse(input.next());
        System.out.print("Data final (ex.: 2025-05-08T14:30:00): ");
        LocalDateTime dataFinal = LocalDateTime.parse(input.next());
        artigosEntreDatas.clear();
        valoresTotais.clear();
        for (Encomendas enc : data.getFinalizdas()) {
            if (enc.getDataCriada().isAfter(dataInicial) && enc.getDataCriada().isBefore(dataFinal)) {
                for (Artigos a2 : enc.getArtigos()) {
                    for (Utilizadores u : data.getUtilizadores()) {
                        if (u.getComprados().contains(a2)) {
                            artigosEntreDatas.add(a2);
                            break;
                        }
                    }
                }
            }
        }
        calculateTotalValues(artigosEntreDatas, valoresTotais,2);
        listaOrdenada(valoresTotais);
        estatisticasVintage();
    }

    public static void alterarPrecoTransportadora(Transportadoras transportadora) {
        System.out.println("[Preco Pequena] " + transportadora.getPequena());
        System.out.println("[Preco media] " + transportadora.getMedia());
        System.out.println("[Preco Grande] " + transportadora.getGrande());
        System.out.println("[Lucro transportadora] " + transportadora.getMargemLucro());
        System.out.println("\n(1) Alterar ou (2) Manter e Sair");
        int opcao = input.nextInt();
        if (opcao == 1) {
            System.out.println("pp -> precoPequena, pm -> precoMedia, pg -> precoGrande, l -> lucro");
            String escolha = input.next();
            switch (escolha) {
                case "pp" ->  {
                    System.out.println("Digite o preco: ");
                    transportadora.setPequenas(input.nextDouble());
                }
                case "pm" -> {
                    System.out.println("Digite o preco: ");
                    transportadora.setMedias(input.nextDouble());
                }
                case "pg" -> {
                    System.out.println("Digite o preco: ");
                    transportadora.setGrandes(input.nextDouble());
                }
                case "l" -> {
                    System.out.println("Digite o lucro: ");
                    transportadora.setMargemlucro(input.nextDouble());
                }
            }
            alterarPrecoTransportadora(transportadora);
        }
        if (opcao == 2) gerirTransportadoras();
    }

    public static void alterarFormula(Transportadoras transportadora) {
        System.out.println("[Formulas]");
        System.out.println("Choose a formula: ");
        System.out.println("(1). premium ? preco * (1+imposto) * 0.4 * 0.9 + margemLucro: preco*0.2*(1+imposto)*0.9 + margemLucro");
        System.out.println("(2). premium ? preco * (1+imposto) * 0.5 * 0.9 + margemLucro: preco*0.3*(1+imposto)*0.9 + margemLucro");
        System.out.println("(3). premium ? preco * (1+imposto) * 0.6 * 0.9 + margemLucro: preco*0.4*(1+imposto)*0.9 + margemLucro");
        System.out.println("(4). premium ? preco * (1+imposto) * 0.7 * 0.9 + margemLucro: preco*0.5*(1+imposto)*0.9 + margemLucro");
        int choice = input.nextInt();
        TransportadoraFormula.Escolha escolha;
        switch (choice) {
            case 1 -> escolha = TransportadoraFormula.Escolha.fr1;
            case 2 -> escolha = TransportadoraFormula.Escolha.fr2;
            case 3 -> escolha = TransportadoraFormula.Escolha.fr3;
            case 4 -> escolha = TransportadoraFormula.Escolha.fr4;
            default -> {
                System.out.println("Invalid choice. Defaulting to Formula 1.");
                escolha = TransportadoraFormula.Escolha.fr1;
            }
        }

        transportadora.enviaTipoFormula(escolha);
    }


    public static void calculateTotalValues(List<Artigos> artigosEntreDatas, Map<String, Double> valoresTotais, int codigo) {
        valoresTotais.clear();

        for (Utilizadores u : data.getUtilizadores()) {
            double total = 0;
            for (Artigos a : artigosEntreDatas) {
                if (codigo == 1 && u.getVendidos().contains(a)) {
                    total += a.precoFinal();
                }
                else if (codigo == 2 && u.getComprados().contains(a)) {
                    total += a.precoFinal();
                }
                else return;
            }
            valoresTotais.put(u.getNome(), total);
        }
    }

    public static void listaOrdenada(Map<String, Double> lista) {
        List<Map.Entry<String, Double>> listaOrdenada = new ArrayList<>(lista.entrySet());
        listaOrdenada.sort(Map.Entry.<String, Double>comparingByValue().reversed());
        for (Map.Entry<String, Double> mp : listaOrdenada) {
            System.out.println(mp.getKey() + ": " + mp.getValue());
        }
    }

    public static double precoArtigosEncomenda(Encomendas encomenda) {
        double soma = 0;
        for (Artigos a : encomenda.getArtigos()) {
            soma += a.precoFinal();
        }
        return soma;
    }

    public static double precoNovoUsado(Encomendas encomendas) {
        long qntNovo = encomendas.getArtigos().stream()
                .filter(Artigos::isNovo)
                .count();

        long qntUsado = encomendas.getArtigos().stream()
                .filter(art -> !art.isNovo())
                .count();

        return qntNovo * 0.5 + 0.25 * qntUsado;
    }

    public static double precoTransportadora(Encomendas encomenda, Encomendas.Dimensao dimensao) {
        switch (dimensao) {
            case pequeno -> {return encomenda.getTransportadora().precoFinal(encomenda.getTransportadora().getPequena());}
            case medio -> {return encomenda.getTransportadora().precoFinal(encomenda.getTransportadora().getMedia());}
            case grande -> {return encomenda.getTransportadora().precoFinal(encomenda.getTransportadora().getGrande());}
        }
        return 0;
    }

    public static double precoEncomenda(Encomendas encomenda) {
        return precoArtigosEncomenda(encomenda) + precoNovoUsado(encomenda) + precoTransportadora(encomenda,encomenda.getDimensao());
    }

    public static Encomendas getEncomendaByCodigo(List<Encomendas> listaEncomendas, int codigo, int inicial) {
        return listaEncomendas.stream()
                .filter(a -> listaEncomendas.indexOf(a) + inicial == codigo)
                .findFirst()
                .orElse(null);
    }

    public static Encomendas.Dimensao calcularDimensao(int qnt) {
        if (qnt < 2) return Encomendas.Dimensao.pequeno;
        if (qnt < 6) return Encomendas.Dimensao.medio;
        return Encomendas.Dimensao.grande;
    }

    public static Artigos getArtigoByCodigo(List<Artigos> listaArtigos, int codigo, int inicial) {
        return listaArtigos.stream().filter(a -> listaArtigos.indexOf(a) + inicial == codigo)
                .findFirst()
                .orElse(null);
    }

}




