package ui;

import java.util.ArrayList;

import game.Card;

public class MainWindowController {
	private ArrayList<Card> playerCards;
	private int playerNum;
	public MainWindowController(){
		playerCards= new ArrayList<Card>();
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
	
	
}
