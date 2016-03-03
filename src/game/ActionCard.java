package game;

import config.Config;

public class ActionCard extends Card {

	public ActionCard(String actionType) {
		this.setType(actionType);
		setCardType(Config.ACTION);
	}
	public ActionCard(String actionType,String cardimg) {
		this.setType(actionType);
		setCardType(Config.ACTION);
		this.setCardImage(cardimg);
	}
	
	public void playUnhorse(String colour) {
		//TO DO: change tournament colour to red blue or yellow if it is currently purple 
	}
	
	public void playChangeWeapon(String colour) {
		//TO DO: change tournament colour to red blue or yellow if it is currently red blue or yellow
	}
	
	public void playDropWeapon() {
		//TO DO: change tournament colour to green if it is currently red blue or yellow 
	}
	
	public void playBreakLance(Player player) {
		//TO DO: rid given player of all purple cards, leave one if they only have purple cards
	}
	
	public void playRiposte(Player player) {
		//TO DO: take the last played card of given player and put it in deck of current player
	}

	public void playDodge(Player player) {
		//TO DO: discard any one of the given player's cards
	}
	
	public void playRetreat(Card card) {
		//TO DO: take given card from display and place it back into hand
	}
	
	public void playKnockDown(Player player) {
		//TO DO: draw a card at random from given opponent's hand
	}
	
	public void playOutmaneuver() {
		//TO DO: discard the last played card on each opponent's display
	}
	
	public void  playCharge() {
		//TO DO: discard the lowest value card from every opponent's display
	}
	
	public void  playCounterCharge() {
		//TO DO: discard the highest value card from every opponent's display
	}
	
	public void playDisgrace() {
		//TO DO: discard all supporters from every opponent's display
	}
	
	public void playAdapt() {
		//TO DO: remove all duplicate cards from each opponent's hand, leaving only one of each card
	}
	
	public void playOutwit(Player player, Card opponentCard, Card playerCard) {
		//TO DO: take one shield or stunned card from in front of the given opponent, and place it in front of current player
		//take one shield or stunned card from the current player and put it in front of the opponent
	}
	
	public void playShield() {
		//TO DO: Place shield card in front of current player and block opponent action cards from having effect
	}
	
	public void playStunned(Player player) {
		//TO DO: place stunned card in front of the given opponent and enable the opponent to only play one card at a time
	}
	
	public void playIvanhoe(Card card) {
		//TO DO: cancel effects of any one card just played
	}
	
	
}
