package game;

import java.util.ArrayList;

public class GameEngine {
	private int numPlayers;
	private String roundColour;
	private ArrayList<Player> players;
	private ArrayList<String> tokens;
	private ArrayList<Card> drawDeck;
	private ArrayList<Card> discardDeck;
	private Player currentPlayer;
 	
	public void joinGame(Player player) {
		//TO DO: Add new player to players array
	}
	
	public void pickTokens() {
		//TO DO: initialize array of tokens the size of the number of players, one purple and each other another colour
	}
	
	public void startGame() {
		//TO DO: randomize drawDeck array and deal out 8 cards to each player
		//TO DO: rearrange player array so that the player that picked purple is first and all others shift accordingly
		//
	}
	

	public void pickupCard(Player player) {
		//TO DO: randomly assign a new card to the selected player 
	}
	
	public void discard(Player player, Card card) {
		//TO DO: discard a specific card for a specific player, to the discardDeck
	}
	
	public void startTurn(Player player) {
		//TO DO: prompt player to begin turn
	}
	
	public void playCard(Player player, Card card) {
		//TO DO: play a specific card for a specific player, handle based on card rules
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setCurrentPlayer(Player currentPlayer) {
		//TO DO: Set currentPlayer to the correct player in order of game rules
		this.currentPlayer = currentPlayer;
	}
	
	public void endTurn(Player player) {
		//TO DO: end current player's turn and announce points, move to next player
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	public String getRoundColour() {
		return roundColour;
	}
	public void setRoundColour(String roundColour) {
		this.roundColour = roundColour;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}




}
