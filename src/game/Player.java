package game;

import java.util.ArrayList;
public class Player {
	
	private String name;
	private String startTokenColour;
	private ArrayList<Card> cards;
	private ArrayList<Card> display;
	private ArrayList<Card> front;
	private int totalCardValue;
	private boolean isStunned;
	private boolean isShielded;
	private boolean isWinner;
	private boolean hasWithdrawn;
	private ArrayList<String> currentTokens;
	private String tournamentColour;
	private boolean gameWinner = false;
	
	public Player(String name) {
		this.name = name;
		currentTokens = new ArrayList<>();
		display = new ArrayList<>();
		front = new ArrayList<>();
	}
	
	public void chooseTournamentColour(String colour) {
		this.tournamentColour = colour;
	}
	
	public String getTournamentColour() {
		return tournamentColour;
	}
	
	public ArrayList<String> getColourPossibilities() {
		//TO DO: determine which colours the player can choose based on the available cards in their deck;
		ArrayList<String> colours = new ArrayList<>();
		for (Card c: cards) {
			if (c.getCardType().equals("colour")) {
				colours.add(c.getType());
			}
		}
		return colours;
	}
	
	public ArrayList<Card> getPlayPossibilities(GameEngine game) {
		//TO DO: determine what cards can be played from hand based on tournament colour
		ArrayList<Card> playableCards = new ArrayList<>();
		for (Card c: cards) {
			if ((c.getType().equals(game.getTournamentColour())) 
					|| (c.getCardType().equals("support")) 
					|| (c.getCardType().equals("action"))) {
				playableCards.add(c);
			}
		}
		return playableCards;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartTokenColour() {
		return startTokenColour;
	}
	public void setStartTokenColour(String startTokenColour) {
		this.startTokenColour = startTokenColour;
	}
	public ArrayList<Card> getCards() {
		return cards;
	}
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	
	public void removeCard(Card card) {
		cards.remove(card);
	}
	
	public void addCard(Card card) {
		//Add a card to the current player's hand
		cards.add(card);
	}
	public ArrayList<Card> getDisplay() {
		return display;
	}

	public void addToDisplay(Card card) {
		display.add(card);
	}
	
	public void setDisplay(ArrayList<Card> display) {
		this.display = display;
	}
	
	public void removeFromDisplay(Card card) {
		display.remove(card);
	}

	public int getTotalCardValue() {
		return totalCardValue;
	}

	public void setTotalCardValue() {
		totalCardValue = 0;
		for (Card c: display) {
			if (c.getCardType().equals("colour")
					|| c.getCardType().equals("support"))
				totalCardValue += c.getValue();
		}
	}
	
	public void resetTotalCardValue() {
		totalCardValue = 0;
	}

	public boolean isStunned() {
		return isStunned;
	}

	public void setStunned(boolean isStunned) {
		this.isStunned = isStunned;
	}

	public boolean isShielded() {
		return isShielded;
	}

	public void setShielded(boolean isShielded) {
		this.isShielded = isShielded;
	}

	public ArrayList<Card> getFront() {
		return front;
	}

	public void setFront(ArrayList<Card> front) {
		this.front = front;
	}
	
	public void addToFront(Card card) {
		front.add(card);
		removeCard(card);
	}

	public boolean isWinner() {
		return isWinner;
	}

	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}

	public ArrayList<String> getCurrentTokens() {
		return currentTokens;
	}

	public void addToken(String token) {
		currentTokens.add(token);
	}

	public boolean hasWithdrawn() {
		return hasWithdrawn;
	}

	public void setWithdrawn(boolean hasWithdrawn) {
		this.hasWithdrawn = hasWithdrawn;
	}

	public boolean isGameWinner() {
		return gameWinner;
	}

	public void setGameWinner(boolean gameWinner) {
		this.gameWinner = gameWinner;
	}
	

}
