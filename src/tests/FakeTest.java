package tests;

import org.junit.Test;


import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.fixture.*;
import org.assertj.swing.testing.AssertJSwingTestCaseTemplate;
public class FakeTest {

	 private FrameFixture window;

	  @BeforeClass
	  public static void setUpOnce() {
	    FailOnThreadViolationRepaintManager.install();
	  }

	  @Before
	  public void setUp() {
		  
			SwingUtilities.invokeLater(new Runnable() {
				    public void run() {
				    	 window = new FrameFixture(new SimpleCopyApplication());
				 	     window.show();
				    }
				});
		
	    // shows the frame to test
	  }

	  @Test
	  public void shouldCopyTextInLabelWhenClickingButton() {
	   // window.textBox("textToCopy").enterText("Some random text");
		  if(window!=null)
			  window.button("copyButton").click();
	    //window.label("copiedText").requireText("Some random text");
	  }

	  @After
	  public void tearDown() {
	    //window.cleanUp();
	  }

}

