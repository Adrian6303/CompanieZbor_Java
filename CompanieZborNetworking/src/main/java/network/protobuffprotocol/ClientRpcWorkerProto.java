package network.protobuffprotocol;

import domain.Angajat;
import domain.Bilet;
import domain.Turist;
import domain.Zbor;
import network.rpcprotocol.Request;
import network.rpcprotocol.RequestType;
import network.rpcprotocol.Response;
import network.rpcprotocol.ResponseType;
import service.IService;
import service.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientRpcWorkerProto implements Runnable, Observer {
    private IService server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ClientRpcWorkerProto(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                CZbor.Response response=handleRequest((CZbor.Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }


    private static CZbor.Response okResponse=new ProtoUtils().createOkResponse();

    private CZbor.Response handleRequest(CZbor.Request request){
        CZbor.Response response=null;
        if (request.getType()== CZbor.Request.RequestType.LOGIN){
            System.out.println("Login request ..."+request.getType());
            Angajat angajat= ProtoUtils.getAngajat(request);
            try {
                //Angajat a=server.findAngajatByUserAndPass(angajat.getUser(), angajat.getPassword(),this);
                Angajat angajat1 = server.findAngajatByUserAndPass(angajat.getUser(), angajat.getPassword(),this);
                if(angajat1!=null){
                    return ProtoUtils.createOkResponse(angajat1);
                }
                else{
                    connected = false;
                    return ProtoUtils.createErrorResponse();
                }
            } catch (Exception e) {
                connected=false;
                return ProtoUtils.createErrorResponse();
            }
        }
        if (request.getType()== CZbor.Request.RequestType.GET_ZBORURI){
            System.out.println("GetZboruri Request ...");
            try {
                //List<Zbor> zboruri=server.findAllZboruri();
                //return new Response.Builder().type(ResponseType.GET_ZBORURI).data(zboruri).build();
                List<Zbor> zboruri = server.findAllZboruri();
                return ProtoUtils.createGetZboruriResponse(zboruri);

            } catch (Exception e) {
                return ProtoUtils.createErrorResponse();
            }
        }
        if (request.getType()== CZbor.Request.RequestType.FILTER_ZBORURI){
            System.out.println("FilterZboruri Request ...");
            Zbor zbor=ProtoUtils.getZbor(request);
            try {
                //List<Zbor> zboruri=server.findZboruriByDestinatieAndDate(zbor.getDestinatia(), zbor.getDataPlecarii());
                //return new Response.Builder().type(ResponseType.FILTER_ZBORURI).data(zboruri).build();
                List<Zbor> zboruri = server.findZboruriByDestinatieAndDate(zbor.getDestinatia(), zbor.getDataPlecarii());
                return ProtoUtils.createFilterZboruriResponse(zboruri);
            } catch (Exception e) {
                return ProtoUtils.createErrorResponse();
            }
        }
        if (request.getType()== CZbor.Request.RequestType.FIND_ADD_TURIST){
            System.out.println("FindAddTurist Request ...");
            String nume=(String) request.getName();
            try {
                //Turist t =server.findOrAddTurist(nume);
                //return new Response.Builder().type(ResponseType.FIND_ADD_TURIST).data(t).build();
                Turist turist = server.findOrAddTurist(nume);
                return ProtoUtils.createFindAddTuristResponse(turist);
            } catch (Exception e) {
                return ProtoUtils.createErrorResponse();
            }
        }
        if (request.getType()== CZbor.Request.RequestType.UPDATE_ZBOR){
            System.out.println("UpdateZbor Request ...");
            Zbor zbor= ProtoUtils.getZbor(request);
            try {
                //server.updateZbor(zbor);
                //return okResponse;
                server.updateZbor(zbor);
                return ProtoUtils.createOkResponse();
            } catch (Exception e) {
                return ProtoUtils.createErrorResponse();
            }
        }
        if (request.getType()== CZbor.Request.RequestType.BUY_BILET){
            System.out.println("BuyBilet Request ...");
            Bilet bilet= ProtoUtils.getBilet(request);
            try {
                server.addBilet(bilet);
                return ProtoUtils.createOkResponse();
            } catch (Exception e) {
                return ProtoUtils.createErrorResponse();
            }
        }
        if (request.getType()== CZbor.Request.RequestType.LOGOUT){
            System.out.println("LOGOUT Request ...");
            Angajat angajat= ProtoUtils.getAngajat(request);
            try {
                server.Logout(angajat);
                return ProtoUtils.createOkResponse();
            } catch (Exception e) {
                return ProtoUtils.createErrorResponse();
            }
        }
        return null;
    }

    private void sendResponse(CZbor.Response response) throws IOException{
        System.out.println("sending response "+response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }


    @Override
    public void updateZbor(Zbor zbor) throws Exception {
        CZbor.Response resp= ProtoUtils.createUpdateZborResponse();
        System.out.println("Updated zbor in "+zbor);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
