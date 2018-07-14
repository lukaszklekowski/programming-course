package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

    /**
     * Klasa odpowiadająca za działanie głównego okna programu
    * gdzie użytkownik może rysować figury
    */

public class MainController {

    public Pane pane;
    public MenuItem circle;
    public MenuItem rectangle;
    public MenuItem polygon;
    public MenuItem Info;
    public MenuBar menubar;

    /**
     * Metoda DrawCircle() wywołuje sie po kliknięciu na przycisk Koło w menu.
     * Tworzy ona nowy obiekt - koło.
     */
    public void DrawCircle(ActionEvent actionEvent) {Circle1 cir= new Circle1(pane,menubar);}

    /**
     * Metoda DrawRectangle() wywołuje sie po kliknięciu na przycisk Prostokąt w menu.
     * Tworzy ona nowy obiekt - prostokąt.
     */
    public void DrawRectangle(ActionEvent actionEvent) {Rectangle1 rec = new Rectangle1(pane,menubar);}
    /**
     * Metoda DrawPolygon() wywołuje sie po kliknięciu na przycisk Wielokąt w menu.
     * Tworzy ona nowy obiekt - wielokąt.
     */
    public void DrawPolygon (ActionEvent actionEvent) {Polygon1 pol = new Polygon1(pane,menubar);}

    /**
     * Metoda infowindow() wywołuje sie po kliknięciu na przycisk Info w menu.
     * Po jej uruchomieniu pojawia się okno z informacjami na temat programu.
     */
    public void infowindow(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Info.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Info");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Problem z wczytaniem danych");
            alert.showAndWait();
        }
    }

}
