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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class VitesTuruEkrani extends Application implements IIslemler{

    Stage window;
    TableView<VitesTuru> table;
    TextField vitesTuruInput;
    TableColumn<VitesTuru,Integer> vitesTuruIDSutun;
    TableColumn<VitesTuru, String> vitesTuruSutun;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
            window.setTitle("Vites Türü Tablosu Kontrol");

        vitesTuruIDSutun = new TableColumn<>("Vites Türü ID");
        vitesTuruIDSutun.setMinWidth(50);
        vitesTuruIDSutun.setCellValueFactory(new PropertyValueFactory<>("vitesTuruID"));

        vitesTuruSutun = new TableColumn<>("Vites Türü");
        vitesTuruSutun.setMinWidth(50);
        vitesTuruSutun.setCellValueFactory(new PropertyValueFactory<>("vitesTuru"));

        vitesTuruInput = new TextField();
        vitesTuruInput.setPromptText("Vites türü");
        vitesTuruInput.setMinWidth(150);


        Button ekleButton = new Button("Ekle");
        ekleButton.setOnAction(e->kayitEkle());

        Button silButton = new Button("Sil");
        silButton.setOnAction(e ->kayitSil());

        Button güncelleButton = new Button("Güncelle");
        güncelleButton.setOnAction(e -> kayitGüncelle());

        Button oncekiEkranButton = new Button("Geri");
        oncekiEkranButton.setOnAction(e ->  oncekiEkran() );

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(vitesTuruInput, ekleButton, silButton, güncelleButton, oncekiEkranButton);

        table = new TableView<>();
        table.setItems(getVitesTuru());
        table.getColumns().addAll(vitesTuruIDSutun,vitesTuruSutun);


        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();

    }

    public ObservableList<VitesTuru> getVitesTuru() {

        ObservableList<VitesTuru> vitesTurus = FXCollections.observableArrayList();
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = new BuildDB().databaseConnection(connection);
            vitesTurus=FXCollections.observableArrayList();
            rs=connection.createStatement().executeQuery("SELECT * FROM tb1_vitesturu ");
            while (rs.next()) {
                vitesTurus.add(new VitesTuru(rs.getInt(1), rs.getString(2) ));
            }
        } catch (SQLException ex) {
            System.err.println("Hata\n"+ex);
        }

        table.setItems(null);
        table.setItems(vitesTurus);

        return  vitesTurus;

    }

    public void kayitEkle(){
        Connection connection = null;
        ResultSet rs = null;


        String sql;
        sql = "INSERT INTO tb1_vitesturu(VitesTuru) VALUES(?)";
        String eklenen = vitesTuruInput.getText();

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
        String sql = "DELETE FROM tb1_vitesturu WHERE " +
                "VitesTuruID = ? ";
        Connection connection = null;

        ObservableList<VitesTuru> secilenVitesTuru;

        secilenVitesTuru = table.getSelectionModel().getSelectedItems();

        int id = secilenVitesTuru.get(0).getVitesTuruID();



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

    public void kayitGüncelle() {
        Connection connection = null;

        String sql = "UPDATE tb1_vitesturu SET"
                + " VitesTuru = ?  WHERE "
                + " VitesTuruID = ?";

        ObservableList<VitesTuru> secilen;

        secilen = table.getSelectionModel().getSelectedItems();

        int id = secilen.get(0).getVitesTuruID();
        String guncellenen = vitesTuruInput.getText();
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


