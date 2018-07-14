package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**\mainpage Edytor Graficzny
 *  @author Łukasz Klekowski
 *  @version 1.0
 *  @since 06-05-2016
 *  \n\n
 *  Edytor graficzny służący do rysowania prostych figur geometrycznych.
 */

 /**  Główna klasa, rozpoczynająca działanie programu.
 *  Znajduje sie w niej publiczna metoda start otwierająca główne okno
 *  programu.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Edytor graficzny");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
        primaryStage.setResizable(false);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
