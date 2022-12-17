package fi.utu.tech.telephonegame.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TransferQueue;

public class Server extends Thread {
    private final int port;
    private TransferQueue<Object> transferQue;
    public static LinkedBlockingQueue<ClientHandler> clientList = new LinkedBlockingQueue<>();

    public Server(int port, TransferQueue<Object> transferQue, LinkedBlockingQueue<ClientHandler> clientList){
        this.port = port;
        this.transferQue = transferQue;
        this.clientList = clientList;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true){
                Socket socket = serverSocket.accept();
                //Server is also a client since peer-to-peer
                ClientHandler ch = new ClientHandler(socket, transferQue);
                clientList.add(ch);
                ch.start();
                System.out.println("Client: "+ Thread.currentThread().getName() +" connected..");
            }
        }catch (IOException e){e.printStackTrace();}


    }

}
