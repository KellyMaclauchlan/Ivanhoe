package uitests;

import static org.junit.Assert.*;

import javax.swing.Icon;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import static org.fest.swing.edt.GuiActionRunner.execute;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.fest.swing.junit.testcase.FestSwingJUnitTestCase;
import org.junit.Test;

import ui.MainWindow;

public class MainscreenTest extends FestSwingJUnitTestCase  {
	private FrameFixture editor;
	

	@Override
	protected void onSetUp() {
		// TODO Auto-generated method stub
		editor = new FrameFixture(robot(), createNewEditor());
	    editor.show();
	  }

	  @RunsInEDT
	  private static MainWindow createNewEditor() {
	    return execute(new GuiQuery<MainWindow>() {
	      protected MainWindow executeInEDT() throws Throwable {
	        return new MainWindow();
	      }
	    });
	  }
	
	
	
	@Test
	public void testLeftArrow() {
		Icon old= editor.button("card3").target.getIcon();
		editor.button("leftArrow").click();
		assertTrue(editor.button("card2").target.getIcon().equals(old));
		//assertTrue(true);
		//fail("Not yet implemented");
	}
	@Test
	public void testRightArrow() {
		Icon old= editor.button("card1").target.getIcon();
		editor.button("rightArrow").click();
		assertTrue(editor.button("card2").target.getIcon().equals(old));
		//assertTrue(true);
		//fail("Not yet implemented");
	}
	@Test
	public void testWithdrawClick() {
		editor.radioButton("player1name").target.setSelected(true);
		editor.button("withdraw").click();
		assertFalse(editor.radioButton("player1name").target.isSelected());
		//assertTrue(true);
		//fail("Not yet implemented");
	}
	@Test
	public void testEndTurnClick() {
		editor.radioButton("player1name").target.setSelected(true);
		editor.button("endTurn").click();
		assertFalse(editor.radioButton("player1name").target.isSelected());
		//assertTrue(true);
		//fail("Not yet implemented");
	}
	

}
