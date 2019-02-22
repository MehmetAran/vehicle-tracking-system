package sample;
public class YakitTuru {
    private String yakitTuru;
    private int yakitTuruID;

    public YakitTuru() {

    }

    public YakitTuru( int yakitTuruID , String yakitTuru) {
        this.yakitTuru = yakitTuru;
        this.yakitTuruID = yakitTuruID;
    }

    public String getYakitTuru() {
        return yakitTuru;
    }

    public void setYakitTuru(String yakitTuru) {
        this.yakitTuru = yakitTuru;
    }

    public int getYakitTuruID() {
        return yakitTuruID;
    }

    public void setYakitTuruID(int yakitTuruID) {
        this.yakitTuruID = yakitTuruID;
    }

}
