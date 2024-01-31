import java.io.Serializable;

public class TransportadoraFormula implements Serializable {
    public enum Escolha {
        fr1, fr2, fr3, fr4
    }
    private Escolha escolha;

    TransportadoraFormula() {
        this.escolha = Escolha.fr1;
    }
    public void setEscolha(Escolha escolha) { this.escolha = escolha; }
    private double formula1(boolean premium, double preco, int imposto, double margemLucro) {
        return premium ? preco * (1+imposto) * 0.4 * 0.9 + margemLucro: preco*0.2*(1+imposto)*0.9 + margemLucro;
    }
    private double formula2(boolean premium, double preco, int imposto, double margemLucro) {
        return premium ? preco * (1+imposto) * 0.5 * 0.9 + margemLucro: preco*0.3*(1+imposto)*0.9 + margemLucro;
    }
    private double formula3(boolean premium, double preco, int imposto, double margemLucro) {
        return premium ? preco * (1+imposto) * 0.6 * 0.9 + margemLucro: preco*0.4*(1+imposto)*0.9 + margemLucro;
    }
    private double formula4(boolean premium, double preco, int imposto, double margemLucro) {
        return premium ? preco * (1+imposto) * 0.7 * 0.9 + margemLucro: preco*0.5*(1+imposto)*0.9 + margemLucro;
    }

    public double escolhaFormula(boolean premium, double preco, int imposto, double margemLucro) {
        switch (escolha) {
            case fr1 -> formula1(premium, preco, imposto, margemLucro);
            case fr2 -> formula2(premium, preco, imposto, margemLucro);
            case fr3 -> formula3(premium, preco, imposto, margemLucro);
            case fr4 -> formula4(premium, preco, imposto, margemLucro);
            default -> throw new IllegalArgumentException("Invalid choice");
        }
        return formula1(premium,preco,imposto,margemLucro);
    }
}
