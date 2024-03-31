package ro.mpp2024.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ro.mpp2024.Main;
import ro.mpp2024.domain.Angajat;
import ro.mpp2024.domain.Zbor;
import ro.mpp2024.service.Service;
import ro.mpp2024.utils.Observer;

import java.io.IOException;
import java.sql.Date;

import java.util.List;

public class SearchController implements Observer{

    public DatePicker dataPlecariiDatePicker;
    public ListView<Zbor> zboruriListView;
    public ComboBox<String> destinatiiComboBox;
    public Button searchButton;
    public Button confirmButton;
    public Button logoutButton;
    Service service;
    private Angajat angajat;

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        innitData();
    }
    public void initialize(){
        destinatiiComboBox.setPromptText("Destinatie");
    }
    public void innitData(){

        destinatiiComboBox.getItems().addAll(service.addDestinations());
        zboruriListView.getItems().setAll(service.findAllZboruri());
    }


    @Override
    public void update() {
        innitData();
    }

    public void setAngajat(Angajat angajat) {
        this.angajat = angajat;
    }

    public void SearchButtonClick(ActionEvent actionEvent) {
        String destinatie = destinatiiComboBox.getValue();
        List<Zbor> zboruri = service.findZboruriByDestinatieAndDate(destinatie, Date.valueOf(dataPlecariiDatePicker.getValue()));
        zboruriListView.getItems().setAll(zboruri);
    }

    public void ConfirmButtonClick(ActionEvent actionEvent) {
        Zbor zbor = zboruriListView.getSelectionModel().getSelectedItem();
        if(zbor != null){
            try {
                openWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No flight selected");
            alert.setHeaderText("No flight selected");
            alert.setContentText("Please select a flight");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    private void openWindow() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("buy_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Buy flight tickets to " + zboruriListView.getSelectionModel().getSelectedItem().getDestinatia() +", "+ angajat.getUser());
        BuyController buyController = fxmlLoader.getController();
        buyController.setAngajat(angajat);
        buyController.setZbor(zboruriListView.getSelectionModel().getSelectedItem());
        buyController.setService(service);
        stage.setScene(scene);
        stage.show();
        this.closeWindow();

    }
    private void closeWindow() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    public void LogoutButtonClick(ActionEvent actionEvent) {
        this.closeWindow();
    }
}
