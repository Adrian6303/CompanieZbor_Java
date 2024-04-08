package client;

import client.gui.*;
import network.rpcprotocol.ServicesRpcProxy;
import service.IService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;


public class StartRpcClientFX extends Application {
    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";


    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties props = new Properties();
        try {
            props.load(StartRpcClientFX.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            props.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return;
        }
        String serverIP = props.getProperty("server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(props.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IService server = new ServicesRpcProxy(serverIP, serverPort);



        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("login_view.fxml"));
        Parent root=loader.load();


        LoginController ctrl =
                loader.<LoginController>getController();
        ctrl.setService(server);




//        FXMLLoader cloader = new FXMLLoader(
//                getClass().getClassLoader().getResource("search_view.fxml"));
//        Parent croot=cloader.load();

//
//        LoginController loginCtrl =
//                cloader.<~>getController();
//        loginCtrl.setService(server);

        //ctrl.setService(chatCtrl);
        //ctrl.setParent(croot);

        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();







/*      angajatRepo = new AngajatRepo(props);
        zborRepo = new ZborRepo(props);
        turistRepo = new TuristRepo(props);
        biletRepo = new BiletRepo(props,angajatRepo,zborRepo,turistRepo);

        service= new Service(angajatRepo, biletRepo, turistRepo, zborRepo);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login_view.fxml"));

        Scene scene = new Scene(loader.load());
        stage.setTitle("Login");
        LoginController loginController = loader.getController();
        loginController.setService(service);
        stage.setScene(scene);
        stage.show();*/

    }


}


