import java.io.*;
public class VintageDataFile {

    private final String ficheiro;

    public VintageDataFile(String ficheiro) {
        this.ficheiro=ficheiro;
        createFicheiro();
    }

    public void setVintageData(VintageData vintageData) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.ficheiro))) {
            out.writeObject(vintageData);
        } catch (IOException e) {
            System.err.println("Erro ao tentar salvar a VintageData: " + e.getMessage());
        }
    }

    public VintageData getVintageData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.ficheiro))){
            return (VintageData) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao tentar abrir a VintageData: " + e.getMessage());
            return new VintageData();
            //return null;
        }
    }

    public void printVintageData() {
        VintageData vintageData = getVintageData();
        if(vintageData == null) {
            System.out.println("A VintageData dentro do ficheiro está nula.");
            return;
        }
        System.out.println(vintageData);
    }

    public void clear() {
        setVintageData(new VintageData());
    }

    public void createFicheiro() {
        File file = new File(this.ficheiro);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Erro ao criar o ficheiro: " + e.getMessage());
            }
        }
    }
}
