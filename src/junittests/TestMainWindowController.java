package junittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import config.Config;
import game.ColourCard;
import ui.MainWindowController;

public class TestMainWindowController {
	MainWindowController controller;
	Config config;
	@Before
	public void setup(){
		controller= new MainWindowController();
		config=new Config();
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

}
