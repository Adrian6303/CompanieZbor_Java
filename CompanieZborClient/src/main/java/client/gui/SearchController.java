package client.gui;

import client.StartRpcClientFX;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.*;
import javafx.stage.Stage;
import domain.*;
import service.*;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SearchController implements Observer{

    public DatePicker dataPlecariiDatePicker;
    public ListView<Zbor> zboruriListView;
    public ComboBox<String> destinatiiComboBox;
    public Button searchButton;
    public Button confirmButton;
    public Button logoutButton;
    IService service;
    private Angajat angajat;

    public void setService(IService service) throws Exception {
        this.service = service;
        //destinatiiComboBox.getItems().addAll(service.addDestinations());
        innitData();
    }

    public void initialize(){
        destinatiiComboBox.setPromptText("Destinatie");
    }
    public void innitData()  {

        try {
            List<Zbor> zboruri=service.findAllZboruri();
            List<String> destinatii = new ArrayList<String>();
            zboruriListView.getItems().setAll(zboruri);
            for (Zbor z:zboruri) {
                if(!destinatii.contains(z.getDestinatia()))
                {
                    destinatii.add(z.getDestinatia());
                }
            }
            destinatiiComboBox.getItems().addAll(destinatii);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void updateZbor(Zbor zbor) throws Exception {
        Platform.runLater(() ->{
            try {
                innitData();
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        });
    }

    public void setAngajat(Angajat angajat) {
        this.angajat = angajat;
    }

    public void SearchButtonClick(ActionEvent actionEvent) throws Exception {
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("buy_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Buy flight tickets to " + zboruriListView.getSelectionModel().getSelectedItem().getDestinatia() +", "+ angajat.getUser());
        BuyController buyController = fxmlLoader.getController();
        buyController.setAngajat(angajat);
        buyController.setZbor(zboruriListView.getSelectionModel().getSelectedItem());
        buyController.setService(service);
        stage.setScene(scene);
        stage.show();

    }
    private void closeWindow() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    public void LogoutButtonClick(ActionEvent actionEvent) throws Exception {
        service.Logout(angajat);
        this.closeWindow();
    }
}
