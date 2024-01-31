import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Encomendas
 * Classe que modela a informação e comportamento relevante de uma encomenda;
 *
 * @author Daniel/João/Miguel
 * @version 20230420
 */
public class Encomendas implements Serializable {

    public enum Dimensao {
        pequeno, medio, grande
    }

    public enum Estado {
        pendente, finalizada, expedida
    }

    private Dimensao dimensao;
    private Estado estado;
    private double preco;
    private LocalDateTime datacriada;
    private LocalDateTime datafinalizada;
    private List<Artigos> artigos;

    private Transportadoras transportadora;

    /**
     * Construtores da classe Encomendas.
     * Declaração dos construtores por omissão (vazio),
     * parametrizado e de cópia
     */
    public Encomendas() {
        this.dimensao = Dimensao.pequeno;
        this.preco = 0;
        this.estado = Estado.pendente;
        this.datacriada = LocalDateTime.now();
        this.datafinalizada = null;
        this.artigos = new ArrayList<>();
        this.transportadora = null;
    }

    public Encomendas(Dimensao dimensao, int preco, Estado estado, List<Artigos> artigos, Transportadoras transportadora) {
        this.dimensao = dimensao;
        this.preco = preco;
        this.estado = estado;
        this.datacriada = LocalDateTime.now();
        this.datafinalizada = null;
        this.artigos = artigos;
        this.transportadora = transportadora;
    }

    public Encomendas(Encomendas encomendas) {
        this.dimensao = encomendas.getDimensao();
        this.preco = encomendas.getPreco();
        this.estado = encomendas.getEstado();
        this.datacriada = encomendas.getDataCriada();
        this.datafinalizada = encomendas.getDataFinalizada();
        this.artigos = encomendas.getArtigos();
        this.transportadora = encomendas.getTransportadora();
    }

    /**
     * Metodo que devolve a dimensao de uma encomenda
     *
     * @return Dimensao com a dimensao da encomenda
     */
    public Dimensao getDimensao() {
        return this.dimensao;
    }

    /**
     * Metodo que devolve o preco de uma encomenda
     *
     * @return double com o preco da encomenda
     */
    public double getPreco() {
        return this.preco;
    }

    /**
     * Metodo que devolve o estado de uma encomenda
     *
     * @return Estado com o estado da encomenda
     */
    public Estado getEstado() {
        return this.estado;
    }

    /**
     * Metodo que devolve a data de uma encomenda
     *
     * @return LocalDateTime com a data da encomenda
     */
    public LocalDateTime getDataCriada() {
        return this.datacriada;
    }

    public LocalDateTime getDataFinalizada() {
        return this.datafinalizada;
    }

    /**
     * Metodo que devolve os artigos de uma encomenda
     *
     * @return List<Artigos> com os artigos da encomenda
     */
    public List<Artigos> getArtigos() {
        return this.artigos;
    }

    public Transportadoras getTransportadora() {
        return this.transportadora;
    }

    public void setDimensao(Dimensao dimensao) {
        this.dimensao = dimensao;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public void setDataCriada(LocalDateTime data) {
        this.datacriada = data;
    }
    public void setDatafinalizada(LocalDateTime data) {
        this.datafinalizada = data;
    }

    public void setArtigos(List<Artigos> artigos) {
        this.artigos = artigos;
    }

    public void setTransportadora(Transportadoras transportadora) {
        this.transportadora = transportadora;
    }

    public void addArtigo(Artigos artigo) {
        this.artigos.add(artigo);
    }

    public void remArtigo(Artigos artigo) {
        this.artigos.remove(artigo);
    }

    /**
     * Implementacao do metodo toString comum na maioria das classes Java
     *
     * @return String com a informacao textual do objecto Encomendas
     */
    public String toString() {
        return "[Encomendas]{\n" +
                "Dimensao: " + this.dimensao + "\n" +
                "Preco: " + this.preco + "\n" +
                "Estado: " + this.estado + "\n" +
                "Data: " + this.datacriada + "\n" +
                "Artigos: " + this.artigos + "\n" +
                "Transportadora: " + this.transportadora + "\n" +
                "[Encomendas]\n";
    }
    /**
     * Implementação do método de igualdade entre duas Encomendas
     *
     * @param obj       encomenda que é comparada com o recetor
     * ** * @return     booleano true ou false
     * ** * */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Encomendas encomendas = (Encomendas) obj;
        return (this.dimensao == encomendas.getDimensao() &&
                this.preco == encomendas.getPreco() &&
                this.estado == encomendas.getEstado() &&
                this.datacriada == encomendas.getDataCriada() &&
                this.datafinalizada == encomendas.getDataFinalizada() &&
                this.artigos.equals(encomendas.getArtigos())) &&
                this.transportadora.equals(encomendas.getTransportadora());
    }
    /**
     * Implementacao do metodo clone comum na maioria das classes Java
     *
     * @return Encomendas copia de uma encomenda
     */
    public Encomendas clone() {
        return new Encomendas(this);
    }

}