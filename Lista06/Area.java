package sample;

import java.util.Random;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Klasa tworząca nowe okno, w którym odbędzie się symulacja, oraz zawierające metody
 * odpowiedzialne za zmianę koloru czy losowanie opóźnienia.
 */

public class Area{

    static Rect[][] recttab;
    GridPane layout;
    Stage window;
    Scene scene1;
    static int k, m, n;
    static boolean thread, stop;
    static double p;
    static Random rand = new Random();

    /**
     * Konstruktor klasy Area tworzący okno z symulacją oraz tabelę z wątkami, które
     * są też uruchamiane.
     * @param column kolumny pola.
     * @param row wiersze pola.
     * @param time czas opoźnienia w ms.
     * @param prob prawdopodobieństwo zmiany koloru.
     * @param stop_continue przycisk głównego okna odpowiedzialny za zatrzymanie
     *                      symulacji.
     */

    Area(int column, int row, int time, double prob, Button stop_continue){
        thread = true;
        stop = false;
        this.k=time;
        this.p=prob;
        this.m=column;
        this.n=row;
        recttab = new Rect[m][n];
        window = new Stage();

        window.setTitle("Pole");

        layout = new GridPane();
        layout.setHgap(0);
        layout.setVgap(0);

        for (int i = 0; i < m; i++) {
            layout.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));}
        for(int i = 0; i<n; i++){
            layout.getRowConstraints().add(new RowConstraints(0, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));}

        scene1 = new Scene(layout, 400,400);
        window.setScene(scene1);

        for (int i = 0; i<m ; i++){
            for(int j=0;j<n;j++){
                Rect rect = new Rect(layout,i,j);
                recttab [i][j] = rect;
                rect.start();
            }
        }

        window.show();
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                thread = false;
                if(stop) {
                    stop = false;
                    for (int i = 0; i < m; i++) {
                        for (int j = 0; j < n; j++) {
                            synchronized (recttab[i][j]) {
                                recttab[i][j].notify();
                            }
                        }
                    }
                }
                window.close();
                stop_continue.setDisable(true);
                stop_continue.setText("Stop");
            }
        });
    }

    /**
     * Metoda odpowiadająca za zmianę koloru StackPane'a. W zależności od prawdopodobieństwa
     * kolor zostanie zmieniony na losowy lub średnią z kolorów sąsiadów.
     * @param column
     * @param row
     */

    public static void color(int column, int row){
        Platform.runLater(() -> {
            double prob = rand.nextDouble();
            if(prob<=p){
                recttab[column][row].rec.backgroundProperty().setValue(new Background(new BackgroundFill(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)),CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else {
                if (row != 0 && column != 0 && row != n-1 && column != m-1)
                    recttab[column][row].rec.backgroundProperty().setValue(new Background(new BackgroundFill(avrColor(recttab[column - 1][row].rec, recttab[column][row + 1].rec, recttab[column][row-1].rec, recttab[column + 1][row].rec),CornerRadii.EMPTY, Insets.EMPTY)));
                else if (row == 0 && column == 0)
                    recttab[column][row].rec.backgroundProperty().setValue(new Background(new BackgroundFill(avrColor(recttab[column + 1][row].rec, recttab[column][row + 1].rec, recttab[column][n-1].rec, recttab[m-1][row].rec),CornerRadii.EMPTY, Insets.EMPTY)));
                else if (row == 0 && column == m-1)
                    recttab[column][row].rec.backgroundProperty().setValue(new Background(new BackgroundFill(avrColor(recttab[m - 2][row].rec, recttab[column-1][row + 1].rec, recttab[m-1][n-1].rec, recttab[0][0].rec),CornerRadii.EMPTY, Insets.EMPTY)));
                else if (row == n-1 && column == 0)
                    recttab[column][row].rec.backgroundProperty().setValue(new Background(new BackgroundFill(avrColor(recttab[column + 1][row-1].rec, recttab[column][n - 2].rec, recttab[m-1][n-1].rec, recttab[0][0].rec),CornerRadii.EMPTY, Insets.EMPTY)));
                else if (row == n-1 && column == m-1)
                    recttab[column][row].rec.backgroundProperty().setValue(new Background(new BackgroundFill(avrColor(recttab[0][n-1].rec, recttab[m-1][n - 2].rec, recttab[m - 2][n-1].rec, recttab[m-1][0].rec),CornerRadii.EMPTY, Insets.EMPTY)));
                else if (row == 0)
                    recttab[column][row].rec.backgroundProperty().setValue(new Background(new BackgroundFill(avrColor(recttab[column - 1][row].rec, recttab[column][row + 1].rec, recttab[column+1][row].rec, recttab[column][n-1].rec),CornerRadii.EMPTY, Insets.EMPTY)));
                else if (column == 0)
                    recttab[column][row].rec.backgroundProperty().setValue(new Background(new BackgroundFill(avrColor(recttab[column][row+1].rec, recttab[column][row - 1].rec, recttab[column+1][row].rec, recttab[m-1][row].rec),CornerRadii.EMPTY, Insets.EMPTY)));
                else if (column == m-1)
                    recttab[column][row].rec.backgroundProperty().setValue(new Background(new BackgroundFill(avrColor(recttab[m-2][row+1].rec, recttab[m-2][row-1].rec, recttab[column-1][row].rec, recttab[0][row].rec),CornerRadii.EMPTY, Insets.EMPTY)));
                else if (row == n-1)
                    recttab[column][row].rec.backgroundProperty().setValue(new Background(new BackgroundFill(avrColor(recttab[column - 1][row].rec, recttab[column+1][n-2].rec, recttab[column][n-2].rec, recttab[column][0].rec),CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
    }

    /**
     * Metoda licząca średnią z kolorów sąsiadów jednego ze StackPane'ów.
     * @param rec1 pierwszy StackPane.
     * @param rec2 drugi StackPane.
     * @param rec3 trzeci StackPane.
     * @param rec4 czwarty StackPane.
     * @return zwraca średnią z kolorów.
     */

    public static Color avrColor(StackPane rec1, StackPane rec2, StackPane rec3, StackPane rec4){
        BackgroundFill color1 = rec1.getBackground().getFills().get(0);
        Color color11 = (Color) color1.getFill();
        BackgroundFill color2 = rec2.getBackground().getFills().get(0);
        Color color22 = (Color) color2.getFill();
        BackgroundFill color3 = rec3.getBackground().getFills().get(0);
        Color color33 = (Color) color3.getFill();
        BackgroundFill color4 = rec4.getBackground().getFills().get(0);
        Color color44 = (Color) color4.getFill();
        Color colavr;
        colavr = Color.rgb((int)((color11.getRed()*255+color22.getRed()*255+color33.getRed()*255+color44.getRed()*255)/4),
                (int)((color11.getGreen()*255+color22.getGreen()*255+color33.getGreen()*255+color44.getGreen()*255)/4),
                (int)((color11.getBlue()*255+color22.getBlue()*255+color33.getBlue()*255+color44.getBlue()*255)/4));
        return colavr;
    }

    /**
     * Metoda "licząca" opóźnienie do uśpienia wątku.
     * @return zwraca czas w ms.
     */

    public static synchronized int randtime(){
        int time = rand.nextInt(k)+k/2;
        return time;
    }

    /**
     * Metoda zwracająca przypadkowy kolor przy tworzeniu StackPane'a.
     * @return zwraca kolor.
     */

    public static synchronized Color randcolor(){
        Color c=Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
        return c;
    }
}