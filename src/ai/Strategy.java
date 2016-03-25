package ai;

import java.util.ArrayList;
import game.Card;

public interface Strategy{

	public String startTournament();
	public String playACard();
	public String continueWithdraw(); 
	
	public void getHand(ArrayList<Card> c);
	public void setStarted(boolean b);
	public boolean getStarted();
	public void setName(String name);
	
	public String processInput(String msg);
	public void processPlayerName(String msg);
	public String processPlayerTurn(String msg);
	public String processPlay(String msg);
	public String processContinueWithdraw(String msg);
	
}
