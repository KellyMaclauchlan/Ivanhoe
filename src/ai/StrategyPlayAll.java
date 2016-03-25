package ai;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import game.Card;

public class StrategyPlayAll implements Strategy{
	/* This strategy plays all possible cards in a round even though it might
	 * not be the smartest strategy */
	
	private Logger log = Logger.getLogger("AI");
	private ArrayList<Card> hand = new ArrayList<Card>();
	private String output = "result";
	private boolean started = false;
	
	public void setStarted(boolean b){started =b;}
	public boolean getStarted(){return started;}
	
	public StrategyPlayAll(){
		log.info("New AI of type 'Play All' has been created");
	}

	public String startTournament() {
		return null;
	}

	public String playACard() {
		return output;
	}

	public String continueWithdraw() {
		return null;
		
	}

	public void getHand(ArrayList<Card> c) {
		hand = c;
	}

}
