package network.rpcprotocol;


import service.IService;
import service.Observer;
import domain.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;


public class ClientRpcWorker implements Runnable, Observer {
    private IService server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ClientRpcWorker(IService server, Socket connection) {
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
                Response response=handleRequest((Request)request);
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


    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request){
        Response response=null;
        if (request.type()== RequestType.LOGIN){
            System.out.println("Login request ..."+request.type());
            Angajat angajat=(Angajat)request.data();
            try {
                Angajat a=server.findAngajatByUserAndPass(angajat.getUser(), angajat.getPassword(),this);
                Response response1 = new Response.Builder().type(ResponseType.OK).data(a).build();
                return response1;
            } catch (Exception e) {
                connected=false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).data(null).build();
            }
        }
        if (request.type()== RequestType.GET_ZBORURI){
            System.out.println("GetZboruri Request ...");
            try {
                List<Zbor> zboruri=server.findAllZboruri();
                return new Response.Builder().type(ResponseType.GET_ZBORURI).data(zboruri).build();
            } catch (Exception e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type()== RequestType.FILTER_ZBORURI){
            System.out.println("FilterZboruri Request ...");
            Zbor zbor=(Zbor)request.data();
            try {
                List<Zbor> zboruri=server.findZboruriByDestinatieAndDate(zbor.getDestinatia(), zbor.getDataPlecarii());
                return new Response.Builder().type(ResponseType.FILTER_ZBORURI).data(zboruri).build();
            } catch (Exception e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type()== RequestType.FIND_ADD_TURIST){
            System.out.println("FindAddTurist Request ...");
            String nume=(String) request.data();
            try {
                Turist t =server.findOrAddTurist(nume);
                return new Response.Builder().type(ResponseType.FIND_ADD_TURIST).data(t).build();
            } catch (Exception e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type()== RequestType.UPDATE_ZBOR){
            System.out.println("UpdateZbor Request ...");
            Zbor zbor=(Zbor)request.data();
            try {
                server.updateZbor(zbor);
                return okResponse;
            } catch (Exception e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type()== RequestType.BUY_BILET){
            System.out.println("BuyBilet Request ...");
            Bilet bilet=(Bilet)request.data();
            try {
                server.addBilet(bilet);
                return okResponse;
            } catch (Exception e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type()== RequestType.LOGOUT){
            System.out.println("LOGOUT Request ...");
            Angajat angajat=(Angajat) request.data();
            try {
                server.Logout(angajat);
                return okResponse;
            } catch (Exception e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        return null;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }


    @Override
    public void updateZbor(Zbor zbor) throws Exception {
        Response resp=new Response.Builder().type(ResponseType.UPDATE_ZBOR).data(zbor).build();
        System.out.println("Updated zbor in "+zbor);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
