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
import game.SupportCard;

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
	public Client(){
		window = new MainWindowController();
		String ipAndPort=window.getIPPortFromPlayer();
		String seperate[]=ipAndPort.split(" ");
		this.connectToServer(seperate[0], Integer.parseInt(seperate[1]));
	}

	public int getID(){
		return this.ID;
	}
	
	/* Used for Testing to check when the Client conencts to the server */
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
	
	/* Handles all the input and output to and from the server */
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
		if(msg.contains(Config.FIRSTPLAYER)){
			return Config.START+" "+this.window.getNumberOfPlayersFromPlayer();
		}
		
		else if(msg.contains(Config.PROMPT_JOIN)){
			String name =window.getNameFromPlayer();
			window.playerName=name;
			return output = Config.JOIN + name;	
		}
		else if(msg.contains(Config.NEED_PLAYERS)){
			this.window.showWaiting();
		}		
		else if (msg.contains(Config.PLAYER_NAME)){
			String name[] = msg.split("name");
			String card[];
			String value[];
			
			for(int i = 0; i < name.length; i++){
				card = name[i].split(" ");
				//if this player is the user
				if(card[0].equalsIgnoreCase(window.playerName)){
					for(int k = 2; k < card.length; k++){
						hand.add(card[i]);					
						value = card[i].split("_");
						window.addCard(getCardFromTypeValue(value[0],value[1]));
					}
					window.setPlayerNum(i);
				}
				//set name on gui
				window.setName(i, card[0]);
				//set name in array
				window.playerNames.add(card[0]);
				
			}
			output = Config.START_TOURNAMENT;
		}
		
		else if (msg.contains(Config.TURN)){
			String input[]=msg.split(" ");
			if(msg.contains(Config.PICKED_PURPLE)){
				if(input[3].equalsIgnoreCase(window.playerName)){
					String value[]=input[4].split("_");
					window.addCard(this.getCardFromTypeValue(value[0], value[1]));
					output = Config.COLOUR_PICKED + " " + window.setTournament();
				}
				for (int i=0;i< window.getTotalPlayers();i++){
					if(window.playerNames.get(i).equalsIgnoreCase(input[3])){
						window.setCurrPlayer(i);
					}
				}
			}else{
				if(input[1].equalsIgnoreCase(window.playerName)){
					String value[]=input[2].split("_");
					window.addCard(this.getCardFromTypeValue(value[0], value[1]));
					output = Config.COLOUR_PICKED + " " + window.setTournament();	
				}
				for (int i=0;i< window.getTotalPlayers();i++){
					if(window.playerNames.get(i).equalsIgnoreCase(input[1])){
						window.setCurrPlayer(i);
					}
				}
			}

		}
		
		else if (msg.contains(Config.PLAY) ){
			String input[]=msg.split(" ");
			String[] options = new String[] {"Blue", "Red", "Yellow", "Green","Purple"};
			for(int i=0;i<5;i++){
				if (input[1].equalsIgnoreCase(options[i])){
					if(window.getTournamentColour()!=i)
						window.setTournamnetColour(i);
				}
			}
			if(window.getPlayerNum()==window.getCurrPlayer()){
				while(this.playedCards==null){}
				output = Config.PLAY + " " + window.lastCard.getType() + " " + window.lastCard.getValue();	
			}
		}
		else if(msg.contains(Config.WAITING)){
			if(msg.contains(Config.UNPLAYABLE)){
				window.cantPlayCardPopup();
			}else{
				String input[]=msg.split(" ");
				Card card=this.getCardFromTypeValue(input[1], input[2]);
				window.addPlayedCard(window.getCurrPlayer(), card);
				if(window.getCurrPlayer()==window.getPlayerNum()){
					window.removeCard(card);
				}
			}
			
			if(window.getCurrPlayer()==window.getPlayerNum()){
				while(this.playedCards==null){}
				output = Config.PLAY + " " + window.lastCard.getType() + " " + window.lastCard.getValue();				
			}
		}
		else if(msg.contains(Config.CONTINUE)){
			
			String input[]=msg.split(" ");
			int old= window.getCurrPlayer();
			window.setScore(window.getCurrPlayer(), Integer.parseInt(input[1]));
			if(window.getPlayerNum()==old){
				window.window.endTurnClicked();
			}
			
			if(msg.contains(Config.TOURNAMENT_WINNER)){
				for(int i=0;i<window.playerNames.size();i++){
					if(window.playerNames.get(i).equalsIgnoreCase(input[5])){
						if(msg.contains(Config.PURPLE_WIN)){
							String chose =window.playerPickToken();
						}else{
							window.addToken(i, window.getTournamentColour());	
						}
						output= Config.START_TOURNAMENT +" "+input[3];
					}
				}
				
			}
			if(msg.contains(Config.GAME_WINNER)){
				//the game is over
				window.GameOverPopup(input[5]);
			}
			
			if(!msg.contains(Config.TOURNAMENT_WINNER)){
				
				for(int i=0;i<window.playerNames.size();i++){
					if(window.playerNames.get(i).equalsIgnoreCase(input[3])){						
						window.setCurrPlayer(i);	
						if(window.getPlayerNum()==window.getCurrPlayer()){
							String value[]=input[4].split("_");
							window.addCard(this.getCardFromTypeValue(value[0], value[1]));
							output = Config.COLOUR_PICKED +window.setTournament();
						}
					}
					
				}
			}
				
		}
		
		else if (msg.contains(Config.WITHDRAW)){			
			String input[]=msg.split(" ");
			if(msg.contains(Config.TOURNAMENT_WINNER)){
				for(int i=0;i<window.playerNames.size();i++){
					if(window.playerNames.get(i).equalsIgnoreCase(input[5])){
						if(msg.contains(Config.PURPLE_WIN)){
							String chose =window.playerPickToken();
						}else{
							window.addToken(i, window.getTournamentColour());	
						}
						output= Config.START_TOURNAMENT +" "+input[3];
					}
				}
				
			}
			if(msg.contains(Config.GAME_WINNER)){
				//the game is over
				window.GameOverPopup(input[5]);
			}
			
			if(!msg.contains(Config.TOURNAMENT_WINNER)){
				int old= window.getCurrPlayer();
				window.setScore(window.getCurrPlayer(), Integer.parseInt(input[1]));
				if(window.getPlayerNum()==old){
					window.window.endTurnClicked();
				}
				for(int i=0;i<window.playerNames.size();i++){
					if(window.playerNames.get(i).equalsIgnoreCase(input[3])){						
						window.setCurrPlayer(i);	
						if(window.getPlayerNum()==window.getCurrPlayer()){
							String value[]=input[4].split("_");
							window.addCard(this.getCardFromTypeValue(value[0], value[1]));
							output = Config.COLOUR_PICKED +window.setTournament();
						}
					}
					
				}
			}
			//output = Config.END_TURN;
		}
		return output; 
	}
	

	/* Convert brit's string into resources */
	public Card getCardFromTypeValue(String type, String value){

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
		/*creates a coloured card*/
		if(!output.equals("")){
			return new ColourCard(type,Integer.parseInt(value),output);
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

		if(!output.equals("")){
			return new SupportCard(type,Integer.parseInt(value),output);
		} 
		return null;
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
