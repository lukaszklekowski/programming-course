package sample;

import javafx.geometry.Insets;
import javafx.scene.layout.*;

/**
 * Klasa tworząca wątki.
 */
public class Rect extends Thread {
    StackPane rec;
    int column, row;

    /**
     * Konstruktor tworzący StackPane'y do zapełnienia pola przy symulacji.
     * @param layout GridPane gdzie pojawią się StackPane'y.
     * @param column kolumna gdzie pojawi się StackPane.
     * @param row wiersz gdzie pojawi się StackPane.
     */

    Rect(GridPane layout, int column, int row){
        this.column=column;
        this.row=row;
        rec = new StackPane();
        rec.backgroundProperty().setValue(new Background(new BackgroundFill(Area.randcolor(), CornerRadii.EMPTY, Insets.EMPTY)));
        layout.add(rec,column,row);
    }

    /**
     * Metoda uruchamiająca wątek zmieniający kolor.
     */

    @Override
    public void run() {
        synchronized (this) {
            while (Area.thread) {
                try {
                    Area.color(column, row);
                    while (Area.stop){
                        wait();
                    }
                    sleep(Area.randtime());
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
