package junittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.ColourCard;
import ui.MainWindowController;

public class TestMainWindowController {
	MainWindowController controller;
	
	@Before
	public void setup(){
		controller= new MainWindowController();
		
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

}
