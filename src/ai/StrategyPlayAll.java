package ai;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import game.Card;

public class StrategyPlayAll implements Strategy{
	/* This strategy plays all possible cards in a round even though it might
	 * not be the smartest strategy */
	
	/* Pick a tournament colour depending on how many colours of each card
	 * it has:
	 * >3 choose that colour
	 * anything less than choose the most colour card
	 */
	
	private Logger log = Logger.getLogger("AI");
	private ArrayList<Card> hand = new ArrayList<Card>();
	private String output = "result";
	private boolean started = false;
	private boolean currentPlayer = false;
	private String name;
	
	public void setStarted(boolean b){started = b;}
	public boolean getStarted(){return started;}
	public void setName(String n){this.name = n;}
	
	public StrategyPlayAll(){
		log.info("New AI of type 'Play All' has been created");
	}

	public String startTournament() {
		return null;
	}

	public String playACard() {
		return output;
	}

	public void getHand(ArrayList<Card> c) {
		hand = c;
	}
	
	public String processInput(String msg){
		return output; 
	}
	
	public void processPlayerName(String msg){
	}
	
	public String processPlayerTurn(String msg){
		return output;
	}
	
	public String processPlay(String msg){
		output = playACard();
		return output;
	}
	
	public String processContinueWithdraw(String msg){
		return output;
	}

}
