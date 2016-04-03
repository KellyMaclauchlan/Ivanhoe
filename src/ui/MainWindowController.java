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
	private String playerName;
	private int moved;
	private MainWindow window;
	private WaitingPopUp waitingPopUp;
	private String lastMessage;
	private Color backgroundColours[] = {new Color(128,156,229),new Color(255,0,40),new Color(255,223,0), new Color(81,186,91), new Color(161,89,188)};
	private Card lastCard;
	private Logger log = Logger.getLogger("UI");
	private int numberOfPlayers;
	
	private ArrayList<Card> playerCards;
	private ArrayList<ArrayList<Card>> playedCards;
	private ArrayList<Integer>playerScores;
	private ArrayList<String>playerNames;
	private ArrayList<Observer>observers = new ArrayList<Observer>();
	private boolean lastTournamentPurple = false;
	
	
	public MainWindowController(){
		playerCards = new ArrayList<Card>();
		playerCards.clear();
		window = new MainWindow();
		window.registerObserver(this);
		window.setTesting(false);
		playedCards = new ArrayList<ArrayList<Card>>();
		playerNames = new ArrayList<String>();
		playerScores = new ArrayList<Integer>();
		moved = 0;
		for(int i=0;i<5;i++){
			this.window.setStun(i, false);
			this.window.setShield(i, false);
		}
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
	public MainWindow getWindow(){return window;}
	public int getMoved(){return moved;}
	public Color getBackgroundColour(int i){return backgroundColours[i];}
	public ArrayList<String> getPlayerNamesArray(){return playerNames;}
	public String getPlayerName(){return playerName;}
	public String getLastMessage(){return lastMessage;}
	public int getScore(int player) {return this.playerScores.get(player);}
	public String getName(int player) {return this.playerNames.get(player);}
	public int numCards(){return playerCards.size();}
	public int getPlayerCardSize(){return this.playerCards.size();}
	public int getPlayerNum() {return playerNum;}
	public int getTotalPlayers() {return totalPlayers;}
	public Card getPlayedCard(int player, int index) {return this.playedCards.get(player).get(index);}
	public Card getPlayerCard(int index) {return this.playerCards.get(index);}
	public Card getLastCard(){return lastCard;}
	public int getTournamentColour() {
		return tournamentColour;
	}
	public int getCurrPlayer() {return currPlayer;}
	
	/* SETTERS */
	public void setPlayerTurn(int i){this.currPlayer = i;}
	public void setPlayerNum(int player) {playerNum = player;}
	public void setTotalPlayers(int totalPlayers) {this.totalPlayers = totalPlayers;}
	public void setMoved(int i){moved = i;}
	
	public int getPlayerByName(String name) {
		int player = playerNames.indexOf(name);
		return player;
	}
	public void setVarPlayerName(String name){playerName = name;}
	
	public void setTextDisplay(String msg){window.getDisplayText().append(msg);}
	
	public void setScore(int player, int score) {
		this.playerScores.set(player, score);	
		window.getPlayerPoints(player).setText("" + score);
	}
	
	public void setName(int player, String name) {
		this.playerNames.set(player, name);
		window.getPlayerNames(player).setText(name);
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
		for(int i=0;i<5;i++){
			window.getPlayerNames(i).setSelected(false);
		}
		window.getPlayerNames(currPlayer).setSelected(true);
	}
	
	public void setNumPlayers(int i){
		this.setTotalPlayers(i);
		int j ;
		for(j = 0; j < i; j++){
			this.playedCards.add(new ArrayList<Card>());
			this.playerNames.add("");
			this.playerScores.add(0);
		}
		for(j=i;j<5;j++){
			this.window.getPlayerCards(j).setEnabled(false);
		}
		log.info("Total number of players is: " + i);
	}
	
	/* Popups */
	public String getIPPortFromPlayer(){return JOptionPane.showInputDialog("Enter your IP address and Port ie: localhost 3000");}
	public String getNumberOfPlayersFromPlayer() {
		String[] possibilities= {"2","3","4","5"};
		String s = (String)JOptionPane.showInputDialog(
            null,
            "Enter the number of players in this game",
            "Customized Dialog",
            JOptionPane.PLAIN_MESSAGE,
            null,
            possibilities,
            "2");
		numberOfPlayers = Integer.parseInt(s);
		return s;
	}
	public String getNumberOfAIFromPlayer() {
		int numPlayers = numberOfPlayers;
		ArrayList<String> nums= new ArrayList<String>();
		for(int i=0;i<=numPlayers;i++){
			nums.add(Integer.toString(i));			
		}
		String[] possibilities= new String[nums.size()];
		nums.toArray(possibilities);
		String s = (String)JOptionPane.showInputDialog(
            null,
            "Enter the number of AI players you want in this game",
            "Customized Dialog",
            JOptionPane.PLAIN_MESSAGE,
            null,
            possibilities,
            "0");
		return s;
	}
	public String setTournament(){
		String[] options = null;
		ArrayList<String> colours = new ArrayList<>();
		for (Card c: playerCards) {
			if (c.getCardType().equals(Config.SUPPORT)) {
				if (lastTournamentPurple) {
					options = new String[] {Config.BLUE, Config.RED, Config.YELLOW, Config.GREEN};
				} else {
					options = new String[] {Config.BLUE, Config.RED, Config.YELLOW, Config.GREEN, Config.PURPLE};
				}
				colours = null;
				break;
			} else if (!colours.contains(c.getType()) && c.getCardType().equals(Config.COLOUR) && 
					!(c.getType().equals(Config.PURPLE) && lastTournamentPurple)) {
				colours.add(c.getType());
			}
		}
		setLastTournamentPurple(false);
		if (colours != null) {
			options = new String[colours.size()];
			for (int i = 0; i < colours.size(); i++) {
				options[i] = colours.get(i);
			}
		}
		if (options == null) {
			endturnClick();
		} else {
		    int response = JOptionPane.showOptionDialog(null, "Pick a tournament colour", "New Round",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[0]);
			log.info("Tournament colour has been set to " + options[response]);
		    return options[response];
		}
		return "nil";
	}
	public String changeColour(){
		String[] options = new String[] {Config.BLUE, Config.RED, Config.YELLOW};
	    int response = JOptionPane.showOptionDialog(null, "Pick a tournament colour", "New Round",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[0]);
		log.info("Tournament colour has been set to " + options[response]);
	    return options[response];
	}
	public String pickAName(String action){
		String[] options = new String[playerNames.size() - 1];
		int i = 0;
		for (String name: playerNames) {
			if (!name.equals(playerName)) {
				options[i] = name;
				i++;
			}
		}
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
		String need ="";
		for (int i = 0; i < 5; i++){
			if(!window.getPlayerArrayofTokens(playerNum, i)){
				need += options[i] + " ";
			}
		}
	    int response = JOptionPane.showOptionDialog(null, "Pick a token colour. You still need: " + need, "New Round",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[0]);
		String output = options[response];
	    log.info("the player picked " + output);
	    return output;
	}
	
	public String playerPickTokenRemove(){
		String[] options = new String[] {Config.BLUE, Config.RED, Config.YELLOW, Config.GREEN, Config.PURPLE};
		ArrayList<String> playerTokens = new ArrayList<String>();
		for (int i = 0; i < 5; i++){
			if(window.getPlayerArrayofTokens(playerNum, i)){
				playerTokens.add(options[i]);
			}
		}
		options = new String[playerTokens.size()];
		for (int i = 0; i < playerTokens.size(); i++) {
			options[i] = playerTokens.get(i);
		}		
		String option = options[0];
	    int response = JOptionPane.showOptionDialog(null, "Pick a tournament colour to lose:", "New Round",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, option);
		String output = options[response];
	    log.info("the player picked " + output);
	    return output;
	}
	
	public void GameOverPopup(String winner){ JOptionPane.showMessageDialog(null, "Game over "+winner+" won!");}
	
	//asks user if they would like to play ivanhoe to stop the action card returns true our false 
	public Boolean playIvanhoe(String name){
			int result =JOptionPane.showConfirmDialog(null, 
				   "Do you want to use Ivanhoe to stop the "+name+" card?",null, JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.OK_OPTION) {
			for (int i = 0; i < playerCards.size(); i++) {
				if (playerCards.get(i).getType().equals(Config.IVANHOE)) {
					removeCard(i);
				}
			}
			return true;
		}
		return false;
	}
	
	public String playerPickCardFromDisplay(String name){
		int player=this.playerNames.indexOf(name);
		String result="";
		ArrayList<String> info= new ArrayList<String>();

		int i=0;
		for(Card c : this.playedCards.get(player)){
			i++;
			info.add(i+". "+c.getCardDescription());
		}
		String[] possibilities= new String[info.size()];
		info.toArray(possibilities);
		String s = (String)JOptionPane.showInputDialog(
                null,
                "pick a card",
                "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                info.get(0));
		for(Card c : this.playedCards.get(player)){
			if(s.contains(c.getCardDescription())){
				return c.getType()+" "+c.getValue();
			}
		}
		return result;
	}
	public String playerPickCardForOutwit(String name){
		int player = this.playerNames.indexOf(name);
		String result = "";
		ArrayList<String> info= new ArrayList<String>();
		int i=0;
		for(Card c : this.playedCards.get(player)){
			i++;
			info.add(i+". "+c.getCardDescription());
		}
		if(window.getShield(player).isVisible()){
			i++;
			info.add(i+". "+Config.SHIELD);
		}
		if(window.getStun(player).isVisible()){
			i++;
			info.add(i+". "+Config.STUNNED);
		}
		String[] possibilities= new String[info.size()];
		info.toArray(possibilities);
		String playerName = null;
		if (name.equals(getPlayerName())) {
			playerName = "your";
		} else {
			playerName = name + "'s";
		}
		
		String s = (String)JOptionPane.showInputDialog(
                null,
                "pick a card to swap from " + playerName + " display",
                "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                info.get(0));
		
		for(Card c : this.playedCards.get(player)){
			if(s.contains(c.getCardDescription())){
				return c.getType()+" "+c.getValue();
			}				
		} 
		if(s.contains(Config.SHIELD)){
			return Config.SHIELD+" 0";
		}
		if(s.contains(Config.STUNNED)){
			return Config.STUNNED+" 0";
		}
		return s;
	}
	
	/*end of popups*/
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
		lastMessage = message;
		switch(message){
			case "leftclick": leftClick();
			break;
			case "rightclick": rightClick();
			break;
			case "withdrawclick": withdrawClick();
			break;
			case "endturnclick": endturnClick();
			break;
			case "playedcard": playCard();
			break;
			case Config.VIEWDISPLAY:displayCards();
			break;
			case "quit":notifyObservers(message);
			break;
			case "description":addDescription();
			break;
		}
		log.info("Updated Subjects");
	}

	private void addDescription() {
		String info= this.playerCards.get(window.getLastCard() + moved).getCardDescription();
		window.getCardTextLabel().setText(info);
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
		if (card != null) {
			playerCards.remove(card);
			this.resetCards();
			log.info("Card removed");
		}
	}
	
	/* Add a card to the given Player's played cards and show it on the button */
	public void addPlayedCard(int player, Card card){
		this.playedCards.get(player).add(card);
		this.window.addPlayedCard(player, card.getCardImage());
		log.info("Player " + this.playedCards.get(player) + " added " + card + " to their hand");
	}
	
	/* Show display for popup of other Player's display */
	public void displayCards() {
		CardDisplayPopUp popup = new CardDisplayPopUp(this.playedCards.get(this.window.getPlayedCard()));
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
		for(int i = 0; i < this.totalPlayers; i++){
			setScore(i,0);			
			this.playedCards.get(i).clear();
			//this.playedCards.set(i, new ArrayList<Card>());						
			this.window.addPlayedCard(i, Config.IMG_BACK);
			this.window.getPlayedCards(i).setEnabled(true);
			//log.info("Player " + this.playedCards.get(i) + "started their round");
		}
		for(int i=0;i<5;i++){
			this.window.setStun(i, false);
			this.window.setShield(i, false);
		}
		this.window.endedTurn();
	}
	
	/* Player plays a cards and notifies */
	public void playCard() {		
		if(this.window.getLastCard() < this.playerCards.size()){
			this.lastCard = this.playerCards.get(this.window.getLastCard() + this.moved);
			log.info("Played card " + this.lastCard.getType()+ " " + this.lastCard.getValue());
			this.notifyObservers(Config.PLAYEDCARD);
		}
	}

	/* Resets the Player's hand after a card has been played */
	public void resetCards(){		
		int max = Math.min(10, playerCards.size());
		int i;
		moved=0;
		for(i = 0; i < max; i++){
			if (i < playerCards.size()) {
				this.window.addPlayerCard(i, playerCards.get(i).getCardImage());
			}
		}
		
		for(i = i; i < 10; i++){
			this.window.getPlayerCards(i).setIcon(null);
		}
		log.info("Cards have been reset");
	}
	
	/* Adds a token for Player after that round */
	public void addToken(int player, int token){
		this.window.setToken(player, token, Config.tokenStrings.get(token));
		log.info("Adding " + Config.tokenStrings.get(token) + " token");
	}
	public void playerWithdraws(String name){
		int player=this.playerNames.indexOf(name);
		window.getPlayerPoints(player).setText("0");
		window.getPlayedCards(player).setEnabled(false);
		
	}
	public void removeToken(int player, String token){		
		this.window.setToken(player, Config.colours.indexOf(token), Config.emptyTokenStrings.get(Config.colours.indexOf(token)));
		this.window.setHasToken(player, Config.colours.indexOf(token), false);
		log.info("Removing " + token + " token");
	}
	public boolean isLastTournamentPurple() {
		return lastTournamentPurple;
	}
	public void setLastTournamentPurple(boolean lastTournamentPurple) {
		this.lastTournamentPurple = lastTournamentPurple;
	}

	public void endTurn(){
		this.window.endedTurn();
	}
	public void startTurn(){
		this.window.startTurn();
	}
	public void setStun(int player, boolean toggle){
		window.setStun(player, toggle);
	}
	public void setShield(int player, boolean toggle){
		window.setShield(player, toggle);
	}
	
	public void resetPlayedCards(int player){
		this.playedCards.get(player).clear();
	}
	public void removePlayedCard(int player, Card c){
		int i = 0;
		for (i = 0; i < playedCards.get(player).size(); i++) {
			if (playedCards.get(player).get(i).getType().equals(c.getType()) && (playedCards.get(player).get(i).getValue() == c.getValue())) {
				break;
			}
		}
		this.playedCards.get(player).remove(i);
		if ((playedCards.get(player).size() > 0) && (i == (playedCards.get(player).size()))) {
				this.window.setPlayedCardImage(player, this.playedCards.get(player).get(i-1).getCardImage());
		} 
	}
}
