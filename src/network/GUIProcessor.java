package network;

import game.Card;

import config.Config;

public class GUIProcessor {
	Client client;
	
	public GUIProcessor(Client client) {
		this.client = client;
	}
	
	public String processPromptJoin(String msg){
		String name = client.getWindow().getNameFromPlayer();
		client.setThisPlayerName(name);
		client.getWindow().setVarPlayerName(name);
		return Config.JOIN + " " + name;	
	}
	
	public String processPlayerName(String msg){
		msg = msg.substring(10);
		String name[] = msg.split("name");
		String card[];
		String value[];
		client.getWindow().setNumPlayers(name.length);
		for (int i = 0; i < name.length; i++){
			card = name[i].split(" ");
			if (card[1].equalsIgnoreCase(client.getWindow().getPlayerName())){
				for (int k = 3; k < card.length; k++){
					client.addToHand(card[k]);					
					value = card[k].split("_");
					client.getWindow().addCard(client.getCardFromTypeValue(value[0],value[1]));
				}
				client.getWindow().resetCards();
				client.getWindow().setPlayerNum(i);
			}
			client.getWindow().setName(i, card[1]);
			
		}
		client.getWindow().showWindow();
		client.getWindow().endTurn();
		
		client.logActivity("***********************\n" +
						"Tournament has Begun\n" +
					"***********************\n");
		
		return Config.START_TOURNAMENT;
	}

	public String processPlayerTurn(String msg){
		String input[] = msg.split(" ");
		
		client.getWindow().showWindow();
		if (msg.contains(Config.PICKED_PURPLE)){
			
				if (input[3].equalsIgnoreCase(client.getWindow().getPlayerName())){
					client.getWindow().startTurn();
					String value[] = input[4].split("_");
					client.getWindow().addCard(client.getCardFromTypeValue(value[0], value[1]));
					client.setOutput(Config.COLOUR_PICKED + " " + client.getWindow().setTournament());
				}
				for (int i = 0; i < client.getWindow().getTotalPlayers();i++){
					if((client.getWindow().getName(i)).equalsIgnoreCase(input[3])){
						client.getWindow().setCurrPlayer(i);
						client.setCurrPlayer(client.getWindow().getName(i));
					}
				}
				client.logActivity(client.getCurrPlayer() + " has the first turn");
				
		} else {
			client.getWindow().startRound();
			if(input[1].equalsIgnoreCase(client.getWindow().getPlayerName())){
				client.getWindow().startTurn();
				String value[] = input[2].split("_");
				client.getWindow().addCard(client.getCardFromTypeValue(value[0], value[1]));
				client.setOutput(Config.COLOUR_PICKED + " " + client.getWindow().setTournament());	
			}
			
			for (int i = 0; i < client.getWindow().getTotalPlayers(); i++){
				if(client.getWindow().getName(i).equalsIgnoreCase(input[1])){
					client.getWindow().setCurrPlayer(i);
					client.setCurrPlayer(client.getWindow().getName(i));
				}
			}
			client.logActivity("It is " + client.getCurrPlayer() + "'s turn");
		}
		return client.getOutput();
	}
	
	public String processColour(String msg) {
		String input[] = msg.split(" ");
		String colour = input[1];
		if (colour.equals(Config.PURPLE)) {
			client.getWindow().setLastTournamentPurple(true);
		}
		for(int i = 0; i < 5; i++){
			if (colour.equalsIgnoreCase(client.getOptions()[i])){
				client.getWindow().setTournamentColour(i);
			}
		}
		return client.getOutput();
	}
	
	public String processPlay(String msg){
		String output = "result";
		output = msg;
		return output;
	}

	public String processWaiting(String msg){
		Boolean isAction = false;
		if(msg.contains(Config.UNPLAYABLE)){
			client.getWindow().addCard(client.getWindow().getLastCard());
			client.getWindow().cantPlayCardPopup();
		} else {
			String input[] = msg.split(" ");
			if (input.length > 1) {
				String[] c = null;
				String type = null;
				String value = null;
				if (input[1].contains("_")) {
					c = input[1].split("_");
					type = c[0];
					value = c[1];
				} else {
					type = input[1];
					value = "0";
					isAction=true;
				}
				Card card = client.getCardFromTypeValue(type, value);
				if (isAction){
					this.processActionCardAction(msg);
				}
				else {
					client.getWindow().addPlayedCard(client.getWindow().getCurrPlayer(), card);
				}
				if (client.getWindow().getCurrPlayer() == client.getWindow().getPlayerNum()){
					client.getWindow().removeCard(card);
				}
			}
		}
		client.logActivity(msg);
		return client.getOutput();
	}
	
	public String processActionCard(){
		String output = "";
		String cardType;
		cardType = client.getWindow().getLastCard().getType();
		output = " " + cardType + " " ;
		if (cardType.equals(Config.UNHORSE)){
			if (client.getWindow().getTournamentColour() != Config.PURPLE_INT) {
				output += Config.PURPLE;
			} else {
				output += client.getWindow().changeColour();
			}
		} else if (cardType.equals(Config.CHANGEWEAPON)) {
			output = " " + Config.CHANGEWEAPON + " " + client.getWindow().changeColour();
		}
		else if (cardType.equals(Config.DROPWEAPON)) {
			output = " " + Config.DROPWEAPON;
		} else if (cardType.equalsIgnoreCase(Config.BREAKLANCE)){
				output = " " + Config.BREAKLANCE + " " + client.getWindow().pickAName("remove all purple cards from their display.");
		} else if (cardType.equalsIgnoreCase(Config.RIPOSTE)){
			output = " " + Config.RIPOSTE + " " + client.getWindow().pickAName("take the last card on their display and add it to yours.");
		} else if (cardType.equalsIgnoreCase(Config.RETREAT)){
			output = " " + Config.RETREAT + " " + client.getWindow().playerPickCardFromDisplay(client.getWindow().getPlayerName());
		} else if (cardType.equalsIgnoreCase(Config.KNOCKDOWN)){
			output = " " + Config.KNOCKDOWN + " " + client.getWindow().pickAName("take a card from.");
		} else if (cardType.equalsIgnoreCase(Config.STUNNED)){
			output = " " + Config.STUNNED + " " + client.getWindow().pickAName("stun.");
		} else if (cardType.equalsIgnoreCase(Config.OUTWIT)){
			output = " " + Config.OUTWIT + " " + client.getWindow().playerPickCardForOutwit(client.getWindow().getPlayerName());
			String name = client.getWindow().pickAName("take a played card from.");
			output += " " + name + " " + client.getWindow().playerPickCardForOutwit(name);
		} else if (cardType.equalsIgnoreCase(Config.DODGE)){
			String name = client.getWindow().pickAName("take a played card from.");
			output = " " + Config.DODGE + " " + name + " " + client.getWindow().playerPickCardFromDisplay(name);
		} else if (cardType.equals(Config.OUTMANEUVER)) {
			output = " " + Config.OUTMANEUVER;
		} else if (cardType.equals(Config.CHARGE)) {
			output = " " + Config.CHARGE;
		} else if (cardType.equals(Config.COUNTERCHARGE)) {
			output = " " + Config.COUNTERCHARGE;
		} else if (cardType.equals(Config.DISGRACE)) {
			output = " " + Config.DISGRACE;
		} else if (cardType.equals(Config.ADAPT)) {
			output = " " + Config.ADAPT;
		} else if (cardType.equals(Config.SHIELD)) {
			output = " " + Config.SHIELD;
		}	
		return output;
	}
	
	public void processActionCardAction(String msg){
		client.setIvanhoePrompted(false);
		String input[]=msg.split(" ");
		String cardType = input[1];
		if (cardType.equalsIgnoreCase(Config.KNOCKDOWN)){
			if(client.getWindow().getPlayerName().equalsIgnoreCase(input[2])){
				client.getWindow().removeCard(client.getWindow().getPlayerCard(0));
			}
			if (client.getWindow().getCurrPlayer()==client.getWindow().getPlayerNum()){
				client.getWindow().addCard(client.getCardFromTypeValue(input[3], input[4]));
			}
		} else if (cardType.equalsIgnoreCase(Config.RIPOSTE)){
			int player = client.getWindow().getPlayerByName(input[2]);
			String playerScore = input[3];
			Card c = client.getCardFromTypeValue(input[4], input[5]);
			client.getWindow().removePlayedCard(player, c);
			client.getWindow().setScore(player, Integer.parseInt(playerScore));
			int addtoplayer = client.getWindow().getPlayerByName(input[6]);
			String addToPlayerScore = input[7];
			client.getWindow().addPlayedCard(addtoplayer,c);	
			client.getWindow().setScore(addtoplayer, Integer.parseInt(addToPlayerScore));
		} else if (cardType.equalsIgnoreCase(Config.BREAKLANCE)){
			int player = client.getWindow().getPlayerByName(input[4]);
			String score = input[5];
			client.getWindow().setScore(player, Integer.parseInt(score));
			client.getWindow().resetPlayedCards(player);
			for (int i = 7; i < input.length ; i += 2) {
				client.getWindow().addPlayedCard(player, client.getCardFromTypeValue(input[i], input[i+1]));
			}		
		} else if (cardType.equalsIgnoreCase(Config.CHANGEWEAPON)||cardType.equalsIgnoreCase(Config.UNHORSE)||cardType.equalsIgnoreCase(Config.DROPWEAPON)){
			client.getWindow().setTournamentColour(Config.colours.indexOf(input[2]));
		} else if (cardType.equalsIgnoreCase(Config.SHIELD)){
			client.getWindow().setShield(client.getWindow().getPlayerByName(input[2]), true);
		} else if (cardType.equalsIgnoreCase(Config.STUNNED)){
			client.getWindow().setStun(client.getWindow().getPlayerByName(input[2]), true);
		} else if (cardType.equalsIgnoreCase(Config.DISGRACE)){
			redoDisplay(input);
		} else if (cardType.equalsIgnoreCase(Config.ADAPT)){
			redoDisplay(input);
		} else if (cardType.equalsIgnoreCase(Config.CHARGE)){
			redoDisplay(input);
		} else if (cardType.equalsIgnoreCase(Config.COUNTERCHARGE)){
			redoDisplay(input);
		} else if (cardType.equalsIgnoreCase(Config.OUTMANEUVER)){
			redoDisplay(input);
		} else if (cardType.equalsIgnoreCase(Config.DODGE)){
			String[] card={input[4],input[5]};
			int player = client.getWindow().getPlayerByName(input[2]);
			int score = Integer.parseInt(input[3]);
			client.getWindow().setScore(player, score);
			client.getWindow().removePlayedCard(player, client.getCardFromTypeValue(card[0], card[1]));			
		}
		else if (cardType.equalsIgnoreCase(Config.RETREAT)) {
			String[] card = {input[4],input[5]};
			int player = client.getWindow().getPlayerByName(input[2]);
			int score = Integer.parseInt(input[3]);
			client.getWindow().setScore(player, score);
			Card c = client.getCardFromTypeValue(card[0], card[1]);
			client.getWindow().removePlayedCard(player, c);
			if (client.getWindow().getPlayerName().equalsIgnoreCase(input[2])) {
				client.getWindow().addCard(c);
			}
		}
		else if(cardType.equalsIgnoreCase(Config.OUTWIT)){
			String playerCardType = input[2];
			String playerCardValue = input[3];
			String opponentName = input[4];
			String opponentCardType = input[5];
			String opponentCardValue = input[6];
			Card playerCard = client.getCardFromTypeValue(playerCardType, playerCardValue);
			Card opponentCard = client.getCardFromTypeValue(opponentCardType, opponentCardValue);
			int currentPlayer = client.getWindow().getCurrPlayer();
			int opponent = client.getWindow().getPlayerByName(opponentName);
			
			client.getWindow().setScore(currentPlayer, client.getWindow().getScore(currentPlayer) 
					+ Integer.parseInt(opponentCardValue) - Integer.parseInt(playerCardValue));
			client.getWindow().setScore(opponent, client.getWindow().getScore(opponent) 
					+ Integer.parseInt(playerCardValue) - Integer.parseInt(opponentCardValue));
			
			if (playerCard.getType().equalsIgnoreCase(Config.SHIELD)){
				client.getWindow().setShield(currentPlayer, false);
				client.getWindow().setShield(opponent, true);
			} else if (playerCard.getType().equalsIgnoreCase(Config.STUNNED)){
				client.getWindow().setStun(currentPlayer, false);
				client.getWindow().setStun(opponent, true);
			} else {
				client.getWindow().removePlayedCard(currentPlayer,playerCard);
				client.getWindow().addPlayedCard(opponent, playerCard);
			} if (opponentCard.getType().equalsIgnoreCase(Config.SHIELD)){
				client.getWindow().setShield(currentPlayer, true);
				client.getWindow().setShield(opponent, false);
			} else if (opponentCard.getType().equalsIgnoreCase(Config.STUNNED)){
				client.getWindow().setStun(currentPlayer, true);
				client.getWindow().setStun(opponent, false);
			} else {
				client.getWindow().removePlayedCard(opponent, opponentCard);
				client.getWindow().addPlayedCard(currentPlayer,opponentCard);
			}
		}
	}
	
	public void redoDisplay(String[] input) {
		for (int i = 0; i < client.getWindow().getTotalPlayers();i++){
			client.getWindow().resetPlayedCards(i);
		}

		int name = -1;
		for (int i=3;i<input.length;i++){
			if (input[i].equalsIgnoreCase(Config.PLAYER_NAME)){
				name=-1;
			} else if (input[i-1].equalsIgnoreCase(Config.PLAYER_NAME)){
				name = client.getWindow().getPlayerByName(input[i]);
			} else if (input[i-2].equalsIgnoreCase(Config.PLAYER_NAME)){
				client.getWindow().setScore(name, Integer.parseInt(input[i]));
			}
			else if (input[i].equalsIgnoreCase(Config.PLAYER_CARDS)){
				// do nothing
			} else {
				String[] card= input[i].split("_");
				client.getWindow().addPlayedCard(name, client.getCardFromTypeValue(card[0], card[1]));						
			}
		}
	}
	
	public String processContinueWithdraw(String msg){
		String output = "result";
		String input[] = msg.split(" ");
		String currentPlayerName = input[0];
		client.setCurrPlayer(currentPlayerName);
		String winningPlayerName = input[input.length - 1];
		String score = input[2];
		String nextPlayerName = input[4];
		int winningPlayer = client.getWindow().getPlayerByName(winningPlayerName);
		int currentPlayer = client.getWindow().getPlayerByName(currentPlayerName);
		client.getWindow().setScore(currentPlayer, Integer.parseInt(score));
		
		if (msg.contains(Config.PURPLE_WIN)){
			client.getWindow().setCurrPlayer(winningPlayer);
			if (client.getWindow().getPlayerName().equalsIgnoreCase(input[input.length - 1])) {
				String chosenColour = client.getWindow().playerPickToken();
				output = Config.PURPLE_WIN + " " + chosenColour;
				if (msg.contains(Config.WITHDRAW)){
					client.logActivity("\n" + client.getCurrPlayer() + " has ended their turn \nand withdrawn from the \ntournament\n");
				}
				client.setCurrPlayer(winningPlayerName);
				
				for (int i = 0; i < 5; i++){
					if(chosenColour.equalsIgnoreCase(client.getOptions()[i])){
						client.getWindow().setTournamentColour(i);
							if (chosenColour.equals(Config.PURPLE)) {
								client.setPurpleChosen(true);;
							}
					}
				}
			}
		} else {
			
			if (msg.contains(Config.TOURNAMENT_WINNER)){
				client.getWindow().setCurrPlayer(winningPlayer);
				client.getWindow().startRound();
				String chosenColour = input[5];
				for (int i = 0; i < 5; i++){
					if (chosenColour.equalsIgnoreCase(client.getOptions()[i])) {
						client.getWindow().setTournamentColour(i);
							if (chosenColour.equals(Config.PURPLE)) {
								client.setPurpleChosen(true);;
							}
					}
				}
				if (!(client.getWindow().getTournamentColour() == 4) || client.isPurpleChosen()) {
					client.getWindow().addToken(client.getWindow().getCurrPlayer(), client.getWindow().getTournamentColour());
					client.setPurpleChosen(false);;
				}
				for(int i = 0; i < client.getWindow().getPlayerNum(); i++){
					client.getWindow().setScore(i, 0);
				}
				client.getWindow().setScore(winningPlayer, 0);
				
				if (msg.contains(Config.WITHDRAW)){
					client.logActivity("\n" + client.getCurrPlayer() + " has ended their turn \nand withdrawn from the \ntournament\n");
				}
				
				client.setCurrPlayer(winningPlayerName);
				client.logActivity("\n" + client.getCurrPlayer() + " won the tournament\n" + 
							"*************************\n" + 
							"Starting New Tournament\n" + 
							"*************************\n");
				
				output = Config.START_TOURNAMENT;
			}
			
			else if (!msg.contains(Config.TOURNAMENT_WINNER)){
				client.getWindow().setScore(client.getWindow().getCurrPlayer(), Integer.parseInt(score));
				
				if (msg.contains(Config.CONTINUE)){
					client.logActivity(client.getCurrPlayer() + " has ended their turn");
				}
				else if (msg.contains(Config.WITHDRAW)){
					client.logActivity(client.getCurrPlayer() + " has ended their turn \nand withdrawn from the \ntournament\n");
					client.setCurrPlayer(nextPlayerName);
				}
				
				if (client.getWindow().getPlayerNum() == currentPlayer){
					client.getWindow().endTurn();
				}
				
				for (int i = 0; i < client.getWindow().getPlayerNamesArray().size(); i++){
					if (client.getWindow().getName(i).equalsIgnoreCase(nextPlayerName)){						
						client.getWindow().setCurrPlayer(i);
						client.setCurrPlayer(client.getWindow().getName(i));
						if (client.getWindow().getPlayerNum() == client.getWindow().getCurrPlayer()){
							client.getWindow().startTurn();
							String card[] = input[5].split("_");
							String type = card[0];
							String value = card[1];
							client.getWindow().addCard(client.getCardFromTypeValue(type, value));
							
							client.logActivity("\nIt is now: " + client.getCurrPlayer() + "'s turn");
						}
					}
				}
			}
		}
		return output; 
	}
}
