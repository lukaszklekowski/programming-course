package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**\mainpage Program do symulacji
 *  @author Łukasz Klekowski
 *  @version 1.0
 *  @since 15-05-2016
 */

/**  Główna klasa, rozpoczynająca działanie programu.
 *  Znajduje sie w niej publiczna metoda start otwierająca główne okno
 *  programu.
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Symulacja");
        primaryStage.setScene(new Scene(root, 503, 95));
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
