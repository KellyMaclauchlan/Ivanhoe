package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import org.apache.log4j.Logger;

import config.Config;
import game.GameEngine;

public class Server implements Runnable {
	public int numPlayers = 0;
	public Thread thread = null;
	public ServerSocket server = null;
	public HashMap<Integer, ServerThread> clients;
	public Logger log = Logger.getLogger("Server");
	public GameEngine game;
	public Scanner sc = new Scanner(System.in);
	public boolean minPlayers = false;
	public boolean maxPlayers = false; 

	public Server(){
		runServer(Config.DEFAULT_PORT);
	}
	
	public boolean testMaxPlayers(){return maxPlayers;}
	public boolean testMinPlayers(){return minPlayers;}
	
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
		game = new GameEngine();
		log.info("Game has started");
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
		if(numPlayers <= Config.MAX_PLAYERS){
			log.info("Client accepted: " + socket );
			try{
				ServerThread sThread = new ServerThread(this, socket);
				sThread.open();
				sThread.start();
				clients.put(sThread.getID(), sThread);
				this.numPlayers++; 
			}catch (IOException e){
				log.error(e);
			}
			
			if(numPlayers == 2){minPlayers = true;}
			if(numPlayers == 5){maxPlayers = true;}
		}else{
			log.info("Client Thread tried to connect: " + socket);
			log.info("Client refused: maximum number of clients reached: " + Config.MAX_PLAYERS);
			maxPlayers = false; 
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

	public void handle(int id, String msg) {
		System.out.println("Message Receieved: " + msg);

		log.info("Message Received: " + msg);
		String send = "input";
		
		if (msg.equals("quit")) {
			log.info(String.format("Removing Client: %d", id));
			if (clients.containsKey(id)) {
				clients.get(id).send("quit!" + "\n");
				remove(id);
			}
		}else if (msg.equals("shutdown")){ shutdown(); }
		
		else if (msg.equals(Config.CLIENT_START)){
			if(numPlayers == 1){
				send1Client(id, Config.FIRSTPLAYER);
			}else{
				send1Client(id, Config.PROMPT_JOIN);
			}
		} 
		
		else {
			send = game.processInput(msg);
			processInput(id, send);
		}
	}
	
	public void send1Client(int id, String msg){
		ServerThread to = clients.get(id);
		to.send(String.format("%s\n", msg));
	}
	
	public void sendAllClients(String msg){
		for(ServerThread to : clients.values()){
			to.send(String.format("%s\n", msg));
		}
	}
	
	/* Figures out whether to send the message to all the clients or just one */
	public void processInput(int id, String send){
		if(send.contains(Config.PROMPT_JOIN)){
			send1Client(id, send);
		}
		
		else if (send.contains(Config.MAX)){
			send1Client(id, send);
		}
		else if (send.contains(Config.UNPLAYABLE)){
			send1Client(id, send);
		}
		
		else if(send.contains(Config.PLAYER_NAME)){
			sendAllClients(send);
		}
		
		else if(send.contains(Config.NEED_PLAYERS)){
			send1Client(id, send);
		}
		
		// output = purple <player name> turn <player name> (first turn) <card picked up> 
		// OR output = turn <player name> <card picked up> (subsequent turns)
		else if(send.contains(Config.TURN)){			

			send1Client(id, send);
			
		}
		
		
		// CHECK BRIT'S GAME ENGINE CHANGE ! 
		// KATIE TO DO: If output = stunned <card played> then send me end turn
		else if (send.contains(Config.PLAY)){
			sendAllClients(send);
		}
		
		else if (send.contains(Config.WAITING)){
			sendAllClients(send);
		}
		
		else if(send.contains(Config.POINTS)){
			sendAllClients(send);
		}
		
		else {
			sendAllClients(send);
		}
	}
}
