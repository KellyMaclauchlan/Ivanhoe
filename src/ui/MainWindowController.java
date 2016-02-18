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
	public MainWindow window;
	public String lastMessege;
	public MainWindowController(){
		playerCards= new ArrayList<Card>();
		config=new Config();
		window = new MainWindow();
		window.registerObserver(this);
		playedCards= new ArrayList<ArrayList<Card>>();
		
	}
	public void showWindow(){
		window.setVisible(true);
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
		}
	}
	public void addPlayedCard(int player, Card card){
		
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
		return null;
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
	public void SetScore(int i, int j) {
		// TODO Auto-generated method stub
		
	}
	public Object getScore(int i) {
		// TODO Auto-generated method stub
		return null;
	}
	public void SetName(int i, String string) {
		// TODO Auto-generated method stub
		
	}
	public Object getName(int i) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
