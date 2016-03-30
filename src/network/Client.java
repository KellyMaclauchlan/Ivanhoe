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
	private int id = 0;
	private Socket socket = null;
	private Thread thread = null;
	private ClientThread client = null;
	private MainWindowController window = null;
	private BufferedReader inStream = null;
	private BufferedWriter outStream = null;
	
	private Logger log = Logger.getLogger("Client");
	private boolean successConnect = false; 
	private String testing = null;
	
	private String output = Config.OUTPUT;
	
	private ArrayList<String> hand = new ArrayList<String>();
	private String playedCards = null;	

	private boolean currentPlayer = false; 
	private String currPlayer = null; // used for logging activity 
	private String[] options = new String[] {Config.BLUE, Config.RED, Config.YELLOW, Config.GREEN, Config.PURPLE};
	private boolean purpleChosen = false;
	private ArrayList<String> actioncards = new ArrayList<String>();

	public Client(){
		window = new MainWindowController();
		window.registerObserver(this);
		String ipAndPort = window.getIPPortFromPlayer();
		String seperate[] = ipAndPort.split(" ");
		this.successConnect = this.connectToServer(seperate[0], Integer.parseInt(seperate[1]));
		this.currentPlayer = false; 
		setActionCardArraylist();
	}
	
	/* Constructor used for Testing */
	public Client(String ip, int port){
		this.successConnect = this.connectToServer(ip, port);
		setActionCardArraylist();
	}
	public void setActionCardArraylist(){
		this.actioncards.add(Config.KNOCKDOWN);
		this.actioncards.add(Config.RIPOSTE);
		this.actioncards.add(Config.BREAKLANCE);
		this.actioncards.add(Config.CHANGEWEAPON);
		this.actioncards.add(Config.UNHORSE);
		this.actioncards.add(Config.DROPWEAPON);
		this.actioncards.add(Config.SHIELD);
		this.actioncards.add(Config.DISGRACE);
		this.actioncards.add(Config.COUNTERCHARGE);
		this.actioncards.add(Config.CHARGE);
		this.actioncards.add(Config.OUTMANEUVER);
		this.actioncards.add(Config.STUNNED);
		this.actioncards.add(Config.OUTWIT);
		this.actioncards.add(Config.DODGE);
		this.actioncards.add(Config.RETREAT);
		this.actioncards.add(Config.IVANHOE);
		this.actioncards.add(Config.ADAPT);
	}

	public int getID(){return this.id;}
	/* Used for testing the strings sent from the server to the client */
	public String testMessages() {return testing;}
	public boolean getSuccessConnect(){return this.successConnect;}

	/* Used for Testing to check when the Client connects to the server */
	public boolean connectToServer(String serverIP, int serverPort) {
		log.info(id + ":Establishing connection. Please wait... ");
		boolean connected = false;
		
		try{
			this.socket = new Socket(serverIP, serverPort);
			this.id = socket.getLocalPort(); 
			
			log.info(id + ": Connected to server: " + socket.getInetAddress());
	    	log.info(id + ": Connected to portid: " + socket.getLocalPort());
	    	
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
					log.info(id + ": Stream Closed");
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
				if(inStream != null){
					inStream.close();}
				if(outStream != null){
					outStream.close();}
				
				if(socket != null){
					socket.close();}
				
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
		String send = Config.OUTPUT;
		System.out.println("Message received: " + msg);
		log.info("Message Received: " + msg);
		
		if(msg.contains("input")){
	   		// do nothing and wait for more players to arrive 
		} else {
			testing = msg;
			send = processInput(msg);

			log.info("Information sent to server: " + send);
			outStream.write(send);
			outStream.write("\n");
			outStream.flush();
			
			if(send.equals(Config.QUIT)){
				stop();
				log.info(id + " has left the game");
			}
		}
		output = Config.OUTPUT;
	}

	public void update(String message) {
		String send = Config.FROMUPDATE;
		if(message.contains(Config.PLAYEDCARD)){
			playedCards = window.getLastCard().getCardType() + " " +  window.getLastCard().getValue(); 
			send = this.playACard();
		}
		else if (message.contains(Config.WITHDRAW)){
			send = " " + Config.WITHDRAW;
		}
		else if (message.contains(Config.QUIT)){
			send = Config.QUIT;
		}
		else{
			send = " " + Config.END_TURN;
		}
		
		//process subject's notify to send to server 
		try {
			this.handle(send);
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	/* Notifies the other players of what is going on in a JTextArea */
	public void logActivity(String msg){
		String displayText = null;
		String input[] = msg.split(" "); 

		if(input[0].equals(Config.WAITING)){
			displayText = currPlayer + " has played a card";
		}
		else{
			displayText = msg;
		}
		window.setTextDisplay(displayText + "\n");
	}

	/* Handles what the server has sent from the Game Engine and processes
	 * what buttons/popups/commands the client and GUI must send back */
	public String processInput(String msg){
		if(msg.equals(Config.QUIT)) {  
			output = Config.QUIT;
		} 
		else if (msg.contains(Config.IS_STUNNED)) {
			processWaiting(msg);
			output = Config.END_TURN;
		}

		/* Processes all actions from the MainWindowController */
		else if(msg.contains(Config.FROMUPDATE)){
			output = msg.substring(Config.FROMUPDATE.length());
		}
		
		/* Check to see if this client is the first player or not */
		else if(msg.contains(Config.CLIENT_START)){
			output = Config.CLIENT_START;
		}
		
		/* Prompts the first player for the number of players in the game 
		 * Input: firstplayer
		 * Output: start #
		 * */
		else if(msg.contains(Config.FIRSTPLAYER)){
			output = Config.START + " " + this.window.getNumberOfPlayersFromPlayer() + " " +
					this.window.getNumberOfAIFromPlayer();
		}

		/* Once the player is connected, prompts that player for their name 
		 * Input:  prompt join 
		 * Output: join <name>
		 * */
		else if(msg.contains(Config.PROMPT_JOIN) || msg.contains(Config.DUPLICATE)){
			output = processPromptJoin(msg);
		}
		
		/* If there is not a sufficient amount of players yet, a waiting for more players window appears */
		else if(msg.contains(Config.NEED_PLAYERS)){
			this.window.showWaiting();
		}
		
		/* Receives each player and their hand
		 * Input:  hand name <player1> card [player1's card] name <player2> cards [player2's card] ... 
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
		
		else if (msg.contains(Config.MAIDEN) && !msg.contains(Config.PLAY) && !msg.contains(Config.HAND) && !msg.contains("_")) {
			String tokenToRemove = null;
			String[] input = msg.split(" ");
			if (msg.equals(Config.MAIDEN)) {			
				tokenToRemove = window.playerPickTokenRemove();
				output = Config.WITHDRAW + " " + tokenToRemove;
			} else {
				tokenToRemove = input[1];
				window.removeToken(window.getCurrPlayer(), tokenToRemove);
				output = Config.WITHDRAW + " " + currPlayer;
				
			}
		}		
		return output; 
	}
	
	public String processPromptJoin(String msg){
		String name = window.getNameFromPlayer();
		window.setVarPlayerName(name);
		return Config.JOIN + " " + name;	
	}
	
	public String processPlayerName(String msg){
		msg = msg.substring(10);
		String name[] = msg.split("name");
		String card[];
		String value[];
		window.setNumPlayers(name.length);
		for(int i = 0; i < name.length; i++){
			card = name[i].split(" ");
			
			//if this player is the user
			if(card[1].equalsIgnoreCase(window.getPlayerName())){
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
		this.window.endTurn();
		
		logActivity("****************************\n" +
						"Tournament has Begun\n" +
					"****************************\n");
		
		return Config.START_TOURNAMENT;
	}
	
	public String processPlayerTurn(String msg){
		String input[] = msg.split(" ");
		
		this.window.showWindow();

		// if it is the first tournament 
		if(msg.contains(Config.PICKED_PURPLE)){
			
				if(input[3].equalsIgnoreCase(window.getPlayerName())){
					window.startTurn();
					String value[] = input[4].split("_");
					window.addCard(this.getCardFromTypeValue(value[0], value[1]));
					output = Config.COLOUR_PICKED + " " + window.setTournament();
					this.currentPlayer = true; 
				}
				for (int i = 0; i < window.getTotalPlayers();i++){
					if((window.getName(i)).equalsIgnoreCase(input[3])){
						window.setCurrPlayer(i);
						currPlayer = window.getName(i);
					}
				}
				logActivity(currPlayer + " has the first turn");
				
		}else{
			window.startRound();
			if(input[1].equalsIgnoreCase(window.getPlayerName())){
				window.startTurn();
				String value[] = input[2].split("_");
				window.addCard(this.getCardFromTypeValue(value[0], value[1]));
				output = Config.COLOUR_PICKED + " " + window.setTournament();	
			}
			
			for (int i = 0; i < window.getTotalPlayers(); i++){
				if(window.getName(i).equalsIgnoreCase(input[1])){
					window.setCurrPlayer(i);
					currPlayer = window.getName(i);
				}
			}
			logActivity("It is " + currPlayer + "'s turn");
		}
		return output;
	}
	
	public String processColour(String msg) {
		String input[] = msg.split(" ");
		String colour = input[1];
		if (colour.equals(Config.PURPLE)) {
			window.setLastTournamentPurple(true);
		}
		
		
		for(int i = 0; i < 5; i++){
			if (colour.equalsIgnoreCase(options[i])){
					window.setTournamentColour(i);
			}
		}
		return output;
	}
	
	public String processPlay(String msg){
		String output = "result";
		//String input[] = msg.split(" ");
		output = msg;
		return output;
	}

	private String playACard() {
		while(this.playedCards == null){}
		
		// if the player choose to withdraw
		if(playedCards.equalsIgnoreCase(Config.WITHDRAW)){
			output = Config.WITHDRAW;
		}
		
		// if the player has finished their turn 
		else if(playedCards.equalsIgnoreCase(Config.END_TURN)){
			output = Config.END_TURN;
		}
		else if(window.getLastCard().getCardType().equalsIgnoreCase(Config.ACTION)){
			output = Config.PLAY + this.processActionCard();
			window.removeCard(window.getLastCard());
			
		}
		else{
			if (window.getLastCard().getType().equals(Config.UNHORSE)) {
				window.setLastTournamentPurple(false);
			}
			output = Config.PLAY + " " + window.getLastCard().getType() + " " + window.getLastCard().getValue();
			window.removeCard(window.getLastCard());
		}
		this.playedCards = null;
		window.setScore(window.getCurrPlayer(), window.getScore(window.getCurrPlayer()) + window.getLastCard().getValue());

		return output;
	}
	
	public String processWaiting(String msg){
		Boolean isAction = false;
		
		// if the client cannot play that card 
		if(msg.contains(Config.UNPLAYABLE)){
			window.addCard(window.getLastCard());
			window.cantPlayCardPopup();
		} else {
			String input[] = msg.split(" ");
			if (input.length > 1) {
				String[] c = null;
				String type = null;
				String value = null;
				if (input[1].contains("_")) {
					c = input[1].split("_");
					type = c[0];
					value = c[1];
				} else {
					type = input[1];
					value = "0";
					isAction=true;
				}
				Card card = this.getCardFromTypeValue(type, value);
				if(isAction){
					this.processActionCardAction(msg);
				}
				else{
					window.addPlayedCard(window.getCurrPlayer(), card);
				}
				if(window.getCurrPlayer() == window.getPlayerNum()){
					window.removeCard(card);
				}
			}
		}
		
		logActivity(msg);
		return output;
	}
	
	
	public String processActionCard(){
		String output = "";
		String cardType = window.getLastCard().getType();
		output = " " + cardType + " " ;
		if (cardType.equals(Config.UNHORSE)){
			if (window.getTournamentColour() != Config.PURPLE_INT) {
				output += Config.PURPLE;
			} else {
				output += window.changeColour();
			}
		
			//added to output <new colour>	
		} else if (cardType.equals(Config.CHANGEWEAPON)) {
			output += window.changeColour();
		}
		else if (cardType.equals(Config.DROPWEAPON)) {
			output += Config.DROPWEAPON;
		} else if(cardType.equalsIgnoreCase(Config.BREAKLANCE)){
				output += window.pickAName("remove all purple cards from their display.");
				//added to output <other name>
		} else if(cardType.equalsIgnoreCase(Config.RIPOSTE)){
			output += window.pickAName("take the last card on their display and add it to yours.");
			//added to output <other name>
		} else if(cardType.equalsIgnoreCase(Config.RETREAT)){
			// pick a card from your display 
			output +=window.playerPickCardFromDisplay(window.getPlayerName());
			//added to output <your cardtype > <value> 
		} else if(cardType.equalsIgnoreCase(Config.KNOCKDOWN)){
			output += window.pickAName("take a card from.");
		} else if(cardType.equalsIgnoreCase(Config.STUNNED)){
			output += window.pickAName("stun.");
			//added to output <other name>
		}
		else if(cardType.equalsIgnoreCase(Config.OUTWIT)){
			//pick a face up card including sheild and stun 
			output += window.playerPickCardForOutwhit(window.getPlayerName());
			String name= window.pickAName("take a played card from.");
			output += " " + name + " " + window.playerPickCardForOutwhit(name);
			//added to output <your cardtype > <value> <other player> <their card type> <value>
			
		}
		else if(cardType.equalsIgnoreCase(Config.DODGE)){
			// pick a player and a card to remove from their display
			String name= window.pickAName("take a played card from.");
			output+= name+" "+window.playerPickCardFromDisplay(name);
			//added to output <other player> <their card type> <value>
			// input = play dodge <player name> <card type> <card value>
		}
		
		return output;
	}
	
	// for input from server on playing the card if an action card is played sent the whole message to this in waiting 
	public void processActionCardAction(String msg){
		String input[]=msg.split(" ");
		String cardType = input[1];
		//note Drop weapon, disgrace, counter charge, charge and outmaneuver don't require anything other than the type
		//output = waiting <card played> <player chosen> (Just remove the first card from that player's hand)
		if(cardType.equalsIgnoreCase(Config.KNOCKDOWN)){
			if(window.getPlayerName().equalsIgnoreCase(input[2])){
				window.removeCard(window.getPlayerCard(0));
			}
			if(window.getCurrPlayer()==window.getPlayerNum()){
				window.addCard(this.getCardFromTypeValue(input[3], input[4]));
			}
		}
		else if(cardType.equalsIgnoreCase(Config.RIPOSTE)){
			//input = waiting <card played> <player stolen from> <player total> <card stolen> <player added to> <player total>
			int player = window.getPlayerByName(input[2]);
			String playerScore = input[3];
			Card c = this.getCardFromTypeValue(input[4], input[5]);
			window.removePlayedCard(player, c);
			window.setScore(player, Integer.parseInt(playerScore));
			int addtoplayer=window.getPlayerByName(input[6]);
			String addToPlayerScore = input[7];
			window.addPlayedCard(addtoplayer,c);	
			window.setScore(addtoplayer, Integer.parseInt(addToPlayerScore));
			
		}
		else if(cardType.equalsIgnoreCase(Config.BREAKLANCE)){
			//in = waiting <card played> display name <player> <player score> cards <display card> <display card> ...
			int player=window.getPlayerByName(input[4]);
			String score = input[5];
			window.setScore(player, Integer.parseInt(score));
			window.resetPlayedCards(player);
			for(int i=7;i<input.length ;i+=2){
				window.addPlayedCard(player, this.getCardFromTypeValue(input[i], input[i+1]));
			}		
		}
		else if(cardType.equalsIgnoreCase(Config.CHANGEWEAPON)||cardType.equalsIgnoreCase(Config.UNHORSE)||cardType.equalsIgnoreCase(Config.DROPWEAPON)){
			window.setTournamentColour(Config.colours.indexOf(input[2]));
		}
		else if(cardType.equalsIgnoreCase(Config.SHIELD)){
			//msg = waiting shield name
			window.setShield(window.getPlayerByName(input[2]), true);
		//TODO
		}
		else if(cardType.equalsIgnoreCase(Config.STUNNED)){
			//msg = waiting stun name
			window.setStun(window.getPlayerByName(input[2]), true);
			//TODO
		}
		else if(cardType.equalsIgnoreCase(Config.DISGRACE)){
			redoDisplay(input);
		}
		else if(cardType.equalsIgnoreCase(Config.ADAPT)){
			redoDisplay(input);
		}
		else if(cardType.equalsIgnoreCase(Config.CHARGE)){
			redoDisplay(input);
		}
		else if(cardType.equalsIgnoreCase(Config.COUNTERCHARGE)){
			redoDisplay(input);
		}
		else if(cardType.equalsIgnoreCase(Config.OUTMANEUVER)){
			redoDisplay(input);
		}
		else if(cardType.equalsIgnoreCase(Config.DODGE)){
			//input[0] = waiting 
			String[] card={input[4],input[5]};
			int player = window.getPlayerByName(input[2]);
			int score = Integer.parseInt(input[3]);
			window.setScore(player, score);
			window.removePlayedCard(player, this.getCardFromTypeValue(card[0], card[1]));
			//input = waiting <card played> <player discarded from> <card discarded>
			
		}
		else if(cardType.equalsIgnoreCase(Config.RETREAT)){
			//input[0] = waiting 
			String[] card={input[4],input[5]};
			int player= window.getPlayerByName(input[2]);
			int score = Integer.parseInt(input[3]);
			window.setScore(player, score);
			Card c= this.getCardFromTypeValue(card[0], card[1]);
			window.removePlayedCard(player, c);
			if(window.getPlayerName().equalsIgnoreCase(input[2])){
				window.addCard(c);
			}
			//input= waiting <card played> <currentPlayerName> <card removed from display and put back into hand>
		}
		else if(cardType.equalsIgnoreCase(Config.OUTWIT)){
			//input[0] = waiting 
			// switch the two things with each other 
			// check if it's sheild or stun 
			// move sheild/ stun if needed
			// move cards from one display to the other if need
			//
			String playerCardValue = input[3];
			String opponentName = input[4];
			String opponentCardValue = input[6];
			
			window.setScore(window.getCurrPlayer(), window.getScore(window.getCurrPlayer()) 
					+ Integer.parseInt(opponentCardValue) - Integer.parseInt(playerCardValue));
			window.setScore(window.getPlayerByName(opponentName), window.getScore(window.getPlayerByName(opponentName)) 
					+ Integer.parseInt(playerCardValue) - Integer.parseInt(opponentCardValue));


		}
	}
	
	public void redoDisplay(String[] input){
		//TODO score
		//input[0]= waiting
		//input[1] = card 
		//input[2]= display
		for (int i=0;i<window.getTotalPlayers();i++){
			window.resetPlayedCards(i);
		}

		int name= -1;
		for(int i=3;i<input.length;i++){
			if(input[i].equalsIgnoreCase(Config.PLAYER_NAME)){
				name=-1;
			}
			else if(input[i-1].equalsIgnoreCase(Config.PLAYER_NAME)){
				name=window.getPlayerByName(input[i]);
			}
			else if(input[i-2].equalsIgnoreCase(Config.PLAYER_NAME)){
				window.setScore(name, Integer.parseInt(input[i]));
			}
			else if(input[i].equalsIgnoreCase(Config.PLAYER_CARDS)){
				// do nothing
			}
			else{
				String[] card= input[i].split("_");
				window.addPlayedCard(name, this.getCardFromTypeValue(card[0], card[1]));						
			}
		}
	}
	
	public String processContinueWithdraw(String msg){
		String output = "result";
		String input[] = msg.split(" ");
		String currentPlayerName = input[0];
		currPlayer = currentPlayerName;
		String winningPlayerName = input[input.length - 1];
		String score = input[2];
		String nextPlayerName = input[4];
		int winningPlayer = window.getPlayerByName(winningPlayerName);
		int currentPlayer = window.getPlayerByName(currentPlayerName);
		window.setScore(currentPlayer, Integer.parseInt(score));
		
		if(msg.contains(Config.PURPLE_WIN)){
			window.setCurrPlayer(winningPlayer);
			if(window.getPlayerName().equalsIgnoreCase(input[input.length - 1])) {
				String chosenColour = window.playerPickToken();
				output = Config.PURPLE_WIN + " " + chosenColour;
				
				
				if(msg.contains(Config.WITHDRAW)){
					logActivity("\n" + currPlayer + " has ended their turn \nand withdrawn from the \ntournament\n");
				}
				currPlayer = winningPlayerName;
				
				for(int i = 0; i < 5; i++){
					if(chosenColour.equalsIgnoreCase(options[i])){
							window.setTournamentColour(i);
							if (chosenColour.equals(Config.PURPLE)) {
								purpleChosen = true;
							}
					}
				}
			}
		}else{
			
			if(msg.contains(Config.TOURNAMENT_WINNER)){
				window.setCurrPlayer(winningPlayer);

				window.startRound();
				String chosenColour = input[5];
				for(int i = 0; i < 5; i++){
					if (chosenColour.equalsIgnoreCase(options[i])) {
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
				
				if(msg.contains(Config.WITHDRAW)){
					logActivity("\n" + currPlayer + " has ended their turn \nand withdrawn from the \ntournament\n");
				}
				
				currPlayer = winningPlayerName;
				logActivity("\n" + currPlayer + " won the tournament\n" + 
							"******************************\n" + 
							"Starting New Tournament\n" + 
							"******************************\n");
				
				output = Config.START_TOURNAMENT;
			}
			
			else if(!msg.contains(Config.TOURNAMENT_WINNER)){
				window.setScore(window.getCurrPlayer(), Integer.parseInt(score));
				
				if(msg.contains(Config.CONTINUE)){
					logActivity(currPlayer + " has ended their turn");
				}
				else if(msg.contains(Config.WITHDRAW)){
					logActivity(currPlayer + " HIIIIIhas ended their turn \nand withdrawn from the \ntournament\n");
					currPlayer = nextPlayerName;
				}
				
				if(window.getPlayerNum() == currentPlayer){
					window.endTurn();
				}
				
				for(int i = 0; i < window.getPlayerNamesArray().size(); i++){
					if(window.getName(i).equalsIgnoreCase(nextPlayerName)){						
						window.setCurrPlayer(i);
						currPlayer = window.getName(i);
						if(window.getPlayerNum() == window.getCurrPlayer()){
							window.startTurn();
							String card[] = input[5].split("_");
							String type = card[0];
							String value = card[1];
							window.addCard(this.getCardFromTypeValue(type, value));
							
							logActivity("\nIt is now: " + currPlayer + "'s turn");
						}
					}
				}
			}
		}
		return output; 
	}


	/* Convert brit's string into resources */
	public Card getCardFromTypeValue(String type, String value){
		output = "";
		String info = "";
		/* Coloured Cards */
		if(type.equals(Config.PURPLE)){
			if(value.equals("3")){
				output = Config.IMG_PURPLE_3; 
				info = Config.infoStrings.get(10);
			}
			else if (value.equals("4")){
				output = Config.IMG_PURPLE_4;
				info = Config.infoStrings.get(11);
			}
			else if(value.equals("5")){
				output = Config.IMG_PURPLE_5;
				info = Config.infoStrings.get(12);
			}
			else if(value.equals("7")){
				output = Config.IMG_PURPLE_7;
				info = Config.infoStrings.get(13);
			}
		}
		else if(type.equals(Config.RED)){
			if(value.equals("3")){
				output = Config.IMG_RED_3;
				info = Config.infoStrings.get(7);
			}
			else if(value.equals("4")){
				output = Config.IMG_RED_4;
				info = Config.infoStrings.get(8);
			}
			else if(value.equals("5")){
				output = Config.IMG_RED_5;
				info = Config.infoStrings.get(9);
			}
		}
		
		else if (type.equals(Config.BLUE)){
			if(value.equals("2")){
				output = Config.IMG_BLUE_2;
				info = Config.infoStrings.get(3);
			}
			else if(value.equals("3")){
				output = Config.IMG_BLUE_3;
				info = Config.infoStrings.get(4);
			}
			else if(value.equals("4")){
				output = Config.IMG_BLUE_4;
				info = Config.infoStrings.get(5);
			}
			else if(value.equals("5")){
				output = Config.IMG_BLUE_5;
				info = Config.infoStrings.get(6);
			}
		}
		
		else if (type.equals(Config.YELLOW)){
			if(value.equals("2")){
				output = Config.IMG_YELLOW_2;
				info = Config.infoStrings.get(0);
			}
			else if(value.equals("3")){
				output = Config.IMG_YELLOW_3;
				info = Config.infoStrings.get(1);
			}
			else if(value.equals("4")){
				output = Config.IMG_YELLOW_4;
				info = Config.infoStrings.get(2);
			}
		}
		
		else if (type.equals(Config.GREEN)){
			output = Config.IMG_GREEN_1;
			info = Config.infoStrings.get(14);
		}
		/*creates a coloured card*/
		if(!output.equals("")){
			Card c = new ColourCard(type,Integer.parseInt(value),output);
			c.setCardDescription(info);
			return c;
		}
		
		/* Supporter */
		if(type.equals(Config.MAIDEN)){
			output = Config.IMG_MAIDEN_6;
			info = Config.infoStrings.get(17);
		}
		else if(type.equals(Config.SQUIRE)){
			if(value.equals("2")){
				output = Config.IMG_SQUIRE_2;
				info = Config.infoStrings.get(15);
			}
			else if(value.equals("3")){
				output = Config.IMG_SQUIRE_3;
				info = Config.infoStrings.get(16);
			}
		}

		if(!output.equals("")){
			Card c = new SupportCard(type,Integer.parseInt(value),output);
			c.setCardDescription(info);
			return c;
		} 
		
		/* Action */
		if(type.equals(Config.DODGE)){
			output = Config.IMG_DODGE;
			info = Config.infoStrings.get(26);
		}
		else if (type.equals(Config.DISGRACE)){
			output = Config.IMG_DISGRACE;
			info = Config.infoStrings.get(32);
		}
		else if (type.equals(Config.RETREAT)){
			output = Config.IMG_RETREAT;
			info = Config.infoStrings.get(27);
		}
		else if(type.equals(Config.RIPOSTE)){
			output = Config.IMG_RIPOSTE;
			info = Config.infoStrings.get(25);
		}
		else if(type.equals(Config.OUTMANEUVER)){
			output = Config.IMG_OUTMANEUVER;
			info = Config.infoStrings.get(29);
		}
		else if(type.equals(Config.COUNTERCHARGE)){
			output = Config.IMG_COUNTER_CHARGE;
			info = Config.infoStrings.get(31);
		}
		else if(type.equals(Config.CHARGE)){
			output = Config.IMG_CHARGE;
			info = Config.infoStrings.get(30);
		}
		else if(type.equals(Config.BREAKLANCE)){
			output = Config.IMG_BREAK_LANCE;
			info = Config.infoStrings.get(24);
		}
		else if(type.equals(Config.ADAPT)){
			output = Config.IMG_ADAPT;
			info = Config.infoStrings.get(33);
		}
		else if (type.equals(Config.OUTWIT)){
			output = Config.IMG_OUTWIT;
			info = Config.infoStrings.get(34);
		}
 		else if (type.equals(Config.DROPWEAPON)){
			output = Config.IMG_DROP_WEAPON;
			info = Config.infoStrings.get(20);
		}
		else if(type.equals(Config.CHANGEWEAPON)){
			output = Config.IMG_CHANGE_WEAPON;
			info = Config.infoStrings.get(19);
		}
		else if(type.equals(Config.UNHORSE)){
			output = Config.IMG_UNHORSE;
			info = Config.infoStrings.get(18);
		}
		else if(type.equals(Config.KNOCKDOWN)){
			output = Config.IMG_KNOCK_DOWN;
			info = Config.infoStrings.get(28);
		}
		else if(type.equals(Config.SHIELD)){
			output = Config.IMG_SHIELD;
			info = Config.infoStrings.get(21);
		}
		else if(type.equals(Config.STUNNED)){
			output = Config.IMG_STUNNED;
			info = Config.infoStrings.get(22);
		}
		else if(type.equals(Config.IVANHOE)){
			output = Config.IMG_IVANHOE;
			info = Config.infoStrings.get(23);
		}
		
		if(!output.equals("")){
			Card c = new ActionCard(type, output);
			c.setCardDescription(info);
			return c;
		}
		return new Card();
	}
}
