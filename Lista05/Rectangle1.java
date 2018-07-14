package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Klasa tworząca prostokąty.
 */

public class Rectangle1{

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    boolean draw = true;

    Rectangle rect;
    DropShadow dropShadow = new DropShadow(10.0, 5.0, 5.0, Color.GRAY);

    /**
     * Konstruktor klasy tworzący prostokąt. Po jego wywołaniu program oczekuje na wybór
     * miejsca gdzie ma być stworzony prostokąt. Tworzone obiekty mają możliwość
     * przenoszenia lewym przyciskiem myszy oraz skalowania scroll'em. Do każdego obiektu
     * dodawane jest także menu kontekstowe dostępne pod prawym przysiskiem myszy
     * umożliwiające zmianę rozmiaru lub koloru prostokąta, a także jego usunięcie.
     *
     * @param pane Miejsce gdzie ma pojawić się nowy prostokąt.
     * @param menu MenuBar głównego okna programu. Konstruktor umożliwia zachowanie go
     *             zawsze na wierzchu.
     */

    Rectangle1(final Pane pane, final MenuBar menu){

        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (draw) {
                    if (event.getButton()== MouseButton.PRIMARY) {
                        rect = new Rectangle(100, 150);
                        rect.setX(event.getX());
                        rect.setY(event.getY());
                        rect.setFill(Color.BLUE);
                        rect.setCursor(Cursor.HAND);
                        pane.getChildren().add(rect);
                        menu.toFront();
                        draw = false;
                        rect.setOnMouseEntered(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                rect.setCursor(Cursor.HAND);
                            }
                        });
                        rect.setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent me) {
                                if (me.getButton() == MouseButton.PRIMARY) {
                                    orgSceneX = me.getSceneX();
                                    orgSceneY = me.getSceneY();
                                    orgTranslateX = rect.getTranslateX();
                                    orgTranslateY = rect.getTranslateY();
                                    rect.toFront();
                                    menu.toFront();
                                    rect.setEffect(dropShadow);
                                }
                            }
                        });
                        rect.setOnMouseDragged(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent me) {
                                if (me.getButton() == MouseButton.PRIMARY && me.getSceneX() > 0 && me.getSceneY() > 25 &&
                                    me.getSceneX() < pane.getWidth() && me.getSceneY() < pane.getHeight()) {
                                    double offsetX = me.getSceneX() - orgSceneX;
                                    double offsetY = me.getSceneY() - orgSceneY;
                                    double newTranslateX = orgTranslateX + offsetX;
                                    double newTranslateY = orgTranslateY + offsetY;
                                    rect.setTranslateX(newTranslateX);
                                    rect.setTranslateY(newTranslateY);
                                    rect.setCursor(Cursor.CLOSED_HAND);
                                }
                                else{
                                    rect.setCursor(Cursor.DEFAULT);
                                }
                            }
                        });
                        rect.setOnMouseReleased(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent me) {
                                rect.setEffect(null);
                                rect.setCursor(Cursor.HAND);
                            }
                        });
                        rect.setOnScroll(new EventHandler<ScrollEvent>() {
                            @Override
                            public void handle(ScrollEvent event) {
                                if(event.getDeltaY()> 0) {
                                    double scaleX = rect.getScaleX() + 0.1;
                                    double scaleY = rect.getScaleY() + 0.1;
                                    rect.setScaleX(scaleX);
                                    rect.setScaleY(scaleY);
                                }
                                else
                                if(event.getDeltaY() < 0 && rect.getScaleY() > 0.4){
                                    double scaleX = rect.getScaleX() - 0.1;
                                    double scaleY = rect.getScaleY() - 0.1;
                                    rect.setScaleX(scaleX);
                                    rect.setScaleY(scaleY);
                                }
                            }
                        });
                        rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.SECONDARY) {
                                    rect.setDisable(true);
                                    ContextMenu conme = new ContextMenu();
                                    MenuItem menu1 = new MenuItem("Zmień rozmiar");
                                    MenuItem menu2 = new MenuItem("Zmień kolor");
                                    MenuItem menu3 = new MenuItem("Usuń figurę");
                                    conme.getItems().addAll(menu1, menu2,new SeparatorMenuItem(), menu3);
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
                                            pane.getChildren().remove(rect);
                                        }
                                    });
                                    conme.show(rect, event.getScreenX(), event.getScreenY());
                                    conme.setOnHiding(new EventHandler<WindowEvent>() {
                                        @Override
                                        public void handle(WindowEvent event) {
                                            rect.setDisable(false);
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
     * Metoda settings() tworzy nowe okno umożliwiające zmianę szerokości, wysokości
     * lub skali figury. Wywołuje się ona po kliknięciu prawym przyciskiem myszy na
     * prostokącie.
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

        Label label1 = new Label("Wysokość:");
        Label label2 = new Label("Szerokość");
        Label label3 = new Label("Skala:");

        final TextField textheight = new TextField();
        final TextField textwidth = new TextField();
        final TextField textscale = new TextField();

        textheight.setText(Double.toString(rect.getHeight()));
        textwidth.setText(Double.toString(rect.getWidth()));
        textscale.setText(Double.toString(rect.getScaleX()));

        Button closeButton = new Button("Zmien rozmiar");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    Double height = Double.parseDouble(textheight.getText());
                    Double width = Double.parseDouble(textwidth.getText());
                    Double scale = Double.parseDouble(textscale.getText());
                    if(width>0 && scale>0 && height>0){
                        rect.setWidth(width);
                        rect.setHeight(height);
                        rect.setScaleY(scale);
                        rect.setScaleX(scale);
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
        GridPane.setConstraints(label3,1,3);
        GridPane.setConstraints(textheight, 2, 1);
        GridPane.setConstraints(textwidth, 2, 2);
        GridPane.setConstraints(textscale,2,3);
        GridPane.setConstraints(closeButton, 2, 5);

        layout.getChildren().addAll(label1, label2, label3, closeButton, textheight, textscale, textwidth);
        Scene scene1 = new Scene(layout);
        window.setScene(scene1);
        window.show();
    }

    /**
     * Metoda colorpicker() umożliwia zmiane koloru figury. Wywoływana jest po wciśnięciu
     * prawego przycisku myszy na prostokącie.
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
                rect.setFill(colorPicker.getValue());
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