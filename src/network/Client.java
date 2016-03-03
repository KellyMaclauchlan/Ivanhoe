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
import game.Card;
import game.ColourCard;

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
			String value[];
			
			for(int i = 0; i < name.length; i++){
				card = name[i].split(" ");
				
				for(int k = 0; k < card.length; k++){
					hand.add(card[i]);
					
					value = card[i].split("_");
					String pic = getCardImage(value[0], value[1]);
					
					Card newCard = new ColourCard(value[0], Integer.parseInt(value[1]), pic);
					window.addCard(newCard);
				}
			}
			output = Config.START_TURN;
		}
		
		else if (msg.contains(Config.PICKED_PURPLE)){
			output = Config.COLOUR_PICKED + " " + Config.colours.get(window.setTournament());
		}
		
		else if (msg.contains(Config.PLAY) || msg.contains(Config.CONTINUE)){
			output = Config.PLAY + " " + window.lastCard.getType() + " " + window.lastCard.getValue();	
		}
		
		else if (msg.contains(Config.WITHDRAW)){
			output = Config.END_TURN;
		}
		return output; 
	}
	
	/* Convert brit's string into resources */
	public String getCardImage(String type, String value){
		String output = "";
		
		/* Coloured Cards */
		if(type.equals("purple")){
			if(value.equals("3")){
				output = Config.IMG_PURPLE_3; 
			}
			else if (value.equals("4")){
				output = Config.IMG_PURPLE_4;	
			}
			else if(value.equals("5")){
				output = Config.IMG_PURPLE_5;
			}
			else if(value.equals("7")){
				output = Config.IMG_PURPLE_7;
			}
		}
		else if(type.equals("red")){
			if(value.equals("3")){
				output = Config.IMG_RED_3;
			}
			else if(type.equals("4")){
				output = Config.IMG_RED_4;
			}
			else if(type.equals("5")){
				output = Config.IMG_RED_5;
			}
			
		}
		
		else if (type.equals("blue")){
			if(value.equals("2")){
				output = Config.IMG_BLUE_2;
			}
			else if(value.equals("3")){
				output = Config.IMG_BLUE_3;
			}
			else if(value.equals("4")){
				output = Config.IMG_BLUE_4;
			}
			else if(value.equals("5")){
				output = Config.IMG_BLUE_5;
			}
		}
		
		else if (type.equals("yellow")){
			if(value.equals("2")){
				output = Config.IMG_YELLOW_2;
			}
			else if(value.equals("3")){
				output = Config.IMG_YELLOW_3;
			}
			else if(value.equals("4")){
				output = Config.IMG_YELLOW_4;
			}
		}
		
		else if (type.equals("green")){
			output = Config.IMG_GREEN_1;
		}
		
		/* SUPPORTER */
		if(type.equals("maiden")){
			output = Config.IMG_MAIDEN_6;
		}
		else if(type.equals("squire")){
			if(value.equals("2")){
				output = Config.IMG_SQUIRE_2;
			}
			else if(value.equals("3")){
				output = Config.IMG_SQUIRE_3;
			}
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
