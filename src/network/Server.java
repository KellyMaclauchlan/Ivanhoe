package network;

import game.GameEngine;
import game.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import config.Config;

public class Server implements Runnable {
	//public int players = 0;
	public Thread thread = null;
	public ServerSocket server = null;
	public HashMap<Integer, ServerThread> clients;
	public Logger log = Logger.getLogger("Server");
	
	public ArrayList<Player> players; 
	public GameEngine game;

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
			game = new GameEngine();
			
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
		if(players.size() < Config.MAX_PLAYERS){
			try{
				ServerThread sThread = new ServerThread(this, socket);
				sThread.open();
				sThread.start();
				clients.put(sThread.getID(), sThread);
				//Player p = new Player();
				//this.players.add(p);
			}catch (IOException e){
				log.error(e);
			}
		}
		
	}


	public void handle(int id, String msg) {
		String action; 
		System.out.println("Message Received: " + msg);
		if (msg.equals("quit")) {
			log.info(String.format("Removing Client: %d", id));
			if (clients.containsKey(id)) {
				clients.get(id).send("quit!" + "\n");
				remove(id);
			}
		}else if (msg.equals("shutdown")){ shutdown(); }

		else {
			action = game.processInput(msg); 
			send2Clients(action);
			log.info("Message Sent: " + action);
		}
	}
	
	public void send2Clients(String msg){
		for(ServerThread to : clients.values()){
			System.out.println("Sending to clients: " + msg);
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
