/**
 * Classe VintageData
 *
 * @author Daniel/João/Miguel
 * @version 20230427
 */

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class VintageData implements Serializable {

    private static final List<VintageData> backups = new ArrayList<>();

    private final List<Utilizadores> utilizadores;
    private final List<Artigos> artigosLoja;
    private final List<Transportadoras> transportadoras;
    private final List<Encomendas> pendentes;
    private final List<Encomendas> finalizadas;
    private final List<Encomendas> expedidas;

    private final LocalDateTime data;

    public VintageData() {
        this.utilizadores = new ArrayList<>();
        this.artigosLoja = new ArrayList<>();
        this.transportadoras = new ArrayList<>();
        this.pendentes = new ArrayList<>();
        this.finalizadas = new ArrayList<>();
        this.expedidas = new ArrayList<>();
        this.data = LocalDateTime.now();
    }

    public VintageData(VintageData vintageData) {
        this.utilizadores = new ArrayList<>(vintageData.getUtilizadores());
        this.artigosLoja = new ArrayList<>(vintageData.getArtigos());
        this.transportadoras = new ArrayList<>(vintageData.getTransportadoras());
        this.pendentes = new ArrayList<>(vintageData.getPendentes());
        this.finalizadas = new ArrayList<>(vintageData.getFinalizdas());
        this.expedidas = new ArrayList<>(vintageData.getExpedidas());
        this.data = LocalDateTime.now();
    }

    public List<VintageData> getBackups() {
        return backups;
    }

    public List<Utilizadores> getUtilizadores() {
        return this.utilizadores;
    }

    public List<Artigos> getArtigos() {
        return this.artigosLoja;
    }

    public List<Encomendas> getPendentes() {
        return this.pendentes;
    }

    public List<Transportadoras> getTransportadoras() {
        return this.transportadoras;
    }

    public List<Encomendas> getFinalizdas() {
        return this.finalizadas;
    }

    public List<Encomendas> getExpedidas() {
        return this.expedidas;
    }

    public LocalDateTime getDate() {
        return this.data;
    }

    public void addUtilizador(Utilizadores utilizador) {
        this.utilizadores.add(utilizador);
    }

    public void removeUtilizador(Utilizadores utilizador) {
        this.utilizadores.remove(utilizador);
    }

    public void addArtigoLoja(Artigos artigo) {
        this.artigosLoja.add(artigo);
    }

    public void removeArtigoLoja(Artigos artigo) {
        this.artigosLoja.remove(artigo);
    }

    public void addEncomendaPendentes(Encomendas encomenda) {
        this.pendentes.add(encomenda);
    }

    public void removeEncomendaCarrinho(Encomendas encomenda) {
        this.pendentes.remove(encomenda);
    }

    public void addTransportadora(Transportadoras transportadora) {
        this.transportadoras.add(transportadora);
    }

    public void removeTransportadora(Transportadoras transportadora) {
        this.transportadoras.remove(transportadora);
    }

    public void addEncomendaFinalizados(Encomendas encomenda) {
        this.finalizadas.add(encomenda);
    }

    public void removeEncomendaFinalizados(Encomendas encomenda) {
        this.finalizadas.remove(encomenda);
    }

    public void addEncomendaExpedidas(Encomendas encomenda) {
        this.expedidas.add(encomenda);
    }

    public void removeEncomendaExpedidas(Encomendas encomenda) {
        this.expedidas.remove(encomenda);
    }

    public void addBackup() {
        VintageData backup = new VintageData(this);
        if (backups.contains(backup)) {
            System.out.println("> Não foi criado um novo backup pois existe um igual já criado");
            return;
        }
        backups.add(backup);
    }

    public void removeBackup(VintageData vintageData) {
        backups.remove(vintageData);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("VintageData{\n");
        sb.append("  Utilizadores:\n");
        this.utilizadores.forEach(utilizador -> {
            sb.append("    - ");
            sb.append(utilizador.getEmail()).append("\n");
        });
        sb.append("  Artigos na loja:\n");
        this.artigosLoja.forEach(artigo -> {
            sb.append("    - ");
            sb.append(artigo.getMarca()).append("\n");
        });
        sb.append("  Transpportadora:\n");
        this.transportadoras.forEach(transportadora -> {
            sb.append("    - ");
            sb.append(transportadora.getNome()).append("\n");
        });
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        VintageData data = (VintageData) obj;
        return (data.getUtilizadores().equals(this.utilizadores) &&
                data.getArtigos().equals(this.artigosLoja) &&
                data.getTransportadoras().equals(this.transportadoras) &&
                data.getDate().equals(this.data));
    }

    public Utilizadores findUserByEmail(String email) {
        return utilizadores.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }

    public boolean filterEmailExists(String email) {
        return utilizadores.stream().anyMatch(u -> u.getEmail().equals(email));
    }

    public void showArtigosLoja() {
        artigosLoja.forEach(artigo -> System.out.printf("(%d) %s - %.2f$\n", artigosLoja.indexOf(artigo) + 1, artigo.getMarca(), artigo.precoFinal()));
    }

    public Utilizadores findUserByArtigoStock(Artigos artigos) {
        return utilizadores.stream().filter(u -> u.getStock().contains(artigos)).findFirst().orElse(null);
    }

    public Utilizadores findUserByArtigoVendidos(Artigos artigos) {
        return utilizadores.stream().filter(u -> u.getVendidos().contains(artigos)).findFirst().orElse(null);
    }

    public VintageData getBackupByCodigo(int codigo, int inicial) {
        return backups.stream().filter(backup -> backups.indexOf(backup)+inicial==codigo)
                .findFirst()
                .orElse(null);
    }

    public Transportadoras getTransportadoraByCodigo(int codigo, int inicial) {
        return transportadoras.stream()
                .filter(a -> transportadoras.indexOf(a) + inicial == codigo)
                .findFirst()
                .orElse(null);
    }

    public Artigos getArtigoByCodigo(int codigo, int inicial) {
        return artigosLoja.stream().filter(a -> artigosLoja.indexOf(a) + inicial == codigo)
                .findFirst()
                .orElse(null);
    }

    public void fixEncomendasTempo(LocalDateTime data){
        this.finalizadas.forEach(encomenda -> {
            if (encomenda.getDataFinalizada().plusDays(2).isAfter(data)){
                this.finalizadas.remove(encomenda);
                this.expedidas.add(encomenda);
            }
        });
    }
    public  void devolverEncomenda(){
        for (Encomendas enc : this.getFinalizdas()) {
            this.removeEncomendaFinalizados(enc);
        }
    }
}

