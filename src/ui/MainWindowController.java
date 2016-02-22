package ui;

import java.util.ArrayList;

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
	private int moved;
	public MainWindow window;
	public String lastMessege;
	public MainWindowController(){
		playerCards= new ArrayList<Card>();
		config=new Config();
		window = new MainWindow();
		window.registerObserver(this);
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
	}
	public void removeCard(int i){
		playerCards.remove(i);
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
			
		}
		
	}
	private void endturnClick() {
		// TODO Auto-generated method stub
		System.out.println("endTurn click");
	}

	private void withdrawClick() {
		// TODO Auto-generated method stub
		System.out.println("withdraw click");
	}

	private void rightClick() {
		// TODO Auto-generated method stub
		System.out.println("right click");
	}

	public void leftClick(){
		System.out.println("left click");
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
		
	}
	public void SetScore(int player, int score) {
		// TODO Auto-generated method stub
		this.playerScores.set(player, score);		
	}
	public Object getScore(int player) {
		// TODO Auto-generated method stub
		return this.playerScores.get(player);
		
	}
	public void SetName(int player, String name) {
		// TODO Auto-generated method stub
		this.playerNames.set(player, name);
	}
	public Object getName(int player) {
		// TODO Auto-generated method stub
		return this.playerNames.get(player);
		
	}
	public void playCard() {
		// TODO Auto-generated method stub
		System.out.println("played card "+this.window.lastCard+"");
		this.removeCard(this.window.lastCard+moved);
		//signal to the client
	}
	
	
}
