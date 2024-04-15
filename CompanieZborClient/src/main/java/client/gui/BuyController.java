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
    public void updateZbor(Zbor zbor) throws Exception {

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
        String adresaClient = adresaClientTextField.getText();
        if (listaTuristiTextArea.getText().equals("")) {
            listaTuristi.add(client);
        }
        else {
            listaTuristi.add(client);
            List<String> Turisti = List.of(listaTuristiTextArea.getText().split(";"));

            for (String turist : Turisti) {
                listaTuristi.add(service.findOrAddTurist(turist));
            }
        }

        //listaTuristi.addAll(service.findOrAddTurists(Turisti));
        int nrLocuri = listaTuristi.size();
        this.zbor.setNrLocuri(this.zbor.getNrLocuri() - nrLocuri);
        service.updateZbor(this.zbor);
        Bilet bilet = new Bilet(angajat,zbor, client,listaTuristi,adresaClient, nrLocuri);
        service.addBilet(bilet);
        this.closeWindow();
    }

    public void CancelButtonClick(ActionEvent actionEvent) throws Exception {
        this.closeWindow();
    }


    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
