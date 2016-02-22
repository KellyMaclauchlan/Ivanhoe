package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import org.apache.log4j.Logger;

import config.Config;

public class Server implements Runnable {
	public int players = 0;
	public Thread thread = null;
	public ServerSocket server = null;
	public HashMap<Integer, ServerThread> clients;
	public Logger log = Logger.getLogger("Server");

	public Server(){
		runServer(Config.DEFAULT_PORT);
	}
	
	public void runServer(int port) {
		try{
			System.out.println("Binding to port " + port + ", please wait...");
			log.info("Binding to port " + port + ", please wait...");
			clients = new HashMap<Integer, ServerThread>();
			server = new ServerSocket(port);
			server.setReuseAddress(true);
			start();
		}catch(IOException e){
			log.error(e);
		}
	}
	
	public void start() {
		if(thread == null){
			thread = new Thread(this);
			log.info("New ServerThread created");
			thread.start();
		}
	}

	public void run() {
		while(thread != null){
			try{
				System.out.println("Waiting for Clients");
				log.info("Waiting for Clients");
				addThread(server.accept());
			}catch(IOException e){
				log.error(e);
			}
		}
		
	}

	public void addThread(Socket socket) {
		log.info("Client accepted: " + socket );
		if(players < Config.MAX_PLAYERS){
			try{
				ServerThread sThread = new ServerThread(this, socket);
				sThread.open();
				sThread.start();
				clients.put(sThread.getID(), sThread);
				this.players++; 
			}catch (IOException e){
				log.error(e);
			}
		}
		
	}


	public void handle(int id, String msg) {
		System.out.println("Message Received: " + msg);
		if (msg.equals("quit")) {
			log.info(String.format("Removing Client: %d", id));
			if (clients.containsKey(id)) {
				clients.get(id).send("quit!" + "\n");
				remove(id);
			}
		}else if (msg.equals("shutdown")){ shutdown(); }

		else {
			System.out.println("Size: " + clients.size());
			//ServerThread from = clients.get(id);
			//for (ServerThread to : clients.values()) {
				//if (to.getID() != id) {
				//		to.send(String.format("%5d: %s\n", id, input));
				//		log.info(String.format("Sending Message from %s:%d to %s:%d: ",from.getSocketAddress(),from.getID(), to.getSocketAddress(), to.getID(), input));
				//}
				//to.send(String.format("%s", msg));
				send2Clients(msg);
				log.info("Message Sent: " + msg);
			//}	
		}
	}
	
	public void send2Clients(String msg){
		for(ServerThread to : clients.values()){
			System.out.println("SEnding to clients");
			to.send(String.format("%s\n", msg));
		}
	}

	public void remove(int id) {
		if(clients.containsKey(id)){
			ServerThread terminate = clients.get(id);
			clients.remove(id);
			log.info("Player " + id + " is being removed");
			
			terminate.close();
			terminate = null;
			log.info("Removed " + id);
		}
	}
	
	public void shutdown() {
		try {
			server.close();
		} catch (IOException e) {
			log.error(e);
		}
	}
}
