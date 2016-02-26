package uitests;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.swing.Icon;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.junit.testcase.FestSwingJUnitTestCase;
import org.junit.Test;

import ui.CardDisplayPopUp;
import ui.MainWindow;
import game.Card;
import game.ColourCard;

public class PopupTest extends FestSwingJUnitTestCase  {
	private FrameFixture editor;
	public static ArrayList<Card>cards;

	@Override
	protected void onSetUp() {
		// TODO Auto-generated method stub
		cards=new ArrayList<Card>();
		cards.add(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		cards.add(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		cards.add(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		cards.add(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		cards.add(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		cards.add(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		cards.add(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		cards.add(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		cards.add(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		cards.add(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		cards.add(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		cards.add(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		cards.add(new ColourCard("purple", 3,"resources/cards_small/simpleCards14.jpg"));
		cards.add(new ColourCard("purple", 4,"resources/cards_small/simpleCards15.jpg"));
		editor = new FrameFixture(robot(), createNewEditor());
	    editor.show();
	  }

	  @RunsInEDT
	  private static CardDisplayPopUp createNewEditor() {
	    return execute(new GuiQuery<CardDisplayPopUp >() {
	      protected CardDisplayPopUp  executeInEDT() throws Throwable {
	        return new CardDisplayPopUp (cards);
	      }
	    });
	  }
	
	
	
	@Test
	public void testLeftArrow() {
		editor.button("rightArrow").click();
		Icon old= editor.button("card2").target.getIcon();
		editor.button("leftArrow").click();
		assertTrue(editor.button("card3").target.getIcon().equals(old));
		//assertTrue(true);
		//fail("Not yet implemented");
	}
	@Test
	public void testRightArrow() {
		Icon old= editor.button("card2").target.getIcon();
		editor.button("rightArrow").click();
		assertTrue(editor.button("card1").target.getIcon().equals(old));
		//assertTrue(true);
		//fail("Not yet implemented");
	}



}


