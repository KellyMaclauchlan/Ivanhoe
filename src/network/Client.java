package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import config.Config;
import ui.MainWindowController;
import config.Observer;

public class Client implements Runnable, Observer {
	public int ID = 0;
	public Socket socket = null;
	public Thread thread = null;
	public ClientThread client = null;
	public MainWindowController window = null;
	public BufferedReader console = null;
	public BufferedReader inStream = null;
	public BufferedWriter outStream = null;
	public String testing = null;
	
	public String playedCards = null;
	public Logger log = Logger.getLogger("Client");
	public ArrayList<String> hand = new ArrayList<String>();

	public int getID(){
		return this.ID;
	}
	
	public Client(){}
	
	public boolean connectToServer(String serverIP, int serverPort) {
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
		while (thread != null) {  
			try {  
				if (outStream != null) {
					outStream.flush();
					outStream.write(console.readLine() + "\n");
				} else {
					log.info(ID + ": Stream Closed");
				}
         }
         catch(IOException e) {  
         	log.error(e);
         	stop();
         }}
	}
	
	public void handle(String msg) {
		String send = "waiting";
		
		log.info("Message Received: " + msg);
		
	   	if (msg.equalsIgnoreCase("quit!")) {  
				log.info(ID + " has left the game");
				stop();
		} else {
			testing = msg;
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
		
		else if (message.equals(Config.WITHDRAW)){
			processInput(Config.WITHDRAW);
		}
		
		else if(message.equals(Config.END_TURN)){
			processInput(Config.END_TURN);
		}
	}

	public String processInput(String msg){
		String output = "result";
		
		if(msg.contains(Config.PROMPT_JOIN)){
			return output = Config.JOIN + window.getNameFromPlayer();	
		}
		
		else if (msg.contains(Config.PLAYER_NAME)){
			String name[] = msg.split("name");
			String card[];
			
			for(int i = 0; i < name.length; i++){
				card = name[i].split(" ");
				
				for(int k = 0; k < card.length; k++){
					hand.add(card[i]);
					// send hand to GUI
				}
			}
			output = Config.START_TURN;
		}
		
		else if (msg.contains(Config.PICKED_PURPLE)){
			output = Config.COLOUR_PICKED + " " + Config.colours.get(window.setTournament());
		}
		
		else if (msg.contains(Config.PLAY) || msg.contains(Config.CONTINUE)){
			// want to send: play <type> <value>
			output = Config.PLAY + " " + playedCards;	
		}
		
		else if (msg.contains(Config.WITHDRAW)){
			output = Config.END_TURN;
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
