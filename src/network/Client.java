package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

import config.Config;
import ui.MainWindowController;
import config.Observer;

public class Client implements Runnable, Observer {
	public int ID = 0;
	public Socket socket = null;
	public Thread thread = null;
	public ClientThread client = null;
	public BufferedReader console = null;
	public BufferedReader inStream = null;
	public BufferedWriter outStream = null;
	
	public String testing = null;
	public Logger log = Logger.getLogger("Client");
	public MainWindowController window;
	
	public String playedCards = null;

	public int getID(){
		return this.ID;
	}
	
	public Client(){}
	
	public boolean connectToServer(String serverIP, int serverPort) {
		//System.out.println(ID + ":Establishing connection. Please wait... ");
		log.info(ID + ":Establishing connection. Please wait... ");
		boolean connected = false;
		
		try{
			this.socket = new Socket(serverIP, serverPort);
			this.ID = socket.getLocalPort(); 
			
			log.info(ID + ": Connected to server: " + socket.getInetAddress());
	    	log.info(ID + ": Connected to portid: " + socket.getLocalPort());
	    	
	    	this.start();
	    	log.info("Client has started");
	    	connected = true;
		}catch(IOException e){
			connected = false;
			log.error(e);
		}
		return connected;
	}
	
	private void start() throws IOException{
		try{
			console = new BufferedReader(new InputStreamReader(System.in));
			inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			log.info("Initializing buffers");
			
			if(thread == null){
				client = new ClientThread(this, socket);
				thread = new Thread(this);
				thread.start();
				log.info("New ClientThread has started");
			}
			
			window = new MainWindowController();
			//window.showWindow();
			//startGame();
			handle(Config.START);
		}catch (IOException e){
			log.error(e);
			throw e; 
		}
	}

	public void run() {
		//System.out.println(ID + ": Client Started...");
		//System.out.println("\n Hit 'ENTER' to start");
		
		/*
		 * check number of clients currently connected first
		 * prompt user for name
		 * send user name to server, add server to arraylist of players 
		 * 
		 */
		while (thread != null) {  
			try {  
				if (outStream != null) {
					outStream.flush();
					outStream.write(console.readLine() + "\n");
				} else {
					log.info(ID + ": Stream Closed");
					System.out.println(ID + ": Stream Closed");
				}
         }
         catch(IOException e) {  
         	log.error(e);
         	stop();
         }}
	}
	
	/* Client has to send something twice and then will receive its echo
	 * 
	 */
	
	public void handle(String msg) {
		String send = "waiting";
		
		log.info("Message Received: " + msg);
		
	   	if (msg.equalsIgnoreCase("quit!")) {  
				System.out.println(ID + "Good bye. Press RETURN to exit ...");
				stop();
			} else {
				testing = msg;
				//System.out.println("Message Received: " + msg);
				send = processInput(msg);
				System.out.println(msg);
			}
	}


	public String testMessages() {
		return testing;
	}

	@Override
	public void update(String message) {
		if(message.equals(Config.PLAYEDCARD)){
			playedCards = window.lastCard.getCardType() + " " +  window.lastCard.getValue(); 
		}
		
		else if (message.equals(Config.DROP_OUT)){
			
		}
		else if(message.equals(Config.END_TURN)){
			
		}
	}
	
	
	/*
	controller methods and what they do :
		show window�
		 �-> shows the game window
		setNumPlayers(int i)�
		 �-> gets the number of players for the game and sets everything up for them
		addPlayedCard( int player, Card c)�
		 �-> takes a player and a card and adds that card to that players display
		setCurrPlayer(int player)
		 �-> changes that player to be current player
		startRound() 
		 -> clears all played cards, resets the scores resets the played card image�
		setScore (int player int score)
		 -> sets that players score to the new one;�
		setName (int player, string name)
		 �-> sets that players name
		addToken(int player, int token)
		 ->set the token for the player to be filled� 
	  
	 */
	public String processInput(String msg){
		String output = "result";
		
		if(msg.equals(Config.PROMPT_JOIN)){
			return output = Config.JOIN + window.getNameFromPlayer();	
		}
		
		else if (msg.contains(Config.START_TURN)){
			if (msg.equals(Config.PICK_COLOUR)){
				output = Config.COLOUR_PICKED + " " + Config.colours.get(window.setTournament());
			}
		}
		
		else if (msg.equals(Config.NEED_PLAYERS)){
			
			
		}
		
		
		
		else if (msg.equals(Config.PLAY)){
			while(playedCards == null){}
			output = Config.PLAY + " " + playedCards;	
			playedCards = null;
		}
		
		return output; 
	}

	public void stop() {
		try{
			if(thread != null){
				thread = null;
				if(console != null){console.close();}
				if(inStream != null){inStream.close();}
				if(outStream != null){outStream.close();}
				
				if(socket != null){socket.close();}
				
				this.socket = null;
				this.console = null;
				this.inStream = null;
				this.outStream = null; 
			}
		}catch (IOException e){
			log.error(e);
		}
		client.close();
	}
}
