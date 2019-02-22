package sample;

public class Araba {
    private int arabaID;
    private int renkID;
    private int vitesTuruID;
    private int yakitTuruID;
    private String marka;
    private String model;

    public Araba() {

    }

    public Araba(int arabaID, String marka, String model, int vitesTuruID, int yakitTuruID,int renkID) {
        this.arabaID = arabaID;
        this.renkID = renkID;
        this.vitesTuruID = vitesTuruID;
        this.yakitTuruID = yakitTuruID;
        this.marka = marka;
        this.model = model;
    }

    public int getArabaID() {
        return arabaID;
    }

    public void setArabaID(int arabaID) {
        this.arabaID = arabaID;
    }

    public int getRenkID() {
        return renkID;
    }

    public void setRenkID(int renkID) {
        this.renkID = renkID;
    }

    public int getVitesTuruID() {
        return vitesTuruID;
    }

    public void setVitesTuruID(int vitesTuruID) {
        this.vitesTuruID = vitesTuruID;
    }

    public int getYakitTuruID() {
        return yakitTuruID;
    }

    public void setYakitTuruID(int yakitTuruID) {
        this.yakitTuruID = yakitTuruID;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "tb1_araba";
    }
}
