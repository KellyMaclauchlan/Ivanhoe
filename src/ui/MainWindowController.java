package ui;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import config.Config;
import config.Observer;
import game.Card;

public class MainWindowController implements Observer{
	private ArrayList<Card> playerCards;
	private ArrayList<ArrayList<Card>> playedCards;
	private ArrayList<Integer>playerScores;
	private ArrayList<String>playerNames;
	private int totalPlayers;
	private int playerNum;
	private static Config config;
	private int currPlayer;
	public int moved;
	public MainWindow window;
	public String lastMessege;
	public MainWindowController(){
		playerCards= new ArrayList<Card>();
		playerCards.clear();
		config=new Config();
		window = new MainWindow();
		window.registerObserver(this);
		window.testing=false;
		playedCards= new ArrayList<ArrayList<Card>>();
		playerNames= new ArrayList<String>();
		playerScores= new ArrayList<Integer>();
		moved=0;
		
	}
	public void showWindow(){
		window.setVisible(true);
	}
	public int getPlayerCardSize(){
		return this.playerCards.size();
	}

	public void setPlayerNum(int player) {
		// TODO Auto-generated method stub
		playerNum=player;
	}
	public int getPlayerNum() {
		return playerNum;	
	}
	public void addCard(Card newCard){
		playerCards.add(newCard);
		if(playerCards.size()<11){
			
			this.window.addPlayerCard(playerCards.size()-1, newCard.getCardImage());
		}
	}
	public void removeCard(int i){
		if(playerCards.size()>10){
			if(moved!=0)
				moved--;
		}
		System.out.println(i);
		playerCards.remove(i);
		this.resetCards();
	}
	public int numCards(){
		return playerCards.size();
	}
	public void setNumPlayers(int i){
		this.setTotalPlayers(i);
		for(int j=0;j<i;j++){
			this.playedCards.add(new ArrayList<Card>());
			this.playerNames.add("");
			this.playerScores.add(0);
		}
	}

	public void addPlayedCard(int player, Card card){
		this.playedCards.get(player).add(card);
		this.window.addPlayedCard(player, card.getCardImage());
	}
	

	@Override
	public void update(String message) {
		// TODO Auto-generated method stub
		lastMessege= message;
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
		
	}
	public void displayCards() {
		// TODO Auto-generated method stub
		CardDisplayPopUp popup= new CardDisplayPopUp(this.playedCards.get(this.window.playedCard));
		popup.setVisible(true);
	}
	public void endturnClick() {
		// TODO Auto-generated method stub
		System.out.println("endTurn click");
	}

	public void withdrawClick() {
		// TODO Auto-generated method stub
		System.out.println("withdraw click");
		this.window.withdrawClicked();
	}

	public void rightClick() {
		// TODO Auto-generated method stub
		System.out.println("right click");
		if(moved<playerCards.size()-10){
			moved++;
			this.window.rightArrowClicked(playerCards.get(moved+9).getCardImage());
		}
		
	}

	public void leftClick(){
		System.out.println("left click");
		if(moved!=0){
			moved--;
			this.window.leftArrowClicked(playerCards.get(moved).getCardImage());
		}
		
	}
	public int getTotalPlayers() {
		return totalPlayers;
	}
	public void setTotalPlayers(int totalPlayers) {
		this.totalPlayers = totalPlayers;
	}
	public Card getPlayedCard(int player, int index) {
		// TODO Auto-generated method stub
		return this.playedCards.get(player).get(index);
		
	}
	public int getCurrPlayer() {
		return currPlayer;
	}
	public void setCurrPlayer(int currPlayer) {
		this.currPlayer = currPlayer;
	}
	public void startRound() {
		// TODO Auto-generated method stub
		for(int i=0;i<this.playerNum;i++){
			setScore(i,0);
			this.window.addPlayedCard(i, "resources/cards_small/simpleCards18.jpg");
			this.playedCards.get(i).clear();
		}	
	}
	
	public void setScore(int player, int score) {
		// TODO Auto-generated method stub
		this.playerScores.set(player, score);	
		window.playerPoints[player].setText(""+score);
	}
	public Object getScore(int player) {
		// TODO Auto-generated method stub
		return this.playerScores.get(player);
		
	}
	public void setName(int player, String name) {
		// TODO Auto-generated method stub
		this.playerNames.set(player, name);
		window.playerNames[player].setText(name);
	}
	public Object getName(int player) {
		// TODO Auto-generated method stub
		return this.playerNames.get(player);
		
	}
	public void playCard() {
		// TODO Auto-generated method stub
		System.out.println("played card "+this.window.lastCard+"");
		if(this.window.lastCard<this.playerCards.size())
			this.removeCard(this.window.lastCard+this.moved);
		//signal to the client
	}
	
	public String getNameFromPlayer(){
		return JOptionPane.showInputDialog("Enter your name");
	}
	
	public void setPlayerTurn(int i){
		
		this.currPlayer=i;
		
	}
	public void resetCards(){		
		int maxx=Math.min(10, playerCards.size());
		int i;
		for(i=0;i<maxx;i++){
			this.window.addPlayerCard(i, playerCards.get(i+moved).getCardImage());
		}
		for(i=i;i<10;i++){
			this.window.playerCards[i].setIcon(null);
		}
	}
	

	
}
