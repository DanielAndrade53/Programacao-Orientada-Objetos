import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Utilizadores
 * Classe que modela a informação e comportamento relevante de um utilizador;
 *
 * @author Daniel/João/Miguel
 * @version 20230420
 */
public class Utilizadores implements Serializable {

    public enum Tipo{
        vendedor, comprador, ambos
    }

    private static long quantidade = 0;

    private final long codigo;
    private String email;
    private String nome;
    private String morada;
    private long nif;
    private List<Artigos> stock;
    private List<Artigos> comprados;
    private List<Artigos> vendidos;
    private Tipo tipo;
    private List<Encomendas> carrinho;

    /**
     * Construtores da classe Utilizadores.
     * Declaração dos construtores por omissão (vazio),
     * parametrizado e de cópia
     */
    public Utilizadores(){
        this.codigo = quantidade++;
        this.email = "n/a";
        this.nome = "n/a";
        this.morada = "n/a";
        this.nif = 0;
        this.stock = new ArrayList<>();
        this.comprados = new ArrayList<>();
        this.vendidos = new ArrayList<>();
        this.tipo = null;
        this.carrinho = new ArrayList<>();
    }

    public Utilizadores(String email, String nome, String morada, long nif, Tipo tipo) {
        this.codigo = quantidade++;
        this.email = email;
        this.nome = nome;
        this.morada = morada;
        this.nif = nif;
        this.stock = new ArrayList<>();
        this.comprados = new ArrayList<>();
        this.vendidos = new ArrayList<>();
        this.tipo = tipo;
        this.carrinho = new ArrayList<>();
    }

    public Utilizadores(Utilizadores utilizadores){
        this.codigo = utilizadores.getCodigo();
        this.email = utilizadores.getEmail();
        this.nome = utilizadores.getNome();
        this.morada = utilizadores.getMorada();
        this.nif = utilizadores.getNif();
        this.stock = utilizadores.getStock();
        this.comprados = utilizadores.getComprados();
        this.vendidos = utilizadores.getVendidos();
        this.tipo = utilizadores.getTipo();
        this.carrinho = utilizadores.getCarrinho();
    }
    /**
     * Método que devolve o codigo de um utilizador
     *
     * @return long com codigo do utilziador
     */
    public long getCodigo() {
        return this.codigo;
    }

    /**
     * Metodo que devolve o email de um utilzaidor
     *
     * @return String com email do utilziador
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Metodo que devolve o nome de um utilziador
     *
     * @return String com nome do utilziador
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Metodo que devolve a morada de um utilizador
     *
     * @return String com morada do utilziador
     */
    public String getMorada() {
        return this.morada;
    }

    /**
     * Metodo que devolve o numero fiscal de um utilziador
     *
     * @return long com numero fiscal do utilizador
     */
    public long getNif() {
        return this.nif;
    }

    /**
     * Metodo que devolve o stock de produtos de um utilizador
     *
     * @return List<Artigos> com o stock do utilizador
     */
    public List<Artigos> getStock() { return this.stock; }

    /**
     * Metodo que devolve os produtso comprados de um utilizador
     *
     * @return ListArtigos> com as compras do utilizador
     */
    public List<Artigos> getComprados() { return this.comprados; }

    /**
     * Metodo que devolve os produtos vendidos de um utilizador
     *
     * @return List<Artigos> com as vendas do utilizador
     */
    public List<Artigos> getVendidos() { return this.vendidos; }

    /**
     * Metodo que devolve o tipo de um utilizador
     *
     * @return Tipo com o tipo do utilziador
     */
    public Tipo getTipo() {
        return this.tipo;
    }

    /**
     * Metodo que devolve o carrinho de um utilizador
     *
     * @return List<Artigos> com o carrinho do utilizador
     */
    public List<Encomendas> getCarrinho() { return this.carrinho; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setNif(long nif) {
        this.nif = nif;
    }
    public void setStock(List<Artigos> stock) {
        this.stock = stock;
    }

    public void setComprados(List<Artigos> comprados) {
        this.comprados = comprados;
    }

    public void setVendidos(List<Artigos> vendidos) {
        this.vendidos = vendidos;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setCarrinho(List<Encomendas> carrinho) {
        this.carrinho = carrinho;
    }

    /**
     * Implementacao do metodo toString comum na maioria das classes Java
     *
     * @return String com a informacao textual do objecto Utilizadores
     */
    public String toString() {
        return "[Utilizador]\n" +
                "Codigo: " + this.codigo + "\n" +
                "Email: " + this.email + "\n" +
                "Nome: " + this.nome + "\n" +
                "Morada: " + this.morada + "\n" +
                "Nif: " + this.nif + "\n" +
                "Stock: " + this.stock + "\n" +
                "Comprados: " + this.comprados + "\n" +
                "Vendidos: " + this.vendidos + "\n" +
                "Tipo: " + this.tipo +  "\n" +
                "Carrinho: " + this.carrinho + "\n" +
                "[Utilizador]\n";
    }
    /**
     * Implementação do método de igualdade entre dois Utilizadores
     *
     * @param obj       utilizador que é comparada com o recetor
     * ** * @return     booleano true ou false
     * ** * */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Utilizadores utilizadores = (Utilizadores) obj;
        return (this.codigo == utilizadores.getCodigo() &&
                this.email.equals(utilizadores.getEmail()) &&
                this.nome.equals(utilizadores.getNome()) &&
                this.morada.equals(utilizadores.getMorada()) &&
                this.nif == utilizadores.getNif() &&
                this.stock.equals(utilizadores.getStock()) &&
                this.comprados.equals(utilizadores.getComprados()) &&
                this.vendidos.equals(utilizadores.getVendidos()) &&
                this.tipo.equals(utilizadores.getTipo())) &&
                this.carrinho.equals(utilizadores.getCarrinho());
    }
    /**
     * Implementacao do metodo clone comum na maioria das classes Java
     *
     * @return Utilizador copia de um utilizador
     */
    public Utilizadores clone(){
        return new Utilizadores(this);
    }

    // Metodos

    public void addEncomendaCarrinho(Encomendas encomenda) {
        this.carrinho.add(encomenda);
    }
    public void addArtigoStock(Artigos artigo) {
        this.stock.add(artigo);
    }
    public void addArtigoComprados(Artigos artigo) {
        this.comprados.add(artigo);
    }
    public void addArtigosVendidos(Artigos artigo) {
        this.vendidos.add(artigo);
    }

    public void removeEncomendaCarrinho(Encomendas encomenda) {
        this.carrinho.remove(encomenda);
    }
    public void removeArtigoStock(Artigos artigo) {
        this.stock.remove(artigo);
    }
    public void removeArtigoComprados(Artigos artigo) {
        this.comprados.remove(artigo);
    }
    public void removeArtigosVendidos(Artigos artigo) {
        this.vendidos.remove(artigo);
    }



}
