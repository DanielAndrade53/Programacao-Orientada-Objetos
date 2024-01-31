import java.io.Serializable;
/**
 * Classe Transportadoras
 * Classe que modela a informação e comportamento relevante de uma transportadora;
 *
 * @author Daniel/João/Miguel
 * @version 20230420
 */
public class Transportadoras implements Serializable {

    private String nome;
    private double precoPequenas;
    private double precoMedias;
    private double precoGrandes;
    private int imposto;
    private double margemlucro;
    private boolean premium;
    private double lucrototal;
    private TransportadoraFormula tipoFormula;

    /**
     * Construtores da classe Transportadoras.
     * Declaração dos construtores por omissão (vazio),
     * parametrizado e de cópia
     */
    public Transportadoras(){
        this.precoPequenas = 0 ;
        this.precoMedias = 0;
        this.precoGrandes = 0;
        this.imposto = 0;
        this.margemlucro = 0;
        this.lucrototal = 0;
        this.premium = false;
        tipoFormula = new TransportadoraFormula();
    }

    public Transportadoras(String nome,double precoPequenas, double precoMedias, double precoGrandes, int imposto, double margemlucro, boolean premium){
        this.nome = nome;
        this.precoPequenas = precoPequenas;
        this.precoMedias = precoMedias;
        this.precoGrandes = precoGrandes;
        this.imposto = imposto;
        this.margemlucro = margemlucro;
        this.lucrototal = 0;
        this.premium = premium;
        tipoFormula = new TransportadoraFormula();
    }

    public Transportadoras(Transportadoras transportadoras){
        this.nome = transportadoras.getNome();
        this.precoPequenas = transportadoras.getPequena();
        this.precoMedias = transportadoras.getMedia();
        this.precoGrandes = transportadoras.getGrande();
        this.imposto = transportadoras.getImposto();
        this.margemlucro = transportadoras.getMargemLucro();
        this.lucrototal = transportadoras.getLucroTotal();
        this.premium = transportadoras.isPremium();
        tipoFormula = new TransportadoraFormula();
    }

    public String getNome() { return this.nome; }

    public double getPequena() {
        return this.precoPequenas;
    }

    public double getMedia() {
        return this.precoMedias;
    }

    public double getGrande() {
        return this.precoGrandes;
    }

    public int getImposto() {
        return this.imposto;
    }

    public double getMargemLucro() {
        return this.margemlucro;
    }

    public double getLucroTotal() {
        return this.lucrototal;
    }

    public boolean isPremium() {
        return this.premium;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPequenas(double pequenas) {
        this.precoPequenas = pequenas;
    }

    public void setMedias(double medias) {
        this.precoMedias = medias;
    }

    public void setGrandes(double grandes) {
        this.precoGrandes = grandes;
    }

    public void setImposto(int imposto) {
        this.imposto = imposto;
    }

    public void setMargemlucro(double margemlucro) {this.margemlucro = margemlucro;}

    public void setLucrototal(double lucrototal) {this.lucrototal = lucrototal;}

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public void addlucro(){
        this.lucrototal += this.margemlucro;
    }
    public void enviaTipoFormula(TransportadoraFormula.Escolha escolha) { this.tipoFormula.setEscolha(escolha); }

    public String toString() {
        return "[Informacao]\n" +
                "> Empresa: " + this.nome + "\n" +
                "> Preço encomendas pequenas: " + this.precoPequenas + "\n" +
                "> Preço encomendas medias: " + this.precoMedias + "\n" +
                "> Preço encomendas grandes: " + this.precoGrandes + "\n" +
                "> Imposto: " + this.imposto + "\n" +
                "> Margem de lucro: " + this.margemlucro + "\n" +
                "> Lucro total: " + this.lucrototal + "\n" +
                "> Premium: " + this.premium;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Transportadoras transportadoras = (Transportadoras) obj;
        return (this.getNome().equals(transportadoras.getNome()) &&
                this.precoPequenas == transportadoras.getPequena() &&
                this.precoMedias == transportadoras.getMedia() &&
                this.precoGrandes == transportadoras.getGrande() &&
                this.imposto == transportadoras.getImposto() &&
                this.margemlucro == transportadoras.getMargemLucro() &&
                this.lucrototal == transportadoras.getLucroTotal() &&
                this.premium == transportadoras.isPremium());
    }

    public Transportadoras clone(){
        return new Transportadoras(this);
    }

    public double precoFinal(double preco) {
   //     return this.isPremium() ? preco * (1+this.imposto) * 0.4 * 0.9 + this.margemlucro: preco*0.2*(1+this.imposto)*0.9 + this.margemlucro;
        return this.tipoFormula.escolhaFormula(premium,preco,imposto,margemlucro);
    }

}
