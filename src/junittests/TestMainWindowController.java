package junittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import config.Config;
import game.Card;
import game.ColourCard;
import ui.MainWindowController;

public class TestMainWindowController {
	MainWindowController controller;
	Config config;
	Card testCard;
	@Before
	public void setup(){
		controller= new MainWindowController();
		config=new Config();
		testCard=new ColourCard("purple", 3,config.IMG_PURPLE_3);
	}
	@Test
	public void testPlayerNum() {
		controller.setPlayerNum(1);
		assertEquals(controller.getPlayerNum(),1);
	}
	@Test
	public void testPlayerAddCard() {
		ColourCard card= new ColourCard("purple",4);
		card.setCardImage(Config.IMG_IVANHOE);
		controller.addCard(card);
		assertEquals(controller.numCards(),1);
	}
	@Test
	public void testPlayerRemoveCard() {
		ColourCard card= new ColourCard("purple",4);
		card.setCardImage(Config.IMG_IVANHOE);
		controller.addCard(card);
		controller.removeCard(0);
		assertEquals(controller.numCards(),0);
	}
	
	@Test
	public void testAddPlayedCard(){		
		controller.setNumPlayers(3);
		controller.addPlayedCard(1, this.testCard);
		assertEquals(controller.getPlayedCard(1,0),testCard);
	}
	@Test
	public void testChangePlayerTurn(){
		controller.setCurrPlayer(1);
		assertEquals(controller.getCurrPlayer(),1);
	}
	@Test
	public void testAddNumPlayers(){
		controller.setNumPlayers(3);
		assertEquals(controller.getTotalPlayers(),3);
	}
	@Test
	public void testStartRound(){
		controller.setNumPlayers(3);
		controller.startRound();
	}	
	@Test
	public void testchangePlayerScore(){
		controller.setNumPlayers(3);
		controller.setScore(1,3);
		assertEquals(controller.getScore(1),3);
	}
	@Test
	public void testchangePlayerName(){
		controller.setNumPlayers(3);
		controller.setName(1,"alex");
		assertEquals(controller.getName(1),"alex");
	}
	@Test
	public void testSelectCard(){
		controller.getWindow().setLastCard(1);
		ColourCard card = new ColourCard("purple",4);
		card.setCardImage(Config.IMG_IVANHOE);
		controller.addCard(card);
		controller.addCard(card);
		int old = controller.getPlayerCardSize();
		controller.playCard();
		controller.removeCard(card);
		System.out.println(old);
		System.out.println(controller.getPlayerCardSize());
		assertEquals(controller.getPlayerCardSize(),old-1);
		
	}
	@Test
	public void testClickLeft(){
		addCards(15);
		controller.setMoved(2);
		int old = controller.getMoved();
		controller.leftClick();
		assertEquals(controller.getMoved(), old-1);
		
	}
	@Test
	public void testClickRight(){
		addCards(15);
		controller.setMoved(0);
		int old = controller.getMoved();
		controller.rightClick();
		assertEquals(controller.getMoved(), old+1);
		
	}
	@Test
	public void testClickRightMax(){
		addCards(12);
		controller.setMoved(3);
		int old = controller.getMoved();
		controller.rightClick();
		assertEquals(controller.getMoved() ,old);
		
	}
	public void addCards(int i){
		ColourCard card= new ColourCard("purple",4);
		card.setCardImage(Config.IMG_IVANHOE);
		for(i=i;i>=0;i--){
			controller.addCard(card);
		}
	}
	@Test
	public void testchangeBackgroundBlue(){
		controller.setTournamentColour(0);
		assertEquals(controller.getWindow().getContentPane().getBackground(),controller.getBackgroundColour(0));
		
	}
	@Test
	public void testchangeBackgroundRed(){
		controller.setTournamentColour(1);
		assertEquals(controller.getWindow().getContentPane().getBackground(),controller.getBackgroundColour(1));
		
	}
	@Test
	public void testchangeBackgroundYellow(){
		controller.setTournamentColour(2);
		assertEquals(controller.getWindow().getContentPane().getBackground(),controller.getBackgroundColour(2));
		
	}
	@Test
	public void testchangeBackgroundGreen(){
		controller.setTournamentColour(3);
		assertEquals(controller.getWindow().getContentPane().getBackground(),controller.getBackgroundColour(3));
		
	}
	@Test
	public void testchangeBackgroundPurple(){
		controller.setTournamentColour(4);
		assertEquals(controller.getWindow().getContentPane().getBackground(),controller.getBackgroundColour(4));
		
	}
	

}
