package game;

import java.util.ArrayList;

public class GameEngine {
	private int numPlayers;
	private String tournamentColour;
	private ArrayList<Player> players;
	private ArrayList<String> tokens;
	private ArrayList<Card> drawDeck;
	private Player currentPlayer;
 	
	public void joinGame(Player player) {
		//TO DO: Add new player to players array
	}
	
	public void createDeck() {
		//TO DO: initialize the draw deck and add all necessary cards
	}
	public void pickTokens() {
		//TO DO: initialize array of tokens the size of the number of players, one purple and each other another colour
	}
	
	public void startGame() {
		//TO DO: randomize drawDeck array and deal out 8 cards to each player
		//TO DO: rearrange player array so that the player that picked purple is first and all others shift accordingly
		//TO DO: current player is set to player at 0
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
		//TO DO: end current player's turn and announce points, move to next player
		//OR: If the current player has less points than another player, call withdraw
	}
	
	public void withdraw() {
		//TO DO: withdraw the current player from the game
		// displayed cards are discarded
	}
	
	public void announceWinner() {
		//TO DO: if the current player is the last remaining, announce that they won
		// add tournament colour token to player's tokens
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
