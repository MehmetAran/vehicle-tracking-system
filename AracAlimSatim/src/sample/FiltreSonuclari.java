package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class FiltreSonuclari extends Application {
    Stage window;
    ObservableList<String> sonuclar;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.show();

    }

}
