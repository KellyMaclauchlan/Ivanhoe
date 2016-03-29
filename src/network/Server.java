package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.apache.log4j.Logger;

import ai.AI;
import ai.StrategyPlayAll;
import ai.StrategyWithdraw;
import config.Config;
import config.Observer;
import game.GameEngine;

public class Server implements Runnable, Observer {
	private int numPlayers = 0;
	private Thread thread = null;
	private ServerSocket server = null;
	private HashMap<Integer, ServerThread> clients;
	private Logger log = Logger.getLogger("Server");
	private GameEngine game;
	private boolean minPlayers = false;
	private boolean maxPlayers = false; 
	private ArrayList<String> names = new ArrayList<String>();
	private AI ai;
	private ArrayList<AI> aiPlayers = new ArrayList<AI>();
	
	private String send = Config.OUTPUT;

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
		
		/* Server receives message that client has quit */
		if (msg.equals(Config.QUIT) || msg.equals(null)) {
			log.info(String.format("Removing Client: %d", id));
			if (clients.containsKey(id)) {
				remove(id);
			}
			msg = Config.PLAYER_LEFT;
			send = game.processInput(msg);
			processInput(id, send);
		}
		
		/* When a client first connects, it checks to see if this is the first client */
		else if (msg.equals(Config.CLIENT_START)){
			checkStart(id);
		}
		
		/* Double checking to make sure that no 2 players have the same name */
		else if (msg.contains(Config.JOIN)){
			doubleCheckNames(id, msg);
		}
		
		/* Receives the number of players and AIs for the game */
		else if (msg.startsWith(Config.START)){
			String[] input = msg.split(" ");
			String reConstruct = input[0] + " " + input[1];
			
			produceAI(Integer.parseInt(input[2]));
			
			send = game.processInput(reConstruct);
			processInput(id, send);
		}
		
		/* All other messages from the client */
		else {
			send = game.processInput(msg);
			processInput(id, send);
			sendToAI(send);
		}
	}
	
	/* Creates the correct number of AIs that the first player has specified */
	public void produceAI(int a){
		Random rand = new Random();
		int dd = 0;
		for(int i = 0; i < a; i++){
			//int r = rand.nextInt(2) + 1;
			int r = 1;
			if(r == 1 ){
				ai = new AI(new StrategyPlayAll("AI" + dd));
			}
			else if(r == 2){
				ai = new AI(new StrategyWithdraw("AI" + dd));
			}
			ai.setName("AI" + dd);
			ai.registerObserver(this);
			aiPlayers.add(ai);
			game.joinGame(ai);
		}
	}
	
	/* Sends the game engine messages to the AI */
	public void sendToAI(String msg){
		for(AI i : aiPlayers){
			i.processInput(msg);
		}
	}
	
	/* Checks to see who the first player is so the correct popups can occur */
	public void checkStart(int id){
		if(numPlayers == 1){
			send1Client(id, Config.FIRSTPLAYER);
		}else{
			send1Client(id, Config.PROMPT_JOIN);
		}
	}
	
	/* Checks for duplicate names (no 2 players can have the same name) */
	public void doubleCheckNames(int id, String msg){
		String send = "input";
		String join[] = msg.split(" ");
		
		// adding the first player 
		if(numPlayers == 1){
			names.add(join[1]);
		}
		
		// checking for all other players
		else{
			for(int i = 0; i < names.size(); i++){
				if(names.get(i).equalsIgnoreCase(join[1])){
					msg = Config.DUPLICATE;
					break;
				}
			}
			
			if(!msg.equals(Config.DUPLICATE)){
				names.add(join[1]);
			}
		}
		send = game.processInput(msg);
		processInput(id, send);
		sendToAI(send);
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
		
		else if(send.contains(Config.HAND)){
			sendAllClients(send);
		}
		
		else if(send.contains(Config.NEED_PLAYERS)){
			send1Client(id, send);
		}

		else if(send.contains(Config.TURN)){			
			send1Client(id, send);
		}

		else if (send.contains(Config.PLAY)){
			sendAllClients(send);
		}
		
		else if (send.contains(Config.WAITING)){
			sendAllClients(send);
		}
		
		else if(send.contains(Config.POINTS)){
			sendAllClients(send);
		} 
		else if(send.contains(Config.IS_STUNNED)) {
			send1Client(id, send);
		}
		
		else{
			sendAllClients(send);
		}
	}

	/* Used for the AI to communicate with the Server (Observer Pattern) */
	public void update(String msg) {
		this.handle(0, msg);
	}
}
