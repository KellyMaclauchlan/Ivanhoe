package junittests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	//TestClient.class, 
	TestGameStart2Player.class, 
	TestGameEngine.class, 
	TestScenarios.class, 
	TestActionCards.class, 
	//TestAI.class, 
	TestMainWindowController.class  })
public class AllTests {

}
