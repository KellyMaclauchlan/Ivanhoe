package game;

import java.util.ArrayList;

import config.Config;
public class Player {
	

	protected String name;
	protected String startTokenColour;
	protected ArrayList<Card> cards;
	protected ArrayList<Card> display;
	protected ArrayList<Card> front;
	protected int totalCardValue;
	protected boolean isStunned;
	protected boolean isShielded;
	protected boolean isWinner;
	protected boolean hasWithdrawn;
	protected ArrayList<String> currentTokens;
	protected String tournamentColour;
	protected boolean gameWinner = false;
	
	public Player(String name) {
		this.name = name;
		currentTokens = new ArrayList<>();
		display = new ArrayList<>();
		front = new ArrayList<>();
	}
	
	public Card getCardFromHand(String type, int value) {
		Card card = null;
		for (int i = 0; i < cards.size(); i++) {
			if (type.equals(cards.get(i).getType()) && value == cards.get(i).getValue()) {
				card = cards.get(i);
				break;
			}
		}
		return card;
	}
	
	public Card getCardFromDisplay(String type, int value) {
		Card card = null;
		for (int i = 0; i < display.size(); i++) {
			if (type.equals(display.get(i).getType()) && value == display.get(i).getValue()) {
				card = display.get(i);
				break;
			}
		}
		return card;
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
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getType().equals(card.getType()) && (cards.get(i).getValue() == card.getValue())) { 
				cards.remove(i);
				break;
			}
		}
	}
	
	public void removeToken(String colour) {
		for (int i = 0; i < currentTokens.size(); i++) {
			if (currentTokens.get(i).equals(colour)) {
				currentTokens.remove(i);
			}
		}
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
		int i = 0;
		if (display.size() != 0) {
			for (i = 0; i < display.size(); i ++) {
				if (card.getType().equals(display.get(i).getType()) && (card.getValue() == display.get(i).getValue())) {
					break;
				}
			}
			if (i < display.size()) {
				display.remove(i);
			}
		}
	}
	
	public void removeFromFront(Card card) {
		int i = 0;
		if (front.size() != 0) {
			for (i = 0; i < front.size(); i ++) {
				if (card.getType().equals(front.get(i).getType()) && (card.getValue() == front.get(i).getValue())) {
					break;
				}
			}
			if (i < front.size()) {
				front.remove(i);
			}
		}
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

	public ArrayList<Card> getFront() {
		return front;
	}

	public void setFront(ArrayList<Card> front) {
		this.front = front;
	}
	
	public void addToFront(Card card) {
		front.add(card);
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
	
	public boolean hasShield() {
		boolean hasShield = false;
		for (Card c: front) {
			if (c.getType().equals(Config.SHIELD)) {
				hasShield = true;
				break;
			}
		}
		return hasShield;
	}
	
	public boolean isStunned() {
		boolean isStunned = false;
		for (Card c: front) {
			if (c.getType().equals(Config.STUNNED)) {
				isStunned = true;
				break;
			}
		}
		return isStunned;
	}
	

}
