package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameEngine {
	private int numPlayers;
	private String tournamentColour;
	private ArrayList<Player> players = new ArrayList<>();
	private ArrayList<String> tokens;
	private ArrayList<Card> drawDeck;
	private ArrayList<Card> discardPile = new ArrayList<>();
	private Player currentPlayer;
	
	public void createDeck() {
		drawDeck = new ArrayList<Card>();
		//purple
		drawDeck.add(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		drawDeck.add(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		drawDeck.add(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		drawDeck.add(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		drawDeck.add(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		drawDeck.add(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		drawDeck.add(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		drawDeck.add(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		drawDeck.add(new ColourCard("purple", 5,"resources/cards_small/simpleCards16.jpg"));
		drawDeck.add(new ColourCard("purple", 5,"resources/cards_small/simpleCards16.jpg"));
		drawDeck.add(new ColourCard("purple", 5,"resources/cards_small/simpleCards16.jpg"));
		drawDeck.add(new ColourCard("purple", 5,"resources/cards_small/simpleCards16.jpg"));
		drawDeck.add(new ColourCard("purple", 7,"resources/cards_small/simpleCards17.jpg"));
		drawDeck.add(new ColourCard("purple", 7,"resources/cards_small/simpleCards17.jpg"));
		
		//red
		drawDeck.add(new ColourCard("red", 3,"resources/cards_small/simpleCards7.jpg"));
		drawDeck.add(new ColourCard("red", 3,"resources/cards_small/simpleCards7.jpg"));
		drawDeck.add(new ColourCard("red", 3,"resources/cards_small/simpleCards7.jpg"));
		drawDeck.add(new ColourCard("red", 3,"resources/cards_small/simpleCards7.jpg"));
		drawDeck.add(new ColourCard("red", 3,"resources/cards_small/simpleCards7.jpg"));
		drawDeck.add(new ColourCard("red", 3,"resources/cards_small/simpleCards7.jpg"));
		drawDeck.add(new ColourCard("red", 4,"resources/cards_small/simpleCards8.jpg"));
		drawDeck.add(new ColourCard("red", 4,"resources/cards_small/simpleCards8.jpg"));
		drawDeck.add(new ColourCard("red", 4,"resources/cards_small/simpleCards8.jpg"));
		drawDeck.add(new ColourCard("red", 4,"resources/cards_small/simpleCards8.jpg"));
		drawDeck.add(new ColourCard("red", 4,"resources/cards_small/simpleCards8.jpg"));
		drawDeck.add(new ColourCard("red", 4,"resources/cards_small/simpleCards8.jpg"));
		drawDeck.add(new ColourCard("red", 5,"resources/cards_small/simpleCards9.jpg"));
		drawDeck.add(new ColourCard("red", 5,"resources/cards_small/simpleCards9.jpg"));

		//blue
		drawDeck.add(new ColourCard("blue", 2,"resources/cards_small/simpleCards10.jpg"));
		drawDeck.add(new ColourCard("blue", 2,"resources/cards_small/simpleCards10.jpg"));
		drawDeck.add(new ColourCard("blue", 2,"resources/cards_small/simpleCards10.jpg"));
		drawDeck.add(new ColourCard("blue", 2,"resources/cards_small/simpleCards10.jpg"));
		drawDeck.add(new ColourCard("blue", 3,"resources/cards_small/simpleCards11.jpg"));
		drawDeck.add(new ColourCard("blue", 3,"resources/cards_small/simpleCards11.jpg"));
		drawDeck.add(new ColourCard("blue", 3,"resources/cards_small/simpleCards11.jpg"));
		drawDeck.add(new ColourCard("blue", 3,"resources/cards_small/simpleCards11.jpg"));
		drawDeck.add(new ColourCard("blue", 4,"resources/cards_small/simpleCards12.jpg"));
		drawDeck.add(new ColourCard("blue", 4,"resources/cards_small/simpleCards12.jpg"));
		drawDeck.add(new ColourCard("blue", 4,"resources/cards_small/simpleCards12.jpg"));
		drawDeck.add(new ColourCard("blue", 4,"resources/cards_small/simpleCards12.jpg"));
		drawDeck.add(new ColourCard("blue", 5,"resources/cards_small/simpleCards13.jpg"));
		drawDeck.add(new ColourCard("blue", 5,"resources/cards_small/simpleCards13.jpg"));

		//yellow
		drawDeck.add(new ColourCard("yellow", 2,"resources/cards_small/simpleCards4.jpg"));
		drawDeck.add(new ColourCard("yellow", 2,"resources/cards_small/simpleCards4.jpg"));
		drawDeck.add(new ColourCard("yellow", 2,"resources/cards_small/simpleCards4.jpg"));
		drawDeck.add(new ColourCard("yellow", 2,"resources/cards_small/simpleCards4.jpg"));
		drawDeck.add(new ColourCard("yellow", 3,"resources/cards_small/simpleCards5.jpg"));
		drawDeck.add(new ColourCard("yellow", 3,"resources/cards_small/simpleCards5.jpg"));
		drawDeck.add(new ColourCard("yellow", 3,"resources/cards_small/simpleCards5.jpg"));
		drawDeck.add(new ColourCard("yellow", 3,"resources/cards_small/simpleCards5.jpg"));
		drawDeck.add(new ColourCard("yellow", 3,"resources/cards_small/simpleCards5.jpg"));
		drawDeck.add(new ColourCard("yellow", 3,"resources/cards_small/simpleCards5.jpg"));
		drawDeck.add(new ColourCard("yellow", 3,"resources/cards_small/simpleCards5.jpg"));
		drawDeck.add(new ColourCard("yellow", 3,"resources/cards_small/simpleCards5.jpg"));
		drawDeck.add(new ColourCard("yellow", 4,"resources/cards_small/simpleCards6.jpg"));
		drawDeck.add(new ColourCard("yellow", 4,"resources/cards_small/simpleCards6.jpg"));
		
		//green
		drawDeck.add(new ColourCard("green", 1,"resources/cards_small/simpleCards3.jpg"));
		drawDeck.add(new ColourCard("green", 1,"resources/cards_small/simpleCards3.jpg"));
		drawDeck.add(new ColourCard("green", 1,"resources/cards_small/simpleCards3.jpg"));
		drawDeck.add(new ColourCard("green", 1,"resources/cards_small/simpleCards3.jpg"));
		drawDeck.add(new ColourCard("green", 1,"resources/cards_small/simpleCards3.jpg"));
		drawDeck.add(new ColourCard("green", 1,"resources/cards_small/simpleCards3.jpg"));
		drawDeck.add(new ColourCard("green", 1,"resources/cards_small/simpleCards3.jpg"));
		drawDeck.add(new ColourCard("green", 1,"resources/cards_small/simpleCards3.jpg"));
		drawDeck.add(new ColourCard("green", 1,"resources/cards_small/simpleCards3.jpg"));
		drawDeck.add(new ColourCard("green", 1,"resources/cards_small/simpleCards3.jpg"));
		drawDeck.add(new ColourCard("green", 1,"resources/cards_small/simpleCards3.jpg"));
		drawDeck.add(new ColourCard("green", 1,"resources/cards_small/simpleCards3.jpg"));
		drawDeck.add(new ColourCard("green", 1,"resources/cards_small/simpleCards3.jpg"));
		drawDeck.add(new ColourCard("green", 1,"resources/cards_small/simpleCards3.jpg"));

		//supporters
		drawDeck.add(new SupportCard("maiden", 6,"resources/cards_small/simpleCards2.jpg"));
		drawDeck.add(new SupportCard("maiden", 6,"resources/cards_small/simpleCards2.jpg"));
		drawDeck.add(new SupportCard("maiden", 6,"resources/cards_small/simpleCards2.jpg"));
		drawDeck.add(new SupportCard("maiden", 6,"resources/cards_small/simpleCards2.jpg"));
		drawDeck.add(new SupportCard("squire", 2,"resources/cards_small/simpleCards.jpg"));
		drawDeck.add(new SupportCard("squire", 2,"resources/cards_small/simpleCards.jpg"));
		drawDeck.add(new SupportCard("squire", 2,"resources/cards_small/simpleCards.jpg"));
		drawDeck.add(new SupportCard("squire", 2,"resources/cards_small/simpleCards.jpg"));
		drawDeck.add(new SupportCard("squire", 2,"resources/cards_small/simpleCards.jpg"));
		drawDeck.add(new SupportCard("squire", 2,"resources/cards_small/simpleCards.jpg"));
		drawDeck.add(new SupportCard("squire", 2,"resources/cards_small/simpleCards.jpg"));
		drawDeck.add(new SupportCard("squire", 2,"resources/cards_small/simpleCards.jpg"));
		drawDeck.add(new SupportCard("squire", 3,"resources/cards_small/simpleCards1.jpg"));
		drawDeck.add(new SupportCard("squire", 3,"resources/cards_small/simpleCards1.jpg"));
		drawDeck.add(new SupportCard("squire", 3,"resources/cards_small/simpleCards1.jpg"));
		drawDeck.add(new SupportCard("squire", 3,"resources/cards_small/simpleCards1.jpg"));
		drawDeck.add(new SupportCard("squire", 3,"resources/cards_small/simpleCards1.jpg"));
		drawDeck.add(new SupportCard("squire", 3,"resources/cards_small/simpleCards1.jpg"));
		drawDeck.add(new SupportCard("squire", 3,"resources/cards_small/simpleCards1.jpg"));
		drawDeck.add(new SupportCard("squire", 3,"resources/cards_small/simpleCards1.jpg"));

		//action
		drawDeck.add(new ActionCard("unhorse"));
		drawDeck.add(new ActionCard("changeweapon"));
		drawDeck.add(new ActionCard("dropweapon"));
		drawDeck.add(new ActionCard("breaklance"));
		drawDeck.add(new ActionCard("riposte"));
		drawDeck.add(new ActionCard("riposte"));
		drawDeck.add(new ActionCard("riposte"));
		drawDeck.add(new ActionCard("dodge"));
		drawDeck.add(new ActionCard("retreat"));
		drawDeck.add(new ActionCard("knockdown"));
		drawDeck.add(new ActionCard("knockdown"));
		drawDeck.add(new ActionCard("outmaneuver"));
		drawDeck.add(new ActionCard("charge"));
		drawDeck.add(new ActionCard("countercharge"));
		drawDeck.add(new ActionCard("disgrace"));
		drawDeck.add(new ActionCard("adapt"));
		drawDeck.add(new ActionCard("outwit"));
		drawDeck.add(new ActionCard("shield"));
		drawDeck.add(new ActionCard("stunned"));
		drawDeck.add(new ActionCard("ivanhoe"));
	}
	
	public void joinGame(Player player) {
		players.add(player);
	}

	public void pickTokens() {
		//add all tokens to token array, starting with purple
		tokens = new ArrayList<String>();
		tokens.add("purple");
		tokens.add("red");
		tokens.add("blue");
		tokens.add("yellow");
		tokens.add("green");
		
		//remove last token in tokens array as long as the number of tokens is greater than the number of players
		for (int i = 0; i < tokens.size(); i++) {
			if (tokens.size() > players.size()) {
				tokens.remove(tokens.size()-1);
			}
		}
		
		//pick a random token for each player, and remove that token from the array
		Random random = new Random();
		for (Player p: players) {
			int index = random.nextInt(tokens.size());
			String token = tokens.get(index);
			p.setStartTokenColour(token);
			tokens.remove(index);
		}	
	}
	
	public void startGame() {
		numPlayers = players.size();
		//randomly pick a token for each player
		pickTokens();
		
		//arrange players in the correct order, starting with the player left of the one that chose purple
		//and continuing with the person left of that one, and so on - in this case, next in the array of players
		arrangePlayers();
		
		//create the drawDeck
		createDeck();
		
		//shuffle drawDeck (twice to be safe)
		Collections.shuffle(drawDeck);
		Collections.shuffle(drawDeck);

		//for each player, create a hand, add the first 8 cards to the hand and remove them from the drawDeck
		for (Player p: players) {
			ArrayList<Card> hand = new ArrayList<>();
			hand.add(drawDeck.get(0));
			drawDeck.remove(0);
			hand.add(drawDeck.get(0));
			drawDeck.remove(0);			
			hand.add(drawDeck.get(0));
			drawDeck.remove(0);
			hand.add(drawDeck.get(0));
			drawDeck.remove(0);
			hand.add(drawDeck.get(0));
			drawDeck.remove(0);
			hand.add(drawDeck.get(0));
			drawDeck.remove(0);
			hand.add(drawDeck.get(0));
			drawDeck.remove(0);
			hand.add(drawDeck.get(0));
			drawDeck.remove(0);

			//set the current player's hand to the one created
			p.setCards(hand);
		}

		currentPlayer = players.get(0);
	}
	
	
	public void arrangePlayers() {
		//arrange players list so that the player left of the one that drew purple
				ArrayList<Player> tempPlayers = players;
				for (int i = 1; i < numPlayers; i++) {
					if ((tempPlayers.get(i-1).getStartTokenColour().equals("purple"))
							|| (tempPlayers.get(i).isWinner()) || (i == numPlayers)) {
						players.set(0, tempPlayers.get(i));
						for (int j = 1; j <= numPlayers; j++) {
							i++;
							if (i < tempPlayers.size()) {
								players.set(j, tempPlayers.get(i));
							} else {
								for (int k = 0; k < numPlayers - j; k++) {
									players.set(j, tempPlayers.get(k));
									j++;
								}
							}
						}
					}
					tempPlayers.get(i-1).setStartTokenColour("nil");
				}
	}
	
	public void discard(Player player, Card card) {
		//TO DO: discard a specific card for a specific player, to the discardDeck
	}
	
	public void startTurn() {
		//TO DO: prompt current player to begin turn
		//OR: if the current player does not have any playable cards, withdraw
		//OR: if it is the first turn of the game, prompt the first player to pick a tournament colour
		//OR: if the player is the only one left, call announceWinner
	}
	
	
	public void playCard(Card card) {
		currentPlayer.addToDisplay(card);
		currentPlayer.removeCard(card);
		currentPlayer.setTotalCardValue();
		//TO DO: play a specific card for current player, handle based on card rules
		//remove card from player hand
		//add card to where it should be added (display, front, discard)
		//
	}
	
	public void pickupCard() {
		//TO DO: Current player picks up top card
	}
	
	public void removeCardfromDeck(Card card) {
		//TO DO: remove a single card from draw deck (mostly for testing)
	}
	
	public void removeAllFromDeck(ArrayList<Card> cards) {
		//TO DO: remove a number of cards from the draw deck (mostly for testing after cards are dealt)
	}
	
	public void addAllToDeck(ArrayList<Card> cards) {
		//TO DO: add a number of cards to the draw deck (mostly for testing after cards are dealt)
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setCurrentPlayer(Player currentPlayer) {
		//TO DO: Set currentPlayer to the correct player in order of game rules
		this.currentPlayer = currentPlayer;
	}
	
	public void endTurn() {
		System.out.println("Player total value: " + currentPlayer.getTotalCardValue());
		for (Player p: players) {
			if (currentPlayer.getTotalCardValue() < p.getTotalCardValue()) {
				withdraw();
			}
		}
		currentPlayer = getNext();
	}
	
	private Player getNext() {
		int index = 0;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i) == currentPlayer) {
				if (i == players.size()-1) {
					index = 0;
				} else {
					index = i + 1;
				}
			}
		}
		return players.get(index);
	}

	public void withdraw() {
		currentPlayer.setWithdrawn(true);
		for (Card c: currentPlayer.getCards()) {
			discardPile.add(c);
			currentPlayer.removeFromDisplay(c);
		}
		currentPlayer.setDisplay(new ArrayList<Card>());
	}
	
	public void announceWinner() {
		//TO DO: if the current player is the last remaining, announce that they won
		//TO DO: add tournament colour token to player's tokens
		//TO DO: if there is a winner of the entire game, announce that they won the game
		//TO DO: if there is no game winner, arrange the player array so that the tournament winner
		//       is first to play in the next tournament, and all other players arranged in their usual order accordingly
		//TO DO: set all players to not withdrawn
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	public String getTournamentColour() {
		return tournamentColour;
	}
	public void setTournamentColour(String tournamentColour) {
		this.tournamentColour = tournamentColour;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public ArrayList<Card> getDrawDeck() {
		return drawDeck;
	}

	public void setDrawDeck(ArrayList<Card> drawDeck) {
		this.drawDeck = drawDeck;
	}




}
