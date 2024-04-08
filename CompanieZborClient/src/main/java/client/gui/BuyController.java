package client.gui;

import client.StartRpcClientFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import domain.*;
import service.IService;
import service.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuyController implements Observer {

    public TextField numeClientTextField;
    public Button buyButton;
    public TextField adresaClientTextField;
    public TextArea listaTuristiTextArea;
    public Button cancelButton;
    private Angajat angajat;
    IService service;

    private Zbor zbor;

    public void setService(IService service) {
        this.service = service;
        innitData();
    }
    public void initialize() {

    }

    public void innitData() {
    }


    @Override
    public void update() {
        innitData();
    }



    public void setZbor(Zbor zbor) {
        this.zbor = zbor;
    }

    public void setAngajat(Angajat angajat) {
        this.angajat = angajat;
    }

    public void BuyButtonClick(ActionEvent actionEvent) throws Exception {
        String numeClient = numeClientTextField.getText();
        Turist client = service.findOrAddTurist(numeClient);
        List<Turist> listaTuristi = new ArrayList<>();
        listaTuristi.add(client);
        String adresaClient = adresaClientTextField.getText();
        List<String> Turisti = List.of(listaTuristiTextArea.getText().split(";"));
        listaTuristi.addAll(service.findOrAddTurists(Turisti));
        int nrLocuri = listaTuristi.size() + 1;
        this.zbor.setNrLocuri(this.zbor.getNrLocuri() - nrLocuri);
        service.updateZbor(this.zbor);
        Bilet bilet = new Bilet(angajat,zbor, client,listaTuristi,adresaClient, nrLocuri);
        service.addBilet(bilet);
        try {
            openWindow();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CancelButtonClick(ActionEvent actionEvent) throws IOException {
        openWindow();
    }

    private void openWindow() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(StartRpcClientFX.class.getResource("search_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Search flights, "+ angajat.getUser());
        SearchController searchController = fxmlLoader.getController();
        searchController.setAngajat(angajat);
        searchController.setService(service);
        stage.setScene(scene);
        stage.show();
        this.closeWindow();

    }

    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
