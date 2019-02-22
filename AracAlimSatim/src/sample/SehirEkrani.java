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

public class SehirEkrani extends Application implements  IIslemler{

        Stage window;
        TableView<Sehir> table;
        TextField sehirInput;
        TableColumn<Sehir,Integer> sehirIDSutun;
        TableColumn<Sehir, String> sehirSutun;

        @Override
        public void start(Stage primaryStage) throws Exception {
            window = primaryStage;
            window.setTitle("Şehir Tablosu Kontrol");

            sehirIDSutun = new TableColumn<>("Şehir ID");
            sehirIDSutun.setMinWidth(50);
            sehirIDSutun.setCellValueFactory(new PropertyValueFactory<>("sehirID"));

            sehirSutun = new TableColumn<>("Şehir");
            sehirSutun.setMinWidth(50);
            sehirSutun.setCellValueFactory(new PropertyValueFactory<>("sehir"));

            sehirInput = new TextField();
            sehirInput.setPromptText("Şehir");
            sehirInput.setMinWidth(150);


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
            hBox.getChildren().addAll(sehirInput, ekleButton, silButton, güncelleButton, oncekiEkranButton);

            table = new TableView<>();
            table.setItems(getSehir());
            table.getColumns().addAll(sehirIDSutun,sehirSutun);

            VBox vBox = new VBox();
            vBox.getChildren().addAll(table, hBox);

            Scene scene = new Scene(vBox);
            window.setScene(scene);
            window.show();

        }

        public ObservableList<Sehir> getSehir() {

            ObservableList<Sehir> sehirs = FXCollections.observableArrayList();
            Connection connection = null;
            ResultSet rs = null;

            try {
                connection = new BuildDB().databaseConnection(connection);
                sehirs=FXCollections.observableArrayList();
                rs=connection.createStatement().executeQuery("SELECT * FROM tb1_sehir ");
                while (rs.next()) {
                    sehirs.add(new Sehir(rs.getInt(1), rs.getString(2) ));
                }
            } catch (SQLException ex) {
                System.err.println("Error"+ex);
            }

            table.setItems(null);
            table.setItems(sehirs);

            return  sehirs;

        }

    public void kayitEkle(){
        Connection connection = null;
        ResultSet rs = null;


        String sql;
        sql = "INSERT INTO tb1_sehir(Sehir) VALUES(?)";
        String eklenen = sehirInput.getText();

        try {
            connection = new BuildDB().databaseConnection(connection);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,eklenen);

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
        String sql = "DELETE FROM tb1_sehir WHERE " +
                "SehirID = ? ";
        Connection connection = null;

        ObservableList<Sehir> secilenSehir;

        secilenSehir = table.getSelectionModel().getSelectedItems();

        int id = secilenSehir.get(0).getSehirID();



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

        String sql = "UPDATE tb1_sehir SET"
                + " Sehir = ?  WHERE "
                + " SehirID = ?";

        ObservableList<Sehir> secilenSehir;

        secilenSehir = table.getSelectionModel().getSelectedItems();

        int id = secilenSehir.get(0).getSehirID();
        String guncellenen = sehirInput.getText();
        System.out.println("Güncellenen : " + guncellenen + "id : " + id);
        try {

            connection = new BuildDB().databaseConnection(connection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, guncellenen);
            preparedStatement.setInt(2, id);
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


