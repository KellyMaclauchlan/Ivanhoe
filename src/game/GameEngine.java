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
	private static ArrayList<Card> drawDeck;
	private ArrayList<Card> discardPile;
	private Player currentPlayer;
	private int turnNumber = 0;
	private boolean choosePurple = false;
	
	public GameEngine() {
		players = new ArrayList<>();
		discardPile = new ArrayList<>();
	}
	
	public String processInput(String input) {
		String output = "waiting";
			// start <number of players>
			if (input.contains(Config.START)) {
				output = processStart(input); // output = prompt join OR output = max 5 (if number of players is too high)
			// input = join <player name>
			} else if (input.contains(Config.JOIN)) {
				output = processJoin(input); // output = need players OR output = name <player name> cards <type_value> <type_value> ...
			// input = begin tournament	
			} else if (input.contains(Config.START_TOURNAMENT)) {	
				output = processStartTournament(input); // output = purple <player name> turn <player name> (first turn) <card picked up> 
														// OR output = turn <player name> <card picked up> (subsequent turns)
			// input = colour <colour>	
			} else if (input.contains(Config.COLOUR_PICKED)) {
				output = processColourPicked(input); // output = play <colour picked>
			// input = play red 4 (can be continued on input at a time for as many cards as available)
			} else if (input.contains(Config.PLAY)) {
				output = processPlay(input); // output = waiting <card played> OR output = waiting <unplayable>
			// input = end turn
			} else if (input.contains(Config.END_TURN)) {
				output = processEndTurn(); // output = <player name> points <player points> [continue OR withdraw] <next player> <card picked up>
												// IF tournament is won, add: tournament winner <winner name> 
												// OR IF tournament is won and tournamentColour is purple, add: purple win <winner name>  
												// IF game is won, add: game winner <winner name>
												
			// input = purple win <colour>
			} else if (input.contains(Config.PURPLE_WIN)) {
				output = processPurpleWin(input); // output = same as a normal tournament win of any colour
			// input = withdraw
			} else if (input.contains(Config.WITHDRAW)) {
				withdraw();				
				output = processEndTurn();	// see above: only change is that the player has chosen to withdraw instead of being forced
			}
		return output;
	}
	
	public String processStart(String input) {
		String output = "";

		String[] start = input.split(" ");
		numPlayers = Integer.valueOf(start[1]);
		if (numPlayers > Config.MAX_PLAYERS) {
			output = Config.MAX;
		}
		output = Config.PROMPT_JOIN;
		return output;
	}
	
	public String processJoin(String input) {
		String output = "";
		String name = input.replace("join ", "");
		Player player = new Player(name);
		joinGame(player);
		if (players.size() < numPlayers) 
			output = Config.NEED_PLAYERS;
		else if (players.size() == numPlayers) {
			//prompt first player to start their turn
			//pick tokens happens automatically 
			startGame();
			for (Player p: players) {
				output += " " + Config.PLAYER_NAME + " " + p.getName() + " " + Config.PLAYER_CARDS; 
				for (Card c: p.getCards()) {
					output += " " + c.getType() + "_" + c.getValue();
				}
			}					
		}
		return output;
	}
	
	public String processStartTournament(String input) {
		String output = "";
		Card picked = pickupCard();
		String purple = "";
		for (Player p: players) {
			if (p.getStartTokenColour() == Config.PURPLE) {
				purple = p.getName();
				output = Config.PURPLE + " " + purple + " " 
						+ Config.TURN + " " + currentPlayer.getName() 
						+ " " + picked.getType() + "_" + picked.getValue();
			} else {
				output = Config.TURN + " " + currentPlayer.getName()
				+ " " + picked.getType() + "_" + picked.getValue();
			}
		}
		return output;
	}
	
	public String processColourPicked(String input) {
		String output = "";
		String[] pick = input.split(" ");
		String colour = pick[1];
		currentPlayer.chooseTournamentColour(colour);
		startTurn();
		output = Config.PLAY + " " + colour;
		return output;
	}
	
	public String processPlay(String input) {
		String output = Config.WAITING;
		String[] play = input.split(" ");
		String type = play[1];
		String value = play[2];
		Card card = null;
		for (Card c: currentPlayer.getCards()) {
			if (type.equals(c.getType()) && value.equals(Integer.toString(c.getValue()))) {
				card = c;
			}
		}
		if (card.getType().equals(tournamentColour) 
				|| card.getCardType().equals(Config.SUPPORT)) {
			playCard(card);
			output += " " + type + "_" + value;
		} else if (card.getType().equals(Config.ACTION)) {
			output += processActionCard((ActionCard) card, input);
		} else {
			output += " " + Config.UNPLAYABLE;
		}
		return output; 
	}
	
	public String processActionCard(ActionCard card, String input) {
		String output = "";
		if (card.getType().equals(Config.UNHORSE)) {
			//TO DO: parse input to get colour
			if (tournamentColour.equals(Config.PURPLE)) {
				card.playUnhorse(this, input);
			}
		}
		return output;
	}

	public String processPurpleWin(String input) {
		String output = "";
		String[] purpleWin = input.split(" ");
		String chosenColour = purpleWin[2];
		if (chosenColour.equals(Config.PURPLE)) choosePurple = true;
		setTournamentColour(chosenColour);
		output = processEndTurn();
		return output;
	}
	
	public String processEndTurn() {
		String output = currentPlayer.getName() + " " + Config.POINTS + " " + currentPlayer.getTotalCardValue();
		Player prevPlayer = currentPlayer;
		endTurn();
		String withdraw = Config.CONTINUE;
		if (prevPlayer.hasWithdrawn()) {
			withdraw = Config.WITHDRAW;
		}
		output += " " + withdraw + " " + currentPlayer.getName();
		startTurn();
		String status = "";
		for (Player p: players) {
			if (p.isWinner() && tournamentColour.equals(Config.PURPLE) && choosePurple == false) {
				status = " " + Config.PURPLE_WIN + " " + p.getName();
				currentPlayer = p;
			}
			else if (p.isWinner() && (!tournamentColour.equals(Config.PURPLE) || choosePurple == true)) {
				arrangePlayers();
				resetPlayers();
				status = " " + Config.TOURNAMENT_WINNER + " " + p.getName();
				choosePurple = false;
			}
			if (p.isGameWinner()) {
				status += " " + Config.GAME_WINNER + " " + p.getName();
			}
		}
		if(status.equalsIgnoreCase("")){
			Card picked = pickupCard();
			status = " " + picked.getType() + "_" + picked.getValue();
		}
		output += status;
		return output;
	}
	
	public void joinGame(Player player) {
		players.add(player);
	}

	public void pickTokens() {
		//add all tokens to token array, starting with purple
		tokens = new ArrayList<String>();
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
	}
	
	public void startTurn() {
		turnNumber ++;
		if (turnNumber == 1) {
			tournamentColour = currentPlayer.getTournamentColour();
		} else if (currentPlayer.getPlayPossibilities(this).size() < 1) {
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
	
	public void discard(Player player, Card card) {
		player.removeCard(card);
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
		currentPlayer.setWinner(true);
		if ((((tournamentColour == Config.PURPLE) && choosePurple == true) || (tournamentColour != Config.PURPLE)) 
			&& (!currentPlayer.getCurrentTokens().contains(tournamentColour))) {
			currentPlayer.addToken(tournamentColour);
		}
		if ((numPlayers <= 3) && (currentPlayer.getCurrentTokens().size() == 5)) {
			currentPlayer.setGameWinner(true);
		} else if ((numPlayers >= 4) && (currentPlayer.getCurrentTokens().size() == 4)) {
			currentPlayer.setGameWinner(true);
		}
	}
	
	public void resetPlayers() {
		for (Player p: players) {
			p.setWithdrawn(false);
			p.setWinner(false);
			p.setDisplay(new ArrayList<Card>());
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

	public void createDeck() {
		drawDeck = new ArrayList<Card>();
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
		drawDeck.add(new ActionCard(Config.BREAKLANCE));
		drawDeck.add(new ActionCard(Config.RIPOSTE));
		drawDeck.add(new ActionCard(Config.RIPOSTE));
		drawDeck.add(new ActionCard(Config.RIPOSTE));
		drawDeck.add(new ActionCard(Config.DODGE));
		drawDeck.add(new ActionCard(Config.RETREAT));
		drawDeck.add(new ActionCard(Config.KNOCKDOWN));
		drawDeck.add(new ActionCard(Config.KNOCKDOWN));
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


}
