package uitests;

import static org.junit.Assert.*;

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
	public void test() {
		//editor.button();
		editor.button("leftArrow").click();
		assertTrue(editor.label("deck").text() =="left click");
		assertTrue(true);
		//fail("Not yet implemented");
	}

}
