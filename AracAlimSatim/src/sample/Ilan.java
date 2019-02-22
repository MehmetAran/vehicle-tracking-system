package sample;

import java.sql.Date;
import java.text.DateFormat;

public class Ilan {
    private int ilanID;
    private String ilanAdi;
    private int fiyat;
    private int km;
    private Date tarih;
    private int arabaID;
    private int sehirID;

    public Ilan() {
    }

    public Ilan(int ilanID, String ilanAdi, int fiyat, int km, Date tarih, int arabaID, int sehirID) {
        this.ilanID = ilanID;
        this.ilanAdi = ilanAdi;
        this.fiyat = fiyat;
        this.km = km;
        this.tarih = tarih;
        this.arabaID = arabaID;
        this.sehirID = sehirID;
    }

    public int getIlanID() {
        return ilanID;
    }

    public void setIlanID(int ilanID) {
        this.ilanID = ilanID;
    }

    public String getIlanAdi() {
        return ilanAdi;
    }

    public void setIlanAdi(String ilanAdi) {
        this.ilanAdi = ilanAdi;
    }

    public int getFiyat() {
        return fiyat;
    }

    public void setFiyat(int fiyat) {
        this.fiyat = fiyat;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    public int getArabaID() {
        return arabaID;
    }

    public void setArabaID(int arabaID) {
        this.arabaID = arabaID;
    }

    public int getSehirID() {
        return sehirID;
    }

    public void setSehirID(int sehirID) {
        this.sehirID = sehirID;
    }
}
