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
		testCard=new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg");
	}
	@Test
	public void testPlayerNum() {
		controller.setPlayerNum(1);
		assertEquals(controller.getPlayerNum(),1);
	}
	@Test
	public void testPlayerAddCard() {
		ColourCard card= new ColourCard("purple",4);
		controller.addCard(card);
		assertEquals(controller.numCards(),1);
	}
	@Test
	public void testPlayerRemoveCard() {
		ColourCard card= new ColourCard("purple",4);
		controller.addCard(card);
		controller.removeCard(0);
		assertEquals(controller.numCards(),0);
	}
	@Test
	public void testPlayerClickLeft() {
		controller.window.leftArrowClicked();
		assertEquals(controller.lastMessege,config.LEFT_CLICK);
	}
	@Test
	public void testPlayerClickRight() {
		controller.window.rightArrowClicked();
		assertEquals(controller.lastMessege,config.RIGHT_CLICK);
	}
	@Test
	public void testPlayerWithdrawLeft() {
		controller.window.withdrawClicked();
		assertEquals(controller.lastMessege,config.WITHDRAW_CLICK);
	}
	@Test
	public void testPlayerEndTurnLeft() {
		controller.window.endTurnClicked();
		assertEquals(controller.lastMessege,config.END_TURN_CLICK);
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
		controller.startRound();
	}	
	@Test
	public void testchangePlayerScore(){
		controller.setNumPlayers(3);
		controller.SetScore(1,3);
		assertEquals(controller.getScore(1),3);
	}
	@Test
	public void testchangePlayerName(){
		controller.setNumPlayers(3);
		controller.SetName(1,"alex");
		assertEquals(controller.getName(1),"alex");
	}
	@Test
	public void testSelectCard(){
		controller.window.lastCard=1;
		ColourCard card= new ColourCard("purple",4);
		controller.addCard(card);
		controller.addCard(card);
		int old=controller.getPlayerCardSize();
		controller.playCard();
		System.out.println(old);
		System.out.println(controller.getPlayerCardSize());
		assertEquals(controller.getPlayerCardSize(),old-1);
		
	}
	@Test
	public void testClickLeft(){
		controller.moved=5;
		int old = controller.moved;
		controller.leftClick();
		assertEquals(controller.moved,old-1);
		
	}
	@Test
	public void testClickRight(){
		controller.moved=5;
		int old = controller.moved;
		controller.rightClick();
		assertEquals(controller.moved,old+1);
		
	}
	

}
