import java.time.LocalDateTime;

/**
 * Classe Sapatilhas
 * Subclasse da classe Artigos;
 *
 * @author Daniel/João/Miguel
 * @version 20230420
 */
public class Sapatilhas extends Artigos{

    private double tamanho;
    private boolean atacadores;
    private String cor;
    private int ano;
    private boolean premium;

    /**
     * Construtores da classe Sapatilhas.
     * Declaração dos construtores por omissão (vazio),
     * parametrizado e de cópia
     */
    public Sapatilhas(){
        super();
        this.tamanho = -1;
        this.atacadores = false;
        this.cor = "n/a";
        this.ano = -1;
        this.premium = false;
    }

    public Sapatilhas(boolean novo, int estado, int donos, String descricao, String marca, double precoBase, int desconto, Transportadoras transportadora, double tamanho, boolean atacadores, String cor, int ano, boolean premium){
        super(novo, estado, donos, descricao, marca, precoBase, desconto, transportadora);
        this.tamanho = tamanho;
        this.atacadores = atacadores;
        this.cor = cor;
        this.ano = ano;
        this.premium = premium;
    }

    public Sapatilhas(Sapatilhas sapatilhas){
        super(sapatilhas);
        this.tamanho = sapatilhas.getTamanho();
        this.atacadores = sapatilhas.hasAtacadores();
        this.cor = sapatilhas.getCor();
        this.ano = sapatilhas.getAno();
        this.premium = sapatilhas.isPremium();
    }

    /**
     * Método que devolve o tamanho de uma sapatilha
     *
     * @return double com o tamanho da sapatilha
     */
    public double getTamanho() {
        return this.tamanho;
    }

    /**
     * Método que devolve se uma sapatilha tem atacadores
     *
     * @return boolean true ou false
     */
    public boolean hasAtacadores() {
        return this.atacadores;
    }

    /**
     * Método que devolve a cor de uma sapatilha
     *
     * @return String com a cor da sapatilha
     */
    public String getCor() {
        return this.cor;
    }

    /**
     * Método que devolve o ano de lancamento de uma sapatilhas
     *
     * @return int com o ano de lancamento da sapatilha
     */
    public int getAno() {
        return this.ano;
    }

    /**
     * Método que devolve se uma sapatilha é premium ou nao
     *
     * @return boolean true ou false
     */
    public boolean isPremium() {
        return this.premium;
    }

    public void setTamanho(double tamanho) {
        this.tamanho = tamanho;
    }

    public void setAtacadores(boolean atacadores) {
        this.atacadores = atacadores;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setPremium(boolean premium){
        this.premium = premium;
    }

    /**
     * Implementacao do metodo toString comum na maioria das classes Java
     *
     * @return String com a informacao textual do objecto Sapatilhas
     */
    @Override
    public String toString() {
        return "[Informacao]\n" +
                "> Codigo: " + super.getCodigo() + "\n" +
                "> Tipo: Sapatilhas\n" +
                "> Marca: " + super.getMarca() + "\n" +
                "> Novo: " + super.isNovo() + "\n" +
                "> Estado: " + super.getEstado() + "%\n" +
                "> Donos: " + super.getDonos() + "\n" +
                "> Descricao: " + super.getDescricao() + "\n" +
                "> Preco base: " + super.getPrecoBase() + "$\n" +
                "> Desconto: " + super.getDesconto() + "%\n" +
                "> Tamanho: " + this.tamanho + "\n" +
                "> Atacadores: " + this.atacadores + "\n" +
                "> Cor: " + this.cor + "\n" +
                "> Ano: " + this.ano + "\n" +
                "> Premium: " + this.premium +  "\n" +
                "> Transportadora: " + super.getTransportadora();
    }

    /**
     * Implementação do método de igualdade entre dois Artigos do tipo Sapatilhas
     *
     * @param obj       sapatilha que é comparada com o recetor
     * ** * @return     booleano true ou false
     * ** * */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Sapatilhas sapatilhas = (Sapatilhas) obj;
        return (super.isNovo() == sapatilhas.isNovo() &&
                super.getEstado() == sapatilhas.getEstado() &&
                super.getDonos() == sapatilhas.getDonos() &&
                super.getDescricao().equals(sapatilhas.getDescricao()) &&
                super.getMarca().equals(sapatilhas.getMarca()) &&
                super.getPrecoBase() == sapatilhas.getPrecoBase() &&
                super.getDesconto() == sapatilhas.getDesconto() &&
                super.getCodigo().equals(sapatilhas.getCodigo()) &&
                super.getTransportadora().equals(sapatilhas.getTransportadora()) &&
                this.tamanho == sapatilhas.getTamanho() &&
                this.atacadores == sapatilhas.hasAtacadores() &&
                this.cor.equals(sapatilhas.getCor()) &&
                this.ano == sapatilhas.getAno() &&
                this.premium == sapatilhas.isPremium());
    }

    /**
     * Implementacao do metodo clone comum na maioria das classes Java
     *
     * @return Sapatilhas copia de uma sapatilha
     */
    @Override
    public Artigos clone(){
        return new Sapatilhas(this);
    }


    // metodos

    /**
     * Implementacao do metodo possuiDesconto() para a subclasse Sapatilhas
     *
     * @return boolean true ou false
     */
    public boolean possuiDesconto() {
        return (super.getDonos() > 0 || this.tamanho >= 45);
    }

    /**
     * Implementacao do metodo precoFinal() para a subclasse Sapatilhas
     *
     * @return double com o preco final de uma sapatilha
     */
    public double precoFinal() {
        return (this.premium ? super.getPrecoBase()+Math.pow(1+0.15,LocalDateTime.now().getYear()-this.getAno()) :
                possuiDesconto() ? super.getPrecoBase() * (100-super.getDesconto())/100 : super.getPrecoBase() );
    }

}