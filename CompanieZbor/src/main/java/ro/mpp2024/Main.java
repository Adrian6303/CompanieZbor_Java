package ro.mpp2024;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mpp2024.domain.Angajat;
import ro.mpp2024.domain.Bilet;
import ro.mpp2024.domain.Turist;
import ro.mpp2024.domain.Zbor;
import ro.mpp2024.repository.AngajatRepo;
import ro.mpp2024.repository.BiletRepo;
import ro.mpp2024.repository.TuristRepo;
import ro.mpp2024.repository.ZborRepo;
import ro.mpp2024.service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javafx.application.Application;

public class Main extends Application {

    public static void main(String[] args) {

        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        launch();

    }

    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }


}