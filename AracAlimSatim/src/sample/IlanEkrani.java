package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IlanEkrani extends Application implements  IIslemler{
    Stage window;
    TableView<Ilan> table;
    TextField ilanAdiInput, fiyatInput,kmInput,arabaIDinput,sehirIDinput;
    TableColumn<Ilan,Integer> ilanIDSutun;
    TableColumn<Ilan, String> ilanAdiSutun;
    TableColumn<Ilan, Integer> fiyatSutun;
    TableColumn<Ilan, Integer> kmSutun;
    TableColumn<Ilan, Date> tarihSutun;
    TableColumn<Ilan, Integer> arabaIDSutun;
    TableColumn<Ilan, Integer> sehirIDSutun;


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Renk Tablosu Kontrol");

        ilanIDSutun = new TableColumn<>("İlan ID ");
        ilanIDSutun.setMinWidth(50);
        ilanIDSutun.setCellValueFactory(new PropertyValueFactory<>("ilanID"));

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

        arabaIDSutun = new TableColumn<>("Araba ID");
        arabaIDSutun.setMinWidth(50);
        arabaIDSutun.setCellValueFactory(new PropertyValueFactory<>("arabaID"));

        sehirIDSutun = new TableColumn<>("Şehir ID");
        sehirIDSutun.setMinWidth(50);
        sehirIDSutun.setCellValueFactory(new PropertyValueFactory<>("sehirID"));

        ilanAdiInput =new TextField();
        ilanAdiInput.setPromptText("İlan Adı");
        ilanAdiInput.setMinWidth(150);

        fiyatInput =new TextField();
        fiyatInput.setPromptText("Fiyat");
        fiyatInput.setMinWidth(150);

        kmInput =new TextField();
        kmInput.setPromptText("Kilometre");
        kmInput.setMinWidth(150);

        arabaIDinput =new TextField();
        arabaIDinput.setPromptText("Araba ID");
        arabaIDinput.setMinWidth(150);

        sehirIDinput =new TextField();
        sehirIDinput.setPromptText("Şehir ID");
        sehirIDinput.setMinWidth(150);

        Button ekleButton = new Button("Ekle");
        ekleButton.setOnAction(e->kayitEkle());

        Button silButton = new Button("Sil");
        silButton.setOnAction(e ->kayitSil());

        Button güncelleButton = new Button("Güncelle");
        güncelleButton.setOnAction(e -> kayitGüncelle());

        Button oncekiEkranButton = new Button("Geri");
        oncekiEkranButton.setOnAction(e -> oncekiEkran());



        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(ilanAdiInput, fiyatInput, kmInput, arabaIDinput, sehirIDinput, ekleButton, silButton, güncelleButton, oncekiEkranButton);

        table = new TableView<>();
        table.setItems(getIlan());
        table.getColumns().addAll(ilanIDSutun, ilanAdiSutun, fiyatSutun, kmSutun, tarihSutun, arabaIDSutun, sehirIDSutun);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();

    }

    public ObservableList<Ilan> getIlan() {

        ObservableList<Ilan> ilans = FXCollections.observableArrayList();
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = new BuildDB().databaseConnection(connection);
            ilans=FXCollections.observableArrayList();
            rs=connection.createStatement().executeQuery("SELECT * FROM tb1_ilan ");
            while (rs.next()) {
                ilans.add(new Ilan(rs.getInt(1), rs.getString(2) ,rs.getInt(3),rs.getInt(4),rs.getDate(5),rs.getInt(6),rs.getInt(7)));
            }
        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }

        table.setItems(null);
        table.setItems(ilans);

        return  ilans;

    }

    public void kayitEkle(){
        Connection connection = null;
        ResultSet rs = null;


        String sql;
        sql = "INSERT INTO tb1_ilan(Ilan_Adi,Ilan_Fiyat,Ilan_Km,Ilan_Tarih,Ilan_ArabaID,Ilan_SehirID) VALUES(?,?,?,?,?,?)";
        String eklenen1 = ilanAdiInput.getText();
        int eklenen2 = Integer.parseInt(fiyatInput.getText());
        int eklenen3 = Integer.parseInt(kmInput.getText());
        Calendar calendar = Calendar.getInstance();
        java.sql.Date eklenen4 = new java.sql.Date(calendar.getTime().getTime());
        int eklenen5 = Integer.parseInt(arabaIDinput.getText());
        int eklenen6 = Integer.parseInt(sehirIDinput.getText());

        try {
            connection = new BuildDB().databaseConnection(connection);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, eklenen1);
            preparedStatement.setInt(2, eklenen2);
            preparedStatement.setInt(3, eklenen3);
            preparedStatement.setDate(4, eklenen4);
            preparedStatement.setInt(5, eklenen5);
            preparedStatement.setInt(6, eklenen6);
            preparedStatement.executeUpdate();

            window.close();
            start(window);


            System.out.println("Başarıyla eklendi.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void kayitSil(){
        String sql = "DELETE FROM tb1_ilan WHERE " +
                "IlanID = ? ";
        Connection connection = null;

        ObservableList<Ilan> secilen;

        secilen = table.getSelectionModel().getSelectedItems();

        int id = secilen.get(0).getIlanID();



        try {
            connection = new BuildDB().databaseConnection(connection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            window.close();
            start(window);

            System.out.println("Başarıyla silindi.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void kayitGüncelle(){
        Connection connection = null;

        String sql = "UPDATE tb1_ilan SET " +
                "Ilan_Adi = ? , " +
                "Ilan_Fiyat = ? , " +
                "Ilan_Km = ? ," +
                "Ilan_Tarih = ? ," +
                "Ilan_ArabaID = ? ," +
                "Ilan_SehirID  = ? " +
                "WHERE "
                + " IlanID = ? ";

        ObservableList<Ilan> secilen;

        secilen = table.getSelectionModel().getSelectedItems();
        int id = secilen.get(0).getIlanID();

        String guncellenen1 = ilanAdiInput.getText();
        int guncellenen2 = Integer.parseInt(fiyatInput.getText());
        int guncellenen3 = Integer.parseInt(kmInput.getText());
        Calendar calendar = Calendar.getInstance();
        java.sql.Date guncellenen4 = new java.sql.Date(calendar.getTime().getTime());
        int guncellenen5 = Integer.parseInt(arabaIDinput.getText());
        int guncellenen6 = Integer.parseInt(sehirIDinput.getText());



        System.out.println("Güncellene  id : " + id);
        try {

            connection = new BuildDB().databaseConnection(connection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, guncellenen1);
            preparedStatement.setInt(2, guncellenen2);
            preparedStatement.setInt(3, guncellenen3);
            preparedStatement.setDate(4, guncellenen4);
            preparedStatement.setInt(5, guncellenen5);
            preparedStatement.setInt(6, guncellenen6);
            preparedStatement.setInt(7,id);
            preparedStatement.executeUpdate();

            window.close();

            start(window);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void oncekiEkran(){
        window.close();
        try {
            new GirisEkrani().start(window);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
