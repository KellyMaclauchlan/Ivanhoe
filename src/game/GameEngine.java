package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameEngine {
	private int numPlayers;
	private String tournamentColour;
	private ArrayList<Player> players;
	private ArrayList<String> tokens;
	private static ArrayList<Card> drawDeck;
	private ArrayList<Card> discardPile;
	private Player currentPlayer;
	private int turnNumber = 0;
	
	public GameEngine() {
		players = new ArrayList<>();
		discardPile = new ArrayList<>();
	}
	
	public void createDeck() {
		drawDeck = new ArrayList<Card>();
		//purple
		drawDeck.add(new ColourCard("purple", 3));
		drawDeck.add(new ColourCard("purple", 3));
		drawDeck.add(new ColourCard("purple", 3));
		drawDeck.add(new ColourCard("purple", 3));
		drawDeck.add(new ColourCard("purple", 4));
		drawDeck.add(new ColourCard("purple", 4));
		drawDeck.add(new ColourCard("purple", 4));
		drawDeck.add(new ColourCard("purple", 4));
		drawDeck.add(new ColourCard("purple", 5));
		drawDeck.add(new ColourCard("purple", 5));
		drawDeck.add(new ColourCard("purple", 5));
		drawDeck.add(new ColourCard("purple", 5));
		drawDeck.add(new ColourCard("purple", 7));
		drawDeck.add(new ColourCard("purple", 7));
		
		//red
		drawDeck.add(new ColourCard("red", 3));
		drawDeck.add(new ColourCard("red", 3));
		drawDeck.add(new ColourCard("red", 3));
		drawDeck.add(new ColourCard("red", 3));
		drawDeck.add(new ColourCard("red", 3));
		drawDeck.add(new ColourCard("red", 3));
		drawDeck.add(new ColourCard("red", 4));
		drawDeck.add(new ColourCard("red", 4));
		drawDeck.add(new ColourCard("red", 4));
		drawDeck.add(new ColourCard("red", 4));
		drawDeck.add(new ColourCard("red", 4));
		drawDeck.add(new ColourCard("red", 4));
		drawDeck.add(new ColourCard("red", 5));
		drawDeck.add(new ColourCard("red", 5));

		//blue
		drawDeck.add(new ColourCard("blue", 2));
		drawDeck.add(new ColourCard("blue", 2));
		drawDeck.add(new ColourCard("blue", 2));
		drawDeck.add(new ColourCard("blue", 2));
		drawDeck.add(new ColourCard("blue", 3));
		drawDeck.add(new ColourCard("blue", 3));
		drawDeck.add(new ColourCard("blue", 3));
		drawDeck.add(new ColourCard("blue", 3));
		drawDeck.add(new ColourCard("blue", 4));
		drawDeck.add(new ColourCard("blue", 4));
		drawDeck.add(new ColourCard("blue", 4));
		drawDeck.add(new ColourCard("blue", 4));
		drawDeck.add(new ColourCard("blue", 5));
		drawDeck.add(new ColourCard("blue", 5));

		//yellow
		drawDeck.add(new ColourCard("yellow", 2));
		drawDeck.add(new ColourCard("yellow", 2));
		drawDeck.add(new ColourCard("yellow", 2));
		drawDeck.add(new ColourCard("yellow", 2));
		drawDeck.add(new ColourCard("yellow", 3));
		drawDeck.add(new ColourCard("yellow", 3));
		drawDeck.add(new ColourCard("yellow", 3));
		drawDeck.add(new ColourCard("yellow", 3));
		drawDeck.add(new ColourCard("yellow", 3));
		drawDeck.add(new ColourCard("yellow", 3));
		drawDeck.add(new ColourCard("yellow", 3));
		drawDeck.add(new ColourCard("yellow", 3));
		drawDeck.add(new ColourCard("yellow", 4));
		drawDeck.add(new ColourCard("yellow", 4));
		
		//green
		drawDeck.add(new ColourCard("green", 1));
		drawDeck.add(new ColourCard("green", 1));
		drawDeck.add(new ColourCard("green", 1));
		drawDeck.add(new ColourCard("green", 1));
		drawDeck.add(new ColourCard("green", 1));
		drawDeck.add(new ColourCard("green", 1));
		drawDeck.add(new ColourCard("green", 1));
		drawDeck.add(new ColourCard("green", 1));
		drawDeck.add(new ColourCard("green", 1));
		drawDeck.add(new ColourCard("green", 1));
		drawDeck.add(new ColourCard("green", 1));
		drawDeck.add(new ColourCard("green", 1));
		drawDeck.add(new ColourCard("green", 1));
		drawDeck.add(new ColourCard("green", 1));

		//supporters
		drawDeck.add(new SupportCard("maiden", 6));
		drawDeck.add(new SupportCard("maiden", 6));
		drawDeck.add(new SupportCard("maiden", 6));
		drawDeck.add(new SupportCard("maiden", 6));
		drawDeck.add(new SupportCard("squire", 2));
		drawDeck.add(new SupportCard("squire", 2));
		drawDeck.add(new SupportCard("squire", 2));
		drawDeck.add(new SupportCard("squire", 2));
		drawDeck.add(new SupportCard("squire", 2));
		drawDeck.add(new SupportCard("squire", 2));
		drawDeck.add(new SupportCard("squire", 2));
		drawDeck.add(new SupportCard("squire", 2));
		drawDeck.add(new SupportCard("squire", 3));
		drawDeck.add(new SupportCard("squire", 3));
		drawDeck.add(new SupportCard("squire", 3));
		drawDeck.add(new SupportCard("squire", 3));
		drawDeck.add(new SupportCard("squire", 3));
		drawDeck.add(new SupportCard("squire", 3));
		drawDeck.add(new SupportCard("squire", 3));
		drawDeck.add(new SupportCard("squire", 3));

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

	}
	
	public void arrangePlayers() {
		//arrange players list so that the player left of the one that drew purple
				ArrayList<Player> tempPlayers = players;
				for (int i = 0; i < numPlayers; i++) {
					if ((i == 0 && tempPlayers.get(tempPlayers.size() - 1).getStartTokenColour().equals("purple")) ||
							(i != 0 && (tempPlayers.get(i-1).getStartTokenColour().equals("purple")))
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
//					tempPlayers.get(i-1).setStartTokenColour("nil");
				}
				currentPlayer = players.get(0);
	}
	
	public void startTurn() {
		//TO DO: prompt current player to begin turn
		turnNumber ++;
		//if it is the first turn of the game, prompt the first player to pick a tournament colour
		if (turnNumber > 1) {
			//TO DO: Prompt user to choose a tournament colour
			
		} else if (currentPlayer.getPlayPossibilities(this).size() < 1) {
			//if the current player does not have any playable cards, withdraw
			withdraw();
		} else {
			int playersLeft = 0;
			for (Player p: players) {
				if (!p.hasWithdrawn())
					playersLeft ++;
			}
			if (playersLeft == 1) {
				//if the player is the only one left, call announceWinner
				announceWinner();
			}
		}
	}
	
	public void discard(Player player, Card card) {
		// discard a specific card for a specific player, to the discardPile
		player.removeCard(card);
		discardPile.add(card);
	}
	
	public void playCard(Card card) {
		// play a specific card for current player, handle based on card rules
		currentPlayer.addToDisplay(card);
		//remove card from player hand
		currentPlayer.removeCard(card);
		//add card to where it should be added (display, front, discard)
		currentPlayer.setTotalCardValue();
	}
	
	public void pickupCard() {
		//current player picks up top card
		currentPlayer.addCard(drawDeck.get(0));
		drawDeck.remove(0);
	}
	
	public void removeCardfromDeck(Card card) {
		//remove a single card from draw deck (mostly for testing)
		drawDeck.remove(card);
	}
	
	public void removeAllFromDeck(ArrayList<Card> cards) {
		// remove a number of cards from the draw deck (mostly for testing after cards are dealt)
		int temp = 0;
		for (Card c: cards) {
			System.out.print("\n Card c: " + c.getType() + " " + c.getValue());
			temp++;
			for (int i = 0; i < drawDeck.size(); i++) {
				if (c.getType().equals(drawDeck.get(i).getType()) && (c.getValue() == drawDeck.get(i).getValue())) {
					System.out.println("\n Removed Card number " + temp + ": " + drawDeck.get(i).getType() + " " + drawDeck.get(i).getValue());
					drawDeck.remove(i);
					break;
				}
			}
		}
	}
	
	public void addAllToDeck(ArrayList<Card> cards) {
		//add a number of cards to the draw deck (mostly for testing after cards are dealt)
		for (Card c: cards) {
				drawDeck.add(c);
		}
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
		// if the current player is the last remaining set them to "winner"
		currentPlayer.setWinner(true);
		//TO DO: add tournament colour token to player's tokens
		if (tournamentColour == "purple") {
			//TO DO: prompt user to choose a token colour 
		} else if (!currentPlayer.getCurrentTokens().contains(tournamentColour)){
			currentPlayer.addToken(tournamentColour);
		}
		if ((numPlayers <= 3) && (currentPlayer.getCurrentTokens().size() == 5)) {
			//TO DO: Announce winner of whole game
		} else if ((numPlayers >= 4) && (currentPlayer.getCurrentTokens().size() == 4)) {
			//TO DO: Announce winner of whole game
		} else {
			//if there is no game winner, arrange the player array so that the tournament winner
			//is first to play in the next tournament, and all other players arranged in their usual order accordingly
			arrangePlayers();
			//Prepare players for a new tournament
			resetPlayers();
		}
	}
	
	public void resetPlayers() {
		for (Player p: players) {
			p.setWithdrawn(false);
			p.setWinner(false);
			p.setDisplay(new ArrayList<>());
			p.setStartTokenColour("nil");
		}
		turnNumber = 0;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setCurrentPlayer(Player currentPlayer) {
		//TO DO: Set currentPlayer to the correct player in order of game rules
		this.currentPlayer = currentPlayer;
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
