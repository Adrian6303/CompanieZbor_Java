package ro.mpp2024.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import ro.mpp2024.domain.Angajat;
import ro.mpp2024.domain.Zbor;
import ro.mpp2024.service.Service;
import ro.mpp2024.utils.Observer;

import java.util.List;

public class SearchController implements Observer{

    public DatePicker dataPlecariiDatePicker;
    public ListView<Zbor> zboruriListView;
    public ComboBox<String> destinatiiComboBox;
    public Button searchButton;
    public Button confirmButton;
    Service service;
    private Angajat angajat;

    public void setService(Service service) {
        this.service = service;
        innitData();
    }
    public void initialize(){
        destinatiiComboBox.setPromptText("Destinatie");
    }
    public void innitData(){

        destinatiiComboBox.getItems().addAll(service.addDestinations());
    }


    @Override
    public void update() {
        innitData();
    }

    public void setAngajat(Angajat angajat) {
        this.angajat = angajat;
    }

    public void SearchButtonClick(ActionEvent actionEvent) {
    }

    public void ConfirmButtonClick(ActionEvent actionEvent) {
    }
}
