/**
 * Classe Outros
 * Subclasse da classe Artigos;
 *
 * @author Daniel/João/Miguel
 * @version 20230421
 */
public class Outros extends Artigos {

    public Outros(){
        super();
    }
    public Outros(boolean novo, int estado, int donos, String descricao, String marca, double precoBase, int desconto, Transportadoras transportadora){
        super(novo, estado, donos, descricao, marca, precoBase, desconto, transportadora);
    }
    public Outros(Outros outro){
        super(outro);
    }

    /**
     * Implementacao do metodo toString comum na maioria das classes Java
     *
     * @return String com a informacao textual do objecto Outros
     */
    @Override
    public String toString(){
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
                "> Transportadora: " + super.getTransportadora();
    }

    /**
     * Implementação do método de igualdade entre dois Artigos do tipo Outros
     *
     * @param obj       outro que é comparada com o recetor
     * ** * @return     booleano true ou false
     * ** * */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Outros outros = (Outros) obj;
        return (super.isNovo() == outros.isNovo() &&
                super.getEstado() == outros.getEstado() &&
                super.getDonos() == outros.getDonos() &&
                super.getDescricao().equals(outros.getDescricao()) &&
                super.getMarca().equals(outros.getMarca()) &&
                super.getPrecoBase() == outros.getPrecoBase() &&
                super.getDesconto() == outros.getDesconto() &&
                super.getCodigo().equals(outros.getCodigo())) &&
                super.getTransportadora().equals(outros.getTransportadora());
    }

    /**
     * Implementacao do metodo clone comum na maioria das classes Java
     *
     * @return Outro copia de um outro
     */
    @Override
    public Outros clone() { return new Outros(this); }

    /**
     * Implementacao do metodo possuiDesconto() para a subclasse Outros
     *
     * @return boolean true ou false
     */
    public boolean possuiDesconto() {
        return this.isNovo();
    }
    /**
     * Implementacao do metodo precoFinal() para a subclasse Sapatilhas
     *
     * @return double com o preco final de um outro
     */
    public double precoFinal() {
        return this.isNovo() ? super.getPrecoBase() : super.getPrecoBase()*(100-super.getDesconto())/100;
    }

}
