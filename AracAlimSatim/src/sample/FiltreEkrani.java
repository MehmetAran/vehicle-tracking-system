package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Calendar;

public class FiltreEkrani extends Application {
    Stage window,window2;
    TableView<Filtre> table;
    TableView<Filtre> table2 = new TableView<>();

    ComboBox <String> tarihInput,yakitTuruInput,vitesTuruInput, istenilenVeriInput,siralamaInput;

    TextField ilanAdiInput, minKmInput,maxKmInput,markaInput,modelInput,renkInput,sehirInput,minFiyatInput,maxFiyatInput;

    Button filtreleButton;

    VBox vBox2 = new VBox();
    Scene scene2 ;

    TableColumn<Filtre, String> ilanAdiSutun;
    TableColumn<Filtre, Integer> fiyatSutun;
    TableColumn<Filtre, Integer> kmSutun;
    TableColumn<Filtre, Date> tarihSutun;
    TableColumn<Filtre, String> markaSutun;
    TableColumn<Filtre, String> modelSutun;
    TableColumn<Filtre, String> yakitTuruSutun;
    TableColumn<Filtre, String> vitesTuruSutun;
    TableColumn<Filtre, String> renkSutun;
    TableColumn<Filtre, String> sehirSutun;
    TableColumn<Filtre, String> sonuclarSutun;
    String sqlQuery;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window2 = primaryStage;
        window2.setTitle("Filtrelenmiş Sonuçlar");
        window.setTitle("Filtre Tablosu ve Filtreleme");

        ilanAdiSutun = new TableColumn<>("İlan Adı ");
        ilanAdiSutun.setMinWidth(150);
        ilanAdiSutun.setCellValueFactory(new PropertyValueFactory<>("ilanAdi"));

        fiyatSutun = new TableColumn<>("Fiyat");
        fiyatSutun.setMinWidth(50);
        fiyatSutun.setCellValueFactory(new PropertyValueFactory<>("fiyat"));

        kmSutun = new TableColumn<>("Kilometre");
        kmSutun.setMinWidth(50);
        kmSutun.setCellValueFactory(new PropertyValueFactory<>("km"));

        tarihSutun = new TableColumn<>("Tarih");
        tarihSutun.setMinWidth(50);
        tarihSutun.setCellValueFactory(new PropertyValueFactory<>("tarih"));

        markaSutun = new TableColumn<>("Marka");
        markaSutun.setMinWidth(50);
        markaSutun.setCellValueFactory(new PropertyValueFactory<>("marka"));

        modelSutun = new TableColumn<>("Model");
        modelSutun.setMinWidth(50);
        modelSutun.setCellValueFactory(new PropertyValueFactory<>("model"));

        yakitTuruSutun = new TableColumn<>("Yakıt Türü");
        yakitTuruSutun.setMinWidth(50);
        yakitTuruSutun.setCellValueFactory(new PropertyValueFactory<>("yakitTuru"));

        vitesTuruSutun = new TableColumn<>("Vites Türü");
        vitesTuruSutun.setMinWidth(50);
        vitesTuruSutun.setCellValueFactory(new PropertyValueFactory<>("vitesTuru"));

        renkSutun = new TableColumn<>("Renk");
        renkSutun.setMinWidth(50);
        renkSutun.setCellValueFactory(new PropertyValueFactory<>("renk"));

        sehirSutun = new TableColumn<>("Şehir");
        sehirSutun.setMinWidth(50);
        sehirSutun.setCellValueFactory(new PropertyValueFactory<>("sehir"));

        sonuclarSutun = new TableColumn<>("Filtre Sonuçları");
        sonuclarSutun.setCellValueFactory(new PropertyValueFactory<>("filtreSonuclari"));
        sonuclarSutun.setMinWidth(100);

        ilanAdiInput =new TextField();
        ilanAdiInput.setPromptText("İlan Adı");
        ilanAdiInput.setMinWidth(20);

        minFiyatInput = new TextField();
        minFiyatInput.setPromptText("Minimum Fiyat");
        minFiyatInput.setMinWidth(20);

        maxFiyatInput = new TextField();
        maxFiyatInput.setPromptText("Maksimum Fiyat");
        maxFiyatInput.setMinWidth(20);


        minKmInput = new TextField();
        minKmInput.setPromptText("Min Kilometre");
        minKmInput.setMinWidth(20);

        maxKmInput = new TextField();
        maxKmInput.setPromptText("Max Kilometre");
        maxKmInput.setMinWidth(20);


        markaInput = new TextField();
        markaInput.setPromptText("Marka");
        markaInput.setMinWidth(20);

        modelInput = new TextField();
        modelInput.setPromptText("Model");
        modelInput.setMinWidth(20);

        renkInput = new TextField();
        renkInput.setPromptText("Renk");
        renkInput.setMinWidth(20);

        sehirInput = new TextField();
        sehirInput.setPromptText("Şehir");
        sehirInput.setMinWidth(20);

        tarihInput = new ComboBox<>();
        tarihInput.getItems().addAll("Son 24 saat","Son 1 hafta","Son 1 ay");
        tarihInput.setPromptText("Tarih");

        yakitTuruInput = new ComboBox<>();
        yakitTuruInput.getItems().addAll("Benzin","Dizel","LPG");
        yakitTuruInput.setPromptText("Yakıt Türü");

        vitesTuruInput = new ComboBox<>();
        vitesTuruInput.getItems().addAll("Manuel","Otomatik");
        vitesTuruInput.setPromptText("Vites Türü");

        istenilenVeriInput = new ComboBox<>();
        istenilenVeriInput.getItems().addAll("Ilan_Adi","Ilan_Fiyat","Ilan_Km","Ilan_Tarih","Araba_Marka","Araba_Model","YakitTuru","VitesTuru","Renk","Sehir");
        istenilenVeriInput.setPromptText("İstenilen Veri");

        siralamaInput = new ComboBox<>();
        siralamaInput.getItems().addAll("A'dan Z'ye","Z'den A'ya","Artan-Azalan","Azalan-Artan");
        siralamaInput.setPromptText("Sıralama");

        Label label = new Label("Tüm ilanlar aşağıda listelenmiştir.\n\n");

        Button button = new Button("Geri");
        button.setOnAction(event -> buEkranaGeriGel() );


        filtreleButton = new Button();
        filtreleButton.setText("Filtrele");
        filtreleButton.setOnAction(e -> {
            try {
                table2.getColumns().add(sonuclarSutun);
                table2.setItems(getFiltreSonuc());
                vBox2 = new VBox();
                GridPane gridPane = new GridPane();
                gridPane.setPadding(new Insets(10, 10, 10, 10));
                gridPane.setVgap(8);
                gridPane.setHgap(10);
                gridPane.setConstraints(button,25,0);
                gridPane.getChildren().add(button);
                vBox2.getChildren().addAll(table2,gridPane);
                scene2 = new Scene(vBox2);
                window2.setScene(scene2);

                new FiltreSonuclari().start(window2);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });


        Button oncekiEkranButton = new Button("Geri");
        oncekiEkranButton.setOnAction(e -> oncekiEkran());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10,   10));
        hBox.setSpacing(10);

        HBox hBox2 = new HBox();
        hBox2.setPadding(new Insets(10, 10, 10,   10));
        hBox2.setSpacing(10);

        hBox.getChildren().addAll(ilanAdiInput,minFiyatInput,maxFiyatInput, minKmInput,maxKmInput,tarihInput,markaInput);
        hBox2.getChildren().addAll(modelInput,yakitTuruInput,vitesTuruInput,renkInput,sehirInput, istenilenVeriInput,siralamaInput);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        gridPane.setConstraints(filtreleButton,80,0);
        gridPane.setConstraints(oncekiEkranButton,80,1);
        gridPane.getChildren().addAll(filtreleButton,oncekiEkranButton);

        table = new TableView<>();
        table.setItems(getFiltre());
        table.getColumns().addAll(ilanAdiSutun,fiyatSutun,kmSutun,tarihSutun,markaSutun,modelSutun,yakitTuruSutun,vitesTuruSutun,renkSutun,sehirSutun);


        VBox vBox = new VBox();
        vBox.getChildren().addAll(label,table, hBox,hBox2,gridPane);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();

    }

    public ObservableList<Filtre> getFiltre() {

        ObservableList<Filtre> filtres = FXCollections.observableArrayList();
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = new BuildDB().databaseConnection(connection);
            filtres=FXCollections.observableArrayList();
            String sql = "SELECT Ilan_Adi ," +
                    "Ilan_Fiyat," +
                    "Ilan_Km," +
                    "Ilan_Tarih," +
                    "Araba_Marka," +
                    "Araba_Model," +
                    "YakitTuru," +
                    "VitesTuru," +
                    "Renk," +
                    "Sehir FROM tb1_ilan ,tb1_araba,tb1_sehir,tb1_vitesturu,tb1_yakitturu,tb1_renk " +
                    "WHERE Ilan_ArabaID = ArabaID and " +
                    "Ilan_SehirID = SehirID and " +
                    "Araba_YakitTuruID = YakitTuruID and " +
                    "Araba_VitesTuruID = VitesTuruID and " +
                    "Araba_RenkID = RenkID";
            rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                filtres.add(new Filtre(rs.getString(1),rs.getInt(2),rs.getInt(3),rs.getDate(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10)));
            }
        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }

        table.setItems(null);
        table.setItems(filtres);

        return  filtres;
    }

    public void oncekiEkran(){
        window.close();
        try {
            new GirisEkrani().start(window);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buEkranaGeriGel(){
        window2.close();
        try {
            new FiltreEkrani().start(window);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Filtre> getFiltreSonuc(){

        ObservableList<Filtre> filtreSonuclari = FXCollections.observableArrayList();
        Connection connection = null;
        ResultSet rs = null;
        int andKontrol = 0;
        connection = new BuildDB().databaseConnection(connection);

        if(istenilenVeriInput.getValue() != null) {
            sqlQuery = "Select " +  istenilenVeriInput.getValue() + " FROM tb1_Ilan,tb1_araba,tb1_vitesturu,tb1_yakitturu,tb1_renk, tb1_sehir WHERE ";

            if (!ilanAdiInput.getText().isEmpty()) {
                if (andKontrol != 0) {
                    sqlQuery += " and ";
                }
                andKontrol++;
                sqlQuery += " Ilan_Adi = '" + ilanAdiInput.getText() + "' ";
            }
            if (!minFiyatInput.getText().isEmpty()) {
                if (andKontrol != 0) {
                    sqlQuery += " and ";
                }else {
                    andKontrol++;
                }
                int fiyat = Integer.parseInt(minFiyatInput.getText());
                sqlQuery += " Ilan_Fiyat > '" + fiyat + "' ";
            }


            if (!maxFiyatInput.getText().isEmpty()) {
                if (andKontrol != 0) {
                    sqlQuery += " and ";
                }else {
                    andKontrol++;
                }
                int fiyat = Integer.parseInt(maxFiyatInput.getText());
                sqlQuery += " Ilan_Fiyat < '" + fiyat + "' ";
            }

            if (!minKmInput.getText().isEmpty()) {
                if (andKontrol != 0) {
                    sqlQuery += " and ";
                }else {
                    andKontrol++;
                }
                int km = Integer.parseInt(minKmInput.getText());
                sqlQuery += " Ilan_Km > '" + km + "' ";
            }


            if (!maxKmInput.getText().isEmpty()) {
                if (andKontrol != 0) {
                    sqlQuery += " and ";
                }else {
                    andKontrol++;
                }
                int km = Integer.parseInt(maxKmInput.getText());
                sqlQuery += " Ilan_Km < '" + km + "' ";
            }
            if (tarihInput.getValue() != null) {
                if (andKontrol != 0) {
                    sqlQuery += " and ";
                }else {

                    andKontrol++;
                }
                Calendar calendar = Calendar.getInstance();
                java.sql.Date tarihBugun = new java.sql.Date(calendar.getTime().getTime());
                if(tarihInput.getValue() == "Son 24 saat") {
                    sqlQuery += " DATEDIFF(Ilan_Tarih,'" + tarihBugun + "'" + ")<=1";
                }
                else if(tarihInput.getValue() == "Son 1 hafta") {
                    sqlQuery += " DATEDIFF(Ilan_Tarih,'" + tarihBugun + "'" + ")<=7";
                }
                else if(tarihInput.getValue() == "Son 1 ay"){
                    sqlQuery += " DATEDIFF(Ilan_Tarih,'"+tarihBugun+"'"+")<=30";

                }

            }

            if (!markaInput.getText().isEmpty()) {
                if (andKontrol != 0) {
                    sqlQuery += " and ";
                }
                andKontrol++;
                sqlQuery += " Araba_Marka = '" + markaInput.getText() + "' ";
            }


            if (!modelInput.getText().isEmpty()) {
                if (andKontrol != 0) {
                    sqlQuery += " and ";
                }
                andKontrol++;
                sqlQuery += " Araba_Model = '" + modelInput.getText() + "' ";
            }

            if (yakitTuruInput.getValue() != null) {
                if (andKontrol != 0) {
                    sqlQuery += " and ";
                }else {
                    andKontrol++;
                }
                sqlQuery += " YakitTuru = '" + yakitTuruInput.getValue() + "'";

            }

            if (vitesTuruInput.getValue() != null) {
                if (andKontrol != 0) {
                    sqlQuery += " and ";
                }else {
                    andKontrol++;
                }
                sqlQuery += " VitesTuru = '" + vitesTuruInput.getValue() + "'";

            }


            if (!renkInput.getText().isEmpty()) {
                if (andKontrol != 0) {
                    sqlQuery += " and ";
                }else {
                    andKontrol++;
                }
                sqlQuery += " Renk = '" + renkInput.getText() + "'";

            }


            if (!sehirInput.getText().isEmpty()) {
                if (andKontrol != 0) {
                    sqlQuery += " and ";
                }else {
                    andKontrol++;
                }
                sqlQuery += " Sehir = '" + sehirInput.getText() + "'";

            }


        }

        if(  andKontrol == 0){
            sqlQuery += " Ilan_ArabaID = ArabaID and " +
                    "Ilan_SehirID = SehirID and " +
                    "Araba_YakitTuruID = YakitTuruID and " +
                    "Araba_VitesTuruID = VitesTuruID and " +
                    "Araba_RenkID = RenkID";
        }else {
            sqlQuery += " and  Ilan_ArabaID = ArabaID and " +
                    "Ilan_SehirID = SehirID and " +
                    "Araba_YakitTuruID = YakitTuruID  and " +
                    "Araba_VitesTuruID = VitesTuruID and " +
                    "Araba_RenkID = RenkID";
        }
        if (siralamaInput != null) {
            if(siralamaInput.getValue() == "Z'den A'ya" || siralamaInput.getValue() == "Artan-Azalan")
            sqlQuery += " order by " + istenilenVeriInput.getValue() + " DESC ";

        }

        try {
            connection = new BuildDB().databaseConnection(connection);
            rs = connection.createStatement().executeQuery(sqlQuery);
            while (rs.next()) {
                if (istenilenVeriInput.getValue() == "Ilan_Fiyat" || istenilenVeriInput.getValue() == "Ilan_Km"  ) {
                    String sonuc = String.valueOf(rs.getInt(1));
                    Filtre filtre = new Filtre();
                    filtre.setFiltreSonuclari(sonuc);
                    filtreSonuclari.add(filtre);
                } else if(istenilenVeriInput.getValue() == "Ilan_Tarih"){
                    String sonuc = String.valueOf(rs.getDate(1));
                    Filtre filtre = new Filtre();
                    filtre.setFiltreSonuclari(sonuc);
                    filtreSonuclari.add(filtre);
                }else{
                    String sonuc = rs.getString(1);
                    Filtre filtre = new Filtre();
                    filtre.setFiltreSonuclari(sonuc);
                    filtreSonuclari.add(filtre);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }
        System.out.println(sqlQuery);
        table2.setItems(null);
        table2.setItems(filtreSonuclari);
        System.out.println(table2.getItems());
        return  filtreSonuclari;
    }

}
