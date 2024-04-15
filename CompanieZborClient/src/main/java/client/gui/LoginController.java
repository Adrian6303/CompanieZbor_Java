package client.gui;

import client.StartRpcClientFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.*;
import domain.*;

import java.io.IOException;

public class LoginController implements Observer {

    public TextField userTextField;
    public PasswordField passwordTextField;
    public Button loginButton;

    private Angajat angajat;
    IService service;

    public void setService(IService service) {
        this.service = service;

    }


    @Override
    public void updateZbor(Zbor zbor) throws Exception {

    }

    public void LoginButtonClick(ActionEvent actionEvent) throws Exception {
        String username = userTextField.getText();
        String password = passwordTextField.getText();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("search_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        SearchController searchController = fxmlLoader.getController();
        angajat = service.findAngajatByUserAndPass(username, password,searchController);
        if (angajat != null) {
            stage.setTitle("Search flights for Angajat: " +  angajat.getUser());
            searchController.setAngajat(angajat);
            searchController.setService(service);
            service.setObserver(angajat.getUser(),searchController);
            stage.setScene(scene);
            stage.show();
            userTextField.clear();
            passwordTextField.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login failed");
            alert.setHeaderText("Login failed");
            alert.setContentText("Username or password incorrect");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

}
