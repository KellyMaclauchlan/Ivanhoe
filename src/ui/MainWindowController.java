package ui;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

import config.Config;
import config.Observer;
import config.Subject;
import game.Card;

public class MainWindowController implements Observer, Subject{
	private int totalPlayers;
	private int playerNum;
	private int tournamentColour;
	private int currPlayer;
	public String playerName;
	public int moved;
	public MainWindow window;
	public WaitingPopUp waitingPopUp;
	public String lastMessege;
	public ArrayList<String>tokenStrings;
	public Color backgroundColours[] = {new Color(128,156,229),new Color(255,0,40),new Color(255,223,0), new Color(81,186,91), new Color(161,89,188)};
	public Card lastCard;
	public Logger log = Logger.getLogger("UI");
	
	private ArrayList<Card> playerCards;
	private ArrayList<ArrayList<Card>> playedCards;
	private ArrayList<Integer>playerScores;
	public ArrayList<String>playerNames;
	private ArrayList<Observer>observers = new ArrayList<Observer>();
	
	public MainWindowController(){
		playerCards = new ArrayList<Card>();
		playerCards.clear();
		window = new MainWindow();
		window.registerObserver(this);
		window.testing = false;
		playedCards = new ArrayList<ArrayList<Card>>();
		playerNames = new ArrayList<String>();
		playerScores = new ArrayList<Integer>();
		moved = 0;
		tokenStrings = new ArrayList<String>();
		tokenStrings.add("resources/icons/blue_full.png");
		tokenStrings.add("resources/icons/red_full.png");
		tokenStrings.add("resources/icons/yellow_full.png");
		tokenStrings.add("resources/icons/green_full.png");
		tokenStrings.add("resources/icons/purple_full.png");
		window.endedTurn();
	}
	/* displays the main window */
	public void showWindow(){window.setVisible(true);}
	
	/* GETTERS */
	public String getNameFromPlayer(){
		String name=JOptionPane.showInputDialog("Enter your name");
		window.setTitle("Ivanhoe: "+name);
		return name ;
		}
	public String getIPPortFromPlayer(){return JOptionPane.showInputDialog("Enter your IP address and Port ie: localhost 3000");}
	public String getNumberOfPlayersFromPlayer(){return JOptionPane.showInputDialog("Enter the number of players in this game (2-5)");}
	public Object getScore(int player) {return this.playerScores.get(player);}
	public Object getName(int player) {return this.playerNames.get(player);}
	public int numCards(){return playerCards.size();}
	public int getPlayerCardSize(){return this.playerCards.size();}
	public int getPlayerNum() {return playerNum;}
	public int getTotalPlayers() {return totalPlayers;}
	public Card getPlayedCard(int player, int index) {return this.playedCards.get(player).get(index);}
	public int getTournamentColour() {
		return tournamentColour;
	}
	public int getCurrPlayer() {return currPlayer;}
	
	/* SETTERS */
	public void setPlayerTurn(int i){this.currPlayer = i;}
	public void setPlayerNum(int player) {playerNum = player;}
	public void setTotalPlayers(int totalPlayers) {this.totalPlayers = totalPlayers;}
	//public void setTournamentColour(int tournamentColour) {this.tournamentColour = tournamentColour;}
	
	public int getPlayerByName(String name) {
		int player = playerNames.indexOf(name);
		return player;
	}
	public void setScore(int player, int score) {
		this.playerScores.set(player, score);	
		window.playerPoints[player].setText(""+score);
	}
	
	public void setName(int player, String name) {
		this.playerNames.set(player, name);
		window.playerNames[player].setText(name);
	}	
	
	public void setTournamentColour(int i) {
		this.tournamentColour=i;
		this.window.getContentPane().setBackground(this.backgroundColours[i]);
	}
	
	public void setCurrPlayer(int currPlayer) {
		this.currPlayer = currPlayer;
		if(currPlayer==this.playerNum){
			window.startTurn();
		}
	}
	
	public void setNumPlayers(int i){
		this.setTotalPlayers(i);
		for(int j = 0; j < i; j++){
			this.playedCards.add(new ArrayList<Card>());
			this.playerNames.add("");
			this.playerScores.add(0);
		}
		log.info("Total number of players is: " + i);
	}
	
	/* Popups */
	public String setTournament(){
		String[] options = new String[] {Config.BLUE, Config.RED, Config.YELLOW, Config.GREEN, Config.PURPLE};
	    int response = JOptionPane.showOptionDialog(null, "Pick a tournament colour", "New Round",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[0]);
		log.info("Tournament colour has been set to " + options[response]);
	    return options[response];
	}
	public String changeColour(){
		String[] options = new String[] {Config.BLUE, Config.RED, Config.YELLOW};
	    int response = JOptionPane.showOptionDialog(null, "Pick a tournament colour", "New Round",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[0]);
		log.info("Tournament colour has been set to " + options[response]);
	    return options[response];
	}
	public String pickAName(String action){

		String[] options = this.playerNames.toArray(new String[0]);
	    int response = JOptionPane.showOptionDialog(null, "Pick a Player to "+action, "New Round",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[0]);
		log.info("Player picked was " + options[response]);
	    return options[response];
	}

	public void cantPlayCardPopup(){ JOptionPane.showMessageDialog(null, "You cannot play that card.");}
	public void automaticWithdrawPopup(){ JOptionPane.showMessageDialog(null, "You cannot play any cards, you have automatically withdrawn from the tournament.");}
	public void showWaiting(){
		this.waitingPopUp= new WaitingPopUp();
		this.waitingPopUp.setVisible(true);
	};
	public void hideWaitng(){ 
		if(this.waitingPopUp!=null)
			this.waitingPopUp.dispose();
		};
	public String playerPickToken(){
		String[] options = new String[] {Config.BLUE, Config.RED, Config.YELLOW, Config.GREEN, Config.PURPLE};
		String have ="";
		for (int i = 0; i < 5; i++){
			if(window.hasTokens[playerNum][i]){
				have += options[i]+" ";
			}
		}
	    int response = JOptionPane.showOptionDialog(null, "Pick a tournament colour, you already have:"+have, "New Round",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[0]);
		String output = options[response];
	    log.info("the player picked " + output);
	    return output;
	}
	public void GameOverPopup(String winner){ JOptionPane.showMessageDialog(null, "Game over "+winner+" won!");}
	
	/* Observer Pattern */
	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}
	
	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}
	
	@Override
	public void notifyObservers(String message) {
		for (Observer ob : observers){
			ob.update(message);
		}
	}
	
	@Override
	public void update(String message) {
		lastMessege = message;
		switch(message){
			case "leftclick": leftClick();
			break;
			case "rightclick": rightClick();
			break;
			case "withdrawclick": withdrawClick();
			break;
			case "endturnclick": endturnClick();
			break;
			case "playedcard":playCard();
			break;
			case "viewdisplay":displayCards();
			break;
		}
		log.info("Updated Subjects");
	}

	/* Add card to Player's hand */
	public void addCard(Card newCard){
		playerCards.add(newCard);
		if(playerCards.size() < 11){
			this.window.addPlayerCard(playerCards.size()-1, newCard.getCardImage());
		}
		log.info("Adding new card");
	}
	
	/* Remove card from Player's hand */
	public void removeCard(int i){
		if(playerCards.size() > 10){
			if(moved != 0){moved--;}
		}
		playerCards.remove(i);
		this.resetCards();
		log.info("Card removed");
	}
	public void removeCard(Card card){
		playerCards.remove(card);
		this.resetCards();
		log.info("Card removed");
	}
	
	/* Add a card to the given Player's played cards and show it on the button */
	public void addPlayedCard(int player, Card card){
		this.playedCards.get(player).add(card);
		this.window.addPlayedCard(player, card.getCardImage());
		log.info("Player " + this.playedCards.get(player) + " added " + card + " to their hand");
	}
	
	/* Show display for popup of other Player's display */
	public void displayCards() {
		CardDisplayPopUp popup = new CardDisplayPopUp(this.playedCards.get(this.window.playedCard));
		popup.setVisible(true);
		log.info("Cards are being displayed");
	}
	
	/*SENDS END TURN NOTIFICAITON AND COMMAND TO NETWORK AND UI*/
	public void endturnClick() {
		this.window.endTurnClicked();
		this.notifyObservers(Config.END_TURN);
		log.info("Player has ended their turn");
	}

	/* Send withdraw notification and command to network and UI */
	public void withdrawClick() {
		this.window.withdrawClicked();
		this.notifyObservers(Config.WITHDRAW);
		log.info("Player has withdrawed");
	}

	/* Moves the Player's cards one to the left to show the cards on the right side that were hidden */
	public void rightClick() {
		if(moved < playerCards.size()-10){
			moved++;
			this.window.rightArrowClicked(playerCards.get(moved+9).getCardImage());
		}
	}

	/* Moves the Player's cards one to the right to show the cards on the left side that were hidden*/
	public void leftClick(){
		if(moved != 0){
			moved--;
			this.window.leftArrowClicked(playerCards.get(moved).getCardImage());
		}
	}
	
	/* Starts a new round with the UI */
	public void startRound() {
		for(int i = 0; i < this.playerNum; i++){
			setScore(i,0);			
			this.playedCards.get(i).clear();
			this.window.addPlayedCard(i, Config.IMG_BACK);
			this.window.playedCards[i].setEnabled(true);
			log.info("Player " + this.playedCards.get(i) + "started their round");
		}	
		this.window.endedTurn();
	}
	
	/* Player plays a cards and notifies */
	public void playCard() {		
		if(this.window.lastCard<this.playerCards.size()){
			this.lastCard= this.playerCards.get(this.window.lastCard+this.moved);
			log.info("Played card " + this.lastCard.getType()+" "+ this.lastCard.getValue());
			this.notifyObservers(Config.PLAYEDCARD);
		}
	}

	/* Resets the Player's hand after a card has been played */
	public void resetCards(){		
		int maxx = Math.min(10, playerCards.size());
		int i;
		for(i = 0; i < maxx; i++){
			this.window.addPlayerCard(i, playerCards.get(i+moved).getCardImage());
		}
		
		for(i = i; i < 10; i++){
			this.window.playerCards[i].setIcon(null);
		}
		log.info("Cards have been reset");
	}
	
	/* Adds a token for Player after that round */
	public void addToken(int player, int token){
		this.window.setToken(player, token, tokenStrings.get(token));
		log.info("Adding " + tokenStrings.get(token) + " token");
	}
	public void playerWithdraws(String name){
		int player=this.playerNames.indexOf(name);
		window.playerPoints[player].setText("0");
		window.playedCards[player].setEnabled(false);
	}
}
