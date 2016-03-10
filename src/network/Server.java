package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;

import config.Config;
import game.GameEngine;

public class Server implements Runnable {
	private int numPlayers = 0;
	private Thread thread = null;
	private ServerSocket server = null;
	private HashMap<Integer, ServerThread> clients;
	private Logger log = Logger.getLogger("Server");
	private GameEngine game;
	private boolean minPlayers = false;
	private boolean maxPlayers = false; 
	private ArrayList<String> names = new ArrayList<String>();

	public Server(){
		runServer(Config.DEFAULT_PORT);
	}
	
	public boolean testMaxPlayers(){return maxPlayers;}
	public boolean testMinPlayers(){return minPlayers;}
	
	public void runServer(int port) {
		try{
			log.info("Binding to port " + port + ", please wait...");
			this.clients = new HashMap<Integer, ServerThread>();
			this.server = new ServerSocket(port);
			this.server.setReuseAddress(true);
			this.start();
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

	@Override
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
			
			if(numPlayers == 2){
				minPlayers = true;
			}
			else if(numPlayers == 5){
				maxPlayers = true;
			}
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
		
		/* Server receives message that client has quit */
		if (msg.equals(Config.QUIT)) {
			log.info(String.format("Removing Client: %d", id));
			if (clients.containsKey(id)) {
				clients.get(id).send("quit!" + "\n");
				remove(id);
			}
		}
		
		/* All clients have quit therefore, the server will now shutdown */
		else if (msg.equals(Config.SHUTDOWN)){ 
			shutdown(); 
		}
		
		/* When a client first connects, it checks to see if this is the first client */
		else if (msg.equals(Config.CLIENT_START)){
			if(numPlayers == 1){
				send1Client(id, Config.FIRSTPLAYER);
			}else{
				send1Client(id, Config.PROMPT_JOIN);
			}
		}
		
		/* Checks the number of players is between 2 and 5 */
		else if(msg.contains(Config.START)){
			String s[] = msg.split(" ");
			
			if(Integer.parseInt(s[1]) < 2 || Integer.parseInt(s[1]) > 5){
				send1Client(id, Config.NOT_ENOUGH);
			}
			
			else{
				send = game.processInput(msg);
				processInput(id, send);
			}
		}
		
		/* Double checking to make sure that no 2 players have the same name */
		else if (msg.contains(Config.JOIN)){
			String join[] = msg.split(" ");
			int count = 0;
			
			// adding the first player 
			if(numPlayers == 1){
				names.add(join[1]);
			}
			
			// checking for all other players
			else{
				for(int i = 0; i < names.size(); i++){
					if(names.get(i).equalsIgnoreCase(join[1])){
						// Brit can you tell me if this is proper coding convention or if i should make a temp 
						msg = Config.DUPLICATE;
						break;
					}
					count++;
				}
				
				if(!msg.equals(Config.DUPLICATE)){
					names.add(join[1]);
				}
			}
			send = game.processInput(msg);
			processInput(id, send);
		}
		
		/* All other messages from the client */
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
		
		else if(send.contains(Config.DUPLICATE)){
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

		else if(send.contains(Config.TURN)){			
			send1Client(id, send);
		}

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
		
		else{
			sendAllClients(send);
		}
	}
}
