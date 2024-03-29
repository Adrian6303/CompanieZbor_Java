package ro.mpp2024.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ro.mpp2024.Main;
import ro.mpp2024.domain.Angajat;
import ro.mpp2024.service.Service;
import ro.mpp2024.utils.Observer;

import java.io.IOException;

public class LoginController implements Observer {

    public TextField userTextField;
    public PasswordField passwordTextField;
    public Button loginButton;

    private Angajat angajat;
    Service service;

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void update() {

    }

    public void LoginButtonClick(ActionEvent actionEvent) throws IOException {
        String username = userTextField.getText();
        String password = passwordTextField.getText();
        angajat = service.findAngajatByUserAndPass(username, password);
        if (angajat != null) {
            this.openWindow();
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

    private void openWindow() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("search_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Search flights");
        SearchController searchController = fxmlLoader.getController();
        searchController.setAngajat(angajat);
        searchController.setService(service);
        stage.setScene(scene);
        stage.show();

    }
}
