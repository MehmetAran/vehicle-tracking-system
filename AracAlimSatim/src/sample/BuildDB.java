package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BuildDB {


    public Connection databaseConnection(Connection connection ) {
        try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/arac_alim_satim?useSSL=false", "root", "mehmet");

        } catch (Exception e) {
                System.out.println("Hata Mysql'e bağlanılamadı");
            }
        return connection;
    }

    public  void buildTable(Connection connection, Statement statement){
           try {
               Class.forName("com.mysql.jdbc.Driver");
               connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/arac_alim_satim?useSSL=false", "root", "mehmet");

           } catch (Exception e) {
               System.out.println("Hata Mysql'e bağlanılamadı");
           }

           try{
               statement =  connection.createStatement();
               String sql;

               sql = "CREATE TABLE IF NOT EXISTS tb1_sehir " +
                        "(SehirID  INT not NULL AUTO_INCREMENT , " +
                        " Sehir VARCHAR(255)not NULL, " +
                        " PRIMARY KEY (SehirID) ," +
                       "   UNIQUE (Sehir) )" ;

               statement.executeUpdate(sql);

               sql = "CREATE TABLE IF NOT EXISTS tb1_renk " +
                        "(RenkID  INT not NULL AUTO_INCREMENT , " +
                        " Renk VARCHAR(255)not NULL, " +
                        " PRIMARY KEY (RenkID ) , " +
                        "UNIQUE (Renk))" ;

               statement.executeUpdate(sql);

               sql = "CREATE TABLE IF NOT EXISTS tb1_vitesturu " +
                        "(VitesTuruID  INT not NULL AUTO_INCREMENT , " +
                        " VitesTuru VARCHAR(255)not NULL, " +
                        " PRIMARY KEY (VitesTuruID ) , " +
                        "UNIQUE (VitesTuru))" ;

               statement.executeUpdate(sql);

               sql = "CREATE TABLE IF NOT EXISTS tb1_yakitturu " +
                        "(YakitTuruID  INT not NULL AUTO_INCREMENT , " +
                        " YakitTuru VARCHAR(255) not NULL, " +
                        " PRIMARY KEY (YakitTuruID )," +
                        "UNIQUE (YakitTuru))" ;

               statement.executeUpdate(sql);

               sql = "CREATE TABLE IF NOT EXISTS tb1_araba " +
                        "(ArabaID  INT not NULL AUTO_INCREMENT  , " +
                        " Araba_Marka VARCHAR(255) not NULL, " +
                        " Araba_Model VARCHAR(255) not NULL, " +
                        " Araba_VitesTuruID INT not NULL, " +
                        " Araba_YakitTuruID INT not NULL, " +
                        " Araba_RenkID INT  not NULL ," +
                        " PRIMARY KEY (ArabaID) , " +
                        "CONSTRAINT fk_vitesturuid FOREIGN KEY (Araba_VitesTuruID) REFERENCES tb1_vitesturu(VitesTuruID) , " +
                        "CONSTRAINT fk_renkid FOREIGN KEY (Araba_RenkID) REFERENCES tb1_renk(RenkID) , " +
                        "CONSTRAINT fk_yakitturu FOREIGN KEY (Araba_YakitTuruID) REFERENCES tb1_yakitturu(YakitTuruID))";


               statement.executeUpdate(sql);

                sql="CREATE TABLE IF NOT EXISTS tb1_ilan(" +
                        "IlanID  INT not NULL AUTO_INCREMENT ," +
                        "Ilan_Adi VARCHAR (255) not NULL," +
                        "Ilan_Fiyat INT not NULL," +
                        "Ilan_Km INT not NULL," +
                        "Ilan_Tarih Date not NULL," +
                        "Ilan_ArabaID INT not NULL," +
                        "Ilan_SehirID INT NOT NULL ," +
                        "PRIMARY KEY (IlanID) , " +
                        "CONSTRAINT fk_arabaid  FOREIGN KEY (Ilan_ArabaID) REFERENCES tb1_araba(ArabaID) ON DELETE CASCADE," +
                        "CONSTRAINT fk_sehirid FOREIGN KEY (Ilan_SehirID) REFERENCES tb1_sehir(SehirID) ON DELETE RESTRICT) ";

              statement.executeUpdate(sql);

            } catch (SQLException e) {
                System.out.println("Hata  :  BuildDB.buildB");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public void closeConnection(Connection connection, Statement statement){
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Hata!");
                    e.printStackTrace();
                }
            }

            if(connection!= null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Hata!");
                }
            }
        }

}
