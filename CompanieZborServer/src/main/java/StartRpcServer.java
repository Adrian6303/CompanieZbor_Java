import network.utils.AbstractServer;
import network.utils.RpcConcurrentServer;
import repository.AngajatRepo;
import repository.BiletRepo;
import repository.TuristRepo;
import repository.ZborRepo;
import server.ServiceImplementation;
import service.IService;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }



        AngajatRepo angajatRepo=new AngajatRepo(serverProps);
        TuristRepo turistRepo=new TuristRepo(serverProps);
        ZborRepo zborRepo=new ZborRepo(serverProps);
        BiletRepo biletRepo=new BiletRepo(serverProps,angajatRepo,zborRepo, turistRepo);
        IService ServerImplementation=new ServiceImplementation(angajatRepo,biletRepo,turistRepo,zborRepo);
        int ServerPort=defaultPort;
        try {
            ServerPort = Integer.parseInt(serverProps.getProperty("server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+ServerPort);
        AbstractServer server = new RpcConcurrentServer(ServerPort, ServerImplementation);
        try {
            server.start();
        } catch (Exception e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(Exception e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}
