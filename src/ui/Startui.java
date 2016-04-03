package ui;

import config.Config;
import game.Card;
import game.ColourCard;

public class Startui {
	public static void main(String[] args) {
		MainWindowController control= new MainWindowController();
		control.showWindow();
		control.setNumPlayers(3);
		control.setPlayerNum(0);
		Card c1=new ColourCard("purple", 3,Config.IMG_PURPLE_3);
		Card c2= new ColourCard("purple", 4,Config.IMG_PURPLE_4);
		c1.setCardDescription(Config.infoStrings.get(33));
		c2.setCardDescription(Config.infoStrings.get(34));
		control.addCard(c1);
		control.addCard(c2);
		control.addCard(c1);
		control.addCard(c2);
		control.addCard(c1);
		control.addCard(c2);
		control.addCard(c1);
		control.addCard(c2);
		control.addCard(c1);
		control.addCard(c2);
		control.addCard(c1);
		control.addCard(c2);
		control.addCard(c1);
		control.addCard(c2);
		control.addCard(c1);
		control.addCard(c2);
		
		control.setScore(0, 5);
		control.addPlayedCard(0, c1);
		control.addPlayedCard(1, c2);
		control.addPlayedCard(0, c1);
		control.addPlayedCard(0, c1);
		control.addPlayedCard(0, c2);
		control.addPlayedCard(0, c1);
		control.addPlayedCard(0, c2);
		control.addPlayedCard(0, c1);
		control.addPlayedCard(0, c2);
		control.addPlayedCard(0, c1);
		control.addPlayedCard(0, c2);
		control.addPlayedCard(0, c1);
		control.addPlayedCard(0, c2);
		control.addPlayedCard(0, c1);
		control.addPlayedCard(0, c2);
		control.addPlayedCard(0, c1);
		control.addPlayedCard(0, c2);
		control.addPlayedCard(0, c1);
		control.addPlayedCard(0, c2);
		control.setVarPlayerName(control.getNameFromPlayer());
		control.setName(0, control.getPlayerName());
		control.getPlayerNamesArray().add(0, control.getPlayerName());
		control.setCurrPlayer(control.getPlayerNum());
		control.setShield(0, true);
		control.setStun(0, true);
		control.setStun(0, false);

		
	}
}
