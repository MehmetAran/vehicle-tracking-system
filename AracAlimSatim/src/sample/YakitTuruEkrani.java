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

public class YakitTuruEkrani extends Application implements IIslemler{

    Stage window;
    TableView<YakitTuru> table;
    TextField yakitTuruInput;
    TableColumn<YakitTuru,Integer> yakitTuruIDSutun;
    TableColumn<YakitTuru, String> yakitTuruSutun;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Yakıt Turu Tablosu Kontrol");

        yakitTuruIDSutun = new TableColumn<>("Yakıt Türü ID");
        yakitTuruIDSutun.setMinWidth(50);
        yakitTuruIDSutun.setCellValueFactory(new PropertyValueFactory<>("yakitTuruID"));

        yakitTuruSutun = new TableColumn<>("Yakıt Türü");
        yakitTuruSutun.setMinWidth(50);
        yakitTuruSutun.setCellValueFactory(new PropertyValueFactory<>("yakitTuru"));

        yakitTuruInput = new TextField();
        yakitTuruInput.setPromptText("Yakıt Türü");
        yakitTuruInput.setMinWidth(150);


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
        hBox.getChildren().addAll(yakitTuruInput, ekleButton, silButton, güncelleButton, oncekiEkranButton);

        table = new TableView<>();
        table.setItems(getYakitTuru());
        table.getColumns().addAll(yakitTuruIDSutun, yakitTuruSutun);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();

    }

    public ObservableList<YakitTuru> getYakitTuru() {

        ObservableList<YakitTuru> yakitTurus = FXCollections.observableArrayList();
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = new BuildDB().databaseConnection(connection);
            yakitTurus=FXCollections.observableArrayList();
            rs=connection.createStatement().executeQuery("SELECT * FROM tb1_yakitturu ");
            while (rs.next()) {
                yakitTurus.add(new YakitTuru(rs.getInt(1), rs.getString(2) ));
            }
        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }

        table.setItems(null);
        table.setItems(yakitTurus);

        return  yakitTurus;

    }

    public void kayitEkle(){
        Connection connection = null;
        ResultSet rs = null;


        String sql;
        sql = "INSERT INTO tb1_yakitturu(YakitTuru) VALUES(?)";
        String eklenen = yakitTuruInput.getText();

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
        String sql = "DELETE FROM tb1_yakitturu WHERE " +
                "YakitTuruID = ? ";
        Connection connection = null;

        ObservableList<YakitTuru> secilen;

        secilen = table.getSelectionModel().getSelectedItems();

        int id = secilen.get(0).getYakitTuruID();



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

        String sql = "UPDATE tb1_YakitTuru SET"
                + " YakitTuru = ?  WHERE "
                + " YakitTuruID = ?";

        ObservableList<YakitTuru> secilen;

        secilen = table.getSelectionModel().getSelectedItems();

        int id = secilen.get(0).getYakitTuruID();
        String guncellenen = yakitTuruInput.getText();
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


