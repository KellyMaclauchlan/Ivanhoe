package ui;

import config.Config;
import game.ColourCard;

public class Startui {
	public static void main(String[] args) {
		//MainWindow window =new MainWindow();
		MainWindowController control= new MainWindowController();
		control.showWindow();
		control.setNumPlayers(3);
		//control.setName(0, control.getNameFromPlayer());
		control.setPlayerNum(0);
		control.addCard(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addCard(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.addCard(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addCard(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.addCard(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addCard(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.addCard(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addCard(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.addCard(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addCard(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.addCard(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addCard(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.addCard(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addCard(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.setScore(0, 5);
		control.addPlayedCard(0, new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.addPlayedCard(1, new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		control.addPlayedCard(0, new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		control.addToken(0, 1);
		control.addToken(0, 3);
		control.addToken(0, 4);
		control.addToken(1, 0);
		control.addToken(1, 2);
		//control.playerNames.add("kelly");
		//control.playerNames.add("katie");
		//control.playerNames.add("brit");
		control.playerName=control.getNameFromPlayer();
		control.setName(0, control.playerName);
		control.playerNames.add(0, control.playerName);
		control.removeToken(1, Config.BLUE);
		//control.playerWithdraws("jo");
		control.window.startTurn();
		//testing various pop ups
		//control.setTournamnetColour(control.setTournament());
		//System.out.println(control.playerPickToken() );
		//control.showWaiting();
		//System.out.println(control.getIPPortFromPlayer());
		//control.hideWaitng();
		//System.out.println(control.getNumberOfPlayersFromPlayer());
		//System.out.println(control.changeColour());
		//System.out.println(control.pickAName("testing to pick a name."));
				//System.out.println(
		
	}
}
