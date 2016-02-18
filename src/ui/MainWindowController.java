package ui;

import java.util.ArrayList;

import config.Config;
import config.Observer;
import game.Card;

public class MainWindowController implements Observer{
	private ArrayList<Card> playerCards;
	private int playerNum;
	private static Config config;
	public MainWindow window;
	public String lastMessege;
	public MainWindowController(){
		playerCards= new ArrayList<Card>();
		config=new Config();
		window = new MainWindow();
		window.registerObserver(this);
		
		
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
	
	
}
