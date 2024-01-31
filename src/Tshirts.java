/**
 * Classe Tshirts
 * Subclasse da classe Artigos;
 *
 * @author Daniel/João/Miguel
 * @version 20230420
 */
public class Tshirts extends Artigos{

    public enum Tamanho {
        S, M, L, XL
    }

    public enum Padrao {
        liso, riscas, palmeiras
    }

    private Tamanho tamanho;
    private Padrao padrao;

    /**
     * Construtores da classe Tshirts.
     * Declaração dos construtores por omissão (vazio),
     * parametrizado e de cópia
     */
    public Tshirts(){
        super();
        this.tamanho = null;
        this.padrao = null;
    }

    public Tshirts(boolean novo, int estado, int donos, String descricao, String marca, double precoBase, int desconto,Transportadoras transportadora, Tamanho tamanho, Padrao padrao){
        super(novo, estado, donos, descricao, marca, precoBase, desconto, transportadora);
        this.tamanho = tamanho;
        this.padrao = padrao;
    }

    public Tshirts(Tshirts tshirts){
        super(tshirts);
        this.tamanho = tshirts.getTamanho();
        this.padrao = tshirts.getPadrao();
    }

    /**
     * Método que devolve o tamanho de uma tshirt
     *
     * @return Tamanho com o tamanho da tshirt
     */
    public Tamanho getTamanho() {
        return this.tamanho;
    }

    /**
     * Método que devolve o padrao de uma tshirt
     *
     * @return Padrao com o padrao da tshirt
     */
    public Padrao getPadrao(){
        return this.padrao;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public void setPadrao(Padrao padrao){
        this.padrao = padrao;
    }

    /**
     * Implementacao do metodo toString comum na maioria das classes Java
     *
     * @return String com a informacao textual do objecto Tshirts
     */
    @Override
    public String toString() {
        return "[Informacao]\n" +
                "> Codigo: " + super.getCodigo() + "\n" +
                "> Tipo: T-Shirt\n" +
                "> Marca: " + super.getMarca() + "\n" +
                "> Novo: " + super.isNovo() + "\n" +
                "> Estado: " + super.getEstado() + "\n" +
                "> Donos: " + super.getDonos() + "\n" +
                "> Descricao: " + super.getDescricao() + "\n" +
                "> Preco base: " + super.getPrecoBase() + "\n" +
                "> Desconto: " + super.getDesconto() + "\n" +
                "> Tamanho: " + this.tamanho + "\n" +
                "> Padrao: " + this.padrao + "\n" +
                "> Transportadora: " + super.getTransportadora();
    }
    /**
     * Implementação do método de igualdade entre dois Artigos do tipo Tshirts
     *
     * @param obj       tshirt que é comparada com o recetor
     * ** * @return     booleano true ou false
     * ** * */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tshirts tshirts = (Tshirts) obj;
        return (super.isNovo() == tshirts.isNovo() &&
                super.getEstado() == tshirts.getEstado() &&
                super.getDonos() == tshirts.getDonos() &&
                super.getDescricao().equals(tshirts.getDescricao()) &&
                super.getMarca().equals(tshirts.getMarca()) &&
                super.getPrecoBase() == tshirts.getPrecoBase() &&
                super.getDesconto() == tshirts.getDesconto() &&
                super.getCodigo().equals(tshirts.getCodigo()) &&
                super.getTransportadora().equals(tshirts.getTransportadora()) &&
                this.tamanho == tshirts.getTamanho() &&
                this.padrao == tshirts.getPadrao());
    }

    /**
     * Implementacao do metodo clone comum na maioria das classes Java
     *
     * @return Tshirts copia de uma tshirt
     */
    @Override
    public Artigos clone(){
        return new Tshirts(this);
    }



    // metodos

    /**
     * Implementacao do metodo possuiDesconto() para a subclasse Tshirts
     *
     * @return boolean true ou false
     */
    public boolean possuiDesconto() {
        return (this.padrao!=Padrao.liso);
    }

    /**
     * Implementacao do metodo precoFinal() para a subclasse Tshirts
     *
     * @return double com o preco final de uma tshirt
     */
    public double precoFinal() {
        return (possuiDesconto() ? super.getPrecoBase() * 0.5 : super.getPrecoBase());
    }
}
