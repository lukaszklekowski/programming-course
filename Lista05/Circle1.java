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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.ColorPicker;

/**
 * Klasa tworząca koła.
 */

public class Circle1 {

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    boolean draw = true;

    Circle circle;
    DropShadow dropShadow = new DropShadow(10.0, 5.0, 5.0, Color.GRAY);

    /**
     * Konstruktor klasy tworzący koło. Po jego wywołaniu program oczekuje na wybór
     * miejsca gdzie ma być stworzone koło. Tworzone obiekty mają możliwość przenoszenia
     * lewym przyciskiem myszy oraz skalowania scroll'em. Do każdego obiektu dodawane jest także menu kontekstowe
     * dostępne pod prawym przysiskiem myszy umożliwiające zmianę rozmiaru lub koloru
     * koła, a także jego usunięcie.
     *
     * @param pane Miejsce gdzie ma pojawić się nowe koło.
     * @param menu MenuBar głównego okna programu. Konstruktor umożliwia zachowanie go
     *             zawsze na wierzchu.
     */

    Circle1(final Pane pane, final MenuBar menu) {

        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (draw){
                    if(event.getButton() == MouseButton.PRIMARY) {
                        circle = new Circle(30, Color.GREEN);
                        circle.setCenterX(event.getSceneX());
                        circle.setCenterY(event.getSceneY());
                        circle.setCursor(Cursor.HAND);
                        pane.getChildren().add(circle);
                        menu.toFront();
                        draw = false;
                        circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                circle.setCursor(Cursor.HAND);
                            }
                        });
                        circle.setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override

                            public void handle(MouseEvent me) {
                                if (me.getButton() == MouseButton.PRIMARY){
                                    orgSceneX = me.getSceneX();
                                    orgSceneY = me.getSceneY();
                                    orgTranslateX = circle.getTranslateX();
                                    orgTranslateY = circle.getTranslateY();
                                    circle.toFront();
                                    menu.toFront();
                                    circle.setEffect(dropShadow);
                                    circle.setCursor(Cursor.CLOSED_HAND);
                                }
                            }
                        });
                        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent me) {
                                if (me.getButton() == MouseButton.PRIMARY && me.getSceneX() > 0 && me.getSceneY() > 5 &&
                                    me.getSceneX() < pane.getWidth() && me.getSceneY() < pane.getHeight()) {
                                    double offsetX = me.getSceneX();
                                    double offsetY = me.getSceneY();
                                    double newTranslateX = orgTranslateX + offsetX;
                                    double newTranslateY = orgTranslateY + offsetY;
                                    circle.setTranslateX(newTranslateX);
                                    circle.setTranslateY(newTranslateY);
                                }
                                else {
                                    circle.setCursor(Cursor.DEFAULT);
                                }
                            }
                        });
                        circle.setOnMouseReleased(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent me) {
                                circle.setEffect(null);
                                circle.setCursor(Cursor.HAND);
                            }
                        });
                        circle.setOnScroll(new EventHandler<ScrollEvent>() {
                            @Override
                            public void handle(ScrollEvent event) {
                                if (event.getDeltaY() > 0) {
                                    double scaleX = circle.getScaleX() + 0.1;
                                    double scaleY = circle.getScaleY() + 0.1;
                                    circle.setScaleX(scaleX);
                                    circle.setScaleY(scaleY);
                                } else if (event.getDeltaY() < 0 && circle.getScaleY() > 0.4) {
                                    double scaleX = circle.getScaleX() - 0.1;
                                    double scaleY = circle.getScaleY() - 0.1;
                                    circle.setScaleX(scaleX);
                                    circle.setScaleY(scaleY);
                                }
                            }
                        });
                        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.SECONDARY) {
                                    circle.setDisable(true);
                                    ContextMenu contme = new ContextMenu();
                                    MenuItem menu1 = new MenuItem("Zmień rozmiar");
                                    MenuItem menu2 = new MenuItem("Zmień kolor");
                                    MenuItem menu3 = new MenuItem("Usuń figurę");
                                    contme.getItems().addAll(menu1, menu2, new SeparatorMenuItem(), menu3);
                                    menu1.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            settings();
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
                                            pane.getChildren().remove(circle);
                                        }
                                    });
                                    contme.show(circle, event.getScreenX(), event.getScreenY());
                                    contme.setOnHiding(new EventHandler<WindowEvent>() {
                                        @Override
                                        public void handle(WindowEvent event) {
                                            circle.setDisable(false);
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * Metoda settings() tworzy nowe okno umożliwiające zmianę promienia lub skali figury.
     * Wywołuje się ona po kliknięciu prawym przyciskiem myszy na kole.
     */

    public void settings() {

        final Stage window = new Stage();

        window.setTitle("Ustawienia");
        window.setMinWidth(250);
        window.setMinHeight(150);
        window.setResizable(false);


        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setHgap(10);
        layout.setVgap(15);

        Label label1 = new Label("Promień:");
        Label label2 = new Label("Skala:");

        final TextField textrad = new TextField();
        final TextField textscale = new TextField();

        textrad.setText(Double.toString(circle.getRadius()));
        textscale.setText(Double.toString(circle.getScaleX()));


        Button closeButton = new Button("Zmien rozmiar");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    Double rad = Double.parseDouble(textrad.getText());
                    Double scale = Double.parseDouble(textscale.getText());
                    if(rad>0 && scale>0){
                        circle.setRadius(rad);
                        circle.setScaleY(scale);
                        circle.setScaleX(scale);
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
        GridPane.setConstraints(label1, 1, 1);
        GridPane.setConstraints(label2, 1, 2);
        GridPane.setConstraints(textrad, 2, 1);
        GridPane.setConstraints(textscale, 2, 2);
        GridPane.setConstraints(closeButton, 2, 5);

        layout.getChildren().addAll(label1, label2, closeButton, textrad, textscale);
        Scene scene1 = new Scene(layout);
        window.setScene(scene1);
        window.show();
    }

    /**
     * Metoda colorpicker() umożliwia zmiane koloru figury. Wywoływana jest po wciśnięciu
     * prawego przycisku myszy na kole.
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
                circle.setFill(colorPicker.getValue());
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

