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
	private String currPlayer = null; // used for logging activity 
	private String[] options = new String[] {Config.BLUE, Config.RED, Config.YELLOW, Config.GREEN, Config.PURPLE};
	private boolean purpleChosen = false;
	private ArrayList<String> actioncards = new ArrayList<String>();
	private String thisPlayerName = null;
	private boolean ivanhoePrompted = false;
	private GUIProcessor guiProcessor;
	private InputProcessor inputProcessor;

	public Client(){
		setWindow(new MainWindowController());
		getWindow().registerObserver(this);
		guiProcessor = new GUIProcessor(this);
		inputProcessor = new InputProcessor(this);
		String ipAndPort = getWindow().getIPPortFromPlayer();
		String seperate[] = ipAndPort.split(" ");
		this.successConnect = this.connectToServer(seperate[0], Integer.parseInt(seperate[1]));
		setActionCardArraylist();
	}
	
	/* Constructor used for Testing */
	public Client(String ip, int port){
		this.successConnect = this.connectToServer(ip, port);
		setActionCardArraylist();
	}
	
	public void addToHand(String cardString) {
		hand.add(cardString);
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
         catch (IOException e) {  
         	log.error(e);
         	stop();
         }}
	}
	
	public void stop() {
		try{
			if (thread != null){
				thread = null;
				if (inStream != null){
					inStream.close();}
				if (outStream != null){
					outStream.close();}
				if (socket != null){
					socket.close();}
				
				this.socket = null;
				this.inStream = null;
				this.outStream = null; 
			}
		} catch (IOException e) {
			log.error(e);
		}
		client.close();
	}
	
	/* Handles all the input and output to and from the server */
	public void handle(String msg) throws IOException {
		String send = Config.OUTPUT;
		System.out.println("Message received: " + msg);
		log.info("Message Received: " + msg);
		
<<<<<<< HEAD
		if(msg.contains("input")){
	   		// do nothing and wait for the server to send something 
=======
		if (msg.contains("input")) {
	   		// do nothing and wait for more players to arrive 
>>>>>>> brit
		} else {
			testing = msg;
			send = inputProcessor.processInput(msg);

			log.info("Information sent to server: " + send);
			outStream.write(send);
			outStream.write("\n");
			outStream.flush();
			
			if (send.equals(Config.QUIT)){
				stop();
				log.info(id + " has left the game");
			}
		}
		setOutput(Config.OUTPUT);
	}

	public void update(String message) {
		String send = Config.FROMUPDATE;
		if(message.contains(Config.PLAYEDCARD)){
			playedCards = getWindow().getLastCard().getCardType() + " " +  getWindow().getLastCard().getValue(); 
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
		if (input[0].equals(Config.WAITING) && !msg.contains(Config.UNPLAYABLE)) {
			displayText = getCurrPlayer() + " has played a card";
		} else {
			displayText = msg;
		}
<<<<<<< HEAD
		window.setTextDisplay(displayText + "\n");
	}

	/* Handles what the server has sent from the Game Engine and processes
	 * what buttons/popups/commands the client and GUI must send back */
	public String processInput(String msg){
		if(msg.equals(Config.QUIT)) {  
			output = Config.QUIT + " " + thisPlayerName;
		} 
		else if(msg.contains(Config.PLAYER_LEFT)){
			window.GameOverPopup("No one has");
		}
		else if (msg.contains(Config.PLAY_IVANHOE)) {
			String[] input = msg.split(" ");
			//input: waiting plyivnhoe <name> <card>
			String cardName = input[3];
			String playerName = input[2];
			
			if (!ivanhoePrompted) {
				if (playerName.equals(thisPlayerName)) {
					boolean played = window.playIvanhoe(cardName);
					ivanhoePrompted = true;
					if (played) {
						return output = Config.PLAY + " " + Config.IVANHOE + " " + cardName;
					} else if (!played) {
						return output = Config.PLAY + " " +  Config.IVANHOE_DECLINED;
					}
				}
			}
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
		
		else if (msg.contains(Config.MAIDEN) && !msg.contains(Config.PLAY) && !msg.contains(Config.HAND) && !msg.contains("_") && !msg.contains(Config.WAITING)) {
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
		thisPlayerName = name;
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
=======
		getWindow().setTextDisplay(displayText + "\n");
>>>>>>> brit
	}

	private String playACard() {
		while(this.playedCards == null){}
		if(playedCards.equalsIgnoreCase(Config.WITHDRAW)){
			setOutput(Config.WITHDRAW);
		} else if (playedCards.equalsIgnoreCase(Config.END_TURN)){
			setOutput(Config.END_TURN);
		}
		else if (getWindow().getLastCard().getCardType().equalsIgnoreCase(Config.ACTION)){
			setOutput(Config.PLAY + getProcessor().processActionCard());
			getWindow().removeCard(getWindow().getLastCard());
			
		} else {
			if (getWindow().getLastCard().getType().equals(Config.UNHORSE)) {
				getWindow().setLastTournamentPurple(false);
			}
			setOutput(Config.PLAY + " " + getWindow().getLastCard().getType() + " " + getWindow().getLastCard().getValue());
			getWindow().removeCard(getWindow().getLastCard());
		}
		this.playedCards = null;
		getWindow().setScore(getWindow().getCurrPlayer(), getWindow().getScore(getWindow().getCurrPlayer()) + getWindow().getLastCard().getValue());
		return getOutput();
	}
	
	public Card getCardFromTypeValue(String type, String value){
		setOutput("");
		String info = "";
		/* Coloured Cards */
		if(type.equals(Config.PURPLE)){
			if(value.equals("3")){
				setOutput(Config.IMG_PURPLE_3); 
				info = Config.infoStrings.get(10);
			}
			else if (value.equals("4")){
				setOutput(Config.IMG_PURPLE_4);
				info = Config.infoStrings.get(11);
			}
			else if(value.equals("5")){
				setOutput(Config.IMG_PURPLE_5);
				info = Config.infoStrings.get(12);
			}
			else if(value.equals("7")){
				setOutput(Config.IMG_PURPLE_7);
				info = Config.infoStrings.get(13);
			}
		}
		else if(type.equals(Config.RED)){
			if(value.equals("3")){
				setOutput(Config.IMG_RED_3);
				info = Config.infoStrings.get(7);
			}
			else if(value.equals("4")){
				setOutput(Config.IMG_RED_4);
				info = Config.infoStrings.get(8);
			}
			else if(value.equals("5")){
				setOutput(Config.IMG_RED_5);
				info = Config.infoStrings.get(9);
			}
		}
		
		else if (type.equals(Config.BLUE)){
			if(value.equals("2")){
				setOutput(Config.IMG_BLUE_2);
				info = Config.infoStrings.get(3);
			}
			else if(value.equals("3")){
				setOutput(Config.IMG_BLUE_3);
				info = Config.infoStrings.get(4);
			}
			else if(value.equals("4")){
				setOutput(Config.IMG_BLUE_4);
				info = Config.infoStrings.get(5);
			}
			else if(value.equals("5")){
				setOutput(Config.IMG_BLUE_5);
				info = Config.infoStrings.get(6);
			}
		}
		
		else if (type.equals(Config.YELLOW)){
			if(value.equals("2")){
				setOutput(Config.IMG_YELLOW_2);
				info = Config.infoStrings.get(0);
			}
			else if(value.equals("3")){
				setOutput(Config.IMG_YELLOW_3);
				info = Config.infoStrings.get(1);
			}
			else if(value.equals("4")){
				setOutput(Config.IMG_YELLOW_4);
				info = Config.infoStrings.get(2);
			}
		}
		
		else if (type.equals(Config.GREEN)){
			setOutput(Config.IMG_GREEN_1);
			info = Config.infoStrings.get(14);
		}
		/*creates a coloured card*/
		if(!getOutput().equals("")){
			Card c = new ColourCard(type,Integer.parseInt(value),getOutput());
			c.setCardDescription(info);
			return c;
		}
		
		/* Supporter */
		if(type.equals(Config.MAIDEN)){
			setOutput(Config.IMG_MAIDEN_6);
			info = Config.infoStrings.get(17);
		}
		else if(type.equals(Config.SQUIRE)){
			if(value.equals("2")){
				setOutput(Config.IMG_SQUIRE_2);
				info = Config.infoStrings.get(15);
			}
			else if(value.equals("3")){
				setOutput(Config.IMG_SQUIRE_3);
				info = Config.infoStrings.get(16);
			}
		}

		if(!getOutput().equals("")){
			Card c = new SupportCard(type,Integer.parseInt(value),getOutput());
			c.setCardDescription(info);
			return c;
		} 
		
		/* Action */
		if(type.equals(Config.DODGE)){
			setOutput(Config.IMG_DODGE);
			info = Config.infoStrings.get(26);
		}
		else if (type.equals(Config.DISGRACE)){
			setOutput(Config.IMG_DISGRACE);
			info = Config.infoStrings.get(32);
		}
		else if (type.equals(Config.RETREAT)){
			setOutput(Config.IMG_RETREAT);
			info = Config.infoStrings.get(27);
		}
		else if(type.equals(Config.RIPOSTE)){
			setOutput(Config.IMG_RIPOSTE);
			info = Config.infoStrings.get(25);
		}
		else if(type.equals(Config.OUTMANEUVER)){
			setOutput(Config.IMG_OUTMANEUVER);
			info = Config.infoStrings.get(29);
		}
		else if(type.equals(Config.COUNTERCHARGE)){
			setOutput(Config.IMG_COUNTER_CHARGE);
			info = Config.infoStrings.get(31);
		}
		else if(type.equals(Config.CHARGE)){
			setOutput(Config.IMG_CHARGE);
			info = Config.infoStrings.get(30);
		}
		else if(type.equals(Config.BREAKLANCE)){
			setOutput(Config.IMG_BREAK_LANCE);
			info = Config.infoStrings.get(24);
		}
		else if(type.equals(Config.ADAPT)){
			setOutput(Config.IMG_ADAPT);
			info = Config.infoStrings.get(33);
		}
		else if (type.equals(Config.OUTWIT)){
			setOutput(Config.IMG_OUTWIT);
			info = Config.infoStrings.get(34);
		}
 		else if (type.equals(Config.DROPWEAPON)){
			setOutput(Config.IMG_DROP_WEAPON);
			info = Config.infoStrings.get(20);
		}
		else if(type.equals(Config.CHANGEWEAPON)){
			setOutput(Config.IMG_CHANGE_WEAPON);
			info = Config.infoStrings.get(19);
		}
		else if(type.equals(Config.UNHORSE)){
			setOutput(Config.IMG_UNHORSE);
			info = Config.infoStrings.get(18);
		}
		else if(type.equals(Config.KNOCKDOWN)){
			setOutput(Config.IMG_KNOCK_DOWN);
			info = Config.infoStrings.get(28);
		}
		else if(type.equals(Config.SHIELD)){
			setOutput(Config.IMG_SHIELD);
			info = Config.infoStrings.get(21);
		}
		else if(type.equals(Config.STUNNED)){
			setOutput(Config.IMG_STUNNED);
			info = Config.infoStrings.get(22);
		}
		else if(type.equals(Config.IVANHOE)){
			setOutput(Config.IMG_IVANHOE);
			info = Config.infoStrings.get(23);
		}
		
		if(!getOutput().equals("")){
			Card c = new ActionCard(type, getOutput());
			c.setCardDescription(info);
			return c;
		}
		return new Card();
	}

	public MainWindowController getWindow() {
		return window;
	}

	public void setWindow(MainWindowController window) {
		this.window = window;
	}

	public ArrayList<String> getHand() {
		return hand;
	}

	public void setHand(ArrayList<String> hand) {
		this.hand = hand;
	}

	public String getThisPlayerName() {
		return thisPlayerName;
	}

	public void setThisPlayerName(String thisPlayerName) {
		this.thisPlayerName = thisPlayerName;
	}

	public String getCurrPlayer() {
		return currPlayer;
	}

	public void setCurrPlayer(String currPlayer) {
		this.currPlayer = currPlayer;
	}

	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}

	public boolean isIvanhoePrompted() {
		return ivanhoePrompted;
	}

	public void setIvanhoePrompted(boolean ivanhoePrompted) {
		this.ivanhoePrompted = ivanhoePrompted;
	}

	public boolean isPurpleChosen() {
		return purpleChosen;
	}

	public void setPurpleChosen(boolean purpleChosen) {
		this.purpleChosen = purpleChosen;
	}

	public String getOutput() {
		return output;
	}

	public String setOutput(String output) {
		this.output = output;
		return output;
	}

	public GUIProcessor getProcessor() {
		return guiProcessor;
	}

	public void setProcessor(GUIProcessor processor) {
		this.guiProcessor = processor;
	}
}
