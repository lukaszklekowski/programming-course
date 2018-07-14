package sample;

import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

    /**
    * Klasa odpowiadająca za działanie głównego okna programu
    * gdzie użytkownik może rysować figury
    */

public class InfoController {

    public Pane pane;

        /**
         * Metoda close wywołoje się po wciśnięciu przycisku OK w oknie Info.
         * Powoduje ona zamknięcie okna.
         */

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }
}
