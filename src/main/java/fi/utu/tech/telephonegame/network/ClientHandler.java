package fi.utu.tech.telephonegame.network;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TransferQueue;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private TransferQueue<Object> outQue;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;

    public ClientHandler(Socket socket, TransferQueue<Object> transferQue) {
        this.clientSocket = socket;
        this.outQue = transferQue;
    }
    //Method to write and flush OOS (Sends the message to all neighbouring nodes)
    public void sendIt(Serializable out) throws IOException {
        outStream.writeObject(out);
        outStream.flush();
    }

    @Override
    public void run(){
        try{
            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inStream = new ObjectInputStream(clientSocket.getInputStream());
            while(true){
                Object inObject = inStream.readObject();
                outQue.offer(inObject);
            }
        }catch (IOException | ClassNotFoundException e){e.printStackTrace();}
    }
}
