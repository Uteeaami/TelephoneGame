package fi.utu.tech.telephonegame.network;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.*;

public class NetworkService extends Thread implements Network {
	/*
	 * Do not change the existing class variables
	 * New variables can be added
	 */
	private TransferQueue<Object> inQueue = new LinkedTransferQueue<Object>(); // For messages incoming from network
	private TransferQueue<Serializable> outQueue = new LinkedTransferQueue<Serializable>(); // For messages outgoing to network
	private LinkedBlockingQueue<ClientHandler> clientList = new LinkedBlockingQueue<>(); //List for tracking all clients, Edit: now it's thread safe

	/*
	 * No need to change the constructor
	 */
	public NetworkService() {this.start();}

	/**
	 * Creates a server instance and starts listening for new peers on specified port
	 * The port used to listen incoming connections is provided by the template
	 * 
	 * @param serverPort Which port should we start to listen to?
	 * 
	 */
	public void startListening(int serverPort) {
		System.out.printf("I should start listening for peers at port %d%n", serverPort);
		// TODO
		//Make a new Server object that gets the list of clients and TQ as a parameter to make the server to be also an client.
		//+ start the run-method in Server class
		Server myThread = new Server(serverPort,inQueue, clientList);
		myThread.start();
	}

	/**
	 * This method will be called when connecting to a peer (other broken telephone
	 * instance)
	 * The IP address and port will be provided by the template (by the resolver)
	 * 
	 * @param peerIP   The IP address to connect to
	 * @param peerPort The TCP port to connect to
	 */
	public void connect(String peerIP, int peerPort) throws IOException {
		System.out.printf("I should connect myself to %s, port %d%n", peerIP, peerPort);
		// TODO

		//Make a new socket, pass it as a parameter alongside with TQ to create a ClienHandler object
		//append the list of clients by this object and start run method in ClientHandler
		Socket socket = new Socket(peerIP,peerPort);
		ClientHandler client = new ClientHandler(socket, inQueue);
		clientList.add(client);
		client.start();
	}

	/**
	 * This method is used to send the message to all connected neighbours (directly connected nodes)
	 * 
	 * @param out The serializable object to be sent to all the connected nodes
	 * 
	 */
	private void send(Serializable out) throws InterruptedException, IOException {
		// TODO
		//Sends message to everyone by iterating the list of clients
		for(ClientHandler cl : clientList){
			cl.sendIt(out);
		}
	}

	/*
	 * Don't edit any methods below this comment
	 * Contains methods to move data between Network and 
	 * MessageBroker
	 * You might want to read still...
	 */

	/**
	 * Add an object to the queue for sending
	 * 
	 * @param outMessage The Serializable object to be sent
	 */
	public void postMessage(Serializable outMessage) {
		try {
			outQueue.offer(outMessage, 1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Get reference to the queue containing incoming messages from the network
	 * 
	 * @return Reference to the queue incoming messages queue
	 */
	public TransferQueue<Object> getInputQueue() {
		return this.inQueue;
	}

	/**
	 * Waits for messages from the core application and forwards them to the network
	 */
	public void run() {
		while (true) {
			try {
				send(outQueue.take());
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
	}


}
