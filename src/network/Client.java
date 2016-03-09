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
import game.ActionCard;
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
	public boolean currentPlayer = false; 
	private String[] options = new String[] {Config.BLUE, Config.RED, Config.YELLOW, Config.GREEN, Config.PURPLE};
	private boolean purpleChosen = false;

	public Client(){
		window = new MainWindowController();
		window.registerObserver(this);
		String ipAndPort = window.getIPPortFromPlayer();
		String seperate[] = ipAndPort.split(" ");
		this.connectToServer(seperate[0], Integer.parseInt(seperate[1]));
		this.currentPlayer = false; 
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
			
			outStream.write(Config.CLIENT_START + "\n");
			outStream.flush();
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
		System.out.println("Message received: " + msg);
		log.info("Message Received: " + msg);
		
	   	if (msg.equalsIgnoreCase("quit!")) {  
				log.info(ID + " has left the game");
				stop();
				
	   	}else if(msg.contains("input")){
	   		// do nothing and wait for more players to arrive 
		} else {
			testing = msg;
			send = processInput(msg);

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
		String send = Config.FROMUPDATE;
		if(message.contains(Config.PLAYEDCARD)){
			playedCards = window.lastCard.getCardType() + " " +  window.lastCard.getValue(); 
			send = this.playACard();
		}
		else if (message.contains(Config.WITHDRAW)){
			
			send = " " + Config.WITHDRAW;
		}
		else{
			send = " " + Config.END_TURN;
		}
		
		//process subject's notify to send to server 
		try {
			this.handle(send);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	/* Handles what the server has sent from the Game Engine and processes
	 * what buttons/popups/commands the client and GUI must send back */
	public String processInput(String msg){
		String output = "result";
		
		if(msg.contains(Config.FROMUPDATE)){
			output = msg.substring(Config.FROMUPDATE.length());
		}
		
		else if(msg.contains(Config.CLIENT_START)){
			output = Config.CLIENT_START;
		}
		
		/* Prompts the first player for the number of players in the game 
		 * Input: firstplayer
		 * Output: start #
		 * */
		else if(msg.contains(Config.FIRSTPLAYER)){
			output = Config.START + " " + this.window.getNumberOfPlayersFromPlayer();
		}
		
		else if(msg.contains(Config.NOT_ENOUGH)){
			/*
			 * Kelly can you add another popup/alter the number of players popup
			 * just added a check to make sure that you can only have between 2 and 5 players
			 * 
			 */
		}

		/* Once the player is connected, prompts that player for their name 
		 * Input:  prompt join 
		 * Output: join <name>
		 * */
		else if(msg.contains(Config.PROMPT_JOIN)){
			output = processPromptJoin(msg);
		}
		
		/* If there is not a sufficient amount of players yet, a waiting for more players window appears */
		else if(msg.contains(Config.NEED_PLAYERS)){
			this.window.showWaiting();

		}
		
		/* Receives each player and their hand
		 * Input:  name <player1> card [player1's card] name <player2> cards [player2's card] ... 
		 * Output: begin tournament 
		 * */
		else if (msg.contains(Config.HAND)){
				this.window.hideWaitng();
			output = processPlayerName(msg);
		}
		
		/* It is the start currentPlayer's turn 
		 * Input: 
		 * 	First tournament purple <player that picked purple> turn <1st player> <1st player's card> picked
		 * 	Player's turn: turn <name> <card picked> picked 
		 * Output: 
		 * 	Start of new tournament: colour <colour picked>
		 * */
		else if (msg.contains(Config.TURN)){
			output = processPlayerTurn(msg);
		}
		
		/* When the player has chosen which card(s) they wish to play
		 * Input: play <colour of tournament>
		 * Output: play <card type> <card value> 
		 * */
		else if (msg.contains(Config.PLAY) ){
			output = processPlay(msg);
		}
		
		/*
		 * set tournament colour
		 */
		else if (msg.contains(Config.COLOUR)) {
			output = processColour(msg);
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
			output = processWaiting(msg);
		}
		
		/* When the currentPlayer has finished playing their turn and does not withdraw
		 * Input: <currentPlayer's name> points <# of points> continue/withdraw <next player>
		 * or :IF tournament is won, add: tournament winner <winner name>
		 * OR IF tournament is won and tournamentColour is purple, add: purple win <winner name> 
		 * IF game is won, add: game winner <winner name>
		 * Output:
		 * 	Tournament Winner: begin tournament 
		 * 	Game Winner: Nothing (game winner popup) 
		 * 	Next Player's turn: currentPlayer has switched to the next player 
		 * */
		else if(msg.contains(Config.CONTINUE)||msg.contains(Config.WITHDRAW)){

			if(msg.length() == 9){
				output = Config.WITHDRAW;
			}else {
				output = processContinueWithdraw(msg);	
			}
		}
		else if(msg.contains(Config.END_TURN)){
			output = Config.END_TURN;
		}
		else if (msg.contains(Config.WITHDRAW)) {
			String[] withdrawString = msg.split(" ");
			String name = withdrawString[0];
			window.playerWithdraws(name); 
		}
		
		/*
		 * Game winner announcement 
		 */
		if(msg.contains(Config.GAME_WINNER)){
			String[] input = msg.split(" ");
			String winner = input[input.length - 1];
			int player = window.getPlayerByName(winner);
			window.addToken(player, window.getTournamentColour());
			window.GameOverPopup(winner);
		}
		return output; 
	}
	
	public String processPromptJoin(String msg){
		String name = window.getNameFromPlayer();
		window.playerName = name;
		return Config.JOIN + " " + name;	
	}
	
	public String processPlayerName(String msg){
		msg=msg.substring(10);
		String name[] = msg.split("name");
		String card[];
		String value[];
		window.setNumPlayers(name.length);
		for(int i = 0; i < name.length; i++){
			card = name[i].split(" ");
			//if this player is the user
			if(card[1].equalsIgnoreCase(window.playerName)){
				for(int k = 3; k < card.length; k++){
					hand.add(card[k]);					
					value = card[k].split("_");
					window.addCard(getCardFromTypeValue(value[0],value[1]));
				}
				window.resetCards();
				window.setPlayerNum(i);
			}
			//set name on gui
			window.setName(i, card[1]);
			
		}
		window.showWindow();
		this.window.window.endedTurn();
		return Config.START_TOURNAMENT;
		
	}
	
	public String processPlayerTurn(String msg){
		String output = "result";
		String input[] = msg.split(" ");
		
		this.window.showWindow();
		//this.window.window.startTurn();

		// if it is the first tournament 
		if(msg.contains(Config.PICKED_PURPLE)){
			//if (input.length > 3) {
				if(input[3].equalsIgnoreCase(window.playerName)){
					this.window.window.startTurn();
					String value[] = input[4].split("_");
					window.addCard(this.getCardFromTypeValue(value[0], value[1]));
					output = Config.COLOUR_PICKED + " " + window.setTournament();
					this.currentPlayer = true; 
				}
				for (int i = 0; i < window.getTotalPlayers();i++){
					if(window.playerNames.get(i).equalsIgnoreCase(input[3])){
						window.setCurrPlayer(i);
					}
				}
				
		}else{
			window.startRound();
			if(input[1].equalsIgnoreCase(window.playerName)){
				window.window.startTurn();
				String value[] = input[2].split("_");
				window.addCard(this.getCardFromTypeValue(value[0], value[1]));
				output = Config.COLOUR_PICKED + " " + window.setTournament();	
			}
			
			for (int i = 0; i < window.getTotalPlayers(); i++){
				if(window.playerNames.get(i).equalsIgnoreCase(input[1])){
					window.setCurrPlayer(i);
				}
			}
		}
		
		//if(this.currentPlayer = false){this.window.window.endedTurn();}
		//}
		return output;
	}
	
	public String processColour(String msg) {
		String output = "result";
		String input[] = msg.split(" ");
		
		
		for(int i = 0; i < 5; i++){
			if (input[1].equalsIgnoreCase(options[i])){
					window.setTournamentColour(i);
			}
		}
		return output;
	}
	
	public String processPlay(String msg){
		String output = "result";
		String input[] = msg.split(" ");
		
		/*
		for(int i = 0; i < 5; i++){
			if (input[1].equalsIgnoreCase(options[i])){
					window.setTournamentColour(i);
			}
		}*/
		
		if(input.length != 2){
			output = msg;
		}
		
		return output;
	}

	private String playACard() {
		String output;
		while(this.playedCards == null){}
		// if the player choose to withdraw
		if(playedCards.equalsIgnoreCase(Config.WITHDRAW)){
			output = Config.WITHDRAW;
		}
		
		// if the player has finished their turn 
		else if(playedCards.equalsIgnoreCase(Config.END_TURN)){
			output = Config.END_TURN;
		}
		else if(window.lastCard.getCardType().equalsIgnoreCase(Config.ACTION)){
			output = Config.PLAY + this.processActionCard();
		}
		else{
			output = Config.PLAY + " " + window.lastCard.getType() + " " + window.lastCard.getValue();
			window.removeCard(window.lastCard);
		}
		this.playedCards = null;
		
		return output;
	}
	
	public String processWaiting(String msg){
		String output = "result";
		// if the client cannot play that card 
		if(msg.contains(Config.UNPLAYABLE)){
			window.addCard(window.lastCard);
			window.cantPlayCardPopup();
		}
		else{
			String input[] = msg.split(" ");
			String c[] = input[1].split("_");
			Card card = this.getCardFromTypeValue(c[0], c[1]);
			window.addPlayedCard(window.getCurrPlayer(), card);
			if(window.getCurrPlayer() == window.getPlayerNum()){
				window.removeCard(card);
			}
		}
		return output;
	}
	
	public String processActionCard(){
		String output = "";
		String cardType = window.lastCard.getType();
		output = " " + cardType + " ";
		
		//note Drop weapon, disgrace, counter charge, charge and outmaneuver don't require anything other than the type
		if(cardType.equalsIgnoreCase(Config.KNOCKDOWN)){
			output += window.pickAName("take a card from.");
		}
		else if(cardType.equalsIgnoreCase(Config.RIPOSTE)){
			output += window.pickAName("take the last card on their display and add it to yours.");
		}
		else if(cardType.equalsIgnoreCase(Config.BREAKLANCE)){
			output += window.pickAName("remove all purple cards from their display.");
		}
		
		else if(cardType.equalsIgnoreCase(Config.CHANGEWEAPON)||cardType.equalsIgnoreCase(Config.UNHORSE)){
			output += window.changeColour();
		}
		return output;
	}
	
	// for input from server on playing the card if an action card is played sent the whole message to this in waiting 
	public void processActionCardAction(String msg){
		String input[]=msg.split(" ");
		String cardType = input[1];
		//output = " " + cardType + " ";
		
		//note Drop weapon, disgrace, counter charge, charge and outmaneuver don't require anything other than the type
		//output = waiting <card played> <player chosen> (Just remove the first card from that player's hand)
		if(cardType.equalsIgnoreCase(Config.KNOCKDOWN)){
			//output += window.pickAName("take a card from.");
		}
		else if(cardType.equalsIgnoreCase(Config.RIPOSTE)){
			//output += window.pickAName("take the last card on their display and add it to yours.");
		}
		else if(cardType.equalsIgnoreCase(Config.BREAKLANCE)){
			//output += window.pickAName("remove all purple cards from their display.");
		}
		//msg =waiting unhorse colour 
		else if(cardType.equalsIgnoreCase(Config.CHANGEWEAPON)||cardType.equalsIgnoreCase(Config.UNHORSE)||cardType.equalsIgnoreCase(Config.DROPWEAPON)){
			window.setTournamentColour(Config.colours.indexOf(input[2]));
		}
	}
	
	public String processContinueWithdraw(String msg){
		String output = "result";
		String input[] = msg.split(" ");
		String currentPlayerName = input[0];
		String winningPlayerName = input[input.length - 1];
		String score = input[2];
		String nextPlayerName = input[4];
		int winningPlayer = window.getPlayerByName(winningPlayerName);
		int currentPlayer = window.getPlayerByName(currentPlayerName);
		window.setScore(currentPlayer, Integer.parseInt(score));
		
		if(msg.contains(Config.PURPLE_WIN)){
			window.setCurrPlayer(winningPlayer);
			if(window.playerName.equalsIgnoreCase(input[input.length - 1])) {
				String chosenColour = window.playerPickToken();
				output = Config.PURPLE_WIN + " " + chosenColour;
				for(int i = 0; i < 5; i++){
					if (chosenColour.equalsIgnoreCase(options[i])){
							window.setTournamentColour(i);
							if (chosenColour.equals(Config.PURPLE)) {
								purpleChosen = true;
							}
					}
				}
			}
		}else{
			
			if(msg.contains(Config.TOURNAMENT_WINNER)){
				window.startRound();
				window.setCurrPlayer(winningPlayer);
				String chosenColour = input[5];
				for(int i = 0; i < 5; i++){
					if (chosenColour.equalsIgnoreCase(options[i])){
							window.setTournamentColour(i);
							if (chosenColour.equals(Config.PURPLE)) {
								purpleChosen = true;
							}
					}
				}
				if (!(window.getTournamentColour() == 4) || purpleChosen) {
					window.addToken(window.getCurrPlayer(), window.getTournamentColour());
					purpleChosen = false;
				}
				for(int i = 0; i < window.getPlayerNum(); i++){
					window.setScore(i, 0);
				}
				this.window.setScore(winningPlayer, 0);
				
				output = Config.START_TOURNAMENT;
			}
			
			if(!msg.contains(Config.TOURNAMENT_WINNER)){
				window.setScore(window.getCurrPlayer(), Integer.parseInt(score));
				
				if(window.getPlayerNum() == currentPlayer){
					window.window.endedTurn();
				}
				
				for(int i = 0; i < window.playerNames.size(); i++){
					if(window.playerNames.get(i).equalsIgnoreCase(nextPlayerName)){						
						window.setCurrPlayer(i);
						
						if(window.getPlayerNum() == window.getCurrPlayer()){
							window.window.startTurn();
							String card[] = input[5].split("_");
							String type = card[0];
							String value = card[1];
							window.addCard(this.getCardFromTypeValue(type, value));
						}
					}
				}
			}
		}
		return output; 
	}

	/* Convert brit's string into resources */
	public Card getCardFromTypeValue(String type, String value){

		String output = "";
		
		/* Coloured Cards */
		if(type.equals(Config.PURPLE)){
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
		else if(type.equals(Config.RED)){
			if(value.equals("3")){
				output = Config.IMG_RED_3;
			}
			else if(value.equals("4")){
				output = Config.IMG_RED_4;
			}
			else if(value.equals("5")){
				output = Config.IMG_RED_5;
			}
		}
		
		else if (type.equals(Config.BLUE)){
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
		
		else if (type.equals(Config.YELLOW)){
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
		
		else if (type.equals(Config.GREEN)){
			output = Config.IMG_GREEN_1;
		}
		/*creates a coloured card*/
		if(!output.equals("")){
			return new ColourCard(type,Integer.parseInt(value),output);
		}
		
		/* Supporter */
		if(type.equals(Config.MAIDEN)){
			output = Config.IMG_MAIDEN_6;
		}
		else if(type.equals(Config.SQUIRE)){
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
		
		/* Action */
		if(type.equals(Config.DODGE)){
			output = Config.IMG_DODGE;
		}
		else if (type.equals(Config.DISGRACE)){
			output = Config.IMG_DISGRACE;
		}
		else if (type.equals(Config.RETREAT)){
			output = Config.IMG_RETREAT;
		}
		else if(type.equals(Config.RIPOSTE)){
			output = Config.IMG_RIPOSTE;
		}
		else if(type.equals(Config.OUTMANEUVER)){
			output = Config.IMG_OUTMANEUVER;
		}
		else if(type.equals(Config.COUNTERCHARGE)){
			output = Config.IMG_COUNTER_CHARGE;
		}
		else if(type.equals(Config.CHARGE)){
			output = Config.IMG_CHARGE;
		}
		else if(type.equals(Config.BREAKLANCE)){
			output = Config.IMG_BREAK_LANCE;
		}
		else if(type.equals(Config.ADAPT)){
			output = Config.IMG_ADAPT;
		}
		else if (type.equals(Config.OUTWIT)){
			output = Config.IMG_OUTWIT;
		}
 		else if (type.equals(Config.DROPWEAPON)){
			output = Config.IMG_DROP_WEAPON;
		}
		else if(type.equals(Config.CHANGEWEAPON)){
			output = Config.IMG_CHANGE_WEAPON;
		}
		else if(type.equals(Config.UNHORSE)){
			output = Config.IMG_UNHORSE;
		}
		else if(type.equals(Config.KNOCKDOWN)){
			output = Config.IMG_KNOCK_DOWN;
		}
		else if(type.equals(Config.SHIELD)){
			output = Config.IMG_SHIELD;
		}
		else if(type.equals(Config.STUNNED)){
			output = Config.IMG_STUNNED;
		}
		else if(type.equals(Config.IVANHOE)){
			output = Config.IMG_IVANHOE;
		}
		
		if(!output.equals("")){
			return new ActionCard(type, output);
		}
		
		return new Card();
	}
}
