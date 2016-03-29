package game;

import java.util.ArrayList;

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
			ArrayList<Card> cardsToRemove = new ArrayList<>();
			for (Card c: player.getDisplay()) {
				if (c.getType().equals(Config.PURPLE) && player.getDisplay().size() > 1) {
					cardsToRemove.add(c);
				}
			}
			for (Card c: cardsToRemove) {
				player.removeFromDisplay(c);
				player.setTotalCardValue();
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
			player.setTotalCardValue();
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
	
	public Card playKnockDown(GameEngine game, Player player) {
		// draw a card at random from given opponent's hand
		Card cardToSteal = null;
		boolean hasShield = false;
		for (Card c: player.getFront()) {
			if (c.getType().equals(Config.SHIELD)) {
				hasShield = true;
			}
		}
		if (!hasShield) {
			cardToSteal = player.getCards().get(0);
			player.getCards().remove(0);
			game.getCurrentPlayer().addCard(cardToSteal);
		}
		return cardToSteal;
	}
	
	public void playOutmaneuver(GameEngine game) {
		// discard the last played card on each opponent's display
		for (Player p: game.getActionablePlayers()) {
			if (!p.getName().equals(game.getCurrentPlayer().getName())) {
				Card cardToRemove = p.getDisplay().get(p.getDisplay().size() - 1);
				p.removeFromDisplay(cardToRemove);
				game.discard(cardToRemove);
				p.setTotalCardValue();
			}
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
				p.removeFromDisplay(cardToRemove);
				game.discard(cardToRemove);
				p.setTotalCardValue();
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
				p.removeFromDisplay(cardToRemove);
				game.discard(cardToRemove);
				p.setTotalCardValue();
			}
		}
	}
	
	public void playDisgrace(GameEngine game) {
		// discard all supporters from every opponent's display
		for (Player p: game.getActionablePlayers()) {
			if (!p.getName().equals(game.getCurrentPlayer().getName())) {
				ArrayList<Card> cardsToDiscard = new ArrayList<>();
				for (Card c: p.getDisplay()) {
					if (c.getCardType().equals(Config.SUPPORT)) {
						cardsToDiscard.add(c);
					}
				} for (Card c: cardsToDiscard) {
					p.removeFromDisplay(c);
					game.discard(c);
				}
				p.setTotalCardValue();
			}
		}
	}
	
	public void playAdapt(GameEngine game) {
		// remove all duplicate cards from each opponent's display, leaving only one of each card
		for (Player p: game.getActionablePlayers()) {
			if (!p.getName().equals(game.getCurrentPlayer().getName())) {
					ArrayList<Card> cardsToKeep = new ArrayList<>();
				for (Card c: p.getDisplay()) {
					if (!cardsToKeep.contains(c)) {
						cardsToKeep.add(c);
					}
				}
				p.setDisplay(cardsToKeep);
				p.setTotalCardValue();
			}
		}
		
	}
	
	public void playOutwit(GameEngine game, Player player, Card opponentCard, Card playerCard) {
		//TO DO: take one shield or stunned card from in front of the given opponent, and place it in front of current player
		//take one shield or stunned card from the current player and put it in front of the opponent
		Card opponentCardToSwap = null;
		Card playerCardToSwap = null;
		for (Card c: player.getDisplay()) {
			if  (c.getType().equals(opponentCard.getType()) && (c.getValue() == opponentCard.getValue())) {
				opponentCardToSwap = c;
			}
		}
		for (Card c: game.getCurrentPlayer().getDisplay()) {
			if  (c.getType().equals(playerCard.getType()) && (c.getValue() == playerCard.getValue())) {
				playerCardToSwap = c;
			}
		}
		for (Card c: player.getFront()) {
			if  (c.getType().equals(opponentCard.getType()) && (c.getValue() == opponentCard.getValue())) {
				opponentCardToSwap = c;
			}
		}
		for (Card c: game.getCurrentPlayer().getFront()) {
			if  (c.getType().equals(playerCard.getType()) && (c.getValue() == playerCard.getValue())) {
				playerCardToSwap = c;
			}
		}
		
		if (opponentCardToSwap.getType().equals(Config.SHIELD) || opponentCardToSwap.getType().equals(Config.STUNNED)) {
			player.removeFromFront(opponentCardToSwap);
			game.getCurrentPlayer().addToFront(opponentCardToSwap);
		} else {
			player.removeFromDisplay(opponentCardToSwap);
			game.getCurrentPlayer().addToDisplay(opponentCardToSwap);
		} 
		if (playerCardToSwap.getType().equals(Config.SHIELD) || playerCardToSwap.getType().equals(Config.STUNNED)) {
			game.getCurrentPlayer().removeFromFront(playerCardToSwap);
			player.addToFront(playerCardToSwap);
		} else {
			game.getCurrentPlayer().removeFromDisplay(playerCardToSwap);
			player.addToDisplay(playerCardToSwap);
		}
		game.getCurrentPlayer().setTotalCardValue();
		player.setTotalCardValue();
		
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
