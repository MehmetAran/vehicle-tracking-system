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

public class RenkEkrani extends Application implements IIslemler{

    Stage window;
    TableView<Renk> table;
    TextField renkInput;
    TableColumn<Renk,Integer> renkIDSutun;
    TableColumn<Renk, String> renkSutun;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Renk Tablosu Kontrol");

        renkIDSutun = new TableColumn<>("Renk ID");
        renkIDSutun.setMinWidth(50);
        renkIDSutun.setCellValueFactory(new PropertyValueFactory<>("renkID"));

        renkSutun = new TableColumn<>("Renk");
        renkSutun.setMinWidth(50);
        renkSutun.setCellValueFactory(new PropertyValueFactory<>("renk"));

        renkInput = new TextField();
        renkInput.setPromptText("Renk");
        renkInput.setMinWidth(150);


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
        hBox.getChildren().addAll(renkInput, ekleButton, silButton, güncelleButton, oncekiEkranButton);

        table = new TableView<>();
        table.setItems(getRenk());
        table.getColumns().addAll(renkIDSutun, renkSutun);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();

    }

    public ObservableList<Renk> getRenk() {

        ObservableList<Renk> renks = FXCollections.observableArrayList();
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = new BuildDB().databaseConnection(connection);
            renks=FXCollections.observableArrayList();
            rs=connection.createStatement().executeQuery("SELECT * FROM tb1_renk ");
            while (rs.next()) {
                renks.add(new Renk(rs.getInt(1), rs.getString(2) ));
            }
        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }

        table.setItems(null);
        table.setItems(renks);

        return  renks;

    }

    public void kayitEkle(){
        Connection connection = null;
        ResultSet rs = null;


        String sql;
        sql = "INSERT INTO tb1_renk(Renk) VALUES(?)";
        String eklenen = renkInput.getText();

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
        String sql = "DELETE FROM tb1_renk WHERE " +
                "RenkID = ? ";
        Connection connection = null;

        ObservableList<Renk> secilen;

        secilen = table.getSelectionModel().getSelectedItems();

        int id = secilen.get(0).getRenkID();



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

        String sql = "UPDATE tb1_renk SET"
                + " Renk = ?  WHERE "
                + " RenkID = ?";

        ObservableList<Renk> secilen;

        secilen = table.getSelectionModel().getSelectedItems();

        int id = secilen.get(0).getRenkID();
        String guncellenen = renkInput.getText();
        System.out.println("Güncellenen : " + guncellenen + " id : " + id);
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


