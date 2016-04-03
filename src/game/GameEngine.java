	package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import config.Config;

public class GameEngine {
	private int numPlayers;
	private String tournamentColour;
	private ArrayList<Player> players;
	private ArrayList<String> tokens;
	private ArrayList<Card> drawDeck;
	private ArrayList<Card> discardPile;
	private Player currentPlayer;
	private int turnNumber = 0;
	private boolean choosePurple = false; 
		
	/* Testing variables */
	private boolean startTournament = false; 
	private boolean joined = false;
	private String supporters = "supporters";
	private boolean token = false; 
	private String round = "round";
	private boolean testCurrPlayer = false; 
	
	/* Testing functions */
	public boolean getStart(){return isStartTournament();}
	public boolean getJoined(){return joined;}
	public String getSupporters(){return supporters;}
	public boolean getToken(){return token;}
	public String getRound(){return round;}
	public boolean checkCurrPlayer(){return testCurrPlayer;}
	public ArrayList<Card> getDiscardPile() {return discardPile;}
	
	public GameEngine() {
		players = new ArrayList<>();
		discardPile = new ArrayList<>();
	}

	public ArrayList<Player> getActionablePlayers() {
		// get players that can be affected by action cards
		ArrayList<Player> actionable = new ArrayList<>();
		for (Player p: players) {
			actionable.add(p);
		}
		for (int i = 0; i < actionable.size(); i++) {
			for (Card c: actionable.get(i).getFront()) {
				if (c.getType().equals(Config.SHIELD)) {
					actionable.remove(i);
					break;
				}
			}
		}
		return actionable;
	}
	public void joinGame(Player player) {
		players.add(player);
		joined = true;
	}

	public void pickTokens() {
		//add all tokens to token array, starting with purple
		tokens = new ArrayList<>();
		tokens.add(Config.PURPLE);
		tokens.add(Config.RED);
		tokens.add(Config.BLUE);
		tokens.add(Config.YELLOW);
		tokens.add(Config.GREEN);
		
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
		//randomly pick a token for each player
		pickTokens();
		//arange players according to picked token
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
				ArrayList<Player> tempPlayers = new ArrayList<Player>();
				for (Player p: players) {
					tempPlayers.add(p);
				}
				for (int i = 0; i < numPlayers; i++) {
					if ((i == 0 && tempPlayers.get(tempPlayers.size() - 1).getStartTokenColour().equals(Config.PURPLE)) ||
							(i != 0 && (tempPlayers.get(i-1).getStartTokenColour().equals(Config.PURPLE)))
							|| (tempPlayers.get(i).isWinner())) {
						players.set(0, tempPlayers.get(i));
						int firstPlayer = i;
						for (int j = 1; j < numPlayers; j++) {
							i++;
							if (i < tempPlayers.size()) {
								players.set(j, tempPlayers.get(i));
							} else {
								for (int k = 0; k < firstPlayer; k++) {
									players.set(j, tempPlayers.get(k));
								}
							}
						}
					}
				}
				currentPlayer = players.get(0);
				testCurrPlayer = true;
	}
	
	public void startTurn() {
		turnNumber ++;
		if (turnNumber == 1) {
			tournamentColour = currentPlayer.getTournamentColour();
			int cardsToPlay = 0;
			for (Card c: currentPlayer.getPlayPossibilities(this)) {
				if (!c.getCardType().equals(Config.ACTION)) {
					cardsToPlay++;
				}
				if (cardsToPlay == 0) {
					withdraw();
				}
			}
		} else if (currentPlayer.getPlayPossibilities(this).isEmpty()) {
			withdraw();
		} 
		int playersLeft = 0;
		for (Player p: players) {
			if (!p.hasWithdrawn())
				playersLeft ++;
		}
		if (playersLeft == 1) {
			announceWinner();
		}
	}
	
	public void discard(Card card) {
		discardPile.add(card);
	}
	
	
	public void playCard(Card card) {
		// play a specific card for current player, handle based on card rules
		if (card.getCardType().equals(Config.COLOUR) || card.getCardType().equals(Config.SUPPORT)) {
			currentPlayer.addToDisplay(card);
		}
		//remove card from player hand
		currentPlayer.removeCard(card);
		//add card to where it should be added (display, front, discard)
		currentPlayer.setTotalCardValue();
	}
	
	public Card pickupCard() {
		//current player picks up top card
		if (drawDeck.isEmpty()) {
			drawDeck = discardPile;
			Collections.shuffle(drawDeck);
			Collections.shuffle(drawDeck);
			discardPile = new ArrayList<Card>();
		}
		Card picked = drawDeck.get(0);
		currentPlayer.addCard(picked);
		drawDeck.remove(0);
		return picked;
	}
	
	public void removeCardfromDeck(Card card) {
		//remove a single card from draw deck (mostly for testing)
		for (int i = 0; i < drawDeck.size(); i++) {
			if (card.getType().equals(drawDeck.get(i).getType()) && (card.getValue() == drawDeck.get(i).getValue())) {
				drawDeck.remove(i);
				break;
			}
		}	}
	
	public void removeAllFromDeck(ArrayList<Card> cards) {
		// remove a number of cards from the draw deck (mostly for testing after cards are dealt)
		for (Card c: cards) {
			for (int i = 0; i < drawDeck.size(); i++) {
				if (c.getType().equals(drawDeck.get(i).getType()) && (c.getValue() == drawDeck.get(i).getValue())) {
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
		for (Player p: players) {
			if ((currentPlayer.getTotalCardValue() <= p.getTotalCardValue()) 
					&& (!currentPlayer.getName().equals(p.getName()))) {
				withdraw();
			}
		}
		currentPlayer = getNext();
	}
	
	public Player getNext() {
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
		if (currentPlayer.getCards().size() != 0) {
			for (Card c: currentPlayer.getCards()) {
				discardPile.add(c);
				currentPlayer.removeFromDisplay(c);
			}
			currentPlayer.setDisplay(new ArrayList<Card>());
		}
	}
	
	public void announceWinner() {
		Player winner = currentPlayer;
		currentPlayer.setWinner(true);
		if (tournamentColour != null) {
			if ((chosePurple() && (!currentPlayer.getCurrentTokens().contains(Config.PURPLE))) || (!tournamentColour.equals(Config.PURPLE))
				&& (!currentPlayer.getCurrentTokens().contains(tournamentColour))) {
				currentPlayer.addToken(tournamentColour);
				setChoosePurple(false);
			}
		}
		if ((numPlayers <= 3) && (winner.getCurrentTokens().size() == 5)) {
			winner.setGameWinner(true);
		} else if ((numPlayers >= 4) && (winner.getCurrentTokens().size() == 4)) {
			winner.setGameWinner(true);
		}

	}
	
	public void resetPlayers() {
		for (Player p: players) {
			p.setWithdrawn(false);
			p.setWinner(false);
			p.setDisplay(new ArrayList<Card>());
			p.setStartTokenColour("nil");
			p.resetTotalCardValue();
			p.setFront(new ArrayList<Card>());
		}
		turnNumber = 0;
	}
	
	public Player getPlayerByName(String name) {
		Player player = null;
		for (Player p: players) {
			if (p.getName().equals(name)) {
				player = p;
			}
		}
		return player;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setCurrentPlayer(Player currentPlayer) {
		//Set currentPlayer to the correct player in order of game rules
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

	public void createDeck() {
		drawDeck = new ArrayList<>();
		//purple
		drawDeck.add(new ColourCard(Config.PURPLE, 3));
		drawDeck.add(new ColourCard(Config.PURPLE, 3));
		drawDeck.add(new ColourCard(Config.PURPLE, 3));
		drawDeck.add(new ColourCard(Config.PURPLE, 3));
		drawDeck.add(new ColourCard(Config.PURPLE, 4));
		drawDeck.add(new ColourCard(Config.PURPLE, 4));
		drawDeck.add(new ColourCard(Config.PURPLE, 4));
		drawDeck.add(new ColourCard(Config.PURPLE, 4));
		drawDeck.add(new ColourCard(Config.PURPLE, 5));
		drawDeck.add(new ColourCard(Config.PURPLE, 5));
		drawDeck.add(new ColourCard(Config.PURPLE, 5));
		drawDeck.add(new ColourCard(Config.PURPLE, 5));
		drawDeck.add(new ColourCard(Config.PURPLE, 7));
		drawDeck.add(new ColourCard(Config.PURPLE, 7));
		
		//red
		drawDeck.add(new ColourCard(Config.RED, 3));
		drawDeck.add(new ColourCard(Config.RED, 3));
		drawDeck.add(new ColourCard(Config.RED, 3));
		drawDeck.add(new ColourCard(Config.RED, 3));
		drawDeck.add(new ColourCard(Config.RED, 3));
		drawDeck.add(new ColourCard(Config.RED, 3));
		drawDeck.add(new ColourCard(Config.RED, 4));
		drawDeck.add(new ColourCard(Config.RED, 4));
		drawDeck.add(new ColourCard(Config.RED, 4));
		drawDeck.add(new ColourCard(Config.RED, 4));
		drawDeck.add(new ColourCard(Config.RED, 4));
		drawDeck.add(new ColourCard(Config.RED, 4));
		drawDeck.add(new ColourCard(Config.RED, 5));
		drawDeck.add(new ColourCard(Config.RED, 5));

		//blue
		drawDeck.add(new ColourCard(Config.BLUE, 2));
		drawDeck.add(new ColourCard(Config.BLUE, 2));
		drawDeck.add(new ColourCard(Config.BLUE, 2));
		drawDeck.add(new ColourCard(Config.BLUE, 2));
		drawDeck.add(new ColourCard(Config.BLUE, 3));
		drawDeck.add(new ColourCard(Config.BLUE, 3));
		drawDeck.add(new ColourCard(Config.BLUE, 3));
		drawDeck.add(new ColourCard(Config.BLUE, 3));
		drawDeck.add(new ColourCard(Config.BLUE, 4));
		drawDeck.add(new ColourCard(Config.BLUE, 4));
		drawDeck.add(new ColourCard(Config.BLUE, 4));
		drawDeck.add(new ColourCard(Config.BLUE, 4));
		drawDeck.add(new ColourCard(Config.BLUE, 5));
		drawDeck.add(new ColourCard(Config.BLUE, 5));

		//yellow
		drawDeck.add(new ColourCard(Config.YELLOW, 2));
		drawDeck.add(new ColourCard(Config.YELLOW, 2));
		drawDeck.add(new ColourCard(Config.YELLOW, 2));
		drawDeck.add(new ColourCard(Config.YELLOW, 2));
		drawDeck.add(new ColourCard(Config.YELLOW, 3));
		drawDeck.add(new ColourCard(Config.YELLOW, 3));
		drawDeck.add(new ColourCard(Config.YELLOW, 3));
		drawDeck.add(new ColourCard(Config.YELLOW, 3));
		drawDeck.add(new ColourCard(Config.YELLOW, 3));
		drawDeck.add(new ColourCard(Config.YELLOW, 3));
		drawDeck.add(new ColourCard(Config.YELLOW, 3));
		drawDeck.add(new ColourCard(Config.YELLOW, 3));
		drawDeck.add(new ColourCard(Config.YELLOW, 4));
		drawDeck.add(new ColourCard(Config.YELLOW, 4));
		
		//green
		drawDeck.add(new ColourCard(Config.GREEN, 1));
		drawDeck.add(new ColourCard(Config.GREEN, 1));
		drawDeck.add(new ColourCard(Config.GREEN, 1));
		drawDeck.add(new ColourCard(Config.GREEN, 1));
		drawDeck.add(new ColourCard(Config.GREEN, 1));
		drawDeck.add(new ColourCard(Config.GREEN, 1));
		drawDeck.add(new ColourCard(Config.GREEN, 1));
		drawDeck.add(new ColourCard(Config.GREEN, 1));
		drawDeck.add(new ColourCard(Config.GREEN, 1));
		drawDeck.add(new ColourCard(Config.GREEN, 1));
		drawDeck.add(new ColourCard(Config.GREEN, 1));
		drawDeck.add(new ColourCard(Config.GREEN, 1));
		drawDeck.add(new ColourCard(Config.GREEN, 1));
		drawDeck.add(new ColourCard(Config.GREEN, 1));

		//supporters
		drawDeck.add(new SupportCard(Config.MAIDEN, 6));
		drawDeck.add(new SupportCard(Config.MAIDEN, 6));
		drawDeck.add(new SupportCard(Config.MAIDEN, 6));
		drawDeck.add(new SupportCard(Config.MAIDEN, 6));
		drawDeck.add(new SupportCard(Config.SQUIRE, 2));
		drawDeck.add(new SupportCard(Config.SQUIRE, 2));
		drawDeck.add(new SupportCard(Config.SQUIRE, 2));
		drawDeck.add(new SupportCard(Config.SQUIRE, 2));
		drawDeck.add(new SupportCard(Config.SQUIRE, 2));
		drawDeck.add(new SupportCard(Config.SQUIRE, 2));
		drawDeck.add(new SupportCard(Config.SQUIRE, 2));
		drawDeck.add(new SupportCard(Config.SQUIRE, 2));
		drawDeck.add(new SupportCard(Config.SQUIRE, 3));
		drawDeck.add(new SupportCard(Config.SQUIRE, 3));
		drawDeck.add(new SupportCard(Config.SQUIRE, 3));
		drawDeck.add(new SupportCard(Config.SQUIRE, 3));
		drawDeck.add(new SupportCard(Config.SQUIRE, 3));
		drawDeck.add(new SupportCard(Config.SQUIRE, 3));
		drawDeck.add(new SupportCard(Config.SQUIRE, 3));
		drawDeck.add(new SupportCard(Config.SQUIRE, 3));

		//action
		drawDeck.add(new ActionCard(Config.UNHORSE));
		drawDeck.add(new ActionCard(Config.CHANGEWEAPON));
		drawDeck.add(new ActionCard(Config.DROPWEAPON));
		
		drawDeck.add(new ActionCard(Config.RIPOSTE));
		drawDeck.add(new ActionCard(Config.RIPOSTE));
		drawDeck.add(new ActionCard(Config.RIPOSTE));
		drawDeck.add(new ActionCard(Config.DODGE));
		drawDeck.add(new ActionCard(Config.RETREAT));
		drawDeck.add(new ActionCard(Config.KNOCKDOWN));
		drawDeck.add(new ActionCard(Config.KNOCKDOWN));
		drawDeck.add(new ActionCard(Config.BREAKLANCE));	
		drawDeck.add(new ActionCard(Config.OUTMANEUVER));
		drawDeck.add(new ActionCard(Config.CHARGE));
		drawDeck.add(new ActionCard(Config.COUNTERCHARGE));
		drawDeck.add(new ActionCard(Config.DISGRACE));
		drawDeck.add(new ActionCard(Config.ADAPT));
		drawDeck.add(new ActionCard(Config.OUTWIT));
		drawDeck.add(new ActionCard(Config.SHIELD));
		drawDeck.add(new ActionCard(Config.STUNNED));
		drawDeck.add(new ActionCard(Config.IVANHOE));

	}
	public boolean isStartTournament() {
		return startTournament;
	}
	public void setStartTournament(boolean startTournament) {
		this.startTournament = startTournament;
	}
	public boolean chosePurple() {
		return choosePurple;
	}
	public void setChoosePurple(boolean choosePurple) {
		this.choosePurple = choosePurple;
	}


}
