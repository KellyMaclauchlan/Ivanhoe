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
	
	public void playUnhorse(GameEngine game, String colour) {
		// change tournament colour to red blue or yellow if it is currently purple
		game.setTournamentColour(colour);
	}
	
	public void playChangeWeapon(GameEngine game, String colour) {
		// change tournament colour to red blue or yellow if it is currently red blue or yellow
		game.setTournamentColour(colour);
	}
	
	public void playDropWeapon(GameEngine game) {
		// change tournament colour to green if it is currently red blue or yellow 
		game.setTournamentColour(Config.GREEN);
		
	}
	
	public void playBreakLance(Player player) {
		// rid given player's display of all purple cards, leave one if they only have purple cards
		boolean hasShield = false;
		for (Card c: player.getFront()) {
			if (c.getType().equals(Config.SHIELD)) {
				hasShield = true;
			}
		}
		if (!hasShield) {
			for (Card c: player.getDisplay()) {
				if (c.getType().equals(Config.PURPLE) && player.getDisplay().size() > 1) {
					player.removeFromDisplay(c);
				}
			}
		}
	}
	
	public Card playRiposte(Player player) {
		// take the last played card of given player and put it in deck of current player
		boolean hasShield = false;
		for (Card c: player.getFront()) {
			if (c.getType().equals(Config.SHIELD)) {
				hasShield = true;
			}
		}
		if (!hasShield) {
			Card cardToSteal = player.getDisplay().get(player.getDisplay().size() - 1);
			player.removeFromDisplay(cardToSteal);
			return cardToSteal;
		}
		return null;
	}

	public void playDodge(Player player, Card card) {
		// discard any one of the given player's displayed cards
		boolean hasShield = false;
		for (Card c: player.getFront()) {
			if (c.getType().equals(Config.SHIELD)) {
				hasShield = true;
			}
		}
		if (!hasShield) {
			player.removeFromDisplay(card);
		}
	}
	
	public void playRetreat(GameEngine game, Card card) {
		// take given card from display and place it back into hand
		game.getCurrentPlayer().removeFromDisplay(card);
		game.getCurrentPlayer().addCard(card);
	}
	
	public void playKnockDown(GameEngine game, Player player) {
		// draw a card at random from given opponent's hand
		boolean hasShield = false;
		for (Card c: player.getFront()) {
			if (c.getType().equals(Config.SHIELD)) {
				hasShield = true;
			}
		}
		if (!hasShield) {
			Card cardToSteal = player.getCards().get(0);
			player.getCards().remove(0);
			game.getCurrentPlayer().addCard(cardToSteal);
		}
	}
	
	public void playOutmaneuver(GameEngine game) {
		// discard the last played card on each opponent's display
		for (Player p: game.getActionablePlayers()) {
			Card cardToRemove = p.getCards().get(p.getCards().size() - 1);
			p.removeCard(cardToRemove);
			game.discard(p, cardToRemove);
		}
	}
	
	public void  playCharge(GameEngine game) {
		// discard the lowest value card from every opponent's display
		for (Player p: game.getActionablePlayers()) {
			if (!p.getName().equals(game.getCurrentPlayer().getName())) {
				Card cardToRemove = p.getDisplay().get(0);
				for (Card c: p.getDisplay()) {
					if (c.getValue() < cardToRemove.getValue()) {
						cardToRemove = c;
					}
				}
				p.removeCard(cardToRemove);
				game.discard(p, cardToRemove);
			}
		}
	}
	
	public void  playCounterCharge(GameEngine game) {
		// discard the highest value card from every opponent's display
		for (Player p: game.getActionablePlayers()) {
			if (!p.getName().equals(game.getCurrentPlayer().getName())) {
				Card cardToRemove = p.getDisplay().get(0);
				for (Card c: p.getDisplay()) {
					if (c.getValue() > cardToRemove.getValue()) {
						cardToRemove = c;
					}
				}
				p.removeCard(cardToRemove);
				game.discard(p, cardToRemove);
			}
		}
	}
	
	public void playDisgrace(GameEngine game) {
		// discard all supporters from every opponent's display
		for (Player p: game.getActionablePlayers()) {
			if (!p.getName().equals(game.getCurrentPlayer().getName())) {
				for (Card c: p.getDisplay()) {
					if (c.getCardType().equals(Config.SUPPORT)) {
						p.removeCard(c);
						game.discard(p, c);
					}
				}
			}
		}
	}
	
	public void playAdapt(GameEngine game) {
		// remove all duplicate cards from each opponent's display, leaving only one of each card
		for (Player p: game.getActionablePlayers()) {
			if (!p.getName().equals(game.getCurrentPlayer().getName())) {
				for (Card c: p.getDisplay()) {
					int duplicates = 0;
					for (Card c2: p.getDisplay()) {
						if (c.getType().equals(c2.getType()) && (c.getValue() == c2.getValue())) {
							duplicates ++;
							if (duplicates > 1) {
								p.removeCard(c2);
								game.discard(p, c);
							}
						}
					}
				}
			}
		}
		
	}
	
	public void playOutwit(Player player, Card opponentCard, Card playerCard) {
		//TO DO: take one shield or stunned card from in front of the given opponent, and place it in front of current player
		//take one shield or stunned card from the current player and put it in front of the opponent
	}
	
	public void playShield(GameEngine game, Card card) {
		// Place shield card in front of current player and block opponent action cards from having effect
		game.getCurrentPlayer().addToFront(card);
	}
	
	public void playStunned(Player player, Card card) {
		//place stunned card in front of the given opponent and enable the opponent to only play one card at a time
		player.addToFront(card);
	}
	
	public void playIvanhoe(Card card) {
		//TO DO: cancel effects of any one card just played
	}
	
	
}
