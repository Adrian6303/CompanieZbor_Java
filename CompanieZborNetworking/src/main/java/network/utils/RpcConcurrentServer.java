package network.utils;

import network.rpcprotocol.*;
import network.utils.*;
import network.rpcprotocol.ClientRpcWorker;
import service.IService;

import java.net.Socket;


public class RpcConcurrentServer extends AbsConcurrentServer {
    private IService chatServer;
    public RpcConcurrentServer(int port, IService chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
       // ChatClientRpcWorker worker=new ChatClientRpcWorker(chatServer, client);
        ClientRpcWorker worker=new ClientRpcWorker(chatServer, client);

        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
