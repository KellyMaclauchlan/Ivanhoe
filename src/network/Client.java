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
	public BufferedReader inStream = null;
	public BufferedWriter outStream = null;
	public String testing = null;
	public ArrayList<String> hand = new ArrayList<String>();
	public String playedCards = null;
	
	public Logger log = Logger.getLogger("Client");
<<<<<<< HEAD
	public ArrayList<String> hand = new ArrayList<String>();
=======
	
>>>>>>> 69dc90c47caf92180ade4983c2f9ff2524f54202
	public Client(){
		window = new MainWindowController();
		String ipAndPort = window.getIPPortFromPlayer();
		String seperate[] = ipAndPort.split(" ");
		this.connectToServer(seperate[0], Integer.parseInt(seperate[1]));
	}

	public int getID(){
		return this.ID;
	}
	
	/* Used for Testing to check when the Client connects to the server */
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
			inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			log.info("Initializing buffers");
			
			if(thread == null){
				client = new ClientThread(this, socket);
				thread = new Thread(this);
				thread.start();
				log.info("New ClientThread has started");
			}

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
					//outStream.write(console.readLine() + "\n");
				} else {
					log.info(ID + ": Stream Closed");
				}
         }
         catch(IOException e) {  
         	log.error(e);
         	stop();
         }}
	}
	
	public void stop() {
		try{
			if(thread != null){
				thread = null;
				if(inStream != null){inStream.close();}
				if(outStream != null){outStream.close();}
				
				if(socket != null){socket.close();}
				
				this.socket = null;
				this.inStream = null;
				this.outStream = null; 
			}
		}catch (IOException e){
			log.error(e);
		}
		client.close();
	}
	
	/* Handles all the input and output to and from the server */
	public void handle(String msg) throws IOException {
		String send = "waiting";
		
		log.info("Message Received: " + msg);
		
	   	if (msg.equalsIgnoreCase("quit!")) {  
				log.info(ID + " has left the game");
				stop();
		} else {
			testing = msg;
			send = processInput(msg);
			//System.out.println(msg);
			log.info("Information sent to server: " + send);
			
			outStream.write(send);
			outStream.write("\n");
			outStream.flush();
		}
	}

	/* Used for testing the strings sent from the server to the client */
	public String testMessages() {
		return testing;
	}

	@Override
	public void update(String message) {
		if(message.equals(Config.PLAYEDCARD)){
			playedCards = window.lastCard.getCardType() + " " +  window.lastCard.getValue(); 
		}
		
		else if (message.equals(Config.WITHDRAW)){
			//processInput(Config.WITHDRAW);
			playedCards = Config.WITHDRAW;
		}
		
		else if(message.equals(Config.END_TURN)){
			//processInput(Config.END_TURN);
			playedCards = Config.END_TURN;
		}
	}

	/* Handles what the server has sent from the Game Engine and processes
	 * what buttons/popups/commands the client and GUI must send back */
	public String processInput(String msg){
		String output = "result";
		
		/* Prompts the first player for the number of players in the game 
		 * Input: firstplayer
		 * Output: start #
		 * */
		if(msg.contains(Config.FIRSTPLAYER)){
			return Config.START + " " + this.window.getNumberOfPlayersFromPlayer();
		}

		/* Once the player is connected, prompts that player for their name 
		 * Input:  prompt join 
		 * Output: join <name>
		 * */
		else if(msg.contains(Config.PROMPT_JOIN)){
			String name = window.getNameFromPlayer();
			window.playerName = name;
			return output = Config.JOIN + " " + name;	
		}
		
		/* If there is not a sufficient amount of players yet, a waiting for more players window appears */
		else if(msg.contains(Config.NEED_PLAYERS)){
			this.window.showWaiting();
		}
		
		/* Receives each player and their hand
		 * Input:  name <player1> card [player1's card] name <player2> cards [player2's card] ... 
		 * Output: begin tournament 
		 * */
		else if (msg.contains(Config.PLAYER_NAME)){
			String name[] = msg.split("name");
			String card[];
			String value[];
			window.setNumPlayers(name.length);
			
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
		
		/* It is the start currentPlayer's turn 
		 * Input: 
		 * 	First tournament purple <player that picked purple> turn <1st player> <1st player's card> picked
		 * 	Player's turn: turn <name> <card picked> picked 
		 * Output: 
		 * 	Start of new tournament: colour <colour picked>
		 * */
		else if (msg.contains(Config.TURN)){
			String input[] = msg.split(" ");
			
			// if it is the first tournament 
			if(msg.contains(Config.PICKED_PURPLE)){
				
				if(input[3].equalsIgnoreCase(window.playerName)){
					String value[] = input[4].split("_");
					window.addCard(this.getCardFromTypeValue(value[0], value[1]));
					output = Config.COLOUR_PICKED + " " + window.setTournament();
				}
				for (int i = 0; i < window.getTotalPlayers();i++){
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
				
				for (int i = 0; i < window.getTotalPlayers(); i++){
					if(window.playerNames.get(i).equalsIgnoreCase(input[1])){
						window.setCurrPlayer(i);
					}
				}
			}
		}
		
		/* When the player has chosen which card(s) they wish to play
		 * Input: play <colour of tournament>
		 * Output: play <card type> <card value> 
		 * */
		else if (msg.contains(Config.PLAY) ){
			String input[] = msg.split(" ");
			String[] options = new String[] {"Blue", "Red", "Yellow", "Green","Purple"};
			
			for(int i = 0; i < 5; i++){
				if (input[1].equalsIgnoreCase(options[i])){
					if(window.getTournamentColour()!=i)
						window.setTournamnetColour(i);
				}
			}
			
			if(window.getPlayerNum() == window.getCurrPlayer()){
				while(this.playedCards == null){}
				
				// if the player choose to withdraw
				if(playedCards.equalsIgnoreCase(Config.WITHDRAW)){
					output = Config.WITHDRAW;
				}
				
				// if the player has finished their turn 
				else if(playedCards.equalsIgnoreCase(Config.END_TURN)){
					output = Config.END_TURN;
				}
				else{
					output = Config.PLAY + " " + window.lastCard.getType() + " " + window.lastCard.getValue();	
				}
				this.playedCards = null;
			}
		}
		
		/* Server is waiting for the next card to be played 
		 * Input:
		 * 	Card picked is playable: waiting <card played> 
		 *  Card picked is unplayable: waiting <card played> unplayable 
		 * Output:
		 * 	Card playable: play <card type> <card value> 
		 *  Card unplayable: informs players of unplayable card 
		 * */
		else if(msg.contains(Config.WAITING)){
			
			// if the client cannot play that card 
			if(msg.contains(Config.UNPLAYABLE)){
				window.cantPlayCardPopup();
			}
			else{
				String input[] = msg.split(" ");
				Card card = this.getCardFromTypeValue(input[1], input[2]);
				window.addPlayedCard(window.getCurrPlayer(), card);
				if(window.getCurrPlayer() == window.getPlayerNum()){
					window.removeCard(card);
				}
			}
			
			if(window.getCurrPlayer() == window.getPlayerNum()){
				while(this.playedCards == null){}
				
				// if the player choose to withdraw
				if(playedCards.equalsIgnoreCase(Config.WITHDRAW)){
					output = Config.WITHDRAW;
				}
				
				// if the player has finished their turn 
				else if(playedCards.equalsIgnoreCase(Config.END_TURN)){
					output = Config.END_TURN;
				}
				else{
					output = Config.PLAY + " " + window.lastCard.getType() + " " + window.lastCard.getValue();	
				}
				this.playedCards = null;			
			}
		}
		
		/* When the currentPlayer has finished playing their turn and does not withdraw
		 * Input: <currentPlayer's name> points <# of points> continue <next player>
		 * or :IF tournament is won, add: tournament winner <winner name>
		 * OR IF tournament is won and tournamentColour is purple, add: purple win <winner name> 
		 * IF game is won, add: game winner <winner name>
		 * Output:
		 * 	Tournament Winner: begin tournament 
		 * 	Game Winner: Nothing (game winner popup) 
		 * 	Next Player's turn: currentPlayer has switched to the next player 
		 * */
		else if(msg.contains(Config.CONTINUE)||msg.contains(Config.WITHDRAW)){
			
			String input[] = msg.split(" ");
			int old = window.getCurrPlayer();
			window.setScore(window.getCurrPlayer(), Integer.parseInt(input[2]));
			if(msg.contains(Config.PURPLE_WIN)){
				if(window.getPlayerNum()==window.getCurrPlayer())
					output = Config.PURPLE_WIN+" "+ window.playerPickToken();
			}else{
				if(msg.contains(Config.TOURNAMENT_WINNER)){
					window.addToken(window.getCurrPlayer(), window.getTournamentColour());
					output= Config.START_TOURNAMENT;
				}
				if(msg.contains(Config.GAME_WINNER)){
					window.GameOverPopup(input[0]);
				}
				if(!msg.contains(Config.TOURNAMENT_WINNER)){
					window.setScore(window.getCurrPlayer(), Integer.parseInt(input[1]));
					
					if(window.getPlayerNum() == old){
						window.window.endedTurn();
					}
					
					for(int i = 0; i < window.playerNames.size(); i++){
						if(window.playerNames.get(i).equalsIgnoreCase(input[4])){						
							window.setCurrPlayer(i);
							
							if(window.getPlayerNum() == window.getCurrPlayer()){
								String value[] = input[5].split("_");
								window.addCard(this.getCardFromTypeValue(value[0], value[1]));
								while(this.playedCards == null){}
								
								// if the player choose to withdraw
								if(playedCards.equalsIgnoreCase(Config.WITHDRAW)){
									output = Config.WITHDRAW;
								}
								
								// if the player has finished their turn 
								else if(playedCards.equalsIgnoreCase(Config.END_TURN)){
									output = Config.END_TURN;
								}
								else{
									output = Config.PLAY + " " + window.lastCard.getType() + " " + window.lastCard.getValue();	
								}
								this.playedCards = null;
							}
						}
					}
				}
				
			}
			
		}
		
		/* When the currentPlayer has finished playing their turn and withdraws
		 * Input: <currentPlayer's name> points <# of points> withdraw <next player>
		 * Output:
		 * 	Tournament Winner: begin tournament 
		 * 	Game Winner: Nothing (game winner popup) 
		 * 	Next Player's turn: currentPlayer has switched to the next player 
		 * */
		//else if (msg.contains(Config.WITHDRAW)){			
			//String input[] = msg.split(" ");
			
			
			//output = Config.END_TURN;
		//}
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
		
		/* Supporter */
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
}
