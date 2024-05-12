package network.protobuffprotocol;

import domain.Angajat;
import domain.Bilet;
import domain.Turist;
import domain.Zbor;
import network.protobuffprotocol.ProtoUtils;
import service.*;

import java.io.*;
import java.net.Socket;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ServicesRpcProxyProto implements IService {
    private String host;
    private int port;

    private Observer observer;

    private InputStream input;
    private OutputStream output;
    private Socket connection;

    private BlockingQueue<CZbor.Response> qresponses;
    private volatile boolean finished;
    public ServicesRpcProxyProto(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<CZbor.Response>();
    }

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

    private synchronized void sendRequest(CZbor.Request request)throws Exception {
        try {
            //output.writeObject(request);
            request.writeDelimitedTo(output);
            output.flush();
        } catch (IOException e) {
            throw new Exception(e);
        }

    }

    private CZbor.Response readResponse() throws Exception {
        CZbor.Response response=null;
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
            output= connection.getOutputStream();
            //output.flush();
            input= connection.getInputStream();
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new network.protobuffprotocol.ServicesRpcProxyProto.ReaderThread());
        tw.start();
    }


    private void handleUpdate(CZbor.Response response){
        if (response.getType() == CZbor.Response.ResponseType.UPDATE_ZBOR){

            try {
                observer.updateZbor(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUpdate(CZbor.Response response){
        return response.getType()== CZbor.Response.ResponseType.UPDATE_ZBOR;
    }

    @Override
    public List<Zbor> findAllZboruri() throws Exception {
//        Request req = new Request.Builder()
//                .type(RequestType.GET_ZBORURI)
//                .build();
//        sendRequest(req);
        sendRequest(ProtoUtils.createGetZboruriRequest());
        CZbor.Response response = readResponse();
        return (List<Zbor>) ProtoUtils.getZboruri(response);
    }
    @Override
    public void addBilet(Bilet bilet) throws Exception {
//        Request req = new Request.Builder()
//                .type(RequestType.BUY_BILET)
//                .data(bilet)
//                .build();
        sendRequest(ProtoUtils.createAddBiletRequest(bilet));
        CZbor.Response response = readResponse();

    }
    @Override
    public Angajat findAngajatByUserAndPass(String username, String password,Observer observer) throws Exception {
        initializeConnection();
        Angajat angajat = new Angajat(username, password);
//        Request req = new Request.Builder()
//                .type(RequestType.LOGIN)
//                .data(angajat)
//                .build();
        sendRequest(ProtoUtils.createLoginRequest(angajat));
        CZbor.Response response = readResponse();
        if (response.getType() == CZbor.Response.ResponseType.OK) {
            this.observer = observer;
            return (Angajat) ProtoUtils.getAngajat(response);
        }
        if (response.getType() == CZbor.Response.ResponseType.ERROR) {
            closeConnection();
            return null;
        }
        return null;
    }

    @Override
    public void setObserver(Angajat angajat, Observer obs) {

    }

    @Override
    public List<Zbor> findZboruriByDestinatieAndDate(String destinatie, Date dataplecarii) throws Exception {
        Zbor zbor = new Zbor(destinatie, dataplecarii, null, 0);

//        Request req = new Request.Builder()
//                .type(RequestType.FILTER_ZBORURI)
//                .data(zbor)
//                .build();
        sendRequest(ProtoUtils.createFindZboruriByDestinatieAndDateRequest(zbor));
        CZbor.Response response = readResponse();
        return (List<Zbor>) ProtoUtils.getZboruri(response);
    }

    @Override
    public Turist findOrAddTurist(String nume) throws Exception {
//        Request req = new Request.Builder()
//                .type(RequestType.FIND_ADD_TURIST)
//                .data(nume)
//                .build();
        sendRequest(ProtoUtils.createFindOrAddTuristRequest(nume));
        CZbor.Response response = readResponse();
        return (Turist) ProtoUtils.getTurist(response);
    }

    @Override
    public void updateZbor(Zbor zbor) throws Exception {

//        Request req = new Request.Builder()
//                .type(RequestType.UPDATE_ZBOR)
//                .data(zbor)
//                .build();
        sendRequest(ProtoUtils.createUpdateZborRequest(zbor));
        CZbor.Response response = readResponse();
    }

    @Override
    public void Logout(Angajat angajat) throws Exception {
//        Request req = new Request.Builder()
//                .type(RequestType.LOGOUT)
//                .data(angajat)
//                .build();
        sendRequest(ProtoUtils.createLogoutRequest(angajat));
        CZbor.Response response =readResponse();
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    //Object response=input.readObject();
                    CZbor.Response response = CZbor.Response.parseDelimitedFrom(input);
                    System.out.println("response received "+response);
                    if (isUpdate((CZbor.Response)response)){
                        handleUpdate((CZbor.Response)response);
                    }else{

                        try {
                            qresponses.put((CZbor.Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }
}
