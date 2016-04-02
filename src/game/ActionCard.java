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
		for (Player p: game.getPlayers()) {
			for (Card c: p.getDisplay()) {
				c.setValue(1);
			}
			p.setTotalCardValue();
		}
	}
	
	public void playBreakLance(Player player) {
		// rid given player's display of all purple cards, leave one if they only have purple cards

		if (!player.hasShield()) {
			ArrayList<Card> cardsToRemove = new ArrayList<>();
			for (Card c: player.getDisplay()) {
				if (c.getType().equals(Config.PURPLE) && (player.getDisplay().size() > 1)) {
					cardsToRemove.add(c);
				}
			}
			for (Card c: cardsToRemove) {
				if (player.getDisplay().size() >1) {
					player.removeFromDisplay(c);
					player.setTotalCardValue();
				}
			}
		}
	}
	
	public Card playRiposte(Player player) {
		// take the last played card of given player and put it in display of current player
		Card cardToSteal = null;
		if (!player.hasShield()) {
			cardToSteal = player.getDisplay().get(player.getDisplay().size() - 1);
			System.out.println("STEALING: " + cardToSteal.getType());
			player.removeFromDisplay(cardToSteal);
			player.setTotalCardValue();
		}
		return cardToSteal;
	}

	public void playDodge(Player player, Card card) {
		// discard any one of the given player's displayed cards
		if (!player.hasShield()) {
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
		if ((!hasShield) && (player.getCards().size() >= 1)){
			cardToSteal = player.getCards().get(0);
			player.getCards().remove(0);
			game.getCurrentPlayer().addCard(cardToSteal);
		}
		return cardToSteal;
	}
	
	public void playOutmaneuver(GameEngine game) {
		// discard the last played card on each opponent's display
		for (Player p: game.getActionablePlayers()) {
			if (!p.getName().equals(game.getCurrentPlayer().getName()) && (p.getDisplay().size() > 1)) {
				Card cardToRemove = p.getDisplay().get(p.getDisplay().size() - 1);
				p.removeFromDisplay(cardToRemove);
				game.discard(cardToRemove);
				p.setTotalCardValue();
			}
		}
	}
	
	public void  playCharge(GameEngine game) {
		// discard the lowest value card from every player's display
		int lowestValue = 7;
		for (Player p: game.getPlayers()) {
			for (Card c: p.getDisplay()) {
				if (c.getValue() < lowestValue) {
					lowestValue = c.getValue();
				}
			}
		}
		for (Player p: game.getActionablePlayers()) {
			if (p.getDisplay().size() > 1) {
				for (int i = 0; i < p.getDisplay().size(); i++) {
					Card card = p.getDisplay().get(i);
					if (card.getValue() == lowestValue) {
						p.removeFromDisplay(card);
						game.discard(card);
					}
				}
				p.setTotalCardValue();
			}
		}
	}
	
	public void  playCounterCharge(GameEngine game) {
		// discard the highest value card from every player's display
		int highestValue = 0;
		for (Player p: game.getPlayers()) {
			for (Card c: p.getDisplay()) {
				if (c.getValue() > highestValue) {
					highestValue = c.getValue();
				}
			}
		}
		for (Player p: game.getActionablePlayers()) {
			if (p.getDisplay().size() > 1) {
				for (int i = 0; i < p.getDisplay().size(); i++) {
					Card card = p.getDisplay().get(i);
					if (card.getValue() == highestValue) {
						p.removeFromDisplay(card);
						game.discard(card);
					}
				}
				p.setTotalCardValue();
			}
		}
	}
	
	
	public void playDisgrace(GameEngine game) {
		// discard all supporters from every player's display
		for (Player p: game.getActionablePlayers()) {
				ArrayList<Card> cardsToDiscard = new ArrayList<>();
				for (Card c: p.getDisplay()) {
					if (c.getCardType().equals(Config.SUPPORT)) {
						cardsToDiscard.add(c);
					}
				} for (Card c: cardsToDiscard) {
					if (p.getDisplay().size() > 1) {
						p.removeFromDisplay(c);
						game.discard(c);
					}
				}
				p.setTotalCardValue();
		}
	}
	
	public boolean containsCard(Card card, ArrayList<Card> cards) {
		boolean containsCard = false;
		for (Card c: cards) {
			if (c.getType().equals(card.getType()) && (c.getValue() == card.getValue())) {
				containsCard = true;
				break;
			}
		}
		return containsCard;
	}
	
	public void playAdapt(GameEngine game) {
		// remove all duplicate cards from each player's display, leaving only one of each card
		for (Player p: game.getActionablePlayers()) {
					ArrayList<Card> cardsToKeep = new ArrayList<>();
				for (Card c: p.getDisplay()) {
					if (!containsCard(c,cardsToKeep)) {
						cardsToKeep.add(c);
					} else {
						game.discard(c);
					}
				}
				p.setDisplay(cardsToKeep);
				p.setTotalCardValue();
			
		}
		
	}
	
	public void playOutwit(GameEngine game, Player player, Card opponentCard, Card playerCard) {
		//Place one of your faceup cards in front of an opponent, and take one faceup
		//card from this opponent and place it face up in front of yourself. This may include the
		//SHIELD and STUNNED cards.
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
	
	public String promptIvanhoe(GameEngine game, Card card) {
		String output = " ";
		if (!card.getType().equals(Config.IVANHOE)) {
				for (Player p: game.getPlayers()) {
					if (!p.getName().equals(game.getCurrentPlayer().getName())) {
					for (Card c: p.getCards()) {
						if (c.getType().equals(Config.IVANHOE)) {
							output = " " + Config.PLAY_IVANHOE + " " + p.getName() + " " + card.getType();
							break;
						}
					}
				}
			}
		}
		return output;
	}
	
	
}
