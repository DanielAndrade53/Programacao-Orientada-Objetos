import java.time.LocalDateTime;

/**
 * Classe Malas
 * Subclasse da classe Artigos;
 *
 * @author Daniel/João/Miguel
 * @version 20230420
 */
public class Malas extends Artigos{

    private double largura;
    private double comprimento;
    private double altura;
    private String material;
    private int ano;
    private boolean premium;

    /**
     * Construtores da classe Malas.
     * Declaração dos construtores por omissão (vazio),
     * parametrizado e de cópia
     */
    public Malas(){
        super();
        this.largura = 0;
        this.comprimento = 0;
        this.altura = 0;
        this.material = "n/a";
        this.ano = 0;
        this.premium = false;
    }
    public Malas(boolean novo, int estado, int donos, String descricao, String marca, double precoBase, int desconto, Transportadoras transportadora, double largura, double comprimento, double altura, String material, int ano, boolean premium){
        super(novo, estado, donos, descricao, marca, precoBase, desconto, transportadora);
        this.largura = largura;
        this.comprimento = comprimento;
        this.altura = altura;
        this.material = material;
        this.ano = ano;
        this.premium = premium;
    }

    public Malas(Malas malas){
        super(malas);
        this.largura = malas.getLargura();
        this.comprimento = malas.getComprimento();
        this.altura = malas.getAltura();
        this.material = malas.getMaterial();
        this.ano = malas.getAno();
        this.premium = malas.isPremium();
    }

    /**
     * Método que devolve a largura de uma mala
     *
     * @return double com largura da mala
     */
    public double getLargura() {
        return this.largura;
    }

    /**
     * Método que devolve o comprimento de uma mala
     *
     * @return double com o comprimento da mala
     */
    public double getComprimento() {
        return this.comprimento;
    }

    /**
     * Método que devolve a altura de uma mala
     *
     * @return double com a altura da mala
     */
    public double getAltura() {
        return this.altura;
    }

    /**
     * Método que devolve o mmaterial de uma mala
     *
     * @return String com o material da mala
     */
    public String getMaterial() {
        return this.material;
    }

    /**
     * Método que devolve o ano de lancamento de uma mala
     *
     * @return int com o ano de lanvcamento da mala
     */
    public int getAno() {
        return this.ano;
    }

    /**
     * Metodo que devolve se uma mala é premium ou nao
     *
     * @return boolean true ou false
     */
    public boolean isPremium() {
        return this.premium;
    }

    public void setLargura(double largura) {
        this.largura = largura;
    }

    public void setComprimento(double comprimento) {
        this.comprimento = comprimento;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }
    /**
     * Implementacao do metodo toString comum na maioria das classes Java
     *
     * @return String com a informacao textual do objecto Malas
     */
    @Override
    public String toString() {
        return "[Informacao]\n" +
                "> Codigo: " + super.getCodigo() + "\n" +
                "> Tipo: Mala\n" +
                "> Marca: " + super.getMarca() + "\n" +
                "> Novo: " + super.isNovo() + "\n" +
                "> Estado: " + super.getEstado() + "\n" +
                "> Donos: " + super.getDonos() + "\n" +
                "> Descricao: " + super.getDescricao() + "\n" +
                "> Preco base: " + super.getPrecoBase() + "\n" +
                "> Desconto: " + super.getDesconto() + "\n" +
                "> Largura: " + this.largura + "\n" +
                "> Comprimento: " + this.comprimento + "\n" +
                "> Altura: " + this.altura + "\n" +
                "> Material: " + this.material + "\n" +
                "> Ano: " + this.ano + "\n" +
                "> Premium: " + this.premium + "\n" +
                "> Transportadora: " + super.getTransportadora();

    }
    /**
     * Implementação do método de igualdade entre dois Artigos do tipo Malas
     *
     * @param obj       mala que é comparada com o recetor
     * ** * @return     booleano true ou false
     * ** * */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Malas malas = (Malas) obj;
        return (super.isNovo() == malas.isNovo() &&
                super.getEstado() == malas.getEstado() &&
                super.getDonos() == malas.getDonos() &&
                super.getDescricao().equals(malas.getDescricao()) &&
                super.getMarca().equals(malas.getMarca()) &&
                super.getPrecoBase() == malas.getPrecoBase() &&
                super.getDesconto() == malas.getDesconto() &&
                super.getCodigo().equals(malas.getCodigo()) &&
                super.getTransportadora() == malas.getTransportadora() &&
                this.largura == malas.getLargura() &&
                this.comprimento == malas.getComprimento() &&
                this.altura == malas.getAltura() &&
                this.material.equals(malas.getMaterial()) &&
                this.ano == malas.getAno() &&
                this.premium == malas.isPremium());
    }

    /**
     * Implementacao do metodo clone comum na maioria das classes Java
     *
     * @return Malas copia de uma mala
     */
    @Override
    public Artigos clone(){
        return new Malas(this);
    }
    /**
     * Implementacao do metodo possuiDesconto() para a subclasse Malas
     *
     * @return boolean true ou false
     */


    // metodos


    public boolean possuiDesconto() {
        return false;
    }

    /**
     * Implementacao do metodo precoFinal() para a subclasse Malas
     *
     * @return double com o preco final de uma mala
     */
    public double precoFinal() {
        return (this.premium ? super.getPrecoBase()+Math.pow(1+0.15, LocalDateTime.now().getYear()-this.getAno()) :
                possuiDesconto() ? super.getPrecoBase() * 1/(this.largura*this.altura*this.comprimento) : super.getPrecoBase());
    }

}
