package sample;

public class Sehir {
    private int sehirID;
    private String sehir;

    public Sehir() {

    }

    public Sehir(int sehirID, String sehir) {
        this.sehirID = sehirID;
        this.sehir = sehir;
    }

    public void setSehir(String sehir) {
        this.sehir = sehir;
    }

    public void setSehirID(int sehirID) {
        this.sehirID = sehirID;
    }

    public int getSehirID() {
        return sehirID;
    }

    public String getSehir() {
        return sehir;
    }

}
