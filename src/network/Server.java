package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.log4j.Logger;
import config.Config;
import game.GameEngine;
import game.Player;

public class Server implements Runnable {
	public int numPlayers = 0;
	public Thread thread = null;
	public ServerSocket server = null;
	public HashMap<Integer, ServerThread> clients;
	public Logger log = Logger.getLogger("Server");
	public GameEngine game;
	public Scanner sc = new Scanner(System.in);
	
	public ArrayList<Player> players = new ArrayList<Player>();

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
		game = new GameEngine();
		log.info("Game has started");
		
		System.out.println("How many players in the game? (2 to 5 players only)\n");
		numPlayers = sc.nextInt();
		handle(0, Config.START + " " + numPlayers);
		
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
		if(players.size() <= numPlayers/*Config.MAX_PLAYERS*/){
			try{
				ServerThread sThread = new ServerThread(this, socket);
				sThread.open();
				sThread.start();
				clients.put(sThread.getID(), sThread);
				this.numPlayers++; 
			}catch (IOException e){
				log.error(e);
			}
		}
		
	}


	public void handle(int id, String msg) {
		System.out.println("Message Receieved: " + msg);
		log.info("Message Received: " + msg);
		String send = "waiting";
		
		
		if (msg.equals("quit")) {
			log.info(String.format("Removing Client: %d", id));
			if (clients.containsKey(id)) {
				clients.get(id).send("quit!" + "\n");
				remove(id);
			}
		}else if (msg.equals("shutdown")){ shutdown(); }
		
		else if (msg.contains(Config.JOIN)){
			String[] text = msg.split(" "); 
			Player newPlayer = new Player(text[1]); 
			players.add(newPlayer); 
			
			send = game.processInput(msg);
			send2Clients(send);
			log.info("Message Sent: " + send);
			System.out.println(send);
		}

		else {			
			send = game.processInput(msg);
			
			send2Clients(send);
			log.info("Message Sent: " + send);
			System.out.println(send);
		}
	}
	
	public void send2Clients(String msg){
		for(ServerThread to : clients.values()){
			System.out.println("Sending to clients");
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
