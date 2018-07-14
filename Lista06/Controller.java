package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Klasa odpowiadająca za działanie głównego okna programu
 * gdzie użytkownik wpisuje parametry i uruchamia symulację.
 */

public class Controller {

    public TextField textm;
    public TextField textn;
    public TextField textk;
    public TextField textp;
    public Button stop_continue;

    /**
     * Metoda startbutton() wywołuje się po kliknięciu na przycisk Start w oknie
     * programu. Pobiera ona dane, które podał użytkownik i tworzy klasę Area,
     * w której powstanie podzielony obszar.
     */

    public void startbutton(ActionEvent actionEvent) {
        stop_continue.setDisable(false);
        try{
            int txtm = Integer.parseInt(textm.getText());
            int txtn = Integer.parseInt(textn.getText());
            int txtk = Integer.parseInt(textk.getText());
            double txtp = Double.parseDouble(textp.getText());
            if(txtm*txtn<=48400&&txtn>2&&txtm>2&&txtk>=0&&txtp<=1&&txtp>=0){
                Area ar = new Area(txtm,txtn,txtk,txtp,stop_continue);}
            else{
                if(txtm*txtn>48400){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText(null);
                    alert.setContentText("Zbyt wiele pól");
                    alert.showAndWait();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText(null);
                    alert.setContentText("Błędne dane");
                    alert.showAndWait();
                }
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

    /**
     * Metoda stopbutton() wywołuje się po kliknięciu na przycisk Stop w oknie
     * programu. Odpowiada za zatrzymanie i ponowne uruchomienie symulacji.
     */

    public synchronized void stopbutton(ActionEvent actionEvent) {
        if(!(Area.stop)){
            Area.stop = true;
            stop_continue.setText("Kontynuuj");
        }
        else{
            Area.stop = false;
            stop_continue.setText("Stop");
            for(int i=0; i<Area.m; i++){
                for(int j = 0; j<Area.n; j++){
                    synchronized (Area.recttab[i][j]){
                    Area.recttab[i][j].notify();
                    }
                }
            }
        }
    }
}
