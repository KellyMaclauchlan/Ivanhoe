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
		
		if (msg.contains("input")) {
	   		// do nothing and wait for more players to arrive 
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
		getWindow().setTextDisplay(displayText + "\n");
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
