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

public class ArabaEkrani extends Application implements IIslemler{

    Stage window;
    TableView<Araba> table;
    TextField renkIDInput,vitesTuruIDInput,yakitTuruIDInput,markaInput,modelInput;
    TableColumn<Araba, Integer> arabaIDSutun;
    TableColumn<Araba, Integer> renkIDSutun;
    TableColumn<Araba, Integer> yakitTuruIDSutun;
    TableColumn<Araba, String>  markaSutun;
    TableColumn<Araba, String>  modelSutun;
    TableColumn<Araba, Integer> vitesTuruIDSutun;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Araba Tablosu Kontrol");

        arabaIDSutun = new TableColumn<>("Araba ID");
        arabaIDSutun.setMinWidth(50);
        arabaIDSutun.setCellValueFactory(new PropertyValueFactory<>("arabaID"));

        renkIDSutun = new TableColumn<>("Renk ID");
        renkIDSutun.setMinWidth(50);
        renkIDSutun.setCellValueFactory(new PropertyValueFactory<>("renkID"));

        vitesTuruIDSutun= new TableColumn<>("Yakıt Türü ID");
        vitesTuruIDSutun.setMinWidth(50);
        vitesTuruIDSutun.setCellValueFactory(new PropertyValueFactory<>("yakitTuruID"));

        yakitTuruIDSutun= new TableColumn<>("Vites Türü ID");
        yakitTuruIDSutun.setMinWidth(50);
        yakitTuruIDSutun.setCellValueFactory(new PropertyValueFactory<>("vitesTuruID"));

        markaSutun= new TableColumn<>("Marka");
        markaSutun.setMinWidth(50);
        markaSutun.setCellValueFactory(new PropertyValueFactory<>("marka"));

        modelSutun= new TableColumn<>("Model");
        modelSutun.setMinWidth(50);
        modelSutun.setCellValueFactory(new PropertyValueFactory<>("model"));

        renkIDInput =new TextField();
        renkIDInput.setPromptText("Renk ID");
        renkIDInput.setMinWidth(150);

        vitesTuruIDInput =new TextField();
        vitesTuruIDInput.setPromptText("Vites Türü ID");
        vitesTuruIDInput.setMinWidth(150);

        yakitTuruIDInput = new TextField();
        yakitTuruIDInput.setPromptText("YakıtTuru ID");
        yakitTuruIDInput.setMinWidth(150);

        markaInput = new TextField();
        markaInput.setPromptText("Marka");
        markaInput.setMinWidth(150);

        modelInput = new TextField();
        modelInput.setPromptText("Model");
        modelInput.setMinWidth(150);


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
        hBox.getChildren().addAll(markaInput,modelInput,vitesTuruIDInput,yakitTuruIDInput,renkIDInput, ekleButton, silButton, güncelleButton, oncekiEkranButton);

        table = new TableView<>();
        table.setItems(getAraba());
        table.getColumns().addAll(arabaIDSutun,markaSutun,modelSutun,vitesTuruIDSutun,yakitTuruIDSutun,renkIDSutun);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();

    }

    public ObservableList<Araba> getAraba() {

        ObservableList<Araba> arabas = FXCollections.observableArrayList();
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = new BuildDB().databaseConnection(connection);
            arabas=FXCollections.observableArrayList();
            rs=connection.createStatement().executeQuery("SELECT * FROM tb1_araba ");
            while (rs.next()) {
                arabas.add(new Araba(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6)));
            }
        } catch (SQLException ex) {
            System.err.println("Hata"+ex);
        }

        table.setItems(null);
        table.setItems(arabas);

        return  arabas;

    }

    public void kayitEkle(){
        Connection connection = null;
        ResultSet rs = null;


        String sql;
        sql = "INSERT INTO tb1_araba(Araba_Marka,Araba_Model,Araba_VitesTuruID,Araba_YakitTuruID,Araba_RenkID) VALUES(?,?,?,?,?)";
        String eklenen1 = markaInput.getText();
        String eklenen2 = modelInput.getText();
        int eklenen3 = Integer.parseInt(vitesTuruIDInput.getText());
        int eklenen4 = Integer.parseInt(yakitTuruIDInput.getText());
        int eklenen5 = Integer.parseInt(renkIDInput.getText());

        try {
            connection = new BuildDB().databaseConnection(connection);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,eklenen1);
            preparedStatement.setString(2,eklenen2);
            preparedStatement.setInt(3, eklenen3);
            preparedStatement.setInt(4, eklenen4);
            preparedStatement.setInt(5, eklenen5);

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
        String sql = "DELETE FROM tb1_araba WHERE " +
                "ArabaID = ? ";
        Connection connection = null;

        ObservableList<Araba> secilen;

        secilen = table.getSelectionModel().getSelectedItems();

        int id = secilen.get(0).getArabaID();



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

        String sql = "UPDATE tb1_araba SET" +
                " Araba_Marka = ? , " +
                " Araba_Model = ? ," +
                " Araba_VitesTuruID = ? ," +
                " Araba_YakitTuruID = ? ," +
                " Araba_RenkID = ? " +
                " WHERE " +
                " ArabaID = ?";

        ObservableList<Araba> secilen;

        secilen = table.getSelectionModel().getSelectedItems();

        int id = secilen.get(0).getArabaID();

        String guncellenen1 = markaInput.getText();
        String guncellenen2 = modelInput.getText();
        int guncellenen3 = Integer.parseInt(vitesTuruIDInput.getText());
        int guncellenen4 = Integer.parseInt(yakitTuruIDInput.getText());
        int guncellenen5 = Integer.parseInt(renkIDInput.getText());

        System.out.println("Güncellenen ID : " + id);
        try {

            connection = new BuildDB().databaseConnection(connection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, guncellenen1);
            preparedStatement.setString(2, guncellenen2);
            preparedStatement.setInt(3, guncellenen3);
            preparedStatement.setInt(4, guncellenen4);
            preparedStatement.setInt(5, guncellenen5);
            preparedStatement.setInt(6, id);
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


