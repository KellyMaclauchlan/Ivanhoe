package ai;

import java.util.ArrayList;
import game.Card;
import game.Player;

public interface Strategy{
	/* AI Strategy Interface */
	public String startTournament();
	public String playACard();
	
	public void getHand(ArrayList<Card> c);
	public void setStarted(boolean b);
	public boolean getStarted();
	
	public void setToPlay(Card c);
	public Card getToPlay();
	
	public void setCurrentPlayer(boolean p);
	public boolean getCurrentPlayer();
	
	public String getName();
	
	public void tokenChoice(ArrayList<String> tokens);
	
	public String processInput(String msg);
	public void processPlayerName(String msg);
	public String processPlayerTurn(String msg);
	public String processPlay(String msg);
	public String processContinueWithdraw(String msg);
	
}
