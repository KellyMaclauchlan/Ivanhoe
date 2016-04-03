package game;

import config.Config;

public class GameProcessor {

	private GameEngine game = new GameEngine();
	private String actionCard;

	// For testing purposes only
	public GameEngine getGame() {return game;}
	
	public void processQuit(String input) {
		String[] quitInput = input.split(" ");
		String playerName = quitInput[1];
		for (int i = 0; i < game.getPlayers().size(); i++) {
			if (playerName.equals(game.getPlayers().get(i))) {
				game.getPlayers().remove(i);
			}
		}
	}
	
	public String processWithdraw(String input) {
		String output = Config.INPUT;
		for (Card c: game.getCurrentPlayer().getDisplay()) {
			if (c.getType().equals(Config.MAIDEN)) {
				output = Config.MAIDEN;
				game.getCurrentPlayer().removeFromDisplay(c);
				return output;
			}
		}
		if (!input.equals(Config.WITHDRAW)) {
			String[] colourInput = input.split(" ");
			if (colourInput[1].equals(game.getCurrentPlayer().getName())) {
				game.withdraw();	
				output = processEndTurn();
			}
			else {
				for (Player p: game.getPlayers()) {
					if (p.getName().equals(colourInput[1])) {
						return output;
					} 
				}
				String colour = colourInput[1];
				game.getCurrentPlayer().removeToken(colour);
				output = Config.MAIDEN + " " + colour;
			}
		}
		else {
			game.withdraw();	
			output = processEndTurn();
		}
		return output;
	}
	
	public String processStart(String input) {
		String output;

		String[] start = input.split(" ");
		game.setNumPlayers(Integer.valueOf(start[1]));
		if (game.getNumPlayers() > Config.MAX_PLAYERS) {
			output = Config.MAX;
		} else {
			output = Config.PROMPT_JOIN;
		}
		return output;
	}
	
	public String processJoin(String input) {
		String output = "";
		String name = input.replace("join ", "");
		Player player = new Player(name);
		game.joinGame(player);
		if (game.getPlayers().size() < game.getNumPlayers()) 
			output = Config.NEED_PLAYERS;
		else if (game.getPlayers().size() == game.getNumPlayers()) {
			//prompt first player to start their turn
			//pick tokens happens automatically 
			game.startGame();
			
			/* BRIT!!!!!!!!!!!!!!!!!!!!!!! */
			
			for(Player p : game.getPlayers()){
				System.out.println("Player Name: " + p.getName());
			}
			
			output += Config.HAND + " ";
			for (Player p: game.getPlayers()) {
				output += " " + Config.PLAYER_NAME + " " + p.getName() + " " + Config.PLAYER_CARDS; 
				for (Card c: p.getCards()) {
					output += " " + c.getType() + "_" + c.getValue();
				}
			}					
		}
		return output;
	}
	
	public String processStartTournament() {
		String output = "";
		Card picked = game.pickupCard();
		String purple;
		int nonAction = 0;
		for (Card c: game.getCurrentPlayer().getCards()) {
			if (!c.getCardType().equals(Config.ACTION)) {
				nonAction ++;
			}
		}
		if (nonAction == 0) {
			game.setCurrentPlayer(game.getNext());
		}
		for (Player p: game.getPlayers()) {
			if (p.getStartTokenColour() == Config.PURPLE) {
				purple = p.getName();
				output = Config.PICKED_PURPLE + " " + purple + " " 
						+ Config.TURN + " " + game.getCurrentPlayer().getName() 
						+ " " + picked.getType() + "_" + picked.getValue();
			} else {
				output = Config.TURN + " " + game.getCurrentPlayer().getName()
				+ " " + picked.getType() + "_" + picked.getValue();
			}
		}
		game.setStartTournament(true);
		return output;
	}
	
	public String processColourPicked(String input) {
		String output;
		String[] pick = input.split(" ");
		String colour = pick[1];
		game.getCurrentPlayer().chooseTournamentColour(colour);
		game.startTurn();
		output = Config.COLOUR + " " + colour;
		return output;
	}
	
	public String processPlay(String input) {
		String output = Config.WAITING;
		String[] play = input.split(" ");
		String type = play[1];
		String value = "0";
		if ((play.length > 2)) {
			value = play[2];
		} else if (input.contains(Config.IVANHOE_DECLINED) && (play.length == 2)) {
			output = processPlay(actionCard + " " + Config.IVANHOE_DECLINED);
		}
		Card card = null;
		boolean hasMaiden = false;
		if (type.equals(Config.IVANHOE)) {
			Player player = null;
			for (Player p: game.getPlayers()) {
				for (Card c: p.getCards()) {
					if (c.getType().equals(type)) {
						card = c;
						player = p;
					}
				}
			}
			player.removeCard(card);
		} else {
			for (Card c: game.getCurrentPlayer().getCards()) {
				if (type.equals(c.getType()) && value.equals(Integer.toString(c.getValue()))
						|| (type.equals(c.getType()) && c.getCardType().equals(Config.ACTION))) {
					card = c;
				}
			}
		}
		if ((card != null) && game.getTournamentColour() != null) {
		if (card.getType().equals(game.getTournamentColour()) 
				|| card.getCardType().equals(Config.SUPPORT)) {
			if (game.getTournamentColour().equals(Config.GREEN) && card.getValue() > 1) {
				card.setValue(1);
			}
			
			if (card.getType().equals(Config.MAIDEN)) {
					for (Card c: game.getCurrentPlayer().getDisplay()) {
						if (c.getType().equals(Config.MAIDEN)) {
							hasMaiden = true;
							output += " " + Config.UNPLAYABLE;
						}
					}
				}
			if (!hasMaiden) {
				game.playCard(card);
				if (game.getCurrentPlayer().isStunned()) {
						output = Config.IS_STUNNED;
				}
				output += " " + type + "_" + value;
			}
		} else if (card.getCardType().equals(Config.ACTION)) {
			output += processActionCard((ActionCard) card, input);
			actionCard = input;			
		} else {
			output += " " + Config.UNPLAYABLE;
		}
		}
		output.replace("  ", " ");
		output.trim();
		return output; 
	}
	
 	public String processPurpleWin(String input) {
		String output;
		String[] purpleWin = input.split(" ");
		String chosenColour = purpleWin[2];
		if (chosenColour.equals(Config.PURPLE)) {
			game.setChoosePurple(true);;
		}
		game.setTournamentColour(chosenColour);
		output = processEndTurn();
		return output;
	}
	
	public String processEndTurn() {
		String output = game.getCurrentPlayer().getName() + " " + Config.POINTS + " " + game.getCurrentPlayer().getTotalCardValue();
		Player prevPlayer = game.getCurrentPlayer();
		game.endTurn();
		String withdraw = Config.CONTINUE;
		if (prevPlayer.hasWithdrawn()) {
			withdraw = Config.WITHDRAW;
		}
		output += " " + withdraw + " " + game.getCurrentPlayer().getName();
		game.startTurn();
		String status = null;
		if (game.getTournamentColour() != null) {
			for (Player p: game.getPlayers()) {
				if (p.isWinner() && game.getTournamentColour().equals(Config.PURPLE) && !game.chosePurple()) {
					status = " " + Config.PURPLE_WIN + " " + p.getName();
					game.setCurrentPlayer(p);;
					p.resetTotalCardValue();
				}
				else if (p.isWinner() && (!game.getTournamentColour().equals(Config.PURPLE) || game.chosePurple())) {
					game.setCurrentPlayer(p);;
					game.announceWinner();
					game.arrangePlayers();
					game.resetPlayers();
					status = " " + game.getTournamentColour() + " " + Config.TOURNAMENT_WINNER + " " + p.getName();
					game.setCurrentPlayer(p);;
				}
				if (p.isGameWinner()) {
					output = " " + Config.GAME_WINNER + " " + p.getName();
					game.setCurrentPlayer(p);;
				}
			}
		}
		if (status == null){
			Card picked = game.pickupCard();
			status = " " + picked.getType() + "_" + picked.getValue();
		}
		output += status;
		return output;
	}
	
	public String processActionCard(ActionCard card, String input) {
		String output = " ";
		if (input.contains(Config.IVANHOE_DECLINED)) {
			output = " ";
		} else {
			output = card.promptIvanhoe(game, card);
		}		
		if (output.equals(" ")) {
			if (card.getType().equals(Config.UNHORSE)) {
				output += processUnhorse(card, input);
			} else if (card.getType().equals(Config.CHANGEWEAPON)) {
					output += processChangeWeapon(card, input); //output = waiting <card played> <colour chosen>
				} else if (card.getType().equals(Config.DROPWEAPON)) {
					output += processDropWeapon(card, input); //output = waiting <card played> green
				} else if (card.getType().equals(Config.BREAKLANCE)) {
					output += processBreaklance(card, input);
				} else if (card.getType().equals(Config.RIPOSTE)) {
					output += processRiposte(card, input);
				} else if (card.getType().equals(Config.DODGE)) {
					output += processDodge(card, input);
				} else if (card.getType().equals(Config.RETREAT)) {
					output += processRetreat(card, input);
				} else if (card.getType().equals(Config.KNOCKDOWN)) {
					output += processKnockdown(card, input);
				} else if (card.getType().equals(Config.OUTMANEUVER)) {
					card.playOutmaneuver(game);
					output += Config.OUTMANEUVER + " " + updateDisplays();
				} else if (card.getType().equals(Config.CHARGE)) {
					card.playCharge(game);
					output += Config.CHARGE + " " + updateDisplays();
				} else if (card.getType().equals(Config.COUNTERCHARGE)) {
					card.playCounterCharge(game);
					output += Config.COUNTERCHARGE + " " + updateDisplays();
				} else if (card.getType().equals(Config.DISGRACE)) {
					card.playDisgrace(game);
					output += Config.DISGRACE + " "  + updateDisplays();
				} else if (card.getType().equals(Config.ADAPT)) {
					card.playAdapt(game);
					output +=  Config.ADAPT + " " + updateDisplays();
				} else if (card.getType().equals(Config.OUTWIT)) {
					output += processOutwit(card, input);
				} else if (card.getType().equals(Config.SHIELD)) {
					card.playShield(game, card);
					output += Config.SHIELD + " " + game.getCurrentPlayer().getName();
				} else if (card.getType().equals(Config.STUNNED)) {
					output += processStunned(card, input);
				} else if (card.getType().equals(Config.IVANHOE)) {
					output += processIvanhoe(card, input);
				}
			if (!(output.contains(Config.UNPLAYABLE))) {
				game.getCurrentPlayer().removeCard(card);
				if (!card.getType().equals(Config.SHIELD) && !card.getType().equals(Config.STUNNED)) {
					game.discard(card);
				}
			}
		}
		return output;
	}
	
	public String processUnhorse(ActionCard card, String input) {
		String output = "";
		String[] cardString = input.split(" ");
		String colour = cardString[2];
		if (game.getTournamentColour().equals(Config.PURPLE)) { 
			card.playUnhorse(game, colour);		
			output += Config.UNHORSE + " " + colour; //output = waiting <card played> <colour chosen>
		} else {
			output += Config.UNPLAYABLE;
		}
		return output;
	}
	
	public String processChangeWeapon(ActionCard card, String input) {
		String output = "";
		String[] cardString = input.split(" ");
		String colour = cardString[2];
		if (game.getTournamentColour().equals(Config.RED) 
				|| game.getTournamentColour().equals(Config.BLUE) 
				|| game.getTournamentColour().equals(Config.YELLOW)) {
			card.playChangeWeapon(game, colour);
		}
		output += Config.CHANGEWEAPON + " " + colour;
		return output;
	}
	
	public String processDropWeapon(ActionCard card, String input) {
		String output = "";
		if (game.getTournamentColour().equals(Config.RED) 
				|| game.getTournamentColour().equals(Config.BLUE) 
				|| game.getTournamentColour().equals(Config.YELLOW)) {
			card.playDropWeapon(game);
		}
		output += Config.DROPWEAPON + " " + Config.GREEN;
		return output;
	}
	
	public String processBreaklance(ActionCard card, String input) {
		String output = "";
		String[] cardString = input.split(" ");
		String playerName = cardString[2];
		Player player = game.getPlayerByName(playerName);
		if ((player.getDisplay().size() < 2) || player.hasShield()) {
			output += Config.UNPLAYABLE;
		} else {
			card.playBreakLance(player);
			output += Config.BREAKLANCE + " ";
			output += Config.DISPLAY + " ";
			output += Config.PLAYER_NAME + " " + playerName + " " + player.getTotalCardValue() + " " + Config.PLAYER_CARDS + " ";

				for (Card c: player.getDisplay()) {
					output += c.getType() + " " + c.getValue(); 
				}
		}
		return output;
	}
	
	public String processRiposte(ActionCard card, String input) {
		String output = "";
		String[] cardString = input.split(" ");
		String playerName = cardString[2];
		Player player = game.getPlayerByName(playerName);
		if ((player.getDisplay().size() < 2) || player.hasShield()){
			output += Config.UNPLAYABLE;
		} else {
			Card cardToSteal = card.playRiposte(player);
			game.getCurrentPlayer().addToDisplay(cardToSteal);
			game.getCurrentPlayer().setTotalCardValue();
			player.setTotalCardValue();
				output += Config.RIPOSTE + " " + playerName + " " + player.getTotalCardValue() + " "
				+ cardToSteal.getType() + " " + cardToSteal.getValue()  + " " 
						+ game.getCurrentPlayer().getName()  + " " + game.getCurrentPlayer().getTotalCardValue();
		}
		return output;
	}
	
	public String processDodge(ActionCard card, String input) {
		String output = "";
		String[] cardString = input.split(" ");
		String playerName = cardString[2];
		String type = cardString[3];
		String value = cardString[4];
		Player player = game.getPlayerByName(playerName);
		if ((player.getDisplay().size() < 2) || player.hasShield()){
			output += Config.UNPLAYABLE;
		} else {
		for (Card c: player.getDisplay()) {
			if (c.getType().equals(type) && c.getValue() == Integer.parseInt(value)) {
				card.playDodge(player, c);
				player.setTotalCardValue();
				break;
			}
		}
			output += Config.DODGE + " " + playerName + " " + player.getTotalCardValue() + " " + type + " " + value;
		}
		return output;
	}
	
	public String processRetreat(ActionCard card, String input) {
		String output = "";
		String[] cardString = input.split(" ");
		String type = cardString[2];
		String value = cardString[3];
		Card cardToRetreat = null;
		for (Card c: game.getCurrentPlayer().getDisplay()) {
			if (c.getType().equals(type) && Integer.toString(c.getValue()).equals(value)) {
				cardToRetreat = c;
				break;
			}
		}
		if (game.getCurrentPlayer().getDisplay().size() < 2) {
			output += Config.UNPLAYABLE;
		} else {
			card.playRetreat(game, cardToRetreat);
			game.getCurrentPlayer().setTotalCardValue();
			output += Config.RETREAT + " " + game.getCurrentPlayer().getName() + " " + game.getCurrentPlayer().getTotalCardValue() + " " + type + " " + value;
		}
		return output;
	}
	
	public String processKnockdown(ActionCard card, String input) {
		String output = "";
		String[] cardString = input.split(" ");
		String playerName = cardString[2];
		Player player = game.getPlayerByName(playerName);
		if (player.hasShield()) {
			output += Config.UNPLAYABLE;
		}
		else {
			Card cardToSteal = card.playKnockDown(game, player);
			output += Config.KNOCKDOWN + " " + playerName + " " + cardToSteal.getType() + " " + cardToSteal.getValue();
		}
		return output;
	}
	
	public String processOutwit(ActionCard card, String input) {
		String output = "";
		String[] cardString = input.split(" ");
		String playerCardType = cardString[2];
		String playerCardValue = cardString[3];
		String opponentName = cardString[4];
		String opponentCardType = cardString[5];
		String opponentCardValue = cardString[6];
		Player opponent = game.getPlayerByName(opponentName);
		Card playerCard = new Card(playerCardType, Integer.parseInt(playerCardValue));
		Card opponentCard = new Card(opponentCardType, Integer.parseInt(opponentCardValue));
		if ((opponent.getDisplay().size() < 1) || opponent.hasShield()) {
			output += Config.UNPLAYABLE;
		} else {
			card.playOutwit(game, opponent, opponentCard, playerCard);
			game.getCurrentPlayer().setTotalCardValue();
			opponent.setTotalCardValue();
			output += Config.OUTWIT + " " + playerCardType + " " + playerCardValue 
					+ " " + opponentName + " " + opponentCardType + " " + opponentCardValue;
		}
		return output;
	}
	
	public String processStunned(ActionCard card, String input) {
		String output = "";
		String[] cardString = input.split(" ");
		String playerName = cardString[2];
		Player player = game.getPlayerByName(playerName);
		card.playStunned(player, card);
		output += Config.STUNNED + " " + playerName;
		return output;
	}
	
	public String processIvanhoe(ActionCard card, String input) {
		String output = "";
		String[] cardString = input.split(" ");
		String cardType = cardString[2];
		Card cardToDiscard = game.getCurrentPlayer().getCardFromHand(cardType, 0);
		output += Config.IVANHOE + " " + game.getCurrentPlayer().getName();
		game.getCurrentPlayer().removeCard(cardToDiscard);
		game.discard(cardToDiscard);
		return output;
	}
	

	
	public String updateDisplays() {
		String output = Config.DISPLAY + " ";
		for (Player p: game.getPlayers()) {
			p.setTotalCardValue();
			output += Config.PLAYER_NAME + " " + p.getName() + " " + p.getTotalCardValue() + " " + Config.PLAYER_CARDS + " ";
				for (Card c: p.getDisplay()) {
					output += c.getType() + "_" + c.getValue() + " ";
			}
		}
		return output;
	}


	public void joinGame(Player player) {
		game.joinGame(player);
	}
}
