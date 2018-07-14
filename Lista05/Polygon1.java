package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

/**
 * Klasa tworząca wielokąty.
 */

public class Polygon1 {

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    public boolean draw = true;

    ArrayList<Double> values = new ArrayList<Double>();
    DropShadow dropShadow = new DropShadow(10.0, 5.0, 5.0, Color.GRAY);
    Polygon pol;

    /**
     * Konstruktor klasy tworzący wielokąt. Po jego wywołaniu program oczekuje na podanie
     * punktów lewym przyciskiem myszy, które będą wierzchołkami wielokąta. Po podaniu
     * wierzchołków należy wcisnąć prawy przycisk myszy aby zakończyć rysowanie.
     * Tworzone obiekty mają możliwość przenoszenia lewym przyciskiem myszy oraz
     * skalowania scroll'em. Do każdego obiektu dodawane jest także menu kontekstowe
     * dostępne pod prawym przysiskiem myszy umożliwiające zmianę rozmiaru lub koloru
     * wielokąta, a także jego usunięcie.
     *
     * @param pane Miejsce gdzie ma pojawić się nowy wielokąt.
     * @param menu MenuBar głównego okna programu. Konstruktor umożliwia zachowanie go
     *             zawsze na wierzchu.
     */

    Polygon1(final Pane pane, final MenuBar menu) {

        pol = new Polygon();
        pane.getChildren().addAll(pol);
        menu.toFront();
        pol.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pol.setCursor(Cursor.HAND);
            }
        });
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (draw) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        pol.setFill(Color.AQUA);
                        values.add(event.getX());
                        values.add(event.getY());
                    }
                    if (event.getButton() == MouseButton.SECONDARY) {
                        pol.getPoints().addAll(values);
                        draw = false;
                    }
                }
            }
        });
        pol.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (me.getButton() == MouseButton.PRIMARY) {
                    orgSceneX = me.getSceneX();
                    orgSceneY = me.getSceneY();
                    orgTranslateX = pol.getTranslateX();
                    orgTranslateY = pol.getTranslateY();
                    pol.toFront();
                    menu.toFront();
                    pol.setEffect(dropShadow);
                }
            }
        });
        pol.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (me.getButton() == MouseButton.PRIMARY && me.getSceneX() > 0 && me.getSceneY() > 25 &&
                    me.getSceneX() < pane.getWidth() && me.getSceneY() < pane.getHeight()) {
                    double offsetX = me.getSceneX() - orgSceneX;
                    double offsetY = me.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;
                    pol.setTranslateX(newTranslateX);
                    pol.setTranslateY(newTranslateY);
                    pol.setCursor(Cursor.CLOSED_HAND);
                }
                else{
                    pol.setCursor(Cursor.DEFAULT);
                }
            }
        });
        pol.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                pol.setEffect(null);
                pol.setCursor(Cursor.HAND);
            }
        });
        pol.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (Double.compare(event.getDeltaY(), 0) > 0) {
                    double scaleX = pol.getScaleX() + 0.1;
                    double scaleY = pol.getScaleY() + 0.1;
                    pol.setScaleX(scaleX);
                    pol.setScaleY(scaleY);
                } else if (event.getDeltaY() < 0 && pol.getScaleY() > 0.4) {
                    double scaleX = pol.getScaleX() - 0.1;
                    double scaleY = pol.getScaleY() - 0.1;
                    pol.setScaleX(scaleX);
                    pol.setScaleY(scaleY);
                }
            }
        });
        pol.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    pol.setDisable(true);
                    ContextMenu conme = new ContextMenu();
                    MenuItem menu1 = new MenuItem("Zmień rozmiar");
                    MenuItem menu2 = new MenuItem("Zmień kolor");
                    MenuItem menu3 = new MenuItem("Usuń figurę");
                    conme.getItems().addAll(menu1, menu2, new SeparatorMenuItem(), menu3);
                    menu1.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            ustawienia();
                        }
                    });
                    menu2.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            colorpicker();
                        }
                    });
                    menu3.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            pane.getChildren().remove(pol);
                        }
                    });
                    conme.show(pol, event.getScreenX(), event.getScreenY());
                    conme.setOnHiding(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            pol.setDisable(false);
                        }
                    });
                }
            }
        });
    }

    /**
     * Metoda settings() tworzy nowe okno umożliwiające zmianę skali figury.
     * Wywołuje się ona po kliknięciu prawym przyciskiem myszy na wielokącie.
     */

    public void ustawienia() {

        final Stage window = new Stage();

        window.setTitle("Ustawienia");
        window.setMinWidth(250);
        window.setMinHeight(150);
        window.setResizable(false);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setHgap(10);
        layout.setVgap(15);

        Label label1 = new Label("Skala:");

        final TextField textscale = new TextField();

        textscale.setText(Double.toString(pol.getScaleX()));

        Button closeButton = new Button("Zmien rozmiar");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    Double scale = Double.parseDouble(textscale.getText());
                    if(scale>0){
                        pol.setScaleY(scale);
                        pol.setScaleX(scale);
                        window.close();
                    }
                }
                catch (NumberFormatException ex){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText(null);
                    alert.setContentText("Błędne dane");

                    alert.showAndWait();
                }
            }
        });
        GridPane.setConstraints(label1, 1, 2);
        GridPane.setConstraints(textscale, 2, 2);
        GridPane.setConstraints(closeButton, 2, 5);

        layout.getChildren().addAll(label1, closeButton, textscale);
        Scene scene1 = new Scene(layout);
        window.setScene(scene1);
        window.show();
    }

    /**
     * Metoda colorpicker() umożliwia zmiane koloru figury. Wywoływana jest po wciśnięciu
     * prawego przycisku myszy na wielokącie.
     */

    public void colorpicker(){

        final Stage window = new Stage();

        window.setTitle("Kolor");
        window.setMinWidth(50);
        window.setMinHeight(50);
        window.setResizable(false);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setHgap(10);
        layout.setVgap(15);

        Label label1 = new Label("Kolor:");
        final ColorPicker colorPicker = new ColorPicker(Color.AQUA);
        Button closeButton = new Button("Zmień kolor");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pol.setFill(colorPicker.getValue());
                window.close();
            }
        });
        GridPane.setConstraints(label1, 1, 1);
        GridPane.setConstraints(colorPicker, 2, 1);
        GridPane.setConstraints(closeButton, 2, 2);
        layout.getChildren().addAll(label1,closeButton, colorPicker);
        Scene scene1 = new Scene(layout);
        window.setScene(scene1);
        window.show();
    }
}

