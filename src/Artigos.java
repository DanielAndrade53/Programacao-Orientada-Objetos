import java.io.Serializable;
import java.util.Random;

/**
 * Classe Artigos
 * Superclasse das classes Sapatilhas, Tshirts, Malas;
 * É abstract pois contem metodos abstratos nela;
 *
 * @author Daniel/João/Miguel
 * @version 20230420
 */
public abstract class Artigos implements Serializable {

    private boolean novo;
    private int estado; // 0-100%
    private int donos;
    private String descricao;
    private String marca;
    private final String codigo;
    private double precoBase;
    private int desconto; // 0-100%
    private Transportadoras transportadora;

    /**
     * Construtores da classe Artigos.
     * Declaração dos construtores por omissão (vazio),
     * parametrizado e de cópia
     */
    public Artigos(){
        this.novo = true;
        this.estado = 100;
        this.donos = 0;
        this.descricao = "n/a";
        this.marca = "n/a";
        this.codigo = gerarCodigo();
        this.precoBase = 0;
        this.desconto = 0;
        this.transportadora = null;

    }
    public Artigos(boolean novo, int estado, int donos, String descricao, String marca, double precoBase, int desconto, Transportadoras transportadora){
        this.novo = novo;
        this.estado = estado;
        this.donos = donos;
        this.descricao = descricao;
        this.marca = marca;
        this.codigo = gerarCodigo();
        this.precoBase = precoBase;
        this.desconto = desconto;
        this.transportadora = transportadora;
    }
    public Artigos(Artigos artigos){
        this.novo = artigos.isNovo();
        this.estado = artigos.getEstado();
        this.donos = artigos.getDonos();
        this.descricao = artigos.getDescricao();
        this.marca = artigos.getMarca();
        this.codigo = gerarCodigo();
        this.precoBase = artigos.getPrecoBase();
        this.desconto = artigos.getDesconto();
        this.transportadora = artigos.getTransportadora();
    }

    /**
     * Método que devolve se o artigo é novo ou usado.
     *
     * @return booleano true ou false
     */
    public boolean isNovo(){ return this.novo; }

    /**
     * Método que devolve o estado (0-100) de um artigo
     *
     * @return int com a classificação do estado do artigo
     */
    public int getEstado(){ return this.estado; }

    /**
     * Método que devolve o número de donos de um artigo
     *
     * @return int com o número de donos do artigo
     */
    public int getDonos(){ return this.donos; }

    /**
     * Método que devolve a marca de um artigo
     *
     * @return String com a marca do artigo
     */
    public String getMarca(){ return this.marca; }

    /**
     * Método que devolve a descrição de um artigo
     *
     * @return String com a descrição do artigo
     */
    public String getDescricao(){ return this.descricao; }

    /**
     * Método que devolve o preco de um artigo
     *
     * @return double com o preco do artigo
     */
    public double getPrecoBase(){ return this.precoBase; }

    /**
     * Método que devolve o desconto de um artigo
     *
     * @return int com o desconto do artigo
     */
    public int getDesconto(){ return this.desconto; }

    /**
     * Método que devolve o código de um artigo
     *
     * @return long com o codigo do artigo
     */
    public String getCodigo(){ return this.codigo; }

    /**
     *
     *  Metodo que devolve a transportadora de um artigo
     *
     * @return Transportadoras com a transportadora do artigo
     */
    public Transportadoras getTransportadora() { return this.transportadora;}

    public void setNovo(boolean novo){ this.novo = novo; }

    public void setEstado(int estado) {this.estado = estado; }

    public void setDonos(int donos) {
        this.donos = donos;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setPrecoBase(double preco) {
        this.precoBase = precoBase;
    }

    public void setDesconto(int desconto) {
        this.desconto = desconto;
    }

    public void setTransportadora(Transportadoras transportadora) {
        this.transportadora = transportadora;
    }

    /**
     * Metodo que gera um codigo alfanumerico de um artigo
     *
     * @return int com o codigo alfanumerico do artigo
     */
    private String gerarCodigo(){
        Random r = new Random(System.currentTimeMillis());
        StringBuilder codigo = new StringBuilder();
        for(int i=0; i<12; i++)
            codigo.append(r.nextInt(10));
        return codigo.toString();
    }

    /**
     * Assinatura do metodo toString()
     *
     * @return String com a informação textual do objecto Artigo
     */
    public abstract String toString();

    /**
     * Assinatura do método de igualdade entre dois Artigos
     *
     * @param obj       artigo que é comparado com o recetor
     * ** * @return     booleano true ou false
     * ** * */
    public abstract boolean equals(Object obj);

    /**
     * Assinatura do metodo clone()
     *
     * @return Artigos copia de um artigo
     */
    public abstract Artigos clone();



    // metodos

    /**
     * Assinatura do metodo possuiDesconto()
     *
     * @return boolean true ou false
     */
    public abstract boolean possuiDesconto();

    /**
     * Assinatura do metodo precoFinal()
     *
     * @return double com o preco final do artigo
     */
    public abstract double precoFinal();

}
