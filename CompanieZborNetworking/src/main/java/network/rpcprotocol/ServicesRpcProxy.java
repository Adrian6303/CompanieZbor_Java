package network.rpcprotocol;
import domain.Angajat;
import domain.Bilet;
import domain.Turist;
import domain.Zbor;
import service.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ServicesRpcProxy implements IService {
    private String host;
    private int port;

    private Observer observer;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public ServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }


//    public void login(Angajat angajat, Observer observer) throws Exception {
//        initializeConnection();
//        Request req = new Request.Builder()
//                .type(RequestType.LOGIN)
//                .data(angajat)
//                .build();
//        sendRequest(req);
//        Response response = readResponse();
//        if (response.type() == ResponseType.OK) {
//            this.observer = observer;
//            return;
//        }
//        if (response.type() == ResponseType.ERROR) {
//            String err = response.data().toString();
//            closeConnection();
//            throw new Exception(err);
//        }
//    }

//
//    public void sendMessage(Message message) throws ChatException {
//        MessageDTO mdto= DTOUtils.getDTO(message);
//        Request req=new Request.Builder().type(RequestType.SEND_MESSAGE).data(mdto).build();
//        sendRequest(req);
//        Response response=readResponse();
//        if (response.type()== ResponseType.ERROR){
//            String err=response.data().toString();
//            throw new ChatException(err);
//        }
//    }
//
//    public void logout(User user, IChatObserver client) throws ChatException {
//        UserDTO udto= DTOUtils.getDTO(user);
//        Request req=new Request.Builder().type(RequestType.LOGOUT).data(udto).build();
//        sendRequest(req);
//        Response response=readResponse();
//        closeConnection();
//        if (response.type()== ResponseType.ERROR){
//            String err=response.data().toString();
//            throw new ChatException(err);
//        }
//    }
//
//
//
//    public User[] getLoggedFriends(User user) throws ChatException {
//        UserDTO udto= DTOUtils.getDTO(user);
//        Request req=new Request.Builder().type(RequestType.GET_LOGGED_FRIENDS).data(udto).build();
//        sendRequest(req);
//        Response response=readResponse();
//        if (response.type()== ResponseType.ERROR){
//            String err=response.data().toString();
//            throw new ChatException(err);
//        }
//        UserDTO[] frDTO=(UserDTO[])response.data();
//        User[] friends= DTOUtils.getFromDTO(frDTO);
//        return friends;
//    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            observer=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request)throws Exception {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new Exception(e);
        }

    }

    private Response readResponse() throws Exception {
        Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() throws Exception {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(Response response){
//        if (response.type()== ResponseType.FRIEND_LOGGED_IN){
//
//            User friend= DTOUtils.getFromDTO((UserDTO) response.data());
//            System.out.println("Friend logged in "+friend);
//            try {
//                client.friendLoggedIn(friend);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        if (response.type()== ResponseType.FRIEND_LOGGED_OUT){
//            User friend= DTOUtils.getFromDTO((UserDTO)response.data());
//            System.out.println("Friend logged out "+friend);
//            try {
//                client.friendLoggedOut(friend);
//            } catch (ChatException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (response.type()== ResponseType.NEW_MESSAGE){
//            Message message= DTOUtils.getFromDTO((MessageDTO)response.data());
//            try {
//                client.messageReceived(message);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    private boolean isUpdate(Response response){
        return response.type()== ResponseType.UPDATE ;
    }

    @Override
    public List<Zbor> findAllZboruri() throws Exception {
        initializeConnection();
        Request req = new Request.Builder()
                .type(RequestType.GET_ZBORURI)
                .build();
        sendRequest(req);
        Response response = readResponse();
        return (List<Zbor>) response.data();
    }
    @Override
    public void addBilet(Bilet bilet) throws Exception {
        initializeConnection();
        Request req = new Request.Builder()
                .type(RequestType.BUY_BILET)
                .data(bilet)
                .build();
        sendRequest(req);
        Response response = readResponse();

    }
    @Override
    public Angajat findAngajatByUserAndPass(String username, String password) throws Exception {
        initializeConnection();
        Angajat angajat = new Angajat(username, password);
        Request req = new Request.Builder()
                .type(RequestType.LOGIN)
                .data(angajat)
                .build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.OK) {
            this.observer = observer;
            return (Angajat) response.data();
        }
        if (response.type() == ResponseType.ERROR) {
            //String err = response.data().toString();
            closeConnection();
            return null;
        }
        return null;
    }
    @Override
    public List<String> addDestinations() throws Exception {
        initializeConnection();
        Request req = new Request.Builder()
                .type(RequestType.GET_DESTINATIONS)
                .build();
        sendRequest(req);
        Response response = readResponse();
        return (List<String>) response.data();

    }

    @Override
    public List<Zbor> findZboruriByDestinatieAndDate(String destinatie, Date dataplecarii) throws Exception {
        initializeConnection();
        Zbor zbor = new Zbor(destinatie, dataplecarii, null, 0);

        Request req = new Request.Builder()
                .type(RequestType.FILTER_ZBORURI)
                .data(zbor)
                .build();
        sendRequest(req);
        Response response = readResponse();
        return (List<Zbor>) response.data();
    }

    @Override
    public Turist findOrAddTurist(String nume) throws Exception {
        initializeConnection();
        Request req = new Request.Builder()
                .type(RequestType.FIND_ADD_TURIST)
                .data(nume)
                .build();
        sendRequest(req);
        Response response = readResponse();
        return (Turist) response.data();
    }

    @Override
    public List<Turist> findOrAddTurists(List<String> listaTuristi) throws Exception {
        return null;
    }

    @Override
    public void updateZbor(Zbor zbor) throws Exception {
        initializeConnection();
        Request req = new Request.Builder()
                .type(RequestType.UPDATE_ZBOR)
                .data(zbor)
                .build();
        sendRequest(req);
        Response response = readResponse();
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{

                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
