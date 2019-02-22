package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class GirisEkrani extends Application{
    Stage window;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Connection connection = null;
        Statement statement = null;
        connection = new BuildDB().databaseConnection(connection);
        statement = connection.createStatement();
        new BuildDB().buildTable(connection,statement);

        window = primaryStage;
        window.setTitle("Mehmet Holding Araç Alım Satım");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        Label label = new Label("Aşağıdan işlem seçin!");
        gridPane.setConstraints(label,0,0);

        Label label1 = new Label("İlan Tablosu için (git) tıklayın");
        gridPane.setConstraints(label1,0,1);

        Button button1 = new Button();
        button1.setText("git");
        button1.setOnAction(e -> {
            try {
                window.close();
                new IlanEkrani().start(window);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        GridPane.setConstraints(button1,1, 1);

        Label label2 = new Label("Şehir Tablosu için (git) tıklayın");
        gridPane.setConstraints(label2,0,2);

        Button button2 = new Button();
        button2.setText("git");
        button2.setOnAction(e -> {
            try {
                window.close();
                new SehirEkrani().start(window);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        gridPane.setConstraints(button2,1,2);


        Label label3 = new Label("Vites Türü Tablosu için (git) tıklayın");
        gridPane.setConstraints(label3,0,3);

        Button button3 = new Button();
        button3.setText("git");
        button3.setOnAction(e -> {
            try {
                window.close();
                new VitesTuruEkrani().start(window);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        gridPane.setConstraints(button3,1,3);

        Label label4 = new Label("Renk Tablosu için (git) tıklayın");
        gridPane.setConstraints(label4,0,4);

        Button button4 = new Button();
        button4.setText("git");
        button4.setOnAction(e -> {
            try {
                window.close();
                new RenkEkrani().start(window);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        gridPane.setConstraints(button4,1,4);

        Label label5 = new Label("Yakıt Türü Tablosu için (git) tıklayın");
        gridPane.setConstraints(label5,0,5);

        Button button5 = new Button();
        button5.setText("git");
        button5.setOnAction(e -> {
            try {
                window.close();
                new YakitTuruEkrani().start(window);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        gridPane.setConstraints(button5,1,5);


        Label label6 = new Label("Araba Tablosu için (git) tıklayın");
        gridPane.setConstraints(label6,0,6);

        Button button6 = new Button();
        button6.setText("git");
        button6.setOnAction(e -> {
            try {
                window.close();
                new ArabaEkrani().start(window);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        gridPane.setConstraints(button6,1,6);

        Label label7 = new Label("Filtrelemek için (git) tıklayın");
        gridPane.setConstraints(label7,0,7);

        Button button7 = new Button();
        button7.setText("git");
        button7.setOnAction(e -> {
            try {
                window.close();
                new FiltreEkrani().start(window);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        gridPane.setConstraints(button7,1,7);


        gridPane.getChildren().addAll(label,label1,button1,label2,button2,label3,button3,label4,button4 , label5 , button5 , label6,button6,label7,button7);

        Scene scene = new Scene(gridPane,400,300);
        window.setScene(scene);
        window.show();

    }
}
