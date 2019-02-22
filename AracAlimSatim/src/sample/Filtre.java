package sample;

import java.sql.Date;

public class Filtre {
    String ilanAdi;
    int fiyat;
    int km;
    Date tarih;
    String marka;
    String model;
    String yakitTuru;
    String vitesTuru;
    String renk;
    String sehir;
    String filtreSonuclari;
    public Filtre() {
    }

    public Filtre(String ilanAdi, int fiyat, int km, Date tarih, String marka, String model, String yakitTuru, String vitesTuru, String renk, String sehir) {
        this.ilanAdi = ilanAdi;
        this.fiyat = fiyat;
        this.km = km;
        this.tarih = tarih;
        this.marka = marka;
        this.model = model;
        this.yakitTuru = yakitTuru;
        this.vitesTuru = vitesTuru;
        this.renk = renk;
        this.sehir = sehir;
    }

    public String getIlanAdi() {
        return ilanAdi;
    }

    public String getFiltreSonuclari() {
        return filtreSonuclari;
    }

    public void setFiltreSonuclari(String filtreSonuclari) {
        this.filtreSonuclari = filtreSonuclari;
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

    public String getYakitTuru() {
        return yakitTuru;
    }

    public void setYakitTuru(String yakitTuru) {
        this.yakitTuru = yakitTuru;
    }

    public String getVitesTuru() {
        return vitesTuru;
    }

    public void setVitesTuru(String vitesTuru) {
        this.vitesTuru = vitesTuru;
    }

    public String getRenk() {
        return renk;
    }

    public void setRenk(String renk) {
        this.renk = renk;
    }

    public String getSehir() {
        return sehir;
    }

    public void setSehir(String sehir) {
        this.sehir = sehir;
    }
}
